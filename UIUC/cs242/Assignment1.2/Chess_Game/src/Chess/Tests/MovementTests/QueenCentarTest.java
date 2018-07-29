package Chess.Tests.MovementTests;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.Pieces.CustomChessPieces.QueenCentaur;
import Chess.Pieces.Piece;
import Chess.StartingPosition.StandardPosition;

import static junit.framework.Assert.assertTrue;

/**
 * Created by jozsef on 2/8/16.
 * Verifies the QueenCentar movements.
 */
public class QueenCentarTest {
    @org.junit.Test
    public void movementTest() {
        Board board = new Board();
        StandardPosition std = new StandardPosition();
        //std.positions(board);
        Piece queen = new QueenCentaur(new Tuple(4, 4), board);
        std.setPiece(board, 0, queen);

        assertTrue(verifyMovement(queen, queen.getMoves(board)));

    }

    public boolean verifyMovement(Piece piece, VectorMove calcMoves){

        VectorTuple moves = hardCodedQueenCentarMoves();
        for(int index = 0; index < moves.size(); index++){
            Tuple move = moves.elementAt(index);
            for(int element = 0; element < calcMoves.size(); element++){
                Move calcmove = calcMoves.elementAt(element);
                if(move.compare(calcmove)){
                    calcMoves.removeElementAt(element);
                    break;
                }
            }
        }
        return calcMoves.size() == 0;
    }

    public VectorTuple hardCodedQueenCentarMoves(){
        VectorTuple out = new VectorTuple();
        //Symmetric Diagonal
        out.add(new Tuple(3,3));
        out.add(new Tuple(2,2));
        out.add(new Tuple(1, 1));
        out.add(new Tuple(0, 0));

        //Complement Diagonal
        out.add(new Tuple(5,3));
        out.add(new Tuple(6,2));
        out.add(new Tuple(7,1));

        out.add(new Tuple(4, 0));
        out.add(new Tuple(4, 1));
        out.add(new Tuple(4, 2));
        out.add(new Tuple(4,3));


        //Column
        out.add(new Tuple(0,4));
        out.add(new Tuple(1,4));
        out.add(new Tuple(2,4));
        out.add(new Tuple(3,4));
        out.add(new Tuple(5,4));
        out.add(new Tuple(6,4));
        out.add(new Tuple(7,4));

        out.add(new Tuple(6,5));
        out.add(new Tuple(5,6));
        out.add(new Tuple(3,6));
        out.add(new Tuple(2,5));

        return out;
    }
}
