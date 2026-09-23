import java.util.ArrayList;

public class QuestionBank {

    public static ArrayList<Question> getQuestions() {

        ArrayList<Question> questions =
            new ArrayList<>();

        // QUESTION 1

        questions.add(new Question(
            "Sino pinaka pogi sa buong mundo?",

            new String[] {
                "Allan Lerios",
                "Allan II",
                "Lerios, Allan II E.",
                "Allan Eamiguel Lerios"
            },

            1
        ));

        // QUESTION 2

        questions.add(new Question(
            "Sinech?",

            new String[] {
                "Lalalala",
                "omsimm",
                "Eatey",
                "dingdong"
            },

            2
        ));

        // QUESTION 3

        questions.add(new Question(
            "Okay ka lang po ba?",

            new String[] {
                "GRRR",
                "DAPAT OKAY PALAGI",
                "DING DONG",
                "ERMMM"
            },

            1
        ));

        return questions;
    }
}