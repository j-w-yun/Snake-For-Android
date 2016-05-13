package com.jay50.jae.snake;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{

    DrawingPanel dp;
//    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        dp = new DrawingPanel(this);
        dp.setWindow(screenWidth, screenHeight);
        dp.setBackgroundColor(Color.BLACK);
        setContentView(dp);
    }

    public class DrawingPanel extends View implements Updatable
    {
        private int width;
        private int height;
        private int div;

        private Bitmap bitmap;
        private Paint bitmapPaint;
        private Canvas canvas;
//        private Context context;

        private GameState gs;
        private JayList<Key> queue;
        private int[] food;

        public DrawingPanel(Context context)
        {
            super(context);
//            this.context = context;
            bitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        public void lose()
        {
            clearGrid();

            for(int j = 0; j < queue.length(); j++)
            {
                drawDot(queue.get(j).x, queue.get(j).y, Color.CYAN);
            }

            bitmapPaint.setTextSize(100);
            bitmapPaint.setColor(Color.WHITE);
            canvas.drawText("GAME OVER", width / 2 - 285, height / 2 - 20, bitmapPaint);

            try
            {
                drawControls();
                Thread.sleep(100);
                clearGrid();
//                drawGrid();
            }
            catch(Exception e) {}

        }

        public void update()
        {
            bitmapPaint.setColor(Color.CYAN);
            bitmapPaint.setTextSize(30);
            canvas.drawText("Made by Jaewan Yun (jay50@pitt.edu)", 10, 25, bitmapPaint);

            drawDot(food[0], food[1], Color.GREEN);

            drawControls();
        }

//        public void updateFood()
//        {
//            drawDot(food[0], food[1], Color.GREEN);
//            invalidate();
//        }


        public void setWindow(int width, int height)
        {
            this.width = width;
            this.height = height;
            this.div = width / 7;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh)
        {
            super.onSizeChanged(w, h, oldw, oldh);

            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);

            gs = new GameState(width / div, height / div);
            queue = new JayList<Key>();
            queue.addLast(gs.acquireKey((width / 2) / div, ((height / 2) - 1) / div));

            food = new int[2];

            (new Thread(new GameLoop(gs, queue, food, dp))).start();

//            drawGrid();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();

            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
            {
                if(x >= 4 * div && x <= 5 * div && y >= 8 * div && y <= 9 * div)
                {
                    // UP
                    gs.turnUp();
                }
                else if(x >= 4 * div && x <= 5 * div && y >= 10 * div && y <= 11 * div)
                {
                    // DOWN
                    gs.turnDown();
                }
                else if(x >= 3 * div && x <= 4 * div && y >= 9 * div && y <= 10 * div)
                {
                    // LEFT
                    gs.turnLeft();
                }
                else if(x >= 5 * div && x <= 6 * div && y >= 9 * div && y <= 10 * div)
                {
                    // RIGHT
                    gs.turnRight();
                }

//                updateFood();
            }

            return true;
        }

        public void drawDot(int x, int y, int color)
        {
            bitmapPaint.setColor(color);
            bitmapPaint.setStyle(Paint.Style.FILL);

            int gridx = (x * div) + 1;
            int gridy = (y * div) + 1;

            Rect r = new Rect(gridx, gridy, gridx + div - 2, gridy + div - 1);
            canvas.drawRect(r, bitmapPaint);

            postInvalidate();
        }

        public void drawControls()
        {
            bitmapPaint.setColor(Color.GREEN);
            bitmapPaint.setStyle(Paint.Style.STROKE);
            bitmapPaint.setTextSize(30);

            int gridx = (4 * div) + 1;
            int gridy = (8 * div) + 1;
            Rect r = new Rect(gridx, gridy, gridx + div - 2, gridy + div - 1);
            canvas.drawRect(r, bitmapPaint);
            r = new Rect(gridx + 1, gridy + 1, gridx + div - 1, gridy + div);
            canvas.drawRect(r, bitmapPaint);
            canvas.drawText("UP", 4 * div + 1, 8 * div + 1, bitmapPaint);

            gridx = (4 * div) + 1;
            gridy = (10 * div) + 1;
            r = new Rect(gridx, gridy, gridx + div - 2, gridy + div - 1);
            canvas.drawRect(r, bitmapPaint);
            r = new Rect(gridx + 1, gridy + 1, gridx + div - 1, gridy + div);
            canvas.drawRect(r, bitmapPaint);
            canvas.drawText("DOWN", 4 * div + 1, 10 * div + 1, bitmapPaint);

            gridx = (3 * div) + 1;
            gridy = (9 * div) + 1;
            r = new Rect(gridx, gridy, gridx + div - 2, gridy + div - 1);
            canvas.drawRect(r, bitmapPaint);
            r = new Rect(gridx + 1, gridy + 1, gridx + div - 1, gridy + div);
            canvas.drawRect(r, bitmapPaint);
            canvas.drawText("LEFT", 3 * div + 1, 9 * div + 1, bitmapPaint);

            gridx = (5 * div) + 1;
            gridy = (9 * div) + 1;
            r = new Rect(gridx, gridy, gridx + div - 2, gridy + div - 1);
            canvas.drawRect(r, bitmapPaint);
            r = new Rect(gridx + 1, gridy + 1, gridx + div - 1, gridy + div);
            canvas.drawRect(r, bitmapPaint);
            canvas.drawText("RIGHT", 5 * div + 1, 9 * div + 1, bitmapPaint);

            postInvalidate();
        }

        public void clearGrid()
        {
            canvas.drawColor(Color.BLACK);
        }

//        public void drawGrid()
//        {
//            bitmapPaint.setColor(Color.WHITE);
//
//            for (int j = 0; j <= width; j += div) {
//                canvas.drawLine(j, 0, j, height, bitmapPaint);
//            }
//            for (int k = 0; k <= height; k += div) {
//                canvas.drawLine(0, k, width, k, bitmapPaint);
//            }
//
//            postInvalidate();
//        }

    }

}
