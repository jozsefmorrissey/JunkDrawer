package Chess.Tests;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.Pieces.Piece;
import Chess.StartingPosition.StandardPosition;

import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

import static org.junit.Assert.assertTrue;

/**
 * Created by jozsef on 2/10/16.
 */
public class ChessInitialSetup {
    Board board = new Board();
    StandardPosition std = new StandardPosition();

    @org.junit.Test
    public void testInitialSetup(){
        Vector<typeLocation> piecePos = hardCodedLocations();
        std.positions(board);
        for(int index = 0; index < piecePos.size(); index++){
            Tuple location = piecePos.elementAt(index).location;
            String type = piecePos.elementAt(index).type;
            Piece occupier = this.board.getOccupierAt(location);
            if(occupier != null && !occupier.getType().equals(type)){
                System.out.println("Error: Piece located at (" + location.x + "," + location.y + ") is incorrect");
            }
            assertTrue(occupier == null || occupier.getType().equals(type));
        }

        for(int row = 2; row < 6; row++){
            for(int column = 0; column < 8; column++){
                Piece occ = board.getOccupierAt(new Tuple(column, row));
                if(occ != null){
                    System.out.println("Error: Piece located at (" + column + "," + row + ") is incorrect");
                }
                assertTrue(occ == null);
            }
        }
    }

    public Vector<typeLocation> hardCodedLocations(){
        Vector<typeLocation> output = new Vector<>();

        output.add(new typeLocation(0,0, "Rook"));
        output.add(new typeLocation(7,0, "Rook"));
        output.add(new typeLocation(7,7, "Rook"));
        output.add(new typeLocation(0,7, "Rook"));

        output.add(new typeLocation(2,0, "Bishop"));
        output.add(new typeLocation(5,0, "Bishop"));
        output.add(new typeLocation(2,7, "Bishop"));
        output.add(new typeLocation(5,7, "Bishop"));

        output.add(new typeLocation(1,0, "Knight"));
        output.add(new typeLocation(6,0, "Knight"));
        output.add(new typeLocation(1,7, "Knight"));
        output.add(new typeLocation(6,7, "Knight"));

        output.add(new typeLocation(3,0, "Queen"));
        output.add(new typeLocation(4,7, "Queen"));

        output.add(new typeLocation(4,0, "King"));
        output.add(new typeLocation(3,7, "King"));

        output.add(new typeLocation(0,1, "Pawn"));
        output.add(new typeLocation(1,1, "Pawn"));
        output.add(new typeLocation(2,1, "Pawn"));
        output.add(new typeLocation(3,1, "Pawn"));
        output.add(new typeLocation(4,1, "Pawn"));
        output.add(new typeLocation(5,1, "Pawn"));
        output.add(new typeLocation(6,1, "Pawn"));
        output.add(new typeLocation(7,1, "Pawn"));

        output.add(new typeLocation(0,6, "Pawn"));
        output.add(new typeLocation(1,6, "Pawn"));
        output.add(new typeLocation(2,6, "Pawn"));
        output.add(new typeLocation(3,6, "Pawn"));
        output.add(new typeLocation(4,6, "Pawn"));
        output.add(new typeLocation(5,6, "Pawn"));
        output.add(new typeLocation(6,6, "Pawn"));
        output.add(new typeLocation(7,6, "Pawn"));

        return output;
    }
}

class typeLocation{
    Tuple location;
    String type;

    public typeLocation(int x, int y, String type){
        this.location = new Tuple(x,y);
        this.type = type;
    }
}


