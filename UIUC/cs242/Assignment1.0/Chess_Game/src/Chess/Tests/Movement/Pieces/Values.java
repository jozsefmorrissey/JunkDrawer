package Chess.Tests.Movement.Pieces;

import Chess.Board.Board;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/30/16.
 * S: indicates the location of the three starting points.
 * E: indicates the 8 positions that enemies will be placed at in all combinations
 * R: indicates the spaces a row traversing piece can move
 * F: indicates the spaces a file traversing piece can move
 * D: indicates the spaces a Diagonal traversing piece can move
 *
 * S | R | R | R | R | R | R | R
 * F | D | # | F | # | D | # | F
 * F | # | E | E | E | # | # | F
 * F | F | E | S | E | F | F | F
 * F | # | E | E | E | # | # | F
 * F | D | # | F | # | D | # | F
 * D | # | # | F | # | # | D | F
 * F | F | F | F | F | F | F | S
 *
 * This tests all 6653 configurations for each piece
 */
public abstract class Values {
    /////////////////////////////////Variables//////////////////////////////////////////
    Board board;

    VectorTuple start = new VectorTuple();
    VectorTuple cornerAvailible[] = new VectorTuple[2];

    VectorTuple enemyLocations = new VectorTuple();
    VectorTuple friendlyLocations = new VectorTuple();
    VectorTuple availibleMoves = new VectorTuple();

    VectorTuple north, south, east, west, northEast, northWest, southEast, southWest;
    Piece friend;
    Piece enemy;

    String type;

    int count;
    boolean presentFriendly[] = new boolean[8];
    boolean presentEnemy[] = new boolean[8];
    ////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////Default initialization code///////////////////////////
    public Values(){
        defaultValues();
    }

    public void defaultValues() {
        start.addElement(new Tuple(0,0));
        start.addElement(new Tuple(7,7));
        start.addElement(new Tuple(3,3));

        enemyLocations.addElement(new Tuple(2,2));
        enemyLocations.addElement(new Tuple(2,3));
        enemyLocations.addElement(new Tuple(2,4));
        enemyLocations.addElement(new Tuple(3,2));
        enemyLocations.addElement(new Tuple(3,4));
        enemyLocations.addElement(new Tuple(4,2));
        enemyLocations.addElement(new Tuple(4,3));
        enemyLocations.addElement(new Tuple(4,4));

        friendlyLocations = new VectorTuple(friendlyLocations);
    }

    public void setAvailibleMoves(){
        availibleMoves.addAll(north);
        availibleMoves.addAll(south);
        availibleMoves.addAll(east);
        availibleMoves.addAll(west);
        availibleMoves.addAll(northEast);
        availibleMoves.addAll(northWest);
        availibleMoves.addAll(southEast);
        availibleMoves.addAll(southWest);
    }
    /////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////Availability code////////////////////////////////
    public VectorTuple row0(){return rowFile(0, 1, 0, 1);}
    public VectorTuple row3(){
        VectorTuple out = new VectorTuple();
        out.addAll(rowFile(3,4,0,1));
        out.addAll(rowFile(3,2,0,-1));
        return out;
    }
    public VectorTuple row7(){return rowFile(7,6,0,-1);}

    public VectorTuple column0(){return rowFile(1,0,1,0);}
    public VectorTuple column3(){
        VectorTuple out = new VectorTuple();
        out.addAll(rowFile(4,3,1,0));
        out.addAll(rowFile(2,3,-1,0));
        return out;
    }
    public VectorTuple column7(){return rowFile(6,7,-1,0);}

    public VectorTuple symetricDiagonal(){
        VectorTuple out = new VectorTuple();
        out.addAll(rowFile(2,2,-1,-1));
        out.addAll(rowFile(4,4,1,1));
        return out;
    }
    public VectorTuple complementaryDiagonal(){
        VectorTuple out = new VectorTuple();
        out.addAll(rowFile(4,2,1,-1));
        out.addAll(rowFile(2,4,-1,1));
        return out;
    }

    public VectorTuple rowFile(int rowStart, int columnStart, int rowOffset, int columnOffset){
        VectorTuple out = new VectorTuple();
        if(board.getOccupierAt(new Tuple(rowStart, columnStart)) != null || board.getOccupierAt(new Tuple(rowStart, columnStart)).getColor() == friend.getColor()){
            rowStart += rowOffset;
            columnOffset += columnOffset;
        }

        for(int index = 1; index < 7; index++){
            out.add(new Tuple(columnStart, rowStart));
            rowStart += rowOffset;
            columnOffset += columnOffset;
        }
        return out;
    }

    /////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////Abstract functions////////////////////////////////
    public abstract void setCornerAvailible();

    public abstract void setNorth();
    public abstract void setSouth();
    public abstract void setEast();
    public abstract void setWest();
    public abstract void setNorthEast();
    public abstract void setNorthWest();
    public abstract void setSouthEast();
    public abstract void setSouthWest();

    public abstract void setFriend();
    public abstract void setEnemy();
    //////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////Incrementation Verification////////////////
    public void printBooleans(){
        zeroBooleans();
        for(int index = 0; index < 6561; index++){
            printBoolean();
            incrementBoleans();
            System.out.println("\t:\t" + count);
        }
    }

    public void printBoolean(){
        for(int index = 0; index < 8; index++){
            int value = 0;
            if(presentEnemy[index])
                value = 1;
            if(presentFriendly[index])
                value = 2;
            System.out.print(value);
        }
    }

    public void zeroBooleans(){
        count = 0;
        for(int index = 0; index < 8; index++){
            presentEnemy[index] = false;
            presentFriendly[index] = false;
        }
    }

    public void incrementBoleans() {
        boolean carryOver = true;
        for(int index = 0; index < 8 && carryOver; index++) {
            carryOver = plusOne(index);
            if(index == 0)
               count++;
        }
    }

    public boolean plusOne(int index){
        if (!presentEnemy[index] && !presentFriendly[index]){
            presentEnemy[index] = true;
        }
        else if (presentEnemy[index]){
            presentEnemy[index] = false;
            presentFriendly[index] = true;
        }
        else{
            presentFriendly[index] = false;
            return true;
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////


    ////////////////////////////Varification code////////////////////////////////
    public boolean isCorrect(VectorMove moves){
        removeNulls(moves);
        removeNulls(availibleMoves);
        if(moves.size() != availibleMoves.size())
            return false;
        for(int indexM = 0; indexM < moves.size(); indexM++){
            for(int indexAV = 0; indexAV < moves.size(); indexAV++){
                if(availibleMoves.elementAt(indexAV).compare(moves.elementAt(indexM))){
                    availibleMoves.removeElementAt(indexAV);
                    break;
                }
            }
        }
        return availibleMoves.size() == 0;
    }

    public void removeNulls(VectorMove moves){
        int index = 0;
        while(index < moves.size()){
            if(moves.elementAt(index).finish == null)
                moves.removeElementAt(index);
            else
                index++;
        }
    }
    public void removeNulls(VectorTuple moves){
        int index = 0;
        while(index < moves.size()){
            if(moves.elementAt(index) == null)
                moves.removeElementAt(index);
            else
                index++;
        }
    }
    ////////////////////////////////////////////////////////////////////////////
}


