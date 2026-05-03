package io.evenements;
import io.classes.*;
import io.simulation.DonneesSimulation;



/**
 * Classe de l'évènement Déplacement robot 
 */

public class DeplacementRobot extends Evenement {   
    private Direction direction;
    private Carte carte;
    private long tempsRestant;

   



    public DeplacementRobot(long date, Robot robot, Direction direction, Carte carte,long tempsRestant, DonneesSimulation d){
        super(date, d);
        this.direction = direction;
        this.carte = carte;
        this.tempsRestant = tempsRestant;
        this.setRobot(robot);
        
    }

    /**
     * Constructeur de copie
     * @param d évènement à copier
     * @param donnees donnees dunouvel évènement
     */
    public DeplacementRobot(DeplacementRobot d, DonneesSimulation donnees){
        super(d.getDate(), donnees, d.getRobot());
        this.carte = donnees.getCarte();
        this.direction = d.getDirection();
        this.tempsRestant = d.tempsRestant;
    }

    public long getTempsRestant() {
        return tempsRestant;
    }
    
    public void decrementerTempsRestant() {
        tempsRestant--;
    }

   @Override
    public void execute() {
    
        // Vérifie le temps de déplacement restant
        //Reprogrammation du déplacement à la date suivante si tempsRestant > 0
        if(tempsRestant>0){
        decrementerTempsRestant();
        long dateSuivante = getDate() + 1;
        System.out.println("Deplacement robot : Reprogrammer Deplacement pour le tick : " + dateSuivante);
        getDonnees().getEvenements().add(new DeplacementRobot(dateSuivante, getRobot(), direction, carte,tempsRestant ,getDonnees() ));
        } 

        //Déplacement terminé, effectue les changements de coordonnées
        else {
            effectuerDeplacement();
        System.out.println("Deplacement robot : déplacement complet");
        }
    } 


    public Carte getCarte(){
        return this.carte;
    }
    public Direction getDirection(){
        return this.direction;
    }

    /**
     * Déplace le robot de sa case actuelle à la case suivant de son chemin
     */
    public void effectuerDeplacement() {
            Case position_now = getRobot().getPosition();
            Case destination = carte.getVoisin(position_now, direction);

            //Vérification de l'existance de la case destination
            if (destination != null) {
                if (getRobot().testdeplacement(destination)) {
                    getRobot().setPosition(destination);
                } 
                else {
                    System.out.println("EffectuerDeplacement : " + getRobot().getTyperobot() + 
                    ": Je ne peux pas accéder à cette case car le type de terrain est " + destination.getNature() + "\n");
                }
            } 
            else {
                System.out.println("EffectuerDeplacement : Pas de case voisine dans la direction " + direction + "!\n");
            }
    }

   
@Override
public String toString() {
    return "DeplacementRobot [date=" + this.getDate() + ", robot=" + getRobot() + ", direction=" + direction + 
    ", position=" + this.getRobot().getLigne() + "," + this.getRobot().getColonne()+"]";
}



}
