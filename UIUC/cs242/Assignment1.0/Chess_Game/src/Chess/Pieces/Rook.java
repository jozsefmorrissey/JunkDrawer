package Chess.Pieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;

/**
 * Created by jozsef on 1/26/16.
 * Rook
 */
public class Rook extends Piece {

    public Rook(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("Rook");
    }

    public VectorMove getMoves(Board board){
        VectorTuple moves = Movement.allPerpendicular(this.getLocation(), board);
        this.movesWithinRange(moves);
        return Move.createMoves(this.getLocation(), moves, board);
    }


}
