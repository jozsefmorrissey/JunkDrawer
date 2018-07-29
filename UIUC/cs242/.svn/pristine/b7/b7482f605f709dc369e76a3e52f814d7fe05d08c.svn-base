package Chess.Tests.Movement;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/31/16.
 */
public class TeamAllignment {
    public static void checkTeams(Board board, Tuple start, Tuple finish){
        int base = board.getOccupierAt(new Tuple(0,0)).getColor();
        System.out.println("Base: " + base);

        for(int file = start.x; file < finish.y; file++){
            for(int row = start.y; row < finish.y; row++) {
                if (board.getOccupierAt(new Tuple(file, row)).getColor() != base)
                    System.out.println("Traitor at: (" + file + "," + row + ")\tAffiliation: " + board.getOccupierAt(new Tuple(file, row)).getColor());
            }
        }
    }
}
