package Chess.Board;

import Chess.Movement.Move;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/26/16.
 * This class defines the characteristics that defines the aspects of a square on the game board.
 */
public class Characteristics {
    private boolean outOfBounds = false;
    private Piece occupier;
    private int squareColor = 0;
    private boolean hasBeenReached = false;
    private boolean move = false;
    private boolean attack = false;
    private boolean rangeAttack = false;
    private Move exicute = null;

    public Characteristics(){
        outOfBounds = false;
        squareColor = 0;
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

    public boolean isHasBeenReached() {
        return hasBeenReached;
    }

    public void setHasBeenReached(boolean hasBeenReached) {
        this.hasBeenReached = hasBeenReached;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isRangeAttack() {
        return rangeAttack;
    }

    public void setRangeAttack(boolean rangeAttack) {
        this.rangeAttack = rangeAttack;
    }

    public Move getExicute() {
        return exicute;
    }

    public void setExicute(Move exicute) {
        this.exicute = exicute;
    }
}
