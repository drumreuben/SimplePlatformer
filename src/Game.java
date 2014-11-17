import java.awt.*;
import java.util.*;

/**
 * Created by Reuben on 11/15/2014.
 * A thing
 */
public class Game {

    public static void main(String[] args) {

        while (true) {
            //new player with certain properties
            Player player1 = new Player(Color.CYAN, 5, 45, 10);

            //new goal
            Goal goal1 = new Goal(280, 5, 295, 95);

            //all the walls in the level
            ArrayList<Wall> walls = new ArrayList<Wall>();
            walls.add(new Wall(0, 5, 5, 95));
            walls.add(new Wall(295, 5, 300, 100));
            walls.add(new Wall(0, 95, 300, 100));
            walls.add(new Wall(0, 0, 80, 5));
            walls.add(new Wall(110, 10, 130, 15));
            walls.add(new Wall(160, 20, 180, 25));
            walls.add(new Wall(210, 30, 230, 35));
            walls.add(new Wall(260, 0, 300, 5));

            //level size (used for camera tracking)
            int levelXmin = 0;
            int levelXmax = 300;
            int levelYmin = 0;
            int levelYmax = 200;
            double currentXmin;
            double currentXmax;
            double currentYmin;
            double currentYmax;

            while (true) {

                StdDraw.clear();
                //determines x camera tracking
                if (player1.xPos - player1.xViewSize < levelXmin) {
                    currentXmin = levelXmin;
                    currentXmax = levelXmin + 2 * player1.xViewSize;
                } else if (player1.xPos + player1.xViewSize > levelXmax) {
                    currentXmin = levelXmax - 2 * player1.xViewSize;
                    currentXmax = levelXmax;
                } else {
                    currentXmin = player1.xPos - player1.xViewSize;
                    currentXmax = player1.xPos + player1.xViewSize;
                }
                StdDraw.setXscale(currentXmin, currentXmax);
                //determines y camera tracking
                if (player1.yPos - player1.yViewSize < levelYmin) {
                    currentYmin = levelYmin;
                    currentYmax = levelYmin + 2 * player1.yViewSize;
                } else if (player1.yPos + player1.yViewSize > levelYmax) {
                    currentYmin = levelYmax - 2 * player1.yViewSize;
                    currentYmax = levelYmax;
                } else {
                    currentYmin = player1.yPos - player1.yViewSize;
                    currentYmax = player1.yPos + player1.yViewSize;
                }
                StdDraw.setYscale(currentYmin, currentYmax);


                //moves player down every game tick (ySpeed == 0 while on ground)
                player1.yPos += player1.ySpeed;

                //determines whether or not gravity should be added to speed (player is not on the ground)
                if (!player1.checkDown(walls)) {
                    player1.ySpeed += player1.gravity;
                } else {
                    player1.ySpeed = 0;
                }

                //checks if player has hit their head on a ceiling
                if (player1.checkUp(walls)) {
                    player1.ySpeed = 0;
                }

                //Displays gameOver text
                player1.gameOver(levelYmin, currentXmin, currentXmax, currentYmin, currentYmax);

                //Displays win text
                goal1.checkCollision(player1, currentXmin, currentXmax, currentYmin, currentYmax);

                if(player1.alive && !player1.won) {

                     //jumps if player is on the ground and hitting space.
                    if (StdDraw.isKeyPressed(32)) {
                        if (player1.checkDown(walls)) {
                            player1.ySpeed = player1.jumpHeight;
                        }
                    }

                    //moves left if the player is not colliding and is holding the left arrow key
                    if (StdDraw.isKeyPressed(37)) {
                        if (!player1.checkLeft(walls)) {
                            player1.xPos -= player1.xSpeed;
                        }
                    }

                    //moves right if the the player is not colliding and is holding the right arrow key.
                    if (StdDraw.isKeyPressed(39)) {
                        if (!player1.checkRight(walls)) {
                            player1.xPos += player1.xSpeed;
                        }
                    }
                }

                //restarts game
                if(StdDraw.isKeyPressed(82)){
                    break;
                }

                //ends game
                if(StdDraw.isKeyPressed(27)){
                    System.exit(0);
                }

                //draws all the walls in the level
                for (Wall wall : walls) {
                    wall.draw();
                }

                //draws goal
                goal1.draw();

                //draws player 1
                player1.draw();

                StdDraw.setPenColor(Color.BLACK);
                StdDraw.text(45, 60, "Use Arrow Keys and Space to Play");
                StdDraw.text(45, 55, "Hit R to Restart");
                StdDraw.text(45, 50, "Hit Esc to Close");

                //animation speed
                StdDraw.show(10);

            }
        }
    }
}
