package Chess.MiscClasses;

import Chess.Board.Board;
import Chess.Pieces.Piece;

/**
 * Created by jozsef on 1/27/16.
 * This class possess the algorithms for basic movements across the board.
 */
public class Movement {


    //This section consists of single getMoves in all eight of the cardinal directions
    public static Tuple takeStep(Tuple location, Board board, int xDirection, int yDirection){
        if(location == null)
            return null;
        Tuple out = location.copy();
        out.y += yDirection;
        out.x += xDirection;
        return withinBounds(out, board) ? out : null;
    }

    public static Tuple north(Tuple location, Board board){
        return takeStep(location, board, 0, -1);
    }

    public static Tuple south(Tuple location, Board board){
        return takeStep(location, board, 0, 1);
    }

    public static Tuple east(Tuple location, Board board){
        return takeStep(location, board, 1, 0);
    }

    public static Tuple west(Tuple location, Board board){
        return takeStep(location, board, -1, 0);
    }

    public static Tuple northEast(Tuple location, Board board){
        return takeStep(location, board, 1, -1);
    }

    public static Tuple southEast(Tuple location, Board board){
        return takeStep(location, board, 1, 1);
    }

    public static Tuple northWest(Tuple location, Board board){
        return takeStep(location, board, -1, -1);
    }

    public static Tuple southWest(Tuple location, Board board){
        return takeStep(location, board, -1, 1);
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    //Based on the string received this function returns a movement in a designated direction.
    public static Tuple direction(String dir, Tuple location, Board board){
        switch (dir){
            case "north":
                return north(location, board);
            case "south":
                return south(location, board);
            case "east":
                return east(location, board);
            case "west":
                return west(location, board);
            case "northEast":
                return northEast(location, board);
            case "northWest":
                return northWest(location, board);
            case "southWest":
                return southWest(location, board);
            case "southEast":
                return southEast(location, board);
            default:
                return null;
        }
    }


    //This function returns all the squares in a designated direction.
    public static VectorTuple AllInDirection(Tuple location, Board board, String dir){
        VectorTuple out = new VectorTuple();
        Tuple next = direction(dir, location, board);

        while(next != null){
            out.add(next);
            next = direction(dir, next, board);
        }
        checkObstructions(board, out, board.getOccupierAt(location));
        return out;
    }

    //Removes squares that are blocked by other pieces.
    public static void checkObstructions(Board board, VectorTuple path, Piece protagonist){
        Piece occupier = null;
        for(int index = 0; index < path.size(); index++){
            Tuple location = path.elementAt(index);
            if(location == null){
                path.removeElementAt(index);
            }
            else {
                occupier = board.getOccupierAt(location);

                if(occupier != null){
                    if(occupier.getColor() == protagonist.getColor() || board.getOutOfBoundsAt(location)){
                        path.removeAfter(index);
                        break;
                    }
                    else{
                        path.removeAfter(index + 1);
                        break;
                    }
                }
            }
        }
    }

    //This section calculates the movements made by the knight chess piece.
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

    ///////////////////////////////////////////////////////////////////////////////

    //Returns all locations reachable in north, south, east, west directions.
    public static VectorTuple allPerpendicular(Tuple location, Board board) {
        VectorTuple out = new VectorTuple();
        out.addAll(Movement.AllInDirection(location, board, "north"));
        out.addAll(Movement.AllInDirection(location, board, "south"));
        out.addAll(Movement.AllInDirection(location, board, "east"));
        out.addAll(Movement.AllInDirection(location, board, "west"));
        return out;
    }

    //Returns all locations reachable in northeast, northwest, southeast, southwest, directions.
    public static VectorTuple allDiagonals(Tuple location, Board board) {
        VectorTuple out = new VectorTuple();
        out.addAll(symmetricDiagonal(location, board));
        out.addAll(complementDiagonals(location, board));
        return out;
    }

    public static VectorTuple symmetricDiagonal(Tuple location, Board board){
        VectorTuple out = new VectorTuple();
        out.addAll(Movement.AllInDirection(location, board, "northWest"));
        out.addAll(Movement.AllInDirection(location, board, "southEast"));
        return out;
    }

    public static VectorTuple complementDiagonals(Tuple location, Board board){
        VectorTuple out = new VectorTuple();
        out.addAll(Movement.AllInDirection(location, board, "northEast"));
        out.addAll(Movement.AllInDirection(location, board, "southWest"));
        return out;
    }

    public static VectorTuple allPerpAndDiagonals(Tuple location, Board board){
        VectorTuple out = new VectorTuple();
        out.addAll(allPerpendicular(location, board));
        out.addAll(allDiagonals(location, board));
        return out;
    }

    //Functions verifies a location is within the bounds of the board.
    public static boolean withinBounds(Tuple location, Board board){
        if((location == null) || (location.x >= board.getLength()) || (location.y >= board.getHeight()) || (location.y < 0) || (location.x < 0)){
            return false;
        }
        return !board.getOutOfBoundsAt(location);
    }

    public static VectorTuple kingsMoves(Tuple location, Board board){
        VectorTuple out = new VectorTuple();
        out.add(Movement.north(location, board));
        out.add(Movement.northEast(location, board));
        out.add(Movement.east(location, board));
        out.add(Movement.southEast(location, board));
        out.add(Movement.south(location, board));
        out.add(Movement.southWest(location, board));
        out.add(Movement.west(location, board));
        out.add(Movement.northWest(location, board));

        removeFriendlyLocations(out, board.getOccupierAt(location).getColor(), board);

        return out;
    }

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

    //Prints all the getMoves for all teams.
    public static void printAllMoves(Board board){
        for(int row=0; row < board.getLength(); row++){
            System.out.println("Row " + (row) + ":");
            for(int column = 0; column < board.getHeight(); column++){
                Piece occupant = board.getOccupierAt(new Tuple(column, row));
                System.out.println("Column " + column + ":");
                if(occupant != null){
                    occupant.getLocation().print("start");
                    occupant.getMoves(board).print();
                    System.out.println();
                }
            }
        }
    }
}
