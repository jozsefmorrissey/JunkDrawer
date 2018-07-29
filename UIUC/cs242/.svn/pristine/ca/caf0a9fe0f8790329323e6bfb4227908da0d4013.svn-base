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
 * Rook
 */
public class Rook extends Piece {

    public Rook(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("Rook");
    }

    public VectorMove getMoves(Board board){
        VectorTuple moves = Movement.allPerpendicular(this.getLocation(), board, Manuevours.MOVE);
        this.movesWithinRange(moves);
        return Move.createMoves(this.getLocation(), moves, board);
    }

    public VectorMove getMeleeAttacks(Board board){
        VectorTuple moves = Movement.allPerpendicular(this.getLocation(), board, Manuevours.MELEE_ATTACK);
        this.movesWithinRange(moves);
        return Move.createMoves(this.getLocation(), moves, board);
    }


}
