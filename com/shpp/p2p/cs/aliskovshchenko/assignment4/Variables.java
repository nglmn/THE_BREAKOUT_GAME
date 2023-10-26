package com.shpp.p2p.cs.aliskovshchenko.assignment4;

import java.awt.*;

public interface Variables {
    /**
     * Width and height of application window in pixels
     */
    int APPLICATION_WIDTH = 400;
    int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of the paddle
     */
    int PADDLE_WIDTH = 60;
    int PADDLE_HEIGHT = 10;

    int PADDLE_CENTER = PADDLE_WIDTH / 2;

    /**
     * Offset of the paddle up from the bottom
     */
    int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    int BRICK_SEP = 2;

    /* Rounded border of brick */
    double ROUNDED_BORDER = 5;

    /**
     * Width of a brick
     */
    int BRICK_WIDTH =
            (APPLICATION_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    int BRICK_HEIGHT = 8;

    /* BRICKS CUSTOM COLORS */
    Color[] BRICKS_COLOR = {
            new Color(255, 39, 39),
            new Color(255, 172, 39),
            new Color(223, 225, 39),
            new Color(39, 225, 103),
            new Color(39, 225, 203),
    };

    /**
     * Radius of the ball in pixels
     */
    int BALL_RADIUS = 10;

    /**
     * Size of the ball in pixels
     */
    double BALL_DIAMETER = BALL_RADIUS * 2;

    /**
     * Offset of the top brick row from the top
     */
    int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    int NTURNS = 3;

    /* The ball speed change in case when the limit of the bricks are decreasing */
    double FIRST_GEAR_FPS = 20;
    double SECOND_GEAR_FPS = 15;
    double THIRD_GEAR_FPS = 10;
    /**
     * The final WIN message
     */
    String YOU_WIN = "YOU WIN";
    /**
     * The final OVER message
     */
    String GAME_OVER = "GAME OVER";
    /**
     * Changed bg color
     */
    Color BG_COLOR = new Color(30, 30, 30);
    /**
     * CUSTOM_PADDLE_COLOR
     */
    Color CUSTOM_PADDLE_COLOR = new Color(235, 235, 235);
    /**
     * CUSTOM_BALL_COLOR
     */
    Color CUSTOM_BALL_COLOR = new Color(235, 235, 235);
    /**
     * Brick residue rate on the field
     */
    double COUNT_RATIO = 0.25;


}
