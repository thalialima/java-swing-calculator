package org.calculator;

import java.awt.*;

public class Main {
    private static final String TITLE = "Java Swing Calculator";

    public static void main(String[] args) {
        setCalculator();
    }

    private static void setCalculator() {
        Calculator calculator = new Calculator();
        Container container = calculator.getContentPane();

        container.setLayout(new BorderLayout());
        calculator.setTitle(TITLE);
        calculator.setPreferredSize(new Dimension(800, 600));
        calculator.pack();
        calculator.setLocation(400, 250);
        calculator.setVisible(true);
        calculator.setResizable(true);
    }
}