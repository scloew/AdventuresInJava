import java.util.ArrayList;
import java.util.Random;

public class Maze {

    private ArrayList<ArrayList<Character>> maze;
    private Random rng = new Random();
    private int maxRecursionDepth = 8;

    public Maze(int width, int height){
        maze = new ArrayList<ArrayList<Character>>(height);
        for(int i=0; i<height; i++){
            ArrayList<Character> mazeRow = new ArrayList<>(width);
            if(i==0 || i==height-1){
                for(int j=0; j<width; j++){
                    mazeRow.add('%');
                }
            }
            else{
                for(int j=0; j<width; j++){
                    if(j==0 || j==width-1){
                        mazeRow.add('%');
                    }
                    else{
                        mazeRow.add('*');
                    }
                }
            }
            maze.add(mazeRow);
        }
        buildMaze(new Point(0, height-1), new Point(width-1, 0), true, 0);
    }

    private void buildMaze(Point bottomLeft, Point topRight, boolean drawVertical, int recursionDepth){
        if(drawVertical){
            fillVertical(bottomLeft, topRight, recursionDepth);
        }
        else{
            fillHorizontal(bottomLeft, topRight, recursionDepth);
        }
    }

    // TODO combine this and the next function into
    //  also this implementation is not generating connected mazes
    private void fillVertical(Point bottomLeft, Point topRight, int recursionDepth){
        System.out.println(bottomLeft.toString()+" "+topRight.toString());
        printMaze();
        if(maxRecursionDepth < recursionDepth || topRight.x-bottomLeft.x<=4){
            return;
        }
        int fillLoc = bottomLeft.x + 2 + rng.nextInt(topRight.x - 3 - bottomLeft.x);
        for(int i=topRight.y+1; i< bottomLeft.y; i++){
            if(maze.get(i).get(fillLoc).equals('.')){
                continue;
            }
            if(1<i && i==topRight.y && !maze.get(i-1).get(fillLoc).equals('.')){
                maze.get(i).set(fillLoc, '%');
            }
            else if(i+1<maze.size() && !maze.get(i+1).get(fillLoc).equals('.')){
                maze.get(i).set(fillLoc, '%');
            }
        }
        int openingLoc = topRight.y + rng.nextInt(bottomLeft.y-1-topRight.y);
        maze.get(openingLoc).set(fillLoc, '.');
        Point newTopRight = new Point(fillLoc, topRight.y);
        Point newBottomLeft = new Point(fillLoc, bottomLeft.y);

        fillHorizontal(bottomLeft, newTopRight, recursionDepth+1);
        fillHorizontal(newBottomLeft, topRight, recursionDepth+1);
    }

    private void fillHorizontal(Point bottomLeft, Point topRight, int recursionDepth){
        System.out.println(bottomLeft.toString()+" "+topRight.toString());
        printMaze();
        if(maxRecursionDepth < recursionDepth || bottomLeft.y- topRight.y<=4){
            return;
        }

        int fillLoc = topRight.y+2 + rng.nextInt(bottomLeft.y-3-topRight.y);
        for(int i=bottomLeft.x+1; i<topRight.x; i++){
            if(maze.get(fillLoc).get(i).equals('.')){
                continue;
            }
            if(i==bottomLeft.x+1 && !maze.get(fillLoc).get(i-1).equals('.')){
                maze.get(fillLoc).set(i, '%'); //TODO add logic to make sure you're not blocking paths
            }
            else if(i<maze.size() && !maze.get(fillLoc).get(i+1).equals('.')){
                maze.get(fillLoc).set(i, '%');
            }

        }
        int opening = bottomLeft.x+1+rng.nextInt(topRight.x-1- bottomLeft.x);
        maze.get(fillLoc).set(opening, '.');

        Point newTopRight = new Point(topRight.x, fillLoc);
        Point newBottomLeft = new Point(bottomLeft.x, fillLoc);

        fillVertical(bottomLeft, newTopRight, recursionDepth+1);
        fillVertical(newBottomLeft, topRight, recursionDepth+1);
    }

    public String toString(){
        String ret = "";
        for(ArrayList<Character> row : maze){
            for(Character chr : row){
                ret+=chr;
            }
            ret+="\n";
        }
        return ret;
    }

    public void printMaze(){
        System.out.println(this);
    }

    private class Point{
        public int x, y;

        Point(int x, int y){
            this.x=x;
            this.y=y;
        }

        public String toString(){
            return "("+String.valueOf(x)+", "+String.valueOf(y)+")";
        }
    }
}
