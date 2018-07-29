package Chess.MiscClasses;

import Chess.Board.Board;
import Chess.Pieces.Piece;

import java.util.Vector;

/**
 * Created by jozsef on 1/27/16.
 * Extended Vector<Tuple> to allow increased functional.
 */
public class VectorTuple extends Vector <Tuple> {

    public VectorTuple(VectorTuple original){
        for(int index = 0; index < original.size(); index++){
            this.addElement(original.elementAt(index));
        }
    }

    public VectorTuple(){};

    /**
     * removes all elements after a given index.
     */
    public void removeAfter(int index){
        while(index < this.size()){
            this.removeElementAt(index);
        }
    }

    /**
     * Used for cleanup.
     */
    public void removeNulls(){
        int index = 0;
        while(index < this.size()){
            if(this.elementAt(index) == null)
                this.removeElementAt(index);
            else
                index++;
        }
    }

    public void removeOccupiedLocations(Board board, int color){
        for(int index = 0; index < this.size(); index++){
            Piece occupier = board.getOccupierAt(this.elementAt(index));
            if(occupier != null){
                this.removeElementAt(index);
                index--;
            }
        }
    }
}
