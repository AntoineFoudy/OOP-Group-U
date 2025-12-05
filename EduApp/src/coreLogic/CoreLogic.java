/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coreLogic;

import java.io.BufferedReader;
import java.io.FileReader;

public class CoreLogic {

    private String jsonFile;

    // Constructor
    public CoreLogic(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    // Method to read extra notes for "problem_solving"
    public String readProblemSolvingNotes() throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
        String line;
        boolean inSection = false;
        StringBuilder extraContent = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("\"problem_solving\"")) {
                inSection = true;
                continue;
            }

            if (inSection && line.startsWith("]")) {
                inSection = false;
                break;
            }

            if (inSection) {
                line = line.replace("\"", "").replace(",", "").trim();
                if (!line.isEmpty()) {
                    extraContent.append(line).append("\n");
                }
            }
        }

        reader.close();
        return extraContent.toString();
    }
}

