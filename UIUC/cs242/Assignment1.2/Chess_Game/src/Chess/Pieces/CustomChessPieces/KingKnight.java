package Chess.Pieces.CustomChessPieces;

import Chess.Board.Board;
import Chess.MiscClasses.Movement;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 2/8/16.
 * This piece combines all the movements of a king and knight. Might make it a little harder to checkmate.
 */
public class KingKnight extends Piece{

    public KingKnight(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("KingKnight");
        this.setLeader(true);
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

        moves.addAll(Movement.cardinalSteps(this.getLocation(), board));


        return Move.createMoves(location, moves, board);
    }

}
