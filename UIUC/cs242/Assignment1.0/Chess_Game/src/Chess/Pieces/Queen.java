package Chess.Pieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;

/**
 * Created by jozsef on 1/26/16.
 * Queen
 */
public class Queen extends Piece {


    public Queen(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("Queen");
    }

    public VectorMove getMoves(Board board){
        VectorTuple out = Movement.allPerpAndDiagonals(this.getLocation(), board);
        this.movesWithinRange(out);
        return Move.createMoves(this.getLocation(), out, board);
    }
}
