    import java.util.LinkedList;
    import java.util.HashSet;

    import io.interfacegraph.*;
    import io.classes.*;




public class TestAffichageRobots{
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        Carte c = new Carte(2,2,10000);
        Case c1 = new Case(0,0);
        c1.setNature(NatureTerrain.FORET);
        Case c2 = new Case(1,0);
        c2.setNature(NatureTerrain.EAU);
        Case c3 = new Case(0,1);
        c3.setNature(NatureTerrain.ROCHE);
        Case c4 = new Case(1,1);
        c4.setNature(NatureTerrain.HABITAT);
        c.setCase(0,0,c1);
        c.setCase(1,0,c2);
        c.setCase(0,1,c3);
        c.setCase(1,1,c4);

        LinkedList<Incendie> i = new LinkedList<Incendie>();
        Incendie i1 = new Incendie(0,0, 1000, c);
        i.add(i1);
        Incendie i2 = new Incendie(1,1, 20,c);
        i.add(i2);

        /* HashSet<Robot> r = new HashSet<Robot>();
        Robot r1 = new Drone(c,0,1);
        r.add(r1);
        Robot r2 = new Chenilles(c,0,0);
        r.add(r2);

        Affichage sim = new Affichage(c, i, r); 
        sim.dessiner();*/


    }
}



//ATTENTION : CERTAINES CLASSES DE ROBOTS NOMMEES AU PLURIEL


