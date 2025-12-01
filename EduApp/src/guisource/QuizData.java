/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guisource;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ikram
 */
public class QuizData {

    public static class Question {
        private int id;              // unique ID for questions
        private String subject;      // e.g. "Maths", "Biology", "CS"
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private char correctAnswer;  // 'A', 'B' or 'C'

        // constructor with ID for CRUD
        public Question(int id, String subject, String question, String optionA, String optionB, String optionC, char correctAnswer) {
            this.id = id;
            this.subject = subject;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.correctAnswer = correctAnswer;
        }

        // constructor for quiz (no ID, no subject)
        public Question(String question, String optionA, String optionB, String optionC, char correctAnswer) {
            this(-1, "", question, optionA, optionB, optionC, correctAnswer);
        }

        // getters
        public int getId() { 
            return id; 
        }
        
        public String getSubject() { 
            return subject; 
        }
        
        public String getQuestion() { 
            return question; 
        }
        
        public String getOptionA() { 
            return optionA; 
        }
        
        public String getOptionB() { 
            return optionB; 
        }
        
        public String getOptionC() { 
            return optionC; 
        }
        
        public char getCorrectAnswer() { 
            return correctAnswer; 
        }

        // setters 
        public void setSubject(String subject) { 
            this.subject = subject; 
        }
        
        public void setQuestion(String question) { 
            this.question = question; 
        }
        
        public void setOptionA(String optionA) { 
            this.optionA = optionA; 
        }
        
        public void setOptionB(String optionB) { 
            this.optionB = optionB; 
        }
        
        public void setOptionC(String optionC) { 
            this.optionC = optionC; 
        }
        
        public void setCorrectAnswer(char correctAnswer) {
            this.correctAnswer = correctAnswer; 
        }
    }

    // ===== Data structure to store ALL questions (default + user) =====
    private static List<Question> questionBank = new ArrayList<>();

    // static block: load default questions once class is first used
    static {
        initDefaultQuestions();
    }

    // fill questionBank with original hard-coded questions
    private static void initDefaultQuestions() {

        // ===== Maths questions =====
        questionBank.add(new Question(0, "Maths", "What is 2 + 2?", "3", "4", "5", 'B'));
        questionBank.add(new Question(1, "Maths", "What is 5 × 3?", "8", "15", "10", 'B'));
        questionBank.add(new Question(2, "Maths", "What is 12 ÷ 4?", "3", "4", "6", 'A'));
        questionBank.add(new Question(3, "Maths", "What is 10 − 7?", "2", "3", "5", 'B'));
        questionBank.add(new Question(4, "Maths", "What is 9 + 1?", "10", "11", "9", 'A'));

        // ===== Biology questions =====
        questionBank.add(new Question(0, "Biology", "What part of the cell contains DNA?", "Cytoplasm", "Nucleus", "Cell membrane", 'B'));
        questionBank.add(new Question(1, "Biology", "What gas do plants take in?", "Oxygen", "Carbon dioxide", "Nitrogen", 'B'));
        questionBank.add(new Question(2, "Biology", "What is the basic unit of life?", "Cell", "Organ", "Tissue", 'A'));
        questionBank.add(new Question(3, "Biology", "Which organ pumps blood?", "Lungs", "Heart", "Stomach", 'B'));
        questionBank.add(new Question(4, "Biology", "What do plants use for photosynthesis?", "Soil", "Light", "Sand", 'B'));

        // ===== Physics questions =====
        questionBank.add(new Question(0, "Physics", "What is the unit of force?", "Newton", "Joule", "Watt", 'A'));
        questionBank.add(new Question(1, "Physics", "What is the speed of light?", "3 × 10^8 m/s", "3 × 10^6 m/s", "3 × 10^5 m/s", 'A'));
        questionBank.add(new Question(2, "Physics", "What is energy?", "Ability to do work", "Ability to run", "Ability to sleep", 'A'));
        questionBank.add(new Question(3, "Physics", "Which force pulls objects to Earth?", "Friction", "Magnetism", "Gravity", 'C'));
        questionBank.add(new Question(4, "Physics", "What device measures current?", "Voltmeter", "Ammeter", "Thermometer", 'B'));

        // ===== Computer Science questions =====
        questionBank.add(new Question(0, "Computer Science", "What does CPU stand for?", "Central Processing Unit", "Computer Personal Unit", "Central Program Unit", 'A'));
        questionBank.add(new Question(1, "Computer Science", "Which of these is an input device?", "Monitor", "Keyboard", "Printer", 'B'));
        questionBank.add(new Question(2, "Computer Science", "What is the brain of the computer?", "RAM", "CPU", "Hard Disk", 'B'));
        questionBank.add(new Question(3, "Computer Science", "Which language is mainly used for web pages?", "HTML", "SQL", "Batch", 'A'));
        questionBank.add(new Question(4, "Computer Science", "What does RAM stand for?", "Read Access Memory", "Random Access Memory", "Rapid Access Memory", 'B'));

        // ===== Chemistry questions =====
        questionBank.add(new Question(0, "Chemistry", "What is H2O?", "Oxygen", "Hydrogen", "Water", 'C'));
        questionBank.add(new Question(1, "Chemistry", "What is the chemical symbol for Sodium?", "Na", "S", "So", 'A'));
        questionBank.add(new Question(2, "Chemistry", "Which gas is used in balloons?", "Oxygen", "Helium", "Carbon dioxide", 'B'));
        questionBank.add(new Question(3, "Chemistry", "What is the pH of pure water?", "7", "1", "14", 'A'));
        questionBank.add(new Question(4, "Chemistry", "Which is a noble gas?", "Oxygen", "Nitrogen", "Neon", 'C'));

        // ===== Side Maths questions =====
        questionBank.add(new Question(0, "Side Maths", "What is 7 + 5?", "10", "11", "12", 'C'));
        questionBank.add(new Question(1, "Side Maths", "What is 9 × 2?", "18", "16", "20", 'A'));
        questionBank.add(new Question(2, "Side Maths", "What is 15 − 6?", "9", "8", "7", 'A'));
        questionBank.add(new Question(3, "Side Maths", "What is 3²?", "6", "9", "12", 'B'));
        questionBank.add(new Question(4, "Side Maths", "What is 20 ÷ 4?", "4", "5", "6", 'B'));
    }

    // ===== CRUD methods for manager tab (Add, Search, Delete, Update) =====

    // Add a new question (from GUI)
    public static void addQuestion(Question q) {
        questionBank.add(q);
    }

    // Search question by ID **and subject** (returns null if not found)
    public static Question findQuestionByIdAndSubject(int id, String subject) {
        for (Question q : questionBank) {
            if (q.getId() == id && q.getSubject().equals(subject)) {
                return q;
            }
        }
        return null;
    }

    // Delete question by ID **and subject** (returns true if deleted, false if not found)
    public static boolean deleteQuestionByIdAndSubject(int id, String subject) {
        for (int i = 0; i < questionBank.size(); i++) {
            Question q = questionBank.get(i);
            if (q.getId() == id && q.getSubject().equals(subject)) {
                questionBank.remove(i);
                return true;
            }
        }
        return false;
    }

    // Update an existing question (match by ID **and subject**)
    public static boolean updateQuestion(Question updated) {
        for (int i = 0; i < questionBank.size(); i++) {
            Question q = questionBank.get(i);
            if (q.getId() == updated.getId() && q.getSubject().equals(updated.getSubject())) {
                questionBank.set(i, updated);
                return true;
            }
        }
        return false;
    }

    // ===== Helper: get questions by subject =====
    private static Question[] getQuestionsForSubject(String subjectName) {
        List<Question> result = new ArrayList<>();

        for (Question q : questionBank) {
            if (q.getSubject().equals(subjectName)) {
                result.add(q);
            }
        }

        return result.toArray(new Question[0]);
    }

    // ===== Maths questions =====
    public static Question[] getMathsQuestions() {
        return getQuestionsForSubject("Maths");
    }

    // ===== Biology questions =====
    public static Question[] getBiologyQuestions() {
        return getQuestionsForSubject("Biology");
    }

    // ===== Physics questions =====
    public static Question[] getPhysicsQuestions() {
        return getQuestionsForSubject("Physics");
    }

    // ===== Computer Science questions =====
    public static Question[] getComputerScienceQuestions() {
        return getQuestionsForSubject("Computer Science");
    }

    // ===== Chemistry questions =====
    public static Question[] getChemistryQuestions() {
        return getQuestionsForSubject("Chemistry");
    }

    // ===== Side Maths questions =====
    public static Question[] getSideMathsQuestions() {
        return getQuestionsForSubject("Side Maths");
    }
}
