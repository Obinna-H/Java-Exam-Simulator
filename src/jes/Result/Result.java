/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jes.Result;

import File.File_Count;
import File.ReadPathScore;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import jes.JES;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Humphrey
 */
public class Result extends JFrame implements ActionListener {

    File_Count fc = new File_Count();
    ReadPathScore getsco = new ReadPathScore();
    float avg;
    JProgressBar scorebar;
    JPanel conPanel, resultPanel, buttonPanel;
    BoxLayout box;
    JLabel pass_fail;
    JButton home, exit;
    JES jes = new JES();
    String loc = "src/jes/path/pause.txt";
    File reader = new File(loc);
    boolean exists = reader.exists();
    BufferedReader bufferedReader;

    public Result() throws IOException {
        getsco.readPathFile();
        fc.CountFiles();
//resume.resume_exam();

        pass_fail = new JLabel();
        pass_fail.setFont(new Font("Serif", Font.BOLD, 20));

        home = new JButton("Home");
        home.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);

//Container panel with box layout, to house the enite panel
        conPanel = new JPanel();
        conPanel.setPreferredSize(new Dimension(1366, 786));
        box = new BoxLayout(conPanel, BoxLayout.Y_AXIS);
        conPanel.setLayout(box);

//Panel that this plays the results
        resultPanel = new JPanel();
        resultPanel.setPreferredSize(new Dimension(1366, 640));
        resultPanel.setLayout(new MigLayout());
        resultPanel.setBackground(Color.yellow);

//Panel that houses the button
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout());

        scorebar = new JProgressBar(0);
        scorebar.setValue(0);
        scorebar.setStringPainted(true);
        scorebar.setPreferredSize(new Dimension(1000, 120));
        scorebar.setOpaque(true);
    }

    public void showdata() throws IOException {
        cal_Result();
        add(conPanel);

        conPanel.add(resultPanel);
        resultPanel.add(scorebar);
        resultPanel.add(pass_fail);

        conPanel.add(buttonPanel);
        buttonPanel.add(exit, "gapleft 100");
        buttonPanel.add(home, "gapleft 1100");

        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String resume_exam() throws IOException {
        File reader = new File(loc);
        boolean exists = reader.exists();
        String folder = null, nofile;
        if (exists) {
//String resume=pausepath.readPathPause();
            String resume_es = null;

            try {
                bufferedReader = new BufferedReader((new FileReader(reader)));
            } catch (FileNotFoundException e) {
            }
            StringBuilder builder = new StringBuilder("");
            while (true) {
                try {
                    if (!((resume_es = bufferedReader.readLine()) != null)) {
                        break;
                    }

                } catch (IOException e) {
                }
                builder.append(resume_es);
            }
            folder = builder.toString();
            try {
                bufferedReader.close();
            } catch (IOException e) {
            }

        } else {
            nofile = "";
        }

        //return folder;
        return folder;
    }

    private void cal_Result() throws IOException {
        //int totalscore;
        int totalscore;
        int totalscorecontuined = Integer.parseInt(getsco.readPathFile());

        String pausefile, string_pause_score;
        int pause_score;

        //Add scorce from pause
        if (exists) {
            //add pause score with total score
            pausefile = resume_exam();
            String[] split_pause = pausefile.split(", ");
            string_pause_score = split_pause[3];
            pause_score = Integer.parseInt(string_pause_score);
            System.out.print("   with resume0:   " + pause_score);
            totalscore = pause_score + totalscorecontuined;
            //Test output
            System.out.print("   with resume:   " + totalscore);
        } else {
            totalscore = Integer.parseInt(getsco.readPathFile());
            //Test output
            System.out.print("   without resume:   " + totalscore);
        }
//Test output
        System.out.print("   Displayed result:  " + totalscore);

        int totalfiles = fc.CountFiles();
        scorebar.setMaximum(totalfiles);
        //Calculate average
        avg = totalfiles / 2;
        if (totalscore > avg) {
            scorebar.setValue(totalscore);
            scorebar.setForeground(Color.green);
            pass_fail.setForeground(Color.green);
            pass_fail.setText("Passed");
        } else {
            scorebar.setValue(totalscore);
            scorebar.setForeground(Color.red);
            pass_fail.setForeground(Color.red);
            pass_fail.setText("Failed");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {
            setVisible(false);
            jes.showGUI();
        }

        if (e.getSource() == exit) {
            System.exit(0);
        }
    }

}
