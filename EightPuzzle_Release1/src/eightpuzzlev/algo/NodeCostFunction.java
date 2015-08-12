/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzlev.algo;

/**
 *
 * @author Chetan
 */
public class NodeCostFunction {

    public int findcost(Node2 node) {
        int cost = node.getCost();
        int[] puzzle = node.getPuzzle();
        int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
        int numOfTiles = 0;

        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != 0) {
                if (puzzle[i] != goalSet[i]) {
                    numOfTiles++;
                }
            }
        }
        return numOfTiles + cost;
    }
    
    public int findcost(int[] puzzle) {
        int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
        int numOfTiles = 0;

        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] != 0) {
                if (puzzle[i] != goalSet[i]) {
                    numOfTiles++;
                }
            }
        }
        return numOfTiles;
    }

    public int findGreedyCostH1(Node2 node)
    {
        
        int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
        int numOfTiles=0;
        int puzzle[]=node.getPuzzle();
        
        for(int i=0;i<puzzle.length;i++)
        {
            if(puzzle[i]!=0)
            
            {if(puzzle[i]!=goalSet[i])
            {
                numOfTiles++;
            }
            }
        }       
        return numOfTiles;
    }
    
    public int findGreedyCostH1(int puzzle[])
    {
        
        int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
        int numOfTiles=0;
      
        
        for(int i=0;i<puzzle.length;i++)
        {
            if(puzzle[i]!=0)
            
            {if(puzzle[i]!=goalSet[i])
            {
                numOfTiles++;
            }
            }
        }       
        return numOfTiles;
    }
}
