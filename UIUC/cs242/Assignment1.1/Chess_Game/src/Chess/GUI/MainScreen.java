package Chess.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by jozsef on 2/17/16.
 */
public class MainScreen extends JFrame{
    private JPanel pane;
    private JButton friendly = new JButton("Freindly");
    private FriendlyListener friendlyListener = new FriendlyListener();
    private JButton competitive = new JButton("Competitive");
    private CompetitiveListener competitiveListener = new CompetitiveListener();
    private JButton classicalChess = new JButton("Classical Chess");
    private ClassicalListener classicalListener = new ClassicalListener();
    private JButton modifiedChess = new JButton("Modified Chess");
    private ModifiedListener modifiedListener = new ModifiedListener();
    private JButton startGame = new JButton("Start Game");
    private int numberPlayers = 2;
    private JTextField playerNames[];
    private JPasswordField playerPasswords[];
    private String passwordTemplate = "j5Ea46]~";
    private boolean infoEntered = false;


    private Enum state[] = {State.UNDECIDED, State.UNDECIDED};

    public MainScreen(int height, int width){

        initBoard(height, width);
    }

    public void initBoard(int height, int width){
        this.pane = new DrawPane();
        setContentPane(this.pane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(width, height);

        setVisible(true);
        initPlayers();
    }

    private void initPlayers() {
        playerNames = new JTextField[numberPlayers];
        playerPasswords = new JPasswordField[numberPlayers];
        for(int index = 0; index < numberPlayers; index++){
            playerNames[index] = new JFormattedTextField("Player " + index);
            playerPasswords[index] = new JPasswordField(passwordTemplate);
        }
    }

    public void openMenu() {
        this.setVisible(true);
        paintComponents(this.getGraphics());
    }

    private class DrawPane extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponents(g);
            g.clearRect(0, 0, this.getWidth(), this.getHeight());
            removeButtons();
            if(state[0] == State.UNDECIDED && state[1] == State.UNDECIDED)
                gameNature(g);
            else if(infoEntered || (state[0] != State.UNDECIDED && state[1] == State.UNDECIDED)){
                gameType(g);
            }
            else if(state[0] != State.UNDECIDED && state[1] != State.UNDECIDED)
                setPlayerFields(g);
        }


        private void gameType(Graphics g) {
            setTitle(g, "Choose A Game   ");

            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;
            int sizeX = this.getHeight() / 3;
            int sizeY = this.getWidth() / 6;

            classicalChess.setBounds(centerX - 4 * sizeX / 3, centerY - sizeY, sizeX, sizeY);
            this.add(classicalChess);
            classicalChess.addActionListener(classicalListener);
            classicalListener.setClicks(0);

            modifiedChess.setBounds(centerX + sizeX / 3, centerY - sizeY, sizeX, sizeY);
            this.add(modifiedChess);
            modifiedChess.addActionListener(modifiedListener);
        }

        private void setPlayerFields(Graphics g){
            setTitle(g, "Player Info");
            int sizeX = this.getWidth()/3;
            int sizeY = this.getHeight()/15;
            int startX = this.getWidth()/2 - 4*sizeX/3;
            int imageX = startX;
            int imageY = this.getHeight()/2 - 2*sizeY;
            int playerIndex = 0;

            for(int rowIndex = 0; rowIndex < 5 && playerIndex < numberPlayers; rowIndex++){
                for(int columnIndex = 0; columnIndex < 2 && playerIndex < numberPlayers; columnIndex++){
                    playerNames[playerIndex].setBounds(imageX, imageY, sizeX, sizeY);
                    this.add(playerNames[playerIndex]);
                    playerNames[playerIndex].addActionListener(friendlyListener);

                    if(state[0] == State.COMPETITIVE) {
                        imageY += sizeY;
                        playerPasswords[playerIndex].setBounds(imageX, imageY, sizeX, sizeY);
                        this.add(playerPasswords[playerIndex]);
                        imageY -= sizeY;
                    }

                    playerIndex++;
                    imageX += 5*sizeX/3;
                }
                imageY += 2*sizeY;
                imageX = startX;
            }

            setStartGameButton(imageY);
        }

        private void setStartGameButton(int startY) {
            int centerX = this.getWidth()/2;
            int sizeX = this.getWidth()/3;
            int sizeY = this.getHeight()/15;

            startGame.setBounds(centerX - sizeX/2, startY + 2*sizeY, sizeX, sizeY);
            this.add(startGame);
            startGame.addActionListener(new StartGameListener());
            ///////////////////////////////////////////////////////////////////////////////////
        }


        private void setTitle(Graphics g, String s) {
            int centerX = this.getWidth()/2;
            int startX = centerX - s.length()*13;
            Font font = new Font("Serif", Font.PLAIN, 48);
            g.setFont(font);

            g.drawString(s, startX, 135);
        }


        public void gameNature(Graphics g) {
            setTitle(g, "Hello Chess");
            drawMainButtons();
        }

        public void drawMainButtons(){
            int centerX = this.getWidth()/2;
            int centerY = this.getHeight()/2;
            int sizeX = this.getHeight()/3;
            int sizeY = this.getWidth()/6;

            friendly.setBounds(centerX - 4*sizeX/3, centerY - sizeY, sizeX, sizeY);
            this.add(friendly);
            friendly.addActionListener(friendlyListener);

            competitive.setBounds(centerX + sizeX/3, centerY - sizeY, sizeX, sizeY);
            this.add(competitive);
            competitive.addActionListener(competitiveListener);
        }

    }

    private void removeButtons(){
        if(friendly != null)
            this.remove(friendly);
        if(competitive != null)
            this.remove(competitive);
        if(modifiedChess != null)
            this.remove(modifiedChess);
        if(classicalChess != null)
            this.remove(classicalChess);
        if(startGame != null)
            this.remove(startGame);
        removePlayerInfo();
    }

    private void removePlayerInfo() {
        for(int index = 0; index < numberPlayers; index++){
            if(playerNames[index] != null)
                this.remove(playerNames[index]);
            if(playerPasswords[index] != null)
                this.remove(playerPasswords[index]);
        }
    }

    private class FriendlyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            state[0] = State.FRIENDLY;
            removeButtons();
            repaint();

        }
    }

    private class CompetitiveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            state[0] = State.COMPETITIVE;
            removeButtons();
            repaint();
        }
    }

    private class ClassicalListener implements ActionListener {
        int clicks = 0;
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(clicks++ == 0) {
                state[1] = State.CLASSICAL;
                removeButtons();
                repaint();

                if (infoEntered) {
                    Control.startGame(state, getNames(), getPasswords());
                    setVisible(false);
                }
            }
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }
    }

    private class ModifiedListener implements ActionListener {
        int clicks = 0;
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(clicks++ == 0) {
                state[1] = State.MODIFIED;
                removeButtons();
                repaint();

                if (infoEntered) {
                    Control.startGame(state, getNames(), getPasswords());
                    setVisible(false);
                }
            }
        }
    }

    private class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            verifyInput();
        }

        private void verifyInput() {
            for(int index = 0; index < numberPlayers; index++){
                if(playerNames[index].getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please input a name for player " + index);
                    return;
                }
                if(state[0] == State.COMPETITIVE && (playerPasswords[index].getText().equals("") || playerPasswords[index].getText().equals(passwordTemplate))){
                    JOptionPane.showMessageDialog(null, "Please input a password for player " + index);
                    return;
                }
            }
            Control.startGame(state, getNames(), getPasswords());
            setVisible(false);
            infoEntered = true;
        }
    }


    private String[] getNames() {
        String[] output = new String[numberPlayers];
        for(int index = 0; index < numberPlayers; index++){
            output[index] = playerNames[index].getText();
        }
        return output;
    }

    private String[] getPasswords() {
        String[] output = new String[numberPlayers];
        for(int index = 0; index < numberPlayers; index++){
            output[index] = playerPasswords[index].getText();
        }
        return output;
    }

    public enum State {
        UNDECIDED,
        CLASSICAL, MODIFIED,
        FRIENDLY, COMPETITIVE,
    }
}
