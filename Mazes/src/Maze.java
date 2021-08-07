import java.util.ArrayList;
import java.util.Arrays;

public class Maze {

    ArrayList<ArrayList<Character>> maze;

    public Maze(int width, int height){
        maze = new ArrayList<ArrayList<Character>>(height);
        for(int i=0; i<height; i++){
            ArrayList<Character> mazeRow = new ArrayList<>(width);
            for(int j=0; j<width; j++){
                mazeRow.add(null);
            }
            maze.add(mazeRow);
        }
    }
}
