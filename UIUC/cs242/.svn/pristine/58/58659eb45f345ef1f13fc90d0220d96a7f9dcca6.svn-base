package Chess.StartingPosition;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.*;

/**
 * Created by jozsef on 1/28/16.
 * Extend this class to implement custom initial board states.
 */
public abstract class StartingPosition {

    /**
     * Sets the piece's positions.
     */
    public abstract void positions(Board board);

    public void setPiece(Board board, int color, Piece piece){
        piece.setColor(color);
        board.setOccupierAt(piece.getLocation(), piece);
        if(piece.isLeader())
            board.getLeaders().add(piece);
    }
}
