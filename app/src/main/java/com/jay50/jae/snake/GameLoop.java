package com.jay50.jae.snake;

/**
 * Created by Jae on 5/12/2016.
 */

import android.graphics.Color;

import java.util.*;

public class GameLoop implements Runnable
{
    volatile GameState gs;
    volatile JayList<Key> queue;
    Updatable dp;
    volatile int[] food;
    final int START_SIZE = 5;

    private boolean foodExists;
    private boolean doNotDelete;

    public GameLoop(GameState gs, JayList<Key> queue, int[] food, Updatable dp)
    {
        this.gs = gs;
        this.queue = queue;
        this.dp = dp;
        this.food = food;
        generateFood();
    }

    public void generateFood()
    {
        food[0] = new Random().nextInt(gs.keys.length);
        food[1] = new Random().nextInt(gs.keys[0].length);
    }

    public void lose()
    {
        for(int j = 0; j < queue.length() - 1; j++)
        {
            gs.delete(queue.removeFirst());
        }
        dp.lose();
    }

    public void run()
    {
        while(true)
        {
            if(!foodExists)
            {
                generateFood();
                foodExists = true;
            }

            if(gs.up)
            {
                Key key = queue.getLast();

                if(key.y - 1 < 0)
                {
                    lose();
                    continue;
                }

                Key temp = queue.addLast(gs.acquireKey(key.x, key.y - 1));
                dp.drawDot(temp.x, temp.y, Color.CYAN);

                if(gs.occupied(temp))
                {
                    lose();
                    continue;
                }
                gs.add(temp);


                if(temp.x == food[0] && temp.y == food[1])
                {
                    dp.drawDot(food[0], food[1], Color.CYAN);
                    doNotDelete = true;
                    foodExists = false;
                }

                if(queue.length() > START_SIZE && !doNotDelete)
                {
                    Key temp2 = queue.removeFirst();
                    gs.delete(temp2);
                    dp.drawDot(temp2.x, temp2.y, Color.BLACK);
                }
                doNotDelete = false;
            }
            else if(gs.down)
            {
                Key key = queue.getLast();

                if(key.y + 1 > gs.keys[0].length - 1)
                {
                    lose();
                    continue;
                }

                Key temp = queue.addLast(gs.acquireKey(key.x, key.y + 1));
                dp.drawDot(temp.x, temp.y, Color.CYAN);

                if(gs.occupied(temp))
                {
                    lose();
                    continue;
                }
                gs.add(temp);


                if(temp.x == food[0] && temp.y == food[1])
                {
                    dp.drawDot(food[0], food[1], Color.CYAN);
                    doNotDelete = true;
                    foodExists = false;
                }

                if(queue.length() > START_SIZE && !doNotDelete)
                {
                    Key temp2 = queue.removeFirst();
                    gs.delete(temp2);
                    dp.drawDot(temp2.x, temp2.y, Color.BLACK);
                }
                doNotDelete = false;
            }
            else if(gs.left)
            {
                Key key = queue.getLast();

                if(key.x - 1 < 0)
                {
                    lose();
                    continue;
                }

                Key temp = queue.addLast(gs.acquireKey(key.x - 1, key.y));
                dp.drawDot(temp.x, temp.y, Color.CYAN);

                if(gs.occupied(temp))
                {
                    lose();
                    continue;
                }
                gs.add(temp);


                if(temp.x == food[0] && temp.y == food[1])
                {
                    dp.drawDot(food[0], food[1], Color.CYAN);
                    doNotDelete = true;
                    foodExists = false;
                }

                if(queue.length() > START_SIZE && !doNotDelete)
                {
                    Key temp2 = queue.removeFirst();
                    gs.delete(temp2);
                    dp.drawDot(temp2.x, temp2.y, Color.BLACK);
                }
                doNotDelete = false;
            }
            else if(gs.right)
            {
                Key key = queue.getLast();

                if(key.x + 1 > gs.keys.length - 1)
                {
                    lose();
                    continue;
                }

                Key temp = queue.addLast(gs.acquireKey(key.x + 1, key.y));
                dp.drawDot(temp.x, temp.y, Color.CYAN);

                if(gs.occupied(temp))
                {
                    lose();
                    continue;
                }
                gs.add(temp);


                if(temp.x == food[0] && temp.y == food[1])
                {
                    dp.drawDot(food[0], food[1], Color.CYAN);
                    doNotDelete = true;
                    foodExists = false;
                }

                if(queue.length() > START_SIZE && !doNotDelete)
                {
                    Key temp2 = queue.removeFirst();
                    gs.delete(temp2);
                    dp.drawDot(temp2.x, temp2.y, Color.BLACK);
                }
                doNotDelete = false;
            }

            try
            {
                Thread.sleep(200);
            }
            catch(Exception e) {}

            dp.update();
        }
    }
}
