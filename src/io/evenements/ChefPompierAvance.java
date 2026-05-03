package io.evenements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import io.classes.Case;
import io.classes.Direction;
import io.classes.Incendie;
import io.classes.NatureTerrain;
import io.classes.Robot;
import io.classes.Typerobot;
import io.simulation.DonneesSimulation;


/**
 * Même stratégie de remplissage que le chef élementaire
 * Envoie les robot à l'incendie le plus proche de leur position
 * 
 */



public class ChefPompierAvance {
    private DonneesSimulation donnees; 
   
    private long dateCourante;
    private Dijkstra dijkstra;
    private LinkedList<Case> eau;
    private LinkedList<Case> adjacentEau;
    private LinkedList<Case> enFeuCases;

    public ChefPompierAvance(DonneesSimulation d, GestionEvenements g, long dateCourante) {
        this.donnees = d;
        this.dateCourante = dateCourante;
        this.dijkstra = new Dijkstra(d);
        this.eau = new LinkedList<Case>();
        this.adjacentEau = new LinkedList<Case>();
        this.enFeuCases = new LinkedList<Case>();
    }

    public DonneesSimulation getDonnees(){
        return this.donnees;
    }

    /**
     * renvoie une liste des robots prêts à éteindre un incendie
     * @return
     */
    public LinkedList<Robot> testIncendie(){
        LinkedList<Robot> dispo = new LinkedList<>();
        for (LinkedList<Robot> listeRobots : donnees.getRobots().values()) {
            for (Robot robot : listeRobots) {
                if(!robot.reservoirIsEmpty() && !robot.getEtat()){
                    dispo.add(robot);
                }
            }
        }
        return dispo;
    }

    /**
     * Récupère les cases sur lesquelles se trouvent les incendies
     * et les stocke dans la likedList en attribut enFeuCases
     */
    public void casesEnflammees(){
        enFeuCases.clear();
        for(Incendie incendie : donnees.getIncendies()){
            Case c = incendie.getPositionIncendie();
            if(!enFeuCases.contains(c)){
                enFeuCases.add(c);
            }
        }
    }

    
    /**
     * Retourne l'incendie se trouvant sur la case c
     * @param c case devant contenir un incendie
     * @return
     */
    public Incendie recupIncendie(Case c){
        LinkedList<Incendie> arson = donnees.getIncendies();
        for (Incendie i : arson){
            Case iPosition = i.getPositionIncendie();
            if(iPosition == c){
                return i;
            }
        }
        System.out.println("recupIncendie : aucun incendie correspondant");
        return null;
    }






     /**
     * Récupère les cases eau et les stocke dans la likedList en attribut eau
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

        //Récupération des robot avec un reservoir vide
        LinkedList<Robot> aRemplir = testReservoirs();

        ArrayList<Case> chemin = new ArrayList<Case>();
        long dateFinale = 0;
        PriorityQueue<Evenement> events = new PriorityQueue<Evenement>();

        //Pour chaque robot, création de la suite d'évènement pour aller se remplir
        for(Robot robot : aRemplir){
            dateFinale = 0;

            //Recherche de la case EAU la plus proche du robot
            if (robot.getTyperobot() == Typerobot.DRONE){
                chemin = dijkstra.casePlusProche(robot, eau);
            }
            else{
                chemin = dijkstra.casePlusProche(robot, adjacentEau);
            }
            
            //Création des Evènements
            dateFinale= dijkstra.getDateFinale();
            events.addAll(dijkstra.genererEvenements(chemin, robot, dateCourante));
            events.add(new RemplirReservoir(dateCourante+dateFinale+1, robot, donnees, robot.getTempRemplissage()));
            robot.setEtat(true);
        }
        
        return events;
    }


    /**
     * Gestion de l'évènement extinction:
     * pour chaque robot disponible avec un reservoir non vide,
     * création des évènement pour l'amener à l'incendie le plus proche
     * puis pour qu'il puisse verser son réservoir
     * @return la Priority Queue de tous les évènements
     */
    public PriorityQueue<Evenement> extinction(){

        //Récupération des robots libres prêts à éteindre un incendie
        LinkedList<Robot> dispo  = testIncendie();
        ArrayList<Case> chemin = new ArrayList<Case>();
        long dateFinale = 0;
        PriorityQueue<Evenement> events = new PriorityQueue<Evenement>();

        //Création des evènements pour chaque robot
        for(Robot robot : dispo){
            dateFinale = 0;

            //Recherche de l'incendie le plus proche
            chemin = dijkstra.casePlusProche(robot, enFeuCases);
            Case destination = chemin.get(chemin.size()-1);
            Incendie aEteindre = recupIncendie(destination);

            //Création des évènements
            dateFinale = dijkstra.getDateFinale();
            events.addAll(dijkstra.genererEvenements(chemin, robot, dateCourante));
            events.add(new VerseEau(dateCourante+dateFinale+1, robot, aEteindre, robot.getReservoir(), donnees));
            robot.setEtat(true);

        }
        return events;
    }




    /**
     * Crée la Priority Queue de tous les évènements à ajouter
     * à la simulation à partir de la date courante en fonction
     * de l'état de chaque robot
     * @param dateCourante
     * @param d donnees de la simulation à l'instant date courante
     * @return
     */
public PriorityQueue<Evenement> execute(long dateCourante, DonneesSimulation d){
    //Vérifie qu'il reste des incendies à éteindre
    if(d.getIncendies().isEmpty()){
        System.out.println("message du chef : tous les incendies ont été éteints, bon travail!");
        return null;
    }
    //S'assure que le chef s'execute avec les bonnes donnees
    this.donnees = d;
    this.dateCourante = dateCourante;

    //Recherche des cases pour le remplissage
    casesEau();
    casesAdjacentesEau();
    casesEnflammees();
    PriorityQueue<Evenement> events = new PriorityQueue<>();
    events.addAll(extinction());
    events.addAll(remplissage());

    return events;
}

}

