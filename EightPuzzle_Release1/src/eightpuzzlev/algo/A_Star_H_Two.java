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
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 *
 * @author Chetan
 */
public class A_Star_H_Two {

    int[] puzzle;public int space;
    List<Node2> listNode = new ArrayList<>();
    Stack<Node2> stack;
    HelperMethods helper;
    int count = 0;
    private HashMap<String, Integer> map;
    int fnMax;
    public String solutionSteps;
    int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
    HeuristicTwo nodeCostCalculate;

    long startTime;
    long stopTime;
    public String time;

    public A_Star_H_Two(int[] puzzle) {
        this.puzzle = puzzle;
        stack = new Stack<>();
        helper = new HelperMethods();
        map = new HashMap<>();
        nodeCostCalculate = new HeuristicTwo();

    }

    public void solve() {
        startTime = System.currentTimeMillis();
        Node2 node = new Node2(puzzle, 0, "", 0);
        Node2 currentNode;
        fnMax = nodeCostCalculate.calcManhattan(node.getPuzzle());
        System.out.println("Cost of the root f(n)=g(n)+h(n)  => " + fnMax);
        listNode.add(node);
        addToMapAndStack(listNode, count);
        System.out.println("************************************************* First Step*********************");
        currentNode = stack.pop();
        System.out.println("Stack Size  after popping off " + stack.size());
        System.out.println("Current Node on stack" + currentNode);
        listNode = (GenerateStateNodes(currentNode));

        printListNodes(listNode);
        addToMapAndStack(listNode, count);
        printStack(stack);
        while (stack.size() > 0) {
            System.out.println("*************************************************");
            currentNode = stack.pop();
            System.out.println("Stack Size  after popping off " + stack.size());
            System.out.println("Current Node on stack" + currentNode);
            listNode = (GenerateStateNodes(currentNode));
            printListNodes(listNode);
            addToMapAndStack(listNode, count);
            printStack(stack);
        }

    }

    /**
     * Method to add List of Nodes to map & stack
     *
     * @param listNode
     * @param count
     */
    private void addToMapAndStack(List<Node2> listNode, int count) {
        ListIterator<Node2> it = listNode.listIterator();

        int min = lowestCostInNodeList(listNode);
        System.out.println("Min: " + min);

        while (it.hasNext()) {
            Node2 node = it.next();

            if (!map.containsKey(Arrays.toString(node.getPuzzle()))) {

                if (node.getCost() <= min) {
                    map.put(Arrays.toString(node.getPuzzle()), ++count);

                    stack.push(node);
                    //heuristicMax = heuristic;
                    System.out.println("Added to stack " + node);
//                    solutionSteps.append(node.getAction());
                }

                //  stack.push(node);
                if (Arrays.equals(node.getPuzzle(), goalSet)) {
                    System.out.println("&&&&&&&&&&&&&&&****************Solution Found****************");
                    System.out.println("At Depth " + node.getDepth());
                    solutionSteps = node.getAction();
                    System.out.println("Solution Actions " + solutionSteps);
                    System.out.println("Cost " + node.getCost());
                    space=stack.size();
                    System.out.println("Stack Size : " + space);
                    stack.clear();

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
        int[] puzzle = node.getPuzzle();
        int depth = node.getDepth();
        int cost = node.getCost();

        PuzzleMoves movePuzzle = new PuzzleMoves();
        int zeroPOS = movePuzzle.findZero(puzzle);

        if (zeroPOS == 8) {
            System.out.println("Zero at position 8 \nTile can depthe moved UP & LEFT");

            //add the state to the map
            Node2 stateLEFT = new Node2();
            Node2 stateUP = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
            stateLEFT.setDepth(depth + 1);

            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
            stateUP.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateUP.setAction(node.getAction() + "U");

            stateLEFT.setCost(nodeCostCalculate.calcManhattan(stateLEFT.getPuzzle()) + 1);
            stateUP.setCost(nodeCostCalculate.calcManhattan(stateUP.getPuzzle()) + 1);

            list.add(stateLEFT);
            list.add(stateUP);

        } else if (zeroPOS == 7) {
            System.out.println("Tile Can be Moved UP, LEFT & RIGHT");

            Node2 stateLEFT = new Node2();
            Node2 stateRIGHT = new Node2();
            Node2 stateUP = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
            stateLEFT.setDepth(depth + 1);
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
            stateRIGHT.setDepth(depth + 1);
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
            stateUP.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");

            stateLEFT.setCost(nodeCostCalculate.calcManhattan(stateLEFT.getPuzzle()) + 1);
            stateRIGHT.setCost(nodeCostCalculate.calcManhattan(stateRIGHT.getPuzzle()) + 1);
            stateUP.setCost(nodeCostCalculate.calcManhattan(stateUP.getPuzzle()) + 1);

            list.add(stateLEFT);
            list.add(stateRIGHT);
            list.add(stateUP);

        } else if (zeroPOS == 6) {
            System.out.println("Tile Can be Moved UP & RIGHT");
            //need to generate two states UP & RIGHT
            Node2 stateRIGHT = new Node2();
            Node2 stateUP = new Node2();

            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
            stateRIGHT.setDepth(depth + 1);
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
            stateUP.setDepth(depth + 1);

            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");

            stateRIGHT.setCost(nodeCostCalculate.calcManhattan(stateRIGHT.getPuzzle()) + 1);
            stateUP.setCost(nodeCostCalculate.calcManhattan(stateUP.getPuzzle()) + 1);

            list.add(stateRIGHT);
            list.add(stateUP);
        } else if (zeroPOS == 5) {
            System.out.println("Tile can be moved UP, DOWN & LEFT");
            //generate three states UP, DOWN, LEFT
            Node2 stateLEFT = new Node2();
            Node2 stateUP = new Node2();
            Node2 stateDOWN = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

            stateLEFT.setDepth(depth + 1);
            stateUP.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateUP.setAction(node.getAction() + "U");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(nodeCostCalculate.calcManhattan(stateLEFT.getPuzzle()) + 1);
            stateUP.setCost(nodeCostCalculate.calcManhattan(stateUP.getPuzzle()) + 1);
            stateDOWN.setCost(nodeCostCalculate.calcManhattan(stateDOWN.getPuzzle()) + 1);
            list.add(stateLEFT);
            list.add(stateUP);
            list.add(stateDOWN);

        } else if (zeroPOS == 4) {
            System.out.println("Tile can be moved UP, DOWN, LEFT & RIGHT");
            Node2 stateLEFT = new Node2();
            Node2 stateRIGHT = new Node2();
            Node2 stateUP = new Node2();
            Node2 stateDOWN = new Node2();

            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

            stateLEFT.setDepth(depth + 1);
            stateRIGHT.setDepth(depth + 1);
            stateUP.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(nodeCostCalculate.calcManhattan(stateLEFT.getPuzzle()) + 1);
            stateRIGHT.setCost(nodeCostCalculate.calcManhattan(stateRIGHT.getPuzzle()) + 1);
            stateUP.setCost(nodeCostCalculate.calcManhattan(stateUP.getPuzzle()) + 1);
            stateDOWN.setCost(nodeCostCalculate.calcManhattan(stateDOWN.getPuzzle()) + 1);

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
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
            stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

            stateRIGHT.setDepth(depth + 1);
            stateUP.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateRIGHT.setAction(node.getAction() + "R");
            stateUP.setAction(node.getAction() + "U");
            stateDOWN.setAction(node.getAction() + "D");

            stateRIGHT.setCost(nodeCostCalculate.calcManhattan(stateRIGHT.getPuzzle()) + 1);
            stateUP.setCost(nodeCostCalculate.calcManhattan(stateUP.getPuzzle()) + 1);
            stateDOWN.setCost(nodeCostCalculate.calcManhattan(stateDOWN.getPuzzle()) + 1);
            list.add(stateRIGHT);
            list.add(stateUP);
            list.add(stateDOWN);

        } else if (zeroPOS == 2) {
            System.out.println("Tile can be moved  DOWN & LEFT");
            //generate 2 states 
            Node2 stateLEFT = new Node2();
            Node2 stateDOWN = new Node2();
            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

            stateLEFT.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(cost + 1);
            stateDOWN.setCost(cost + 1);

            list.add(stateLEFT);
            list.add(stateDOWN);

        } else if (zeroPOS == 1) {
            System.out.println("Tile can be moved LEFT, RIGHT & DOWN");
            //generate 3 states LEFT, RIGHT & DOWN
            Node2 stateLEFT = new Node2();
            Node2 stateRIGHT = new Node2();
            Node2 stateDOWN = new Node2();
            stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

            stateLEFT.setDepth(depth + 1);
            stateRIGHT.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateLEFT.setAction(node.getAction() + "L");
            stateRIGHT.setAction(node.getAction() + "R");
            stateDOWN.setAction(node.getAction() + "D");

            stateLEFT.setCost(nodeCostCalculate.calcManhattan(stateLEFT.getPuzzle()) + 1);
            stateRIGHT.setCost(nodeCostCalculate.calcManhattan(stateRIGHT.getPuzzle()) + 1);
            stateDOWN.setCost(nodeCostCalculate.calcManhattan(stateDOWN.getPuzzle()) + 1);

            list.add(stateLEFT);
            list.add(stateRIGHT);
            list.add(stateDOWN);

        } else if (zeroPOS == 0) {
            System.out.println("Tile can be moved RIGHT & DOWN");
            //generate 4 states UP, DOWN, LEFT & RIGHT
            Node2 stateRIGHT = new Node2();
            Node2 stateDOWN = new Node2();
            stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
            stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

            stateRIGHT.setDepth(depth + 1);
            stateDOWN.setDepth(depth + 1);

            stateRIGHT.setAction(node.getAction() + "R");
            stateDOWN.setAction(node.getAction() + "D");

            stateRIGHT.setCost(nodeCostCalculate.calcManhattan(stateRIGHT.getPuzzle()) + 1);
            stateDOWN.setCost(nodeCostCalculate.calcManhattan(stateDOWN.getPuzzle()) + 1);

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
            System.out.println(Arrays.toString(n[i].getPuzzle()) + " Depth :" + n[i].getDepth() + " \t Cost " + n[i].getCost());
        }
    }

    /**
     * Method to print the stack
     *
     * @param stack
     */
    public void printStack(Stack<Node2> stack) {
        System.out.println("Contents of Stack ");
        Node2[] n = new Node2[stack.size()];
        int y = 0;
        Iterator it = stack.iterator();
        while (it.hasNext()) {
            n[y] = (Node2) it.next();
            y++;
        }

        for (int i = 0; i < n.length; i++) {
            //copy list into array
            System.out.println(Arrays.toString(n[i].getPuzzle()) + " Depth :" + n[i].getDepth() + "\t Cost " + (n[i].getCost()));
        }
    }

    /**
     * Method for finding the lowest cost in list
     *
     * @param list
     */
    public int lowestCostInNodeList(List<Node2> list) {

        Node2 node;
        int min = 1000000;
        ListIterator<Node2> it = list.listIterator();
        while (it.hasNext()) {
            node = it.next();
            if (node.getCost() < min) {
                min = node.getCost();
            }

        }
//        System.out.println("Min " + min);
        return min;

    }
}
