package model;

public class Question {

    private String question;
    private String[] choices;
    private int correctAnswer;
    private QuestionCategory category;

    public Question(
            String question,
            String[] choices,
            int correctAnswer,
            QuestionCategory category
    ) {

        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.category = category;
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

    public QuestionCategory getCategory() {
        return category;
    }
}
