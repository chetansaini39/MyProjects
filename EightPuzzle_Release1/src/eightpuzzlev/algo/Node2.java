package eightpuzzlev.algo;

import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chetan
 */
public class Node2 
{
 private int[] puzzle;
    private int depth;
    private String action;
    private int cost;

    public Node2(int[] puzzle, int depth, String action, int cost) {
        this.puzzle = puzzle;
        this.depth = depth;
        this.action = action;
        this.cost = cost;
    }

    public Node2() {
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

    /**
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        String node=(Arrays.toString(this.puzzle)+"\t Depth: "+this.getDepth()+"\t Cost: "+this.getCost());
        return node.toString(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
