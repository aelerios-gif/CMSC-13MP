package ui;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import app.GameFrame;
import data.QuestionBank;
import model.Friend;
import model.Question;

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

        //questions = QuestionBank.getQuestions();
        questions = new ArrayList<>();

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

        questionNumberLabel = new JLabel(
            "Question 1 / " + questions.size()
        );

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

            answerButtons[i] = new JButton();

            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));

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

        JPanel bottomPanel = new JPanel(new FlowLayout());

        friendButton = new JButton("CALL A FRIEND");
        friendButton.setFont(new Font("Arial", Font.BOLD, 16));

        nextButton = new JButton("NEXT QUESTION");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setEnabled(false);

        JButton menuButton = new JButton("MENU");
        menuButton.setFont(new Font("Arial", Font.BOLD, 16));

        bottomPanel.add(friendButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(menuButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // ==========================
        // BUTTON ACTIONS
        // ==========================

        friendButton.addActionListener(e -> {

            callFriendWindow();

        });

        nextButton.addActionListener(e -> {

            nextQuestion();

        });

        menuButton.addActionListener(e -> {
            showGameMenu();
        });
    }

    // ==========================
    // RESET GAME
    // ==========================

    public void resetGame() {

        questions = new ArrayList<>();

        ArrayList<Question> module1Questions = QuestionBank.getModule1Questions();

        ArrayList<Question> module2Questions = QuestionBank.getModule2Questions();

        ArrayList<Question> module3Questions = QuestionBank.getModule3Questions();

        ArrayList<Question> module4Questions = QuestionBank.getModule4Questions();

        ArrayList<Question> module5Questions = QuestionBank.getModule5Questions();

        ArrayList<Question> module6Questions = QuestionBank.getModule6Questions();

        ArrayList<Question> module7Questions = QuestionBank.getModule7Questions();

        ArrayList<Question> typeAQuestions = QuestionBank.getTypeAQuestions();

        ArrayList<Question> typeBQuestions = QuestionBank.getTypeBQuestions();

        ArrayList<Question> typeCQuestions = QuestionBank.getTypeCQuestions();

        questions.addAll(module1Questions);
        questions.addAll(module2Questions);
        questions.addAll(module3Questions);
        questions.addAll(module4Questions);
        questions.addAll(module5Questions);
        questions.addAll(module6Questions);
        questions.addAll(module7Questions);

        questions.addAll(typeAQuestions);
        questions.addAll(typeBQuestions);
        questions.addAll(typeCQuestions);

        /* questions = QuestionBank.getQuestions();

        Collections.shuffle(questions); */

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

        //dialog after module 1
        if(currentQuestion == 10)
        {
            JOptionPane.showMessageDialog(
                this, 
                "Hmmm just because you finished 10 questions doesn't mean you are really good.\n\n" 
            + "Let's see if you can withstand PROCEDURAL PROGRAMMING!",
             "Round 2", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after module 2
        if(currentQuestion == 20)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "Let's see if you can withstand FUNCTIONAL PROGRAMMING!",
             "Round 3", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after module 3
        if(currentQuestion == 30)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "Let's see if you can withstand OBJECT ORIENTED PROGRAMMING!",
             "Round 4", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after module 4
        if(currentQuestion == 40)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "Now let's talk about IMPERATIVE VS DECLARATIVE PROGRAMMING!",
             "Round 5", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after module 5
        if(currentQuestion == 50)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "Let's see if you can withstand EVENT-DRIVEN PROGRAMMING!",
             "Round 6", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after module 6
        if(currentQuestion == 60)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "Let's see if you can withstand EVENT-DRIVEN PROGRAMMING!",
             "Round 7", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after module 7
        if(currentQuestion == 70)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "Let's see if you can withstand PROGRAMMING QUESTIONS!",
             "Programming A", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after TYPE A
        if(currentQuestion == 95)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "MORE PROGRAMMING QUESTIONS!!",
             "Programming B", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after TYPE B
        if(currentQuestion == 120)
        {
            JOptionPane.showMessageDialog(
                this, 
                "I mean, you're not bad.\n\n" 
            + "LAST ROUND!",
             "Programming C", 
             JOptionPane.INFORMATION_MESSAGE);
        }

        //dialog after all questions
        if(currentQuestion >= questions.size())
        {
            showFinalScore();

            return;
        }


        showQuestion();
    }

    //---------
    //GAME MENU
    //------------

    private void showGameMenu()
    {
        String[] options = {
            "Resume Game",
            "Main Menu",
            "Settings",
            "Exit Program"
        };

        int choice = JOptionPane.showOptionDialog(
            this,
            "What would you like to do?",
            "Gane Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        //Resume the current game
        if(choice == 0)
        {
            return;
        }

        //Return to the main menu
        else if(choice == 1)
        {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to return to the main menu? "
                + "Your current progress may be lost.",
                "Confirm Main Menu",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if(confirm == JOptionPane.YES_OPTION)
            {
                frame.returnToHome();
            }
        }

        //Open settings
        else if(choice == 2)
        {
            SettingsDialog settings = new SettingsDialog(frame);
            settings.setVisible(true);
        }

        //Exit the program
        else if(choice == 3)
        {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the program?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if(confirm == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
    }


    // ==========================
    // CALL FRIEND WINDOW
    // ==========================

    private void callFriendWindow() {

        FriendPanel friendPanel =  new FriendPanel(this);

        friendPanel.setVisible(true);
    }

    // ==========================
    // CALL FRIEND
    // ==========================

    public void callFriend(Friend friend)
    {
        Question question = questions.get(currentQuestion);

        int correctAnswer = question.getCorrectAnswer();

        String[] choices = question.getChoices();

        //Check whether the friend specializes int his questions category
        boolean isExpert = friend.isExpertIn(question.getCategory());

        //Experts may 70% na magka correct ans
        //Nonexperts may 30% chance maka correct ans
        int chanceOfCorrectAnswer;

        if(isExpert)
        {
            chanceOfCorrectAnswer = 70;
        }
        else
        {
            chanceOfCorrectAnswer = 30;
        }

        int chance = (int) (Math.random() * 100);

        String answer;

        if(chance < chanceOfCorrectAnswer)
        {
            //The friend gives the correct answer
            answer = (char) ('A' + correctAnswer) + ". " + choices[correctAnswer];
        }
        else
        {
            //The friend gives a randomly selected wrong answer
            int wrongAnswer;

            do
            {
                wrongAnswer = (int) (Math.random() * 4);
            }
            while(wrongAnswer == correctAnswer);

            answer = (char) ('A' + wrongAnswer) + ", " + choices[wrongAnswer];
        }

        JOptionPane.showMessageDialog(
            this, 
            friend.getName()
            + " says:\n\n"
            + "\"I think the answer is "
            + answer
            + "!\"\n\n"
            + "Specialty: "
            + java.util.Arrays.toString(friend.getSpecialties())
            + "\nChance of being correct: "
            + chanceOfCorrectAnswer
            + "%",
            "Call with " 
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