package Chess.Board;

import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/26/16.
 * This class defines the characteristics that defines the aspects of a square on the game board.
 */
public class Characteristics {
    private boolean outOfBounds = false;
    private Piece occupier;
    private int squareColor = 0;

    public Characteristics(){
        outOfBounds = false;
        squareColor = 0;
    }

    public Characteristics(Characteristics characteristics){
        this.outOfBounds = characteristics.outOfBounds;
        this.squareColor = characteristics.squareColor;
        if(characteristics.occupier != null)
            this.occupier = new Piece(characteristics.occupier);
        else
            this.occupier = null;
    }

    public Piece getOccupier(){
        return this.occupier;
    }

    public void setOccupier(Piece piece){
        this.occupier = piece;

    }

    public int getSquareColor(){
        return squareColor;
    }

    public void setSquareColor(int squareColor){
        this.squareColor = squareColor;
    }

    public void setOutOfBounds(boolean outOfBounds){
        this.outOfBounds = outOfBounds;
    }

    public boolean getOutOfBounds(){
        return outOfBounds;
    }

}
