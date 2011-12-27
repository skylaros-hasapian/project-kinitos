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

    static boolean graph[][] = {
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
    static int degree[] = {0, 1, 1, 2, 2, 2, 3, 4, 5};
    static int n = 9;           // Αριθμός κόμβων

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int i;                       // Indexes


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
        CNM("Black", i, komvos);
        for (i = 0; i < n; i++) {
            System.out.println(komvos[i].outstanding);
        }


        for (i = 0; i < n; i++) {
//            if (komvos[i].color.equals("Black") || komvos[i].color.equals("Blue")) {
                System.out.println(komvos[i].color);
//            }
        }
    }

    static void CNM(String color, int i, Node komvos[]) {
        int max = 0;
        int k;
        boolean black;
        boolean blue;

        for (k = 0; k < n; k++) {
            // Αρχικά κάνω outstanding τους εκάστοτε γείτονες
            if (!komvos[k].outstanding && graph[i][k]) {        // αν ο κόμβός είναι παιδί του κόμβου που έστειλε CNM
                if (!komvos[k].it.hasNext()) {                  // αν ο κόμβος δεν έχει αδερφό
                    komvos[k].outstanding = true;               // κάνε τον outstanding
                } else {                                        // αλλιώς αν έχει αδερφό
                    max = k;
                    while (komvos[k].it.hasNext()) {            // έλεγξε τα αδέρφια
                        int value = (Integer) komvos[k].it.next();
                        //System.out.println("val=" + value);
                        if (graph[i][value]) {
                            max = (max > value) ? max : value;  // βρες ποιος έχει τη μεγαλύτερη ενέργεια (id)
                        }
                    }
                }
                komvos[max].outstanding = true;                 // κάνε τον outstanding
            }
        }

        // αν το CNM είναι Black
        if (color.equals("Black")) {
            for (k = 0; k < n; k++) {
                if (komvos[k].outstanding && komvos[k].color == null) {
                    komvos[k].color = "White";
                    CNM("White", k, komvos);
                }
            }
        } else if (color.equals("White")) {
            black = false;
            blue = false;
            for (k = 0; k < n; k++) {
                if (komvos[k].color != null) {
                    if (graph[i][k] && komvos[k].color.equals("Black")) {
                        black = true;
                        break;
                    }
                }
            }
            if (!black) {
                komvos[i].color = "Black";
                for (k = 0; k < n; k++) {
                    // αν έχει blue πατέρα
                    if (graph[i][k] && komvos[k].degree == komvos[i].degree - 1 && komvos[k].color.equals("Blue")) {
                        blue = true;
                        break;
                    }
                }
                Connect(k, komvos);
                CNM("Black", i, komvos);
            }
        } // αν το CNM είναι BLUE μην κάνεις τίποτα
        //        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static void Connect(int k, Node komvos[]) {
        try {
            komvos[k].color = "Blue";
            CNM("Blue", k, komvos);
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e){
            
        };
        
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
