package Chess.MiscClasses;

import Chess.Board.Board;
import Chess.Pieces.StandardChessPieces.Pawn;
import Chess.Pieces.Piece;
import Chess.Pieces.StandardChessPieces.Queen;

/**
 * Created by jozsef on 1/30/16.
 *
 */
public abstract class powerUps {

    /**
     *  Promotes a setPawns to a queen
     */
    public static void passant(Board board, Piece pawn, boolean orientation) {
        if(pawn.powerUpTurn == -1){
            promote(board, pawn, orientation);
        }

    }

    public static void demote(Board board, Piece queen, Piece pawn) {
        if(queen.getTurns() == -1){
            board.setOccupierAt(queen.getLocation(), pawn);
            board.removeFromTeam(queen);
        }
    }

    public static void promote(Board board, Piece piece, boolean orientation){

        Tuple location = piece.getLocation();
        Piece queen = new Queen(location, board, piece.getColor(), piece);
        if (piece.getClass() == Pawn.class)
            if (orientation && location.y == 0) {
                board.setOccupierAt(location, queen);
                piece.powerUpTurn = piece.getTurns();
            }
            else if (!orientation && location.y == board.getHeight() - 1){
                board.setOccupierAt(location, queen);
                piece.powerUpTurn = piece.getTurns();
            }

        queen.setTurns(0);

    }

}
