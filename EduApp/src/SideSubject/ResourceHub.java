/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SideSubject;


/**
 *
 * @author Shoaib
 */
import java.awt.Desktop;//import for the url
import java.io.*;//import to save file
import javax.swing.JTable;//import to help populate the table
import javax.swing.JOptionPane;//import for the gui popups
import javax.swing.table.DefaultTableModel;
import java.net.URI;//import for the url

public class ResourceHub {//start ResourceHub class

    public ResourceHub() {

    }

    private final String resourcetxt = "ResourcesFile.txt";//declaring string for resources.txt

    public void addResources(String firstname, String surname, String Course, String Date, String resourceType, String URL) {// this will allow the end user to store their own personal resources, like links or other resourceTypes , this will write it to a txt file
        try (BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(resourcetxt, true))) {// bufferedwriter is the buffered writer obj, i will use this to write data to a txt file called resoruces, it is in a try catch to avoid the system from crashing
            bufferedwriter.write(firstname + "," + surname + "," + Course + "," + Date + "," + resourceType + "," + URL);//writing the variables passed in as arguments to this method
            bufferedwriter.newLine();//using the new line to add a line after so they are not cancatanated.
            bufferedwriter.flush();//flush is like wrapping things up succesfully
            System.out.println("Resources succesfully added to a txt file!");//notify the end user formally
        } catch (IOException ex) {//catch with io exception to notiify the user were it went wrong along the road
            System.out.println("Error: We ran into this error trying to save resources: " + ex.getMessage());//sout to notify the end user there was an error along the way , so they can see what is is
        }//end catch
    }//end add resoruces methods

    public void listAllResources(JTable ResourceTBL) {//method to display all resources into the table in the GUI, within the warm educational enviorment i created, display into the table that was passed in as an argument resourceTBL
        File resourcetxt = new File("ResourcesFile.txt");//file resourcetxt to asssign file of where resources are stored

        DefaultTableModel table = (DefaultTableModel) ResourceTBL.getModel();
        table.setRowCount(0); //set existing rows to 0, removing existing rows

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resourcetxt))) {//using the bufferedReader obj to read the resourcetxt text file , so i can then list them all into the table
            String line;//a line String for the reader to read from the file
            while ((line = bufferedReader.readLine()) != null) {//using a while loop to keep it looping until the very end 
                String[] row = line.split(",");//breaking the lines using commas, to keep everything nice neat and seperated

                if (row.length == table.getColumnCount()) {//ensure it matches the epected count of columns in the table
                    table.addRow(row);//add a row if not
                } else {//else if
                    JOptionPane.showMessageDialog(null,
                            "Encountered an Error! The number of resources and rows within the table must not match up.",
                            "Error related to data",
                            JOptionPane.ERROR_MESSAGE);//display as an error message
                    break;//break
                }//end else if
            }//end while loop
        } catch (IOException ex) {//ccatch here if something went wrong
            System.out.println("Unfortuneatly we encountered an error loading the requested file: " + ex.getMessage());//ntify the end user
        }//end catch
    }//end load resurces method

    public void SaveEndUserFeedback(String firstname, String surname, String gmail, String dateReview, String content) {//method to save the feedback entered into the contact us form.
        try (BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter("contactFile.txt", true))) {//creat bufffered writer to write to a file, linked contact.txt
            // Write user input to file with comma separation
            bufferedwriter.write(firstname + "," + surname + "," + gmail + "," + dateReview + "," + content);//writing this data to a file using bufferedwriter
            bufferedwriter.newLine();//adds line after break
            bufferedwriter.flush();//ending and signing finsishing
            System.out.println("Sucessfully written to the file and stored for review! Please give our team 1-3 business days!");//notify the end user that is was succesfully added to the file
        } catch (IOException ex) {//catch with IOException e
            System.out.println("We encounters an error writing the user feedback to the file. Exception: " + ex.getMessage());//notify the user it went astray along the way
        }//end catch
    }//end method

    public static void ConnectingTutorial1() {//method to link tutorials to labels within the GUI educational enviorment
        try {// start try
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=jhDUxynEQRI"));//linking youtube tutorial to the GUI labels, ive chosen a youtuber i watch a bit in my spar time as his videos are both entertaining and educations, will reference his channel in the write up
        } catch (Exception ex) {//catch if somethign went wrong so program doesnt catch
            System.out.println("Error encountered when opening link: " + ex.getMessage());//sout to notify the end user there was a error encountered opening the link
        }//end catch for error handling
    }//end method

    public static void ConnectingTutorial2() {//method to link tutorials to labels within the GUI educational enviorment
        try {// start try
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=K1iu1kXkVoA"));//linking youtube tutorial to the GUI labels, ive chosen a youtuber i watch a bit in my spar time as his videos are both entertaining and educations, will reference his channel in the write up
        } catch (Exception ex) {//catch if somethign went wrong so program doesnt catch
            System.out.println("Error encountered when opening link: " + ex.getMessage());
        }//end catch for error handling
    }//end method

    public static void ConnectingTutorial3() {//method to link tutorials to labels within the GUI educational enviorment
        try {// start try
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=tj5sLSFjVj4"));//linking youtube tutorial to the GUI labels, ive chosen a youtuber i watch a bit in my spar time as his videos are both entertaining and educations, will reference his channel in the write up
        } catch (Exception ex) {//catch if somethign went wrong so program doesnt catch
            System.out.println("Error encountered when opening link: " + ex.getMessage());
        }//end catch for error handling
    }//end method

    public static void ConnectingTutorial4() {//method to link tutorials to labels within the GUI educational enviorment
        try {// start try
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=vKVzRbsMnTQ"));//linking youtube tutorial to the GUI labels, ive chosen a youtuber i watch a bit in my spar time as his videos are both entertaining and educations, will reference his channel in the write up
        } catch (Exception ex) {//catch if somethign went wrong so program doesnt catch
            System.out.println("Error encountered when opening link: " + ex.getMessage());
        }//end catch for error handling
    }//end method

    public static void ConnectingTutorial5() {//method to link tutorials to labels within the GUI educational enviorment
        try {// start try
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=QvHBHuuddYk"));//linking youtube tutorial to the GUI labels, ive chosen a youtuber i watch a bit in my spar time as his videos are both entertaining and educations, will reference his channel in the write up
        } catch (Exception ex) {//catch if somethign wenr wrong so program doesnt catch
            System.out.println("Error encountered when opening link: " + ex.getMessage());
        }//end catch for error handling
    }//end method

}//end class
