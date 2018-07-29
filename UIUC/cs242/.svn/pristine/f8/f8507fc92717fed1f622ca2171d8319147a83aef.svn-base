package Chess.StartingPosition;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorTuple;
import Chess.Pieces.*;

/**
 * Created by jozsef on 1/28/16.
 * This class sets up initial positions of game pieces on the board, in the standard chess layout.
 */




public class StandardPosition extends StartingPosition {
    boolean team1 = false;
    boolean Black = true;

    int colors[] = {Integer.MAX_VALUE, 0};

    public void positions(Board board){
        setKings(board);
        setQueens(board);
        setBishops(board);
        setKnights(board);
        setRooks(board);
        pawn(board);
    }

    public void setKings(Board board){
        VectorTuple positions = new VectorTuple();

        positions.add(new Tuple(4,0));

        positions.add(new Tuple(3,7));

        setPieces(board, getColors(1), positions, "setKings");
    }


    public void setQueens(Board board){
        VectorTuple positions = new VectorTuple();

        positions.add(new Tuple(3,0));

        positions.add(new Tuple(4,7));

        setPieces(board, getColors(1), positions, "setQueens");
    }

    public void setBishops(Board board){
        VectorTuple positions = new VectorTuple();

        positions.add(new Tuple(2,0));
        positions.add(new Tuple(5,0));

        positions.add(new Tuple(2,7));
        positions.add(new Tuple(5,7));

        setPieces(board, getColors(2), positions, "setBishops");
    }

    public void setKnights(Board board){
        VectorTuple positions = new VectorTuple();

        positions.add(new Tuple(1,0));
        positions.add(new Tuple(6,0));

        positions.add(new Tuple(1,7));
        positions.add(new Tuple(6,7));

        setPieces(board, getColors(2), positions, "setKnights");
    }

    public void setRooks(Board board){
        VectorTuple positions = new VectorTuple();

        positions.add(new Tuple(0,0));
        positions.add(new Tuple(7,0));

        positions.add(new Tuple(0,7));
        positions.add(new Tuple(7,7));

        setPieces(board, getColors(2), positions, "setRooks");
    }

    public void setPieces(Board board, int color[], VectorTuple position, String type){
        for (int index = 0; index < color.length; index++) {
            Piece piece = type(type, position.elementAt(index), board);
            setPiece(board, color[index], piece);
        }
    }

    public Piece type(String type, Tuple position, Board board){
        switch (type){
            case "setKings":
                return  new King(position, board);
            case "setQueens":
                return new Queen(position, board);
            case "setBishops":
                return new Bishop(position, board);
            case "setKnights":
                return new Knight(position, board);
            case "setRooks":
                return new Rook(position, board);
        }
        return null;
    }

    public void pawn(Board board){
        Tuple whitePosition = new Tuple(0, 1);
        lineUpPawns(board, whitePosition, colors[0], team1);

        Tuple blackPosition = new Tuple(0, 6);
        lineUpPawns(board, blackPosition, colors[1], Black);
    }

    public void lineUpPawns(Board board, Tuple start, int color, boolean orient){
        Tuple worker = new Tuple(start);
        for(int columbs = start.x; columbs < board.getLength(); columbs++){
            Pawn piece = new Pawn(worker, orient, board);
            setPiece(board, color, piece);
            worker = new Tuple(worker.x + 1, worker.y);
        }
    }



    //Creats color array for piece assignment.
    public int[] getColors(int quantityPerTeam){
        int out[] = new int[quantityPerTeam*2];
        for(int index = 0; index<quantityPerTeam*2; index++){
            if(index < quantityPerTeam)
                out[index] = colors[0];
            else
                out[index] = colors[1];
        }
        return out;
    }

}
