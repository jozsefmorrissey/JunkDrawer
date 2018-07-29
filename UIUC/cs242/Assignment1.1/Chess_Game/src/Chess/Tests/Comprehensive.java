package Chess.Tests;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.Pieces.Piece;
import Chess.Pieces.StandardChessPieces.Knight;
import Chess.Pieces.StandardChessPieces.Pawn;
import Chess.StartingPosition.StandardPosition;

import static org.junit.Assert.assertTrue;

/**
 * Created by jozsef on 2/15/16.
 */
public class Comprehensive {

    @org.junit.Test
    public void comprehensive(){
        Board board = new Board();
        StandardPosition stdPos = new StandardPosition();
        stdPos.positions(board);
        
        moveKnight(board);
        movePawn(board);
        moveKnight2(board);
        moveBishop(board);
        moveKnight3(board);
        moveQueen(board);
        movePawn2(board);
    }

    private void movePawn2(Board board) {
        Piece pawn = board.getOccupierAt(new Tuple(7,1));
        pawn.getManeuvers(board).elementAt(1).move();
        assertTrue(board.getOccupierAt(new Tuple(7,3)).getClass() == pawn.getClass());
        pawn.getManeuvers(board).elementAt(0).move();
        assertTrue(board.getOccupierAt(new Tuple(7,4)).getClass() == pawn.getClass());
    }

    private void moveQueen(Board board) {
        Piece queen = board.getOccupierAt(new Tuple(4,7));
        queen.getManeuvers(board).elementAt(4).move();
        assertTrue(board.getOccupierAt(new Tuple(4,2)).getClass() == queen.getClass());
    }

    private void moveKnight3(Board board) {
        Piece knight = board.getOccupierAt(new Tuple(4,4));
        knight.getManeuvers(board).elementAt(2).move();
        assertTrue(board.getOccupierAt(new Tuple(6,5)).getClass() == knight.getClass());
    }

    private void moveBishop(Board board) {
        Piece bishop = board.getOccupierAt(new Tuple(5,7));
        bishop.getManeuvers(board).elementAt(2).move();
        assertTrue(board.getOccupierAt(new Tuple(2,4)).getClass() == bishop.getClass());
    }

    private void moveKnight2(Board board) {
        Piece knight = board.getOccupierAt(new Tuple(5,2));
        knight.getManeuvers(board).elementAt(3).move();
        assertTrue(board.getOccupierAt(new Tuple(4,4)).getClass() == knight.getClass());
        assertTrue(board.getOccupierAt(new Tuple(4,4)).getClass() != Pawn.class);
    }

    private void movePawn(Board board) {
        Piece pawn = board.getOccupierAt(new Tuple(4,6));
        pawn.getManeuvers(board).elementAt(1).move();
        assertTrue(board.getOccupierAt(new Tuple(4,4)).getClass() == pawn.getClass());
    }

    private void moveKnight(Board board) {
        Piece knight = board.getOccupierAt(new Tuple(6,0));
        VectorMove moves = knight.getManeuvers(board);
        moves.elementAt(1).move();
        assertTrue(board.getOccupierAt(new Tuple(5,2)).getClass() == knight.getClass());
    }
}
