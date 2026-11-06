package ui;

import app.GameFrame;
import data.QuestionBank;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import model.Friend;
import model.FriendRoster;
import model.Question;
import util.GameFonts;
import audio.AudioLoader;

import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class GamePanel extends JPanel 
{

    private GameFrame frame;

    private Image backgroundImage;

    private ArrayList<Question> questions;

    private int currentQuestion;
    private int score;

    private int lives = 5;
    private int consecutiveCorrect = 0;

    private JLabel livesLabel;

    private JLabel questionNumberLabel;
    private JLabel scoreLabel;


    private JTextArea questionTextArea;
    private JScrollPane questionScrollPane;

    private JLabel proctorImageLabel;
    private Friend currentProctor;

    private GameButton[] answerButtons;

    private JPanel answerPanel;
    private JScrollPane answerScrollPane;

    private GameButton nextButton;

    private GameButton friendButton;

    private boolean answered;

    private int friendCallsThisRound = 0;
    private static final int MAX_FRIEND_CALLS_PER_ROUND = 3;

    private ArrayList<Friend> calledFriendsThisRound = new ArrayList<>();



    public GamePanel(GameFrame frame) 
    {
        
        this.frame = frame;

        backgroundImage = new ImageIcon("resources/images/GameplayBackground.png").getImage();

        questions = new ArrayList<>();

        createGUI();

        resetGame();

        setupDebugShortcuts();
    }


    //create GUI
    private void createGUI() 
    {

        setLayout(new BorderLayout(20, 20));

        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));


        //top
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        questionNumberLabel = new JLabel(
            "Question 1 / " + questions.size()
        );

        questionNumberLabel.setFont(GameFonts.question());

        scoreLabel = new JLabel("Score: 0");

        scoreLabel.setFont(GameFonts.heading());

        //lives display
        livesLabel = new JLabel();
        livesLabel.setFont(GameFonts.heading());
        updateLivesDisplay();

        //arrange the top panel
        topPanel.add(questionNumberLabel, BorderLayout.WEST);
        topPanel.add(livesLabel, BorderLayout.CENTER);
        topPanel.add(scoreLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);



        //Center
        questionTextArea = new JTextArea("Question");

        questionTextArea.setFont(GameFonts.question());
        questionTextArea.setForeground(GameFonts.TEXT);
        questionTextArea.setBackground(GameFonts.PANEL);

        questionTextArea.setEditable(false);
        questionTextArea.setFocusable(false);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);

        questionTextArea.setOpaque(false);
        questionTextArea.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 110, 150)
        );

        questionScrollPane = new JScrollPane(questionTextArea);

        questionScrollPane.setBorder(null);
        questionScrollPane.setOpaque(false);
        questionScrollPane.getViewport().setOpaque(false);

        questionScrollPane.setHorizontalScrollBarPolicy(
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        questionScrollPane.setVerticalScrollBarPolicy(
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        //Question box
        JPanel questionBox = new JPanel(new BorderLayout())
        {
            @Override 
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //background
                g2d.setColor(GameFonts.PANEL);

                g2d.fillRoundRect(
                    0, 0,
                    getWidth() - 1,
                    getHeight() - 1,
                    20, 20
                );

                //border
                g2d.setColor(GameFonts.NEON_GREEN);
                g2d.setStroke(new BasicStroke(3));

                g2d.drawRoundRect(
                    1, 1,
                    getWidth() - 3,
                    getHeight() - 3,
                    20, 20
                );

                g2d.dispose();
            }
        };

        questionBox.setOpaque(false);

        //space between the question text and the border
        questionBox.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        //layered pane for the question and proctor portrait
        JLayeredPane questionLayers = new JLayeredPane();

        questionLayers.setPreferredSize(new Dimension(700, 300));

        //proctor portrait
        proctorImageLabel = new JLabel();
        proctorImageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        proctorImageLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        //add components to separate layers
        questionLayers.add(questionScrollPane, Integer.valueOf(0));
        questionLayers.add(proctorImageLabel, Integer.valueOf(1));

        questionBox.add(questionLayers, BorderLayout.CENTER);


        questionLayers.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            @Override 
            public void componentResized(java.awt.event.ComponentEvent e)
            {
                int width = questionLayers.getWidth();
                int height = questionLayers.getHeight();

                questionScrollPane.setBounds(
                    20,
                    20,
                    width - 40,
                    height - 40
                );

                proctorImageLabel.setBounds(
                    width - 145,
                    height - 140,
                    125,
                    130
                );
            }
        });



        //Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);

        centerPanel.add(questionBox, BorderLayout.CENTER);


        //answers
        answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.setOpaque(false);

        answerButtons = new GameButton[4];

        for(int i = 0; i < 4; i++)
        {
            answerButtons[i] = new GameButton();
            answerButtons[i].setFont(GameFonts.button());

            // Allow the button to expand horizontally.
            answerButtons[i].setMinimumSize(new Dimension(0, 55));
            answerButtons[i].setAlignmentX(Component.LEFT_ALIGNMENT);

            final int answerIndex = i;

            answerButtons[i].addActionListener(e -> {
                checkAnswer(answerIndex);
            });

            answerPanel.add(answerButtons[i]);

            if (i < 3)
            {
                answerPanel.add(Box.createVerticalStrut(10));
            }
        }

        answerScrollPane = new JScrollPane(answerPanel);
        answerScrollPane.setBorder(null);
        answerScrollPane.setOpaque(false);
        answerScrollPane.getViewport().setOpaque(false);

        answerScrollPane.setHorizontalScrollBarPolicy(
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        answerScrollPane.setVerticalScrollBarPolicy(
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        //reserve space for the choices while keeping the question visible
        answerScrollPane.setPreferredSize(new Dimension(700, 190));

        centerPanel.add(
            answerScrollPane,
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
        bottomPanel.setOpaque(false);

        friendButton = new GameButton("CALL A FRIEND");
        friendButton.setFont(GameFonts.button());
        friendButton.setPreferredSize(new Dimension(180, 45));

        nextButton = new GameButton("NEXT QUESTION");
        nextButton.setFont(GameFonts.button());
        nextButton.setPreferredSize(new Dimension(200, 45));
        nextButton.setEnabled(false);

        GameButton menuButton = new GameButton("MENU");
        menuButton.setFont(GameFonts.button());
        menuButton.setPreferredSize(new Dimension(120, 45));

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


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );

        g2d.drawImage(
            backgroundImage,
            0,
            0,
            getWidth(),
            getHeight(),
            this
        );

        g2d.dispose();
    }


    public void showFirstProctorIntro()
    {
        showRoundIntro(
            "Hey! Welcome to DeBuGGeD!\n"
            + "Are you ready to test your programming knowledge?",
            0
        );
    }


    // ==========================
    // RESET GAME
    // ==========================

    public void resetGame() {
        AudioLoader.playMainGameBG();
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

        Collections.shuffle(module1Questions);
        Collections.shuffle(module2Questions);
        Collections.shuffle(module3Questions);
        Collections.shuffle(module4Questions);
        Collections.shuffle(module5Questions);
        Collections.shuffle(module6Questions);
        Collections.shuffle(module7Questions);

        Collections.shuffle(typeAQuestions);
        Collections.shuffle(typeBQuestions);
        Collections.shuffle(typeCQuestions);

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

        for(Question question : questions)
        {
            question.shuffleChoices();
        }

        currentQuestion = 0;

        score = 0;

        //reset the life system
        lives = 5;
        consecutiveCorrect = 0;

        updateLivesDisplay();

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

        Question question = questions.get(currentQuestion);

        
        //determine the proctor from the current section
        currentProctor = FriendRoster.getProctor(currentQuestion);

        //load and display the proctor image
        ImageIcon proctorIcon = new ImageIcon(currentProctor.getImagePath());

        if(proctorIcon.getIconWidth() > 0)
        {
            Image scaledImage = proctorIcon.getImage().getScaledInstance(
                125, 130, Image.SCALE_SMOOTH
            );

            proctorImageLabel.setIcon(new ImageIcon(scaledImage));
        }
        else
        {
            proctorImageLabel.setIcon(null);
            System.err.println("Could not load proctor image: " + currentProctor.getImagePath());
        }

        proctorImageLabel.setToolTipText("Your proctor: " + currentProctor.getName());



        questionNumberLabel.setText(
            "Question "
            + (currentQuestion + 1)
            + " / "
            + questions.size()
        );

        scoreLabel.setText(
            "Score: " + score
        );

        String formattedQuestion = question.getQuestion()
        .replace("\t", "    ");

        questionTextArea.setText(formattedQuestion);

        questionTextArea.setCaretPosition(0);



        String[] choices =
            question.getChoices();

        
        int answerWidth = answerScrollPane.getViewport().getWidth();

        if(answerWidth <= 0)
        {
            answerWidth = 700;
        }

        answerWidth = Math.max(300, answerWidth - 40);

        for(int i = 0; i < 4; i++)
        {
            String formattedChoice = choices[i]
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\t", "    ")
                .replace("\n", "<br>");

            int buttonHeight = calculateAnswerHeight(
                choices[i],
                answerWidth - 40,
                answerButtons[i].getFont()
            );

            answerButtons[i].setText(
                "<html><div style='width: " + (answerWidth - 40)
                + "px; text-align: left;'>"
                + (char) ('A' + i)
                + ". "
                + formattedChoice
                + "</div></html>"
            );

            answerButtons[i].setHorizontalAlignment(SwingConstants.LEFT);

            answerButtons[i].setPreferredSize(
                new Dimension(answerWidth, buttonHeight)
            );

            answerButtons[i].setMaximumSize(
                new Dimension(Integer.MAX_VALUE, buttonHeight)
            );

            answerButtons[i].setEnabled(true);
        }

        answerPanel.revalidate();
        answerPanel.repaint();
        answerScrollPane.revalidate();

        nextButton.setEnabled(false);

        answered = false;

        friendButton.setEnabled(true);
    }

    // ==========================
    // CHECK ANSWER
    // ==========================

    private void checkAnswer(int selectedAnswer) 
    {

        if(answered)
        {
            return;
        }

        answered = true;

        Question question = questions.get(currentQuestion);

        int correctAnswer = question.getCorrectAnswer();

        //disable answer buttons
        for(GameButton button : answerButtons)
        {
            button.setEnabled(false);
        }

        //check the players answer
        if(selectedAnswer == correctAnswer)
        {   
            AudioLoader.playCorrectAnswer();
            score++;
            consecutiveCorrect++;

            //every 5 consecutive correct answers
            if(consecutiveCorrect == 5)
            {
                if(lives < 5)
                {
                    lives++;

                    JOptionPane.showMessageDialog(
                        this,
                        "INCREDIBLE! 5 CORRECT ANSWERS IN A ROW!\n"
                        + "You restored one heart! <3"
                    );
                }
                else
                {
                    JOptionPane.showMessageDialog(
                        this,
                        "INCREDIBLE! 5 CORRECT ANSWERS IN A ROW!\n"
                        + "Your hearts are already full!"
                    );
                }

                //start a new streak
                consecutiveCorrect = 0;
            }
            else
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Correct!"
                );
            }
        }
        else
        {
            //wrong answer costs one heart
            lives--;
            AudioLoader.playWrongAnswer();
            //reset the consecutive correct answers
            consecutiveCorrect = 0;

            JOptionPane.showMessageDialog(
                this,
                "Wrong answer!\n"
                + "The correct answer was "
                + (char)('A' + correctAnswer)
                + "\n\nYou lost one heart! </3"
            );
        }

        //update the displays
        scoreLabel.setText("Score: " + score);

        updateLivesDisplay();

        //Check if the player has lost all hearts
        if(lives <= 0)
        {   
            AudioLoader.playGameOver();
            JOptionPane.showMessageDialog(
                this,
                "You have run out of hearts!\n"
                + "Better luck next time!"
            );

            showFinalScore(false);

            return;
        }

        nextButton.setEnabled(true);

        friendButton.setEnabled(false);
    }

    private void updateLivesDisplay()
    {
        StringBuilder hearts = new StringBuilder("<html>");

        for(int i = 0; i < 5; i++)
        {
            if(i < lives)
            {
                hearts.append("<font color='#FF3333' size='8'>♥</font>");
            }
            else
            {
                hearts.append("<font color='#555555' size='8'>♡</font> ");
            }

            hearts.append("&nbsp;");
        }

        hearts.append("</html>");

        livesLabel.setText(hearts.toString());
    }

    // ==========================
    // NEXT QUESTION
    // ==========================

    private void nextQuestion() {

        currentQuestion++;

        //dialog after module 1
        if(currentQuestion == 10)
        {
            resetFriendCalls();

             showRoundIntro(
                "You made it through the first round!\n" 
                + "Let's see if you can withstand PROCEDURAL PROGRAMMING!",
                10
             );
        }

        //dialog after module 2
        if(currentQuestion == 20)
        {
            resetFriendCalls();

             showRoundIntro(
                "Not bad! But we're just getting started.\n" 
                + "Let's move on to FUNCTIONAL PROGRAMMING!",
                20
             );
        }

        //dialog after module 3
        if(currentQuestion == 30)
        {
            resetFriendCalls();

             showRoundIntro(
                "You're doing pretty well!\n" 
                + "Next up: OBJECT-ORIENTED PROGRAMMING!",
                30
             );
        }

        //dialog after module 4
        if(currentQuestion == 40)
        {
            resetFriendCalls();

             showRoundIntro(
                "Ready for another challenge?\n" 
                + "Let's explore IMPERATIVE AND DECLARATIVE PROGRAMMING!",
                40
             );
        }

        //dialog after module 5
        if(currentQuestion == 50)
        {
            resetFriendCalls();

            showRoundIntro(
                "Still going strong!\n" 
                + "Next up: EVENT-DRIVEN PROGRAMMING!",
                50
             );
        }

        //dialog after module 6
        if(currentQuestion == 60)
        {
            resetFriendCalls();

            showRoundIntro(
                "Almost done with the theoretical questions!\n"
                + "Let's move on to the final module!",
                60
            );
        }

        //dialog after module 7
        if(currentQuestion == 70)
        {
            AudioLoader.playProgrammingMusic();

            resetFriendCalls();

            showRoundIntro(
                "Enough theory! It's time to put your knowledge into practice!",
                70
            );
        }

        //dialog after TYPE A
        if(currentQuestion == 95)
        {
            resetFriendCalls();

            showRoundIntro(
                "You're getting closer!\n"
                + "But there's still more programming to do!",
                95
            );
        }

        //dialog after TYPE B
        if(currentQuestion == 120)
        {
            resetFriendCalls();

            showRoundIntro(
                "This is it!\n"
                + "Welcome to the LAST ROUND!",
                120
            );
        }

        //dialog after all questions
        if(currentQuestion >= questions.size())
        {
            showFinalScore(true);

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

    private void callFriendWindow() 
    {
        if(friendCallsThisRound >= MAX_FRIEND_CALLS_PER_ROUND)
        {
            JOptionPane.showMessageDialog(
                this,
                "You have already used all 3 friend calls this round!",
                "Friend Calls Unavailable",
                JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        FriendPanel friendPanel =  new FriendPanel(this, currentProctor);

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


        if(calledFriendsThisRound.contains(friend))
        {
            JOptionPane.showMessageDialog(
                this,
                friend.getName() + " has already helped you this round!",
                "Friend Already Called",
                JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        //record this friend call
        calledFriendsThisRound.add(friend);
        friendCallsThisRound++;



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

    private void showFinalScore(boolean won) {

        double percentage = ((double) score / questions.size()) * 100;

        String message = (won ? "YOU WIN!\n\n" : "GAME OVER!\n\n")
        + "Your score: "
        + score
        + " / "
        + questions.size()
        + "\n\n"
        + String.format(
            "Percentage: %1.1f%%",
            percentage
        );

        JOptionPane.showMessageDialog(
            this, message, "Final Score",
            JOptionPane.INFORMATION_MESSAGE
        );

        frame.showEndingScreen(won);
    }

    private void showRoundIntro(String message, int nextSectionIndex)
    {
        Friend nextProctor = FriendRoster.getProctor(nextSectionIndex);

        RoundIntroDialog dialog = new RoundIntroDialog(
            frame,
            nextProctor,
            message
        );

        dialog.setVisible(true);
    }

    private void resetFriendCalls()
    {
        friendCallsThisRound = 0;
        calledFriendsThisRound.clear();
    }

    public int getRemainingFriendCalls()
    {
        return MAX_FRIEND_CALLS_PER_ROUND - friendCallsThisRound;
    }

    public boolean hasCalledFriend(Friend friend)
    {
        return calledFriendsThisRound.contains(friend);
    }

    private int calculateAnswerHeight(String text, int availableWidth, Font font)
    {
        FontMetrics metrics = getFontMetrics(font);

        int lineCount = 0;

        String[] paragraphs = text.split("\n", -1);

        for(String paragraph : paragraphs)
        {
            if(paragraph.isEmpty())
            {
                lineCount++;
                continue;
            }

            int currentLineWidth = 0;
            String[] words = paragraph.split("\\s+");

            for(String word : words)
            {
                int wordWidth = metrics.stringWidth(word);
                int spaceWidth = metrics.stringWidth(" ");

                if(currentLineWidth > 0 && currentLineWidth + spaceWidth + wordWidth > availableWidth)
                {
                    lineCount++;
                    currentLineWidth = wordWidth;
                }
                else
                {
                    if(currentLineWidth > 0)
                    {
                        currentLineWidth += spaceWidth;
                    }

                    currentLineWidth += wordWidth;
                }
            }

            lineCount++;
        }

        return Math.max(55, lineCount * metrics.getHeight() + 28);
    }

    private void setupDebugShortcuts()
    {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0),
            "skipStage"
        );

        getActionMap().put("skipStage", new AbstractAction()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                skipCurrentStage();
            }
        });
    }

    private void skipCurrentStage()
    {
        if(questions.isEmpty() || currentQuestion >= questions.size())
        {
            return;
        }

        int[] stageBoundaries = {
            10, 20, 30, 40, 50, 60, 70, 95, 120, questions.size()
        };

        for(int boundary : stageBoundaries)
        {
            if(boundary > currentQuestion)
            {
                currentQuestion = boundary - 1;

                nextQuestion();

                return;
            }
        }
    }
}