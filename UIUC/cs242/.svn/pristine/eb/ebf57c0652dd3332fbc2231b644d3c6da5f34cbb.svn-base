package Chess.Pieces.StandardChessPieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/26/16.
 * Knight
 */
public class Knight extends Piece {
    public Knight(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("Knight");
    }

    public VectorMove getMoves(Board board){
        VectorTuple moves = new VectorTuple();
        Tuple location = new Tuple(this.getLocation());
        moves.add(Movement.oneOclock(location, board));
        moves.add(Movement.twoOclock(location, board));
        moves.add(Movement.fourOclock(location, board));
        moves.add(Movement.fiveOclock(location, board));
        moves.add(Movement.sevenOclock(location, board));
        moves.add(Movement.eightOclock(location, board));
        moves.add(Movement.tenOclock(location, board));
        moves.add(Movement.elevenOclock(location, board));
        return Move.createMoves(location, moves, board);
    }

    public VectorMove getMeleeAttacks(Board board){
        return new VectorMove();
    }
}
