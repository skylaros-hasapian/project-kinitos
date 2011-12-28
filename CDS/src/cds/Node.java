/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cds;

import java.util.List;
//import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author georgehatzinotas
 */
public class Node {

    int id;
    int degree;
    String color;
    boolean outstanding;
    boolean BCNM;
    List<Integer> siblings = new CopyOnWriteArrayList<Integer>();
    List<Integer> parents = new CopyOnWriteArrayList<Integer>();
    List<Integer> neighbors = new CopyOnWriteArrayList<Integer>();
    List<Integer> children = new CopyOnWriteArrayList<Integer>();

    public Node(int id, int degree) {
        this.id = id;
        this.degree = degree;
        color = null;
        outstanding = false;
        BCNM = false;
    }
}
