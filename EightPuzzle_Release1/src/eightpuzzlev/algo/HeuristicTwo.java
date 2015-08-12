/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzlev.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chetan
 */
public class HeuristicTwo {

    int[] puzzle;
    int[] problemEasy = {1, 3, 4, 8, 6, 2, 7, 0, 5};
    String problem="134862705";
  int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
            
 
   
 public  int calcManhattan(String currState, String goalState){
//lookup table for manhattan distance
int [][] manValue = {
{0,1,2,1,2,3,2,3,4},
{1,0,1,2,1,2,3,2,3},
{2,1,0,3,2,1,4,3,2},
{1,2,3,0,1,2,1,2,3},
{2,1,2,1,0,1,2,1,2},
{3,2,1,2,1,0,3,2,1},
{2,3,4,1,2,3,0,1,2},
{3,2,3,2,1,2,1,0,1},
{4,3,2,3,2,1,2,1,0},
};
//calculate manhattan distance
int heu = 0 ;
int result = 0;
//String a = null;
for (int i=1; i<9;i++){
heu = manValue[currState.indexOf(String.valueOf(i))][goalState.indexOf(String.valueOf(i))];
//heuristic=manValue[]
result = result + heu;

}
return result;
}
 
 /**
  * Method for calulating the heuristic of the function using Manhatten Distance
  * @param current
  * @param goal
  * @return 
  */
 public  int calcManhattan(int[] current){
String currState=Arrays.toString(current).replaceAll("[,\\[\\] ]", "");
String goalState=Arrays.toString(goalSet).replaceAll("[,\\[\\] ]", "");
//lookup table for manhattan distance
int [][] manValue = {
{0,1,2,1,2,3,2,3,4},
{1,0,1,2,1,2,3,2,3},
{2,1,0,3,2,1,4,3,2},
{1,2,3,0,1,2,1,2,3},
{2,1,2,1,0,1,2,1,2},
{3,2,1,2,1,0,3,2,1},
{2,3,4,1,2,3,0,1,2},
{3,2,3,2,1,2,1,0,1},
{4,3,2,3,2,1,2,1,0},
};
//calculate manhattan distance
int heu = 0 ;
int result = 0;
//String a = null;
for (int i=1; i<9;i++){
heu = manValue[currState.indexOf(String.valueOf(i))][goalState.indexOf(String.valueOf(i))];
//heuristic=manValue[]
result = result + heu;

}
return result;
}
}
