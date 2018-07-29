package Chess.Pieces;

import Chess.Board.Board;
import Chess.MiscClasses.Movement;
import Chess.MiscClasses.Tuple;
import Chess.MiscClasses.VectorMove;
import Chess.MiscClasses.VectorTuple;

import java.util.Vector;

/**
 * Created by jozsef on 1/26/16.
 *This is the default class for game pieces.
 */

public class Piece {

    private Tuple location;
    private int turns = 0;
    private int color = 0;
    private String type = "setPawns";
    private int colors[] = {Integer.MAX_VALUE, 0};
    private boolean isLeader = false;
    private boolean onTeam;
    private int kills = 0;
    private int AttackRange = 14;
    private int health = 100;
    private int damage = 10;
    private int accuracy = 50;
    private int orientation = 0;
    public int powerUpTurn = -1;

    /**
     *All Characters have the potential to possess movements Melee Attacks and Range
     * Attacks, this function is not to be changed or overridden. It simply compiles
     * all possible "Maneuvers".
     */
    public VectorMove getManeuvers(Board board){
        VectorMove output = new VectorMove();
        output.addAll(getMoves(board));
        output.addAll(getMeleeAttacks(board));
        output.addAll(getRangeAttacks(board));
        output.removeNulls();
        return output;
    }

    public VectorMove getMoves(Board board){return new VectorMove();}
    public VectorMove getMeleeAttacks(Board board){ return new VectorMove();}
    public VectorMove getRangeAttacks(Board board){return new VectorMove();}

    public void movesWithinRange(VectorTuple moves){
        for(int index = 0; index < moves.size(); index++){
            Tuple finalLocation = moves.elementAt(index);
            if(!inRange(finalLocation)){
                moves.removeElementAt(index--);
            }
        }
    }

    public Piece(Piece piece){
        this.location = piece.location;
        this.turns = piece.turns;
        this.color = piece.color;
        this.type = piece.type;
        this.colors = piece.colors;
    }
    public Piece(){}

    public String colorName(){
        switch (color){
            case Integer.MAX_VALUE:
                return "White";
            case 0:
                return "black";
            default:
                return "null";
        }
    }

    public boolean isOnTeam() {
        return onTeam;
    }

    public void setOnTeam(boolean onTeam) {
        this.onTeam = onTeam;
    }

    public Tuple getLocation() {
        return location;
    }

    public void setLocation(Tuple location) {
        this.location = location;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAttackRange() {
        return AttackRange;
    }

    public void setAttackRange(int attackRange) {
        this.AttackRange = attackRange;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getColor(){return this.color;}
    public VectorMove getMyMoves(Board board){return board.getMyMoves(this);}
    public VectorMove getEnemyMoves(Board board){return board.getOpponentsMoves(this);}



    /**
     * powerUp is simply a function called after each move to allow any promotions
     * regenerations or anything you can think of happening to your piece after
     * specific movement circumstances.
     */
    public void powerUp(Board board){}

    /**
     * @return true if the given location is in AttackRange.
     */
    public boolean inRange(Tuple location){
        int distance = this.location.distance(location);
        int maxDistance = (this.kills + this.AttackRange)*(this.kills + this.AttackRange);
        return distance <= maxDistance;
    }

    /**
     * Function called when piece is attacked.
     * @return true by default.
     */
    public boolean isDead(int Damage) {
        return true;
    }

    /**
     * prints the piece type.
     */
    public void print(){
        System.out.print(String.format("%-9s", this.type));
    }

    public int getDamage() {
        return  damage*(accuracy + kills*5);
    }

    public int getOrientation(){
        return orientation;
    }

    /**
     * Used to set the orientation based on last exicuted move. In sequential order:
     * (stepNorth, northeast, stepEast, southeast, stepSouth, southwest, stepWest, northwest)
     */
    public void setOrientation(Tuple start, Tuple finish){
        int xDirection = finish.x - start.x;
        int yDirection = finish.y - start.y;

        if(xDirection == 0 && yDirection < 0)
            this.orientation = 0;

        if(xDirection > 0 && yDirection < 0)
            this.orientation = 1;

        if (xDirection > 0 && yDirection == 0)
            this.orientation = 2;

        else if(xDirection > 0 && yDirection > 0)
            this.orientation = 3;

        else  if(xDirection == 0 && yDirection > 0)
            this.orientation = 4;

        else if(xDirection < 0 && yDirection > 0)
            this.orientation = 5;

        else if(xDirection < 0 && yDirection == 0)
            this.orientation = 6;

        else if(xDirection < 0 && yDirection < 0)
            this.orientation = 7;
    }

}
