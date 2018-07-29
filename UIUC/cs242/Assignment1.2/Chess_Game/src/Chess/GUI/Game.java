package Chess.GUI;

import Chess.Board.Board;
import Chess.MiscClasses.Movement;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;
import Chess.Pieces.StandardChessPieces.*;
import Chess.StartingPosition.StartingPosition;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by jozsef on 2/8/16.
 * Borrowed the initial framework of this class from
 * http://stackoverflow.com/questions/2134840/drawing-on-jframe
 *
 */
public class Game extends JFrame{
    private int initialHeight = 600;
    private int initialWidth = 500;
    private int excessHeight = 100;
    private int outOfBoundsColor = 0;
    private Board board;
    private StartingPosition initialState;
    private JButton jButtons[][];
    private Listener listeners [][];
    private GameStateListener gameStateListener = new GameStateListener(this);
    private DrawPane pane;
    private JButton undo = new JButton("Undo");
    private UndoListener undoListener = new UndoListener(this);
    private JButton forfeit = new JButton("Forfeit");
    private ForfeitListener forfeitListener = new ForfeitListener(this);
    private JButton newGame = new JButton("New Game");
    private NewGameListener newGameListener = new NewGameListener(this);
    Timer t = null;


    /**
     * This constructor is used when to change the standard height and width.
     * Note: When implementing new boards the initial height and width needs to be a multiple of
     * the rows and columns respectively otherwise display may not appear symmetric.
     */
    public Game(Board board, StartingPosition initialState, int height, int width){
        this.board = board;
        this.initialState = initialState;
        this.initialState.positions(board);
        this.setInitialHeight(height);
        this.setInitialWidth(width);

        initBoard();
    }

    public Game(Board board, StartingPosition initialState, JButton jButtons[][], Listener listeners[][]){
        this.board = board;
        this.initialState = initialState;
        this.initialState.positions(board);
        this.setjButtons(jButtons);
        this.setListeners(listeners);
        init(jButtons, listeners);

        initBoard();
    }

    public void init(JButton jButton[][], Listener listeners[][]){
        for(int row = 0; row < jButton.length; row++){
            for(int column = 0; column < jButton[0].length; column++){
                jButton[row][column] = new JButton();
                listeners[row][column] = new Listener(row, column, this);
            }

        }
    }

    public void initBoard(){
        this.pane = new DrawPane();
        setContentPane(this.pane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(this.getInitialWidth(), this.getInitialHeight());

        setVisible(true);
    }

    public void repaint(Board b) {
        board = b;
        removeButton();
        setContentPane(this.pane);
        super.repaint();
    }

    private void removeButton(){
        this.remove(forfeit);
        this.remove(newGame);
        this.remove(undo);
    }

    public int getInitialHeight() {
        return initialHeight;
    }

    public void setInitialHeight(int initialHeight) {
        this.initialHeight = initialHeight;
    }

    public int getInitialWidth() {
        return initialWidth;
    }

    public void setInitialWidth(int initialWidth) {
        this.initialWidth = initialWidth;
    }

    public int getOutOfBoundsColor() {
        return outOfBoundsColor;
    }

    public void setOutOfBoundsColor(int outOfBoundsColor) {
        this.outOfBoundsColor = outOfBoundsColor;
    }

    public JButton getjButtonAt(Tuple location) {
        return jButtons[location.x][location.y];
    }

    public void setjButtons(JButton[][] jButtons) {
        this.jButtons = jButtons;
    }

    public Listener[][] getListeners() {
        return listeners;
    }

    public void setListeners(Listener[][] listeners) {
        this.listeners = listeners;
    }


    class DrawPane extends JPanel{
        public void paintComponent(Graphics g){
                drawBoard(g, board);
        }


        /**
         * Creates a path to the right directory for a given piece.
         */
        public Image getPieceImage(Piece piece) throws IOException {
            String pathLocation;
            if(piece == null){
                return null;
            }
            
            else if(piece.getColor() == piece.getColors()[0]){
                pathLocation =  System.getProperty("user.dir");
                pathLocation = pathLocation + "/Images/BlackPieces/";
            }
            
            else{
                pathLocation =  System.getProperty("user.dir");
                pathLocation = pathLocation + "/Images/WhitePieces/";
            }
            return getImage(piece, pathLocation);
        }

        /**
         * Gets the correct chess piece image in the given directory "pathLocation".
         */
        private Image getImage(Piece piece, String pathLocation) throws IOException {
            return ImageIO.read(new File(pathLocation + piece.getType() + ".png"));
        }


        /**
         * Draws each grid square and the Piece, if any, located on it.
         */
        private void drawBoard(Graphics g, Board board){
            int yStart = 0, yEnd = 0;
            int xStart = 0, xEnd = 0;
            int incX = this.getWidth()/board.getLength();
            int incY = (this.getHeight() - excessHeight)/board.getHeight();
            for(int row = 0; row < board.getHeight(); row++){
                xEnd += incX;
                for(int column = 0; column < board.getLength(); column++){
                    yEnd += incY;
                    Tuple location = new Tuple(row, column);
                    drawLayout(g, board, location);
                    g.fillRect(xStart, yStart, xEnd, yEnd);
                    drawImage(g, board, yStart, yEnd, xStart, xEnd, location);

                    if(board.activeSection(location)) {
                        JButton startButton = setJbutton(location, incX, incY, xStart, yStart);
                        this.add(startButton);
                        Listener listener = listeners[location.x][location.y];
                        listener.setClicks(0);
                        startButton.addActionListener(listener);
                    }
                    else {
                        JButton startButton = setJbutton(location, incX, incY, xStart, yStart);
                        this.remove(startButton);
                    }
                    yStart = yEnd;
                }
                xStart = xEnd;
                yStart = yEnd = 0;
            }
            drawUndoButton(this.getHeight() - excessHeight, g);
        }

        private void drawUndoButton(int startHieght, Graphics g) {
            JButton  currentButton;
            Listener currentListener;
            if(Control.isRunning()) {
                currentButton = forfeit;
                currentListener = forfeitListener;
            }
            else{
                currentButton = newGame;
                currentListener = newGameListener;
            }
            int heightSize = (this.getHeight() - startHieght)/4;
            int widthSize = this.getWidth()/3;

            undo.setBounds(widthSize, startHieght, widthSize, heightSize);
            undo.addActionListener(undoListener);
            this.add(undo);
            undoListener.setClicks(0);

            int endY = startHieght + 12*heightSize/4;
            currentButton.setBounds(widthSize, endY, widthSize, heightSize);
            currentButton.addActionListener(currentListener);
            this.add(currentButton);
            currentListener.setClicks(0);


            drawScore(widthSize, startHieght+ heightSize, widthSize, endY, g);
            drawJail(startHieght, widthSize, g);
        }

        private void drawScore(int startX, int startY, int length, int endY, Graphics g) {
            int sizeY = (endY - startY)/2 + 1;
            int lengthX = length;

            String[] names = Control.getNames();
            int[] scores = Control.getScores();

            g.setColor(Color.white);
            g.fillRect(startX, startY, length, sizeY);



            g.setColor(Color.black);
            g.fillRect(startX, startY + sizeY, length, sizeY);

            int bottomMargin = 4;

            Font font = new Font("Serif", Font.PLAIN, 20);
            g.setFont(font);
            g.drawString(names[0] + ":  " + scores[0], startX, startY + sizeY - bottomMargin);

            g.setColor(Color.white);
            g.drawString(names[1] + ":  " + scores[1], startX, startY + 2*sizeY - bottomMargin);
        }

        private void drawJail(int startHieght, int widthSize, Graphics g) {
            Vector<Vector<Piece>> teams = board.getCaptured();
            Vector<Piece> team1 = teams.elementAt(0);
            Vector<Piece> team2 = teams.elementAt(1);

            Color jail1 = jailColor(team1);
            g.setColor(jail1);
            g.fillRect(0, startHieght, widthSize, this.getHeight());
            drawInmates(team1, 0, startHieght, widthSize, this.getHeight(), g);

            Color jail2 = jailColor(team2);
            g.setColor(jail2);
            g.fillRect(widthSize*2, startHieght, this.getWidth(), this.getHeight());
            drawInmates(team2, widthSize*2, startHieght, this.getWidth(), this.getHeight(), g);
        }


        private Color jailColor(Vector<Piece> team1){
            if(team1.size() == 0)
                return Color.gray;
            else{
                return Color.getColor("", team1.elementAt(0).getColor());
            }
        }

        private void drawInmates(Vector<Piece> team, int startx, int starty, int endx, int endy, Graphics g){
            int widthDivision = (endx - startx)/5;
            int heightDivision = (endy - starty)/3;
            int scale = widthDivision < heightDivision ? widthDivision : heightDivision;
            int pieceIndex = 0;
            int imagex = startx;
            int imagey = starty;

            for(int row = 0; row < 3; row ++){
                for(int column = 0; column < 5 && pieceIndex < team.size(); column++){
                    Piece piece = team.elementAt(pieceIndex++);
                    drawInmate(piece, imagex, imagey, scale, g);
                    imagex += scale;
                }
                imagex = startx;
                imagey += scale;
            }


        }

        private void drawInmate(Piece piece,  int startx, int starty, int scale, Graphics g){
            try {
                Image image = getPieceImage(piece);
                if(image != null) {
                    Image scaledImage = image.getScaledInstance(scale, scale, Image.SCALE_SMOOTH);
                    g.drawImage(scaledImage, startx, starty, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Draws the appropriate image.
         * yStart, yEnd, xStart, xEnd are used to center the image.
         * @param location - indicates the grid on the board that is being drawn.
         */
        private void drawImage(Graphics g, Board board, int yStart, int yEnd, int xStart,int xEnd, Tuple location) {
            Piece piece = board.getOccupierAt(location);
            try {
                Image image = getPieceImage(piece);
                if(image != null) {
                    int smallestDemention = this.getHeight() < this.getWidth() ? this.getHeight() : this.getWidth();
                    int scaled = smallestDemention / board.getHeight();
                    Image scaledImage = image.getScaledInstance(scaled, scaled, Image.SCALE_SMOOTH);
                    int imageX = getImageLocation(xStart, xEnd, scaledImage.getWidth(null));
                    int imageY = getImageLocation(yStart, yEnd, scaledImage.getWidth(null));
                    g.drawImage(scaledImage, imageX, imageY, null);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private JButton setJbutton(Tuple location, int width, int height, int imageX, int imageY) {
            //button
            JButton startButton = getjButtonAt(location);
            startButton.setOpaque(false);
            startButton.setContentAreaFilled(false);
            startButton.setBorderPainted(false);                    // startButton.setBounds(300, 50,140, 50 );
            startButton.setBounds(imageX, imageY, width, height);
            return startButton;
        }

        private int getImageLocation(int start, int end, int size) {
            int middle = (end + start)/2;
            return middle - size/2;
        }


        /**
         * Draws the grid square.
         */
        private void drawLayout(Graphics g, Board board, Tuple location) {
            if(board.getOutOfBoundsAt(location)){
                int color = getObColor(board, location);
                g.setColor(Color.getColor("obColor", color));
            }
            else {
                if (board.isMoveAt(location) || board.isAttackAt(location) || board.isRangeAttackAt(location)) {
                    g.setColor(Color.gray);
                } else {
                    g.setColor(Color.getColor("Board1", board.getColorAt(location)));
                }
            }
        }

        /**
         * Determines whether a particular piece is nieghbor to a piece that is inbounds.
         * Used to outline the boundries with board.getWallColor().
         */
        private int getObColor(Board board, Tuple location) {
            if(Movement.withinBounds(location.copy(0,1), board) && !board.getOutOfBoundsAt(location.copy(0,1)))
                return board.getWallColor();
            if(Movement.withinBounds(location.copy(0,-1), board) && !board.getOutOfBoundsAt(location.copy(0,-1)))
                return board.getWallColor();
            if(Movement.withinBounds(location.copy(1,-1), board) && !board.getOutOfBoundsAt(location.copy(1,-1)))
                return board.getWallColor();
            if(Movement.withinBounds(location.copy(-1,-1), board) && !board.getOutOfBoundsAt(location.copy(-1,-1)))
                return board.getWallColor();

            if(Movement.withinBounds(location.copy(1,0), board) && !board.getOutOfBoundsAt(location.copy(1,0)))
                return board.getWallColor();
            if(Movement.withinBounds(location.copy(-1,0), board) && !board.getOutOfBoundsAt(location.copy(-1,0)))
                return board.getWallColor();
            if(Movement.withinBounds(location.copy(-1,1), board) && !board.getOutOfBoundsAt(location.copy(-1,1)))
                return board.getWallColor();
            if(Movement.withinBounds(location.copy(1,1), board) && !board.getOutOfBoundsAt(location.copy(1,1)))
                return board.getWallColor();

            return getOutOfBoundsColor();
        }

    }

    public class Listener implements ActionListener {
        Tuple location;
        Game jFrame;

        public int getClicks() {
            return clicks;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }

        int clicks = 0;

        public Listener(){}

        public Listener(int row, int column, Game jFrame){
            location = new Tuple(row, column);
            this.jFrame = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clicks++;
            if(clicks == 1) {
                if(Board.GameState.CHECK == Control.userInput(this.location, board, jFrame)) {
                    paintWarning();
                    //t = new Timer(1000, gameStateListener);
                }
            }
        }
        public void paintWarning(){
            Graphics g = jFrame.getGraphics();
            Font font = new Font("Serif", Font.PLAIN, 20);
            g.setFont(font);
            g.drawString("Invalid Move", jFrame.getWidth()/2, jFrame.getHeight()/2);
        }
    }

    private class GameStateListener extends Listener{
        public GameStateListener(Game jFrame) {
            this.jFrame = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }

    }


    private class UndoListener extends Listener{
        public UndoListener(Game jFrame) {
            this.jFrame = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clicks++;
            if(clicks == 1) {
                Control.undo(board, jFrame);
            }
        }
    }

    private class ForfeitListener extends Listener{
        public ForfeitListener(Game jFrame) {
            this.jFrame = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clicks++;
            if(clicks == 1) {
                Control.forfiet(board, jFrame);
            }
        }
    }

    private class NewGameListener extends Listener{
        public NewGameListener(Game jFrame) {
            this.jFrame = jFrame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clicks++;
            if(clicks == 1) {
                Control.newGame(board, jFrame);
            }
        }
    }
}

