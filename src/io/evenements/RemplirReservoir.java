package io.evenements;

import io.classes.Robot;
import io.simulation.DonneesSimulation;


/**
 * Classe de l'évènement RemplirReservoir, pour remplir les robots
 */

public class RemplirReservoir extends Evenement{


    public RemplirReservoir(long date, Robot robot, DonneesSimulation donnees,int tempsRestant){
    super(date, donnees, robot);
    //this.temps_restant = tempsRestant;
    toString();
   } 

   /**
    * Constructeur de copie
    * @param r évènement à copier
    * @param donnees données du nouvel évènement
    */
    public RemplirReservoir (RemplirReservoir r, DonneesSimulation donnees){
        super(r.getDate(), donnees, r.getRobot());
        //this.robot = donnees.getRobot(robot.getTyperobot(), robot.getIndice());
        toString();
    }

     public RemplirReservoir (long date, Robot robot, DonneesSimulation d){
        super(date, d, robot);
        toString();
    } 

    public void execute() {
    
        // Vérifier si le robot peut remplir son réservoir (est sur une case d'eau)
         if (!getRobot().peutremplirReservoir()) {
            System.out.println(getRobot().getTyperobot() + ": Le terrain ne contient pas de l'eau.");
            //getRobot().setEtat(true); //re_check later
            return;
        } 

        // Démarrer le processus de remplissage si le réservoir n'est pas plein
        if (getRobot().getReservoir()<getRobot().getReservoirMax()) {
            this.getRobot().remplissageUnitaire();

            // Planifier le prochain tick pour continuer le remplissage
            long dateSuivante = getDate() + 1;
            System.out.println("RemplirReservoir : reprogrammer RemplirReservoir pour le tick : " + dateSuivante);
            getDonnees().getEvenements().add(new RemplirReservoir(dateSuivante, getRobot(), getDonnees()));
        }
        //Sinon, le robot est innocupé 
        else {
            getRobot().setEtat(false);
            System.out.println("RemplirReservoir : remplissage complet");
        }
    }



    @Override
    public String toString() {
        return "RemplirReservoir [date=" + this.getDate() + ", robot=" + getRobot() +
        ", position=" + this.getRobot().getLigne() + "," + this.getRobot().getColonne();
    }

}
