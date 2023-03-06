package org.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class Calculator extends JFrame implements ActionListener {

    //constants
    private static final String ABOUT_INFO = " About Java Swing Calculator";
    private static final String ABOUT_CALCULATOR = "About Calculator";
    private static final String FILE = "File";
    private static final String EXIT = "Exit";
    private static final String HELP = "Help";
    private static final List<String> OPERATORS = Arrays.asList("+/-", ".", "=", "/", "*", "-", "+", "sqrt", "1/x", "%");
    private static final String DIVISION_OPERATOR = "/";
    private static final String MULTIPLICATION_OPERATOR = "*";
    private static final String SUBTRACTION_OPERATOR = "-";
    private static final String SUM_OPERATOR = "+";
    private static final String ZERO = "0";
    private static final int MAX_INPUT_LENGTH = 20;
    private static final int INPUT_MODE = 0;
    private static final int RESULT_MODE = 1;
    private static final int ERROR_MODE = 2;

    // swing constants
    private final JMenu jMenuFile;
    private final JMenu jMenuHelp;
    private final JMenuItem jMenuItemExit;
    private final JMenuItem jMenuItemAbout;


    // variables
    int displayMode;
    boolean clearOnNextDigit;
    boolean percent;
    double lastNumber;
    String lastOperator;

    // swing variables
    private JLabel jLabelOutPut;
    private JButton[] jButtonButtons;
    private JPanel jPanelMaster;
    private JPanel jPanelBackSpace;
    private JPanel jPanelControl;

    /*
     * Font(String name, int style, int size)
     * Creates a new font from the specified name, style and point size
     */

    Font notoMono20 = new Font("NotoMono", Font.PLAIN, 20);
    Font notoMono20Bold = new Font("NotoMono", Font.BOLD, 20);

    // constructor
    public Calculator() {
        /*
         * Set up the JMenuBar;
         * Define Mnemonics;
         * Provide some JMenuItem components with keyboard Accelerators.
         */

        // jMenuFile
        jMenuFile = new JMenu(FILE);
        jMenuFile.setFont(notoMono20Bold);
        jMenuFile.setMnemonic(KeyEvent.VK_F);

        jMenuItemExit = new JMenuItem(EXIT);
        jMenuItemExit.setFont(notoMono20);
        jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        jMenuFile.add(jMenuItemExit);

        // jMenuHelp
        jMenuHelp = new JMenu(HELP);
        jMenuHelp.setFont(notoMono20);
        jMenuHelp.setMnemonic(KeyEvent.VK_H);

        jMenuItemAbout = new JMenuItem(ABOUT_CALCULATOR);
        jMenuItemAbout.setFont(notoMono20);

        jMenuHelp.add(jMenuItemAbout);

        // jMenuBar
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuHelp);
        setJMenuBar(jMenuBar);

        // set frame layout manager
        setFrameLayoutManager();
        addComponentsToFrame();
        activateActionListener();

    } // end of constructor calculator


    @Override
    public void actionPerformed(ActionEvent e) {
        double result = 0;

        if (e.getSource() == jMenuItemAbout) {
            JDialog jDialogAbout = new CustomAboutDialog(this, ABOUT_INFO, true);
            jDialogAbout.setVisible(true);
        } else if (e.getSource() == jMenuItemExit) {
            System.exit(0);
        }

        //search for the button pressed until end of array or key found
        for (int i = 0; i < jButtonButtons.length; i++) {
            if (e.getSource() == jButtonButtons[i]) {
                if (i < 10) {
                    addDigitToDisplay(i);
                }
                if (i >= 13 && i <= 16) {
                    processOperator(jButtonButtons[i].getText());
                }

                switch (i) {
                    case 10: // +/-
                        processSignChange();
                        break;
                    case 11: // decimal point
                        addDecimalPoint();
                        break;
                    case 12: // =
                        processEquals();
                        break;
                    default:
                        break;

                }

            }
        }


    }


    private void setFrameLayoutManager() {
        setBackground(Color.WHITE);

        jPanelMaster = new JPanel();
        jLabelOutPut = new JLabel(ZERO);
        jLabelOutPut.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabelOutPut.setBackground(Color.GRAY);
        jLabelOutPut.setOpaque(true);
    }

    private void addComponentsToFrame() {

        getContentPane().add(jLabelOutPut, BorderLayout.NORTH);

        jButtonButtons = new JButton[23];

        JPanel jPanelButtons = new JPanel(); //container for jButtonButtons

        // create numeric JButtons
        for (int i = 0; i < 10; i++) {
            // set each JButton to the value of index
            jButtonButtons[i] = new JButton(String.valueOf(i));
        }

        // create operator JButtons
        for (int i = 0; i < 10; i++) {
            // set each JButton to an operator
            jButtonButtons[i + 10] = new JButton(OPERATORS.get(i));
        }

        jPanelBackSpace = new JPanel();
        jPanelBackSpace.setLayout(new GridLayout(1, 1, 2, 2));

        jButtonButtons[20] = new JButton("BackSpace");
        jPanelBackSpace.add((jButtonButtons[20]));

        jPanelControl = new JPanel();
        jPanelControl.setLayout(new GridLayout(1, 2, 2, 2));

        jButtonButtons[21] = new JButton(" CE ");
        jButtonButtons[22] = new JButton("C");

        jPanelControl.add(jButtonButtons[21]);
        jPanelControl.add(jButtonButtons[22]);

        // setting all numbered JButtons to blue
        Arrays.stream(jButtonButtons)
                .limit(10)
                .forEach(jb -> jb.setForeground(Color.BLUE));

        // and the rest to red
        Arrays.stream(jButtonButtons)
                .skip(10)
                .forEach(jb -> jb.setForeground(Color.RED));

        // set jPanelButton layout manager for 4 by 5 grid
        jPanelButtons.setLayout(new GridLayout(4, 5, 2, 2));

        // add buttons to keypad panel starting at top left
        // * first row
        Arrays.stream(jButtonButtons)
                .skip(6)
                .limit(3)
                .forEach(jPanelButtons::add);

        // add button / and sqrt
        jPanelButtons.add(jButtonButtons[13]);
        jPanelButtons.add(jButtonButtons[17]);

        // * second row
        Arrays.stream(jButtonButtons)
                .skip(3)
                .limit(3)
                .forEach(jPanelButtons::add);

        // add button * and x^2
        jPanelButtons.add(jButtonButtons[14]);
        jPanelButtons.add(jButtonButtons[18]);

        // * third row
        Arrays.stream(jButtonButtons)
                .skip(1)
                .limit(3)
                .forEach(jPanelButtons::add);

        // add button - and %
        jPanelButtons.add(jButtonButtons[15]);
        jPanelButtons.add(jButtonButtons[19]);

        // * fourth row
        // add 0, +/-, ., +, and =
        jPanelButtons.add(jButtonButtons[0]);
        jPanelButtons.add(jButtonButtons[10]);
        jPanelButtons.add(jButtonButtons[11]);
        jPanelButtons.add(jButtonButtons[16]);
        jPanelButtons.add(jButtonButtons[12]);

        setJPanelMaster(jPanelButtons);
    }

    private void setJPanelMaster(JPanel jPanelButtons) {
        jPanelMaster.setLayout(new BorderLayout());
        jPanelMaster.add(jPanelBackSpace, BorderLayout.WEST);
        jPanelMaster.add(jPanelControl, BorderLayout.EAST);
        jPanelMaster.add(jPanelButtons);

        // add components to frame
        getContentPane().add(jPanelMaster, BorderLayout.SOUTH);
        requestFocus();
    }

    private void activateActionListener() {
        Arrays.stream(jButtonButtons)
                .forEach(jb -> jb.addActionListener(this));
        jMenuItemAbout.addActionListener(this);
        jMenuItemExit.addActionListener(this);

        clearAll();

        // add window listener for closing frame and ending program
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void clearAll() {
        setDisplayString(ZERO); //set display string
        lastOperator = ZERO;
        lastNumber = 0;
        displayMode = INPUT_MODE;
        clearOnNextDigit = true;
    }

    private void addDigitToDisplay(int digit) {
        if (clearOnNextDigit) setDisplayString("");

        String inputString = jLabelOutPut.getText();

        if (inputString.indexOf(ZERO) == 0) {
            inputString = inputString.substring(1);
        }

        if ((!ZERO.equals(inputString) || digit > 0) && inputString.length() < MAX_INPUT_LENGTH) {
            setDisplayString(inputString + digit);
        }

        displayMode = INPUT_MODE;
        clearOnNextDigit = false;
    }

    private void processOperator(String operator) {
        if (displayMode != ERROR_MODE) {
            double numberInDisplay = getNumberInDisplay();
            if (!ZERO.equals(lastOperator)) {
                try {
                    double result = processLastOperator();
                    displayResult(result);
                } catch (UnsupportedOperationException e) {
                    displayError(e.getMessage());
                }
            } else {
                lastNumber = numberInDisplay;
            }

            clearOnNextDigit = true;
            lastOperator = operator;
        }
    }

    private double processLastOperator() throws UnsupportedOperationException {
        double numberInDisplay = getNumberInDisplay();

        switch (lastOperator) {
            case DIVISION_OPERATOR:
                if (numberInDisplay == 0)
                    throw new UnsupportedOperationException("Division by zero is not allowed in mathematics!");
                return lastNumber / numberInDisplay;
            case MULTIPLICATION_OPERATOR:
                return lastNumber / numberInDisplay;
            case SUBTRACTION_OPERATOR:
                return lastNumber - numberInDisplay;
            case SUM_OPERATOR:
                return lastNumber + numberInDisplay;
            default:
                return 0;
        }
    }

    private double getNumberInDisplay() {
        return Double.parseDouble(jLabelOutPut.getText());
    }

    private void displayResult(double result) {
        setDisplayString(Double.toString(result));
        lastNumber = result;
        displayMode = RESULT_MODE;
        clearOnNextDigit = true;
    }

    private void displayError(String errorMessage) {
        setDisplayString(errorMessage);
        lastNumber = 0;
        displayMode = RESULT_MODE;
        clearOnNextDigit = true;
    }

    private void processSignChange() {
        if (displayMode == INPUT_MODE) {
            String input = getDisplayStrig();

            if (input.length() > 0 && !ZERO.equals(input)) {
                if (input.indexOf("-") == 0) {
                    setDisplayString(input.substring(1));
                } else {
                    setDisplayString("-" + input);
                }
            } else if (displayMode == RESULT_MODE) {
                double numberInDisplay = getNumberInDisplay();
                if (numberInDisplay != 0) displayResult(-numberInDisplay);
            }
        }
    }

    private void addDecimalPoint() {
        displayMode = INPUT_MODE;

        if (clearOnNextDigit) setDisplayString("");
        String inputString = getDisplayStrig();

        // if the input string already has a decimal point, don't do anything
        if (!inputString.contains(".")) setDisplayString(inputString + ".");
    }

    private void processEquals() {
        double result;

        if (displayMode != ERROR_MODE) {
            try {
                result = processLastOperator();
                displayResult(result);
            } catch (UnsupportedOperationException e) {
                displayError(e.getMessage());
            }

            lastOperator = ZERO;
        }
    }

    private void setDisplayString(String string) {
        jLabelOutPut.setText(string);
    }

    private String getDisplayStrig() {
        return jLabelOutPut.getText();
    }


}
