/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guisource;

/**
 *
 * @author ikram
 */
public class QuizData {

    // Simple Question class
    public static class Question {
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private char correctAnswer;  // 'A', 'B' or 'C'

        public Question(String question, String optionA, String optionB, String optionC, char correctAnswer) {
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.correctAnswer = correctAnswer;
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
    }

    // ===== Maths questions =====
    public static Question[] getMathsQuestions() {
        return new Question[] {
            new Question("What is 2 + 2?", "3", "4", "5", 'B'),
            new Question("What is 5 × 3?", "8", "15", "10", 'B'),
            new Question("What is 12 ÷ 4?", "3", "4", "6", 'A'),
            new Question("What is 10 − 7?", "2", "3", "5", 'B'),
            new Question("What is 9 + 1?", "10", "11", "9", 'A')
        };
    }

    // ===== Biology questions =====
    public static Question[] getBiologyQuestions() {
        return new Question[] {
            new Question("What part of the cell contains DNA?", "Cytoplasm", "Nucleus", "Cell membrane", 'B'),
            new Question("What gas do plants take in?", "Oxygen", "Carbon dioxide", "Nitrogen", 'B'),
            new Question("What is the basic unit of life?", "Cell", "Organ", "Tissue", 'A'),
            new Question("Which organ pumps blood?", "Lungs", "Heart", "Stomach", 'B'),
            new Question("What do plants use for photosynthesis?", "Soil", "Light", "Sand", 'B')
        };
    }

    // ===== Physics questions =====
    public static Question[] getPhysicsQuestions() {
        return new Question[] {
            new Question("What is the unit of force?", "Newton", "Joule", "Watt", 'A'),
            new Question("What is the speed of light?", "3 × 10^8 m/s", "3 × 10^6 m/s", "3 × 10^5 m/s", 'A'),
            new Question("What is energy?", "Ability to do work", "Ability to run", "Ability to sleep", 'A'),
            new Question("Which force pulls objects to Earth?", "Friction", "Magnetism", "Gravity", 'C'),
            new Question("What device measures current?", "Voltmeter", "Ammeter", "Thermometer", 'B')
        };
    }
    
    // ===== Computer Science questions =====
    public static Question[] getComputerScienceQuestions() {
        return new Question[] {
            new Question("What does CPU stand for?", "Central Processing Unit", "Computer Personal Unit", "Central Program Unit", 'A'),
            new Question("Which of these is an input device?", "Monitor", "Keyboard", "Printer", 'B'),
            new Question("What is the brain of the computer?", "RAM", "CPU", "Hard Disk", 'B'),
            new Question("Which language is mainly used for web pages?", "HTML", "SQL", "Batch", 'A'),
            new Question("What does RAM stand for?", "Read Access Memory", "Random Access Memory", "Rapid Access Memory", 'B')
        };
    }

    // ===== Chemistry questions =====
    public static Question[] getChemistryQuestions() {
        return new Question[] {
            new Question("What is H2O?", "Oxygen", "Hydrogen", "Water", 'C'),
            new Question("What is the chemical symbol for Sodium?", "Na", "S", "So", 'A'),
            new Question("Which gas is used in balloons?", "Oxygen", "Helium", "Carbon dioxide", 'B'),
            new Question("What is the pH of pure water?", "7", "1", "14", 'A'),
            new Question("Which is a noble gas?", "Oxygen", "Nitrogen", "Neon", 'C')
        };
    }

    // ===== Side Maths questions (can be different from core) =====
    public static Question[] getSideMathsQuestions() {
        return new Question[] {
            new Question("What is 7 + 5?", "10", "11", "12", 'C'),
            new Question("What is 9 × 2?", "18", "16", "20", 'A'),
            new Question("What is 15 − 6?", "9", "8", "7", 'A'),
            new Question("What is 3²?", "6", "9", "12", 'B'),
            new Question("What is 20 ÷ 4?", "4", "5", "6", 'B')
        };
    }
}
