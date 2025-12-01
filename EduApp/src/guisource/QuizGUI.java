/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guisource;
import guisource.QuizData;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
/**
 *
 * @author ikram
 */
public class QuizGUI extends javax.swing.JFrame {

    // ===== Core quiz fields (Maths / Biology / Physics) =====
    private Timer timer;                         // Timer for core quiz tab
    private int remainingSeconds = 30;           // Time left for core quiz (seconds)
    private QuizData.Question[] currentQuestions;// Array of questions for selected core subject
    private int currentQuestionIndex = 0;        // Index of current question in core quiz
    private int score = 0;                       // Player score for core quiz
    private int totalQuestions = 0;              // Number of questions in current core quiz

    // ===== Side quiz fields (CompSci / Chemistry / Mathematics) =====
    private Timer sideTimer;                     // Timer for side quiz tab
    private int sideRemainingSeconds = 30;       // Time left for side quiz (seconds)
    private QuizData.Question[] sideQuestions;   // Array of questions for selected side subject
    private int sideCurrentQuestionIndex = 0;    // Index of current question in side quiz
    private int sideScore = 0;                   // Player score for side quiz
    private int sideTotalQuestions = 0;          // Number of questions in current side quiz
    
    /**
     * Creates new form QuizGUI2
     */
    public QuizGUI() {
        initComponents();
        initTimer();         // Set up core quiz timer (countdown)
        initSideTimer();     // Set up side quiz timer (countdown)

        // Timer text areas: display-only, start at 30 seconds
        TimerTA.setEditable(false);
        TimerTA.setText("0:30");

        TimerTA1.setEditable(false);
        TimerTA1.setText("0:30");

        // Attach listeners for subject radio buttons and tab changes
        setupSubjectListeners();        // Core subjects (Maths/Bio/Physics)
        setupSideSubjectListeners();    // Side subjects (CS/Chem/Maths)

        // Question display areas should not be editable by user
        DisplayTA.setEditable(false);
        DisplayTA1.setEditable(false);
    }
    
    // ===== Core timer (for main/core quiz tab) =====
    private void initTimer() {
        // Timer fires every 1000 ms (1 second)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds--;  // count down

                if (remainingSeconds >= 0) {
                    int minutes = remainingSeconds / 60;
                    int seconds = remainingSeconds % 60;

                    // Show remaining time in m:ss format, e.g. 0:25
                    TimerTA.setText(String.format("%d:%02d", minutes, seconds));
                }

                // When core quiz time runs out
                if (remainingSeconds <= 0) {
                    timer.stop();

                    String result = "Time's up!\n\n";
                    result = result + "Your score: " + score + " out of " + totalQuestions;

                    // Show final result and clear selected answers
                    DisplayTA.setText(result);
                    AnswersBG.clearSelection();
                }
            }
        });
    }

    // ===== Side timer (for side subjects tab) =====
    private void initSideTimer() {
        // Timer fires every 1000 ms (1 second)
        sideTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sideRemainingSeconds--;  // count down

                if (sideRemainingSeconds >= 0) {
                    int minutes = sideRemainingSeconds / 60;
                    int seconds = sideRemainingSeconds % 60;

                    // Show remaining time in m:ss format for side quiz
                    TimerTA1.setText(String.format("%d:%02d", minutes, seconds));
                }

                // When side quiz time runs out
                if (sideRemainingSeconds <= 0) {
                    sideTimer.stop();

                    String result = "Time's up!\n\n";
                    result = result + "Your score: " + sideScore + " out of " + sideTotalQuestions;

                    // Show final result and clear selected answers
                    DisplayTA1.setText(result);
                    AnswersBG.clearSelection();
                }
            }
        });
    }

    // ===== Attach listeners to core subject radio buttons =====
    private void setupSubjectListeners() {
        // When each subject is selected, load its questions and start timer
        MathsRB.addActionListener(e -> loadSubject("Maths"));
        BiologyRB.addActionListener(e -> loadSubject("Biology"));
        PhysicsRB.addActionListener(e -> loadSubject("Physics"));
    }

    // ===== Attach listeners to side subject radio buttons =====
    private void setupSideSubjectListeners() {
        CompSciRB.addActionListener(e -> loadSideSubject("CS"));
        ChemistryRB.addActionListener(e -> loadSideSubject("Chemistry"));
        MathematicsRB.addActionListener(e -> loadSideSubject("Maths"));
    }

    // ===== Load core subject questions and start core quiz =====
    private void loadSubject(String subject) {
        // Pick the correct question set based on which subject was selected
        if (subject.equals("Maths")) {
            currentQuestions = QuizData.getMathsQuestions();
        } else if (subject.equals("Biology")) {
            currentQuestions = QuizData.getBiologyQuestions();
        } else if (subject.equals("Physics")) {
            currentQuestions = QuizData.getPhysicsQuestions();
        }

        // Reset progress for this quiz
        currentQuestionIndex = 0;
        score = 0;

        if (currentQuestions != null) {
            totalQuestions = currentQuestions.length;
        } else {
            totalQuestions = 0;
        }

        // Show the first question
        showCurrentQuestion();

        // Reset and start core timer
        if (timer != null) {
            remainingSeconds = 30;
            TimerTA.setText("0:30");
            timer.start();
        }
    }

    // ===== Load side subject questions and start side quiz =====
    private void loadSideSubject(String subject) {
        // Pick the correct question set for the side quiz based on subject
        if (subject.equals("CS")) {
            sideQuestions = QuizData.getComputerScienceQuestions();
        } else if (subject.equals("Chemistry")) {
            sideQuestions = QuizData.getChemistryQuestions();
        } else if (subject.equals("Maths")) {
            sideQuestions = QuizData.getSideMathsQuestions();
        }

        // Reset progress for side quiz
        sideCurrentQuestionIndex = 0;
        sideScore = 0;

        if (sideQuestions != null) {
            sideTotalQuestions = sideQuestions.length;
        } else {
            sideTotalQuestions = 0;
        }

        // Reset and start side timer
        sideRemainingSeconds = 30;
        TimerTA1.setText("0:30");

        if (sideTimer != null) {
            sideTimer.start();
        }

        // Show the first side question
        showCurrentSideQuestion();
    }

    // ===== Show current core question or final result =====
    private void showCurrentQuestion() {
        // No questions loaded (e.g. subject not chosen)
        if (currentQuestions == null || totalQuestions == 0) {
            DisplayTA.setText("No questions for this subject.");
            return;
        }

        // If there are still questions left, show the current one
        if (currentQuestionIndex < totalQuestions) {
            QuizData.Question q = currentQuestions[currentQuestionIndex];

            String text = "Question " + (currentQuestionIndex + 1) + " of " + totalQuestions + ":\n\n";
            text = text + q.getQuestion() + "\n\n";
            text = text + "A) " + q.getOptionA() + "\n";
            text = text + "B) " + q.getOptionB() + "\n";
            text = text + "C) " + q.getOptionC() + "\n";

            // Show question and options in core display area
            DisplayTA.setText(text);

            // Clear any previously selected answer
            AnswersBG.clearSelection();

        } else {
            // All questions answered: end core quiz
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

            String result = "Quiz finished!\n\n";
            result = result + "Your score: " + score + " out of " + totalQuestions;

            DisplayTA.setText(result);
            AnswersBG.clearSelection();
        }
    }

    // ===== Show current side question or final result =====
    private void showCurrentSideQuestion() {
        // No questions loaded for side quiz
        if (sideQuestions == null || sideTotalQuestions == 0) {
            DisplayTA1.setText("No questions for this subject.");
            return;
        }

        // If there are still side questions left, show the current one
        if (sideCurrentQuestionIndex < sideTotalQuestions) {
            QuizData.Question q = sideQuestions[sideCurrentQuestionIndex];

            String text = "Question " + (sideCurrentQuestionIndex + 1) + " of " + sideTotalQuestions + ":\n\n";
            text = text + q.getQuestion() + "\n\n";
            text = text + "A) " + q.getOptionA() + "\n";
            text = text + "B) " + q.getOptionB() + "\n";
            text = text + "C) " + q.getOptionC() + "\n";

            // Show question and options in side display area
            DisplayTA1.setText(text);

            // Clear selected answer (same button group used)
            AnswersBG.clearSelection();

        } else {
            // All side questions answered: end side quiz
            if (sideTimer != null && sideTimer.isRunning()) {
                sideTimer.stop();
            }

            String result = "Side quiz finished!\n\n";
            result = result + "Your score: " + sideScore + " out of " + sideTotalQuestions;

            DisplayTA1.setText(result);
            AnswersBG.clearSelection();
        }
    }

    // ===== Reset core quiz when tab changes or exiting =====
    private void resetCoreQuiz() {
        // Stop core timer if running
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // Reset time display and counters
        remainingSeconds = 30;
        TimerTA.setText("0:30");

        currentQuestions = null;
        currentQuestionIndex = 0;
        score = 0;
        totalQuestions = 0;

        // Clear questions and selections
        DisplayTA.setText("");

        if (SubjectsBG != null) {
            SubjectsBG.clearSelection();    // Clear core subject radio buttons
        }

        if (AnswersBG != null) {
            AnswersBG.clearSelection(); // Clear A/B/C answers
        }
    }

    // ===== Reset side quiz when tab changes or exiting =====
    private void resetSideQuiz() {
        // Stop side timer if running
        if (sideTimer != null && sideTimer.isRunning()) {
            sideTimer.stop();
        }

        // Reset time display and side quiz counters
        sideRemainingSeconds = 30;
        TimerTA1.setText("0:30");

        sideQuestions = null;
        sideCurrentQuestionIndex = 0;
        sideScore = 0;
        sideTotalQuestions = 0;

        // Clear questions and selections
        DisplayTA1.setText("");

        if (SubjectsBG != null) {
            SubjectsBG.clearSelection();    // Clear side subject radio buttons (if grouped here)
        }

        if (AnswersBG != null) {
            AnswersBG.clearSelection(); // Clear A/B/C answers
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AnswersBG = new javax.swing.ButtonGroup();
        SubjectsBG = new javax.swing.ButtonGroup();
        NavPNL = new javax.swing.JPanel();
        CoreNavPNL = new javax.swing.JPanel();
        CoreNavIcon = new javax.swing.JLabel();
        CoreNavLBL = new javax.swing.JLabel();
        SideNavPNL = new javax.swing.JPanel();
        SideNavIcon = new javax.swing.JLabel();
        SideNavLBL = new javax.swing.JLabel();
        EditNavPNL = new javax.swing.JPanel();
        EditNavIcon = new javax.swing.JLabel();
        EditNavLBL = new javax.swing.JLabel();
        MainMenuBTN = new javax.swing.JPanel();
        MainMenuIcon = new javax.swing.JLabel();
        MainMenuLBL = new javax.swing.JLabel();
        LinePNL = new javax.swing.JPanel();
        CardPNL = new javax.swing.JPanel();
        CorePNL = new javax.swing.JPanel();
        TitlePNL = new javax.swing.JPanel();
        LogoLBL = new javax.swing.JLabel();
        TitleLBL = new javax.swing.JLabel();
        QuizSubjectsPNL = new javax.swing.JPanel();
        MathsRB = new javax.swing.JRadioButton();
        BiologyRB = new javax.swing.JRadioButton();
        PhysicsRB = new javax.swing.JRadioButton();
        TimerLBL = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TimerTA = new javax.swing.JTextArea();
        SelectAnsLBL = new javax.swing.JLabel();
        AnswerARB = new javax.swing.JRadioButton();
        AnswerBRB = new javax.swing.JRadioButton();
        AnswerCRB = new javax.swing.JRadioButton();
        AnswerBTN = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        DisplayTA = new javax.swing.JTextArea();
        SidePNL = new javax.swing.JPanel();
        TitlePNL1 = new javax.swing.JPanel();
        LogoLBL1 = new javax.swing.JLabel();
        TitleLBL1 = new javax.swing.JLabel();
        QuizSubjectsPNL1 = new javax.swing.JPanel();
        MathematicsRB = new javax.swing.JRadioButton();
        ChemistryRB = new javax.swing.JRadioButton();
        CompSciRB = new javax.swing.JRadioButton();
        TimerLBL1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TimerTA1 = new javax.swing.JTextArea();
        SelectAnsLBL1 = new javax.swing.JLabel();
        AnswerARB1 = new javax.swing.JRadioButton();
        AnswerBRB1 = new javax.swing.JRadioButton();
        AnswerCRB1 = new javax.swing.JRadioButton();
        AnswerBTN1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        DisplayTA1 = new javax.swing.JTextArea();
        EditPNL = new javax.swing.JPanel();
        QuestionIDLBL = new javax.swing.JLabel();
        QuestionIDTF = new javax.swing.JTextField();
        SubjectLBL = new javax.swing.JLabel();
        SubjectCB = new javax.swing.JComboBox<>();
        QuestionLBL = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        QuestionTA3 = new javax.swing.JTextArea();
        OptionALBL = new javax.swing.JLabel();
        OptionATF = new javax.swing.JTextField();
        OptionBLBL = new javax.swing.JLabel();
        OptionBTF = new javax.swing.JTextField();
        OptionCLBL = new javax.swing.JLabel();
        OptionCTF = new javax.swing.JTextField();
        CorrectLBL = new javax.swing.JLabel();
        CorrectCB = new javax.swing.JComboBox<>();
        AddQuestionBTN = new javax.swing.JButton();
        SearchQuestionBTN = new javax.swing.JButton();
        DeleteQuestionBTN = new javax.swing.JButton();
        ClearBTN = new javax.swing.JButton();
        ManageLBL = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ManageDisplayTA = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        NavPNL.setBackground(new java.awt.Color(0, 0, 0));

        CoreNavPNL.setBackground(new java.awt.Color(153, 153, 255));
        CoreNavPNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CoreNavPNL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CoreNavPNLMouseClicked(evt);
            }
        });

        CoreNavIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/core-icon.png"))); // NOI18N

        CoreNavLBL.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CoreNavLBL.setForeground(new java.awt.Color(255, 255, 255));
        CoreNavLBL.setText("Core");

        javax.swing.GroupLayout CoreNavPNLLayout = new javax.swing.GroupLayout(CoreNavPNL);
        CoreNavPNL.setLayout(CoreNavPNLLayout);
        CoreNavPNLLayout.setHorizontalGroup(
            CoreNavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoreNavPNLLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(CoreNavLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(CoreNavPNLLayout.createSequentialGroup()
                .addComponent(CoreNavIcon)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        CoreNavPNLLayout.setVerticalGroup(
            CoreNavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoreNavPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CoreNavIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CoreNavLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SideNavPNL.setBackground(new java.awt.Color(153, 153, 255));
        SideNavPNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SideNavPNL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SideNavPNLMouseClicked(evt);
            }
        });

        SideNavIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/side-icon.png"))); // NOI18N

        SideNavLBL.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SideNavLBL.setForeground(new java.awt.Color(255, 255, 255));
        SideNavLBL.setText("Side");

        javax.swing.GroupLayout SideNavPNLLayout = new javax.swing.GroupLayout(SideNavPNL);
        SideNavPNL.setLayout(SideNavPNLLayout);
        SideNavPNLLayout.setHorizontalGroup(
            SideNavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SideNavPNLLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(SideNavLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(SideNavPNLLayout.createSequentialGroup()
                .addComponent(SideNavIcon)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        SideNavPNLLayout.setVerticalGroup(
            SideNavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SideNavPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SideNavIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SideNavLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        EditNavPNL.setBackground(new java.awt.Color(153, 153, 255));
        EditNavPNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        EditNavPNL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditNavPNLMouseClicked(evt);
            }
        });

        EditNavIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit-icon.png"))); // NOI18N

        EditNavLBL.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        EditNavLBL.setForeground(new java.awt.Color(255, 255, 255));
        EditNavLBL.setText("Edit");

        javax.swing.GroupLayout EditNavPNLLayout = new javax.swing.GroupLayout(EditNavPNL);
        EditNavPNL.setLayout(EditNavPNLLayout);
        EditNavPNLLayout.setHorizontalGroup(
            EditNavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditNavPNLLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(EditNavLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(EditNavPNLLayout.createSequentialGroup()
                .addComponent(EditNavIcon)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        EditNavPNLLayout.setVerticalGroup(
            EditNavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditNavPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EditNavIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditNavLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainMenuBTN.setBackground(new java.awt.Color(153, 153, 255));
        MainMenuBTN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MainMenuBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainMenuBTNMouseClicked(evt);
            }
        });

        MainMenuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/main-menu-icon.png"))); // NOI18N

        MainMenuLBL.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainMenuLBL.setForeground(new java.awt.Color(255, 255, 255));
        MainMenuLBL.setText("Edit");

        javax.swing.GroupLayout MainMenuBTNLayout = new javax.swing.GroupLayout(MainMenuBTN);
        MainMenuBTN.setLayout(MainMenuBTNLayout);
        MainMenuBTNLayout.setHorizontalGroup(
            MainMenuBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuBTNLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(MainMenuLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(MainMenuBTNLayout.createSequentialGroup()
                .addComponent(MainMenuIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        MainMenuBTNLayout.setVerticalGroup(
            MainMenuBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainMenuBTNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainMenuIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainMenuLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout NavPNLLayout = new javax.swing.GroupLayout(NavPNL);
        NavPNL.setLayout(NavPNLLayout);
        NavPNLLayout.setHorizontalGroup(
            NavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainMenuBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(EditNavPNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(SideNavPNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(CoreNavPNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        NavPNLLayout.setVerticalGroup(
            NavPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavPNLLayout.createSequentialGroup()
                .addComponent(CoreNavPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SideNavPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(EditNavPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(MainMenuBTN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 107, Short.MAX_VALUE))
        );

        getContentPane().add(NavPNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 80, 550));

        LinePNL.setBackground(new java.awt.Color(153, 204, 255));
        LinePNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout LinePNLLayout = new javax.swing.GroupLayout(LinePNL);
        LinePNL.setLayout(LinePNLLayout);
        LinePNLLayout.setHorizontalGroup(
            LinePNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        LinePNLLayout.setVerticalGroup(
            LinePNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );

        getContentPane().add(LinePNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 10, 550));

        CardPNL.setBackground(new java.awt.Color(0, 102, 102));
        CardPNL.setLayout(new java.awt.CardLayout());

        CorePNL.setBackground(new java.awt.Color(0, 102, 102));
        CorePNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.cyan, java.awt.Color.white, java.awt.Color.black, java.awt.Color.lightGray));
        CorePNL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TitlePNL.setBackground(new java.awt.Color(0, 102, 102));

        LogoLBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quiz logo.png"))); // NOI18N

        TitleLBL.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TitleLBL.setForeground(new java.awt.Color(255, 255, 255));
        TitleLBL.setText("Quiz");

        javax.swing.GroupLayout TitlePNLLayout = new javax.swing.GroupLayout(TitlePNL);
        TitlePNL.setLayout(TitlePNLLayout);
        TitlePNLLayout.setHorizontalGroup(
            TitlePNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePNLLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(TitleLBL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LogoLBL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TitlePNLLayout.setVerticalGroup(
            TitlePNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TitlePNLLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(TitlePNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LogoLBL)
                    .addComponent(TitleLBL))
                .addGap(20, 20, 20))
        );

        CorePNL.add(TitlePNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 170, 70));

        QuizSubjectsPNL.setBackground(new java.awt.Color(0, 0, 0));
        QuizSubjectsPNL.setForeground(new java.awt.Color(255, 255, 255));

        MathsRB.setBackground(new java.awt.Color(0, 0, 0));
        MathsRB.setForeground(new java.awt.Color(255, 255, 255));
        MathsRB.setText("Maths");

        BiologyRB.setBackground(new java.awt.Color(0, 0, 0));
        BiologyRB.setForeground(new java.awt.Color(255, 255, 255));
        BiologyRB.setText("Biology");

        PhysicsRB.setBackground(new java.awt.Color(0, 0, 0));
        PhysicsRB.setForeground(new java.awt.Color(255, 255, 255));
        PhysicsRB.setText("Physics");

        javax.swing.GroupLayout QuizSubjectsPNLLayout = new javax.swing.GroupLayout(QuizSubjectsPNL);
        QuizSubjectsPNL.setLayout(QuizSubjectsPNLLayout);
        QuizSubjectsPNLLayout.setHorizontalGroup(
            QuizSubjectsPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuizSubjectsPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(QuizSubjectsPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PhysicsRB)
                    .addComponent(MathsRB)
                    .addComponent(BiologyRB))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        QuizSubjectsPNLLayout.setVerticalGroup(
            QuizSubjectsPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuizSubjectsPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MathsRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BiologyRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PhysicsRB)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        CorePNL.add(QuizSubjectsPNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 90));

        TimerLBL.setForeground(new java.awt.Color(255, 255, 255));
        TimerLBL.setText("Timer:");
        CorePNL.add(TimerLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        TimerTA.setBackground(new java.awt.Color(0, 0, 0));
        TimerTA.setColumns(20);
        TimerTA.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TimerTA.setForeground(new java.awt.Color(255, 255, 255));
        TimerTA.setRows(5);
        jScrollPane1.setViewportView(TimerTA);

        CorePNL.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 210, 110));

        SelectAnsLBL.setForeground(new java.awt.Color(255, 255, 255));
        SelectAnsLBL.setText("Select Answer:");
        CorePNL.add(SelectAnsLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        AnswerARB.setBackground(new java.awt.Color(0, 0, 0));
        AnswerARB.setForeground(new java.awt.Color(255, 255, 255));
        AnswerARB.setText("A");
        CorePNL.add(AnswerARB, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 50, -1));

        AnswerBRB.setBackground(new java.awt.Color(0, 0, 0));
        AnswerBRB.setForeground(new java.awt.Color(255, 255, 255));
        AnswerBRB.setText("B");
        CorePNL.add(AnswerBRB, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 50, -1));

        AnswerCRB.setBackground(new java.awt.Color(0, 0, 0));
        AnswerCRB.setForeground(new java.awt.Color(255, 255, 255));
        AnswerCRB.setText("C");
        CorePNL.add(AnswerCRB, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 50, -1));

        AnswerBTN.setBackground(new java.awt.Color(0, 0, 0));
        AnswerBTN.setForeground(new java.awt.Color(255, 255, 255));
        AnswerBTN.setText("Answer");
        AnswerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnswerBTNActionPerformed(evt);
            }
        });
        CorePNL.add(AnswerBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        DisplayTA.setBackground(new java.awt.Color(0, 0, 0));
        DisplayTA.setColumns(20);
        DisplayTA.setForeground(new java.awt.Color(255, 255, 255));
        DisplayTA.setRows(5);
        jScrollPane.setViewportView(DisplayTA);

        CorePNL.add(jScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 328, 133));

        CardPNL.add(CorePNL, "card2");

        SidePNL.setBackground(new java.awt.Color(0, 102, 102));
        SidePNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.cyan, java.awt.Color.white, java.awt.Color.black, java.awt.Color.lightGray));
        SidePNL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TitlePNL1.setBackground(new java.awt.Color(0, 102, 102));

        LogoLBL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quiz logo.png"))); // NOI18N

        TitleLBL1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TitleLBL1.setForeground(new java.awt.Color(255, 255, 255));
        TitleLBL1.setText("Quiz");

        javax.swing.GroupLayout TitlePNL1Layout = new javax.swing.GroupLayout(TitlePNL1);
        TitlePNL1.setLayout(TitlePNL1Layout);
        TitlePNL1Layout.setHorizontalGroup(
            TitlePNL1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePNL1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(TitleLBL1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LogoLBL1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TitlePNL1Layout.setVerticalGroup(
            TitlePNL1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TitlePNL1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(TitlePNL1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LogoLBL1)
                    .addComponent(TitleLBL1))
                .addGap(20, 20, 20))
        );

        SidePNL.add(TitlePNL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 170, 70));

        QuizSubjectsPNL1.setBackground(new java.awt.Color(0, 0, 0));
        QuizSubjectsPNL1.setForeground(new java.awt.Color(255, 255, 255));

        MathematicsRB.setBackground(new java.awt.Color(0, 0, 0));
        MathematicsRB.setForeground(new java.awt.Color(255, 255, 255));
        MathematicsRB.setText("Mathematics");

        ChemistryRB.setBackground(new java.awt.Color(0, 0, 0));
        ChemistryRB.setForeground(new java.awt.Color(255, 255, 255));
        ChemistryRB.setText("Chemistry");

        CompSciRB.setBackground(new java.awt.Color(0, 0, 0));
        CompSciRB.setForeground(new java.awt.Color(255, 255, 255));
        CompSciRB.setText("Computer Science");

        javax.swing.GroupLayout QuizSubjectsPNL1Layout = new javax.swing.GroupLayout(QuizSubjectsPNL1);
        QuizSubjectsPNL1.setLayout(QuizSubjectsPNL1Layout);
        QuizSubjectsPNL1Layout.setHorizontalGroup(
            QuizSubjectsPNL1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuizSubjectsPNL1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(QuizSubjectsPNL1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MathematicsRB)
                    .addComponent(CompSciRB)
                    .addComponent(ChemistryRB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        QuizSubjectsPNL1Layout.setVerticalGroup(
            QuizSubjectsPNL1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuizSubjectsPNL1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CompSciRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChemistryRB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MathematicsRB)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        SidePNL.add(QuizSubjectsPNL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, 90));

        TimerLBL1.setForeground(new java.awt.Color(255, 255, 255));
        TimerLBL1.setText("Timer:");
        SidePNL.add(TimerLBL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        TimerTA1.setBackground(new java.awt.Color(0, 0, 0));
        TimerTA1.setColumns(20);
        TimerTA1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TimerTA1.setForeground(new java.awt.Color(255, 255, 255));
        TimerTA1.setRows(5);
        jScrollPane3.setViewportView(TimerTA1);

        SidePNL.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 220, 110));

        SelectAnsLBL1.setForeground(new java.awt.Color(255, 255, 255));
        SelectAnsLBL1.setText("Select Answer:");
        SidePNL.add(SelectAnsLBL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        AnswerARB1.setBackground(new java.awt.Color(0, 0, 0));
        AnswerARB1.setForeground(new java.awt.Color(255, 255, 255));
        AnswerARB1.setText("A");
        SidePNL.add(AnswerARB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 50, -1));

        AnswerBRB1.setBackground(new java.awt.Color(0, 0, 0));
        AnswerBRB1.setForeground(new java.awt.Color(255, 255, 255));
        AnswerBRB1.setText("B");
        SidePNL.add(AnswerBRB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 50, -1));

        AnswerCRB1.setBackground(new java.awt.Color(0, 0, 0));
        AnswerCRB1.setForeground(new java.awt.Color(255, 255, 255));
        AnswerCRB1.setText("C");
        SidePNL.add(AnswerCRB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 50, -1));

        AnswerBTN1.setBackground(new java.awt.Color(0, 0, 0));
        AnswerBTN1.setForeground(new java.awt.Color(255, 255, 255));
        AnswerBTN1.setText("Answer");
        AnswerBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnswerBTN1ActionPerformed(evt);
            }
        });
        SidePNL.add(AnswerBTN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        DisplayTA1.setBackground(new java.awt.Color(0, 0, 0));
        DisplayTA1.setColumns(20);
        DisplayTA1.setForeground(new java.awt.Color(255, 255, 255));
        DisplayTA1.setRows(5);
        jScrollPane2.setViewportView(DisplayTA1);

        SidePNL.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 328, 133));

        CardPNL.add(SidePNL, "card3");

        EditPNL.setBackground(new java.awt.Color(0, 102, 102));
        EditPNL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        QuestionIDLBL.setForeground(new java.awt.Color(255, 255, 255));
        QuestionIDLBL.setText("Question ID:");
        EditPNL.add(QuestionIDLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        QuestionIDTF.setBackground(new java.awt.Color(0, 0, 0));
        QuestionIDTF.setForeground(new java.awt.Color(255, 255, 255));
        EditPNL.add(QuestionIDTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 100, -1));

        SubjectLBL.setForeground(new java.awt.Color(255, 255, 255));
        SubjectLBL.setText("Subjects:");
        EditPNL.add(SubjectLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        SubjectCB.setBackground(new java.awt.Color(255, 255, 255));
        SubjectCB.setForeground(new java.awt.Color(0, 0, 0));
        SubjectCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Maths", "Biology", "Physics", "Computer Science", "Chemistry", "Side Maths" }));
        EditPNL.add(SubjectCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

        QuestionLBL.setForeground(new java.awt.Color(255, 255, 255));
        QuestionLBL.setText("Question:");
        EditPNL.add(QuestionLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        QuestionTA3.setBackground(new java.awt.Color(0, 0, 0));
        QuestionTA3.setColumns(20);
        QuestionTA3.setForeground(new java.awt.Color(255, 255, 255));
        QuestionTA3.setRows(5);
        jScrollPane4.setViewportView(QuestionTA3);

        EditPNL.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        OptionALBL.setForeground(new java.awt.Color(255, 255, 255));
        OptionALBL.setText("Option A:");
        EditPNL.add(OptionALBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        OptionATF.setBackground(new java.awt.Color(0, 0, 0));
        OptionATF.setForeground(new java.awt.Color(255, 255, 255));
        EditPNL.add(OptionATF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 120, -1));

        OptionBLBL.setForeground(new java.awt.Color(255, 255, 255));
        OptionBLBL.setText("Option B:");
        EditPNL.add(OptionBLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        OptionBTF.setBackground(new java.awt.Color(0, 0, 0));
        OptionBTF.setForeground(new java.awt.Color(255, 255, 255));
        OptionBTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptionBTFActionPerformed(evt);
            }
        });
        EditPNL.add(OptionBTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 120, -1));

        OptionCLBL.setForeground(new java.awt.Color(255, 255, 255));
        OptionCLBL.setText("Option C:");
        EditPNL.add(OptionCLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        OptionCTF.setBackground(new java.awt.Color(0, 0, 0));
        OptionCTF.setForeground(new java.awt.Color(255, 255, 255));
        OptionCTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OptionCTFActionPerformed(evt);
            }
        });
        EditPNL.add(OptionCTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 120, -1));

        CorrectLBL.setForeground(new java.awt.Color(255, 255, 255));
        CorrectLBL.setText("Correct Answer:");
        EditPNL.add(CorrectLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        CorrectCB.setBackground(new java.awt.Color(255, 255, 255));
        CorrectCB.setForeground(new java.awt.Color(0, 0, 0));
        CorrectCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C" }));
        EditPNL.add(CorrectCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, -1, -1));

        AddQuestionBTN.setBackground(new java.awt.Color(0, 0, 0));
        AddQuestionBTN.setForeground(new java.awt.Color(255, 255, 255));
        AddQuestionBTN.setText("Add");
        AddQuestionBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddQuestionBTNActionPerformed(evt);
            }
        });
        EditPNL.add(AddQuestionBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        SearchQuestionBTN.setBackground(new java.awt.Color(0, 0, 0));
        SearchQuestionBTN.setForeground(new java.awt.Color(255, 255, 255));
        SearchQuestionBTN.setText("Search");
        SearchQuestionBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchQuestionBTNActionPerformed(evt);
            }
        });
        EditPNL.add(SearchQuestionBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, -1, -1));

        DeleteQuestionBTN.setBackground(new java.awt.Color(0, 0, 0));
        DeleteQuestionBTN.setForeground(new java.awt.Color(255, 255, 255));
        DeleteQuestionBTN.setText("Delete");
        DeleteQuestionBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteQuestionBTNActionPerformed(evt);
            }
        });
        EditPNL.add(DeleteQuestionBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, -1, -1));

        ClearBTN.setBackground(new java.awt.Color(0, 0, 0));
        ClearBTN.setForeground(new java.awt.Color(255, 255, 255));
        ClearBTN.setText("Clear");
        ClearBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearBTNActionPerformed(evt);
            }
        });
        EditPNL.add(ClearBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, -1, -1));

        ManageLBL.setForeground(new java.awt.Color(255, 255, 255));
        ManageLBL.setText("Manage:");
        EditPNL.add(ManageLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, -1, -1));

        ManageDisplayTA.setBackground(new java.awt.Color(0, 0, 0));
        ManageDisplayTA.setColumns(20);
        ManageDisplayTA.setForeground(new java.awt.Color(255, 255, 255));
        ManageDisplayTA.setRows(5);
        jScrollPane5.setViewportView(ManageDisplayTA);

        EditPNL.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 360, 120));

        CardPNL.add(EditPNL, "card4");

        getContentPane().add(CardPNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 490, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CoreNavPNLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CoreNavPNLMouseClicked
        // TODO add your handling code here:
        
        // reset quiz
        resetCoreQuiz();
        resetSideQuiz();
        
        // Once panel clicked only selected panel visible
        CorePNL.setVisible(true);
        SidePNL.setVisible(false);
        EditPNL.setVisible(false);
    }//GEN-LAST:event_CoreNavPNLMouseClicked

    private void AnswerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnswerBTNActionPerformed
        // TODO add your handling code here:
        // If no subject is selected or quiz already finished, do nothing
        if (currentQuestions == null || currentQuestionIndex >= totalQuestions) {
            return;
        }

        char selectedAnswer = 'X';  // Default means nothing selected

        // Determine which answer (A/B/C) the user selected
        if (AnswerARB.isSelected()) {
            selectedAnswer = 'A';
        } else if (AnswerBRB.isSelected()) {
            selectedAnswer = 'B';
        } else if (AnswerCRB.isSelected()) {
            selectedAnswer = 'C';
        } else {
            // User clicked "Answer" without selecting anything
            javax.swing.JOptionPane.showMessageDialog(this, "Please select A, B or C.");
            return;
        }

        // Get the current question object
        QuizData.Question current = currentQuestions[currentQuestionIndex];

        // If user answer matches the correct letter, increase score
        if (selectedAnswer == current.getCorrectAnswer()) {
            score++;
        }

        // Move to next question and refresh display
        currentQuestionIndex++;
        showCurrentQuestion();
    }//GEN-LAST:event_AnswerBTNActionPerformed

    private void AddQuestionBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddQuestionBTNActionPerformed
        // TODO add your handling code here:
        // Read and validate ID
        String idText = QuestionIDTF.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Question ID.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Question ID must be a number.");
            return;
        }

        String subject = (String) SubjectCB.getSelectedItem();
        String questionText = QuestionTA3.getText().trim();
        String optionA = OptionATF.getText().trim();
        String optionB = OptionBTF.getText().trim();
        String optionC = OptionCTF.getText().trim();
        String correctStr = (String) CorrectCB.getSelectedItem();

        // Simple validation on text fields
        if (subject == null || subject.isEmpty()
            || questionText.isEmpty()
            || optionA.isEmpty()
            || optionB.isEmpty()
            || optionC.isEmpty()
            || correctStr == null || correctStr.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all fields and choose the correct answer.");
            return;
        }

        char correctAnswer = correctStr.charAt(0); // 'A','B','C'

        // Create new Question object from GUI fields
        QuizData.Question q = new QuizData.Question(id, subject, questionText,
            optionA, optionB, optionC,
            correctAnswer);

        // Check if this ID already exists in the bank
        QuizData.Question existing = QuizData.findQuestionByIdAndSubject(id, subject);

        if (existing == null) {
            // New question → add it
            QuizData.addQuestion(q);
            ManageDisplayTA.setText("Question added successfully with ID: " + id);
        } else {
            // Existing question → update it
            boolean updated = QuizData.updateQuestion(q);
            if (updated) {
                ManageDisplayTA.setText("Question ID " + id + " updated successfully.");
            } else {
                ManageDisplayTA.setText("Failed to update question with ID: " + id);
            }
        }

    }//GEN-LAST:event_AddQuestionBTNActionPerformed

    private void SearchQuestionBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchQuestionBTNActionPerformed
        // TODO add your handling code here:

        String idText = QuestionIDTF.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Question ID to search.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Question ID must be a number.");
            return;
        }

        String subject = (String) SubjectCB.getSelectedItem();
        if (subject == null || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a subject.");
            return;
        }

        QuizData.Question q = QuizData.findQuestionByIdAndSubject(id, subject);

        if (q == null) {
            ManageDisplayTA.setText("No question found with ID: " + id);
        } else {
            // Fill fields with the found question
            SubjectCB.setSelectedItem(q.getSubject());
            QuestionTA3.setText(q.getQuestion());
            OptionATF.setText(q.getOptionA());
            OptionBTF.setText(q.getOptionB());
            OptionCTF.setText(q.getOptionC());

            String correctText = String.valueOf(q.getCorrectAnswer()); // "A","B","C"
            CorrectCB.setSelectedItem(correctText);

            String result = "Question found:\n\n";
            result = result + "ID: " + q.getId() + "\n";
            result = result + "Subject: " + q.getSubject() + "\n";
            result = result + "Question: " + q.getQuestion() + "\n";
            result = result + "A) " + q.getOptionA() + "\n";
            result = result + "B) " + q.getOptionB() + "\n";
            result = result + "C) " + q.getOptionC() + "\n";
            result = result + "Correct: " + q.getCorrectAnswer() + "\n";

            ManageDisplayTA.setText(result);
        }
    }//GEN-LAST:event_SearchQuestionBTNActionPerformed

    private void DeleteQuestionBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteQuestionBTNActionPerformed
        // TODO add your handling code here:

        String idText = QuestionIDTF.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Question ID to delete.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Question ID must be a number.");
            return;
        }

        String subject = (String) SubjectCB.getSelectedItem();
        if (subject == null || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a subject.");
            return;
        }

        boolean deleted = QuizData.deleteQuestionByIdAndSubject(id, subject);

        if (deleted) {
            ManageDisplayTA.setText("Question with ID " + id + " deleted.");

            // Optionally clear the input fields
            QuestionTA3.setText("");
            OptionATF.setText("");
            OptionBTF.setText("");
            OptionCTF.setText("");
            CorrectCB.setSelectedIndex(-1);
        } else {
            ManageDisplayTA.setText("No question found with ID: " + id);
        }
    }//GEN-LAST:event_DeleteQuestionBTNActionPerformed

    private void OptionBTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptionBTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OptionBTFActionPerformed

    private void OptionCTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OptionCTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OptionCTFActionPerformed

    private void ClearBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBTNActionPerformed
        // TODO add your handling code here:

        QuestionIDTF.setText("");
        QuestionTA3.setText("");
        OptionATF.setText("");
        OptionBTF.setText("");
        OptionCTF.setText("");
        CorrectCB.setSelectedIndex(-1);   // no selection
        SubjectCB.setSelectedIndex(-1);   // no selection
        ManageDisplayTA.setText("");
    }//GEN-LAST:event_ClearBTNActionPerformed

    private void SideNavPNLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SideNavPNLMouseClicked
        // TODO add your handling code here:
        
        // reset quiz
        resetCoreQuiz();
        resetSideQuiz();
        
        // Once panel clicked only selected panel visible
        CorePNL.setVisible(false);
        SidePNL.setVisible(true);
        EditPNL.setVisible(false);
    }//GEN-LAST:event_SideNavPNLMouseClicked

    private void EditNavPNLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditNavPNLMouseClicked
        // TODO add your handling code here:
        
        // reset quiz
        resetCoreQuiz();
        resetSideQuiz();
        
        // Once panel clicked only selected panel visible
        CorePNL.setVisible(false);
        SidePNL.setVisible(false);
        EditPNL.setVisible(true);
    }//GEN-LAST:event_EditNavPNLMouseClicked

    private void AnswerBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnswerBTN1ActionPerformed
        // TODO add your handling code here
        // If side subject is not selected or quiz finished, do nothing
        if (sideQuestions == null || sideCurrentQuestionIndex >= sideTotalQuestions) {
            return;
        }

        char selectedAnswer = 'X';  // Default means nothing selected

        // Determine which answer (A/B/C) the user chose for side quiz
        if (AnswerARB1.isSelected()) {
            selectedAnswer = 'A';
        } else if (AnswerBRB1.isSelected()) {
            selectedAnswer = 'B';
        } else if (AnswerCRB1.isSelected()) {
            selectedAnswer = 'C';
        } else {
            // User clicked "Answer" with no answer chosen
            javax.swing.JOptionPane.showMessageDialog(this, "Please select A, B or C.");
            return;
        }

        // Get current side question
        QuizData.Question current = sideQuestions[sideCurrentQuestionIndex];

        // Increase side quiz score if correct
        if (selectedAnswer == current.getCorrectAnswer()) {
            sideScore++;
        }

        // Go to next side question
        sideCurrentQuestionIndex++;
        showCurrentSideQuestion();
    }//GEN-LAST:event_AnswerBTN1ActionPerformed

    private void MainMenuBTNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainMenuBTNMouseClicked
        // TODO add your handling code here:
        
        // Opens home page
        setVisible(false);
        HomePage HP = new HomePage();
        HP.setVisible(true);
    }//GEN-LAST:event_MainMenuBTNMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuizGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuizGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuizGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuizGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuizGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddQuestionBTN;
    private javax.swing.JRadioButton AnswerARB;
    private javax.swing.JRadioButton AnswerARB1;
    private javax.swing.JRadioButton AnswerBRB;
    private javax.swing.JRadioButton AnswerBRB1;
    private javax.swing.JButton AnswerBTN;
    private javax.swing.JButton AnswerBTN1;
    private javax.swing.JRadioButton AnswerCRB;
    private javax.swing.JRadioButton AnswerCRB1;
    private javax.swing.ButtonGroup AnswersBG;
    private javax.swing.JRadioButton BiologyRB;
    private javax.swing.JPanel CardPNL;
    private javax.swing.JRadioButton ChemistryRB;
    private javax.swing.JButton ClearBTN;
    private javax.swing.JRadioButton CompSciRB;
    private javax.swing.JLabel CoreNavIcon;
    private javax.swing.JLabel CoreNavLBL;
    private javax.swing.JPanel CoreNavPNL;
    private javax.swing.JPanel CorePNL;
    private javax.swing.JComboBox<String> CorrectCB;
    private javax.swing.JLabel CorrectLBL;
    private javax.swing.JButton DeleteQuestionBTN;
    private javax.swing.JTextArea DisplayTA;
    private javax.swing.JTextArea DisplayTA1;
    private javax.swing.JLabel EditNavIcon;
    private javax.swing.JLabel EditNavLBL;
    private javax.swing.JPanel EditNavPNL;
    private javax.swing.JPanel EditPNL;
    private javax.swing.JPanel LinePNL;
    private javax.swing.JLabel LogoLBL;
    private javax.swing.JLabel LogoLBL1;
    private javax.swing.JPanel MainMenuBTN;
    private javax.swing.JLabel MainMenuIcon;
    private javax.swing.JLabel MainMenuLBL;
    private javax.swing.JTextArea ManageDisplayTA;
    private javax.swing.JLabel ManageLBL;
    private javax.swing.JRadioButton MathematicsRB;
    private javax.swing.JRadioButton MathsRB;
    private javax.swing.JPanel NavPNL;
    private javax.swing.JLabel OptionALBL;
    private javax.swing.JTextField OptionATF;
    private javax.swing.JLabel OptionBLBL;
    private javax.swing.JTextField OptionBTF;
    private javax.swing.JLabel OptionCLBL;
    private javax.swing.JTextField OptionCTF;
    private javax.swing.JRadioButton PhysicsRB;
    private javax.swing.JLabel QuestionIDLBL;
    private javax.swing.JTextField QuestionIDTF;
    private javax.swing.JLabel QuestionLBL;
    private javax.swing.JTextArea QuestionTA3;
    private javax.swing.JPanel QuizSubjectsPNL;
    private javax.swing.JPanel QuizSubjectsPNL1;
    private javax.swing.JButton SearchQuestionBTN;
    private javax.swing.JLabel SelectAnsLBL;
    private javax.swing.JLabel SelectAnsLBL1;
    private javax.swing.JLabel SideNavIcon;
    private javax.swing.JLabel SideNavLBL;
    private javax.swing.JPanel SideNavPNL;
    private javax.swing.JPanel SidePNL;
    private javax.swing.JComboBox<String> SubjectCB;
    private javax.swing.JLabel SubjectLBL;
    private javax.swing.ButtonGroup SubjectsBG;
    private javax.swing.JLabel TimerLBL;
    private javax.swing.JLabel TimerLBL1;
    private javax.swing.JTextArea TimerTA;
    private javax.swing.JTextArea TimerTA1;
    private javax.swing.JLabel TitleLBL;
    private javax.swing.JLabel TitleLBL1;
    private javax.swing.JPanel TitlePNL;
    private javax.swing.JPanel TitlePNL1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
