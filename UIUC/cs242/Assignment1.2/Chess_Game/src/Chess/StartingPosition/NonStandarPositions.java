package Chess.StartingPosition;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorTuple;
import Chess.Pieces.CustomChessPieces.KingKnight;
import Chess.Pieces.CustomChessPieces.QueenCentaur;
import Chess.Pieces.Piece;

import java.util.Vector;

/**
 * Created by jozsef on 2/17/16.
 */
public class NonStandarPositions extends StandardPosition{
    @Override
    public void positions(Board board) {
        super.positions(board);
    }

    @Override
    public void setKings(Board board) {
        VectorTuple positions = new VectorTuple();

        positions.add(new Tuple(4,0));

        positions.add(new Tuple(3,7));

        setPieces(board, getColors(1), positions, "setKingKnights");
    }

    @Override
    public void setQueens(Board board) {
        VectorTuple positions = new VectorTuple();

        positions.add(new Tuple(3,0));

        positions.add(new Tuple(4,7));

        setPieces(board, getColors(1), positions, "setQueenCentaurs");
    }
}
