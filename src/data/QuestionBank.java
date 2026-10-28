package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import model.Question;

public class QuestionBank
{
    private static final Map<String, ArrayList<Question>> questionGroups;

    static
    {
        try
        {
            questionGroups = QuestionCSVReader.loadQuestionsByGroup();
        }
        catch(IOException exception)
        {
            throw new ExceptionInInitializerError("Failed to load questions.csv: " + exception.getMessage());
        }
    }

    private static ArrayList<Question> getGroup(String groupName)
    {
        ArrayList<Question> originalQuestions = questionGroups.get(groupName);

        if(originalQuestions == null)
        {
            throw new IllegalStateException("Question group not found: " + groupName);
        }

        ArrayList<Question> copiedQuestions = new ArrayList<>();

        for(Question question : originalQuestions)
        {
            Question copiedQuestion = new Question(
                question.getQuestion(),
                question.getChoices().clone(),
                question.getCorrectAnswer(),
                question.getCategory()
            );

            copiedQuestions.add(copiedQuestion);
        }

        return copiedQuestions;
    }

    public static ArrayList<Question> getModule1Questions()
    {
        return getGroup("module1");
    }

    public static ArrayList<Question> getModule2Questions()
    {
        return getGroup("module2");
    }

    public static ArrayList<Question> getModule3Questions()
    {
        return getGroup("module3");
    }

    public static ArrayList<Question> getModule4Questions()
    {
        return getGroup("module4");
    }

    public static ArrayList<Question> getModule5Questions()
    {
        return getGroup("module5");
    }

    public static ArrayList<Question> getModule6Questions()
    {
        return getGroup("module6");
    }

    public static ArrayList<Question> getModule7Questions()
    {
        return getGroup("module7");
    }

    public static ArrayList<Question> getTypeAQuestions()
    {
        return getGroup("typea");
    }

    public static ArrayList<Question> getTypeBQuestions()
    {
        return getGroup("typeb");
    }

    public static ArrayList<Question> getTypeCQuestions()
    {
        return getGroup("typec");
    }
}