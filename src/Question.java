public class Question {

    private String question;
    private String[] choices;
    private int correctAnswer;

    public Question(
        String question,
        String[] choices,
        int correctAnswer
    ) {

        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {

        return question;
    }

    public String[] getChoices() {

        return choices;
    }

    public int getCorrectAnswer() {

        return correctAnswer;
    }
}