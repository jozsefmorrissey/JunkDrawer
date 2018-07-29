package Chess.Pieces.StandardChessPieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Movement.Move;
import Chess.MiscClasses.Movement;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/26/16.
 * GodKing
 */
public class King extends Piece {

    public King(){}

    public King(Tuple loc, Board board){
        this.setLocation(loc);
        this.setType("King");
        this.setLeader(true);
    }

    public VectorMove getMoves(Board board){
        VectorTuple moves = Movement.cardinalSteps(this.getLocation(), board);
        VectorMove out = Move.createMoves(this.getLocation(), moves, board);

        VectorMove castle = castling(board);

        if(castle != null)
            out.addAll(castle);

        return out;
    }

    public VectorMove getMeleeAttacks(Board board){
        return new VectorMove();
    }


    /**
     * @return Available castle moves.
     */
    public VectorMove castling(Board board) {
        if (this.getTurns() > 0)
            return null;
        VectorMove out = new VectorMove();

        Tuple location = this.getLocation();
        Tuple rook1Location = new Tuple(0, location.y);
        Piece rook1 = board.getOccupierAt(rook1Location);
        Tuple rook2Location = new Tuple(board.getLength() - 1, location.y);
        Piece rook2 = board.getOccupierAt(rook2Location);
        if (rook1 != null && rook1.getTurns() == 0 && checkClear(rook1.getLocation(), location, board)) {
            out.add(castle(rook1.getLocation(), board, -1));
        }
        if (rook2 != null && rook2.getTurns() == 0 && checkClear(location, rook2.getLocation(), board)){
            out.add(castle(rook2.getLocation(), board, 1));
        }
        return out;
    }

    /**
     * @return true if the path between the king and a given rook is clear.
     */
    public boolean checkClear(Tuple start, Tuple end, Board board){
        Tuple iter = new Tuple(start);
        iter.x++;
        while(iter.x != end.x){
            Piece occ = board.getOccupierAt(iter);
            if(occ != null)
                return false;
            iter.x++;
        }
        return true;
    }

    /**
     * @return a linked list castle move.
     */
    public Move castle(Tuple rook, Board board, int offSet){
        Tuple finish = new Tuple(this.getLocation().x + offSet, this.getLocation().y);
        Move rookMove = new Move(board, rook, finish, false);
        Tuple kingFinish = new Tuple(finish.x + offSet, finish.y);
        Move kingMove = new Move(board, this.getLocation(), kingFinish, false);
        kingMove.add(rookMove);
        return kingMove;
    }

}
