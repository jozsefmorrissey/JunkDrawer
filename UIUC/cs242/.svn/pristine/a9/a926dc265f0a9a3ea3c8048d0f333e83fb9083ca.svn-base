package Chess.Board;

import Chess.MiscClasses.Tuple;
import Chess.Pieces.Piece;

import java.util.Vector;

/**
 * Created by jozsef on 2/3/16.
 */
public class War extends Board{
    int RED = 0x00ff0000;
    public War(){
        this.setLength(47);
        this.setHeight(47);
        this.setCharacteristics(new Characteristics [this.getHeight()][this.getLength()]);
        this.setLeaders(new Vector<Piece>(0));
        this.initCharacteristics();
        this.alternatingColors(RED, RED);
        setOutOfBounds();
    }

    public void setOutOfBounds(){
        setCornersOBS();
        setOuterPathGaps();
        setPathDefinition();
        setSmallInnerRectangls();
        setInnerSquares();
        setWalls();
    }

    public void setCornersOBS(){
        Tuple leftCornerTopStart = new Tuple(0,0);
        Tuple leftCornerTopFinish = new Tuple(8,8);
        this.outOfBoundsSquare(leftCornerTopStart, leftCornerTopFinish);

        Tuple rightCornerTopStart = new Tuple(39,0);
        Tuple rightCornerTopFinish = new Tuple(47,8);
        this.outOfBoundsSquare(rightCornerTopStart, rightCornerTopFinish);

        Tuple LeftCornerBottomStart = new Tuple(39,39);
        Tuple LeftCornerBottomFinish = new Tuple(47,47);
        this.outOfBoundsSquare(LeftCornerBottomStart, LeftCornerBottomFinish);

        Tuple RightCornerBottomStart = new Tuple(0,39);
        Tuple RightCornerBottomFinish = new Tuple(8,47);
        this.outOfBoundsSquare(RightCornerBottomStart, RightCornerBottomFinish);
    }

    public void setOuterPathGaps(){
        Tuple topStart = new Tuple(20,0);
        Tuple topFinish = new Tuple(27,5);
        this.outOfBoundsSquare(topStart, topFinish);

        Tuple rightStart = new Tuple(42,20);
        Tuple rightFinish = new Tuple(47,27);
        this.outOfBoundsSquare(rightStart, rightFinish);

        Tuple leftStart = new Tuple(0,20);
        Tuple leftFinish = new Tuple(5,27);
        this.outOfBoundsSquare(leftStart, leftFinish);

        Tuple bottomStart = new Tuple(20,42);
        Tuple bottomFinish = new Tuple(27,47);
        this.outOfBoundsSquare(bottomStart, bottomFinish);
    }

    public void setPathDefinition(){
        Tuple northWestRight = new Tuple(3,11);
        setLargePathChunks(northWestRight, true);

        Tuple northWestDown = new Tuple(11,3);
        setLargePathChunks(northWestDown, false);

        Tuple northEastRight = new Tuple(36,11);
        setLargePathChunks(northEastRight, true);

        Tuple northEastDown = new Tuple(30,3);
        setLargePathChunks(northEastDown, false);

        Tuple soutEastRight = new Tuple(36,30);
        setLargePathChunks(soutEastRight, true);

        Tuple southEastDown = new Tuple(30, 36);
        setLargePathChunks(southEastDown, false);

        Tuple southWestRight = new Tuple(3, 30);
        setLargePathChunks(southWestRight, true);

        Tuple southWestLeft = new Tuple(11, 36);
        setLargePathChunks(southWestLeft, false);
    }

    public void setLargePathChunks(Tuple start, boolean rightDown){
        int length = 8;
        int height = 6;
        Tuple finish = new Tuple(start);
        if(rightDown){
            finish.x += length;
            finish.y += height;
        }
        else{
            finish.y += length;
            finish.x += height;
        }
        this.outOfBoundsSquare(start, finish);
    }

    public void setSmallInnerRectangls(){
        Tuple northWestStart = new Tuple(15,13);
        Tuple northWestFinish = new Tuple(17,17);
        this.outOfBoundsSquare(northWestStart, northWestFinish);

        Tuple northEastStart = new Tuple(30,13);
        Tuple northEastFinish = new Tuple(32,17);
        this.outOfBoundsSquare(northEastStart, northEastFinish);

        Tuple southWestStart = new Tuple(15,30);
        Tuple southWestFinish = new Tuple(17,34);
        this.outOfBoundsSquare(southWestStart, southWestFinish);

        Tuple southEastStart = new Tuple(30,30);
        Tuple southEastFinish = new Tuple(32,34);
        this.outOfBoundsSquare(southEastStart, southEastFinish);
    }

    public void setInnerSquares(){
        Tuple northWestStart = new Tuple(13,15);
        Tuple northWestFinish = new Tuple(15,17);
        this.outOfBoundsSquare(northWestStart, northWestFinish);

        Tuple northEastStart = new Tuple(32,15);
        Tuple northEastFinish = new Tuple(34,17);
        this.outOfBoundsSquare(northEastStart, northEastFinish);

        Tuple southWestStart = new Tuple(13,30);
        Tuple southWestFinish = new Tuple(15,32);
        this.outOfBoundsSquare(southWestStart, southWestFinish);

        Tuple southEastStart = new Tuple(32,30);
        Tuple southEastFinish = new Tuple(34,32);
        this.outOfBoundsSquare(southEastStart, southEastFinish);


        Tuple northWestStart2 = new Tuple(11,11);
        Tuple northWestFinish2 = new Tuple(13,13);
        this.outOfBoundsSquare(northWestStart2, northWestFinish2);

        Tuple northEastStart2 = new Tuple(34,11);
        Tuple northEastFinish2 = new Tuple(36,13);
        this.outOfBoundsSquare(northEastStart2, northEastFinish2);

        Tuple southWestStart2 = new Tuple(11,34);
        Tuple southWestFinish2 = new Tuple(13,36);
        this.outOfBoundsSquare(southWestStart2, southWestFinish2);

        Tuple southEastStart2 = new Tuple(34,34);
        Tuple southEastFinish2 = new Tuple(36,36);
        this.outOfBoundsSquare(southEastStart2, southEastFinish2);
    }

    public void setWalls(){
        Tuple northWest = new Tuple(16,16);
        setWall1(northWest, 1, 1);

        Tuple northEast = new Tuple(29,16);
        setWall2(northEast, 1, 1);

        Tuple southEast = new Tuple(27,27);
        setWall1(southEast, 1, 1);

        Tuple southWest = new Tuple(18,27);
        setWall2(southWest, 1, 1);
    }

    public void setWall1(Tuple start, int upDown, int rightLeft){
        for(int index = 0; index < 3; index++){
            Tuple finish = new Tuple(start);
            finish.x += rightLeft*2;
            finish.y += upDown*2;

            this.outOfBoundsSquare(start, finish);
            start.x += rightLeft;
            start.y += upDown;
        }

    }

    public void setWall2(Tuple start, int upDown, int rightLeft){
        for(int index = 0; index < 3; index++){
            Tuple finish = new Tuple(start);
            finish.x += rightLeft*2;
            finish.y += upDown*2;

            this.outOfBoundsSquare(start, finish);
            start.x -= rightLeft;
            start.y += upDown;
        }

    }
}
