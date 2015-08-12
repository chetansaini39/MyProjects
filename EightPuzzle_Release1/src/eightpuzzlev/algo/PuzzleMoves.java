/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzlev.algo;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Chetan
 */
public class PuzzleMoves {
    /**
     * Method to move the Blank TIle UP.  Method Checks if the TILE can be moved or not
     * @param puzzle
     * @return Puzzle
     */
     HashMap<String, Integer> map ;

    public PuzzleMoves(HashMap<String, Integer> map) {
        this.map = map;
    }

    public PuzzleMoves() {
    }
    
     
  public int[] moveUP(int[] puzzle)
  {
//      int[] puzzle=helper.DeepCopyArray(puzzle);
      //first find the location of the blank space
      int zeroPos=findZero(puzzle);
      if(zeroPos>2)
      {
//      System.out.println("Moving UP");
      int temp=puzzle[zeroPos-3];
      puzzle[zeroPos-3]=0;
      puzzle[zeroPos]=temp;
    
      }
      
      else
      {
          System.out.println("Cannot Move the Blank Tile UP");
      }
     
      return puzzle;
  }
  /**
   * Method to move the Blank Tile DOWN.
   * @param puzzle
   * @return puzzle
   */
  public int[] moveDOWN(int[] puzzle)
  {
      int zeroPos=findZero(puzzle);
      if(zeroPos<6)
      {
//      System.out.println("Moving DOWN");
      int temp=puzzle[zeroPos+3];
      puzzle[zeroPos+3]=0;
      puzzle[zeroPos]=temp;
      }
      else
      {
          System.out.println("****Cannot Move the Blank Tile DOWN****");
      }
      return puzzle;
  }
  public int[] moveLEFT(int[] puzzle)
  {
      int zeroPos=findZero(puzzle);
      if(zeroPos!=0&&zeroPos!=3&&zeroPos!=6)
      {
//      System.out.println("Moving LEFT");
      int temp=puzzle[zeroPos-1];
      puzzle[zeroPos-1]=0;
      puzzle[zeroPos]=temp;
    
      }
      else
      {
          System.out.println("Cannot Move the Blank Tile LEFT");
      }
      

      return puzzle;
  }
  public int[] moveRIGHT(int[] puzzle)
  {
     int zeroPos=findZero(puzzle);
      if(zeroPos!=2&&zeroPos!=5&&zeroPos!=8)
      {
//      System.out.println("Moving RIGHT");
      int temp=puzzle[zeroPos+1];
      puzzle[zeroPos+1]=0;
      puzzle[zeroPos]=temp;
  
        
      }
      else
      {
          System.out.println("Cannot Move the Blank Tile RIGHT");
      }
     
   
      return puzzle;
  }
  public int findZero(int[] puzzle)
  {
      int zeroPosition=10;
      for(int i=0;i<puzzle.length;i++)
      {
          if(puzzle[i]==0)
          {
              zeroPosition=i;
          }
      }
      return zeroPosition;
  }
    
}
