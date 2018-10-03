package com.slgriff.starfleet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class Evaluator {
    private static final Logger logger = LoggerFactory.getLogger(Evaluator.class);

    public static void main(String[] args) {
        Spaceship spaceship = new Spaceship();
        
        MineField minefield = new MineField();
        minefield.addMine(0,0,26);

        String command = "gamma";

        int i = 1;
        do {
            logger.debug("Step {}", i);

            logger.debug(minefield.prettyPrint(spaceship.getX(), spaceship.getY(), spaceship.getZ()));

            logger.debug("{}", command);       
            evaluate(spaceship, minefield, "gamma");

            logger.debug(minefield.prettyPrint(spaceship.getX(), spaceship.getY(), spaceship.getZ()));
    
            spaceship.fall();
            ++i;
        } while (minefield.getNumMines() != 0 && minefield.getMinesAtDepth(spaceship.getZ()) == 0);

        if (minefield.getMinesAtDepth(spaceship.getZ()) != 0 || minefield.getNumMines() != 0) {
            logger.debug("fail (0)");
        } else {
            int score = 0;
            logger.debug("pass ({})", score);
        }
    }

    public static void evaluate(Spaceship spaceship, MineField minefield, String command) {
        List<List<Integer>> struckCoordinates = spaceship.fireTorpedoes(command);
        for (List<Integer> coordinates : struckCoordinates) {
            minefield.destroyMine(coordinates.get(0), coordinates.get(1));
        }
    }
}
