package Chess.Pieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.powerUps;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;

/**
 * Created by jozsef on 1/30/16.
 */
public class Pawn extends Piece{
    boolean UP = true;
    boolean orientation = UP;

    public Pawn(Tuple loc, boolean orient, Board board){
        this.setLocation(loc);
        this.setOrientation(orient);
    }

    public Pawn(Pawn piece){
        this.setLocation(piece.getLocation());
        this.setTurns(piece.getTurns());
        this.setColor(piece.getColor());
        this.setType(piece.getType());
        this.setColors(piece.getColors());
        this.setOrientation(piece.orientation);
    }

    public boolean isOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public Pawn(Tuple loc, int color, boolean orient, Board board){
        this.setLocation(loc);
        this.setOrientation(orient);
        this.setColor(color);
    }

    public Pawn(int color){
        this.setColor(color);
    }

    public Pawn(){setTurns(0);}

    //Returns a Vector<Tuple> of possible getMoves.
    public VectorMove getMoves(Board board){
        VectorTuple moves = new VectorTuple();

        moves.add(straightAhead(board, upDown(), true));
        moves.add(straightAhead(board, upDown()*2, this.getTurns() == 0));

        moves.add(checkEnemies(board, -1));
        moves.add(checkEnemies(board, 1));

        return Move.createMoves(this.getLocation(), moves, board);
    }

    public Tuple straightAhead(Board board, int offSet, boolean legal){
        VectorTuple out = new VectorTuple();

        Tuple loc = new Tuple(this.getLocation().x, this.getLocation().y + offSet);
        Piece oLoc;

        if(Movement.withinBounds(loc, board))
            oLoc = board.getOccupierAt(loc);
        else
            return null;

        if(oLoc == null && legal)
           return loc;

        return null;
    }

    public Tuple checkEnemies(Board board, int offSet){
        Tuple loc = new Tuple(this.getLocation().x + offSet, this.getLocation().y + upDown());
        Piece oLoc;

        if(Movement.withinBounds(loc, board))
            oLoc = board.getOccupierAt(loc);
        else
            return null;

        if(oLoc != null && oLoc.getColor() != this.getColor())
            return loc;

        return null;
    }

    //Since pawns only move one direction this method is used to determine the direction.
    public int upDown(){
        if(orientation)
            return -1;
        else
            return 1;
    }

    public void powerUp(Board board){powerUps.passant(board, this.getLocation(), orientation);}

    public void print(){
        System.out.print(String.format("%-9s", this.getType()));
    }

}
