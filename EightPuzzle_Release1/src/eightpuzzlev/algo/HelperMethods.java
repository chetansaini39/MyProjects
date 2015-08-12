/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzlev.algo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Chetan
 */
public class HelperMethods {
    static int count=0;
    String initVal="";
    public static String solutionSteps;
    long startTime= System.currentTimeMillis();
    int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
    HashMap<String, String> m= new HashMap<>();
     boolean finished=true;
    
     public int[] DeepCopyArray(int[] arr)
    {
        int[] copy= new int[arr.length];
        for(int i=0;i<arr.length;i++)
        {
            copy[i]=arr[i];
        }
        return copy;
    }
     /**
     * Method to add int[] array to Map & queue
     * @param node
     * @param n 
     * @param map 
     */
     public void addToBFS_MapQueue(int[] node, HashMap map,Queue queue,int[] parent,String val)
    {
       boolean finished=true;
        String key=Arrays.toString(node);
        String mParent=Arrays.toString(parent);
      
        if(!map.containsKey(key))
        {
            map.put(key, getCount());
            queue.add(key);
            m.put(key, m.get(mParent).concat(val));
            if(Arrays.equals(goalSet, node))
            {
                System.out.println("*********************Goal State Found**************************"  );
                System.out.println("Solution Exists at Levet "+map.get(key)+ " of the Tree");
                System.out.println("Steps : "+m.get(key));
                solutionSteps=m.get(key).replaceAll(" ", "");
//                PlaySolution();
                long stopTime=System.currentTimeMillis();
                SimpleDateFormat df= new SimpleDateFormat("mm:ss:SSS");
                System.out.println("Total Time : "+(df.format(new Date(stopTime-startTime))));
                System.out.println("Depth -> "+solutionSteps.length());
//                System.exit(0);
                queue.clear();
              finished=false;
                
            }
          
        }
    
    }
     public void addToDFS_MapStack(int[] node, HashMap map,Stack stack,int[] parent,String val)
    {
       
        String key=Arrays.toString(node);
        String mParent=Arrays.toString(parent);
        if(!map.containsKey(key))
        {
            map.put(key, getCount());
            stack.push(key);
           m.put(key, m.get(mParent).concat(val));
           System.gc();
            if(Arrays.equals(goalSet, node))
            {
                System.out.println("*********************Goal State Found**************************"  );
                System.out.println("Solution Exists at Level "+map.get(key)+ " of the Tree");
                 solutionSteps=m.get(key).replaceAll(" ", "");
//                PlaySolution();
                 long stopTime=System.currentTimeMillis();
                 SimpleDateFormat df= new SimpleDateFormat("mm:ss:SSS");
                System.out.println("Total Time : "+(df.format(new Date(stopTime-startTime))));
                stack.clear();
                stack.clear();
                stack.removeAllElements();
//                System.exit(0);
              
            }
           
        }
    }
     /**
      * Method to print the puzzle board in current state
      * @param arr 
      */
     public void printPuzzle(int[] arr)
    {System.out.println("");
        if(arr.length!=9)
        {
            System.out.println("Improper puzzle length");
        }
        
            System.out.println(arr[0]+" | "+arr[1]+" | "+arr[2]+"\n"+"---------"+"\n"+
                               arr[3]+" | "+arr[4]+" | "+arr[5]+"\n"+"---------"+"\n"+
                               arr[6]+" | "+arr[7]+" | "+arr[8]);
            
            
        
    }
     public int[] convertStringToIntArray(String data)
     {
         String data1=data.replaceAll("[\\,\\[\\]\\ ]", "");
         int[] arrInt= new int[9];
         int length=data1.length();
         for(int i=0;i<length;i++)
         {
             arrInt[i]=Character.getNumericValue(data1.charAt(i));
         }
         return arrInt;
     }
     /**
      * Method to maintain count state for MAP
      * @return 
      */
     public int getCount()
     {
        count =count+1;
//         System.out.println("Count : "+count);
         
         return count;
     }
     /**
      * Method for printing the contents of the MAP
      * @param map 
      */
     public void printMap(HashMap<String,Integer> map)
     {
        for(String key : map.keySet())
        {
            System.out.println(key + " " + map.get(key));
        }
     }
     public void printQueue(Queue q)
     {
         int count=1;
         Iterator it=q.iterator();
         while(it.hasNext())
         {
             
             count++;
//             System.out.println(count+ " : "+it.next());
         }
     }
     
     /**
      * Map to add the moves
      */
     public void addMovesToMap(int[] key, String value)
     {
         String s=Arrays.toString(key);
         m.put(s, value);
//         System.out.println("Map - > "+m);
     }
     
//   public void PlaySolution()
//    {
//        PuzzleMoves movePuzzle= new PuzzleMoves();
//    
//       printPuzzle(BFS_Algo.problemSet);
//       for(int i=0;i<solutionSteps.length();i++)
//       {
//           if(solutionSteps.charAt(i)=='L')
//           {
//               movePuzzle.moveLEFT(BFS_Algo.problemSet);
//               printPuzzle(BFS_Algo.problemSet);
//           }
//           else  if(solutionSteps.charAt(i)=='R')
//           {
//               movePuzzle.moveRIGHT(BFS_Algo.problemSet);
//               printPuzzle(BFS_Algo.problemSet);
//           }
//           if(solutionSteps.charAt(i)=='U')
//           {
//               movePuzzle.moveUP(BFS_Algo.problemSet);
//               printPuzzle(BFS_Algo.problemSet);
//           }
//           else  if(solutionSteps.charAt(i)=='D')
//           {
//               movePuzzle.moveDOWN(BFS_Algo.problemSet);
//               printPuzzle(BFS_Algo.problemSet);
//           }
//       }
//    }
   
   /**
     * Method to print the stack
     *
     * @param stack
     */
    public void printStack(Stack<Node> stack) {
        System.out.println("Contents of Stack ");
        Node[] n = new Node[stack.size()];
        int y = 0;
        Iterator it = stack.iterator();
        while (it.hasNext()) {
            n[y] = (Node) it.next();
            y++;
        }
    }
    
    public int FindPositionOf(int data, int[] puzzle)
    { if(m!=null)
    {
        m=null;
    }
        int postion=10;
        for(int i=0;i<puzzle.length;i++)
        {
            if(data==puzzle[i])
            {
                postion=i;
            }
        }
        return postion;
    }
}
