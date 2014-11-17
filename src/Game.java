import java.awt.*;
import java.util.*;

/**
 * Created by Reuben on 11/15/2014.
 * A thing
 */
public class Game {

    public static void main(String[] args) {

         //all the walls in the level
        ArrayList<Wall> level0 = new ArrayList<Wall>();
        level0.add(new Wall(0, 5, 5, 95));
        level0.add(new Wall(295, 5, 300, 100));
        level0.add(new Wall(0, 95, 300, 100));
        level0.add(new Wall(0, 0, 80, 5));
        level0.add(new Wall(110, 10, 130, 15));
        level0.add(new Wall(160, 20, 180, 25));
        level0.add(new Wall(210, 30, 230, 35));
        level0.add(new Wall(260, 0, 300, 5));

        ArrayList<Wall> level1 = new ArrayList<Wall>();
        level1.add(new Wall(0, 5, 5, 95));
        level1.add(new Wall(295, 5, 300, 100));
        level1.add(new Wall(0, 95, 300, 100));
        level1.add(new Wall(0, 0, 80, 5));
        level1.add(new Wall(110, 10, 130, 15));
        level1.add(new Wall(175, 20, 195, 25));
        level1.add(new Wall(260, 0, 300, 5));

        //new player with certain properties
        Player player1 = new Player(Color.CYAN, 5, 45, 10);

        //new goal
        Goal goal1 = new Goal(280, 5, 295, 95);
        Goal goal2 = new Goal(280, 5, 295, 95);

        //new Level
        Level zero = new Level(level0, 0, goal1, 0, 300, 0, 200);
        Level one = new Level(level1, 1, goal2, 0, 300, 0, 200);

        //level size (used for camera tracking)
        double currentXmin;
        double currentXmax;
        double currentYmin;
        double currentYmax;

        ArrayList<Level> levels = new ArrayList<Level>();
        levels.add(zero);
        levels.add(one);

        while (true) {

            player1.setDefault();

            while (true) {

                StdDraw.clear();

                //determines x camera tracking
                if (player1.xPos - player1.xViewSize < levels.get(player1.currentLevel).xMin) {
                    currentXmin = levels.get(player1.currentLevel).xMin;
                    currentXmax = levels.get(player1.currentLevel).xMin + 2 * player1.xViewSize;
                } else if (player1.xPos + player1.xViewSize > levels.get(player1.currentLevel).xMax) {
                    currentXmin = levels.get(player1.currentLevel).xMax - 2 * player1.xViewSize;
                    currentXmax = levels.get(player1.currentLevel).xMax;
                } else {
                    currentXmin = player1.xPos - player1.xViewSize;
                    currentXmax = player1.xPos + player1.xViewSize;
                }
                StdDraw.setXscale(currentXmin, currentXmax);
                //determines y camera tracking
                if (player1.yPos - player1.yViewSize < levels.get(player1.currentLevel).yMin) {
                    currentYmin = levels.get(player1.currentLevel).yMin;
                    currentYmax = levels.get(player1.currentLevel).yMin + 2 * player1.yViewSize;
                } else if (player1.yPos + player1.yViewSize > levels.get(player1.currentLevel).yMax) {
                    currentYmin = levels.get(player1.currentLevel).yMax - 2 * player1.yViewSize;
                    currentYmax = levels.get(player1.currentLevel).yMax;
                } else {
                    currentYmin = player1.yPos - player1.yViewSize;
                    currentYmax = player1.yPos + player1.yViewSize;
                }
                StdDraw.setYscale(currentYmin, currentYmax);


                //moves player down every game tick (ySpeed == 0 while on ground)
                player1.yPos += player1.ySpeed;

                //determines whether or not gravity should be added to speed (player is not on the ground)
                if (!player1.checkDown(levels.get(player1.currentLevel).walls)) {
                    player1.ySpeed += player1.gravity;
                } else {
                    player1.ySpeed = 0;
                }

                //checks if player has hit their head on a ceiling
                if (player1.checkUp(levels.get(player1.currentLevel).walls)) {
                    player1.ySpeed = 0;
                }

                //Displays gameOver text
                player1.gameOver(levels.get(player1.currentLevel).yMin, currentXmin, currentXmax, currentYmin, currentYmax);

                //Displays win text
                levels.get(player1.currentLevel).goal.checkCollision(player1, currentXmin, currentXmax, currentYmin, currentYmax);

                if(player1.alive && !player1.won) {

                     //jumps if player is on the ground and hitting space.
                    if (StdDraw.isKeyPressed(38)) {
                        if (player1.checkDown(levels.get(player1.currentLevel).walls)) {
                            player1.ySpeed = player1.jumpHeight;
                        }
                    }

                    //moves left if the player is not colliding and is holding the left arrow key
                    if (StdDraw.isKeyPressed(37)) {
                        if (!player1.checkLeft(levels.get(player1.currentLevel).walls)) {
                            player1.xPos -= player1.xSpeed;
                        }
                    }

                    //moves right if the the player is not colliding and is holding the right arrow key.
                    if (StdDraw.isKeyPressed(39)) {
                        if (!player1.checkRight(levels.get(player1.currentLevel).walls)) {
                            player1.xPos += player1.xSpeed;
                        }
                    }

                    if(StdDraw.mousePressed()){
                        if(player1.shotTimer == 0){

                        }
                    }
                }

                //restarts game
                if(StdDraw.isKeyPressed(82)){
                    break;
                }

                //moves to next level
                if(StdDraw.isKeyPressed(32) && player1.won){
                    player1.currentLevel++;
                    break;
                }

                //ends game
                if(StdDraw.isKeyPressed(27)){
                    System.exit(0);
                }

                //draws all the walls in the level
                for (Wall wall : levels.get(player1.currentLevel).walls) {
                    wall.draw();
                }

                //draws goal
                levels.get(player1.currentLevel).goal.draw();

                shot1.draw();

                //draws player 1
                player1.draw();

                //animation speed
                StdDraw.show(10);

            }
        }
    }
}
