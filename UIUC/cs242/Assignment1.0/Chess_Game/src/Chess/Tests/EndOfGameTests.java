package Chess.Tests;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;
import Chess.StartingPosition.TestingPositions.Continuing;
import Chess.StartingPosition.TestingPositions.Stalemate;
import Chess.StartingPosition.TestingPositions.Victory;

/**
 * Created by jozsef on 1/30/16.
 */
public class EndOfGameTests {
    @org.junit.Test
    public void stalemateTest() { // Press Ctrl-Shift-F10 here to run only twoPlusTwo test
        Board stalemate = new Board();
        assert(stalemate.status == stalemate.RUNNING);

        Stalemate test1 = new Stalemate();
        test1.positions(stalemate);

        Piece rook = stalemate.getOccupierAt(new Tuple(7,0));
        rook.getMoves(stalemate).elementAt(0).move();

        assert(stalemate.status == stalemate.STALEMATE);
    }

    @org.junit.Test
    public void gameNotOver(){
        Board continuing = new Board();
        assert(continuing.status == continuing.RUNNING);

        Continuing test1 = new Continuing();
        test1.positions(continuing);

        Piece rook = continuing.getOccupierAt(new Tuple(5,1));
        rook.getMoves(continuing).elementAt(7).move();

        assert(continuing.status == continuing.RUNNING);
    }

    @org.junit.Test
    public void victoryTest(){
        Board victory = new Board();
        assert(victory.status == victory.RUNNING);

        Victory test1 = new Victory();
        test1.positions(victory);
        Piece rook = victory.getOccupierAt(new Tuple(7,0));
        rook.getMoves(victory).print();
        rook.getMoves(victory).elementAt(0).move();
        assert(victory.status == victory.VICTORY);
    }
}
