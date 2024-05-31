package com.example.brickbuster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {
    Context context;
    float ballX;
    float ballY;
    Velocity velocity;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint scorePaint = new Paint();
    Paint hpPaint = new Paint();
    Paint healthPaint = new Paint();
    Paint brickPaint = new Paint();
    float textSize = 60;
    float paddleX;
    float paddleY;
    float oldX;
    float oldPaddleX;
    int points = 0;
    int lives = 3;
    Bitmap ball;
    Bitmap paddle;
    int dWidth;
    int dHeight;
    int ballWidth;
    int ballHeight;
    MediaPlayer bouncePlayer;
    MediaPlayer failPlayer;
    MediaPlayer breakPlayer;
    MediaPlayer gameOverPlayer;
    Random random;
    Brick[] bricks = new Brick[48];
    int numBricks = 0;
    int brokenBricks = 0;
    boolean gameOver = false;

    boolean winning = true;

    String difficulty = MainActivity.difficulty;
    public GameView(Context context) {
        super(context);
        this.context = context;
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        bouncePlayer = MediaPlayer.create(context, R.raw.boing2);
        failPlayer = MediaPlayer.create(context, R.raw.king_tower_gone_07);
        breakPlayer = MediaPlayer.create(context, R.raw.pop2);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(textSize);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        hpPaint.setTextSize(textSize);
        healthPaint.setColor(Color.WHITE);
        hpPaint.setColor(Color.WHITE);
        //brickPaint.setColor(Color.argb(255,249,129,0));
        brickPaint.setColor(Color.BLUE);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        random = new Random();
        ballX = random.nextInt(dWidth - 50);
        ballY = dHeight/3;
        paddleY = (dHeight * 4)/5;
        paddleX = dWidth/2 - paddle.getWidth()/2;
        ballWidth = ball.getWidth();
        ballHeight = ball.getHeight();
        // Adjusted velocity of the ball for each difficulty level
        if (difficulty == "easy") {
            velocity = new Velocity(12,16);
        } else if (difficulty == "normal") {
            velocity = new Velocity(18,24);
        } else if (difficulty == "hard") {
            velocity = new Velocity(24,32);
        }
        createBricks();
    }

    private void createBricks() {
        // Each brick is 1/8 of the screen's width and 1/32 of the screen's height
        int brickWidth = dWidth/8;
        int brickHeight = dHeight/32;
        int rows = 4;
        // More difficult = more rows
        if (difficulty == "easy") {
            rows = 2;
        } else if (difficulty == "normal") {
            rows = 4;
        } else if (difficulty == "hard") {
            rows = 6;
        }
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < rows; row++) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        ballX += velocity.getX();
        ballY += velocity.getY();
        // If ball hits either end of the screen, reverse x component of the velocity
        if ((ballX >= dWidth - ball.getWidth()) || ballX <= 0) {
            velocity.setX(velocity.getX() * -1);
        }
        // If ball hits the top of the screen, reverse y component of velocity
        if (ballY <= 0) {
            velocity.setY(velocity.getY() * -1);
        }
        if (ballY > dHeight/*paddleY + paddle.getHeight()*/) {
            ballX = 1 + random.nextInt(dWidth - ball.getWidth() - 1);
            ballY = dHeight/3;
            if (failPlayer != null) {
                failPlayer.start();
            }
            velocity.setX(xVelocity());
            //velocity.setY(16);
            lives--;
            if (lives == 0) {
                gameOverPlayer = MediaPlayer.create(context, R.raw.king_laughter_01);
                gameOver = true;
                launchGameOver();
            }
        }
            if (((ballX + ball.getWidth()) >= paddleX) && (ballX <= paddleX + paddle.getWidth()) && (ballY + ball.getHeight() >= paddleY) && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())) {
                if (bouncePlayer != null) {
                    bouncePlayer.start();
                }
//                velocity.setX(velocity.getX() + 1);
//                velocity.setY((velocity.getY() + 1) * -1);
                velocity.setY((velocity.getY()) * -1);
            }
            canvas.drawBitmap(ball, ballX, ballY, null);
            canvas.drawBitmap(paddle, paddleX, paddleY, null);
            for (int i = 0; i < numBricks; i++) {
                Random random = new Random();
                int number = random.nextInt(4);
                if (i % 5 == 0) {
                    brickPaint.setColor(Color.RED);
                } else if (i % 5 == 1) {
                    brickPaint.setColor(Color.argb(255,249,129,0));
                } else if (i % 5 == 2) {
                    brickPaint.setColor(Color.argb(255,0,150,0));
                } else if (i % 5 == 3) {
                    brickPaint.setColor(Color.BLUE);
                } else if (i % 5 == 4) {
                    brickPaint.setColor(Color.argb(255,160,32,240));
                }
                if (bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].column * bricks[i].width + 1, bricks[i].row * bricks[1].height + 1, bricks[i].column * bricks[i].width + bricks[i].width -1, bricks[i].row * bricks[i].height + bricks[i].height - 1, brickPaint);
                }
                canvas.drawText("Points: " + points + "/" + numBricks, 20, textSize, scorePaint);
//                if (lives == 2) {
//                    healthPaint.setColor(Color.YELLOW);
//                    hpPaint.setColor(Color.YELLOW);
//                } else if (lives == 1) {
//                    healthPaint.setColor(Color.RED);
//                    hpPaint.setColor(Color.RED);
//                }
                //canvas.drawRect(dWidth - 200, 30, dWidth - 200 + 60 * lives, 80, healthPaint);
                Log.d("Benny", String.valueOf(textSize));
                canvas.drawText("Lives: " + lives, dWidth - 200, textSize, hpPaint);
                for (int j = 0; j < numBricks; j++) {
                    if (bricks[j].getVisibility()) {
                        if (ballX + ballWidth >= bricks[j].column * bricks[j].width && ballX <= bricks[j].column * bricks[j].width + bricks[j].width && ballY <= bricks[j].row * bricks[j].height + bricks[j].height && ballY >= bricks[j].row * bricks[j].height) {
                            if (breakPlayer != null) {
                                breakPlayer.start();
                            }
                            velocity.setY((velocity.getY() + 1) * -1);
                            bricks[j].setInvisible();
                            points ++;
                            brokenBricks++;
                            if (brokenBricks >= numBricks) {
                                gameOverPlayer = MediaPlayer.create(context, R.raw.king_laughter_03);
                                launchGameOver();
//                                while (gameOver == true) {
//                                    gameOverPlayer.start();
//                                }
                            }
                        }
                    }
                }

                if (brokenBricks == numBricks) {
                    gameOver = true;
                }
                if (!gameOver) {
                    handler.postDelayed(runnable, UPDATE_MILLIS);
                }
            }
        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (/*touchY >= paddleY*/true) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldPaddleX = paddleX;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newPaddleX = oldPaddleX - shift;
                if (newPaddleX <= 0) {
                    paddleX = 0;
                } else if (newPaddleX >= dWidth - paddle.getWidth()) {
                    paddleX = dWidth - paddle.getWidth();
                } else {
                    paddleX = newPaddleX;
                }
            }
//            if (action == KeyEvent.KEYCODE_A) {
//                Log.d("Benny","Hsiao");
//                paddleX -= 10;
//            }
        }
        return true;
    }

    private void launchGameOver() {
        Log.d("philoBricks", String.valueOf(numBricks));
        gameOverPlayer.start();
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(context, gameOver.class);
        intent.putExtra("points", points);
        intent.putExtra("numBricks", numBricks);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
    private int xVelocity() {
        int[] values = {0, 0, 0, 0, 0, 0};
        if (difficulty == "easy") {
            values = new int[]{-18, -15, -12, 12, 15, 18};
        } else if (difficulty == "normal") {
            values = new int[]{-27, -23, -18, 18, 23, 27};
        } else if (difficulty == "hard") {
            values = new int[]{-36, -30, -24, 24, 30, 36};
        }
        int index = random.nextInt(6);
        return values[index];
    }
}
