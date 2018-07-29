package Chess.MiscClasses;

import Chess.Movement.Move;
import Chess.Pieces.Piece;

import java.util.Vector;

/**
 * Created by jozsef on 1/30/16.
 * Extention of Vector<Move> class to implement further functionality.
 */
public class VectorMove extends Vector<Move> {
    public void print(){
        for(int index = 0; index < this.size(); index++){
            Tuple location = this.elementAt(index).finish;
            if(location != null)
                location.print("Index " + index);
            else
                System.out.println("null");
        }
    }

    /**
     * @return a VectorTuple of all the destinations for "this" Vector of moves.
     */
    public VectorTuple getFinal(){
        VectorTuple out = new VectorTuple();
        for(int index = 0; index < this.size(); index++){
            out.add(this.elementAt(index).finish);
        }
        return out;
    }

    /**
     * Used for cleanup.
     */
    public void removeNulls(){
        int index = 0;
        while(index < this.size()){
            if(this.elementAt(index).finish == null)
                this.removeElementAt(index);
            else
                index++;
        }
    }

    /**
     * @return true iff the calling Vector has a move which will end on "location".
     */
    public boolean canReach(Tuple location){
        for(int index = 0; index < this.size(); index++){
            if(location.compare(this.elementAt(index))){
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean add(Move move) {
        boolean output = super.add(move);
        Vector<Vector<Piece>> CapturedTeams = move.board.getCaptured();
        Vector<Vector<Piece>> ActiveTeams = move.board.getTeams();

        captivityActivity(move, ActiveTeams, CapturedTeams);
        return output;
    }

    @Override
    public synchronized void removeElementAt(int index) {
        Move move = this.elementAt(index);
        Vector<Vector<Piece>> CapturedTeams = move.board.getCaptured();
        Vector<Vector<Piece>> ActiveTeams = move.board.getTeams();

        captivityActivity(move, CapturedTeams, ActiveTeams);
        super.removeElementAt(index);
    }

    public void captivityActivity(Move move, Vector<Vector<Piece>> searchTeams, Vector<Vector<Piece>> placementTeams) {
        Piece piece = move.previousOccupant;
        if(piece == null)
            return ;

        for(int teamIndex = 0; teamIndex < searchTeams.size(); teamIndex++){
            Vector<Piece> searchTeam = searchTeams.elementAt(teamIndex);
            Vector<Piece> placementTeam = placementTeams.elementAt(teamIndex);
            Piece activePiece = searchRemoveFromTeam(searchTeam, move);
            if(activePiece != null){
                placementTeam.add(activePiece);
                break;
            }
        }
    }

    public Piece searchRemoveFromTeam(Vector<Piece> team, Move move){
        Piece basis = move.previousOccupant;
        for(int index = 0; index < team.size(); index++){
            Piece piece = team.elementAt(index);
            if(piece.getLocation().compare(basis.getLocation()) && basis.getColor() == piece.getColor() && basis.getType() == piece.getType()){
                team.removeElementAt(index);
                return piece;
            }
        }
        return null;
    }
}
