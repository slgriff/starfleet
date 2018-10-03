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
        x = 0;
        y = 0;
        z = 0;

        firingPatterns = new HashMap<String, List<List<Integer>>>();

        List<List<Integer>> gamma = new ArrayList<List<Integer>>(3);
    
        gamma.add(Arrays.asList(-1, 0));
        gamma.add(Arrays.asList(0, 0));
        gamma.add(Arrays.asList(1, 0));

        firingPatterns.put("gamma", gamma);
    }

    public void resetPosition() {
        x = 0;
        y = 0;
        z = 0;
    }
  
    public void moveNorth() {
        --y;
    }

    public void moveSouth() {
        ++y;
    }

    public void moveEast() {
        ++x;
    }

    public void moveWest() {
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
