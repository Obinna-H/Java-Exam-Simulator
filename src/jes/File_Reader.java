/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jes;

import File.File_Count;
import File.ReadPathAnswer;
import File.ReadPathFile;
import File.ReadPathPause;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import jes.Result.Result;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Humphrey
 */
public final class File_Reader extends JFrame implements ActionListener {

    JPanel con_panel, que_panel, ans_panel, but_panel;
    BoxLayout box;
    JLabel que_label, main_que_label, ans_label, sec_label, exp_label, number, score;
    JRadioButton opt_a, opt_b, opt_c, opt_d;
    ButtonGroup group;
    JComboBox mark_cb;
    JButton prev, show_ans, mark, next, go_to, pause, end;
    int counter;
    int frist_counter = 1;
    String getpath, correct, strmark;
    ReadPathFile read = new ReadPathFile();
    ReadPathAnswer ansFile = new ReadPathAnswer();
    ReadPathPause pausepath = new ReadPathPause();
    Result r = new Result();
    String loc = "src/jes/path/pause.txt";
    BufferedReader bufferedReader;
    File reader = new File(loc);
    boolean exists = reader.exists();
    File_Count fc = new File_Count();

    public File_Reader() throws IOException {
        read.readPathFile();
        File_Count fc = new File_Count();
        ansFile.readPathAnswer();
//r.showdata();
//Container panel with box layout, to house the enite panel
        con_panel = new JPanel();
        con_panel.setPreferredSize(new Dimension(1366, 786));
        box = new BoxLayout(con_panel, BoxLayout.Y_AXIS);
        con_panel.setLayout(box);
//Question panel
        que_panel = new JPanel();
        que_panel.setPreferredSize(new Dimension(100, 450));
        que_panel.setLayout(new MigLayout("", "[][]20[]", "[]20[]"));
//Answer Panel
        ans_panel = new JPanel();
        ans_panel.setPreferredSize(new Dimension(180, 200));
        ans_panel.setLayout(new MigLayout());
//Button Panel
        but_panel = new JPanel();
        but_panel.setPreferredSize(new Dimension(50, 50));
        but_panel.setLayout(new MigLayout());
//
        que_label = new JLabel();
        main_que_label = new JLabel();
        ans_label = new JLabel();
        ans_label.setVisible(false);
        sec_label = new JLabel();
        sec_label.setVisible(false);
        exp_label = new JLabel();
        exp_label.setVisible(false);
        number = new JLabel();
        score = new JLabel("0");

//Group the radio buttons.
        group = new ButtonGroup();
        group.add(opt_a);
        group.add(opt_b);
        group.add(opt_c);
        group.add(opt_d);

//
        opt_a = new JRadioButton();
        opt_a.addActionListener(this);
        opt_b = new JRadioButton();
        opt_b.addActionListener(this);
        opt_c = new JRadioButton();
        opt_c.addActionListener(this);
        opt_d = new JRadioButton();
        opt_d.addActionListener(this);

//
        mark_cb = new JComboBox();

//
        prev = new JButton("Prev");
        prev.addActionListener(this);
        show_ans = new JButton("Show Anwser");
        show_ans.addActionListener(this);
        mark = new JButton("Mark");
        mark.addActionListener(this);
        next = new JButton("Next");
        next.addActionListener(this);
        go_to = new JButton("Go to/Unmark");
        go_to.addActionListener(this);
        pause = new JButton("Pause");
        pause.addActionListener(this);
        end = new JButton("End");
        end.addActionListener(this);
        if (exists) {
            String paused_number = null, num = "";
            num = resume_exam();
            String[] pause_es = num.split(", ");
            paused_number = pause_es[2];
            counter = Integer.parseInt(paused_number) + 1;
        } else {
            counter = 2;
        }
    }

    void read_file() throws FileNotFoundException, IOException {
        String f0 = null, fullfilename = null, paused_number = null, num = "";

        if (exists) {
            num = resume_exam();
            f0 = read.readPathFile();
            String[] pause_es = num.split(", ");
            fullfilename = pause_es[0];
            fullfilename = fullfilename.replace("\\", "\\\\");
            //System.out.println("File name: "+fullfilename);
            //String filename=pause_es[1];
            //System.out.println("File name: "+filename);
            paused_number = pause_es[2];
            //File reader = new File(loc);
            //boolean exists = reader.exists();
        }
        int t = 1;
        if (fullfilename != null) {
            //if (exists){
            if (f0.equals(fullfilename)) {
                //Compare
                t = Integer.parseInt(paused_number);
            } else {
                t = 1;
            }
        }
        String correctAns = null;
        //RESET RAIDO BUTTON
        resetRadioButton();
        //HIDE ANSWER PANEL
        hideAnsPanel();
        //COUNT
        File reader = new File(loc);
        boolean exists = reader.exists();
        if (exists) {
            number.setText("" + t++);
        } else {
            number.setText("" + frist_counter++);
        }
        BufferedReader br = null;
//List all Files in folder
        try (Stream<Path> walk = Files.walk(Paths.get(read.readPathFile()))) {
            List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
            for (int count = 0; count < t; count++) {
                File file = new File(result.get(count));
                br = new BufferedReader(new FileReader(file));
                String sb;
                while ((sb = br.readLine()) != null) //QUESTION
                {

                    if (sb.contains("QUESTION")) {
                        que_label.setText(sb);
                    } else if (sb.contains("QUE:")) {
                        System.out.println("READ : " + sb);
                        String[] que = sb.split("QUE:");
                        main_que_label.setText(que[1]);
                        //OPTIONS
                    } else if (sb.contains("A.")) {
                        opt_a.setText(sb);
                    } else if (sb.contains("B.")) {
                        opt_b.setText(sb);
                    } else if (sb.contains("C.")) {
                        opt_c.setText(sb);
                    } else if (sb.contains("D.")) {
                        opt_d.setText(sb);
                    } else if (sb.contains("Correct Answer:")) {
                        ans_label.setText(sb);
                        correctAns = sb;
                        correctAns = correctAns.replace("Correct Answer:", "");
                        String pathtext = "C:\\Users\\Humphrey\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\Path0.txt";
                        try {
                            // Java 7
                            Files.write(Paths.get(pathtext), correctAns.getBytes());
                        } catch (IOException ioe) {
                        }
                    } else if (sb.contains("Section:")) {
                        sec_label.setText(sb);
                    } else if (sb.contains("Explanation/Reference:")) {
                        exp_label.setText(sb);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException | NullPointerException ex) {

                Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void displayJES() throws IOException {
        read_file();
        add(con_panel);
        con_panel.add(que_panel);
        que_panel.add(que_label, "split 2");
        que_panel.add(number, "align left");
        que_panel.add(mark_cb, "split 2");
        que_panel.add(go_to);
        que_panel.add(pause);
        que_panel.add(end, " wrap");
        que_panel.add(main_que_label, "wrap");

        que_panel.add(opt_a, "wrap");
        group.add(opt_a);
        que_panel.add(opt_b, "wrap");
        group.add(opt_b);
        que_panel.add(opt_c, "wrap");
        group.add(opt_c);
        que_panel.add(opt_d, "wrap");
        group.add(opt_d);

        con_panel.add(ans_panel);
        ans_panel.add(ans_label, "wrap");
        ans_panel.add(sec_label, "wrap");
        ans_panel.add(exp_label, "wrap");

        con_panel.add(but_panel);
        but_panel.add(prev, "gapleft 30");
        but_panel.add(show_ans, "gapleft 300");
        but_panel.add(mark, "gapleft 300");
        but_panel.add(next, "gapleft 320");

        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            {

                //System.out.println("  Score:  "   +score.getText());
                //RESET RAIDO BUTTON
                resetRadioButton();
                //HIDE ANSWER PANEL
                hideAnsPanel();
                //COUNT
                // number.setText(""+counter++);
                BufferedReader br = null;
                //List all Files in folder
                try (Stream<Path> walk = Files.walk(Paths.get(read.readPathFile()))) {
                    List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

                    //JLabel numbering sent to loop for counting
                    String num = number.getText();
                    int i = Integer.parseInt(num);

                    //COUNT
                    number.setText("" + counter++);

                    //int totalfiles=fc.CountFiles();
                    for (int count = 1; count < i; ++count) {
                        //for(int count = Integer.parseInt(num);count<totalfiles;++count){
                        File file = new File(result.get(count));
                        br = new BufferedReader(new FileReader(file));
                        String sb;
                        while ((sb = br.readLine()) != null) //QUESTION
                        {
                            if (sb.contains("QUESTION")) {
                                que_label.setText(sb);
                            } else if (sb.contains("QUE:")) {
                                main_que_label.setText(sb);
                                //OPTIONS
                            } else if (sb.contains("A.")) {
                                opt_a.setText(sb);
                            } else if (sb.contains("B.")) {
                                opt_b.setText(sb);
                            } else if (sb.contains("C.")) {
                                opt_c.setText(sb);
                            } else if (sb.contains("D.")) {
                                opt_d.setText(sb);
                            } else if (sb.contains("Correct Answer:")) {
                                ans_label.setText(sb);
                                correct = sb;
                                correct = correct.replace("Correct Answer:", "");
                                String pathtext = "C:\\Users\\Humphrey\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\Path0.txt";
                                try {
                                    // Java 7
                                    Files.write(Paths.get(pathtext), correct.getBytes());
                                } catch (IOException ioe) {
                                }
                            } else if (sb.contains("Section:")) {
                                sec_label.setText(sb);
                            } else if (sb.contains("Explanation/Reference:")) {
                                exp_label.setText(sb);
                            }
                        }

                        try {
                            br.close();
                        } catch (IOException | NullPointerException ex) {
                            Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (IndexOutOfBoundsException ioobe) {
                    next.setVisible(false);
                    prev.setVisible(true);
                    ///Option to end the exam
                    int input = JOptionPane.showConfirmDialog(null, "Do you want to end your exam");
                    // 0=yes, 1=no, 2=cancel
                    switch (input) {
                        case 0:
                            setVisible(false);
                            getScore();
                             {
                                try {
                                    r.showdata();
                                } catch (IOException ex) {
                                    Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            break;

                        case 1:
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == prev) {
            //HIDE ANSWER PANEL
            hideAnsPanel();
            //RESET RADIO BUTTON
            resetRadioButton();
            number.setText("");
            number.setText("" + counter--);
            BufferedReader br = null;
            //List all Files in folder
            try (Stream<Path> walk = Files.walk(Paths.get(read.readPathFile()))) {
                List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
                //JLabel numbering sent to loop for counting
                String num = number.getText();
                int i = Integer.parseInt(num);
                int count;
                for (count = Integer.parseInt(num); count == i; count--) {
                    File file = new File(result.get(count));
                    br = new BufferedReader(new FileReader(file));
                    String sb;
                    while ((sb = br.readLine()) != null) //QUESTION
                    {
                        if (sb.contains("QUESTION")) {
                            que_label.setText(sb);
                        } else if (sb.contains("QUE:")) {
                            main_que_label.setText(sb);
                            //OPTIONS
                        } else if (sb.contains("A.")) {
                            opt_a.setText(sb);
                        } else if (sb.contains("B.")) {
                            opt_b.setText(sb);
                        } else if (sb.contains("C.")) {
                            opt_c.setText(sb);
                        } else if (sb.contains("D.")) {
                            opt_d.setText(sb);
                        } else if (sb.contains("Correct Answer:")) {
                            ans_label.setText(sb);
                        } else if (sb.contains("Section:")) {
                            sec_label.setText(sb);
                        } else if (sb.contains("Explanation/Reference:")) {
                            exp_label.setText(sb);
                        }
                    }
                    try {
                        br.close();
                    } catch (IOException | NullPointerException ex) {
                        Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IndexOutOfBoundsException ioobe) {
                prev.setVisible(false);
                next.setVisible(true);
                JOptionPane.showMessageDialog(this, "You have reached the beginning of your exam", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }
        }

        if (e.getSource() == mark) {
            String num = number.getText();
            mark_cb.addItem(num);
            // Get number of items in combo box
            int num_cb = mark_cb.getItemCount();
            //If item in mark combo box is more tahn one run a check
            if (num_cb > 1) {
// Loop to get item in mark
                for (int i = 0; i < num_cb; i++) {
                    Object itemi = mark_cb.getItemAt(i);
                    for (int j = i + 1; j < num_cb; j++) {
                        Object itemj = mark_cb.getItemAt(j);
//Run a comparison to check for redundancy
                        if (itemi.equals(itemj)) {
//if match remove last, match added.
                            mark_cb.removeItemAt(mark_cb.getItemCount() - 1);
                        }
                    }
                }
            }
        }

        if (e.getSource() == show_ans) {
            showAnsPanel();
        }

        if (e.getSource() == go_to) {
            try {
                //RESET RAIDO BUTTON
                resetRadioButton();
                //HIDE ANSWER PANEL
                hideAnsPanel();
                //JLabel numbering sent to loop for counting
                String strcount = mark_cb.getSelectedItem().toString();
                int markcount = Integer.parseInt(strcount);
                //COUNT
                number.setText("" + markcount);
                BufferedReader br = null;
                //List all Files in folder
                try (Stream<Path> walk = Files.walk(Paths.get(read.readPathFile()))) {
                    List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
                    //
                    for (int count = 1; count < markcount; ++count) {
                        File file = new File(result.get(count));
                        br = new BufferedReader(new FileReader(file));
                        String sb;
                        while ((sb = br.readLine()) != null) //QUESTION
                        {
                            if (sb.contains("QUESTION")) {
                                que_label.setText(sb);
                            } else if (sb.contains("QUE:")) {
                                main_que_label.setText(sb);
                                //OPTIONS
                            } else if (sb.contains("A.")) {
                                opt_a.setText(sb);
                            } else if (sb.contains("B.")) {
                                opt_b.setText(sb);
                            } else if (sb.contains("C.")) {
                                opt_c.setText(sb);
                            } else if (sb.contains("D.")) {
                                opt_d.setText(sb);
                            } else if (sb.contains("Correct Answer:")) {
                                ans_label.setText(sb);
                                correct = sb;
                                correct = correct.replace("Correct Answer:", "");
                                String pathtext = "C:\\Users\\Humphrey\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\Path0.txt";
                                try {
                                    // Java 7
                                    Files.write(Paths.get(pathtext), correct.getBytes());
                                } catch (IOException ioe) {
                                }
                            } else if (sb.contains("Section:")) {
                                sec_label.setText(sb);
                            } else if (sb.contains("Explanation/Reference:")) {
                                exp_label.setText(sb);
                            }
                        }

                        try {
                            br.close();
                        } catch (IOException | NullPointerException ex) {
                            Logger.getLogger(JES.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.print("Out of bounds" + ioobe.getMessage());
                    next.setVisible(false);
                    prev.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Remove marked item
                mark_cb.removeItem(strcount);
            } catch (NullPointerException npe) {
            }
        }

        if (e.getSource() == pause) {
            try {
                //Get JLabel numbering and File name and save in txt
                String num = number.getText();
                //Get Score
                String Pscore = score.getText();
                File file = new File(read.readPathFile());
                String filename = file.getName();
                String paused = file.getAbsolutePath() + ", " + filename + ", " + num + ", " + Pscore;
                String pathtext = "C:\\Users\\Humphrey\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\Pause.txt";
                try {
                    // Java 7
                    Files.write(Paths.get(pathtext), paused.getBytes());
                } catch (IOException ioe) {
                }
            } catch (IOException ex) {
                Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == end) {
            setVisible(false);
            getScore();
            try {
                r.showdata();
            } catch (IOException ex) {
                Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (e.getSource() == opt_a) {
            try {
                String a;
                a = opt_a.getText();
                char charAt = a.charAt(0);
                String ans = ansFile.readPathAnswer();
                String ansf = ans.replaceAll("\\s+", "");
                String arb = Character.toString(charAt);
                String opta = arb.replaceAll("\\s+", "");
                String Fscore = score.getText();
                if (ansf.equalsIgnoreCase(opta)) {
                    int marka = 1;
                    if (Fscore == null) {
                        strmark = Integer.toString(marka);
                        score.setText(strmark);
                    }
                    if (Fscore != null) {
                        int getmark;
                        int updatedmark;
                        getmark = Integer.parseInt(Fscore);
                        updatedmark = getmark + marka;
                        strmark = Integer.toString(updatedmark);
                        score.setText(strmark);
                    }
                } else {
                    ans_label.setVisible(true);
                    sec_label.setVisible(true);
                    exp_label.setVisible(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == opt_b) {
            try {
                String b;
                b = opt_b.getText();
                char charAt = b.charAt(0);
                String ans = ansFile.readPathAnswer();
                String ansf = ans.replaceAll("\\s+", "");
                String brb = Character.toString(charAt);
                String optb = brb.replaceAll("\\s+", "");
                String Fscore = score.getText();
                if (ansf.equalsIgnoreCase(optb)) {
                    int markb = 1;
                    if (Fscore == null) {
                        strmark = Integer.toString(markb);
                        score.setText(strmark);
                    }
                    if (Fscore != null) {
                        int getmark;
                        int updatedmark;
                        getmark = Integer.parseInt(Fscore);
                        updatedmark = getmark + markb;
                        strmark = Integer.toString(updatedmark);
                        score.setText(strmark);
                    }
                } else {
                    ans_label.setVisible(true);
                    sec_label.setVisible(true);
                    exp_label.setVisible(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == opt_c) {
            try {
                String c;
                c = opt_c.getText();
                char charAt = c.charAt(0);
                String ans = ansFile.readPathAnswer();
                String ansf = ans.replaceAll("\\s+", "");
                String crb = Character.toString(charAt);
                String optc = crb.replaceAll("\\s+", "");
                String Fscore = score.getText();
                if (ansf.equalsIgnoreCase(optc)) {
                    int markc = 1;
                    if (Fscore == null) {
                        strmark = Integer.toString(markc);
                        score.setText(strmark);
                    }
                    if (Fscore != null) {
                        int getmark;
                        int updatedmark;
                        getmark = Integer.parseInt(Fscore);
                        updatedmark = getmark + markc;
                        strmark = Integer.toString(updatedmark);
                        score.setText(strmark);
                    }
                } else {
                    ans_label.setVisible(true);
                    sec_label.setVisible(true);
                    exp_label.setVisible(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == opt_d) {
            try {
                String d;
                d = opt_d.getText();
                char charAt = d.charAt(0);
                String ans = ansFile.readPathAnswer();
                String ansf = ans.replaceAll("\\s+", "");
                String drb = Character.toString(charAt);
                String optd = drb.replaceAll("\\s+", "");
                String Fscore = score.getText();
                if (ansf.equalsIgnoreCase(optd)) {
                    int markd = 1;
                    if (Fscore == null) {
                        strmark = Integer.toString(markd);
                        score.setText(strmark);
                    }
                    if (Fscore != null) {
                        int getmark;
                        int updatedmark;
                        getmark = Integer.parseInt(Fscore);
                        updatedmark = getmark + markd;
                        strmark = Integer.toString(updatedmark);
                        score.setText(strmark);
                    }
                } else {
                    ans_label.setVisible(true);
                    sec_label.setVisible(true);
                    exp_label.setVisible(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(File_Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void resetRadioButton() {
        if (opt_a.isSelected() == false) {
//RESET RADIO BUTTON
            group.clearSelection();
        }
        if (opt_b.isSelected() == false) {
//RESET RADIO BUTTON
            group.clearSelection();
        }
        if (opt_c.isSelected() == false) {
//RESET RADIO BUTTON
            group.clearSelection();
        }
        if (opt_d.isSelected() == false) {
//RESET RADIO BUTTON
            group.clearSelection();
        }
    }

    void hideAnsPanel() {
//HIDE ANWSER PANEL
        ans_label.setVisible(false);
        sec_label.setVisible(false);
        exp_label.setVisible(false);
    }

    void showAnsPanel() {
//HIDE ANWSER PANEL
        ans_label.setVisible(true);
        sec_label.setVisible(true);
        exp_label.setVisible(true);
    }

    void getScore() {
        String getscore = score.getText();
        String pathtext = "C:\\Users\\Humphrey\\Documents\\NetBeansProjects\\JES\\src\\jes\\Path\\Score.txt";
        try {
            // Java 7
            Files.write(Paths.get(pathtext), getscore.getBytes());
        } catch (IOException ioe) {
        }
    }

    public String resume_exam() throws IOException {
        File readloc = new File(loc);
        boolean fileexisits = readloc.exists();
        String folder = null, nofile;
        if (fileexisits) {
            String resume = pausepath.readPathPause();
            String resume_es = null;

            try {
                bufferedReader = new BufferedReader((new FileReader(readloc)));
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
        return folder;
    }

}
