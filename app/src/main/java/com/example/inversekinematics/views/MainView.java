package com.example.inversekinematics.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.inversekinematics.classes.Food;
import com.example.inversekinematics.classes.Segment;
import com.example.inversekinematics.classes.Snake;
import com.example.inversekinematics.classes.Vector;
import com.example.inversekinematics.engine.GameEngine;
import com.example.inversekinematics.enums.SnakeType;

import java.util.List;

public class MainView extends View {
    private Paint mPaint = new Paint();
    private Snake playerSnake;
    private List<Snake> enemySnakes;
    private List<Food> food;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        GameEngine.screenSize = new Vector(dm.widthPixels, dm.heightPixels);
    }

    public void setView(Snake snake, List<Snake> enemySnakes, List<Food> food) {
        this.playerSnake = snake;
        this.enemySnakes = enemySnakes;
        this.food = food;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw food
        if (food != null) {
            for (Vector apple : food) {
                mPaint.setColor(Color.RED);
                canvas.drawCircle((float) apple.getX(), (float) apple.getY(), GameEngine.FOOD_RADIUS, mPaint);
            }
        }

        // Draw player
        if (playerSnake != null) {
            List<Segment> body = playerSnake.getSnake();
            for (int i = 0; i < body.size() - 1; i++) {
                mPaint.setColor(Color.DKGRAY);
                canvas.drawCircle((float) body.get(i).getCenter().getX(), (float) body.get(i).getCenter().getY(), playerSnake.getBodySize(), mPaint);
            }

            mPaint.setColor(Color.BLUE);
            canvas.drawCircle((float) body.get(body.size() - 1).getCenter().getX(), (float) body.get(body.size() - 1).getCenter().getY(), playerSnake.getBodySize() * 1.05f, mPaint);
        }

        // Draw enemies
        if (enemySnakes != null) {
            for (Snake snake : enemySnakes) {
                List<Segment> body = snake.getSnake();
                for (int i = 0; i < body.size() - 1; i++) {
                    mPaint.setColor(Color.DKGRAY);
                    canvas.drawCircle((float) body.get(i).getCenter().getX(), (float) body.get(i).getCenter().getY(), snake.getBodySize(), mPaint);
                }

                if (snake.getType() == SnakeType.Hybrid) {
                    mPaint.setColor(Color.YELLOW);
                } else if (snake.getType() == SnakeType.Aggressive) {
                    mPaint.setColor(Color.MAGENTA);
                }
                if (snake.getType() == SnakeType.Passive) {
                    mPaint.setColor(Color.GREEN);
                }
                canvas.drawCircle((float) body.get(body.size() - 1).getCenter().getX(), (float) body.get(body.size() - 1).getCenter().getY(), snake.getBodySize() * 1.05f, mPaint);
            }
        }

        // Draw score
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(75);
        canvas.drawText("Score: " + GameEngine.getScore(), 30, 70, mPaint);

        // Draw time left
        mPaint.setTextSize(90);
        canvas.drawText(String.valueOf(GameEngine.timeLeft / 30), 955, 80, mPaint);
    }
}
