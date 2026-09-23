import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class GamePanel extends JPanel 
{

    private GameFrame frame;

    private ArrayList<Question> questions;

    private int currentQuestion;
    private int score;

    private JLabel questionNumberLabel;
    private JLabel scoreLabel;

    private JLabel questionLabel;

    private JButton[] answerButtons;

    private JButton nextButton;

    private JButton friendButton;

    private boolean answered;



    public GamePanel(GameFrame frame) 
    {

        this.frame = frame;

        questions = QuestionBank.getQuestions();

        createGUI();

        resetGame();
    }


    //create GUI
    private void createGUI() 
    {

        setLayout(new BorderLayout(20, 20));

        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));


        //top
        JPanel topPanel = new JPanel(new BorderLayout());

        questionNumberLabel = new JLabel("Question 1 / 70");

        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 18));

        scoreLabel = new JLabel("Score: 0");

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));

        topPanel.add(questionNumberLabel, BorderLayout.WEST);

        topPanel.add(scoreLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);



        //Center
        questionLabel = new JLabel("Question");

        questionLabel.setFont(new Font("Arial", Font.BOLD, 28));

        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));

        centerPanel.add(questionLabel, BorderLayout.CENTER);


        //answers
        JPanel answerPanel = new JPanel(new GridLayout(2, 2, 15, 15));

        answerButtons = new JButton[4];

        for (int i = 0; i < 4; i++) 
        {

            answerButtons[i] =
                new JButton();

            answerButtons[i].setFont(
                new Font("Arial", Font.PLAIN, 18)
            );

            final int answerIndex = i;

            answerButtons[i].addActionListener(e -> {

                checkAnswer(answerIndex);

            });

            answerPanel.add(
                answerButtons[i]
            );
        }

        centerPanel.add(
            answerPanel,
            BorderLayout.SOUTH
        );

        add(
            centerPanel,
            BorderLayout.CENTER
        );

        // ==========================
        // BOTTOM BUTTONS
        // ==========================

        JPanel bottomPanel =
            new JPanel(new FlowLayout());

        friendButton =
            new JButton("📞 CALL A FRIEND");

        friendButton.setFont(
            new Font("Arial", Font.BOLD, 16)
        );

        nextButton =
            new JButton("NEXT QUESTION");

        nextButton.setFont(
            new Font("Arial", Font.BOLD, 16)
        );

        nextButton.setEnabled(false);

        bottomPanel.add(friendButton);
        bottomPanel.add(nextButton);

        add(
            bottomPanel,
            BorderLayout.SOUTH
        );

        // ==========================
        // BUTTON ACTIONS
        // ==========================

        friendButton.addActionListener(e -> {

            callFriendWindow();

        });

        nextButton.addActionListener(e -> {

            nextQuestion();

        });
    }

    // ==========================
    // RESET GAME
    // ==========================

    public void resetGame() {

        questions = QuestionBank.getQuestions();

        Collections.shuffle(questions);

        currentQuestion = 0;

        score = 0;

        answered = false;

        showQuestion();
    }

    // ==========================
    // SHOW QUESTION
    // ==========================

    private void showQuestion() {

        if (questions.size() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "You don't have any questions!"
            );

            return;
        }

        Question question =
            questions.get(currentQuestion);

        questionNumberLabel.setText(
            "Question "
            + (currentQuestion + 1)
            + " / "
            + questions.size()
        );

        scoreLabel.setText(
            "Score: " + score
        );

        questionLabel.setText(
            "<html><center>"
            + question.getQuestion()
            + "</center></html>"
        );

        String[] choices =
            question.getChoices();

        for (int i = 0; i < 4; i++) {

            answerButtons[i].setText(
                (char)('A' + i)
                + ". "
                + choices[i]
            );

            answerButtons[i].setEnabled(true);
        }

        nextButton.setEnabled(false);

        answered = false;

        friendButton.setEnabled(true);
    }

    // ==========================
    // CHECK ANSWER
    // ==========================

    private void checkAnswer(
        int selectedAnswer
    ) {

        if (answered) {
            return;
        }

        answered = true;

        Question question =
            questions.get(currentQuestion);

        int correctAnswer =
            question.getCorrectAnswer();

        // Disable answers

        for (JButton button : answerButtons) {

            button.setEnabled(false);
        }

        if (selectedAnswer == correctAnswer) {

            score++;

            JOptionPane.showMessageDialog(
                this,
                "Correct! 🎉"
            );

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Wrong answer!\n"
                + "The correct answer was "
                + (char)('A' + correctAnswer)
            );
        }

        scoreLabel.setText(
            "Score: " + score
        );

        nextButton.setEnabled(true);

        friendButton.setEnabled(false);
    }

    // ==========================
    // NEXT QUESTION
    // ==========================

    private void nextQuestion() {

        currentQuestion++;

        if (currentQuestion >= questions.size()) {

            showFinalScore();

            return;
        }

        showQuestion();
    }

    // ==========================
    // CALL FRIEND WINDOW
    // ==========================

    private void callFriendWindow() {

        FriendPanel friendPanel =
            new FriendPanel(
                frame,
                this
            );

        friendPanel.setVisible(true);
    }

    // ==========================
    // CALL FRIEND
    // ==========================

    public void callFriend(Friend friend) {

        Question question =
            questions.get(currentQuestion);

        int correctAnswer =
            question.getCorrectAnswer();

        String[] choices =
            question.getChoices();

        /*
         * For now, the friend gives the
         * correct answer based on their
         * reliability.
         */

        int chance =
            (int)(Math.random() * 100);

        String answer;

        if (chance < friend.getReliability()) {

            answer =
                (char)('A' + correctAnswer)
                + ". "
                + choices[correctAnswer];

        } else {

            int wrongAnswer;

            do {

                wrongAnswer =
                    (int)(Math.random() * 4);

            } while (
                wrongAnswer == correctAnswer
            );

            answer =
                (char)('A' + wrongAnswer)
                + ". "
                + choices[wrongAnswer];
        }

        JOptionPane.showMessageDialog(
            this,

            friend.getName()
            + " says:\n\n"
            + "\"I think the answer is "
            + answer
            + "!\"\n\n"
            + "Specialty: "
            + friend.getSpecialty(),

            "📞 Call with "
            + friend.getName(),

            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==========================
    // FINAL SCORE
    // ==========================

    private void showFinalScore() {

        double percentage =
            ((double) score / questions.size())
            * 100;

        String message =
            "GAME OVER!\n\n"
            + "Your Score: "
            + score
            + " / "
            + questions.size()
            + "\n\n"
            + String.format(
                "Percentage: %.1f%%",
                percentage
            );

        JOptionPane.showMessageDialog(
            this,
            message,
            "Final Score",
            JOptionPane.INFORMATION_MESSAGE
        );

        frame.returnToHome();
    }
}