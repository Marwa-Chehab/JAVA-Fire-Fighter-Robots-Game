package io.evenements;
import io.simulation.*;
import java.util.PriorityQueue;




/**
 * A ce stade du projet, la classe est ne sert plus à rien et la méthode nextEvenement(long date) 
 * aurait probablement plus sa place dans la classe Simulateur ou les classes Chef Pompier mais nous
 * nous en sommes rendues compte trop tard
 */



public class GestionEvenements {

     private DonneesSimulation donnees;
    private PriorityQueue<Evenement> evenements;

     public GestionEvenements(DonneesSimulation d){
        this.donnees = d;
        this.evenements = d.getEvenements();
    } 
 
    public DonneesSimulation getDonnees(){
        return donnees;
    }

    public void setDonnees(DonneesSimulation d){
        this.donnees = d;
    }

    /**
     * Execute les evenements de date inférieure à la date courante
     * @param date
     */

    public void nextEvenement(long date){
        //System.out.println("\nnextEvenement : donnees du gestionnaire : " + donnees);
        //System.out.println("\nnextEvenement : verif de donnees.events : " + evenements);
        if (evenements.iterator().hasNext()){
            while(!this.evenements.isEmpty() && this.evenements.peek().getDate() <= date){
                Evenement event = this.evenements.poll();  // Récupère et retire l'événement en tête
                System.out.println("nextEvenement : event executé : " + event);
                event.execute();  // Exécute l'événement
            }
        }
        else{
            System.out.println("nextEvenement : Il n'y a plus d'évènements\n");
        }
    }

}
