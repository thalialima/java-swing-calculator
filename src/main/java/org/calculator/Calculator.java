package org.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class Calculator extends JFrame implements ActionListener {

    // variables
    final int MAX_INPUT_LENGTH = 20;
    final int INPUT_MODE = 0;
    final int RESULT_MODE = 1;
    final int ERROR_MODE = 2;
    int displayMode;

    boolean clearOnNextDigit;
    boolean percent;
    double lastNumber;
    String lastOperator;

    private JMenu jMenuFile;
    private JMenu jMenuHelp;
    private JMenuItem jMenuItemExit;
    private JMenuItem jMenuItemAbout;

    private JLabel jLabelOutPut;
    private JButton jButtonButtons[];
    private JPanel jPanelMaster;
    private JPanel jPanelBackSpace;
    private JPanel jPanelControl;

    /*
     * Font(String name, int style, int size)
     * Creates a new font from the specified name, style and point size
     */

    Font notoMono16 = new Font("NotoMono", Font.PLAIN, 16);
    Font notoMono16Bold = new Font("NotoMono", Font.BOLD, 16);

    // constructor
    public Calculator() {
        /*
         * Set up the JMenuBar;
         * Define Mnemonics;
         * Provide some JMenuItem components with keyboard Accelerators.
         */

        // jMenuFile
        jMenuFile = new JMenu("File");
        jMenuFile.setFont(notoMono16Bold);
        jMenuFile.setMnemonic(KeyEvent.VK_F);

        jMenuItemExit = new JMenuItem("Exit");
        jMenuItemExit.setFont(notoMono16);
        jMenuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        jMenuFile.add(jMenuItemExit);

        // jMenuHelp
        jMenuHelp = new JMenu("Help");
        jMenuHelp.setFont(notoMono16);
        jMenuHelp.setMnemonic(KeyEvent.VK_H);

        jMenuItemAbout = new JMenuItem("About Calculator");
        jMenuItemAbout.setFont(notoMono16);

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


    }


    private void setFrameLayoutManager() {
        setBackground(Color.WHITE);

        jPanelMaster = new JPanel();
        jLabelOutPut = new JLabel("0");
        jLabelOutPut.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabelOutPut.setBackground(Color.GRAY);
        jLabelOutPut.setOpaque(true);
    }

    private void addComponentsToFrame() {

        final List<String> OPERATORS = Arrays.asList("+/-", ".", "=", "/", "*", "-", "+", "sqrt", "1/x", "%");

        getContentPane().add(jLabelOutPut, BorderLayout.NORTH);

        JPanel jPanelButtons = new JPanel(); //container for jButtonButtons

        // create numeric JButtons
        for (int i = 0; i < 10; i++) {
            // set each JButton to the value of index
            jButtonButtons[i] = new JButton(String.valueOf(1));
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
        jLabelOutPut.setText("0"); //set display string
        lastOperator = "0";
        lastNumber = 0;
        displayMode = INPUT_MODE;
        clearOnNextDigit = true;
    }
}
