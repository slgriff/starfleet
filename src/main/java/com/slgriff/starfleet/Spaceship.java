package com.slgriff.starfleet;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Spaceship {
    private final Logger logger = LoggerFactory.getLogger(Spaceship.class);

    private int x,y,z;
    
    private Map<String, List<List<Integer>>> firingPatterns;

    public Spaceship() {
        firingPatterns = new HashMap<String, List<List<Integer>>>();

        List<List<Integer>> alpha = new ArrayList<List<Integer>>(4);
        alpha.add(Arrays.asList(-1,-1));
        alpha.add(Arrays.asList(-1,1));
        alpha.add(Arrays.asList(1,-1));
        alpha.add(Arrays.asList(1,1));

        List<List<Integer>> beta = new ArrayList<List<Integer>>(4);
        beta.add(Arrays.asList(-1,0));
        beta.add(Arrays.asList(0,-1));
        beta.add(Arrays.asList(0,1));
        beta.add(Arrays.asList(1,0));

        List<List<Integer>> gamma = new ArrayList<List<Integer>>(3);
        gamma.add(Arrays.asList(-1, 0));
        gamma.add(Arrays.asList(0, 0));
        gamma.add(Arrays.asList(1, 0));

        List<List<Integer>> delta = new ArrayList<List<Integer>>(3);
        delta.add(Arrays.asList(0, -1));
        delta.add(Arrays.asList(0, 0));
        delta.add(Arrays.asList(0, 1));


        firingPatterns.put("alpha", alpha);
        firingPatterns.put("beta", beta);
        firingPatterns.put("gamma", gamma);
        firingPatterns.put("delta", delta);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(String direction) {
        if (direction.equals("north")) {
            moveNorth();
        } else if (direction.equals("south")) {
            moveSouth();
        } else if (direction.equals("east")) {
            moveEast();
        } else { // west
            moveWest();
        }
    }
  
    private void moveNorth() {
        --y;
    }

    private void moveSouth() {
        ++y;
    }

    private void moveEast() {
        ++x;
    }

    private void moveWest() {
        --x;
    }

    public void fall() {
        ++z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private List<List<Integer>> getFiringPattern(String firingPattern) {
        if (!firingPatterns.containsKey(firingPattern)) {
            return new ArrayList<List<Integer>>();
        }

        return firingPatterns.get(firingPattern);
    }

    public List<List<Integer>> fireTorpedoes(String firingPattern) {
        List<List<Integer>> offsets = getFiringPattern(firingPattern);
        List<List<Integer>> struckCoordinates = new ArrayList<List<Integer>>();
        for (List<Integer> offset : offsets) {
            struckCoordinates.add(Arrays.asList(offset.get(0)+x, offset.get(1)+y));
        }

        logger.debug("struckCoordinates={}", struckCoordinates);
        return struckCoordinates;
    }
}
