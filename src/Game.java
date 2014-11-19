import java.awt.*;
import java.util.*;

/**
 * Created by Reuben on 11/15/2014.
 * A thing
 */
public class Game {

    public static void main(String[] args) throws Exception {

        boolean cheating = true;

        ArrayList<Level> levelList = new ArrayList<Level>();
        for (int i = 0; i < methods.countFiles("foo"); i++) {
            levelList.add(methods.readLevel("level" + i));
            System.out.println("read file " + i);
        }

        //new player with certain properties
        Player player1 = new Player(Color.CYAN, 5);

        while (true) {

            player1.setDefault();
            player1.xPos = levelList.get(player1.currentLevel).startX;
            player1.yPos = levelList.get(player1.currentLevel).startY;
            ArrayList<shot> projectiles = new ArrayList<shot>();

            //level size (used for camera tracking)
            int levelXmin = levelList.get(player1.currentLevel).levelXmin;
            int levelXmax = levelList.get(player1.currentLevel).levelXmax;
            int levelYmin = levelList.get(player1.currentLevel).levelYmin;
            int levelYmax = levelList.get(player1.currentLevel).levelYmax;
            double currentXmin;
            double currentXmax;
            double currentYmax;
            double currentYmin;

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
                if (!player1.checkDown(levelList.get(player1.currentLevel).walls)) {
                    player1.ySpeed += player1.gravity;
                } else {
                    player1.ySpeed = 0;
                }

                //checks if player has hit their head on a ceiling
                if (player1.checkUp(levelList.get(player1.currentLevel).walls)) {
                    player1.ySpeed = 0;
                }

                //Displays gameOver text
                player1.gameOver(levelYmin, currentXmin, currentXmax,
                        currentYmin, currentYmax);

                //Displays win text
                levelList.get(player1.currentLevel).goal.checkCollision(player1, currentXmin,
                        currentXmax, currentYmin, currentYmax);

                if (player1.alive && !player1.won) {

                    //jumps if player is on the ground and hitting space.
                    if (StdDraw.isKeyPressed(87)) {
                        if
                                (player1.checkDown(levelList.get(player1.currentLevel).walls)) {
                            player1.ySpeed = player1.jumpHeight;
                        }
                    }

                    //moves left if the player is not colliding and is holding the left arrow key
                    if (StdDraw.isKeyPressed(65)) {
                        if
                                (!player1.checkLeft(levelList.get(player1.currentLevel).walls)) {
                            player1.xPos -= player1.xSpeed;
                        }
                    }

                    //moves right if the the player is not colliding and is holding the right arrow key.
                    if (StdDraw.isKeyPressed(68)) {
                        if
                                (!player1.checkRight(levelList.get(player1.currentLevel).walls)) {
                            player1.xPos += player1.xSpeed;
                        }
                    }

                    if (StdDraw.mousePressed() || StdDraw.isKeyPressed(32)) {
                        if (player1.canfire()) {
                            projectiles.add(new shot(player1));
                        }
                    }
                }

                //cheating functionality(devs only!!!!!)
                if(StdDraw.isKeyPressed(70) && cheating){
                    player1.won = true;
                }

                //restarts game
                if (StdDraw.isKeyPressed(82)) {
                    break;
                }

                //moves to next level
                if (StdDraw.isKeyPressed(84) && player1.won) {
                    player1.currentLevel++;
                    break;
                }

                //ends game
                if (StdDraw.isKeyPressed(27)) {
                    System.exit(0);
                }

                //draws all the walls in the level
                for (Wall wall : levelList.get(player1.currentLevel).walls) {
                    wall.draw();
                }

                //draws goal
                levelList.get(player1.currentLevel).goal.draw();

                //draws player 1
                player1.draw();

                for (shot shot : projectiles) {
                    shot.move();
                    shot.draw();
                }

                player1.incrementShotTimer();

                //animation speed
                StdDraw.show(10);

            }
        }
    }
}
