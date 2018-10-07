package com.slgriff.starfleet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Simulator {
    private static final Logger logger = LoggerFactory.getLogger(Simulator.class);

    public static final int MINE_SCORE = 10;
    public static final int SHOT_PENALTY = 5;
    public static final int MOVE_PENALTY = 2;

    public static final int SHOT_MAX_LOSS_MULTIPLIER = 5;
    public static final int MOVE_MAX_LOSS_MULTIPLIER = 3;
    
    private MineField minefield = new MineField();
    private Spaceship spaceship = new Spaceship();

    private int numInitialMines = 0;
    private int numShotsFired = 0;
    private int numMoves = 0;
    private int score = 0;

    private boolean missedMine = false;

    public Simulator() {}

    public void populateMinefield(List<List<Integer>> mines, int width, int height) {
        minefield.reset();

        for (List<Integer> mine : mines) {
            minefield.addMine(mine.get(0), mine.get(1), mine.get(2));
        }

        numInitialMines = mines.size();

        logger.debug("numInitialMines={}", numInitialMines);

        int midX = width/2;
        int midY = height/2;

        logger.debug("midX={}, midY={}", midX, midY);

        spaceship.setPosition(midX, midY); // center spaceship
    }

    public void reset() {
        numInitialMines = 0;
        numShotsFired = 0;
        numMoves = 0;
        score = 0;

        missedMine = false;

        minefield.reset();
    }

    private void moveSpaceship(String direction) {
        ++numMoves;
        spaceship.move(direction);
    }

    private void fireTorpedoes(String firingPattern) {
        ++numShotsFired;
        destroyMines(spaceship.fireTorpedoes(firingPattern));
    }

    public void performAction(String command) {
        int separatorIndex = command.indexOf(" ");

        if (separatorIndex != -1) {
            String firingPattern = command.substring(0, separatorIndex);
            String direction = command.substring(separatorIndex+1);

            logger.debug("firingPattern='{}', direction='{}'", firingPattern, direction);

            fireTorpedoes(firingPattern);
            moveSpaceship(direction);
            
        } else {
            if (isMoveCommand(command)) {
                moveSpaceship(command);
            } else {
                fireTorpedoes(command);
            }
        }

        spaceship.fall();

        missedMine = missedMine || (minefield.getMinesAtDepth(spaceship.getZ()) != 0);
    }

    private void destroyMines(List<List<Integer>> struckCoordinates) {
        for (List<Integer> struckCoordinate : struckCoordinates) {
            minefield.destroyMine(struckCoordinate.get(0),struckCoordinate.get(1));
        }
    }

    public void printMinefield() {
        System.out.println(minefield.prettyPrint(spaceship.getX(), spaceship.getY(), spaceship.getZ()));
    }

    public boolean missedMine() {
        return missedMine;
    }

    public boolean minesCleared() {
        return minefield.getNumMines() == 0;
    }

    private boolean isMoveCommand(String command) {
        return command.equals("north") || command.equals("south") || command.equals("east") || command.equals("west");
    }

    public int computeScore() {
        logger.debug("numInitialMines={}, numShotsFired={}, numMoves={}", numInitialMines, numShotsFired, numMoves);

        if (minefield.getNumMines() != 0 || missedMine) {
            return 0;
        }

        return MINE_SCORE * numInitialMines 
                - Math.min(SHOT_PENALTY * numShotsFired, SHOT_MAX_LOSS_MULTIPLIER * numInitialMines)
                - Math.min(MOVE_PENALTY * numMoves, MOVE_MAX_LOSS_MULTIPLIER * numInitialMines);
    }

    
}
