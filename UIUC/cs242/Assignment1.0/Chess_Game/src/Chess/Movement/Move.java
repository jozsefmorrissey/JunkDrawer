package Chess.Movement;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Pieces.Piece;

import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

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

    public Move(){}

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

    public void add(Move next){
        if(this.next == null)
            this.next = next;
        else
            next.add(next);
    }


    public static VectorMove createMoves(Tuple start, VectorTuple finish, Board board){
        VectorMove out = new VectorMove();
        for(int index = 0; index < finish.size(); index++){
            out.add(new Move(board, start, finish.elementAt(index), false));
        }
        return out;
    }

    //This function returns null if the game is over or the current move jeopardizes
    //the leader, otherwise returns a move that would reverse the current move.
    public Move move() {
        Piece activePiece = board.getOccupierAt(start);
        Piece targetPiece = board.getOccupierAt(finish);

        if (leaderNotSafe(activePiece)){
            System.out.println("Move puts leader in jeopardy");
            return null;
        }
        else if (targetPiece == null || targetPiece.isDead(activePiece.getDamage())){
            Piece finishOccupant = board.getOccupierAt(finish);
            makeMove();
            activePiece.setKills(activePiece.getKills() + 1);
            activePiece.setOrientation(start, finish);
            if (gameOver(activePiece)) return null;
            return new Move(board, finish, start, false, finishOccupant);
        }
        activePiece.setOrientation(start, finish);
        return null;
    }

    public boolean gameOver(Piece activePiece) {
        Vector<Piece> enemys = board.getOtherTeam(activePiece);
        Piece enemy = enemys.elementAt(0);
        if(noValidMoves(enemy)){
            if(leaderNotSafe(enemy)) {
                board.status = board.VICTORY;
                System.out.println("GAME OVER: " + activePiece.getColor() + " Won!");
            }
            else {
                board.status = board.STALEMATE;
                System.out.println("GAME OVER: Its a stalemate");
            }
            return true;
        }
        return false;
    }


    public void checkGameOver(Piece lastMoved){

    }

    //Move is designed to allow getMoves to be linked together, the loop executes all linked getMoves.
    public void makeMove(){
        Move current = this;
        while(current != null) {
            Piece activePlayer = current.board.getOccupierAt(current.start);
            activePlayer.setLocation(current.finish);
            current.board.setOccupierAt(current.finish, activePlayer);
            current.board.setOccupierAt(current.start, current.previousOccupant);
            activePlayer.powerUp(current.board);
            current = current.next;
            activePlayer.setTurns(activePlayer.getTurns() + 1);
        }
    }

    public void makeMove(Tuple start, Tuple finish, Board board){
        board.getOccupierAt(start).setLocation(finish);
        board.setOccupierAt(finish, board.getOccupierAt(start));
        board.setOccupierAt(start, previousOccupant);
    }


    public boolean leaderNotSafe(Piece piece){
        for(int index = 0; index < board.getLeaders().size(); index++){
            Board simulation = new Board(board);
            Piece leader = simulation.getOccupierAt(piece.getLocation()).findLeader(board);
            if(leader.getColor() == piece.getColor()){
                if(pieceSafe(leader))
                    return true;
            }
        }
        return false;
    }

    public boolean pieceSafe(Piece piece){
        VectorMove enemyMoves = piece.getEnemyMoves(this.board);
        return enemyMoves.canReach(piece.getLocation());
    }


    //This function creates a simulated board for all possible friendly getMoves to verify whether
    //there exists at least one in which you do not put your leader (king) in harms way.
    //The structure only verifies the well being of a single leader, for multiple leaders
    //other functionality may need to be added.
    public boolean noValidMoves(Piece piece){
        VectorMove friendlyMoves = piece.getMyMoves(this.board);
        int teamColor = piece.getColor();
        for(int index = 0; index < friendlyMoves.size(); index++) {
            Board simulation = new Board(this.board);
            Piece leader = simulation.findLeader(teamColor);
            VectorMove moves = leader.getMyMoves(this.board);
            Move move = moves.elementAt(index);

            if (validMove(simulation, leader, move))
                return false;
        }
        return true;
    }

    //Tests that a particular move does not make the leader vulnerable.
    public Boolean validMove(Board simulation, Piece leader, Move move) {
        if(move == null)
            return false;
        VectorMove enemyMoves = leader.getEnemyMoves(this.board);
        move.board = simulation;
        move.makeMove();

        if(!enemyMoves.canReach(move.finish)) {
            return true;
        }
        return false;
    }

}
