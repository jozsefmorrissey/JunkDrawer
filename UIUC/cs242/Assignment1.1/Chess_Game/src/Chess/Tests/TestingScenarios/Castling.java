package Chess.Tests.TestingScenarios;

import Chess.Board.Board;
import Chess.StartingPosition.StandardPosition;

/**
 * Created by jozsef on 2/2/16.
 * Creates a board with kings and rooks to run castling tests.
 */
public class Castling extends StandardPosition{
    public void positions(Board board){
        setKings(board);
        setRooks(board);
    }
}
