package com.slgriff.starfleet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        String fieldFileName = args[0];
        String scriptFileName = args[1];

        int gridWidth = -1;
        int gridHeight = 0;

        List<List<Integer>> mines = new LinkedList<List<Integer>>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fieldFileName))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (gridWidth == -1) {
                    gridWidth = currentLine.length();
                }

                int y = gridHeight++;
                for (int x = 0; x < gridWidth; ++x) {
                    char c = currentLine.charAt(x);
                    if (c != '.') {
                        mines.add(Arrays.asList(x, y, MineField.getStartingDepth(String.valueOf(c))));
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Caught an I/O error, add proper error handling", e);
        }

        Simulator simulator = new Simulator();

        logger.debug("gridWidth={}, gridHeight={}", gridWidth, gridHeight);

        simulator.populateMinefield(mines, gridWidth, gridHeight-1);
        
        int stepCount = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFileName))) {
            String currentLine;
            while (!simulator.missedMine() && !simulator.minesCleared() && ((currentLine = reader.readLine()) != null)) {
                System.out.format("Step %d\n", stepCount);

                System.out.println();

                simulator.printMinefield();
            
                System.out.println();

                System.out.println(currentLine);
        
                simulator.performAction(currentLine);

                System.out.println();
                
                simulator.printMinefield();

                System.out.println();

                ++stepCount;

                logger.debug("missedMine={}, minesCleared={}", simulator.missedMine(), simulator.minesCleared());
            }

            String nextLine = reader.readLine();
            logger.debug("nextLine={}", nextLine);

            if (simulator.missedMine() || !simulator.minesCleared()) {
                System.out.println("fail (0)\n");
            } else if (nextLine != null) {
                System.out.println("pass (1)\n");
            } else {
                System.out.format("pass (%d)\n", simulator.computeScore());
            }
        } catch (IOException e) {
            logger.error("Caught an I/O error, add proper error handling", e);
        }
    }
}
