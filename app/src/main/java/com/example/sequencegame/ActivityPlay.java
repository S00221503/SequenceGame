package com.example.sequencegame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ActivityPlay extends AppCompatActivity {

    private List<Circle> circles = new ArrayList<>();
    private Random random = new Random();
    private Handler handler = new Handler(Looper.getMainLooper()); //Handler for delayed action

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CircleView circleView = new CircleView(this);
        setContentView(circleView);

        generateCircles();

        //Post a delayed action to move to OverActivity after 10 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToOverActivity();
            }
        }, 10000); // 10 seconds delay
    }

    private void generateCircles() {
        Set<Integer> usedColors = new HashSet<>();

        // Generate circles in random positions
        for (int i = 0; i < 4; i++) {
            int radius = 180; // Adjust size of circle
            int x = random.nextInt(getScreenWidth() - 2 * radius) + radius;
            int y = random.nextInt(getScreenHeight() - 2 * radius) + radius;

            int color = getRandomDistinctColor(usedColors); //Ensure distinct colors
            Circle circle = new Circle(x, y, radius, color);
            circles.add(circle);
        }
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    private int getRandomDistinctColor(Set<Integer> usedColors) {
        int[] availableColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        List<Integer> remainingColors = new ArrayList<>();

        for (int color : availableColors) {
            if (!usedColors.contains(color)) {
                remainingColors.add(color);
            }
        }

        if (remainingColors.isEmpty()) {
            //If all colors are used, reset the set
            usedColors.clear();
            remainingColors.addAll(usedColors);
        }

        int selectedColor = remainingColors.get(random.nextInt(remainingColors.size()));
        usedColors.add(selectedColor);
        return selectedColor;
    }

    private class CircleView extends View {

        private Paint paint = new Paint();
        private Circle draggedCircle; //To keep track of the circle being dragged

        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //Draw circles on the canvas
            for (Circle circle : circles) {
                paint.setColor(circle.getColor());
                canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius(), paint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //Check if the touch is inside any circle
                    draggedCircle = findCircleAt(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (draggedCircle != null) {
                        //Update the position of the dragged circle
                        draggedCircle.setX(touchX);
                        draggedCircle.setY(touchY);
                        invalidate(); // Redraw the view
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    draggedCircle = null; //Reset dragged circle when touch is released
                    break;
            }

            return true;
        }

        private Circle findCircleAt(float touchX, float touchY) {
            //Check if the touch is inside any circle
            for (Circle circle : circles) {
                if (isTouchInsideCircle(touchX, touchY, circle)) {
                    return circle;
                }
            }
            return null;
        }

        private boolean isTouchInsideCircle(float touchX, float touchY, Circle circle) {
            float distance = (float) Math.sqrt(
                    Math.pow(touchX - circle.getX(), 2) + Math.pow(touchY - circle.getY(), 2));

            return distance <= circle.getRadius();
        }
    }

    private static class Circle {
        private float x;
        private float y;
        private int radius;
        private int color;

        public Circle(float x, float y, int radius, int color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getRadius() {
            return radius;
        }

        public int getColor() {
            return color;
        }
    }

    private void moveToOverActivity() {
        Intent intent = new Intent(ActivityPlay.this, OverActivity.class);
        startActivity(intent);
        finish(); //Finish the current activity to prevent the user from returning
    }
}
