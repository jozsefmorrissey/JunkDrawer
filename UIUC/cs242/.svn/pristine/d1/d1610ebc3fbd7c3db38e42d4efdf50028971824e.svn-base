package Chess.Pieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;

/**
 * Created by jozsef on 1/26/16.
 * Bishop
 */
public class Bishop extends Piece {

    public Bishop(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("Bishop");
    }

    public VectorMove getMoves(Board board){
        VectorTuple moves = Movement.allDiagonals(this.getLocation(), board);

        this.movesWithinRange(moves);

        return Move.createMoves(this.getLocation(), moves, board);
    }
}
