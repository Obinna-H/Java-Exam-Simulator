/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jes;

import File.ReadPathFile;
import File.ReadPathPause;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Humphrey
 */
public class JES extends JFrame implements ActionListener {

    JLabel intro, folder_loc, strpath;
    BoxLayout box, box2;
    JPanel con_panel, con_panel_1, welcome_panel, path_panel, button_panel, button_panel_1;
    JTextField license;
    JButton enter, folder_button, load, delete;
    JFileChooser folder;
    File file, savedpath;
    ReadPathFile read = new ReadPathFile();
    ReadPathPause pause = new ReadPathPause();
    String loc = "src/jes/path/login.txt";
    String locpas = "src/jes/path/pause.txt";
//Client client = new Client();

    public JES() throws IOException {
        read.readPathFile();
        File reader = new File(locpas);
        boolean exists = reader.exists();
        if (exists) {
            pause.readPathPause();
        } else {

        }
//client.clientConnection();
        intro = new JLabel("Java Exam Simulator");
        intro.setForeground(Color.blue);
        intro.setFont(new Font("Serif", Font.BOLD, 80));

        folder_loc = new JLabel("");
        folder_loc.setVisible(false);

        strpath = new JLabel("");
        String strcontent = read.readPathFile();
        strcontent = strcontent.replace("\\\\", "\\");
        strpath.setText("");
        strpath.setText(strcontent);

        strpath.setVisible(false);

        license = new JTextField(20);

        enter = new JButton("Enter Lic");
        enter.addActionListener(this);

        load = new JButton("Load");
        load.addActionListener(this);
        load.setVisible(false);

        delete = new JButton("Delete");
        delete.addActionListener(this);
        delete.setVisible(false);

        folder_button = new JButton("Select Folder");
        folder_button.addActionListener(this);
        folder_button.setVisible(false);

        con_panel = new JPanel();
        box = new BoxLayout(con_panel, BoxLayout.Y_AXIS);
        con_panel.setLayout(box);

        con_panel_1 = new JPanel();
        box2 = new BoxLayout(con_panel_1, BoxLayout.X_AXIS);
        con_panel_1.setLayout(box2);

        welcome_panel = new JPanel();
        welcome_panel.setLayout(new MigLayout());
        welcome_panel.setPreferredSize(new Dimension(1366, -400));

        path_panel = new JPanel();
        path_panel.setLayout(new MigLayout());

        button_panel = new JPanel();
        button_panel.setLayout(new MigLayout());
        button_panel.setPreferredSize(new Dimension(180, -480));

        button_panel_1 = new JPanel();
        button_panel_1.setLayout(new MigLayout());

    }

    public void showGUI() {
//Check if login file exist
        File reader = new File(loc);
        boolean exists = reader.exists();
        if (exists) {
            validate_login_credentials();
        }

        add(con_panel);

        con_panel.add(welcome_panel);
        welcome_panel.add(intro, "gapleft -10");

        con_panel.add(con_panel_1);
        con_panel_1.add(path_panel);
        path_panel.add(strpath);

        con_panel_1.add(button_panel_1);
        button_panel_1.add(load);
        button_panel_1.add(delete);

        con_panel.add(button_panel);
        button_panel.add(license);
        button_panel.add(enter);
        button_panel.add(folder_loc);
        button_panel.add(folder_button);

        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

//Delete file if file is more than a month old
    private void dateSetup() {
        //File creation date

        File file = new File(loc);
        Date lastModDate = new Date(file.lastModified());
        long diff = new Date().getTime() - file.lastModified();
        int x = 20;
        if (diff > x * 24 * 60 * 60 * 1000) {
            file.delete();

        }
    }

    private void validate_login_credentials() {
        dateSetup();
        //read files with login credential
        // and by pass login
        File file = new File(loc);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
        }
        String loginva = null;
        StringBuilder builder = new StringBuilder("");
        while (true) {
            try {
                if (!((loginva = bufferedReader.readLine()) != null)) {
                    break;
                }
            } catch (IOException e) {
            }
            builder.append(loginva);
            String[] login = loginva.split(" ");
            String usernamel = login[0];
            //if file does not equal  equal null
            if (usernamel.equals("a")) {
                redirect();
            }
        }
        // Log.d("Output", builder.toString());
        try {
            bufferedReader.close();
        } catch (IOException e) {
        }
    }

    private void redirect() {
        // client.clientConnection();
        license.setVisible(false);
        enter.setVisible(false);
        folder_loc.setVisible(true);
        folder_button.setVisible(true);
        strpath.setVisible(true);
        load.setVisible(true);
        delete.setVisible(true);
    }

    /**
     * ***************************
     * Incomplete
     *
     * @throws IOException
     */
//Reload path
    void displayPath() throws IOException {
        read.readPathFile();
        strpath.setText("");
        String strcontent = read.readPathFile();
//strcontent = strcontent.replace("\\\\", "\\");
        strpath.setText(strcontent);
//strpath.setText("strcontent1");
    }

    void resume() throws IOException {
        pause.readPathPause();
        System.out.print("Resume:   " + pause.readPathPause());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            String lic = "a";
            String lic_text = license.getText();
            String activation = "C:\\Users\\Personal PC\\Documents\\NetBeansProjects\\JES\\src\\jes\\Activation\\key.txt";
            if (lic_text != null) {
                try {
                    File file_license = new File(activation);
                    //create file
                    if (file_license.createNewFile()) {
                        // write new file
                        FileWriter writer = new FileWriter(file_license);
                        writer.write(lic_text);
                        //Connect to network and send encrpt messgage
                        writer.close();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (lic_text.equals(lic)) {
                //create file and save login details
                File output = new File(loc);
                String value0 = lic_text;
                //write path to file
                try {
                    FileOutputStream fileout = new FileOutputStream(output.getAbsolutePath());
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(value0);
                    outputWriter.close();
                } catch (IOException x) {
                    //Log.e("Exception", "File write failed: " + e.toString());
                }
                redirect();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect lic " + lic, "ERROR", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (e.getSource() == folder_button) {
            //Set up the file chooser.
            if (folder == null) {
                folder = new JFileChooser();
                folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //Add a custom file filter and disable the default
                //(Accept All) file filter.
                folder.setAcceptAllFileFilterUsed(false);
            }
            //Show it.
            int returnVal = folder.showDialog(JES.this, "Attach");
            //Process the results.
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //GET DIRECTORY PATTH AND SAVE IN FILE
                file = folder.getCurrentDirectory();

                folder_loc.setText("");
                folder_loc.setText(file.getName());
                String fullPath = file.getAbsolutePath();
                try {
                    displayPath();
                } catch (IOException ex) {
                    Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                }
                String path = "C:\\Users\\Personal PC\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\path.txt";
                try {
                    // Java 7
                    Files.write(Paths.get(path), fullPath.getBytes());
                } catch (IOException ioe) {
                }
                if (file == null) {
                    JOptionPane.showMessageDialog(this, "Select Exam folder", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //Reset the file chooser for the next time it's shown.
            folder.setSelectedFile(null);
        }
        if (e.getSource() == load) {
            //Resume
            //Check if pause file exist
            File reader = new File(locpas);
            boolean exists = reader.exists();
            if (exists) {
                try {
                    String f0 = strpath.getText();
                    String f1 = pause.readPathPause();
                    String[] pause_es = f1.split(", ");
                    String f2 = pause_es[0];
                    //Compare file location with pause file
                    if (f0.equals(f2)) {
                        //OPtionpane Yes or no to resume yes contines from last que, no delte files
                        int input = JOptionPane.showConfirmDialog(null, "Do you want to resume your exam");
                        // 0=yes, 1=no, 2=cancel
                        switch (input) {
                            case 0:
                                //here
                                //Hide Welcome Frame with resume fuction
                                setVisible(false);
                                dispose();
                                try {
                                    File_Reader fr;
                                    fr = new File_Reader();
                                    fr.displayJES();
                                } catch (IOException ex) {
                                    Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 1:

                                try {
                                    String path = "C:\\Users\\Personal PC\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\pause.txt";
                                    File file = new File(path);
                                    if (file.delete()) {
                                        System.out.println(file.getName() + " is deleted!");

                                    } else {
                                        System.out.println("Delete operation is failed.");
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                /**
                                 * ***************
                                 * //Future update to redirect to fie reader
                                 * after file has been deleted if (!exists){ try
                                 * { //Hide Welcome Frame without resume fuction
                                 * setVisible(false); dispose(); File_Reader fr
                                 * = new File_Reader(); fr.displayJES(); // * }
                                 * catch (IOException ex) {
                                 * Logger.getLogger(JES.class.getName()).log(Level.SEVERE,
                                 * null, ex); } } ************************
                                 */
                                break;
                            case 2:
                                break;
                            default:
                                break;
                        }
                    } else {
                        //Hide Welcome Frame without resume fuction
                        setVisible(false);
                        dispose();
                        try {
                            File_Reader fr;
                            fr = new File_Reader();
                            fr.displayJES();
                        } catch (IOException ex) {
                            Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Hide Welcome Frame without resume fuction
                setVisible(false);
                dispose();
                try {
                    File_Reader fr;
                    fr = new File_Reader();
                    fr.displayJES();
                } catch (IOException ex) {
                    Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        if (e.getSource() == delete) {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to end your exam");
            // 0=yes, 1=no, 2=cancel   \
            switch (input) {
                case 0:
                    try {
                        String path = "C:\\Users\\Personal PC\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\path.txt";
                        String del = "";
                        // Java 7
                        Files.write(Paths.get(path), del.getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }

        }
    }

    public static void main(String[] args) throws IOException {
        JES e = new JES();
        e.showGUI();
    }

}
