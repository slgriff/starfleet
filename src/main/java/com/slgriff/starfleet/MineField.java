package com.slgriff.starfleet;

import java.util.SortedMap;
import java.util.TreeMap;

import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MineField {
    private final Logger logger = LoggerFactory.getLogger(MineField.class);

    private SortedMap<Integer, SortedMap<Integer, Integer>> minefield;

    private int numMines;

    private SortedMap<Integer, Integer> minesAtDepth;

    private static final String[] prettyPrintArray = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public MineField() {
        minefield = new TreeMap<Integer, SortedMap<Integer, Integer>>();
        minesAtDepth = new TreeMap<Integer, Integer>();
        numMines = 0;
    }

    public void reset() {
        minefield.clear();
        minesAtDepth.clear();
        numMines = 0;
    }

    public void addMine(int x, int y, int z) {
        logger.debug("Adding mine at coordinate ({},{},{})", x, y, z);
        if (minefield.containsKey(y)) {
            minefield.get(y).put(x, z);
        } else {
            SortedMap<Integer, Integer> innerMap = new TreeMap<Integer, Integer>();
            innerMap.put(x, z);
            minefield.put(y, innerMap);
        }
       
        if (!minesAtDepth.containsKey(z)) {
            minesAtDepth.put(z, 1);
        } else {
            minesAtDepth.put(z, minesAtDepth.get(z)+1);
        }

        logger.debug("minesAtDepth={}", minesAtDepth);

        ++numMines;
    }

    public void destroyMine(int x, int y) {
        logger.debug("Destroying mine at coordinate ({},{})", x, y);
        if (minefield.containsKey(y)) {
            SortedMap<Integer, Integer> innerMap = minefield.get(y);
            if (innerMap != null) {
                if (innerMap.containsKey(x)) {
                    int z = innerMap.remove(x);
            
                    minesAtDepth.put(z, minesAtDepth.get(z)-1);

                    --numMines;
                    logger.debug("Found and removed mine");
                }
            }

            if (innerMap == null || innerMap.size() == 0) {
                minefield.remove(y);
            }
        }
    }

    public int getNumMines() {
        return numMines;
    }

    public int getMinesAtDepth(int z) {
        if (!minesAtDepth.containsKey(z)) {
            return 0;
        }

        return minesAtDepth.get(z);
    }

    public String prettyPrint(int x, int y, int z) {
        if (numMines == 0) {
            return ".";
        }
        
        StringBuilder sb = new StringBuilder();
    
        int minRow = Math.min(y, minefield.firstKey());
        int maxRow = Math.max(y, minefield.lastKey());
        

        double rowMid = (minRow+maxRow)/2.0;

        logger.debug("minRow={},maxRow={},mid={}", minRow, maxRow, rowMid);


        if (minRow != maxRow) {

           if (y < rowMid) {
              --minRow;
              if ((Math.abs(maxRow-minRow)+1 & 1) == 0) {
                  --minRow;
              }
           }

           if (y > rowMid) {
              ++maxRow;
 
              if ((Math.abs(maxRow-minRow)+1 & 1) == 0) {
                  ++maxRow;
              }
           }
        }


        int minCol = x;
        int maxCol = x;

        for (Integer i : minefield.keySet()) {
            minCol = Math.min(minCol, minefield.get(i).firstKey());
            maxCol = Math.max(maxCol, minefield.get(i).lastKey());
        }

        double colMid = (minCol+maxCol)/2.0;


        logger.debug("minCol={},maxCol={},mid={}", minCol, maxCol, colMid);

        if (maxCol != minCol) {
            if (x < colMid) {
                --minCol;

                if ((Math.abs(maxCol-minCol)+1 & 1) == 0) {
                    --minCol;
                }
            }

            if (x > colMid) {
                ++maxCol;

                if ((Math.abs(maxCol-minCol)+1 & 1) == 0) {
                    ++maxCol;
                }
            }
        }

        for (int row = minRow; row <= maxRow; ++row) {
            for (int col = minCol; col <= maxCol; ++col) {
                String loc = ".";                
                if (minefield.containsKey(row)) {
                    if (minefield.get(row).containsKey(col)) {
                        int mineZ = minefield.get(row).get(col);
                        if (mineZ <= z) {
                            loc = "*";
                        } else {
                            loc = prettyPrintArray[mineZ-1-z];
                        }
                    }
                }
                sb.append(loc);
            }
            if (row != maxRow) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static int getStartingDepth(String c) {
        for (int index = 0; index < prettyPrintArray.length; ++index) {
            if (prettyPrintArray[index].equals(c)) {
                return index+1;
            }
        }
        
        throw new IllegalStateException();
    }
}
