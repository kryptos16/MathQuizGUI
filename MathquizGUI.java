import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

 class MathQuizGUI {
    private static final int NUM_QUESTIONS = 5;
    private int score = 0;
    private int questionCount = 0;
    private int correctAnswer;
    private final Random random = new Random();
    private final JFrame frame;
    private final JLabel questionLabel;
    private final JTextField answerField;
    private final JButton submitButton;
    private final JLabel resultLabel;

    public MathQuizGUI() {
        frame = new JFrame("Math Quiz");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));
        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        answerField = new JTextField();
        submitButton = new JButton("Submit");
        resultLabel = new JLabel("", SwingConstants.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        
        frame.add(questionLabel);
        frame.add(answerField);
        frame.add(submitButton);
        frame.add(resultLabel);
        
        generateQuestion();
        
        frame.setVisible(true);
    }

    private void generateQuestion() {
        if (questionCount >= NUM_QUESTIONS) {
            resultLabel.setText("Quiz Over! Your final score: " + score + "/" + NUM_QUESTIONS);
            submitButton.setEnabled(false);
            return;
        }

        int num1 = random.nextInt(10) + 1;
        int num2 = random.nextInt(10) + 1;
        char operator = getRandomOperator();
        correctAnswer = calculateAnswer(num1, num2, operator);
        
        questionLabel.setText("What is " + num1 + " " + operator + " " + num2 + "?");
        answerField.setText("");
        questionCount++;
    }

    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerField.getText());
            if (userAnswer == correctAnswer) {
                score++;
                resultLabel.setText("Correct!");
            } else {
                resultLabel.setText("Wrong! The correct answer was " + correctAnswer);
            }
            generateQuestion();
        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter a valid number.");
        }
    }

    private char getRandomOperator() {
        char[] operators = {'+', '-', '*'};
        return operators[random.nextInt(operators.length)];
    }

    private int calculateAnswer(int num1, int num2, char operator) {
        return switch (operator) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MathQuizGUI::new);
    }
}
