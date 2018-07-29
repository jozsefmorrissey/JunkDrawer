package Chess.Tests

import Chess.Board.Board
import Chess.MiscClasses.Tuple
import Chess.Pieces.Piece
import Chess.StartingPosition.TestingPositions.Stalemate

/**
 * Created by jozsef on 2/6/16.
 */
class Test extends GroovyTestCase {


    public void stalemateTest() { // Press Ctrl-Shift-F10 here to run only twoPlusTwo test
        Board stalemate = new Board();
        assert(stalemate.status == stalemate.RUNNING);

        Stalemate test1 = new Stalemate();
        test1.positions(stalemate);
        Piece king = stalemate.at(new Tuple(4,0)).occupier;
        king.getMoves(stalemate).elementAt(0).move();

        assert(stalemate.status == stalemate.STALEMATE);
    }
}
