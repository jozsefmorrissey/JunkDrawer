package Chess.Tests.Movement.Pieces;

import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorTuple;
import Chess.Pieces.Pawn;

/**
 * Created by jozsef on 1/30/16.
 */
public class PawnTests extends Values {
    public PawnTests(){
        this.type = "pawn";
    }

    @Override
    public void setCornerAvailible() {
        cornerAvailible[0] = new VectorTuple();
        cornerAvailible[1] = new VectorTuple();
        cornerAvailible[1].add(new Tuple(7,6));
    }

    @Override
    public void setNorth() {
        north = new VectorTuple();
        if(!presentEnemy[1] && !presentFriendly[1])
            north.add(new Tuple(4,3));
    }

    @Override
    public void setSouth(){
        south = new VectorTuple();
    }

    @Override
    public void setEast() {
        east = new VectorTuple();
    }

    @Override
    public void setWest() {
        west = new VectorTuple();
    }

    @Override
    public void setNorthEast() {
        northEast = new VectorTuple();
        if(presentEnemy[2])
            northEast.add(new Tuple(4, 2));
    }

    @Override
    public void setNorthWest() {
        northWest = new VectorTuple();
        if(presentEnemy[0])
            northWest.add(new Tuple(2,2));
    }

    @Override
    public void setSouthEast() {
        southEast = new VectorTuple();
    }

    @Override
    public void setSouthWest() {
        southWest = new VectorTuple();
    }

    @Override
    public void setEnemy() {
        enemy = new Pawn(0);
    }

    @Override
    public void setFriend() {
        friend = new Pawn(Integer.MAX_VALUE);
    }
}
