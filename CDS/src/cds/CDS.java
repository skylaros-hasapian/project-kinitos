/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cds;

/**
 *
 * @author georgehatzinotas
 */
public class CDS {

    public static boolean graph[][] = {
        {true, true, true, false, false, false, false, false, false},
        {true, false, false, true, true, true, false, false, false},
        {true, false, false, false, true, false, false, false, false},
        {false, true, false, false, true, true, false, false, false},
        {false, true, false, true, false, true, false, false, false},
        {false, true, false, true, true, false, false, false, false},
        {false, false, false, false, false, true, false, true, false},
        {false, false, false, false, false, false, true, false, true},
        {false, false, false, false, false, false, false, false, false}
    }; //αν το graph[i][i]==true τότε το κόμβος με id=i είναι η ρίζα του δέντρου
    public static int degree[] = {0, 1, 1, 2, 2, 2, 3, 4, 5};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int i, j;                   // Indexes
        int n = graph.length;       // Αριθμός κόμβων


//        for (i = 0; i < graph.length - 1; i++) {
//            for (j = 0; j < graph[i].length - 1; j++) {
//                System.out.print(graph[i][j]);
//                System.out.print('\t');
//            }
//            System.out.println();
//        }

        Node[] komvos = new Node[n];

        for (i = 0; i < n; i++) {
            komvos[i] = new Node(i, degree[i]);
        }

        System.out.println("ID\tDegree");

        for (i = 0; i < n; i++) {
            System.out.println(komvos[i].id + 1 + "\t" + komvos[i].degree);
        }

        for (i = 0; (i < 9 && !graph[i][i]); i++);
        komvos[i].color = "Black";
        CNM(komvos[i].color, komvos[i].degree, n, komvos);

        System.out.println(komvos[1].color);

    }

    static void CNM(String color, int j, int n, Node komvos[]) {
        if (color.equals("Black")) {
            for (int k = 0; k < n; k++) {
                if (komvos[k].degree == komvos[j].degree + 1) {
                    komvos[k].color = "White";
                }

            }
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
