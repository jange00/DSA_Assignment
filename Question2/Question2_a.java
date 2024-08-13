/*
 * You are tasked with implementing a basic calculator with a graphical user interface (GUI) in Java. The calculator 
should be able to evaluate valid mathematical expressions entered by the user and display the result on the GUI.
Specifications:
1. Create a Java GUI application named "BasicCalculatorGUI" that extends JFrame.
2. The GUI should contain the following components:
• A JTextField for the user to input the mathematical expression.
• A JButton labeled "Calculate" that triggers the evaluation of the expression.
• A JLabel to display the result of the calculation.
3. When the "Calculate" button is clicked, the application should:
• Retrieve the expression entered by the user from the text field.
• Evaluate the expression using a custom algorithm (without using any built-in function that evaluates strings as 
mathematical expressions, such as eval()).
• Display the result of the evaluation on the label.
4. The calculator should support basic arithmetic operations, including addition, subtraction, multiplication, and 
division. It should also handle parentheses for grouping expressions.
5. The GUI should have an appropriate layout and size to ensure usability and aesthetics.
6. Ensure proper error handling and validation of user input. Display error messages if the input expression is invalid 
or cannot be evaluated.
Example 1:
Input: s = "1 + 1"
Output: 2
Example 2:
Input: s = " 2-1 + 2 "
Output: 3
Example 3:
Input: s = "(1+(4+5+2)-3)+(6+8)"
Output: 23
 */

 package Question2;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.util.Stack;
 
 public class Question2_a extends JFrame {
 
     private JTextField displayField;
     private JLabel resultLabel;
 
     // Constructor to initialize components
     public Question2_a() {
         initComponents();
     }
 
     // Initialize GUI components
     private void initComponents() {
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setTitle("Calculator");
         setLayout(new BorderLayout());
         setResizable(false);
 
         // Create the display field for user input
         displayField = new JTextField();
         displayField.setHorizontalAlignment(JTextField.RIGHT);
         displayField.setEditable(false); // Make it read-only to avoid direct text editing
         displayField.setBackground(Color.BLACK);
         displayField.setForeground(Color.WHITE);
         displayField.setFont(new Font("Arial", Font.BOLD, 24));
 
         // Create result label
         resultLabel = new JLabel("Result: ");
         resultLabel.setHorizontalAlignment(JLabel.RIGHT);
         resultLabel.setForeground(Color.WHITE);
         resultLabel.setBackground(Color.BLACK);
         resultLabel.setOpaque(true);
         resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
 
         // Create buttons
         String[] numberButtonLabels = {"7", "8", "9", "C",
                                        "4", "5", "6", "/",
                                        "1", "2", "3", "*",
                                        "0", ".", "(", ")"};
 
         String[] operatorButtonLabels = {"+", "-", "="};
 
         JPanel buttonPanel = new JPanel();
         buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
         buttonPanel.setBackground(Color.DARK_GRAY);
 
         // Add buttons to panel
         for (String label : numberButtonLabels) {
             JButton button = createButton(label);
             button.addActionListener(new ButtonClickListener());
             buttonPanel.add(button);
         }
 
         // Add operator buttons to panel
         for (String label : operatorButtonLabels) {
             JButton button = createButton(label);
             if (label.equals("=")) {
                 button.setBackground(Color.RED);
                 button.setForeground(Color.WHITE);
                 button.addActionListener(e -> {
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
             } else {
                 button.addActionListener(new ButtonClickListener());
             }
             buttonPanel.add(button);
         }
 
         // Layout for display and result
         JPanel displayPanel = new JPanel();
         displayPanel.setLayout(new BorderLayout());
         displayPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
         displayPanel.setBackground(Color.BLACK);
         displayPanel.add(displayField, BorderLayout.CENTER);
         displayPanel.add(resultLabel, BorderLayout.SOUTH);
 
         add(displayPanel, BorderLayout.NORTH);
         add(buttonPanel, BorderLayout.CENTER);
 
         // Set the size and location of the frame
         setSize(400, 500);
         setLocationRelativeTo(null);
     }
 
     // Create button with specified text
     private JButton createButton(String text) {
         JButton button = new JButton(text);
         button.setFont(new Font("Arial", Font.BOLD, 20));
         button.setPreferredSize(new Dimension(60, 60));
         button.setBackground(Color.GRAY);
         button.setForeground(Color.WHITE);
         button.setFocusPainted(false);
         button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
         return button;
     }
 
     // Action listener for all buttons
     private class ButtonClickListener implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent e) {
             JButton source = (JButton) e.getSource();
             String text = source.getText();
 
             switch (text) {
                 case "C":
                     displayField.setText("");
                     resultLabel.setText("Result: ");
                     break;
                 default:
                     displayField.setText(displayField.getText() + text);
                     break;
             }
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
             if (Character.isDigit(token) || token == '.') {
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
                 } else if (isOperator(token) || token == '^') {
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
         return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
     }
 
     private boolean hasPrecedence(char op1, char op2) {
         if (op2 == '(' || op2 == ')') {
             return false;
         }
         if (op1 == '^' && op2 != '^') {
             return false;
         }
         if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-' || op2 == '^')) {
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
             case '^':
                 return Math.pow(a, b);
         }
         return 0;
     }
 
     // Validate the input expression
     private boolean validateExpression(String expression) {
         expression = expression.replaceAll("\\s+", "");
 
         if (!expression.matches("[0-9+\\-*/().^]*")) {
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
 
         if (expression.matches(".*[+*/^]{2,}.*") || expression.matches(".*\\(\\).*")) {
             return false;
         }
 
         if (expression.matches(".*[+*/^]$")) {
             return false;
         }
 
         return true;
     }
 
     // Main method to run the application
     public static void main(String[] args) {
         EventQueue.invokeLater(() -> new Question2_a().setVisible(true));
     }
 }
 