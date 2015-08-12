/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoOld;

import eightpuzzlev.algo.HelperMethods;
import eightpuzzlev.algo.Node;
import eightpuzzlev.algo.PuzzleMoves;
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
public class IterativeDeepeningSearch {

    public int space;
    PuzzleMoves movePuzzle;
    HelperMethods helper;
    int depthLimit = 0;
    int[] puzzle;
    int[] goalSet = {1, 2, 3, 8, 0, 4, 7, 6, 5};
    int[] currentPuzzle;
    private HashMap<String, Integer> map;
    private Queue<String> queue;
    private Stack<Node> stack;
    Node n = new Node();
    HashMap<String, String> m;
    public String solutionSteps;
    boolean flag = true;
    long startTime;
    long stopTime;
    public String time;

    public IterativeDeepeningSearch(int[] puzzle) {
        movePuzzle = new PuzzleMoves();
        helper = new HelperMethods();
        this.puzzle = puzzle;
        currentPuzzle = puzzle;
        map = new HashMap<>();

    }

    public void solve() {
        startTime = System.currentTimeMillis();

        while (flag) {
            System.out.println("Depth Limit Increased to " + depthLimit);
            System.out.println("Recreacting Map & Stack");
            
            queue = new LinkedList<>();
            stack = new Stack<>();
            m = new HashMap<>();
            solutionSteps = "";
            List<Node> listNode = new ArrayList<>();
            int count = 0;
            int currentDepth = 0;
            Node parent = new Node(puzzle, count, "");
            Node currentNode = parent;
            m.put(Arrays.toString(currentNode.getPuzzle()), "");
            listNode.add(parent);

            while (currentNode.getDepth() < depthLimit && flag) {
                System.out.println("Depth : " + depthLimit);
                addToMapAndStack(listNode, count);
                currentNode = stack.pop();
                System.out.println("Current Node " + Arrays.toString(currentNode.getPuzzle()));
                listNode = GenerateStateNodes(currentNode);
                printListNodes(listNode);
                printStack(stack);
                System.out.println("Map : " + map);

            }
            listNode = null;
            while (!stack.empty()) {

                currentNode = stack.pop();
                System.out.println("IDS ... Current Element for Expension " + Arrays.toString(currentNode.getPuzzle()));
                if (currentNode.getDepth() < depthLimit) {
                    System.out.println("Expended");
                    listNode = GenerateStateNodes(currentNode);
                    addToMapAndStack(listNode, count);
                }

            }

            depthLimit++;
            System.out.println("*****************************************************");

        }

    }

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

        for (int i = 0; i < n.length; i++) {
            //copy list into array
            System.out.println(Arrays.toString(n[i].getPuzzle()) + " Depth :" + n[i].getDepth());
        }
    }

    /**
     * Method for printing the list
     *
     * @param list
     */
    public void printListNodes(List<Node> list) {
        System.out.println("Contents of List");
        Node[] n = new Node[list.size()];
        int y = 0;
        Iterator it = list.iterator();

        while (it.hasNext()) {
            n[y] = (Node) it.next();
            y++;
        }

        for (int i = 0; i < n.length; i++) {
            //copy list into array
            System.out.println(Arrays.toString(n[i].getPuzzle()) + " Depth :" + n[i].getDepth());
        }

    }

    /**
     * Method to generate state Nodes
     *
     * @param puzzle
     * @param b
     * @return List<Node>
     */
    public List GenerateStateNodes(Node node) {
        //int[] puzzle = helper.convertStringToIntArray(p);
        List<Node> list = new ArrayList<>();
        Node n = node;
        int[] puzzle = n.getPuzzle();
        int b = node.getDepth();

        if (b < depthLimit) {
            int zeroPOS = movePuzzle.findZero(puzzle);

            if (zeroPOS == 8) {
                System.out.println("Zero at position 8 \nTile can be moved UP & LEFT");

                //add the state to the map
                Node stateLEFT = new Node();
                Node stateUP = new Node();

                stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
                stateLEFT.setDepth(b + 1);

                stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
                stateUP.setDepth(b + 1);

                stateLEFT.setAction(node.getAction() + "L");
                stateUP.setAction(node.getAction() + "U");

                list.add(stateLEFT);
                list.add(stateUP);

            } else if (zeroPOS == 7) {
                System.out.println("Tile Can be Moved UP, LEFT & RIGHT");

                Node stateLEFT = new Node();
                Node stateRIGHT = new Node();
                Node stateUP = new Node();

                stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
                stateLEFT.setDepth(b + 1);
                stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
                stateRIGHT.setDepth(b + 1);
                stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
                stateUP.setDepth(b + 1);

                stateLEFT.setAction(node.getAction() + "L");
                stateRIGHT.setAction(node.getAction() + "R");
                stateUP.setAction(node.getAction() + "U");

                list.add(stateLEFT);
                list.add(stateRIGHT);
                list.add(stateUP);

            } else if (zeroPOS == 6) {
                System.out.println("Tile Can be Moved UP & RIGHT");
                //need to generate two states UP & RIGHT
                Node stateRIGHT = new Node();
                Node stateUP = new Node();

                stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
                stateRIGHT.setDepth(b + 1);
                stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
                stateUP.setDepth(b + 1);

                stateRIGHT.setAction(node.getAction() + "R");
                stateUP.setAction(node.getAction() + "U");

                list.add(stateRIGHT);
                list.add(stateUP);
            } else if (zeroPOS == 5) {
                System.out.println("Tile can be moved UP, DOWN & LEFT");
                //generate three states UP, DOWN, LEFT
                Node stateLEFT = new Node();
                Node stateUP = new Node();
                Node stateDOWN = new Node();

                stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
                stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
                stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

                stateLEFT.setDepth(b + 1);
                stateUP.setDepth(b + 1);
                stateDOWN.setDepth(b + 1);

                stateLEFT.setAction(node.getAction() + "L");
                stateUP.setAction(node.getAction() + "U");
                stateDOWN.setAction(node.getAction() + "D");

                list.add(stateLEFT);
                list.add(stateUP);
                list.add(stateDOWN);

            } else if (zeroPOS == 4) {
                System.out.println("Tile can be moved UP, DOWN, LEFT & RIGHT");
                Node stateLEFT = new Node();
                Node stateRIGHT = new Node();
                Node stateUP = new Node();
                Node stateDOWN = new Node();

                stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
                stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
                stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
                stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

                stateLEFT.setDepth(b + 1);
                stateRIGHT.setDepth(b + 1);
                stateUP.setDepth(b + 1);
                stateDOWN.setDepth(b + 1);

                stateLEFT.setAction(node.getAction() + "L");
                stateRIGHT.setAction(node.getAction() + "R");
                stateUP.setAction(node.getAction() + "U");
                stateDOWN.setAction(node.getAction() + "D");

                list.add(stateLEFT);
                list.add(stateRIGHT);
                list.add(stateUP);
                list.add(stateDOWN);

            } else if (zeroPOS == 3) {
                System.out.println("Tile can be moved UP, DOWN & RIGHT");
                //generate 3 states UP, DOWN, & RIGHT
                Node stateRIGHT = new Node();
                Node stateUP = new Node();
                Node stateDOWN = new Node();
                stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
                stateUP.setPuzzle(movePuzzle.moveUP(helper.DeepCopyArray(puzzle)));
                stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

                stateRIGHT.setDepth(b + 1);
                stateUP.setDepth(b + 1);
                stateDOWN.setDepth(b + 1);

                stateRIGHT.setAction(node.getAction() + "R");
                stateUP.setAction(node.getAction() + "U");
                stateDOWN.setAction(node.getAction() + "D");

                list.add(stateRIGHT);
                list.add(stateUP);
                list.add(stateDOWN);

            } else if (zeroPOS == 2) {
                System.out.println("Tile can be moved  DOWN & LEFT");
                //generate 2 states 
                Node stateLEFT = new Node();
                Node stateDOWN = new Node();
                stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
                stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

                stateLEFT.setDepth(b + 1);
                stateDOWN.setDepth(b + 1);

                stateLEFT.setAction(node.getAction() + "L");
                stateDOWN.setAction(node.getAction() + "D");

                list.add(stateLEFT);
                list.add(stateDOWN);

            } else if (zeroPOS == 1) {
                System.out.println("Tile can be moved LEFT, RIGHT & DOWN");
                //generate 3 states LEFT, RIGHT & DOWN
                Node stateLEFT = new Node();
                Node stateRIGHT = new Node();
                Node stateDOWN = new Node();
                stateLEFT.setPuzzle(movePuzzle.moveLEFT(helper.DeepCopyArray(puzzle)));
                stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
                stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

                stateLEFT.setDepth(b + 1);
                stateRIGHT.setDepth(b + 1);
                stateDOWN.setDepth(b + 1);

                stateLEFT.setAction(node.getAction() + "L");
                stateRIGHT.setAction(node.getAction() + "R");
                stateDOWN.setAction(node.getAction() + "D");

                list.add(stateLEFT);
                list.add(stateRIGHT);
                list.add(stateDOWN);

            } else if (zeroPOS == 0) {
                System.out.println("Tile can be moved RIGHT & DOWN");
                //generate 4 states UP, DOWN, LEFT & RIGHT
                Node stateRIGHT = new Node();
                Node stateDOWN = new Node();
                stateRIGHT.setPuzzle(movePuzzle.moveRIGHT(helper.DeepCopyArray(puzzle)));
                stateDOWN.setPuzzle(movePuzzle.moveDOWN(helper.DeepCopyArray(puzzle)));

                stateRIGHT.setDepth(b + 1);
                stateDOWN.setDepth(b + 1);

                stateRIGHT.setAction(node.getAction() + "R");
                stateDOWN.setAction(node.getAction() + "D");

                list.add(stateRIGHT);
                list.add(stateDOWN);
            }
        }
        return list;
    }

    public void PlaySolution() {
        helper.printPuzzle(puzzle);
        for (int i = 0; i < solutionSteps.length(); i++) {
            if (solutionSteps.charAt(i) == 'L') {
                movePuzzle.moveLEFT(puzzle);
                helper.printPuzzle(puzzle);
            } else if (solutionSteps.charAt(i) == 'R') {
                movePuzzle.moveRIGHT(puzzle);
                helper.printPuzzle(puzzle);
            }
            if (solutionSteps.charAt(i) == 'U') {
                movePuzzle.moveUP(puzzle);
                helper.printPuzzle(puzzle);
            } else if (solutionSteps.charAt(i) == 'D') {
                movePuzzle.moveDOWN(puzzle);
                helper.printPuzzle(puzzle);
            }
        }
    }

    /**
     * Method to add contents to MAP after varifying that they dont exists in
     * MAP
     *
     * @param p
     * @param count
     */
    public void addToMap(int[] p, int count) {
        String puzzle = Arrays.toString(p);
        if (!map.containsKey(puzzle)) {
            map.put(puzzle, ++count);
            //System.out.println("Added to Map : " + puzzle);
        }
    }

    /**
     * Method to add data to stack and MAP and avoid duplicate
     *
     * @param list
     * @param count
     */
    public void addToMapAndStack(List<Node> list, int count) {
        ListIterator<Node> it = list.listIterator();
        Node node;

        while (it.hasNext() && flag) {
            node = it.next();
            if (!map.containsKey(node.getPuzzle())) {

                map.put(Arrays.toString(node.getPuzzle()), ++count);

                stack.push(node);
                if (Arrays.equals(node.getPuzzle(), goalSet)) {
                    solutionSteps = node.getAction();
                    System.out.println("&&&&&&&&&&&&&&&****************Solution Found****************");
                    System.out.println("At Depth " + node.getDepth());
                    System.out.println("Solution Actions " + solutionSteps);
                    space = stack.size();
                    System.out.println("Stack Size : " + space);
                    stack.clear();
                    stopTime = System.currentTimeMillis();
                    SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
                    time = Long.toString(stopTime - startTime);
                    System.out.println("Total Time HH:MM:SS : " + df.format(new Date(stopTime - startTime)));
                    System.out.println("Time in Milliseconds : " + time);

                    flag = false;
//                   PlaySolution();
//                     System.exit(0);

                }
            }
        }
    }

}
