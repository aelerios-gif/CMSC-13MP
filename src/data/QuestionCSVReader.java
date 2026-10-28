package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Question;
import model.QuestionCategory;

public class QuestionCSVReader 
{
    private static final Path CSV_PATH = Path.of("resources", "data", "questions.csv");

    public static Map<String, ArrayList<Question>> loadQuestionsByGroup() throws IOException
    {
        String csv = Files.readString(CSV_PATH, StandardCharsets.UTF_8);

        List<List<String>> records = parseCSV(csv);

        if(records.isEmpty())
        {
            throw new IOException("The questions CSV file is empty.");
        }

        List<String> header = records.get(0);

        if(header.size() != 10
            || !header.get(0).trim().equalsIgnoreCase("id")
            || !header.get(1).trim().equalsIgnoreCase("section")
            || !header.get(2).trim().equalsIgnoreCase("module")
            || !header.get(3).trim().equalsIgnoreCase("question"))
        {
            throw new IOException("Invalid CSV header. Expected the 10-column questions format.");
        }

        Map<String, ArrayList<Question>> groups = new LinkedHashMap<>();

        for(int i = 1; i < records.size(); i++)
        {
            List<String> row = records.get(i);

            if(row.size() != 10)
            {
                throw new IOException(
                "CSV record " + (i + 1) 
                + " has " + row.size() 
                + " columns instead of 10.");
            }

            String section = row.get(1).trim().toUpperCase(Locale.ROOT);
            String group = row.get(2).trim().toLowerCase(Locale.ROOT);
            String questionText = row.get(3);

            String[] choices = {
                row.get(4),
                row.get(5),
                row.get(6),
                row.get(7)
            };

            String answerLetter = row.get(8).trim().toUpperCase(Locale.ROOT);

            int correctAnswer;

            switch(answerLetter)
            {
                case "A":
                    correctAnswer = 0;
                    break;

                case "B":
                    correctAnswer = 1;
                    break;
                
                case "C":
                    correctAnswer = 2;
                    break;

                case "D":
                    correctAnswer = 3;
                    break;

                default:
                    throw new IOException("Invalid correct answer at CSV record " + (i + 1) + ": " + answerLetter);
            }

            String categoryName = row.get(9).trim();

            QuestionCategory category;

            try
            {
                category = QuestionCategory.valueOf(categoryName);
            }
            catch(IllegalArgumentException exception)
            {
                throw new IOException("Invalid question category at CSV record " + (i + 1) + ": " + categoryName, exception);
            }


            boolean validGroup = 
                section.equals("THEORETICAL")
                && group.matches("module[1-7]")
                || section.equals("PRACTICAL")
                && group.matches("type[a-c]");

            if(!validGroup)
            {
                throw new IOException("Invalid section/group at CSV record " + (i + 1) + ": " + section + "/" + group);
            }

            Question question = new Question(
                questionText,
                choices,
                correctAnswer,
                category
            );

            groups.computeIfAbsent(group, key -> new ArrayList<Question>()).add(question);
        }

        return groups;
    }

    private static List<List<String>> parseCSV(String csv) throws IOException
    {
        List<List<String>> records = new ArrayList<>();
        List<String> record = new ArrayList<>();
        StringBuilder field = new StringBuilder();

        boolean insideQuotes = false;

        for(int i = 0; i < csv.length(); i++)
        {
            char current = csv.charAt(i);

            if(current == '"')
            {
                if(insideQuotes 
                    && i + 1 < csv.length() 
                    && csv.charAt(i + 1) == '"')
                {
                    field.append('"');
                    i++;
                }
                else
                {
                    insideQuotes = !insideQuotes;
                }
            }
            else if(current == ',' && !insideQuotes)
            {
                record.add(field.toString());
                field.setLength(0);
            }
            else if((current == '\n' || current == '\r') && !insideQuotes)
            {
                if(current == '\r' 
                    && i + 1 < csv.length() 
                    && csv.charAt(i + 1) == '\n')
                {
                    i++;
                }

                record.add(field.toString());
                field.setLength(0);

                if(!isBlankRecord(record))
                {
                    records.add(record);
                }

                record = new ArrayList<>();
            }
            else
            {
                field.append(current);
            }
        }

        if(insideQuotes)
        {
            throw new IOException("Invalid CSV: a quoted field was never closed.");
        }

        if(field.length() > 0 || !record.isEmpty())
        {
            record.add(field.toString());

            if(!isBlankRecord(record))
            {
                records.add(record);
            }
        }

        return records;
    }

    private static boolean isBlankRecord(List<String> record)
    {
        for(String value : record)
        {
            if(!value.isBlank())
            {
                return false;
            }
        }

        return true;
    }
}
