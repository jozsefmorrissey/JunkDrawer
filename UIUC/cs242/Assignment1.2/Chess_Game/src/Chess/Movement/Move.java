package Chess.Movement;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Pieces.Piece;

import java.util.Vector;

/**
 * Created by jozsef on 1/28/16.
 * Moves the piece at the start location to the finish location on the board.
 */
public class Move {
    public Board board;
    public Tuple start;
    public Tuple finish;
    public Move next = null;
    public Piece previousOccupant = null;
    public Piece captured = null;

    public Move(Board board, Tuple start, Tuple finish, boolean move){
        this.board = board;
        this.start = start;
        this.finish = finish;
        this.next = null;
        if(move)
            move();
    }

    public Move(Board board, Tuple start, Tuple finish, boolean move, Piece previousOccupant){
        this.board = board;
        this.start = start;
        this.finish = finish;
        this.previousOccupant = previousOccupant;
        this.next = null;
        if(move)
            move();
    }

    /**
     * adds next to a linked list structure for chaining moves together.
     */
    public void add(Move next){
        if(this.next == null)
            this.next = next;
        else
            next.add(next);
    }

    /**
     * Turnes a VectorTuple into a Vector of moves with "start" as the starting
     * location.
     */
    public static VectorMove createMoves(Tuple start, VectorTuple finish, Board board){
        VectorMove out = new VectorMove();
        for(int index = 0; index < finish.size(); index++){
            out.add(new Move(board, start, finish.elementAt(index), false));
        }
        return out;
    }

    /**
     * @return null if the game is over or the current move jeopardizes the leader,
     * otherwise returns a move that would reverse the current move.
     */
    public Move move() {
        Piece activePiece = board.getOccupierAt(start);
        Piece targetPiece = board.getOccupierAt(finish);

        if (activePiece == null){

            return null;
        }

        else if (targetPiece == null || targetPiece.isDead(activePiece.getDamage())){
            captured = board.getOccupierAt(finish);
            makeMove();
            if (leaderNotSafe(activePiece)){
                System.out.println("Move puts leader in jeopardy");
                Move revert = new Move(board, finish, start, false, captured);
                revert.makeMove();
                board.setCurrentTeamIndex();
                return null;
            }
            else {
                activePiece.setTurns(activePiece.getTurns() + 1);
                activePiece.setKills(activePiece.getKills() + 1);
                activePiece.setOrientation(start, finish);
                //this.board.removeFromTeam(finishOccupant);


                System.out.println();
                if(activePiece != null)
                    System.out.println(activePiece.getType() + ": " + activePiece.getTurns());
                if(targetPiece != null)
                    System.out.println(targetPiece.getType() + ": " + targetPiece.getTurns());


                activePiece.powerUp(this.board);
                return recurseReverse();
            }
        }
        activePiece.setOrientation(start, finish);
        return null;
    }

    public Move recurseReverse() {
        Move output;
        if(this.next != null)
            output = this.next.recurseReverse();
        else
            return new Move(board, finish, start, false, captured);

        Move current = output;
        while (current.next != null){
            current = current.next;
        }
        current.next = new Move(board, finish, start, false, captured);
        return output;
    }

    /**
     * Move is designed to allow getManeuvers to be linked together, the loop executes all linked getManeuvers.
     */
    public void makeMove(){
        Move current = this;
        while(current != null) {
            Piece activePlayer = current.board.getOccupierAt(current.start);
            if(current.previousOccupant != null)
                current.previousOccupant.print();
            current.board.setOccupierAt(current.start, current.previousOccupant);
            current.board.setOccupierAt(current.finish, activePlayer);
            //current.board.removeOccupierAt(current.start);
            if(activePlayer != null){
                activePlayer.setLocation(current.finish);
            }
            current = current.next;
        }
    }

    /**
     * @return true if the piece's leader is not safe.
     */
    public boolean leaderNotSafe(Piece piece){

         Piece leader = board.getCurrentLeader();

            if(pieceNotSafe(leader, board))
                return true;

        return false;
    }

    /**
     * @return true if the piece is not safe.
     */
    public boolean pieceNotSafe(Piece piece, Board board){
        if(piece == null)
            return true;
        VectorMove enemyMoves = piece.getEnemyMoves(board);
        enemyMoves.removeNulls();
        return enemyMoves.canReach(piece.getLocation());
    }
}
