package com.jay50.jae.snake;

import java.util.HashMap;
import java.util.Set;

public class GameState
{
    private volatile HashMap state;
    Key[][] keys;

    volatile boolean up;
    volatile boolean down;
    volatile boolean left;
    volatile boolean right;

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public GameState(int x, int y)
    {
        state = new HashMap();
        keys = new Key[x][y];
        for(int j = 0; j < x; j++)
        {
            for(int k = 0; k < y; k++)
            {
                keys[j][k] = new Key(j, k);
            }
        }
    }

    public synchronized void turnUp()
    {
        up = true;
        down = false;
        left = false;
        right = false;
    }

    public synchronized void turnDown()
    {
        up = false;
        down = true;
        left = false;
        right = false;
    }

    public synchronized void turnLeft()
    {
        up = false;
        down = false;
        left = true;
        right = false;
    }

    public synchronized void turnRight()
    {
        up = false;
        down = false;
        left = false;
        right = true;
    }

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public synchronized void add(Key key)
    {
        state.put(key, 0);
    }

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public synchronized void delete(Key key)
    {
        state.remove(key);
    }

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public synchronized Key acquireKey(int x, int y)
    {
        return keys[x][y];
    }

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public synchronized boolean occupied(Key key)
    {
        return state.containsKey(key);
    }

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public synchronized int length()
    {
        return state.keySet().size();
    }

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public synchronized Set getKeys()
    {
        return state.keySet();
    }

    /**
     *	@since 1.0.0
     *	@author Jaewan Yun (Jay50@pitt.edu)
     */
    public synchronized void clear()
    {
        state.clear();
    }


}