package com.shpp.p2p.cs.aliskovshchenko.assignment4;

import static com.shpp.p2p.cs.aliskovshchenko.assignment4.Variables.*;

import acm.graphics.*;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;


public class Breakout extends WindowProgram {

    /**
     * Width and height of application window in pixels
     */
    public static int APPLICATION_WIDTH = 400;
    public static int APPLICATION_HEIGHT = 600;

    GRect paddle; // paddle object
    GLabel livesLabel; // count of lives label
    GLabel bricksLabel; // count of bricks label
    GLabel resultText; // the final message when the game is over

    int BORDER_SHIFT = 10; //the value of shifting the labels from application edges
    int livesCount = NTURNS;
    int bricksCount = NBRICKS_PER_ROW * NBRICK_ROWS;
    double vx;
    double vy = 3.0;


    /**
     * run() - the general method which build the breakout game
     */
    public void run() {
        setBackground(BG_COLOR);
        addMouseListeners();
        createBrickWall();
        createPaddle();
        livesLabel();
        bricksLabel();

        GOval ball = createBall(CUSTOM_BALL_COLOR);
        ballMoving(ball);
    }

    /**
     * This method describes the behavior of the ball relative to other objects
     *
     * @param ball - object
     */
    private void ballMoving(GOval ball) {
        //the game will start when the user click the left mouse button
        waitForClick();

        randomDirectionBall();

        // while count lives more than zero the game continue
        while (bricksCount != 0) {
            GObject collider = getCollidingObject(ball);

            //paddle colliding
            if (collider == paddle && vy <= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT) {
                if (vy > 0) {
                    vy = -vy;
                }
            }
            appEdgesColliding(ball);

            //this condition check colliding paddle with brick and if that true, the colliding brick is removing
            if (collider != paddle && collider != null && collider != bricksLabel && collider != livesLabel) {
                remove(collider);
                vy = -vy;
                bricksCount--;
                bricksLabel.setLabel("Bricks: " + bricksCount);
            }
            /*the condition check position ball at Y axis, if that position less than position paddle,
            the livesCount is less by 1*/
            if (ball.getY() > getHeight() - BALL_DIAMETER) {
                if (loseOneLife(ball)) break;
            }

            ball.move(vx, vy);
            ballSpeed();
        }
        if (bricksCount == 0) {
            removeAll();
            resultMessage(YOU_WIN);
        }
    }

    /**
     * When the ball
     */
    private boolean loseOneLife(GOval ball) {
        livesCount--;
        livesLabel.setLabel("Lives: " + livesCount);

        ball.setLocation(getWidth() / 2.0 - BALL_RADIUS,
                getHeight() / 2.0 - BALL_RADIUS);
        if (livesCount == 0) {
            removeAll();
            resultMessage(GAME_OVER);
            return true;
        }
        waitForClick();
        return false;
    }

    /**
     * in every game start the ball moves with random X coordinate
     */
    private void randomDirectionBall() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) {
            vx *= -vx;
        }
    }

    /**
     * when the ball touches the edge of application
     *
     * @param ball - colliding object
     */
    private void appEdgesColliding(GOval ball) {
        //colliding left border
        if (ball.getX() < 0) {
            vx = -vx;
        }
        //colliding right border
        if (ball.getX() > getWidth() - BALL_DIAMETER) {
            vx = -vx;
        }
        //colliding top border
        if (ball.getY() < 0) {
            vy = -vy;
        }
    }

    /**
     * resultMessage() - the final message for user
     *
     * @param text - result
     */
    private void resultMessage(String text) {
        resultText = new GLabel(text);
        resultText.setColor(Color.WHITE);
        resultText.setLocation(getWidth() / 2.0 - resultText.getWidth() / 2.0,
                getHeight() / 2.0 - resultText.getHeight() / 2.0);
        add(resultText);
    }

    /* ballSpeed() - depending on the number of bricks on the screen, the ball speed increases */
    private void ballSpeed() {
        double quarterPartOfBricks = (NBRICKS_PER_ROW * NBRICK_ROWS) * COUNT_RATIO;
        if (bricksCount > NBRICKS_PER_ROW * NBRICK_ROWS - quarterPartOfBricks) {
            pause(FIRST_GEAR_FPS);
        } else if (bricksCount > (NBRICKS_PER_ROW * NBRICK_ROWS) / 2.0 - quarterPartOfBricks){
            pause(SECOND_GEAR_FPS);
        } else{
            pause(THIRD_GEAR_FPS);
        }
    }

    /**
     * getCollidingObject() - function that return colliding object with ball
     *
     * @param ball - the object whose coordinates we find
     */
    private GObject getCollidingObject(GOval ball) {
        //the top left corner
        if (getElementAt(ball.getX(), ball.getY()) != null) {
            return getElementAt(ball.getX(), ball.getY());
        }
        //the top right corner
        if (getElementAt(ball.getX() + BALL_DIAMETER, ball.getY()) != null) {
            return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY());
        }
        //the bottom left corner
        if (getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER) != null) {
            return getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER);
        }
        //the bottom right corner
        if (getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER) != null) {
            return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER);
        }
        return null;
    }

    /**
     * createBall() - method that create ball
     *
     * @param color - custom ball color
     */
    private GOval createBall(Color color) {
        GOval ball = new GOval(getWidth() / 2.0 - BALL_RADIUS,
                getHeight() / 2.0 - BALL_RADIUS,
                BALL_DIAMETER, BALL_DIAMETER);
        ball.setFilled(true);
        ball.setColor(color);
        add(ball);
        return ball;
    }

    /* createBrickWall() - This method builds a matrix of bricks  */
    private void createBrickWall() {
        for (int row = 0; row < NBRICK_ROWS; row++) {
            for (int col = 0; col < NBRICKS_PER_ROW; col++) {

                int x = getWidth() / 2 - (NBRICKS_PER_ROW * (BRICK_WIDTH + BRICK_SEP)) / 2 + ((BRICK_WIDTH + BRICK_SEP) * row);
                int y = BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * col;

                createBrick(x, y, col);
            }
        }
    }

    private void createBrick(double x, double y, int col) {
        GRoundRect brick = new GRoundRect(x, y, BRICK_WIDTH, BRICK_HEIGHT, ROUNDED_BORDER);
        brick.setFilled(true);

        changeColor(col, brick);
        add(brick);
    }

    /**
     * changeColor() - that method draw the colorized brick wall
     *
     * @param col   - number of row
     * @param brick - single brick
     */
    private static void changeColor(int col, GRoundRect brick) {
        switch (col) {
            case 0, 1 -> brick.setColor(BRICKS_COLOR[0]);
            case 2, 3 -> brick.setColor(BRICKS_COLOR[1]);
            case 4, 5 -> brick.setColor(BRICKS_COLOR[2]);
            case 6, 7 -> brick.setColor(BRICKS_COLOR[3]);
            default -> brick.setColor(BRICKS_COLOR[4]);
        }
    }

    /**
     * create paddle at the center of X and the 30px from bottom
     */
    private void createPaddle() {
        paddle = new GRect(getWidth() / 2.0 - PADDLE_CENTER, getHeight() - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        paddle.setColor(CUSTOM_PADDLE_COLOR);
        add(paddle);
    }

    /**
     * mouseMoved() - method which control paddle behavior
     *
     * @param event - mouse moved event
     */
    public void mouseMoved(MouseEvent event) {
        if (event.getX() >= PADDLE_CENTER && event.getX() <= getWidth() - PADDLE_CENTER) {
            paddle.setLocation(event.getX() - PADDLE_CENTER, getHeight() - PADDLE_Y_OFFSET);
        }
    }

    // bricksLabel() - the method that draw count of bricks
    private void bricksLabel() {
        bricksLabel = new GLabel("Bricks: " + bricksCount);
        bricksLabel.setLocation(getWidth() - bricksLabel.getWidth() - BORDER_SHIFT, bricksLabel.getHeight());
        bricksLabel.setColor(Color.WHITE);
        add(bricksLabel);
    }

    // livesLabel() - the method that draw lives score
    private void livesLabel() {
        livesLabel = new GLabel("Lives: " + livesCount);
        livesLabel.setLocation(BORDER_SHIFT, livesLabel.getHeight());
        livesLabel.setColor(Color.WHITE);
        add(livesLabel);
    }
}

