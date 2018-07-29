package Chess.Pieces.StandardChessPieces;

import Chess.Board.Board;
import Chess.Manuevours;
import Chess.MiscClasses.*;
import Chess.Movement.Move;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/26/16.
 * Queen
 */
public class Queen extends Piece {
    private Piece formerlyKnownAs = null;


    public Queen(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("Queen");
    }

    public Queen(Tuple loc, Board board, int color){
        this.setLocation(loc);
        this.setType("Queen");
        this.setColor(color);
    }

    public Queen(Tuple loc, Board board, int color, Piece fka){
        this.setLocation(loc);
        this.setType("Queen");
        this.setColor(color);
        this.setFormerlyKnownAs(fka);
    }

    public VectorMove getMoves(Board board){
        VectorTuple moves = Movement.allPerpendicularsAndDiagonals(this.getLocation(), board, Manuevours.MOVE);
        this.movesWithinRange(moves);

        VectorMove output = Move.createMoves(this.getLocation(), moves, board);

        return output;
    }

    public VectorMove getMeleeAttacks(Board board){
        VectorTuple moves = Movement.allPerpendicularsAndDiagonals(this.getLocation(), board, Manuevours.MELEE_ATTACK);
        this.movesWithinRange(moves);

        VectorMove output = Move.createMoves(this.getLocation(), moves, board);

        return output;
    }

    public Piece getFormerlyKnownAs() {
        return formerlyKnownAs;
    }

    public void setFormerlyKnownAs(Piece formerlyKnownAs) {
        this.formerlyKnownAs = formerlyKnownAs;
    }

    @Override
    public void powerUp(Board board) {
        powerUps.demote(board, this, getFormerlyKnownAs());
    }
}
