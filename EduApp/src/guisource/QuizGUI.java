/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guisource;

import guisource.HomePage;
import guisource.QuizData;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
/**
 *
 * @author ikram
 */
public class QuizGUI extends javax.swing.JFrame {
    private Timer timer;            
    private int secondsPassed = 0;
    private QuizData.Question[] currentQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int totalQuestions = 0;
    
    private Timer sideTimer;
    private int sideSecondsPassed = 0;

    private QuizData.Question[] sideQuestions;
    private int sideCurrentQuestionIndex = 0;
    private int sideScore = 0;
    private int sideTotalQuestions = 0;
    /**
     * Creates new form QuizGUI
     */
    public QuizGUI() {
        initComponents();
        initTimer();                 
        initSideTimer();
        
        TimerTA.setEditable(false);  
        TimerTA.setText("0:00"); 
        
        TimerTA1.setEditable(false); // side timer display
        TimerTA1.setText("0:00");
        
        setupSubjectListeners();
        setupSideSubjectListeners();
        setupTabChangeListener();
        
        DisplayTA.setEditable(false);
        DisplayTA1.setEditable(false);
    }
    
    private void initTimer() {
        // Timer fires every 1000 ms (1 second)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;

                int minutes = secondsPassed / 60;
                int seconds = secondsPassed % 60;

                // Display as m:ss e.g. 0:05, 1:09, 2:34
                TimerTA.setText(String.format("%d:%02d", minutes, seconds));
            }
        });
    }
    
    private void initSideTimer() {
        sideTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sideSecondsPassed++;

                int minutes = sideSecondsPassed / 60;
                int seconds = sideSecondsPassed % 60;

                TimerTA1.setText(String.format("%d:%02d", minutes, seconds));
            }
        });
    }
    
    private void setupSubjectListeners() {
        MathsRB.addActionListener(e -> loadSubject("Maths"));
        BiologyRB.addActionListener(e -> loadSubject("Biology"));
        PhysicsRB.addActionListener(e -> loadSubject("Physics"));
    }
    
    private void setupSideSubjectListeners() {
        CompSciRB.addActionListener(e -> loadSideSubject("CS"));
        ChemistryRB.addActionListener(e -> loadSideSubject("Chemistry"));
        MathematicsRB.addActionListener(e -> loadSideSubject("Maths"));
    }
    
    private void setupTabChangeListener() {
        TabsPNL.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                resetCoreQuiz();
                resetSideQuiz();
            }
        });
    }
    
    private void loadSubject(String subject) {
        if (subject.equals("Maths")) {
            currentQuestions = QuizData.getMathsQuestions();
        } else if (subject.equals("Biology")) {
            currentQuestions = QuizData.getBiologyQuestions();
        } else if (subject.equals("Physics")) {
            currentQuestions = QuizData.getPhysicsQuestions();
        }

        currentQuestionIndex = 0;
        score = 0;

        if (currentQuestions != null) {
            totalQuestions = currentQuestions.length;
        } else {
            totalQuestions = 0;
        }

        showCurrentQuestion();
        
        if (timer != null) {
            secondsPassed = 0;
            TimerTA.setText("0:00");
            timer.start();
        }
    }
    
    private void loadSideSubject(String subject) {
        if (subject.equals("CS")) {
            sideQuestions = QuizData.getComputerScienceQuestions();
        } else if (subject.equals("Chemistry")) {
            sideQuestions = QuizData.getChemistryQuestions();
        } else if (subject.equals("Maths")) {
            sideQuestions = QuizData.getSideMathsQuestions();
        }

        sideCurrentQuestionIndex = 0;
        sideScore = 0;

        if (sideQuestions != null) {
            sideTotalQuestions = sideQuestions.length;
        } else {
            sideTotalQuestions = 0;
        }

        // reset side timer
        sideSecondsPassed = 0;
        TimerTA1.setText("0:00");

        if (sideTimer != null) {
            sideTimer.start();
        }

        showCurrentSideQuestion();
    }
    
    private void showCurrentQuestion() {
        if (currentQuestions == null || totalQuestions == 0) {
            DisplayTA.setText("No questions for this subject.");
            return;
        }

        if (currentQuestionIndex < totalQuestions) {
            QuizData.Question q = currentQuestions[currentQuestionIndex];

            String text = "Question " + (currentQuestionIndex + 1) + " of " + totalQuestions + ":\n\n";
            text = text + q.getQuestion() + "\n\n";
            text = text + "A) " + q.getOptionA() + "\n";
            text = text + "B) " + q.getOptionB() + "\n";
            text = text + "C) " + q.getOptionC() + "\n";

            DisplayTA.setText(text);

            // clear selected answer
            AwnsersBG.clearSelection();

        } else {
            // quiz finished
            if (timer != null && timer.isRunning()) {
                timer.stop();        // <<< STOP THE TIMER HERE
            }

            String result = "Quiz finished!\n\n";
            result = result + "Your score: " + score + " out of " + totalQuestions;

            DisplayTA.setText(result);
            AwnsersBG.clearSelection();
        }
    }
    
    private void showCurrentSideQuestion() {
        if (sideQuestions == null || sideTotalQuestions == 0) {
            DisplayTA1.setText("No questions for this subject.");
            return;
        }

        if (sideCurrentQuestionIndex < sideTotalQuestions) {
            QuizData.Question q = sideQuestions[sideCurrentQuestionIndex];

            String text = "Question " + (sideCurrentQuestionIndex + 1) + " of " + sideTotalQuestions + ":\n\n";
            text = text + q.getQuestion() + "\n\n";
            text = text + "A) " + q.getOptionA() + "\n";
            text = text + "B) " + q.getOptionB() + "\n";
            text = text + "C) " + q.getOptionC() + "\n";

            DisplayTA1.setText(text);

            // clear selected answer (same ButtonGroup)
            AwnsersBG.clearSelection();

        } else {
            // quiz finished
            if (sideTimer != null && sideTimer.isRunning()) {
                sideTimer.stop();
            }

            String result = "Side quiz finished!\n\n";
            result = result + "Your score: " + sideScore + " out of " + sideTotalQuestions;

            DisplayTA1.setText(result);
            AwnsersBG.clearSelection();
        }
    }
    
    private void resetCoreQuiz() {
        // stop timer
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        secondsPassed = 0;
        TimerTA.setText("0:00");

        currentQuestions = null;
        currentQuestionIndex = 0;
        score = 0;
        totalQuestions = 0;

        DisplayTA.setText("");

        // clear subject selection
        if (MathsRB != null) MathsRB.setSelected(false);
        if (BiologyRB != null) BiologyRB.setSelected(false);
        if (PhysicsRB != null) PhysicsRB.setSelected(false);

        // clear answers
        if (AwnsersBG != null) {
            AwnsersBG.clearSelection();
        }
    }
    
    private void resetSideQuiz() {
        if (sideTimer != null && sideTimer.isRunning()) {
            sideTimer.stop();
        }

        sideSecondsPassed = 0;
        TimerTA1.setText("0:00");

        sideQuestions = null;
        sideCurrentQuestionIndex = 0;
        sideScore = 0;
        sideTotalQuestions = 0;

        DisplayTA1.setText("");

        if (CompSciRB != null) CompSciRB.setSelected(false);
        if (ChemistryRB != null) ChemistryRB.setSelected(false);
        if (MathematicsRB != null) MathematicsRB.setSelected(false);

        if (AwnsersBG != null) {
            AwnsersBG.clearSelection();
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

        AwnsersBG = new javax.swing.ButtonGroup();
        MainBG = new javax.swing.ButtonGroup();
        TabsPNL = new javax.swing.JTabbedPane();
        CorePNL = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        DisplayTA = new javax.swing.JTextArea();
        QuizSubjectsPNL = new javax.swing.JPanel();
        ResetCoreBTN = new javax.swing.JButton();
        MathsRB = new javax.swing.JRadioButton();
        BiologyRB = new javax.swing.JRadioButton();
        PhysicsRB = new javax.swing.JRadioButton();
        MenuBTN = new javax.swing.JButton();
        SelectAnsLBL = new javax.swing.JLabel();
        AnswerARB = new javax.swing.JRadioButton();
        AwnserBRB = new javax.swing.JRadioButton();
        AwnserCRB = new javax.swing.JRadioButton();
        AnswerBTN = new javax.swing.JButton();
        TitleLBL = new javax.swing.JLabel();
        TimerLBL = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TimerTA = new javax.swing.JTextArea();
        LogoLBL = new javax.swing.JLabel();
        SidePNL = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DisplayTA1 = new javax.swing.JTextArea();
        QuizSubjectsPNL1 = new javax.swing.JPanel();
        SideResetBTN = new javax.swing.JButton();
        MathematicsRB = new javax.swing.JRadioButton();
        ChemistryRB = new javax.swing.JRadioButton();
        CompSciRB = new javax.swing.JRadioButton();
        MenuBTN1 = new javax.swing.JButton();
        SelectAnsLBL1 = new javax.swing.JLabel();
        AnswerARB1 = new javax.swing.JRadioButton();
        AwnserBRB1 = new javax.swing.JRadioButton();
        AwnserCRB1 = new javax.swing.JRadioButton();
        AnswerBTN1 = new javax.swing.JButton();
        TitleLBL1 = new javax.swing.JLabel();
        TimerLBL1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TimerTA1 = new javax.swing.JTextArea();
        LogoLBL1 = new javax.swing.JLabel();
        AddPNL = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        DisplayTA2 = new javax.swing.JTextArea();
        QuizSubjectsPNL2 = new javax.swing.JPanel();
        StartCoreBTN2 = new javax.swing.JButton();
        CoreSubject3RB2 = new javax.swing.JRadioButton();
        CoreSubject2RB2 = new javax.swing.JRadioButton();
        CoreSubject1RB2 = new javax.swing.JRadioButton();
        MenuBTN2 = new javax.swing.JButton();
        SelectAnsLBL2 = new javax.swing.JLabel();
        AnswerARB2 = new javax.swing.JRadioButton();
        AwnserBRB2 = new javax.swing.JRadioButton();
        AwnserCRB2 = new javax.swing.JRadioButton();
        AnswerBTN2 = new javax.swing.JButton();
        TitleLBL2 = new javax.swing.JLabel();
        TimerLBL2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TimerTA2 = new javax.swing.JTextArea();
        LogoLBL2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        TabsPNL.setBackground(new java.awt.Color(0, 0, 0));
        TabsPNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.white, java.awt.Color.black, java.awt.Color.white));
        TabsPNL.setForeground(new java.awt.Color(255, 255, 255));
        TabsPNL.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        CorePNL.setBackground(new java.awt.Color(0, 102, 102));
        CorePNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.cyan, java.awt.Color.white, java.awt.Color.black, java.awt.Color.lightGray));
        CorePNL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DisplayTA.setBackground(new java.awt.Color(0, 0, 0));
        DisplayTA.setColumns(20);
        DisplayTA.setForeground(new java.awt.Color(255, 255, 255));
        DisplayTA.setRows(5);
        jScrollPane.setViewportView(DisplayTA);

        CorePNL.add(jScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 328, 133));

        QuizSubjectsPNL.setBackground(new java.awt.Color(0, 0, 0));
        QuizSubjectsPNL.setForeground(new java.awt.Color(255, 255, 255));

        ResetCoreBTN.setBackground(new java.awt.Color(102, 102, 102));
        ResetCoreBTN.setForeground(new java.awt.Color(255, 255, 255));
        ResetCoreBTN.setText("Reset");
        ResetCoreBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetCoreBTNActionPerformed(evt);
            }
        });

        MathsRB.setBackground(new java.awt.Color(0, 0, 0));
        MainBG.add(MathsRB);
        MathsRB.setForeground(new java.awt.Color(255, 255, 255));
        MathsRB.setText("Maths");

        BiologyRB.setBackground(new java.awt.Color(0, 0, 0));
        MainBG.add(BiologyRB);
        BiologyRB.setForeground(new java.awt.Color(255, 255, 255));
        BiologyRB.setText("Biology");

        PhysicsRB.setBackground(new java.awt.Color(0, 0, 0));
        MainBG.add(PhysicsRB);
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
                    .addComponent(BiologyRB)
                    .addComponent(ResetCoreBTN))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ResetCoreBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CorePNL.add(QuizSubjectsPNL, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 85, -1, -1));

        MenuBTN.setBackground(new java.awt.Color(0, 0, 0));
        MenuBTN.setForeground(new java.awt.Color(255, 255, 255));
        MenuBTN.setText("Main Menu");
        MenuBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBTNActionPerformed(evt);
            }
        });
        CorePNL.add(MenuBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        SelectAnsLBL.setForeground(new java.awt.Color(255, 255, 255));
        SelectAnsLBL.setText("Select Answer:");
        CorePNL.add(SelectAnsLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        AnswerARB.setBackground(new java.awt.Color(0, 0, 0));
        AwnsersBG.add(AnswerARB);
        AnswerARB.setForeground(new java.awt.Color(255, 255, 255));
        AnswerARB.setText("A");
        CorePNL.add(AnswerARB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        AwnserBRB.setBackground(new java.awt.Color(0, 0, 0));
        AwnsersBG.add(AwnserBRB);
        AwnserBRB.setForeground(new java.awt.Color(255, 255, 255));
        AwnserBRB.setText("B");
        CorePNL.add(AwnserBRB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        AwnserCRB.setBackground(new java.awt.Color(0, 0, 0));
        AwnsersBG.add(AwnserCRB);
        AwnserCRB.setForeground(new java.awt.Color(255, 255, 255));
        AwnserCRB.setText("C");
        CorePNL.add(AwnserCRB, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        AnswerBTN.setBackground(new java.awt.Color(0, 0, 0));
        AnswerBTN.setForeground(new java.awt.Color(255, 255, 255));
        AnswerBTN.setText("Answer");
        AnswerBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnswerBTNActionPerformed(evt);
            }
        });
        CorePNL.add(AnswerBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        TitleLBL.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TitleLBL.setForeground(new java.awt.Color(255, 255, 255));
        TitleLBL.setText("Quiz");
        CorePNL.add(TitleLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        TimerLBL.setForeground(new java.awt.Color(255, 255, 255));
        TimerLBL.setText("Timer:");
        CorePNL.add(TimerLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, -1));

        TimerTA.setBackground(new java.awt.Color(0, 0, 0));
        TimerTA.setColumns(20);
        TimerTA.setForeground(new java.awt.Color(255, 255, 255));
        TimerTA.setRows(5);
        jScrollPane1.setViewportView(TimerTA);

        CorePNL.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, 110));

        LogoLBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quiz logo.png"))); // NOI18N
        CorePNL.add(LogoLBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        TabsPNL.addTab("Core ", CorePNL);

        SidePNL.setBackground(new java.awt.Color(0, 102, 102));
        SidePNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.cyan, java.awt.Color.white, java.awt.Color.black, java.awt.Color.lightGray));
        SidePNL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DisplayTA1.setBackground(new java.awt.Color(0, 0, 0));
        DisplayTA1.setColumns(20);
        DisplayTA1.setForeground(new java.awt.Color(255, 255, 255));
        DisplayTA1.setRows(5);
        jScrollPane2.setViewportView(DisplayTA1);

        SidePNL.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 328, 133));

        QuizSubjectsPNL1.setBackground(new java.awt.Color(0, 0, 0));
        QuizSubjectsPNL1.setForeground(new java.awt.Color(255, 255, 255));

        SideResetBTN.setBackground(new java.awt.Color(102, 102, 102));
        SideResetBTN.setForeground(new java.awt.Color(255, 255, 255));
        SideResetBTN.setText("Reset");

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
                    .addComponent(ChemistryRB)
                    .addComponent(SideResetBTN))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SideResetBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SidePNL.add(QuizSubjectsPNL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 85, -1, -1));

        MenuBTN1.setBackground(new java.awt.Color(0, 0, 0));
        MenuBTN1.setForeground(new java.awt.Color(255, 255, 255));
        MenuBTN1.setText("Main Menu");
        MenuBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBTN1ActionPerformed(evt);
            }
        });
        SidePNL.add(MenuBTN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        SelectAnsLBL1.setForeground(new java.awt.Color(255, 255, 255));
        SelectAnsLBL1.setText("Select Answer:");
        SidePNL.add(SelectAnsLBL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        AnswerARB1.setBackground(new java.awt.Color(0, 0, 0));
        AwnsersBG.add(AnswerARB1);
        AnswerARB1.setForeground(new java.awt.Color(255, 255, 255));
        AnswerARB1.setText("A");
        SidePNL.add(AnswerARB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        AwnserBRB1.setBackground(new java.awt.Color(0, 0, 0));
        AwnsersBG.add(AwnserBRB1);
        AwnserBRB1.setForeground(new java.awt.Color(255, 255, 255));
        AwnserBRB1.setText("B");
        SidePNL.add(AwnserBRB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        AwnserCRB1.setBackground(new java.awt.Color(0, 0, 0));
        AwnsersBG.add(AwnserCRB1);
        AwnserCRB1.setForeground(new java.awt.Color(255, 255, 255));
        AwnserCRB1.setText("C");
        SidePNL.add(AwnserCRB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        AnswerBTN1.setBackground(new java.awt.Color(0, 0, 0));
        AnswerBTN1.setForeground(new java.awt.Color(255, 255, 255));
        AnswerBTN1.setText("Answer");
        AnswerBTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnswerBTN1ActionPerformed(evt);
            }
        });
        SidePNL.add(AnswerBTN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        TitleLBL1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TitleLBL1.setForeground(new java.awt.Color(255, 255, 255));
        TitleLBL1.setText("Quiz");
        SidePNL.add(TitleLBL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        TimerLBL1.setForeground(new java.awt.Color(255, 255, 255));
        TimerLBL1.setText("Timer:");
        SidePNL.add(TimerLBL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

        TimerTA1.setBackground(new java.awt.Color(0, 0, 0));
        TimerTA1.setColumns(20);
        TimerTA1.setForeground(new java.awt.Color(255, 255, 255));
        TimerTA1.setRows(5);
        jScrollPane3.setViewportView(TimerTA1);

        SidePNL.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 90, 230, 110));

        LogoLBL1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quiz logo.png"))); // NOI18N
        SidePNL.add(LogoLBL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        TabsPNL.addTab("Side", SidePNL);

        AddPNL.setBackground(new java.awt.Color(0, 102, 102));
        AddPNL.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.cyan, java.awt.Color.white, java.awt.Color.black, java.awt.Color.lightGray));
        AddPNL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DisplayTA2.setBackground(new java.awt.Color(0, 0, 0));
        DisplayTA2.setColumns(20);
        DisplayTA2.setForeground(new java.awt.Color(255, 255, 255));
        DisplayTA2.setRows(5);
        jScrollPane4.setViewportView(DisplayTA2);

        AddPNL.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 328, 133));

        QuizSubjectsPNL2.setBackground(new java.awt.Color(0, 0, 0));
        QuizSubjectsPNL2.setForeground(new java.awt.Color(255, 255, 255));

        StartCoreBTN2.setBackground(new java.awt.Color(102, 102, 102));
        StartCoreBTN2.setForeground(new java.awt.Color(255, 255, 255));
        StartCoreBTN2.setText("Start");

        CoreSubject3RB2.setForeground(new java.awt.Color(255, 255, 255));
        CoreSubject3RB2.setText("Subject 3");

        CoreSubject2RB2.setForeground(new java.awt.Color(255, 255, 255));
        CoreSubject2RB2.setText("Subject 2");

        CoreSubject1RB2.setForeground(new java.awt.Color(255, 255, 255));
        CoreSubject1RB2.setText("Subject 1");

        javax.swing.GroupLayout QuizSubjectsPNL2Layout = new javax.swing.GroupLayout(QuizSubjectsPNL2);
        QuizSubjectsPNL2.setLayout(QuizSubjectsPNL2Layout);
        QuizSubjectsPNL2Layout.setHorizontalGroup(
            QuizSubjectsPNL2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuizSubjectsPNL2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(QuizSubjectsPNL2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CoreSubject3RB2)
                    .addComponent(CoreSubject1RB2)
                    .addComponent(CoreSubject2RB2)
                    .addComponent(StartCoreBTN2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        QuizSubjectsPNL2Layout.setVerticalGroup(
            QuizSubjectsPNL2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuizSubjectsPNL2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CoreSubject1RB2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CoreSubject2RB2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CoreSubject3RB2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StartCoreBTN2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AddPNL.add(QuizSubjectsPNL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 85, -1, -1));

        MenuBTN2.setBackground(new java.awt.Color(0, 0, 0));
        MenuBTN2.setForeground(new java.awt.Color(255, 255, 255));
        MenuBTN2.setText("Main Menu");
        MenuBTN2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBTN2ActionPerformed(evt);
            }
        });
        AddPNL.add(MenuBTN2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        SelectAnsLBL2.setForeground(new java.awt.Color(255, 255, 255));
        SelectAnsLBL2.setText("Select Answer:");
        AddPNL.add(SelectAnsLBL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        AwnsersBG.add(AnswerARB2);
        AnswerARB2.setForeground(new java.awt.Color(255, 255, 255));
        AnswerARB2.setText("A");
        AddPNL.add(AnswerARB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        AwnsersBG.add(AwnserBRB2);
        AwnserBRB2.setForeground(new java.awt.Color(255, 255, 255));
        AwnserBRB2.setText("B");
        AddPNL.add(AwnserBRB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        AwnsersBG.add(AwnserCRB2);
        AwnserCRB2.setForeground(new java.awt.Color(255, 255, 255));
        AwnserCRB2.setText("C");
        AddPNL.add(AwnserCRB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        AnswerBTN2.setBackground(new java.awt.Color(0, 0, 0));
        AnswerBTN2.setForeground(new java.awt.Color(255, 255, 255));
        AnswerBTN2.setText("Answer");
        AddPNL.add(AnswerBTN2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        TitleLBL2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        TitleLBL2.setForeground(new java.awt.Color(255, 255, 255));
        TitleLBL2.setText("Quiz");
        AddPNL.add(TitleLBL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        TimerLBL2.setForeground(new java.awt.Color(255, 255, 255));
        TimerLBL2.setText("Timer:");
        AddPNL.add(TimerLBL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, -1, -1));

        TimerTA2.setBackground(new java.awt.Color(0, 0, 0));
        TimerTA2.setColumns(20);
        TimerTA2.setForeground(new java.awt.Color(255, 255, 255));
        TimerTA2.setRows(5);
        jScrollPane5.setViewportView(TimerTA2);

        AddPNL.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, 110));

        LogoLBL2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/quiz logo.png"))); // NOI18N
        AddPNL.add(LogoLBL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        TabsPNL.addTab("Add", AddPNL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabsPNL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabsPNL, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBTNActionPerformed
        // TODO add your handling code here:
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        setVisible(false);
        HomePage HP = new HomePage();
        HP.setVisible(true);
    }//GEN-LAST:event_MenuBTNActionPerformed

    private void MenuBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBTN1ActionPerformed
        // TODO add your handling code here:
        if (sideTimer != null && sideTimer.isRunning()) {
            sideTimer.stop();
        }  
        
        setVisible(false);
        HomePage HP = new HomePage();
        HP.setVisible(true);
    }//GEN-LAST:event_MenuBTN1ActionPerformed

    private void MenuBTN2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBTN2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuBTN2ActionPerformed

    private void ResetCoreBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetCoreBTNActionPerformed
        // TODO add your handling code here:
        secondsPassed = 0;             
        TimerTA.setText("0:00");    
        
        if (timer != null) {
            timer.start();   
        }
        
    }//GEN-LAST:event_ResetCoreBTNActionPerformed

    private void AnswerBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnswerBTNActionPerformed
        // TODO add your handling code here:
        if (currentQuestions == null || currentQuestionIndex >= totalQuestions) {
            return; // nothing to do
        }

        char selectedAnswer = 'X';

        if (AnswerARB.isSelected()) {
            selectedAnswer = 'A';
        } else if (AwnserBRB.isSelected()) {
            selectedAnswer = 'B';
        } else if (AwnserCRB.isSelected()) {
            selectedAnswer = 'C';
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select A, B or C.");
            return;
        }

        QuizData.Question current = currentQuestions[currentQuestionIndex];

        if (selectedAnswer == current.getCorrectAnswer()) {
            score++;
        }

        currentQuestionIndex++;
        showCurrentQuestion();
    }//GEN-LAST:event_AnswerBTNActionPerformed

    private void AnswerBTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnswerBTN1ActionPerformed
        // TODO add your handling code here
        if (sideQuestions == null || sideCurrentQuestionIndex >= sideTotalQuestions) {
            return;
        }

        char selectedAnswer = 'X';

        if (AnswerARB1.isSelected()) {
            selectedAnswer = 'A';
        } else if (AwnserBRB1.isSelected()) {
            selectedAnswer = 'B';
        } else if (AwnserCRB1.isSelected()) {
            selectedAnswer = 'C';
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select A, B or C.");
            return;
        }

        QuizData.Question current = sideQuestions[sideCurrentQuestionIndex];

        if (selectedAnswer == current.getCorrectAnswer()) {
            sideScore++;
        }

        sideCurrentQuestionIndex++;
        showCurrentSideQuestion();
    }//GEN-LAST:event_AnswerBTN1ActionPerformed

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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private javax.swing.JPanel AddPNL;
    private javax.swing.JRadioButton AnswerARB;
    private javax.swing.JRadioButton AnswerARB1;
    private javax.swing.JRadioButton AnswerARB2;
    private javax.swing.JButton AnswerBTN;
    private javax.swing.JButton AnswerBTN1;
    private javax.swing.JButton AnswerBTN2;
    private javax.swing.JRadioButton AwnserBRB;
    private javax.swing.JRadioButton AwnserBRB1;
    private javax.swing.JRadioButton AwnserBRB2;
    private javax.swing.JRadioButton AwnserCRB;
    private javax.swing.JRadioButton AwnserCRB1;
    private javax.swing.JRadioButton AwnserCRB2;
    private javax.swing.ButtonGroup AwnsersBG;
    private javax.swing.JRadioButton BiologyRB;
    private javax.swing.JRadioButton ChemistryRB;
    private javax.swing.JRadioButton CompSciRB;
    private javax.swing.JPanel CorePNL;
    private javax.swing.JRadioButton CoreSubject1RB2;
    private javax.swing.JRadioButton CoreSubject2RB2;
    private javax.swing.JRadioButton CoreSubject3RB2;
    private javax.swing.JTextArea DisplayTA;
    private javax.swing.JTextArea DisplayTA1;
    private javax.swing.JTextArea DisplayTA2;
    private javax.swing.JLabel LogoLBL;
    private javax.swing.JLabel LogoLBL1;
    private javax.swing.JLabel LogoLBL2;
    private javax.swing.ButtonGroup MainBG;
    private javax.swing.JRadioButton MathematicsRB;
    private javax.swing.JRadioButton MathsRB;
    private javax.swing.JButton MenuBTN;
    private javax.swing.JButton MenuBTN1;
    private javax.swing.JButton MenuBTN2;
    private javax.swing.JRadioButton PhysicsRB;
    private javax.swing.JPanel QuizSubjectsPNL;
    private javax.swing.JPanel QuizSubjectsPNL1;
    private javax.swing.JPanel QuizSubjectsPNL2;
    private javax.swing.JButton ResetCoreBTN;
    private javax.swing.JLabel SelectAnsLBL;
    private javax.swing.JLabel SelectAnsLBL1;
    private javax.swing.JLabel SelectAnsLBL2;
    private javax.swing.JPanel SidePNL;
    private javax.swing.JButton SideResetBTN;
    private javax.swing.JButton StartCoreBTN2;
    private javax.swing.JTabbedPane TabsPNL;
    private javax.swing.JLabel TimerLBL;
    private javax.swing.JLabel TimerLBL1;
    private javax.swing.JLabel TimerLBL2;
    private javax.swing.JTextArea TimerTA;
    private javax.swing.JTextArea TimerTA1;
    private javax.swing.JTextArea TimerTA2;
    private javax.swing.JLabel TitleLBL;
    private javax.swing.JLabel TitleLBL1;
    private javax.swing.JLabel TitleLBL2;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
