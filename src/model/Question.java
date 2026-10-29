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

    public void shuffleChoices()
    {
        for(int i = choices.length - 1; i > 0; i--)
        {
            int j = (int) (Math.random() * (i + 1));

            //Remember the choice currently at index i
            String temporary = choices[i];

            //Swap the two choices
            choices[i] = choices[j];
            choices[j] = temporary;

            //keep the correct-answer index synchronized
            if(correctAnswer == i)
            {
                correctAnswer = j;
            }
            else if(correctAnswer == j)
            {
                correctAnswer = i;
            }
        }
    }
}
