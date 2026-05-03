package io.evenements;

import io.classes.Robot;
import io.simulation.DonneesSimulation;


/**
 * Classe mère de tous les évènements
 */

public abstract class Evenement implements Comparable<Evenement>{
    private long date;
    private Robot robot;
    private DonneesSimulation donnees;


    public Evenement(long date, DonneesSimulation newDonnees, Robot r){
        this.date = date;
        this.donnees = newDonnees;

        //Récupère dans newDonnees le robot à la meme place que dans donnees
        this.robot = newDonnees.getRobot(r.getTyperobot(),r.getIndice());
        }

    public Evenement(long date, DonneesSimulation d){
        this.date = date;
        this.donnees = d;
    }

    public Evenement(long date){
        this.date = date;
    }

    public Evenement(Evenement e, DonneesSimulation d){
        this.date = e.getDate();
        this.donnees = d;
    }
    
    public long getDate(){
        return this.date;
    }

    public void setDate(long date){
        this.date = date;
    }

    public void setRobot(Robot r){
        this.robot = r;
    }

    public Robot getRobot(){
        return this.robot;
    }

    public DonneesSimulation getDonnees(){
        return this.donnees;
    }

    public void setDonnees(DonneesSimulation d){
        this.donnees = d;
    }

     @Override
    public int compareTo(Evenement other) {
        return Long.compare(this.date, other.getDate());
    } 

    public abstract void execute();
}
