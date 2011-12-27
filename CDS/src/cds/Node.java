/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cds;

import java.util.List;
//import java.util.ArrayList;
import java.util.Iterator;
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
    List<Integer> sibList = new CopyOnWriteArrayList<Integer>();
    Iterator it;

    public Node(int id, int degree) {
        this.id = id;
        this.degree = degree;
        color = null;
        outstanding = false;
    }
}
