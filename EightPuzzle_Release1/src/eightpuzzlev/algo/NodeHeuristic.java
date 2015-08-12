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
public class NodeHeuristic extends Node
{
    Node node;
   int heuristic;

    public NodeHeuristic(Node node, int heuristic) {
        this.node = node;
        this.heuristic = heuristic;
    }
   
   
    
}
