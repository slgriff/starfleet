package com.slgriff.starfleet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

class TestSimulator {

    private final Logger logger = LoggerFactory.getLogger(TestMineField.class);
    

    @Test
    void simpleRun() {
        Simulator simulator = new Simulator();

        List<List<Integer>> mines = new LinkedList<List<Integer>>();

        mines.add(Arrays.asList(0,0,26));

        simulator.populateMinefield(mines,0,0);

        List<String> commands = new LinkedList<String>();

        commands.add("gamma");

        for (String command : commands) {
            simulator.performAction(command);
        }

        assertEquals(5, simulator.computeScore());    
    }

    @Test
    void successfulRun() {
        Simulator simulator = new Simulator();

        List<List<Integer>> mines = new LinkedList<List<Integer>>();

        mines.add(Arrays.asList(2,0,52));
        mines.add(Arrays.asList(0,2,52));
        mines.add(Arrays.asList(4,2,52));
        mines.add(Arrays.asList(2,4,52));

        simulator.populateMinefield(mines,5,5);

        List<String> commands = new LinkedList<String>();

        commands.add("north");
        commands.add("delta south");
        commands.add("west");
        commands.add("gamma east");
        commands.add("east");
        commands.add("gamma west");
        commands.add("south");
        commands.add("delta");

        for (String command : commands) {
            simulator.performAction(command);
        }

        assertEquals(8, simulator.computeScore());    
    }

    @Test
    void missedMine() {
        Simulator simulator = new Simulator();

        List<List<Integer>> mines = new LinkedList<List<Integer>>();

        mines.add(Arrays.asList(2,0,1));
        mines.add(Arrays.asList(2,4,1));

        simulator.populateMinefield(mines, 5, 5);

        List<String> commands = new LinkedList<String>();

        commands.add("north");
        commands.add("delta south");
        commands.add("south");
        commands.add("south");
        commands.add("delta");

        for (String command : commands) {
            simulator.performAction(command);
        }

        assertEquals(0, simulator.computeScore());    
    }

    @Test
    void tooShort() {
        Simulator simulator = new Simulator();

        List<List<Integer>> mines = new LinkedList<List<Integer>>();

        mines.add(Arrays.asList(2,0,52));
        mines.add(Arrays.asList(0,2,52));
        mines.add(Arrays.asList(4,2,52));
        mines.add(Arrays.asList(2,4,52));

        simulator.populateMinefield(mines,5,5);

        List<String> commands = new LinkedList<String>();

        commands.add("north");
        commands.add("delta south");
        commands.add("west");
        commands.add("gamma east");
        commands.add("east");
        commands.add("gamma west");

        for (String command : commands) {
            simulator.performAction(command);
        }

        assertEquals(0, simulator.computeScore());    
    }

}
