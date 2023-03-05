package org.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomAboutDialog extends JDialog implements ActionListener {
    private JButton jButtonOk;

    public CustomAboutDialog(JFrame parent, String title, boolean modal) {
        super(parent, title, modal);
        setBackground(Color.BLACK);

        setPanelsAndTextArea();

        setLocation(408, 270);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Window aboutDialog = e.getOppositeWindow();
                aboutDialog.dispose();
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButtonOk) {
            this.dispose();
        }
    }

    private void setPanelsAndTextArea() {
        JPanel jPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        StringBuffer text = new StringBuffer();
        text.append("Calculator information\n\n");
        text.append("Developer: Hemanth\n");
        text.append("Version: 2.0");

        JTextArea jTextAreaAbout = new JTextArea(5, 21);
        jTextAreaAbout.setText(text.toString());
        jTextAreaAbout.setFont(new Font("NotoMono", 1, 14));
        jTextAreaAbout.setEditable(false);

        jPanel1.add(jTextAreaAbout);
        jPanel1.setBackground(Color.RED);
        getContentPane().add(jPanel1, BorderLayout.CENTER);

        JPanel jPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jButtonOk = new JButton(" OK ");
        jButtonOk.addActionListener(this);

        jPanel2.add(jButtonOk);
        getContentPane().add(jPanel2, BorderLayout.SOUTH);

    }
}
