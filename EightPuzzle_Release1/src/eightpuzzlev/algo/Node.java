/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzlev.algo;

import java.util.Arrays;

/**
 *
 * @author Chetan
 */
public class Node {
    private int[] puzzle;
    private int depth;
    private String action;
   

    public Node(int[] puzzle, int depth,String action) {
        this.puzzle = puzzle;
        this.depth = depth;
        this.action=action;
    }

    public Node() {
    }
    

    /**
     * @return the puzzle
     */
    public int[] getPuzzle() {
        return puzzle;
    }

    /**
     * @param puzzle the puzzle to set
     */
    public void setPuzzle(int[] puzzle) {
        this.puzzle = puzzle;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
    
        String node=Arrays.toString(this.puzzle)+" Depth "+this.depth;
        
        return node.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
