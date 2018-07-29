package Chess.MiscClasses;

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


    public void print(){
        for(int index = 0; index < this.size(); index++){
            if(this.elementAt(index) != null) {
                String label = "Index " + index + ": ";
                this.elementAt(index).print(label);
            }
            else{
                System.out.println("null");
            }
        }
        System.out.println();
    }

    public void removeAfter(int index){
        while(index < this.size()){
            this.removeElementAt(index);
        }
    }

    public void removeNulls(){
        int index = 0;
        while(index < this.size()){
            if(this.elementAt(index) == null)
                this.removeElementAt(index);
            else
                index++;
        }
    }

}
