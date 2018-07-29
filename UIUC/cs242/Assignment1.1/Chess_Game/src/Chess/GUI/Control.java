package Chess.GUI;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.StartingPosition.NonStandarPositions;
import Chess.StartingPosition.StandardPosition;

import javax.swing.*;

/**
 * Created by jozsef on 2/11/16.
 */
public class Control {
    public static String[] getNames() {
        return names;
    }

    public static int[] getScores() {
        return scores;
    }

    public static void setScores(int[] scores) {
        Control.scores = scores;
    }

    public static void setNames(String[] names) {
        Control.names = names;
    }

    public static String[] getPasswords() {
        return passwords;
    }

    public static void setPasswords(String[] passwords) {
        Control.passwords = passwords;
    }

    private static String[] names;
    private static String[] passwords;
    private static int[] scores;
    private static boolean running = true;
    private static boolean forfeit = false;
    private static int victor = -1;
    public static MainScreen mainScreen;
    private static Game jFrame;

    public static boolean isRunning() {
        return running;
    }


    private int getButtonLocation(int start, int end, int size) {
        int middle = (end + start)/2;
        return middle - size/2;
    }

    public static void main(String args[]) {
        mainScreen = new MainScreen(500, 600);
        scores = new int[10];
        for(int index = 0; index < scores.length; index++){
            scores[index] = 0;
        }
    }

    public static void nonStandardChess(){
        StandardPosition startState = new NonStandarPositions();
        chessGame(startState);
    }

    public static void standardChess(){
        StandardPosition startState = new StandardPosition();
        chessGame(startState);
    }

    public static void chessGame(StandardPosition startState) {
        Board board = new Board();


        JButton jButton[][] = new JButton[board.getLength()][board.getHeight()];
        Game.Listener listener[][] = new Game.Listener[board.getLength()][board.getHeight()];
        jFrame = new  Game(board, startState, jButton, listener);
    }


    public static Board.GameState userInput(Tuple location, Board board, Game jFrame) {
        if(running) {
            int status = board.changeState(location);
            victor = status;
            jFrame.repaint(board);
            if (status != -1)
                running = false;
        }
        return board.getStatus();
    }

    public static void undo(Board board, Game jFrame) {
        running = true;
        board.undo();
        jFrame.repaint(board);

    }

    public static void newGame(Board board, Game jFrame) {
        if(victor > -1)
            scores[victor]++;
        if(victor > -1 && forfeit)
            for(int index = 0; index < scores.length; index++)
                if(victor != index)
                    scores[index]++;
        running = true;
        //jFrame.setVisible(false);
        mainScreen.openMenu();
        jFrame.dispose();
        System.out.println("Score is: "+ scores[0]
                + ":" + scores[1]);
    }

    public static void startGame(Enum[] state, String[] nm, String[] ps) {
        names = nm;
        if(state[0] == MainScreen.State.FRIENDLY){
            passwords = null;
        }
        else {
            passwords = ps;
        }
        if(state[1] == MainScreen.State.CLASSICAL){
            standardChess();
        }
        else {
            nonStandardChess();
        }
    }

    public static void forfiet(Board board, Game jFrame) {
        victor = board.getCurrentTeamIndex();
        running = false;
        forfeit = false;
        jFrame.repaint(board);
    }
}