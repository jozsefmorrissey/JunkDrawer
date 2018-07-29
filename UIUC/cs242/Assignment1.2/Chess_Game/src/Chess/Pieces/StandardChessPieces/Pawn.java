package Chess.Pieces.StandardChessPieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.powerUps;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/30/16.
 */
public class Pawn extends Piece {
    boolean UP = true;
    boolean orientation = UP;

    public Pawn(Tuple loc, boolean orient, Board board){
        this.setLocation(loc);
        this.setOrientation(orient);
        this.setType("Pawn");
    }


    public Pawn(Pawn piece){
        this.setLocation(piece.getLocation());
        this.setTurns(piece.getTurns());
        this.setColor(piece.getColor());
        this.setType(piece.getType());
        this.setColors(piece.getColors());
        this.setOrientation(piece.orientation);
        this.setType("Pawn");
    }

    public VectorMove getMoves(Board board){
        VectorTuple moves = new VectorTuple();

        moves.add(straightAhead(board, upDown(), true));
        if(moves.size()>0 && moves.elementAt(0) != null)
            moves.add(straightAhead(board, upDown()*2, this.getTurns() == 0));

        moves.removeNulls();

        return Move.createMoves(this.getLocation(), moves, board);
    }

    public VectorMove getMeleeAttacks(Board board){
        VectorTuple moves = new VectorTuple();

        moves.add(checkEnemies(board, -1));
        moves.add(checkEnemies(board, 1));

        moves.removeNulls();

        return Move.createMoves(this.getLocation(), moves, board);
    }

    public boolean isOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    /**
     * @return A Tuple in front of the setPawns with a length designated by the offset.
     */
    public Tuple straightAhead(Board board, int offSet, boolean legal){

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

    //Todo: found bug dont account for pawn direction.
    /**
     * @return a tuple of an enemy location, left/right is determined by "offset".
     */
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

    /**
     * Since pawns only move one direction this method is used to determine the direction.
     */
    public int upDown(){
        if(orientation)
            return -1;
        else
            return 1;
    }

    /**
     * peasant
     */
    public void powerUp(Board board){powerUps.passant(board, this, orientation);}

    public void print(){
        System.out.print(String.format("%-9s", this.getType()));
    }

}
