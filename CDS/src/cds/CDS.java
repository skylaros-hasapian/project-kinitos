package cds;

import java.util.Iterator;

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
        boolean done = false;

        // Δημιουργία πίνακα κόμβων
        Node[] komvos = new Node[n];

        // Δημιουργία κόμβων
        for (i = 0; i < n; i++) {
            komvos[i] = new Node(i, degree[i]);
        }

        // Δημιουργία λίστών αδερφών, γονιών, παιδιών, γειτόνων για κάθε κόμβο
        for (i = 0; i < n; i++) {
            for (int l = 0; l < n; l++) {
                if ((graph[i][l] || graph[l][i]) && i != l) {
                    komvos[i].neighbors.add(l);
                    if (komvos[i].degree == komvos[l].degree) {
                        komvos[i].siblings.add(l);
                    } else if (komvos[i].degree == komvos[l].degree + 1) {
                        komvos[i].parents.add(l);
                    } else if (komvos[i].degree + 1 == komvos[l].degree) {
                        komvos[i].children.add(l);
                    }
                }
            }
        }

        for (i = 0; (i < 9 && !graph[i][i]); i++);      // βρες τη ρίζα i -->  graph[i][i]=true
        komvos[i].color = "Black";                      // κάνε τη ρίζα μάυρη
        komvos[i].outstanding = true;                   // και outstanding
        CNM("Black", i, komvos);                        // στείλε CNM

        while (!done) {
            for (i = 0; i < n; i++) {
                if (komvos[i].color == null) {
                    fire(i, komvos);
                }
            }
            done = true;
            for (i = 0; i < n; i++) {
                if (komvos[i].color == null) {
                    done = false;
                    break;
                }
            }
        }

        System.out.println("out\tcolor\tBCNM");
        for (i = 0; i < n; i++) {
            System.out.println(komvos[i].outstanding + "\t" + komvos[i].color + "\t" + komvos[i].BCNM);
        }

        System.out.println("The CDS is the following set:");
        for (i = 0; i < n; i++) {
            if (komvos[i].color.equals("Black") || komvos[i].color.equals("Blue")) {
                System.out.println(i);
            }
        }
    }

    static void CNM(String color, int i, Node komvos[]) {
        int max;
        int k;
        int bro;
        int neigh;
        // Αρχικά κάνω outstanding τους γείτονες
        Iterator nit = komvos[i].neighbors.iterator();
        while (nit.hasNext()) {                                 // για κάθε γείτονα του i
            neigh = (Integer) nit.next();
            Iterator sit = komvos[neigh].siblings.iterator();   // Iterator για τα αδέρφια του γείτονα
            if (!komvos[neigh].outstanding) {                   // αν δεν είναι outstanding
                if (!sit.hasNext()) {                           // αν δεν έχει αδέφια  
                    komvos[neigh].outstanding = true;           // γίνεται outstanding
//                    System.out.println("o κόμβος " + i + " έκανε outstanding τον κόμβο " + neigh);
                } else {
                    max = komvos[neigh].id;
                    while (sit.hasNext()) {                 // για κάθε αδερφό του γείτονα
                        bro = (Integer) sit.next();         // αδερφός του γείτονα
                        if (komvos[bro].color == null) {      // αν δε έχει αποφασίσει το χρώμα του
                            max = (max > komvos[bro].id) ? max : komvos[bro].id; // βρες ποιος έχει τη μεγαλύτερη ενέργεια (id)
                        }
                    }
                    if (!komvos[max].outstanding) {
                        komvos[max].outstanding = true;                 // και κάνε τον outstanding
//                        System.out.println("o κόμβος " + i + " έκανε outstanding τον κόμβο με max id=" + max);
                    }
                }

            }
        }



        if (color.equals("Black")) {                // αν το CNM είναι Black στείλε σε όλους
            nit = komvos[i].neighbors.iterator();   // ειδόποίησε τους γείτονες
            while (nit.hasNext()) {
                k = (Integer) nit.next();
                komvos[k].BCNM = true;
            }
        }


        // αν το CNM είναι Blue ή White μην κάνεις τίποτα
        //        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static void Connect(int k, Node komvos[]) {
        komvos[k].color = "Blue";
        CNM("Blue", k, komvos);
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static void fire(int i, Node komvos[]) {
        if (komvos[i].outstanding && komvos[i].color == null) {
            if (komvos[i].BCNM) {
                komvos[i].color = "White";
                CNM("White", i, komvos);
            } else {
                int max = 0;
                int k;
                boolean black;
                boolean blue;
                Iterator nit = komvos[i].neighbors.iterator();
                black = false;
                blue = false;
                nit = komvos[i].neighbors.iterator();
                while (nit.hasNext()) {                     // έλεγξε αν έχεις μαύρο πατέρα ή αδερφό
                    k = (Integer) nit.next();
                    if (komvos[k].color != null) {
                        if (komvos[k].color.equals("Black")) {
                            black = true;
                            break;
                        }
                    }
                }
                if (!black) {                           // αν δεν έχεις
                    komvos[i].color = "Black";          // βάψου μαύρος
                    CNM("Black", i, komvos);
                    Iterator pit = komvos[i].neighbors.iterator();
                    while (pit.hasNext()) {
                        int papa = (Integer) pit.next();
                        if (komvos[papa].color != null) {
                            if (komvos[papa].color.equals("Blue")) {
                                blue = true;
                                break;
                            }
                        }
                    }
                    if (!blue) {
                        max = 0;
                        pit = komvos[i].parents.iterator();
                        while (pit.hasNext()) {
                            int papa = (Integer) pit.next();
                            if (komvos[papa].color.equals("White")) {
                                max = (max > komvos[papa].id) ? max : komvos[papa].id;
                            }
                        }
                        Connect(max, komvos);
                    }
                }
            }

        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
