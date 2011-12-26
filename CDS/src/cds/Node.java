/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cds;

/**
 *
 * @author georgehatzinotas
 */
public class Node {

    int id;
    int degree;
    String color;
    boolean outstanding;

    public Node(int id, int degree) {
        this.id = id;
        this.degree = degree;
        color = null;
        outstanding = false;

    }
}
