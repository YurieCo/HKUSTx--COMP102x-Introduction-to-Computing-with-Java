
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;





import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import comp102x.Canvas;
import comp102x.ColorImage;

/**
 * This is the main game class
 */
public class Hangman implements KeyListener
{
    //constants
    private final char UNKNOWN_CHARACTER = '?'; //this is the symbol used for unknown characters
    private final int MAX_WORD_LIST_SIZE = 128; //this is the maximum number of words that can be loaded and stored in our word list array
    private final int STARTING_LIFE = 6; //this it the starting player's life at the beginning of the game
    
    private Canvas canvas; // canvas for hangman display
    private JTextArea inputArea; // input area for hangman
    private int life; // player's life
    private String secretWord; // the random secret word
    private String displayWord; // the initial display word which characters are all unknown
    private boolean gameOver; // flag indicating whether the game is over
    
    public Hangman() throws IOException {

        // create the canvas for the hangman display
        canvas = new Canvas(400, 400);
        
        // setup the input area
        inputArea = new JTextArea();
        inputArea.addKeyListener(this);
        inputArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) inputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(inputArea));
        panel.setBorder(BorderFactory.createEtchedBorder());
        canvas.setComponentAtBottom(panel);

        // initialize the player's life
        life = STARTING_LIFE;

        // create the word list array
        String[] wordList = new String[MAX_WORD_LIST_SIZE];

        // read the word list from the file and store the words in wordList
        int wordListSize = readWordList("wordList.txt", wordList);

        // pick a random secret word from the word list
        secretWord = pickSecretWord(wordList, wordListSize);
        
        // initialize the game over flag 
        gameOver = false;
    }
    
    /**
     * The entry point of the program
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        //create a new instance of the game
        Hangman game = new Hangman();
        
        //run the main game loop
        game.runGameLoop();
    }
    
    /**
     * The main game loop
     */
    public void runGameLoop()
    {
        //construct the initial display word which characters are all unknown
        displayWord = constructInitialDisplayWord(secretWord);
        
        //show the guessed letters so far and draw the hangman
        showProgress(displayWord, life, canvas);
        
        //ask for the letter guess
        inputArea.append("Guess a letter: ");
        
        while(!isGameOver())
        {
            
        }
        
        //print an empty line
        inputArea.append("\n");
        
        //show the game result
        if(life == 0)  inputArea.append("You failed... :(\n");
        else  inputArea.append("You guessed the word correctly! Well done! :)\n");
    }
    
    public synchronized boolean isGameOver() {
        
        return gameOver;
    }
    
    public synchronized void setGameOver(boolean gameOver) {
        
        this.gameOver = gameOver;
    }
    
    /**
     * Load the word list from the specified text file and store the words in an array
     * 
     * @param   fileName      the file name of the word list text file
     * @param   wordList      the array for storing the words
     * @return  the number of words loaded from the file 
     * @throws IOException 
     */
    private int readWordList(String fileName, String[] wordList) throws IOException 
    {
        // Please write your code after this line
        File file = new File(fileName);
        int i = 0; // store number of words in a file

            Scanner input = new Scanner(file);
            
            for(; input.hasNextLine();i++){
                wordList[i] = input.nextLine();
            }
            
            input.close();
        
        return i;
    }
    
    /**
     * Pick a secret word randomly from the word list array
     * 
     * @param   wordList      the word list array
     * @param   wordListSize  the size of the word list array
     * @return  a randomly-chosen word from the word list array
     */
    private String pickSecretWord(String[] wordList, int wordListSize)
    {
        // Please write your code after this line
        Random chosen = new Random();
        int a = chosen.nextInt(wordListSize);        
        
        return wordList [ a ];
        
        
    }
    
    /**
     * Contruct the initial display word which contains exactly X UNKNOWN_CHARACTER where X is the number of letters in the secret word
     * 
     * @param   secretWord      the secret word
     * @return  the constructed display word
     */
    private String constructInitialDisplayWord(String secretWord)
    {
        // Please write your code after this line
        
        int length = secretWord.length();
        String space10 = new String(new char[length]).replace('\0', UNKNOWN_CHARACTER); // or stringbuilder myName.setCharAt(4, 'x');
        
        return space10;
        
    }
    
    /**
     * Update the display word - reveal all the occurrences of the guessed character if the guessed character is in the secret word
     * 
     * @param   displayWord     the current display word
     * @param   secretWord      the secret word
     * @param   guess           the guessed character
     * @return  the updated display word
     */
    private String updateDisplayWord(String displayWord, String secretWord, char guess)
    {
        // Please write your code after this line
        int le = secretWord.length();
        String temp = new String(new char[le]);
        
        for(int i = 0; i < le;i++)
        if (secretWord.charAt(i) == guess) 
             temp = secretWord.substring(i,i);

        
        
        
        return temp;
    }
    
    /**
     * Check if the game is over: either the display word is the same as the secret word, or that the life is 0
     * 
     * @param   displayWord     the current display word
     * @param   secretWord      the secret word
     * @param   life            the player's life
     * @return  whether the game is over
     */
    private boolean checkGameOver(String displayWord, String secretWord, int life)
    {
        // Please write your code after this line
        
        if ( displayWord.equals(secretWord) || life == 0)
        return true;
        
        return false;
    }
    
    /**
     * Check if the display word has been changed, i.e., different from its original version
     * 
     * @param   displayWord              the current display word
     * @param   originalDisplayWord      the original dislpay word
     * @return  whether the display word has been changed
     */
    private boolean checkIfDisplayWordChanged(String originalDisplayWord, String displayWord)
    {
        return !originalDisplayWord.equals(displayWord);
    }
    
    /**
     * Check if the display word has been changed, i.e., different from its original version
     * 
     * @param   displayWord     the current display word
     * @param   life            the player's life
     * @param   canavs          the canvas to draw on
     */
    private void showProgress(String displayWord, int life, Canvas canvas)
    {
        //print a new line
        inputArea.append("\n");
        
        //show the display word
        inputArea.append("The word: " + displayWord + "\n");
        
        //clear the canvas
        canvas.removeAll();
        
        //draw the hangman image that represents the current life
        ColorImage image = new ColorImage("images/life" + life + ".png");
        image.setMovable(false);
        canvas.add(image);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
        
        if (isGameOver())
        {
            return;
        }
        
        else
        {
            //obtain a guess from the player
            char guess = e.getKeyChar();
            
            // check if the input is valid
            if ((e.getKeyChar() < 'A' || e.getKeyChar() > 'Z') && (e.getKeyChar() < 'a' || e.getKeyChar() > 'z')) {
                return;
            }
            
            // display it on the text box
            inputArea.append(("" + guess).toLowerCase() + "\n");
            
            //update the display word accordingly
            String originalDisplayWord = displayWord; //save the original display word before the update
            displayWord = updateDisplayWord(displayWord, secretWord, guess); //now update the display word
            if(checkIfDisplayWordChanged(originalDisplayWord, displayWord)) //if any update is made to the display word, the guess is correct
            {
                inputArea.append("Good job.\n");
            }
            else //otherwise, the guess is incorrect
            {
                inputArea.append("Try another.\n");
                life--; //decrease the life by 1
            }
            
            //show the guessed letters so far and draw the hangman
            showProgress(displayWord, life, canvas);
            
            //check if the game is over now
            setGameOver(checkGameOver(displayWord, secretWord, life));
            
            if (!isGameOver())
                inputArea.append("Guess a letter: ");
        }
    }
}
