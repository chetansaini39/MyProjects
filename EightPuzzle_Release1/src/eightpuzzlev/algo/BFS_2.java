/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eightpuzzlev.algo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Chetan
 */
public class BFS_2 implements Runnable {

    public int space;
    int[] puzzle;
    List<Node2> listNode = new ArrayList<>();
    Queue<Node2> queue;
    private HashMap<String, Integer> map;
    HelperMethods helper;
    int count = 0;
    
    public String solutionSteps;
    int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
    long startTime;
    long stopTime;
    public String time;

    public BFS_2(int[] puzzle) {
        this.puzzle = puzzle.clone();
        queue = new LinkedList<>();
        helper = new HelperMethods();
        map = new HashMap<>();
    }

    public void solve() {
        startTime = System.currentTimeMillis();
        Node2 node = new Node2(puzzle, 0, "", 0);
        listNode.add(node);
        if (Arrays.equals(node.getPuzzle(), goalSet)) {
            System.out.println("&&&&&&&&&&&&&&&****************Solution Found****************");
        }
        addToMapAndQueue(listNode, count);
        System.out.println("Queue empty ? " + queue.isEmpty());
        while (queue.size() > 0) {
            node = queue.poll();
            System.out.println("Queue Size  after popping off " + queue.size());
            System.out.println("Current Node on Queue" + node);
            listNode = (GenerateStateNodes(node));
            addToMapAndQueue(listNode, count);
            printListNodes(listNode);
        }
    }

    /**
     * Method to add List of Nodes to map & stack
     *
     * @param listNode
     * @param count
     */
    private void addToMapAndQueue(List<Node2> listNode, int count) {
        ListIterator<Node2> it = listNode.listIterator();
        while (it.hasNext()) {
            Node2 node = it.next();
            if (!map.containsKey(Arrays.toString(node.getPuzzle()))) {
                map.put(Arrays.toString(node.getPuzzle()), ++count);
//                stack.push(node);
                queue.add(node);
                System.out.println("Added to stack " + node);
                if (Arrays.equals(node.getPuzzle(), goalSet)) {
                    System.out.println("&&&&&&&&&&&&&&&****************Solution Found****************");
                    System.out.println("At Depth " + node.getDepth());
                    solutionSteps = node.getAction();
                    System.out.println("Solution Actions " + solutionSteps);
                    space = queue.size();
                    System.out.println("Queue Size : " + space);
                    queue.clear();
                    stopTime = System.currentTimeMillis();
                    SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
                    time = Long.toString(stopTime - startTime);
                    System.out.println("Total Time HH:MM:SS : " + df.format(new Date(stopTime - startTime)));
                    System.out.println("Time in Milliseconds : " + time);
                    break;
                }
            }
        }
    }

    /**
     * Method to generate state Nodes
     *
     * @param puzzle
     * @param b
     * @return List<Node>
     */
    public List GenerateStateNodes(Node2 node) {
        //int[] puzzle = helper.convertStringToIntArray(p);
        List<Node2> list = new ArrayList<>();
        int[] p = node.getPuzzle();
        int depth = node.getDepth();
        PuzzleMoves movePuzzle = new PuzzleMoves();
        int zeroPOS = movePuzzle.findZero(p);

        if (zeroPOS == 8) {
            System.out.println("Zero at position 8 \nTile can depthe moved UP & LEFT");

            //add the state to the map
            Node2 stateLEFT = new Node2();
            Node2 stateUP = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(p)));
            stateLEFT.setDepth(depth + 1);

            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(p)));
            stateUP.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateUP.setAction(node.getAction() + "U");

            stateLEFT.setCost(0);
            stateUP.setCost(0);

            list.add(stateLEFT);
            list.add(stateUP);

        } else if (zeroPOS == 7) {
            System.out.println("Tile Can be Moved UP, LEFT & RIGHT");

            Node2 stateLEFT = new Node2();
            Node2 stateRIGHT = new Node2();
            Node2 stateUP = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(p)));
            stateLEFT.setDepth(depth + 1);
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(p)));
            stateRIGHT.setDepth(depth + 1);
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(p)));
            stateUP.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");

            stateLEFT.setCost(0);
            stateRIGHT.setCost(0);
            stateUP.setCost(0);

            list.add(stateLEFT);
            list.add(stateRIGHT);
            list.add(stateUP);

        } else if (zeroPOS == 6) {
            System.out.println("Tile Can be Moved UP & RIGHT");
            //need to generate two states UP & RIGHT
            Node2 stateRIGHT = new Node2();
            Node2 stateUP = new Node2();

            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(p)));
            stateRIGHT.setDepth(depth + 1);
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(p)));
            stateUP.setDepth(depth + 1);

            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");

            stateRIGHT.setCost(0);
            stateUP.setCost(0);

            list.add(stateRIGHT);
            list.add(stateUP);
        } else if (zeroPOS == 5) {
            System.out.println("Tile can be moved UP, DOWN & LEFT");
            //generate three states UP, DOWN, LEFT
            Node2 stateLEFT = new Node2();
            Node2 stateUP = new Node2();
            Node2 stateDOWN = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(p)));
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(p)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(p)));

            stateLEFT.setDepth(depth + 1);
            stateUP.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateUP.setAction(node.getAction() + "U");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(0);
            stateUP.setCost(0);
            stateDOWN.setCost(0);
            list.add(stateLEFT);
            list.add(stateUP);
            list.add(stateDOWN);

        } else if (zeroPOS == 4) {
            System.out.println("Tile can be moved UP, DOWN, LEFT & RIGHT");
            Node2 stateLEFT = new Node2();
            Node2 stateRIGHT = new Node2();
            Node2 stateUP = new Node2();
            Node2 stateDOWN = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(p)));
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(p)));
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(p)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(p)));

            stateLEFT.setDepth(depth + 1);
            stateRIGHT.setDepth(depth + 1);
            stateUP.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(0);
            stateRIGHT.setCost(0);
            stateUP.setCost(0);
            stateDOWN.setCost(0);

            list.add(stateLEFT);
            list.add(stateRIGHT);
            list.add(stateUP);
            list.add(stateDOWN);

        } else if (zeroPOS == 3) {
            System.out.println("Tile can be moved UP, DOWN & RIGHT");
            //generate 3 states UP, DOWN, & RIGHT
            Node2 stateRIGHT = new Node2();
            Node2 stateUP = new Node2();
            Node2 stateDOWN = new Node2();
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(p)));
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(p)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(p)));

            stateRIGHT.setDepth(depth + 1);
            stateUP.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");
            stateDOWN.setAction(node.getAction() + "D");

            stateRIGHT.setCost(0);
            stateUP.setCost(0);
            stateDOWN.setCost(0);
            list.add(stateRIGHT);
            list.add(stateUP);
            list.add(stateDOWN);

        } else if (zeroPOS == 2) {
            System.out.println("Tile can be moved  DOWN & LEFT");
            //generate 2 states 
            Node2 stateLEFT = new Node2();
            Node2 stateDOWN = new Node2();
            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(p)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(p)));

            stateLEFT.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(0);
            stateDOWN.setCost(0);

            list.add(stateLEFT);
            list.add(stateDOWN);

        } else if (zeroPOS == 1) {
            System.out.println("Tile can be moved LEFT, RIGHT & DOWN");
            //generate 3 states LEFT, RIGHT & DOWN
            Node2 stateLEFT = new Node2();
            Node2 stateRIGHT = new Node2();
            Node2 stateDOWN = new Node2();
            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(p)));
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(p)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(p)));

            stateLEFT.setDepth(depth + 1);
            stateRIGHT.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateRIGHT.setAction(node.getAction() + "R");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(0);
            stateRIGHT.setCost(0);
            stateDOWN.setCost(0);

            list.add(stateLEFT);
            list.add(stateRIGHT);
            list.add(stateDOWN);

        } else if (zeroPOS == 0) {
            System.out.println("Tile can be moved RIGHT & DOWN");
            //generate 4 states UP, DOWN, LEFT & RIGHT
            Node2 stateRIGHT = new Node2();
            Node2 stateDOWN = new Node2();
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(p)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(p)));

            stateRIGHT.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateRIGHT.setAction(node.getAction() + "R");
            stateDOWN.setAction(node.getAction() + "D");

            stateRIGHT.setCost(0);
            stateDOWN.setCost(0);

            list.add(stateRIGHT);
            list.add(stateDOWN);
        }

        return list;
    }

    /**
     * Method for printing the list
     *
     * @param list
     */
    public void printListNodes(List<Node2> list) {
        System.out.println("Contents of List");
        Node2[] n = new Node2[list.size()];
        int y = 0;
        Iterator it = list.iterator();

        while (it.hasNext()) {
            n[y] = (Node2) it.next();
            y++;

        }
        for (int i = 0; i < n.length; i++) {
            //copy list into array
            System.out.println(Arrays.toString(n[i].getPuzzle()) + " Depth :" + n[i].getDepth());
        }
    }

    @Override
    public void run() {
        solve();
    }

}
