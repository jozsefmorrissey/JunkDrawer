package Chess.Movement;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 2/3/16.
 */
public class RangeAttack extends Move {

    public RangeAttack(Board board, Tuple start, Tuple finish, boolean move){
        this.board = board;
        this.start = start;
        this.finish = finish;
        this.next = null;
        if(move)
            move();
    }

    @Override
    public Move move() {
        Piece activePlayer = board.getOccupierAt(start);
        Piece targetPlayer = board.getOccupierAt(finish);
        activePlayer.setOrientation(start, finish);
        if(targetPlayer.isDead(activePlayer.getDamage()))
            return this;
        return null;
    }
}
