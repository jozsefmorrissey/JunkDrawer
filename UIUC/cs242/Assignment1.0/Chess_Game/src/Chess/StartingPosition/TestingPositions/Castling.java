package Chess.StartingPosition.TestingPositions;

import Chess.Board.Board;
import Chess.StartingPosition.StandardPosition;

/**
 * Created by jozsef on 2/2/16.
 */
public class Castling extends StandardPosition{
    public void positions(Board board){
        setKings(board);
        setRooks(board);
    }
}
