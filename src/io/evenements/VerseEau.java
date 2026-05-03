package io.evenements;

import java.util.LinkedList;



/**
 * Classe de l'évènement VerseEau lorsque les robots vident leurs réservoirs
 */

import io.classes.Incendie;
import io.classes.Robot;
import io.classes.Typerobot;
import io.simulation.DonneesSimulation;


public class VerseEau extends Evenement{
   private Incendie incendie;
   private int volume;
   private LinkedList<Incendie> incendies;


public VerseEau(long date, Robot robot, Incendie incendie, int volume, DonneesSimulation d) {
    super(date, d, robot);
    this.incendie = incendie;
    this.volume = volume;
    this.incendies = getDonnees().getIncendies();
} 

 
/**
 * Constructeur de copie
 * @param v évènement à copier
 * @param d données du nouvel évènement
 */
public VerseEau(VerseEau v, DonneesSimulation d){
    super(v.getDate(), d, v.getRobot());
    this.incendie =d.getIncendie(v.getIncendie().getIndice());
    this.volume = v.volume;
    this.incendies = getDonnees().getIncendies();
   }


public Incendie getIncendie(){
    return this.incendie;
}



/**
 * Décrémente le réservoir du robot et l'intensité de l'incendie 
 * Tant que le réservoir et l'intensité ne sont pas nuls crée une nouvelle instance
 * de l'évènement programé pour la date suivante
 * Si l'intensité de l'incendie est à 0, supprime l'incendie et change l'état du robot à false (inoccupé)
 * Si le reservoir est vide, change l'état du robot à false (inoccupé)
 */
@Override
public void execute() {
    
    //Vérifie que le robot est bien sur un incendie
    if(getRobot().getPosition() != incendie.getPositionIncendie()){
        System.out.println("VerseEau : le robot n'est pas sur l'incendie");
        return;
    }

    //Si le robot est de type pattes, son réservoir est infini
    if (getRobot().getTyperobot() == Typerobot.PATTES) {
        if(incendie.getIntensite()>0){
            incendie.setIntensite(incendie.getIntensite() - getRobot().getUniteVerse());
            getDonnees().getEvenements().add(new VerseEau(getDate()+1, getRobot(), incendie, volume, getDonnees()));
        }
        else {
            if(incendie.getIntensite()<=0){
                incendies.remove(incendie);
            }
            getRobot().setEtat(false);}
        return;
    }

    //Pour verser de l'eau, il faut intensité != 0 et réservoir != 0
    if (getRobot().getReservoir()>0 && incendie.getIntensite()>0) {
        int eau = getRobot().deverseUnite(incendie.getIntensite());
        incendie.diminuerIntensite(eau);

        long dateSuivante = getDate() + 1;
        System.out.println("VerseEau : Reprogrammer VerseEau pour le tick : " + dateSuivante);
        getDonnees().getEvenements().add(new VerseEau(dateSuivante, getRobot(), incendie, volume, getDonnees()));
    }
    //Sinon le robot de vient innocupé
    else {
        if(incendie.getIntensite()<=0){
            incendies.remove(incendie);
        }
        getRobot().setEtat(false);
        System.out.println("VerseEau : versement complet");
    }  
}
    



@Override
public String toString() {
    return "VerseEau [date=" + this.getDate() + ", robot=" + getRobot() + ", incendie=" + incendie + 
    ", position=" + this.getRobot().getLigne() + "," + this.getRobot().getColonne()+ ", quantite" + volume +"]";
}

}
