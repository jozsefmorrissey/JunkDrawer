package Chess.StartingPosition.TestingPositions;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;
import Chess.Pieces.Rook;
import Chess.StartingPosition.StandardPosition;

/**
 * Created by jozsef on 2/2/16.
 */
public class Stalemate extends StandardPosition{
    public void positions(Board board){
        setKings(board);
        setRooks(board);
    }

    public void setRooks(Board board){
        Piece friendlyKing = board.getOccupierAt(new Tuple(3,7));
        Piece rook1 = new Rook(new Tuple(3,6), board);
        Piece rook2 = new Rook(new Tuple(5,6), board);
        Piece rook3 = new Rook(new Tuple(7,0), board);
        int kingColor = friendlyKing.getColor();
        setPiece(board, kingColor, rook1);
        setPiece(board, kingColor, rook2);
        setPiece(board, kingColor, rook3);
    }

}
