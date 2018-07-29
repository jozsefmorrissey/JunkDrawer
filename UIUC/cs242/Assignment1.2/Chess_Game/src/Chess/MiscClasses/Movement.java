package Chess.MiscClasses;

import Chess.Board.Board;
import Chess.Manuevours;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/27/16.
 * This class possess the algorithms for basic movements across the board.
 */
public class Movement {

    /**
     * @return a single step in the designated x,y direction.
     */
    public static Tuple takeStep(Tuple location, Board board, int xDirection, int yDirection){
        if(location == null)
            return null;
        Tuple out = location.copy();
        out.y += yDirection;
        out.x += xDirection;
        return withinBounds(out, board) ? out : null;
    }

    public static Tuple stepNorth(Tuple location, Board board){
        return takeStep(location, board, 0, -1);
    }

    public static Tuple stepSouth(Tuple location, Board board){
        return takeStep(location, board, 0, 1);
    }

    public static Tuple stepEast(Tuple location, Board board){
        return takeStep(location, board, 1, 0);
    }

    public static Tuple stepWest(Tuple location, Board board){
        return takeStep(location, board, -1, 0);
    }

    public static Tuple stepNorthEast(Tuple location, Board board){
        return takeStep(location, board, 1, -1);
    }

    public static Tuple stepSouthEast(Tuple location, Board board){
        return takeStep(location, board, 1, 1);
    }

    public static Tuple stepNorthWest(Tuple location, Board board){
        return takeStep(location, board, -1, -1);
    }

    public static Tuple stepSouthWest(Tuple location, Board board){
        return takeStep(location, board, -1, 1);
    }

    /**
     * @return movement in a designated direction.
     */
    public static Tuple direction(String dir, Tuple location, Board board){
        switch (dir){
            case "stepNorth":
                return stepNorth(location, board);
            case "stepSouth":
                return stepSouth(location, board);
            case "stepEast":
                return stepEast(location, board);
            case "stepWest":
                return stepWest(location, board);
            case "stepNorthEast":
                return stepNorthEast(location, board);
            case "stepNorthWest":
                return stepNorthWest(location, board);
            case "stepSouthWest":
                return stepSouthWest(location, board);
            case "stepSouthEast":
                return stepSouthEast(location, board);
            default:
                return null;
        }
    }

    /**
     * @return all the squares in a designated direction.
     */
    public static VectorTuple AllInDirection(Tuple location, Board board, String dir, Enum cond){
        VectorTuple out = new VectorTuple();
        Tuple next = direction(dir, location, board);

        while(next != null){
            out.add(next);
            next = direction(dir, next, board);
        }
        //checkObstructions(board, out, board.getOccupierAt(location));
        conditionCheck(cond, board, out, board.getOccupierAt(location));
        return out;

    }

    /**
     * Pieces can "MOVE" "MELEE_ATTACK" or "RANGE_ATTACK" this function routes other
     * functions to the proper discriminant for the type of move.
     */
    public static void conditionCheck(Enum function, Board board, VectorTuple list, Piece activePiece){
        if (function.equals(Manuevours.MOVE)) {
            clearPath(board, list);
        } else if (function.equals(Manuevours.MELEE_ATTACK)) {
            targetAcquired(board, list, activePiece);
        } else if (function.equals(Manuevours.RANGE_ATTACK)) {
            targetAcquired(board, list, activePiece);
        }
    }

    /**
     * Removes locations from "path" that are blocked by other pieces.
     */
    public static void clearPath(Board board, VectorTuple path){
        path.removeNulls();
        for(int index = 0; index < path.size(); index++){
            Tuple location = path.elementAt(index);
            if(board.getOccupierAt(location) != null){
                path.removeAfter(index);
                return;
            }
        }
    }

    /**
     * Function used to find enemies from the "list" of locations to attack.
     */
    public static void targetAcquired(Board board, VectorTuple list, Piece activePiece){
        list.removeNulls();
        for(int index = 0; index < list.size(); index++){
            Tuple location = list.elementAt(index);
            Piece occupier = board.getOccupierAt(location);
            if(occupier == null) {
                list.removeElementAt(index);
                index--;
            }
            else if(occupier.getColor() == activePiece.getColor()){
                list.removeAfter(index);
                break;
            }
            else{
                list.removeAfter(index + 1);
                break;
            }
        }
    }

    /**
     * @return the movements made by the knight chess piece.
     */
    public static Tuple oclock(Tuple location, Board board, int xOffset, int yOffset){
        if(location == null)
            return null;
        Tuple out = location.copy();
        out.x += xOffset;
        out.y += yOffset;
        if(!withinBounds(out, board) || friendly(location, out, board))
            out = null;
        return out;
    }
    public static Tuple oneOclock(Tuple location, Board board){
        return oclock(location, board, 1, -2);
    }
    public static Tuple twoOclock(Tuple location, Board board){
        return oclock(location, board, 2, -1);
    }
    public static Tuple fourOclock(Tuple location, Board board){
        return oclock(location, board, 2, 1);
    }
    public static Tuple fiveOclock(Tuple location, Board board) {
        return oclock(location, board, 1, 2);
    }
    public static Tuple sevenOclock(Tuple location, Board board) {
        return oclock(location, board, -1, 2);
    }
    public static Tuple eightOclock(Tuple location, Board board){
        return oclock(location, board, -2, 1);
    }
    public static Tuple tenOclock(Tuple location, Board board){
        return oclock(location, board, -2, -1);
    }
    public static Tuple elevenOclock(Tuple location, Board board){
        return oclock(location, board, -1, -2);
    }

    /**
     * @return all locations reachable in stepNorth, stepSouth, stepEast, stepWest directions.
     */
    public static VectorTuple allPerpendicular(Tuple location, Board board, Enum cond) {
        VectorTuple out = new VectorTuple();
        out.addAll(Movement.AllInDirection(location, board, "stepNorth", cond));
        out.addAll(Movement.AllInDirection(location, board, "stepSouth", cond));
        out.addAll(Movement.AllInDirection(location, board, "stepEast", cond));
        out.addAll(Movement.AllInDirection(location, board, "stepWest", cond));
        return out;
    }

    /**
     * @return all locations reachable in northeast, northwest, southeast, southwest, directions.
     */
    public static VectorTuple allDiagonals(Tuple location, Board board, Enum cond) {
        VectorTuple out = new VectorTuple();
        out.addAll(symmetricDiagonal(location, board, cond));
        out.addAll(complementDiagonals(location, board, cond));
        return out;
    }

    public static VectorTuple symmetricDiagonal(Tuple location, Board board, Enum cond){
        VectorTuple out = new VectorTuple();
        out.addAll(Movement.AllInDirection(location, board, "stepNorthWest", cond));
        out.addAll(Movement.AllInDirection(location, board, "stepSouthEast", cond));
        return out;
    }

    public static VectorTuple complementDiagonals(Tuple location, Board board, Enum cond){
        VectorTuple out = new VectorTuple();
        out.addAll(Movement.AllInDirection(location, board, "stepNorthEast", cond));
        out.addAll(Movement.AllInDirection(location, board, "stepSouthWest", cond));
        return out;
    }

    public static VectorTuple allPerpendicularsAndDiagonals(Tuple location, Board board, Enum cond){
        VectorTuple out = new VectorTuple();
        out.addAll(allPerpendicular(location, board, cond));
        out.addAll(allDiagonals(location, board, cond));
        return out;
    }

    /**
     * Functions verifies a location is within the bounds of the board.
     */
    public static boolean withinBounds(Tuple location, Board board){
        if((location == null) || (location.x >= board.getLength()) || (location.y >= board.getHeight()) || (location.y < 0) || (location.x < 0)){
            return false;
        }
        return !board.getOutOfBoundsAt(location);
    }

    /**
     * @return a single step in all the cardinal directions.
     */
    public static VectorTuple cardinalSteps(Tuple location, Board board){
        VectorTuple out = new VectorTuple();
        out.add(Movement.stepNorth(location, board));
        out.add(Movement.stepNorthEast(location, board));
        out.add(Movement.stepEast(location, board));
        out.add(Movement.stepSouthEast(location, board));
        out.add(Movement.stepSouth(location, board));
        out.add(Movement.stepSouthWest(location, board));
        out.add(Movement.stepWest(location, board));
        out.add(Movement.stepNorthWest(location, board));

        removeFriendlyLocations(out, board.getOccupierAt(location).getColor(), board);

        return out;
    }

    /**
     * Removes detinations occupied by a friendly piece.
     */
    public static void removeFriendlyLocations(VectorTuple destinations, int teamColor, Board board){
        destinations.removeNulls();
        for(int index = 0; index < destinations.size(); index++) {
            Piece occupant = board.getOccupierAt(destinations.elementAt(index));
            if (occupant != null && occupant.getColor() == teamColor){
                destinations.removeElementAt(index);
                index--;
            }
        }
    }

    public static boolean friendly(Tuple piece1location, Tuple piece2location, Board board){
        Piece pawn1 = board.getOccupierAt(piece1location);
        Piece pawn2 = board.getOccupierAt(piece2location);

        if(pawn1 == null || pawn2 == null)
            return false;
        else
            return pawn1.getColor() == pawn2.getColor();
    }
}
