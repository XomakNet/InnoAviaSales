package innopolis.project.e4.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FirstWindow extends JFrame {
    private FirstWindow() {
        setTitle("AviaSales FLIK");
        this.setBounds(300,200,500,350);
        this.setResizable(false);

        JTabbedPane mainWindow = new JTabbedPane();
        getContentPane().add(mainWindow);
        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel();

        mainWindow.addTab("Окно выбора полётиков №1", tab1);
        mainWindow.addTab("Окно выбора полётиков №2", tab2);

        JTextField inputFrom = new JTextField("", 25);
        JLabel labelInputFrom = new JLabel("From:");
        JTextField inputTo = new JTextField("", 25);
        JLabel labelInputTo = new JLabel("To:");
        JTextField inputDateDay  = new JTextField("", 3);
        JTextField inputDateMonth  = new JTextField("", 3);
        JTextField inputDateYear  = new JTextField("", 3);
        JLabel labelInputDate = new JLabel("Input date (dd/mm/yy):");
        JRadioButton radio1 = new JRadioButton("Oneway ticket");
        JRadioButton radio2 = new JRadioButton("Round trip");
        JCheckBox check = new JCheckBox("Business class", false);
        JButton button = new JButton("Find trip!");

//        tab1.setLayout(new GridLayout(0, 2, 1, 1));  // standard layout
        tab1.setLayout(null);

        Insets insets = tab1.getInsets();
        Dimension size = button.getPreferredSize();
        button.setBounds(170 + insets.left, 200 + insets.top,
                size.width+80, size.height+65);

        size = inputFrom.getPreferredSize();
        inputFrom.setBounds(95 + insets.left, 40 + insets.top,
                size.width, size.height);

        size = labelInputFrom.getPreferredSize();
        labelInputFrom.setBounds(40 + insets.left, 40 + insets.top,
                size.width, size.height);

        size = inputTo.getPreferredSize();
        inputTo.setBounds(95 + insets.left, 80 + insets.top,
                size.width, size.height);

        size = labelInputTo.getPreferredSize();
        labelInputTo.setBounds(40 + insets.left, 80 + insets.top,
                size.width, size.height);

        size = labelInputDate.getPreferredSize();
        labelInputDate.setBounds(40 + insets.left, 120 + insets.top,
                size.width, size.height);

        size = inputDateDay.getPreferredSize();
        inputDateDay.setBounds(180 + insets.left, 120 + insets.top,
                size.width, size.height);

        size = inputDateMonth.getPreferredSize();
        inputDateMonth.setBounds(220 + insets.left, 120 + insets.top,
                size.width, size.height);

        size = inputDateYear.getPreferredSize();
        inputDateYear.setBounds(260 + insets.left, 120 + insets.top,
                size.width, size.height);

        size = radio1.getPreferredSize();
        radio1.setBounds(40 + insets.left, 150 + insets.top,
                size.width, size.height);

        size = radio2.getPreferredSize();
        radio2.setBounds(180 + insets.left, 150 + insets.top,
                size.width, size.height);

        size = check.getPreferredSize();
        check.setBounds(40 + insets.left, 180 + insets.top,
                size.width, size.height);


        tab1.add(labelInputDate);
        tab1.add(inputDateDay);
        tab1.add(inputDateMonth);
        tab1.add(inputDateYear);
        tab1.add(labelInputFrom);
        tab1.add(inputFrom);
        tab1.add(labelInputTo);
        tab1.add(inputTo);
        tab1.add(radio1);
        tab1.add(radio2);
        tab1.add(check);
        tab1.add(button);
        radio1.setSelected(true);



        class ButtonEventListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String message = "";
                message += "We have found best flight for You!\n";
                message += "Date of your departure: " + inputDateDay.getText() + "\n";
                message += (radio1.isSelected() ? "Oneway ticket " : "Round trip ticket ")
                        + "was chosen.\n";
                message += ((check.isSelected())
                        ? "Business class." : "Econom class.");
                JOptionPane.showMessageDialog(null,
                        message,
                        "Flight chosen!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }

        button.addActionListener(new ButtonEventListener());
    }


    public static void main(String[] args) {
        FirstWindow tp = new FirstWindow();
        tp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tp.setVisible(true);


    }

}


/*
package innopolis.project.e4.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow extends JFrame {
    private JButton button = new JButton("Find trip!");

    private JTextField inputDate = new JTextField("", 5);
    private JLabel labelInputDate = new JLabel("Input date:");

    private JTextField inputFrom = new JTextField("", 5);
    private JLabel labelInputFrom = new JLabel("From:");

    private JTextField inputTo = new JTextField("", 5);
    private JLabel labelInputTo = new JLabel("To:");

    private JRadioButton radio1 = new JRadioButton("Oneway ticket");
    private JRadioButton radio2 = new JRadioButton("Round trip");

    private JCheckBox check = new JCheckBox("Business class", false);


    private class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = "";
            message += "We have found best flight for You!\n";
            message += "Date of your departure: " + inputDate.getText() + "\n";
            message += (radio1.isSelected() ? "Oneway ticket " : "Round trip ticket ")
                    + "was chosen.\n";
            message += ((check.isSelected())
                    ? "Business class." : "Econom class.");
            JOptionPane.showMessageDialog(null,
                    message,
                    "Flight chosen!",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    private FirstWindow() {

        super("AviaSales");
        this.setBounds(100,100,800,400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(6, 8, 1, 1));

        container.add(labelInputFrom);
        container.add(inputFrom);

        container.add(labelInputTo);
        container.add(inputTo);

        container.add(labelInputDate);
        container.add(inputDate);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        container.add(radio1);

        radio1.setSelected(true);
        container.add(radio2);
        container.add(check);
        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    public static void main(String[] args) {
        FirstWindow app = new FirstWindow();
        app.setVisible(true);
    }
}

*/
