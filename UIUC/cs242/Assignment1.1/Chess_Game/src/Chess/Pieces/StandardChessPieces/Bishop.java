package Chess.Pieces.StandardChessPieces;

import Chess.Board.Board;
import Chess.Manuevours;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;
import Chess.Pieces.Piece;

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
        VectorTuple moves = Movement.allDiagonals(this.getLocation(), board, Manuevours.MOVE);

        this.movesWithinRange(moves);

        VectorMove output = Move.createMoves(this.getLocation(), moves, board);
        //output.addAll(Move.createMoves(this.getLocation(), moves, board));
        //output.addAll(Move.createMoves(this.getLocation(), moves, board));

        return output;
    }

    public VectorMove getMeleeAttacks(Board board){
        VectorTuple moves = Movement.allDiagonals(this.getLocation(), board, Manuevours.MELEE_ATTACK);

        this.movesWithinRange(moves);

        VectorMove output = Move.createMoves(this.getLocation(), moves, board);
        //output.addAll(Move.createMoves(this.getLocation(), moves, board));
        //output.addAll(Move.createMoves(this.getLocation(), moves, board));

        return output;
    }
}
