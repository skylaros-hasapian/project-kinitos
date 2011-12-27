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
        {false, true, false, false, false, true, false, false, false},
        {false, true, true, false, false, true, false, false, false},
        {false, true, false, true, true, false, true, false, false},
        {false, false, false, false, false, true, false, true, false},
        {false, false, false, false, false, false, true, false, true},
        {false, false, false, false, false, false, false, true, false}
    }; //αν το graph[i][i]==true τότε το κόμβος με id=i είναι η ρίζα του δέντρου
    public static int degree[] = {0, 1, 1, 2, 2, 2, 3, 4, 5};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int i, j;                       // Indexes
        int n = graph.length;           // Αριθμός κόμβων

        // Δημιουργία πίνακα κόμβων
        Node[] komvos = new Node[n];

        // Δημιουργία κόμβων
        for (i = 0; i < n; i++) {
            komvos[i] = new Node(i, degree[i]);
        }

        // Δημιουργία λίστας αδερφών για κάθε κόμβο
        for (i = 0; i < n; i++) {
            for (int l = 0; l < n; l++) {
                if (komvos[i].degree == komvos[l].degree && graph[i][l] && i != l) {
                    //System.out.println(i+1+","+(l+1));
                    komvos[i].sibList.add(l);
                }
            }
            komvos[i].it = komvos[i].sibList.iterator();  // Iterator για τη λίστα των αδερφών για κάθε κόμβο
        }


        for (i = 0; (i < 9 && !graph[i][i]); i++);
        komvos[i].color = "Black";
        CNM(komvos[i].color, 7, n, komvos);
        for (i = 0; i < n; i++) {
            System.out.println(komvos[i].outstanding);
        }
    }

    static void CNM(String color, int i, int n, Node komvos[]) {
        int max = 0;
        if (color.equals("Black")) {
            for (int k = 0; k < n; k++) {
                if (komvos[k].degree == komvos[i].degree + 1 && graph[i][k]) {     // αν ο κόμβός είναι παιδί του κόμβου που έστειλε CNM
                    if (!komvos[k].it.hasNext()) {                  // αν ο κόμβος δεν έχει αδερφό
                        komvos[k].outstanding = true;
                    } else {                                      // αλλιώς αν έχει αδερφό
                        max = k;
                        while (komvos[k].it.hasNext()) {            // έλεγξε τα αδέρφια
                            int value = (Integer) komvos[k].it.next();
                            //System.out.println("val=" + value);
                            if (graph[i][value]) {                    // αν είναι παιδιά του κόμβου
                                max = (max > value) ? max : value;
                            }
                        }
                        komvos[max].outstanding = true;
                    }
                }
            }
            
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
