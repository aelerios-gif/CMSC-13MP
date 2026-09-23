import java.util.ArrayList;

public class QuestionBank {

    public static ArrayList<Question> getQuestions() {

        ArrayList<Question> questions =
            new ArrayList<>();

        // QUESTION 1

        questions.add(new Question(
            "What is 2 + 2?",

            new String[] {
                "3",
                "4",
                "5",
                "6"
            },

            1
        ));

        // QUESTION 2

        questions.add(new Question(
            "What planet do we live on?",

            new String[] {
                "Mars",
                "Venus",
                "Earth",
                "Jupiter"
            },

            2
        ));

        // QUESTION 3

        questions.add(new Question(
            "What is the capital of the Philippines?",

            new String[] {
                "Cebu",
                "Manila",
                "Davao",
                "Baguio"
            },

            1
        ));

        // ==================================================
        // COPY THIS FORMAT FOR QUESTIONS 4 - 70
        // ==================================================

        /*
        questions.add(new Question(
            "YOUR QUESTION",

            new String[] {
                "ANSWER A",
                "ANSWER B",
                "ANSWER C",
                "ANSWER D"
            },

            CORRECT_ANSWER
        ));
        */

        return questions;
    }
}