package Chess.Tests;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;
import Chess.Tests.TestingScenarios.Continuing;
import Chess.Tests.TestingScenarios.Stalemate;
import Chess.Tests.TestingScenarios.Victory;

import static org.junit.Assert.assertTrue;

/**
 * Created by jozsef on 1/30/16.
 * Tests the three end of game scenarios.
 * (victory, stalemate, and not over.
 */
public class EndOfGameTests{
    @org.junit.Test
    public void stalemateTest() { // Press Ctrl-Shift-F10 here to run only twoPlusTwo test
        Board stalemate = new Board();
        assertTrue(stalemate.status == Board.GameState.RUNNING);

        Stalemate test1 = new Stalemate();
        test1.positions(stalemate);

        Piece rook = stalemate.getOccupierAt(new Tuple(7,0));
        rook.getManeuvers(stalemate).elementAt(0).move();
        Piece leader = stalemate.findLeader(Integer.MAX_VALUE);

        stalemate.setExicuteAt(new Tuple(1,0), leader.getManeuvers(stalemate).elementAt(0));
        stalemate.setAttackAt(new Tuple(1,0));
        stalemate.changeState(new Tuple(1,0));
        assertTrue(stalemate.status == Board.GameState.STALEMATE);
    }

    @org.junit.Test
    public void gameNotOver(){
        Board continuing = new Board();
        assertTrue(continuing.status == Board.GameState.RUNNING);

        Continuing test1 = new Continuing();
        test1.positions(continuing);

        Piece rook = continuing.getOccupierAt(new Tuple(5,1));
        rook.getManeuvers(continuing).elementAt(7).move();

        assertTrue(continuing.status == Board.GameState.RUNNING);
    }

    @org.junit.Test
    public void victoryTest(){
        Board victory = new Board();
        assertTrue(victory.status == Board.GameState.RUNNING);

        Victory test1 = new Victory();
        test1.positions(victory);
        Piece rook = victory.getOccupierAt(new Tuple(7,0));
        rook.getManeuvers(victory).print();
        rook.getManeuvers(victory).elementAt(0).move();

        Piece leader = victory.findLeader(Integer.MAX_VALUE);


        victory.setExicuteAt(new Tuple(1,0), leader.getManeuvers(victory).elementAt(0));
        victory.setAttackAt(new Tuple(1,0));
            victory.changeState(new Tuple(1,0));

        assertTrue(victory.status == Board.GameState.VICTORY);
    }
}
