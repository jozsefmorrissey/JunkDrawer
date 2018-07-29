package Chess;

import Chess.Board.Board;
import Chess.Board.War;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;
import Chess.Pieces.Queen;
import Chess.StartingPosition.StandardPosition;


public class Main {

    public void main(String[] args) {
        Board board = new Board();
        StandardPosition std = new StandardPosition();
        std.positions(board);
        Piece queen = new Queen(new Tuple(4,3), board);
        std.setPiece(board, 0, queen);
        //queen.getMoves(board).print();
        //BoardModificationCode.printCharacteristics(board);

        Board war = new War();
        war.printTerain();

    }

}
