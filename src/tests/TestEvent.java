import io.classes.Robot;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.zip.DataFormatException;

import io.classes.Carte;
import io.classes.Case;
import io.classes.Direction;
import io.classes.Typerobot;
import io.evenements.*;
import io.simulation.*;
 



public class TestEvent {
    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestAffichageSituationInitiale <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation d = new DonneesSimulation(args[0]);
            Simulateur affichage = new Simulateur(d);

            d.setSimulateur(affichage);

            affichage.dessiner(d);


         // d.ajouterEvenement(new DeplacementRobot((long) 0, d.getRobots().get(Typerobot.DRONE).getFirst(), DirectionCarte.SUD, d.getCarte(),3L,d));
         // d.ajouterEvenement(new DeplacementRobot((long) 4, d.getRobots().get(Typerobot.DRONE).getFirst(), DirectionCarte.SUD, d.getCarte(),3L,d));
          //d.ajouterEvenement(new DeplacementRobot((long) 8, d.getRobots().get(Type robot.DRONE).getFirst(), DirectionCarte.SUD, d.getCarte(),3L,d)); 
             Robot robot = d.getRobots().get(Typerobot.DRONE).getFirst();
             System.out.println("Position initiale du robot : " + robot.getPosition());
            Case destination = d.getCarte().getCase(9, 0); // Exemple de case cible
             Dijkstra dijkstra = new Dijkstra(d);
             System.out.println("Position initiale du robot : " + robot.getPosition());

            List<Case> chemin = dijkstra.trouverChemin(robot, destination);
             PriorityQueue<Evenement> evenements = new PriorityQueue<>();       
                  System.out.println("Position initiale du robot : " + robot.getPosition());


            dijkstra.genererEvenements(chemin, robot, 0L, evenements);
            System.out.println("Événements générés pour le déplacement :");
            System.out.println("Position initiale du robot : " + robot.getPosition());

            while (!evenements.isEmpty()) {
                d.ajouterEvenement(evenements.poll());

            }
            System.out.println("\nExécution des événements :");
            System.out.println("Position initiale du robot : " + robot.getPosition());
//Position initiale du robot : io.classes.Case@418eed8f
//Position initiale du robot : io.classes.Case@418f2581

           /*  while (d.getEvenements().size() > 0) {
                Evenement e = d.getEvenements().poll(); // récupère l'événement avec la date la plus proche
                e.execute(); // exécute chaque événement dans l'ordre
                System.out.println(e); // affichage de l'événement pour le suivi
            } */

          
        /*   for( int i=0; i<9; i++){
            long x = (long)d.getRobots().get(Typerobot.DRONE).getFirst().tempsdeplacement(d.getRobots().get(Typerobot.DRONE).getFirst().getPosition());
                d.ajouterEvenement(new DeplacementRobot((long) i*x, d.getRobots().get(Typerobot.DRONE).getFirst(), Direction.SUD, d.getCarte(),x,d));
            }   
           for(int i=9; i<13; i++){
              long x = (long)d.getRobots().get(Typerobot.DRONE).getFirst().tempsdeplacement(d.getRobots().get(Typerobot.DRONE).getFirst().getPosition());
                d.ajouterEvenement(new DeplacementRobot((long) i*x, d.getRobots().get(Typerobot.DRONE).getFirst(), Direction.OUEST, d.getCarte(),3L,d));
            } 
           System.out.println("====");
            d.ajouterEvenement(new VerseEau(130L, d.getRobot(Typerobot.DRONE, 0),d.getIncendies().get(4), d ,10000,30)); 
 */
            // d.ajouterEvenement(new DeplacementRobot((long) 1, d.getRobots().get(Typerobot.DRONE).getFirst(), DirectionCarte.SUD, d.getCarte()));

            // d.ajouterEvenement(new DeplacementRobot((long) 2, d.getRobots().get(Typerobot.DRONE).getFirst(), DirectionCarte.SUD, d.getCarte()));
           // d.ajouterEvenement(new DeplacementRobot((long) 0, d.getRobots().get(Typerobot.PATTES).get(2), DirectionCarte.SUD, d.getCarte(),(long)d.getRobots().get(Typerobot.PATTES).get(2).tempsdeplacement(d.getRobots().get(Typerobot.PATTES).get(2).getPosition()),d));

        /*      for(int i=0; i<21; i= i+2){
                d.ajouterEvenement(new DeplacementRobot((long) i, d.getRobots().get(Typerobot.PATTES).get(2), DirectionCarte.SUD, d.getCarte()));
                 d.ajouterEvenement(new DeplacementRobot((long) i+1, d.getRobots().get(Typerobot.PATTES).get(2), DirectionCarte.EST, d.getCarte()));
            } */
            // d.ajouterEvenement(new RemplirReservoir((long) 21, d.getRobots().get(Typerobot.PATTES).get(2))); 

/*             
           for(int i = 0 ; i< 9 ; i++){
                long x=(long)d.getRobots().get(Typerobot.DRONE).getFirst().tempsdeplacement(d.getRobots().get(Typerobot.DRONE).getFirst().getPosition());
                d.ajouterEvenement(new DeplacementRobot((long)i*x,d.getRobots().get(Typerobot.DRONE).getFirst(),DirectionCarte.OUEST,d.getCarte(),x,d));
            }
            for(int i = 9 ; i< 9+6 ; i++){
                long x=(long)d.getRobots().get(Typerobot.DRONE).getFirst().tempsdeplacement(d.getRobots().get(Typerobot.DRONE).getFirst().getPosition());
                d.ajouterEvenement(new DeplacementRobot((long)i*x,d.getRobots().get(Typerobot.DRONE).getFirst(),DirectionCarte.NORD,d.getCarte(),x,d));
            }
            //long x=(long)d.getRobots().get(Typerobot.DRONE).getFirst().getIntervention();
            d.ajouterEvenement(new VerseEau(151L,d.getRobot(Typerobot.DRONE, 0),d.getIncendies().get(4), d ,10000,30));
 

            long time = 182;
            for (int i = 0; i < 6; i++) { 
                long x = (long)d.getRobots().get(Typerobot.DRONE).getFirst().tempsdeplacement(d.getRobots().get(Typerobot.DRONE).getFirst().getPosition());
                d.ajouterEvenement(new DeplacementRobot(time, d.getRobots().get(Typerobot.DRONE).getFirst(), DirectionCarte.SUD, d.getCarte(), x, d));
                time += x;
            }
            
            for (int i = 0; i < 9; i++) { 
                long x = (long)d.getRobots().get(Typerobot.DRONE).getFirst().tempsdeplacement(d.getRobots().get(Typerobot.DRONE).getFirst().getPosition());
                d.ajouterEvenement(new DeplacementRobot(time, d.getRobots().get(Typerobot.DRONE).getFirst(), DirectionCarte.EST, d.getCarte(), x, d));
                time += x; 
            }  

        time = 333;
            long x = (long)d.getRobots().get(Typerobot.DRONE).getFirst().tempsdeplacement(d.getRobots().get(Typerobot.DRONE).getFirst().getPosition());
            d.ajouterEvenement(new DeplacementRobot(time, d.getRobots().get(Typerobot.DRONE).getFirst(), DirectionCarte.SUD, d.getCarte(), x, d));
         //   System.out.println("Je suis sur" + d.getRobots().get(Typerobot.DRONE).getFirst().getPosition().getNature());
            
           time=344;
          d.ajouterEvenement(new RemplirReservoir(time,d.getRobots().get(Typerobot.DRONE).getFirst(), d,d.getRobots().get(Typerobot.DRONE).getFirst().getTempRemplissage()) );

 */

       } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

        } 
    }
}
