 package io.evenements;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;

import io.classes.Case;
import io.classes.Direction;
import io.classes.Incendie;
import io.classes.NatureTerrain;
import io.classes.Robot;
import io.classes.Typerobot;
import io.simulation.DonneesSimulation;

/**
 * Chef pompier élementaire:
 * Associe un robot à chaque incendie conformément au sujet mais
 * Quelques améliorations ont été apportées par rapport au sujet :
 * 
 *      - Lorsqu'un robot a fini d'éteindre un incendie, est vide 
 *      ou a fini de se remplir, il passe en état inoccupé (false), 
 *      ce qui permet de déclencher le chef pompier lors du prochain
 *      appel à next(). Le robot d'attend pas la fin de la simulation 
 *      sans rien faire
 * 
 *      - Lorsque son réservoir est vide, le chef pompier le dirige 
 *      vers la case eau la plus proche
 * 
 *      -Inversement, lorsque le robot à fini de se remplir, le chef 
 *      le dirige vers le premier incendie de la LinkedList attribut 
 *      de donnees (donnees.getIncendies())
 * 
 *      - Lorsqu'un incendie est éteint mais que le réservoir du
 *      robot n'est pas vide, ce dernier est dirigé vers le premier 
 *      incendie de la LinkedList attribut de donnees (donnees.getIncendies())
 */



public class ChefPompierElementaire { 
   
   private DonneesSimulation donnees; 
   private long dateCourante;
   private Dijkstra dijkstra;
   private LinkedList<Case> eau;
   private LinkedList<Case> adjacentEau;

   public ChefPompierElementaire(DonneesSimulation d, GestionEvenements g, long dateCourante) {
      this.donnees = d;
      //this.gestionEvenements = g;
      this.dateCourante = dateCourante;
      this.dijkstra = new Dijkstra(d);
      this.eau = new LinkedList<Case>();
      this.adjacentEau = new LinkedList<Case>();
  }
  /**
   * Retourne l'attribut de type DonneesSimulation
   * @return
   */
  public DonneesSimulation getDonnees(){
    return this.donnees;
  }

  /**
   * Retourne la liste de robots pouvant traiter l'incendie en paramètre
   * @param incendie
   * @return
   */
  public LinkedList<Robot> peutTraiterIncendie(Incendie incendie) {
    LinkedList<Robot> robotsDisponibles = new LinkedList<>();
    
    for (LinkedList<Robot> listeRobots : donnees.getRobots().values()) {
        for (Robot robot : listeRobots) {
            Boolean etat = robot.getEtat();
            if (etat != null && !etat && robot.getReservoir() > 0 && robot.testdeplacementDijkstra(incendie.getPositionIncendie())) {
                robotsDisponibles.add(robot);
            }
        }
    }
    return robotsDisponibles;
}


/**
 * Retourne la liste d'évènements permettant au premier robot de la liste roboDisponibles
 * d'aller traiter l'incendie en paramètre
 * @param incendie
 * @return
 */
public PriorityQueue<Evenement> traiterIncendie(Incendie incendie) {
    PriorityQueue<Evenement> evenements = new PriorityQueue<>();
    LinkedList<Robot> robotsDisponibles = this.peutTraiterIncendie(incendie);
    if (!robotsDisponibles.isEmpty()) {
        Robot robot = robotsDisponibles.getFirst();

        System.out.println("Traiter incendie : Robot "+ robot + " sélectionné pour éteindre l'incendie "  + incendie);
        evenements.addAll(dijkstra.execute(robot, incendie.getPositionIncendie(), dateCourante, donnees));
        long dateFinale = dijkstra.getDateFinale();

        if (dateFinale >= 0) {
            long dateVerseEau  = dateFinale + dateCourante;
            Evenement verseEau = new VerseEau(dateVerseEau+1, robot, incendie, robot.getReservoir(), donnees);
            evenements.add(verseEau);
        }
        robot.setEtat(true);
    } 

    return evenements;
}


/**
 * Retourne tous les évènements permettant aux robots disponibles d'aller traiter les incendies de la simulation
 * @return
 */
public PriorityQueue<Evenement> traiterTousLesIncendies() {
    LinkedList<Incendie> incendies = this.donnees.getIncendies();
    PriorityQueue<Evenement> evenements = new PriorityQueue<Evenement>(new ComparateurEvenements());

    for (Incendie incendie : incendies) {
        evenements.addAll(traiterIncendie(incendie));
    }
    
    return evenements;
}


/**
 * Parcoure les cases de la carte pour stocker dans l'attribut eau les cases de nature EAU
 */
public void casesEau(){
    for(int i=0; i<donnees.getCarte().getNblignes(); i++){
        for(int j=0; j<donnees.getCarte().getNbcolonnes(); j++){
            Case c = donnees.getCarte().getCase(i, j);
            NatureTerrain nature = c.getNature();
            if (nature == NatureTerrain.EAU && !eau.contains(c)){
            eau.add(c);
            }
        }
    }
}


/**
 * Parcoure les cases de la carte pour stocker dans l'attribut adjacentEau
 * les cases dont le type n'est pas EAU adjacentes à une case EAU 
 */
public void casesAdjacentesEau(){
    for(Case c : eau){
        for (Direction dir : Direction.values()){
            Case adj = donnees.getCarte().getVoisin(c, dir) ;
            //System.out.println("CAE : case=" + adj + ", nature = " + adj.getNature());
            if(adj!=null && adj.getNature() != NatureTerrain.EAU && !adjacentEau.contains(adj)){
                adjacentEau.add(adj);
            }
        }

    }
}
 

/**
 * Retourn la liste des robots dont le reservoir est vide
 * @return
 */
public LinkedList<Robot> testReservoirs(){
    LinkedList<Robot> aRemplir = new LinkedList<Robot>();
    for (LinkedList<Robot> listeRobots : donnees.getRobots().values()) {
        for (Robot robot : listeRobots) {
            if(robot.reservoirIsEmpty() && !robot.getEtat()){
                aRemplir.add(robot);
            }
        }
    }
    return aRemplir;
}

/**
 * Gestion de l'évènement remplissage:
 * pour chaque robot avec un reservoir vide,
 * création des évènement pour l'amener à la case EAU la plus proche
 * puis pour qu'il puisse se remplir
 * @return la Priority Queue de tous les évènements
 */
public PriorityQueue<Evenement> remplissage(){
    LinkedList<Robot> aRemplir = testReservoirs();
    ArrayList<Case> chemin = new ArrayList<Case>();
    long dateFinale = 0;
    PriorityQueue<Evenement> events = new PriorityQueue<Evenement>();
    for(Robot robot : aRemplir){
        dateFinale = 0;
        //Un drone se remplit sur une case EAU
        if (robot.getTyperobot() == Typerobot.DRONE){
            chemin = dijkstra.casePlusProche(robot, eau);
        }
        //Les autres robots se remplissent à coté d'une case EAU
        else{
            chemin = dijkstra.casePlusProche(robot, adjacentEau);
        }
        
         
        dateFinale= dijkstra.getDateFinale();
        events.addAll(dijkstra.genererEvenements(chemin, robot, dateCourante));
        events.add(new RemplirReservoir(dateCourante+dateFinale+1, robot, donnees, robot.getTempRemplissage()));
        robot.setEtat(true);
    }
    
    return events;
}


/**
 * Crée la Priority Queue d'évènements à venir à partir des donnée de la simulation à la date courante 
 * @param dateCourante
 * @param d
 * @return
 */
public PriorityQueue<Evenement> execute(long dateCourante, DonneesSimulation d){
    
    //Vérifie qu'il reste des incendies à éteindre
    if(d.getIncendies().isEmpty()){
        System.out.println("message du chef : tous les incendies ont été éteints, bon travail!");
        return null;
    }

    //mise à jour des données connues du chef pompier
    this.donnees = d;
    this.dateCourante = dateCourante;

    //Création des évènements
    casesEau();
    casesAdjacentesEau();
    PriorityQueue<Evenement> events = new PriorityQueue<>();
    events.addAll(traiterTousLesIncendies());
    events.addAll(remplissage());

    return events;
}

}


 

 