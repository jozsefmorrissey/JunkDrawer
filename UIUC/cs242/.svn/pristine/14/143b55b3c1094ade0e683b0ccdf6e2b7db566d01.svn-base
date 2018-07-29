package Chess.Tests.Movement;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.Pieces.King;
import Chess.Pieces.Piece;
import Chess.Pieces.Queen;
import Chess.StartingPosition.StartingPosition;
import Chess.StartingPosition.TestingPositions.Castling;

import java.util.Vector;

/**
 * Created by jozsef on 2/4/16.
 */
public class MovementTests {
    @org.junit.Test
    public void castling(){
        Board castling = new Board();
        Tuple rookStart = new Tuple(0,0);
        Tuple rookEnd = new Tuple(3,0);
        Tuple kingEnd = new Tuple(2,0);
        castle(castling, rookStart, rookEnd, kingEnd, 5, " negative direction");

        castling = new Board();
        rookStart = new Tuple(7,0);
        rookEnd = new Tuple(5,0);
        kingEnd = new Tuple(6,0);
        castle(castling, rookStart, rookEnd, kingEnd, 6, " positive direction");
    }

    public void castle(Board castling, Tuple rookStart, Tuple rookEnd, Tuple kingEnd, int index, String direction) {
        Castling test = new Castling();
        test.positions(castling);
        Piece king = castling.getOccupierAt(new Tuple(4,0));
        Piece rook = castling.getOccupierAt(rookStart);
        king.getMoves(castling).elementAt(index).move();
        assert(king.getLocation().compare(kingEnd));
        assert(rook.getLocation().compare(rookEnd));
    }

    @org.junit.Test
    public void pieceMovements(){
        Vector<Piece> pieces = new Vector<>();
        Board clear = new Board();
        pieces.add(new Queen(new Tuple(4,4), clear));
        VectorTuple moves = hardCodedQueenMoves(false);
        assert(verifyMovement(pieces, moves, clear));

        clear = new Board();
        //friendly
        pieces.add(new King(new Tuple(1,1), clear));
        pieces.add(new King(new Tuple(4,2), clear));


        //enemy
        pieces.add(new King(new Tuple(3,5), clear));
        pieces.add(new King(new Tuple(6,4), clear));

        moves = hardCodedQueenMoves(true);
        assert(verifyMovement(pieces, moves, clear));

    }

    public static boolean verifyMovement(final Vector<Piece> pieces, VectorTuple moves, Board clear){
        final StartingPosition vacant = new StartingPosition() {
            @Override
            public void positions(Board board) {
                for(int index = 0; index < pieces.size(); index++){
                    Piece piece = pieces.elementAt(index);
                    setPiece(board, 0, piece);

                }
            }
        };
        vacant.positions(clear);
        Piece testSubject = pieces.elementAt(0);
        VectorMove calcMoves = testSubject.getMoves(clear);
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

    public static VectorTuple hardCodedQueenMoves(boolean enemys){
        VectorTuple out = new VectorTuple();
        //Symmetric Diagonal
        out.add(new Tuple(5,5));
        out.add(new Tuple(6,6));
        out.add(new Tuple(7,7));
        out.add(new Tuple(3,3));
        out.add(new Tuple(2,2));
        if(!enemys) {
            out.add(new Tuple(1, 1));
            out.add(new Tuple(0, 0));
        }
        //Complement Diagonal
        out.add(new Tuple(5,3));
        out.add(new Tuple(6,2));
        out.add(new Tuple(7,1));
        out.add(new Tuple(3,5));
        if(!enemys) {
            out.add(new Tuple(2, 6));
            out.add(new Tuple(1, 7));
        }
        //Row
        if(!enemys) {
            out.add(new Tuple(4, 0));
            out.add(new Tuple(4, 1));
            out.add(new Tuple(4, 2));
        }
        out.add(new Tuple(4,3));
        out.add(new Tuple(4,5));
        out.add(new Tuple(4,6));
        out.add(new Tuple(4,7));

        //Column
        out.add(new Tuple(0,4));
        out.add(new Tuple(1,4));
        out.add(new Tuple(2,4));
        out.add(new Tuple(3,4));
        out.add(new Tuple(5,4));
        out.add(new Tuple(6,4));
        if(!enemys) {
            out.add(new Tuple(7, 4));
        }
        return out;
    }
}
