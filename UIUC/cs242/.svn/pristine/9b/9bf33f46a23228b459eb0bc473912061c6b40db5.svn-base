package Chess.Tests;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;
import Chess.StartingPosition.StandardPosition;
import org.junit.Test;

/**
 * Created by jozsef on 1/31/16.
 * Verifies the teams on each side of the standard board are compoased of friendlies
 */
public class TeamAllignment {
    @org.junit.Test
    public void checkTeams(){
        Board board = new Board();
        StandardPosition cur = new StandardPosition();
        cur.positions(board);
        int base = board.getOccupierAt(new Tuple(0,0)).getColor();

        for(int file = 0; file < 8; file++){
            for(int row = 0; row < 2; row++) {
                if (board.getOccupierAt(new Tuple(file, row)).getColor() != base)
                    System.out.println("Traitor at: (" + file + "," + row + ")\tAffiliation: " + board.getOccupierAt(new Tuple(file, row)).getColor());
            }
        }
    }
}
