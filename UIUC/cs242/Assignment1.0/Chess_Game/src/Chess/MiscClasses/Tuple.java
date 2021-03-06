package Chess.MiscClasses;

import Chess.Movement.Move;

/**
 * Created by jozsef on 1/27/16.
 * Location tuple
 */
public class Tuple {
    public int x = 0;
    public int y = 0;

    public Tuple(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    public Tuple(Tuple tup){
        this.x = tup.x;
        this.y = tup.y;
    }

    public Tuple copy(){
        return new Tuple(this.x, this.y);
    }

    public void print(String name){
        System.out.println(name + "\ty: " + this.y + "\tx: " + this.x);
    }

    public boolean compare(Move move){
        return compare(move.finish);
    }

    public boolean compare(Tuple other){
        if(other == null)
            return false;
        return this.x == other.x && this.y == other.y;
    }

    public int distance(Tuple location){
        int xDist = this.x - location.x;
        int yDist = this.y - location.y;
        return ((yDist*yDist) + (xDist*xDist));
    }
}

