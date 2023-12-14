package com.example.sequencegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircleView extends View {

    private int[] circleColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private Random random = new Random();
    private List<PointF> circleCenters = new ArrayList<>();

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int circleRadius = Math.min(viewWidth, viewHeight) / 8;

        // Draw the sequence of circles with non-overlapping random positions
        for (int i = 0; i < 4; i++) {
            paint.setColor(circleColors[i]);

            // Generate non-overlapping random positions for the circles
            PointF center = generateNonOverlappingPosition(viewWidth, viewHeight, circleRadius);
            float centerX = center.x;
            float centerY = center.y;

            canvas.drawCircle(centerX, centerY, circleRadius, paint);
            circleCenters.add(center);
        }
    }

    // Generate non-overlapping random positions for the circles
    private PointF generateNonOverlappingPosition(int viewWidth, int viewHeight, int circleRadius) {
        PointF center;
        int attempts = 0;

        do {
            float centerX = random.nextInt(viewWidth - 2 * circleRadius) + circleRadius;
            float centerY = random.nextInt(viewHeight - 2 * circleRadius) + circleRadius;

            center = new PointF(centerX, centerY);

            // Check for overlap with existing circles
            boolean isOverlapping = false;
            for (PointF existingCenter : circleCenters) {
                float distance = (float) Math.sqrt(
                        Math.pow(centerX - existingCenter.x, 2) + Math.pow(centerY - existingCenter.y, 2));
                if (distance < 2 * circleRadius) {
                    isOverlapping = true;
                    break;
                }
            }

            if (!isOverlapping) {
                break;
            }

            attempts++;
        } while (attempts < 100); // Maximum attempts to find non-overlapping position
        return center;
    }
}
