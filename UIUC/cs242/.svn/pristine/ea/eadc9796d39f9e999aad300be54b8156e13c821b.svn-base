package Chess.MiscClasses;

import Chess.Movement.Move;

import java.util.Vector;

/**
 * Created by jozsef on 1/30/16.
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

    public VectorTuple getFinal(){
        VectorTuple out = new VectorTuple();
        for(int index = 0; index < this.size(); index++){
            out.add(this.elementAt(index).finish);
        }
        return out;
    }

    public void removeNulls(){
        int index = 0;
        while(index < this.size()){
            if(this.elementAt(index).finish == null)
                this.removeElementAt(index);
            else
                index++;
        }
    }

    public boolean canReach(Tuple location){
        for(int index = 0; index < this.size(); index++){
            if(location.compare(this.elementAt(index))){
                return true;
            }
        }
        return false;
    }
}
