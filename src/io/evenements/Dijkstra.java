package io.evenements; 

import java.util.*;

import io.classes.Carte;
import io.classes.Case;
import io.classes.Direction;
import io.classes.Robot;
import io.simulation.DonneesSimulation;


/**
 * Classe permettant de trouver un plus cours chemin entre deux cases
 */


public class Dijkstra {
    private long dateFinale;
    private DonneesSimulation donnees;

    public Dijkstra (DonneesSimulation d){
        this.donnees = d;
    }

    public long getDateFinale() {
        return dateFinale;
    }


    /**
     * retourne les évènements permettant au robot de se rendre sur la case @param destination
     * depuis sa position
     * @param robot robot à déplacer
     * @param dateCourante
     * @param d données de la simulation à cet instant
     * @return
     */
    public PriorityQueue<Evenement> execute(Robot robot, Case destination, long dateCourante, DonneesSimulation d ){
        this.donnees = d;
            dateFinale = 0;

            //Génération du plus court chemin
            List<Case> chemin = trouverChemin(robot.getPosition(), destination, robot);
            
            //Calcul du coût du chemin en temps
            long coutTotal = 0;
            for (int i = 0; i < chemin.size() - 1; i++) {
                coutTotal += robot.tempsdeplacement(chemin.get(i + 1));
            }
            dateFinale = coutTotal;
            // Génère et affiche les événements de déplacement
            if(!chemin.isEmpty()){
                return genererEvenements(chemin, robot, dateCourante);
            }
            else{
                System.out.println("dijkstra : pas de chemin possible entre le robot" + robot.toString() 
                + "et la destination (" +destination.getLigne()+", " + destination.getColonne());
                return null;
            }

            

    }
    

    /**
     * Trouves les cases par lesquelles passe le plus court chemin entre le robot et sa destination
     * Les stocke dans un arrayList et les retourne
     * (algorithme de Dijkstra)
     * @param depart
     * @param destination
     * @param robot
     * @return
     */
    public ArrayList<Case> trouverChemin(Case depart, Case destination, Robot robot) {
        Map<Case, Double> distances = new HashMap<>();
        Map<Case, Case> predecesseurs = new HashMap<>();
        double nouvelleDistance;
    
        // Comparateur pour la file prioritaire qui considère une distance infinie pour les cases absentes
        PriorityQueue<Case> filePrioritaire = new PriorityQueue<>(
            Comparator.comparingDouble(c -> distances.getOrDefault(c, Double.MAX_VALUE))
        );
    
        distances.put(depart, 0.0);
        filePrioritaire.add(depart);
    
        while (!filePrioritaire.isEmpty()) {
            Case courante = filePrioritaire.poll();
            
            if (courante.equals(destination)) {
                break;
            }
    
            for (Direction dir : Direction.values()) {
                Case voisin = donnees.getCarte().getVoisin(courante, dir);
                
                if (voisin != null && robot.testdeplacementDijkstra(voisin)) {
                    double cout = robot.tempsdeplacement(voisin);
                    nouvelleDistance = distances.get(courante) + cout;
    
                    // Mise à jour de la distance et des prédécesseurs
                    if (nouvelleDistance < distances.getOrDefault(voisin, Double.MAX_VALUE)) {
                        distances.put(voisin, nouvelleDistance);
                        predecesseurs.put(voisin, courante);
                        filePrioritaire.add(voisin);
                    }
                }
            }
        }
    
        // Construction du chemin à partir des prédécesseurs
        ArrayList<Case> chemin = new ArrayList<>();
        for (Case f = destination; f != null; f = predecesseurs.get(f)) {
            chemin.add(f);
        }
        Collections.reverse(chemin);  // Inverser pour obtenir l'ordre de départ à destination
        return chemin;
    }
    
    
    /**
     * à partir d'un ArrayList de cases consécutives, génère les évènements pour que le robot puisse se déplacer
     * @param chemin
     * @param robot
     * @param dateDepart
     * @return
     */
    public PriorityQueue<Evenement> genererEvenements(List<Case> chemin, Robot robot, long dateDepart) {
        long date = dateDepart;
        PriorityQueue<Evenement> events = new PriorityQueue<Evenement>(new ComparateurEvenements());

        for (int i = 0; i < chemin.size() - 1; i++) {
            Case courante = chemin.get(i);
            Case suivante = chemin.get(i + 1);
            Direction direction = determinerDirection(courante, suivante, robot.getCarte());

            if (direction != null) {
                // Calculer le temps de déplacement pour chaque étape
                long tempsDeplacement = (long) robot.tempsdeplacement(suivante);
                DeplacementRobot evenement = new DeplacementRobot(date, robot, direction, robot.getCarte(),tempsDeplacement, donnees);
                events.add(evenement);
                date += tempsDeplacement;

            }
        }
        return events;
    }


    /**
     * Détermine la direction vers laquelle doit se déplacer le robot pour se rendre sur la case suivante
     * à partir de la case courante
     * @param courante
     * @param suivante
     * @param carte
     * @return
     */
    public Direction determinerDirection(Case courante, Case suivante, Carte carte) {
        for (Direction dir : Direction.values()) {
            Case voisin = carte.getVoisin(courante, dir);
            
            if (voisin != null && voisin.equals(suivante)) {
                return dir;
            }
        }
        return null; 
    }

    /**
     * Pour un @param robot et un groupe de @param cases donnés, calcul le chemin pour atteindre 
     * la case la plus proche du robot.
     * @return
     */
    public ArrayList<Case> casePlusProche(Robot robot, LinkedList<Case> cases){
        ArrayList<Case> chemin = new ArrayList<Case>();
        long coutMin = robot.getCarte().getNbcolonnes()*robot.getCarte().getNblignes()*robot.getCarte().getTailleCases();
        for (Case c : cases){
            ArrayList<Case> bis = trouverChemin(robot.getPosition(), c, robot);
            long coutTotal = 0;
            for (int i = 0; i < bis.size() - 1; i++) {
                coutTotal += robot.tempsdeplacement(bis.get(i + 1));
            }
            if(coutTotal < coutMin){
                coutMin = coutTotal;
                chemin = trouverChemin(robot.getPosition(), c, robot);
            }
        dateFinale = coutMin;
        }
        return chemin;
    }


 
}




    
 