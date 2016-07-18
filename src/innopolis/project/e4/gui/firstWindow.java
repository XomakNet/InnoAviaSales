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

