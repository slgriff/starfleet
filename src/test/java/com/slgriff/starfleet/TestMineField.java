package com.slgriff.starfleet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class TestMineField {

    private final Logger logger = LoggerFactory.getLogger(TestMineField.class);
    

    @Test
    void simple() {
        MineField minefield = new MineField();
        assertEquals(0, minefield.getNumMines(), "Minefield should start empty");

        minefield.addMine(0,0,26);
        assertEquals(1, minefield.getNumMines(), "Minefield should contain a mine");

        minefield.destroyMine(0,0);
        assertEquals(0, minefield.getNumMines(), "Minefield should be empty");        
    }

    @Test
    void display() {
        MineField minefield = new MineField();
        assertEquals(".", minefield.prettyPrint(0,0,0));

        minefield.addMine(0,0,26);
        assertEquals("z", minefield.prettyPrint(0,0,0));

        minefield.reset();
    
        minefield.addMine(2,1,1);
        minefield.addMine(1,0,5);
        minefield.addMine(0,2,27);

        assertEquals(".e.\n..a\nA..", minefield.prettyPrint(1,1,0));

        minefield.reset();
        
        minefield.addMine(-2,0,10);
        minefield.addMine(1,1,10);
        
        assertEquals(".....\nj....\n...j.", minefield.prettyPrint(0,0,0));
        assertEquals(".....\n.....\n.....\ni....\n...i.", minefield.prettyPrint(0,-1,1));
    }
}
