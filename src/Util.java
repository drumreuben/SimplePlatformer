import java.awt.*;
import java.util.*;

/**
 * Created by Reuben on 11/15/2014.
 * Does shit.
 */

class Player {

    //movement speed of the player horizontally
    double xSpeed = 1;
    //current vertical speed of the player
    double ySpeed = 0;
    //other attributes
    double jumpHeight = 1.8;
    Color color;
    double size;
    double xPos;
    double yPos;
    int xViewSize = 50;
    int yViewSize = 50;
    double score = 0;
    boolean hitCeiling = false;
    boolean alive = true;
    boolean won = false;
    //current gravity acting on player (negative is down, positive is up)
    double gravity = -.07;

    //constructor. Takes color, size, starting position x and y
    public Player(Color playerColor, double playerSize, double x, double y) {
        color = playerColor;
        size = playerSize;
        xPos = x;
        yPos = y;
    }

    //draws the player
    void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledRectangle(xPos, yPos, size, size);
    }

    //checks collision to the left, and also fixes clipping.
    boolean checkLeft(ArrayList<Wall> walls) {
        for (Wall wall : walls) {
            if (xPos - size - xSpeed <= wall.x2 && xPos - size - xSpeed > wall.x1) {
                if ((yPos + size > wall.y1 && yPos + size < wall.y2) || (yPos - size < wall.y2 && yPos - size > wall.y1) || (yPos > wall.y1 && yPos < wall.y2)) {
                    xPos = wall.x2 + size;
                    return true;
                }
            }
        }
        return false;
    }

    //checks collision to the right, and also fixes clipping.
    boolean checkRight(ArrayList<Wall> walls) {
        for (Wall wall : walls) {
            if (xPos + size + xSpeed >= wall.x1 && xPos + size +xSpeed < wall.x2) {
                if ((yPos + size > wall.y1 && yPos + size < wall.y2) || (yPos - size < wall.y2 && yPos - size > wall.y1) || (yPos > wall.y1 && yPos < wall.y2)) {
                    xPos = wall.x1 - size;
                    return true;
                }
            }
        }
        return false;
    }

    //checks collision down and also fixes clipping.
    boolean checkDown(ArrayList<Wall> walls) {
        for (Wall wall : walls) {
            if (yPos - size + ySpeed <= wall.y2 && yPos - size + ySpeed > wall.y1) {
                if ((xPos + size > wall.x1 && xPos + size < wall.x2) || (xPos - size < wall.x2 && xPos - size > wall.x1)) {
                    yPos = wall.y2 + size;
                    return true;
                }
            }
        }
        return false;

    }

    //checks collision up and also fixes clipping.
    boolean checkUp(ArrayList<Wall> walls) {
        for(Wall wall : walls){
            if(yPos + size + ySpeed >= wall.y1 && yPos + size + ySpeed < wall.y2){
                if((xPos + size > wall.x1 && xPos + size < wall.x2) || (xPos - size < wall.x2 && xPos - size > wall.x1)){
                    yPos = wall.y1 - size;
                    return true;
                }
            }

        }
        return false;
    }

    //checks gameOver
    void gameOver(int levelMin, double currentXmin, double currentXmax, double currentYmin, double currentYmax){
        if(yPos < levelMin - 30){
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.text((currentXmin + currentXmax) / 2, (currentYmin + currentYmax) / 2, "You Died!");
            alive = false;
        }
    }
}

class Wall {

    //position
    double x1;
    double y1;
    double x2;
    double y2;

    //construction
    public Wall(double xPos1, double yPos1, double xPos2, double yPos2){
        x1 = xPos1;
        y1 = yPos1;
        x2 = xPos2;
        y2 = yPos2;
    }

    //draws wall
    void draw(){
        double xCenter = (x2+x1)/2;
        double yCenter = (y2+y1)/2;
        double halfWidth = x2-xCenter;
        double halfHeight = y2-yCenter;
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(xCenter, yCenter, halfWidth, halfHeight);
    }

}

class Level {

    int num;
    ArrayList<Wall> walls;

    public Level(ArrayList<Wall> list, int arg){
        walls = list;
        num = arg;

    }

}

class Goal {

    //position
    int x1;
    int y1;
    int x2;
    int y2;

    //Constructor
    public Goal(int x1a, int y1a, int x2a, int y2a) {
        x1 = x1a;
        y1 = y1a;
        x2 = x2a;
        y2 = y2a;
    }

    //checks win and displays win message
    void checkCollision(Player player, double currentXmin, double currentXmax, double currentYmin, double currentYmax){
        if((player.xPos + player.size > x1 && player.xPos + player.size < x2) || player.xPos - player.size < x2 && player.xPos - player.size > x1){
            if((player.yPos + player.size > y1 && player.yPos + player.size < y2) || (player.yPos - player.size < y2 && player.yPos - player.size > y1)){
                StdDraw.setPenColor(Color.BLACK);
                StdDraw.text((currentXmin + currentXmax) / 2, (currentYmin + currentYmax) / 2, "You won!");
                player.won = true;
            }
        }
    }

    //draws goal
    void draw(){
        StdDraw.setPenColor(173, 255, 179);
        double xCenter = (x2+x1)/2;
        double yCenter = (y2+y1)/2;
        double halfWidth = x2-xCenter;
        double halfHeight = y2-yCenter;
        StdDraw.filledRectangle(xCenter, yCenter, halfWidth, halfHeight);
    }

}

