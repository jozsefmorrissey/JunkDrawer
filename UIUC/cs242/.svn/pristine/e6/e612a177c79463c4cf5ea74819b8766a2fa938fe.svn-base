package Chess.MiscClasses;

import Chess.Board.Board;
import Chess.Pieces.Pawn;
import Chess.Pieces.Piece;
import Chess.Pieces.Queen;

/**
 * Created by jozsef on 1/30/16.
 *
 */
public abstract class powerUps {

    //Promotes a pawn to a queen
    public static void passant(Board board, Tuple location, boolean orientation){
        Piece piece = board.getOccupierAt(location);
        if(piece.getClass() == Pawn.class)
            if(orientation && location.y == 0)
                board.setOccupierAt(location, new Queen(location, board));
            else if(!orientation && location.y == board.getHeight() - 1)
                board.setOccupierAt(location, new Queen(location, board));
    }

}
