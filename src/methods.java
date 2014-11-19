import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by Reuben on 11/18/2014.
 */
public class methods {

    //counts the number of levels in levelData folder
    public static int countFiles(String directory){
        int fileCount = new File("C:\\Users\\Reuben\\Desktop\\IdeaProjects\\GunsAndGravity\\src\\levelData\\").listFiles().length;
        return fileCount;
    }

    public static Level readLevel(String levelNum) throws Exception {
        Level level = new Level();
        File levelData = new File("C:\\Users\\Reuben\\Desktop\\IdeaProjects\\GunsAndGravity\\src\\levelData\\" + levelNum + ".txt");
        FileReader fr = new FileReader(levelData);
        Scanner in = new Scanner(fr);
        level.startX = in.nextInt();
        level.startY = in.nextInt();
        System.out.println("Found starting position at " + level.startX + " " + level.startY);
        int goalX1 = in.nextInt();
        int goalY1 = in.nextInt();
        int goalX2 = in.nextInt();
        int goalY2 = in.nextInt();
        level.goal = new Goal(goalX1, goalY1, goalX2, goalY2);
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        while(in.hasNext()) {
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            int x2 = in.nextInt();
            int y2 = in.nextInt();
            if (x1 < xMin) {
                xMin = x1;
            }
            if (x2 > xMax) {
                xMax = x2;
            }
            if (y1 < yMin) {
                yMin = y1;
            }
            if (y2 > yMax) {
                yMax = y2;
            }
            level.walls.add(new Wall(x1, y1, x2, y2));
            System.out.println("Read wall at " + x1 + " " + y1 + " " + x2 + " " + y2);
        }
        level.levelXmin = xMin;
        level.levelXmax = xMax;
        level.levelYmin = yMin;
        level.levelYmax = yMax;
        return level;
    }
}
