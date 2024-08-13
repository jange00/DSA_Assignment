package Question2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class q_no_2a_Calculator extends JFrame {

    private JTextField displayField;
    private JLabel resultLabel;

    // Constructor to initialize components
    public q_no_2a_Calculator() {
        initComponents();
    }

    // Initialize GUI components
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(" BasicCalculator");
        setLayout(new BorderLayout());

        // Create the display field for user input
        displayField = new JTextField();
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false); // Make it read-only to avoid direct text editing
        displayField.setBackground(Color.BLACK);
        displayField.setForeground(Color.WHITE);
        displayField.setFont(new Font("Arial", Font.BOLD, 18));

        resultLabel = new JLabel("Result: ");
        resultLabel.setHorizontalAlignment(JLabel.RIGHT);
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBackground(Color.BLACK);
        resultLabel.setOpaque(true);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create buttons
        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(String.valueOf(i), Color.DARK_GRAY, "Number " + i);
            numberButtons[i].addActionListener(new NumberButtonListener());
        }

        JButton addButton = createButton("+", Color.ORANGE, "Add");
        JButton subButton = createButton("-", Color.ORANGE, "Subtract");
        JButton mulButton = createButton("*", Color.ORANGE, "Multiply");
        JButton divButton = createButton("/", Color.ORANGE, "Divide");
        JButton clearButton = createButton("C", Color.LIGHT_GRAY, "Clear");
        JButton equalsButton = createButton("=", Color.RED, "Calculate");
        JButton openParenButton = createButton("(", Color.ORANGE, "Open Parenthesis");
        JButton closeParenButton = createButton(")", Color.ORANGE, "Close Parenthesis");
        JButton backButton = createButton("←", Color.LIGHT_GRAY, "Backspace");

        // Action listeners for operator buttons
        addButton.addActionListener(new OperatorButtonListener());
        subButton.addActionListener(new OperatorButtonListener());
        mulButton.addActionListener(new OperatorButtonListener());
        divButton.addActionListener(new OperatorButtonListener());
        openParenButton.addActionListener(new OperatorButtonListener());
        closeParenButton.addActionListener(new OperatorButtonListener());

        // Action listener for clear button
        clearButton.addActionListener(e -> {
            displayField.setText("");
            resultLabel.setText("Result: ");
        });
        
        // Action listener for back button
        backButton.addActionListener(e -> {
            String text = displayField.getText();
            if (text.length() > 0) {
                displayField.setText(text.substring(0, text.length() - 1));
            }
        });
        
        // Action listener for equals button
        equalsButton.addActionListener(e -> {
            try {
                String infixExpression = displayField.getText();
                if (validateExpression(infixExpression)) {
                    infixExpression = insertImplicitMultiplication(infixExpression);
                    String postfixExpression = infixToPostfix(infixExpression);
                    double result = evaluatePostfix(postfixExpression);
                    resultLabel.setText("Result: " + result);
                } else {
                    resultLabel.setText("Error: Invalid Expression");
                }
            } catch (Exception ex) {
                resultLabel.setText("Error: Invalid Expression");
            }
        });

        // Layout for display and result
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayPanel.add(displayField, BorderLayout.CENTER);
        displayPanel.add(resultLabel, BorderLayout.SOUTH);

        // Layout for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add buttons to panel
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        buttonPanel.add(openParenButton);
        buttonPanel.add(closeParenButton);
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(divButton);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(mulButton);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(subButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(new JLabel("")); // Empty cell for alignment
        buttonPanel.add(equalsButton);
        buttonPanel.add(addButton);

        add(displayPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Set the size and location of the frame
        setSize(400, 450);
        setLocationRelativeTo(null);
    }

    // Create button with specified text, background color, and tooltip
    private JButton createButton(String text, Color backgroundColor, String tooltip) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK); // Set the text color to white
        button.setFont(new Font("Arial", Font.BOLD, 20)); // Increase the font size
        button.setToolTipText(tooltip);
        button.setFocusPainted(false); // Remove focus border
        button.setPreferredSize(new Dimension(60, 60)); // Set a consistent size for buttons
        return button;
    }
    

    // Action listener for number buttons
    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            displayField.setText(displayField.getText() + source.getText());
        }
    }

    // Action listener for operator buttons
    private class OperatorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            displayField.setText(displayField.getText() + " " + source.getText() + " ");
        }
    }

    // Handle implicit multiplication in the expression
    private String insertImplicitMultiplication(String expression) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);
            if (i > 0 && (current == '(' || Character.isDigit(current)) &&
                    (expression.charAt(i - 1) == ')' || Character.isDigit(expression.charAt(i - 1)))) {
                result.append('*');
            }
            result.append(current);
        }
        return result.toString();
    }

    // Convert infix expression to postfix
    private String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);
            if (Character.isDigit(token)) {
                number.append(token);
            } else {
                if (number.length() > 0) {
                    result.append(number).append(' ');
                    number.setLength(0);
                }
                if (token == ' ') {
                    continue;
                } else if (token == '(') {
                    stack.push(token);
                } else if (token == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        result.append(stack.pop()).append(' ');
                    }
                    stack.pop(); // Pop the '('
                } else if (isOperator(token)) {
                    while (!stack.isEmpty() && hasPrecedence(token, stack.peek())) {
                        result.append(stack.pop()).append(' ');
                    }
                    stack.push(token);
                }
            }
        }

        if (number.length() > 0) {
            result.append(number).append(' ');
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(' ');
        }

        return result.toString();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    // Evaluate postfix expression
    private double evaluatePostfix(String expression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                double b = stack.pop();
                double a = stack.pop();
                double result = applyOp(token.charAt(0), a, b);
                stack.push(result);
            }
        }

        return stack.pop();
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double applyOp(char op, double a, double b) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }

    // Validate the input expression
    private boolean validateExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");

        if (!expression.matches("[0-9+\\-*/()]*")) {
            return false;
        }

        int balance = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
            if (balance < 0) {
                return false;
            }
        }
        if (balance != 0) {
            return false;
        }

        if (expression.matches(".*[+*/]{2,}.*") || expression.matches(".*\\(\\).*")) {
            return false;
        }

        return true;
    }

    // Main method to run the application
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new q_no_2a_Calculator().setVisible(true));
    }
}