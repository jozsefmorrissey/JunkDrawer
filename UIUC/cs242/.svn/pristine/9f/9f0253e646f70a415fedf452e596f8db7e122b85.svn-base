package Chess.Pieces.CustomChessPieces;

import Chess.Board.Board;
import Chess.Manuevours;
import Chess.MiscClasses.Movement;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 2/8/16.
 * This piece an move forward right and left like a queen but can only move backwards like a knight.
 */
public class QueenCentaur extends Piece{
    public QueenCentaur(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("QueenCentaur");
    }

    public VectorMove getMoves(Board board){

        VectorTuple moves = getPossibleMoves(board);

        return Move.createMoves(this.getLocation(), moves, board);
    }

    public VectorTuple getPossibleMoves(Board board) {
        VectorTuple moves = new VectorTuple();
        moves.addAll(Movement.AllInDirection(this.getLocation(), board, "stepNorthEast", Manuevours.MOVE));
        moves.addAll(Movement.AllInDirection(this.getLocation(), board, "stepNorthWest", Manuevours.MOVE));
        moves.addAll(Movement.AllInDirection(this.getLocation(), board, "stepNorth", Manuevours.MOVE));
        moves.addAll(Movement.AllInDirection(this.getLocation(), board, "stepEast", Manuevours.MOVE));
        moves.addAll(Movement.AllInDirection(this.getLocation(), board, "stepWest", Manuevours.MOVE));

        moves.add(Movement.fourOclock(this.getLocation(), board));
        moves.add(Movement.fiveOclock(this.getLocation(), board));
        moves.add(Movement.sevenOclock(this.getLocation(), board));
        moves.add(Movement.eightOclock(this.getLocation(), board));
        return moves;
    }

    public VectorMove getMeleeAttacks(Board board){
        VectorTuple moves = Movement.allPerpendicularsAndDiagonals(this.getLocation(), board, Manuevours.MELEE_ATTACK);

        return Move.createMoves(this.getLocation(), moves, board);
    }

}
