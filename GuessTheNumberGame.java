import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGame extends JFrame implements ActionListener {
    private JLabel promptLabel, feedbackLabel, scoreLabel, attemptsLabel;
    private JTextField guessField;
    private JButton guessButton, newRoundButton;
    private int randomNumber, attemptsLeft, score, round;
    private final int MAX_ATTEMPTS = 10;

    public GuessTheNumberGame() {
        setTitle("Guess The Number Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize game variables
        round = 1;
        score = 0;

        // Setup layout
        setLayout(new GridLayout(6, 1));

        promptLabel = new JLabel("Round " + round + ": Guess a number between 1 and 100", SwingConstants.CENTER);
        add(promptLabel);

        guessField = new JTextField(); // Ensure guessField is initialized before use
        add(guessField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(this);
        add(guessButton);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        add(feedbackLabel);

        attemptsLabel = new JLabel("Attempts left: " + MAX_ATTEMPTS, SwingConstants.CENTER);
        add(attemptsLabel);

        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        add(scoreLabel);

        newRoundButton = new JButton("Start New Round");
        newRoundButton.addActionListener(this);
        newRoundButton.setEnabled(false);
        add(newRoundButton);

        startNewRound();  // Move this call after GUI components are initialized
    }

    private void startNewRound() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attemptsLeft = MAX_ATTEMPTS;
        guessField.setText("");  // Clear the text field at the start of the round
        feedbackLabel.setText("");
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        promptLabel.setText("Round " + round + ": Guess a number between 1 and 100");
        newRoundButton.setEnabled(false);
        guessButton.setEnabled(true);
        guessField.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            makeGuess();
        } else if (e.getSource() == newRoundButton) {
            round++;
            startNewRound();
        }
    }

    private void makeGuess() {
        String guessText = guessField.getText().trim();
        if (guessText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number!");
            return;
        }

        try {
            int guess = Integer.parseInt(guessText);

            if (guess < 1 || guess > 100) {
                JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 100!");
                return;
            }

            attemptsLeft--;

            if (guess == randomNumber) {
                feedbackLabel.setText("Correct! You've guessed the number.");
                updateScore();
                endRound();
            } else if (guess < randomNumber) {
                feedbackLabel.setText("Too low! Try again.");
            } else {
                feedbackLabel.setText("Too high! Try again.");
            }

            attemptsLabel.setText("Attempts left: " + attemptsLeft);

            if (attemptsLeft == 0 && guess != randomNumber) {
                feedbackLabel.setText("No more attempts! The correct number was " + randomNumber);
                endRound();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }
    }

    private void updateScore() {
        score += attemptsLeft * 10; // Points based on remaining attempts
        scoreLabel.setText("Score: " + score);
    }

    private void endRound() {
        guessButton.setEnabled(false);
        guessField.setEnabled(false);
        newRoundButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuessTheNumberGame game = new GuessTheNumberGame();
            game.setVisible(true);
        });
    }
}
