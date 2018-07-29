package Chess.Board;

import Chess.GUI.Game;
import Chess.MiscClasses.Movement;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.Movement.Move;
import Chess.Pieces.Piece;

import javax.swing.*;
import java.security.PrivateKey;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

import static java.lang.Math.PI;
import static java.lang.Math.abs;


/**
 * Created by jozsef on 1/26/16.
 * This class is creates a 2d array that will represent the environment the pieces interact in.
 */

/**
 *The base class is the size of a standard chess board. With colors alternating from black to white.
 */
public class Board {

    public GameState status = GameState.RUNNING;

    private VectorMove previousMoves = new VectorMove();

    private Vector<Vector<Piece>> teams;
    private Vector<Vector<Piece>> captured;
    private int currentTeamIndex = 0;
    private int numberOfTeams = 0;
    private int length;
    private int height;
    private Characteristics characteristics [][];
    private Vector<Piece> Leaders;
    private int wallColor;

    public Board(){
        this.setLength(8);
        this.setHeight(8);
        this.setCharacteristics(new Characteristics [this.height][this.length]);
        this.setTeams(new Vector<Vector<Piece>>(0));
        this.setLeaders(new Vector<Piece>(0));
        this.initCharacteristics();
        this.alternatingColors(200, Integer.MAX_VALUE - 200);
        calculateWallColor();
    }

    /**
     * Determines a color of high contrast to existing board piece colors.
     */
    public void calculateWallColor() {
        int colors[] = new int[getHeight()*getLength()];
        int size = findColors(colors);

        if(size == 1) {
            int whiteDiff = 0xffffff - colors[0];
            int color = whiteDiff > colors[0]? Integer.MAX_VALUE : 0;
            setWallColor(color);
        }
        else {
            int maxDiffColors[] = {colors[0], colors[1]};
            int maxDifference = abs(colors[0] - colors[1]);
            for(int index1 = 2; index1 < size; index1++){
                for(int index2 = 0; index2 < size; index2++){
                    int currentDiff = abs(colors[index1] - colors[index2]);
                    if(currentDiff > maxDifference){
                        maxDiffColors[0] = colors[index1];
                        maxDiffColors[1] = colors[index2];
                    }
                }
            }
            this.setWallColor((maxDiffColors[0] + maxDiffColors[1])/2);
        }
    }

    /**
     * @param colors - An array to befilled with the different colors of board pieces.
     * @return - The number of colors added which are zero indexed.
     */
    private int findColors(int colors[]) {
        int size = 0;
        for(int row = 0; row < getHeight(); row++){
            for(int column = 0; column < getLength(); column++){
                Tuple location = new Tuple(row, column);
                boolean newColor = true;
                for(int index = 0; index < size; index++){
                    if(this.getOutOfBoundsAt(location) || this.getColorAt(location) == colors[index]){
                        newColor = false;
                        break;
                    }
                }
                if(newColor){
                    colors[size] = this.getColorAt(location);
                    size++;
                }
            }
        }
        return size;
    }


    public void setTeams(Vector<Vector<Piece>> teams) {
        this.teams = teams;
    }
    public Vector<Vector<Piece>> getTeams(){return teams;}

    public Vector<Vector<Piece>> getCaptured(){return captured;}

    public void setLeaders(Vector<Piece> leaders) {
        Leaders = leaders;
    }

    public void setLength(int length){
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setCharacteristics(Characteristics[][] characteristics) {
        this.characteristics = characteristics;
    }

    public Vector<Piece> getLeaders() {
        return Leaders;
    }

    public Piece getOccupierAt(Tuple location){
        return this.at(location).getOccupier();
    }

    /**
     * Set occupier in addition to setting the piece occupying the "location" if it is is the first time
     * being set, it is placed on the appropriate team and added to the list of leaders if appropriate.
     * @param location
     * @param piece
     */
    public void setOccupierAt(Tuple location, Piece piece){
        if(!Movement.withinBounds(location, this))
            return;
        this.at(location).setOccupier(piece);

        if(piece != null && !piece.isOnTeam()) {
            piece.setOnTeam(true);
            this.addPieceToTeam(piece);
            if (piece.isLeader())
                this.Leaders.add(piece);
        }
    }

    public void removeFromTeam(Piece piece){
        if(piece == null)
            return;
        for(int teamIndex = 0; teamIndex < teams.size(); teamIndex++){
            Vector<Piece> team = teams.elementAt(teamIndex);
            for(int playerIndex = 0; playerIndex < team.size(); playerIndex++){
                Piece player = team.elementAt(playerIndex);
                if(player.getLocation().compare(piece.getLocation()) && player.getColor() == piece.getColor()){
                    team.removeElementAt(playerIndex);
                }
            }
        }
    }

    public boolean isMoveAt(Tuple location){
        return characteristics[location.x][location.y].isMove();
    }

    public boolean isAttackAt(Tuple location){
        return characteristics[location.x][location.y].isAttack();
    }

    public boolean isRangeAttackAt(Tuple location){
        return characteristics[location.x][location.y].isRangeAttack();
    }

    public Move getExicuteAt(Tuple location){
        return characteristics[location.x][location.y].getExicute();
    }

    public void setMoveAt(Tuple location){
        characteristics[location.x][location.y].setMove(true);
    }

    public void setAttackAt(Tuple location){
        characteristics[location.x][location.y].setMove(true);
    }

    public void setRangeAttackAt(Tuple location){
        characteristics[location.x][location.y].setMove(true);
    }

    public void setExicuteAt(Tuple location, Move move){
        characteristics[location.x][location.y].setExicute(move);
    }

    public Piece getCurrentLeader(){
        Vector<Piece> team = teams.elementAt((currentTeamIndex));
        for(int index = 0; index < team.size(); index++){
            if(team.elementAt(index).isLeader())
                return new Piece(team.elementAt(index));
        }
        return null;
    }

    public void resetMovements(){
        for(int row = 0; row < characteristics.length; row++){
            for (int column = 0; column < characteristics[0].length; column++){
                Characteristics characteristics = this.characteristics[row][column];
                characteristics.setMove(false);
                characteristics.setAttack(false);
                characteristics.setRangeAttack(false);
                characteristics.setExicute(null);
            }
        }
    }

    public int changeState(Tuple location) {
        if (isAttackAt(location) || isMoveAt(location) || isRangeAttackAt(location)) {
            Move revert = this.getExicuteAt(location).move();
            if(revert != null) {
                previousMoves.add(revert);
                this.status = GameState.RUNNING;
            }
            else {
                this.status = GameState.CHECK;
            }
            this.resetMovements();
            this.setCurrentTeamIndex();
            if(this.checkForGameOver()){
                if(checkEndGameConditions())
                    return this.currentTeamIndex;
                else
                    this.status = GameState.STALEMATE;
                    return -1;
            }
        } else {
            this.resetMovements();
            setMoves(location);
        }
        return -1;
    }

    public boolean checkEndGameConditions(){
        Piece leader = getCurrentLeader();
        VectorMove enemyMoves = getOpponentsMoves(leader);
        if(!enemyMoves.canReach(leader.getLocation())){
            this.status = GameState.STALEMATE;
            System.out.println("StaleMate");
            return false;
        }
        else{
            this.status = GameState.VICTORY;
            System.out.println("Game Over: ");
            return true;
        }

    }

    public boolean checkForGameOver(){
        VectorMove currentMoves = getCurrentMoves();
        for(int index = 0; index < currentMoves.size(); index++){
            Move move = currentMoves.elementAt(index);
            Piece prevOccupier = this.getOccupierAt(move.finish);
            move.makeMove();
            if (!move.leaderNotSafe(this.getOccupierAt(move.finish))){
                Move revert = new Move(this, move.finish, move.start, false, prevOccupier);
                revert.makeMove();
                return false;
            }
            Move revert = new Move(this, move.finish, move.start, false, prevOccupier);
            revert.makeMove();
        }
        System.out.println("Game Over");
        return true;
    }

    public void setMoves(Tuple location){
        Piece selected = this.getOccupierAt(location);
        VectorMove moves = selected.getManeuvers(this);
        moves.removeNulls();
        for (int index = 0; index < moves.size(); index++){
            Move move = moves.elementAt(index);
            Tuple boardLocation = move.finish;
            this.setExicuteAt(boardLocation, move);
            this.setMoveAt(boardLocation);
            this.setAttackAt(boardLocation);
        }
    }

    public boolean activeSection(Tuple location){
        Piece baseLine = teams.elementAt(currentTeamIndex).elementAt(0);
        Piece testPiece = getOccupierAt(location);
        return (testPiece != null && sameTeam(baseLine, testPiece)) || isAttackAt(location) || isMoveAt(location) || isRangeAttackAt(location);
    }

    public boolean getHasBeenReachedAt(Tuple location){
        return this.at(location).isHasBeenReached();
    }

    public void setHasBeenReachedAt(Tuple location){
        this.at(location).setHasBeenReached(true);
    }

    /**
     * The has been reached variable is used for recursivly determining possible paths.
     * This function is called to reset all values to false.
     */
    public void resetHasBeenReached(){
        for(int row = 0; row < this.getHeight(); row++){
            for(int column = 0; column < this.getLength(); column++){
                this.at(new Tuple(row, column)).setHasBeenReached(false);
            }
        }
    }

    public int getColorAt(Tuple location){
        return this.at(location).getSquareColor();
    }

    public void setColorAt(Tuple location, int color){
        this.at(location).setSquareColor(color);
    }

    public void setOutOfBoundsAt(Tuple location, boolean outOfBounds){
        this.at(location).setOutOfBounds(outOfBounds);
    }

    public boolean getOutOfBoundsAt(Tuple location){
        return this.at(location).getOutOfBounds();
    }

    /**
     * Returns a Vector of all members of piece's team.
     * @param piece
     * @return
     */
    public Vector<Piece> getTeam(Piece piece){
        for(int index = 0; index < teams.size(); index++){
            Vector<Piece> team = teams.elementAt(index);
            if(team.size() > 0 && team.elementAt(0).getColor() == piece.getColor())
                return team;
        }
        return null;
    }

    /**
     * Returns a Vector containing all opponents.
     * @param piece
     * @return
     */
    public Vector<Piece> getOtherTeam(Piece piece){
        Vector<Piece> output = new Vector<>();
        for(int index = 0; index < teams.size(); index++){
            Vector<Piece> team = teams.elementAt(index);
            if(team.size() > 0 && team.elementAt(0).getColor() != piece.getColor())
                output.addAll(team);
        }
        return output;
    }

    public Piece getTeamMember(Vector<Piece> team){
        for(int index = 0; index < team.size(); index++){
            if(team.elementAt(index) != null)
                return team.elementAt(index);
        }
        return null;
    }

    /**
     * Adds piece to the appropriate team.
     * @param piece
     */
    public void addPieceToTeam(Piece piece){
        if(piece == null)
            return;
        for(int vectIndex = 0; vectIndex < teams.size(); vectIndex++){
            Vector<Piece> team = teams.elementAt(vectIndex);
            if(team.size() > 0 && team.elementAt(0).getColor() == piece.getColor()){
                team.add(piece);
                return;
            }
        }
        numberOfTeams++;
        Vector<Piece> newTeam = new Vector<Piece>(1);
        newTeam.add(piece);
        teams.add(newTeam);
    }

    /**
     * Declared private to ensure all Characteristic changes are done through the specified setter.
     * @param index
     * @return
     */
    private Characteristics at(Tuple index){
        return characteristics[index.x][index.y];
    }

    /**
     *  Returns a vector of all moves of the team piece is on.
     */
    public VectorMove getMyMoves(Piece piece){return getTeamMoves(findGroup(piece, "sameTeam"));}

    /**
     *  Returns a vector of all moves of all the teams piece is not on.
     */
    public VectorMove getOpponentsMoves(Piece piece){return getTeamMoves(findGroup(piece, "diffTeam"));}

    public VectorMove getCurrentMoves(){
        Vector<Piece> team = this.teams.elementAt(currentTeamIndex);
        return getTeamMoves(team);
    }
    /**
     *  Returns a vector of the moves of the entire team.
     */
    private VectorMove getTeamMoves(Vector<Piece> teammates){
        VectorMove out = new VectorMove();
        for(int index = 0; index < teammates.size(); index++){
            out.addAll(teammates.elementAt(index).getManeuvers(this));
        }
        return out;
    }

    /**
     *  This section is generalized to finding all pieces with a certain relation
     */
    private Vector<Piece> findGroup(Piece piece, String type){
        Vector <Piece> team = new Vector<>(0);
        for(int teamIndex = 0; teamIndex < teams.size(); teamIndex++){
            Vector<Piece> testTeam = teams.elementAt(teamIndex);
            if(testTeam.size() > 0) {
                Piece currentPiece = testTeam.elementAt(0);
                if (currentPiece != null && getPieceCmp(type, piece, currentPiece))
                    for(int playerIndex = 0; playerIndex < testTeam.size(); playerIndex++)
                        team.add(testTeam.elementAt(playerIndex));
            }
        }

        return team;
    }


    /**
     *  Switch statement acts as a function pointer to a piece comparator function.
     */
    private boolean getPieceCmp(String type, Piece piece1, Piece piece2){
        switch (type){
            case "sameTeam":
                return sameTeam(piece1, piece2);
            case "diffTeam":
                return diffTeam(piece1, piece2);
            default:
                return false;
        }
    }

    /**
     *  Finds the leader of a specified team color
     */
    public Piece findLeader(int color){
        for(int index = 0; index < Leaders.size(); index++){
            Piece leader = Leaders.elementAt(index);
            if(leader.getColor() == color)
                return leader;
        }
        return null;
    }

    /**
     *  Initializes each characteristic object of the game board.
     */
    public void initCharacteristics(){
        for(int row = 0; row < this.height; row++){
            for(int column = 0; column < this.length; column++){
                this.characteristics[row][column] = new Characteristics();
            }
        }
    }

    /**
     *  Color is one of the elements of the Characteristic class. This function alternates two colors across the board.
     */
    public void alternatingColors(int color1, int color2){
        for(int row = 0; row < this.height; row++){
            for(int column = 0; column < this.length; column++){
                int odd = (row % 2 == 0) ? 0 : 1;
                if(column % 2 == odd){
                    this.setColorAt(new Tuple(column,row), color1);
                }
                else{
                    this.setColorAt(new Tuple(column,row), color2);
                }
            }
        }
    }

    /**
     *  I don't check for index errors since players will not have access to this function.
     */
    public void outOfBoundsSquare(Tuple start, Tuple finish){
        for(int column = start.x; column < finish.x; column++){
            for(int row = start.y; row < finish.y; row++){
                this.setOutOfBoundsAt(new Tuple(column, row), true);
            }
        }
    }



    private boolean sameTeam(Piece piece1, Piece piece2){return piece1.getColor() == piece2.getColor();}
    private boolean diffTeam(Piece piece1, Piece piece2){return piece1.getColor() != piece2.getColor();}


    public int getWallColor() {
        return wallColor;
    }

    public void setWallColor(int wallColor) {
        this.wallColor = wallColor;
    }


    public void setCurrentTeamIndex() {
        currentTeamIndex = (currentTeamIndex + 1)%numberOfTeams;
    }


    public Move undo() {
        if(previousMoves.size() == 0){
            System.out.println("There are no moves to be undone");
            return null;
        }
        int lastMove = previousMoves.size() - 1;
        Move undo = this.previousMoves.elementAt(lastMove);
        this.previousMoves.removeElementAt(lastMove);
        Piece reset = this.getOccupierAt(undo.start);
        if(reset == null)
            return undo;
        reset.setTurns(reset.getTurns() - 2);
        undo.move();
        resetMovements();

        this.setCurrentTeamIndex();
        return undo;
    }

    public void setCaptured() {
        this.captured = new Vector<>();
        for(int index = 0; index < numberOfTeams; index++)
            this.captured.add(new Vector<Piece>());
    }

    public int getCurrentTeamIndex() {
        return currentTeamIndex;
    }

    public GameState getStatus() {
        return status;
    }

    public enum GameState{
        RUNNING, STALEMATE, VICTORY, CHECK
    }
}
