package Chess.Board;

import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.Pieces.Piece;

import java.util.Vector;
import java.util.concurrent.SynchronousQueue;


/**
 * Created by jozsef on 1/26/16.
 * This class is creates a 2d array that will represent the environment the pieces interact in.
 */


public class Board {
    /**
     *The base class is the size of a standard chess board. With colors alternating from black to white.
     */
    public int RUNNING = 0;
    public int STALEMATE = 1;
    public int VICTORY = 2;
    public int VICTOR_COLOR;

    public int status = RUNNING;

    private Vector<Vector<Piece>> teams;
    private int length;
    private int height;
    private Characteristics characteristics [][];
    private Vector<Piece> Leaders;

    public Board(){
        this.setLength(8);
        this.setHeight(8);
        this.setCharacteristics(new Characteristics [this.height][this.length]);
        this.setTeams(new Vector<Vector<Piece>>(0));
        this.setLeaders(new Vector<Piece>(0));
        this.initCharacteristics();
        this.alternatingColors(0, Integer.MAX_VALUE);
    }

    public Board(Board board){
        this.length = board.length;
        this.height = board.height;
        this.setTeams(board.getTeams());
        this.Leaders = new Vector<>(board.Leaders);

        this.characteristics = new Characteristics[this.height][this.length];
        for(int row = 0; row < this.height; row++){
            for(int column = 0; column < this.length; column++){
                this.characteristics[row][column] = new Characteristics(board.characteristics[row][column]);
            }
        }
    }

    public Vector<Vector<Piece>> getTeams() {
        return teams;
    }

    public void setTeams(Vector<Vector<Piece>> teams) {
        this.teams = teams;
    }

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

    public void setOccupierAt(Tuple location, Piece piece){
        this.at(location).setOccupier(piece);
        if(piece == null)
            return;
        if(!piece.isOnTeam()) {
            piece.setOnTeam(true);
            this.addPiece(piece);
            if (piece.isLeader())
                this.Leaders.add(piece);
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

    public Vector<Piece> getTeam(Piece piece){
        for(int index = 0; index < teams.size(); index++){
            Vector<Piece> team = teams.elementAt(index);
            if(team.size() > 0 && team.elementAt(0).getColor() == piece.getColor())
                return team;
        }
        return null;
    }

    public Vector<Piece> getOtherTeam(Piece piece){
        Vector<Piece> output = new Vector<>();
        for(int index = 0; index < teams.size(); index++){
            Vector<Piece> team = teams.elementAt(index);
            if(team.size() > 0 && team.elementAt(0).getColor() != piece.getColor())
                output.addAll(team);
        }
        return output;
    }

    public void addPiece(Piece piece){
       if(piece == null)
           return;
        for(int vectIndex = 0; vectIndex < teams.size(); vectIndex++){
            Vector<Piece> team = teams.elementAt(vectIndex);
            if(team.size() > 0 && team.elementAt(0).getColor() == piece.getColor()){
                team.add(piece);
                return;
            }
        }
        Vector<Piece> newTeam = new Vector<Piece>(1);
        newTeam.add(piece);
        teams.add(newTeam);
    }

    public Characteristics at(Tuple index){
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

    /**
     *  Returns a vector of the moves of the entire team.
     */
    private VectorMove getTeamMoves(Vector<Piece> teammates){
        VectorMove out = new VectorMove();
        for(int index = 0; index < teammates.size(); index++){
            out.addAll(teammates.elementAt(index).getMoves(this));
        }
        return out;
    }

    /**
     *  This section is generalized to finding all pieces with a certain relation
     */
    private Vector<Piece> findGroup(Piece piece, String type){
        Vector <Piece> team = new Vector<>(0);
        for(int row = 0; row < 8; row++){
            for(int column = 0; column < 8; column++){
                Piece currentPiece = this.getOccupierAt(new Tuple(column, row));
                if(currentPiece !=null && getPieceCmp(type, piece, currentPiece))
                    team.add(currentPiece);
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
     *  Prints a board layout consisting of the out of the out of bounds characteristic only.
     */
    public void printTerain(){
        for(int row = -1; row < this.height + 1; row++){
            System.out.print("///");
            for(int column = 0; column < this.length; column++){
                if(row == -1 || row == this.height)
                    System.out.print("///");
                else if(this.getOutOfBoundsAt(new Tuple(column,row)))
                    System.out.print("///");
                else
                    System.out.print("   ");
            }
            System.out.println("///");
        }
    }

    /**
     *  Prints all the characteristics of a board in a format similar to a chess board.
     */
    public void printCharacteristics() {
        for(int row = 0; row < this.height; row++){
            printCharOccupier(row);
            System.out.println();
            printCharColor(row);
            System.out.println();
            printCharOutBounds(row);
            System.out.println();
            printOccColor(row);
            System.out.println("\n");

        }
    }

    /**
     *  Prints a row of all outOfBounds aspects of a board.
     */
    public void printCharOutBounds(int row) {
        for(int column = 0; column < this.length; column++){
            System.out.print(String.format("Out Bounds: %-7s", this.getOutOfBoundsAt(new Tuple(column,row))));
        }
    }

    /**
     *  Prints a row of all color aspects of a board.
     */
    public void printCharColor(int row) {
        for(int column = 0; column < this.length; column++){
            System.out.print(String.format("color: %-11d ", this.getColorAt(new Tuple(column, row))));
        }
    }

    /**
     *  Prints a row of all occupier aspects of a board.
     */
    public void printCharOccupier(int row) {
        for(int column = 0; column < this.length; column++){
            System.out.print(String.format("occupier: "));
            Tuple location = new Tuple(column, row);
            if(this.getOccupierAt(location) == null) {
                if(this.getOutOfBoundsAt(location))
                    System.out.print(String.format("%-9s", "OB"));
                else
                    System.out.print(String.format("%-9s", "null"));
            }
            else
                this.getOccupierAt(location).print();
        }

    }

    /**
     *  Prints the color of the current occupier of a square.

     */
    public void printOccColor(int row){
        for(int column = 0; column < this.length; column++){
            System.out.print(String.format("occ Color: "));
            Tuple location = new Tuple(column, row);
            if(this.getOccupierAt(location) == null)
                System.out.print(String.format("%-8s", "null"));
            else
                System.out.print(String.format("%-8s", this.getOccupierAt(location).colorName()));
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






}
