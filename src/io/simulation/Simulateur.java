package io.simulation;


import io.classes.*;
import io.evenements.*;

import java.awt.Color;
import java.util.zip.DataFormatException;
import java.util.*;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import gui.Oval; 

/**
 * Affiche dans une fenêtre graphique les données stockées dans DonneesSimulation
 * Permet le contrôle de la simulation avec les méthodes next() et restart() héritées de Simulable
 */


public class Simulateur implements Simulable{
    //facteur de division de taille des cases pour que la fenetre rentre à l'ecran de la machine
    private static int div = 1;

    private GUISimulator gui;
    private DonneesSimulation donnees;
    private PriorityQueue<Evenement> events;
    private DonneesSimulation donneesInit;
    private GestionEvenements gestion;
    private long dateCourante = 0;
    private ChefPompierElementaire chef;


/**
 * Construit le Simulateur associé aux données @param d en paramètre
 * Adapte la taille des cases dessinées en fonction de leur nombre (et de leur taille)
 * Fait une copie en profondeur des données (nécéssaire pour le restart()), stockée dans donneesInit
 */
    public Simulateur(DonneesSimulation d){ 
        
        //Adaptation de la taille des cases en fonction de la carte
        switch (d.getCarte().getNbcolonnes()) {
            case 8:
                div = 100;
                break;
            case 20:
                div = 2;
                break;
            case 50 :
                div = 5;
                break;
            default:
                System.out.println("Simulateur: carte de taille" + d.getCarte().getNbcolonnes() + " ,cas non couvert à rajouter");
                break;
        }
        
        //Initialisation du simulateur
        int casesGui = d.getCarte().getTailleCases()/div; 
        this.gui = new GUISimulator(d.getCarte().getNbcolonnes()*casesGui, d.getCarte().getNblignes()*casesGui, Color.BLACK);
        gui.setSimulable(this);
        this.donnees = d;
        this.donneesInit = new DonneesSimulation(d);
        this.chef = new ChefPompierElementaire(this.donnees, this.gestion, this.dateCourante);
        dessiner(this.donnees);
        this.events = this.donnees.getEvenements();
        events.addAll(chef.execute(dateCourante, this.donnees));
        this.gestion = new GestionEvenements(this.donnees);
        
    }



    /**
     * Affiche toutes les données dans la fenêtre graphique
     * @param d
     */
    public void dessiner(DonneesSimulation d){
        gui.reset();
        dessiner_carte(d);
        dessiner_incendies(d);
        dessiner_robots(d);
    }


    /**
     * Affiche la carte
     * @param d
     */
    public void dessiner_carte(DonneesSimulation d){
        int nbcol = this.donnees.getCarte().getNbcolonnes();
        int nblig = this.donnees.getCarte().getNblignes();

        //Dimension graphique d'une case
        int t = this.donnees.getCarte().getTailleCases()/div;

        //affichage carte :
        for (int y = 0; y < nblig; y++){
            for (int x= 0; x < nbcol; x++){
                Case c = this.donnees.getCarte().getCase(y,x);
                try{   
   
        
                NatureTerrain n = c.getNature();
                switch(n){
                    case EAU:
                        gui.addGraphicalElement(new Rectangle((x*t)+(21*(t/2)), (y*t)+(t/2), Color.BLACK, Color.CYAN, t));
                        break;
                    case FORET:
                        gui.addGraphicalElement(new Rectangle((x*t)+(21*(t/2)), (y*t)+(t/2), Color.BLACK, Color.GREEN, t));
                        break;
                    case ROCHE :
                        gui.addGraphicalElement(new Rectangle((x*t)+(21*(t/2)), (y*t)+(t/2), Color.BLACK, Color.GRAY, t));
                        break;
                    case TERRAIN_LIBRE :
                        gui.addGraphicalElement(new Rectangle((x*t)+(21*(t/2)), (y*t)+(t/2), Color.BLACK, Color.WHITE, t));
                        break;
                    case HABITAT :
                        gui.addGraphicalElement(new Rectangle((x*t)+(21*(t/2)), (y*t)+(t/2), Color.BLACK, Color.PINK, t));
                        break;
                    default :
                        throw new DataFormatException("Nature terrain invalide");
                        
                }

                //affichage date courante :
                String date = String.format("Date courante: %d", this.dateCourante);
                gui.addGraphicalElement(new Text(5*t, 10, Color.WHITE, date));

                }catch(DataFormatException e){
                    System.out.println(e);
                }
                
            }
        }
    }

    /**
     * Affiche les incendies
     * @param d
     */
    public void dessiner_incendies(DonneesSimulation d){
        int t = this.donnees.getCarte().getTailleCases()/div;
        LinkedList<Incendie> l = this.donnees.getIncendies();
        for (int i=0; i<l.size(); i++){
            String intens = String.format("%d", l.get(i).getIntensite()); //conversion int/String
            gui.addGraphicalElement(new Oval((l.get(i).getColonne()*t)+(21*(t/2)), (l.get(i).getLigne()*t)+(t/2), Color.BLACK, Color.RED, t/2));
            gui.addGraphicalElement(new Text((l.get(i).getColonne()*t)+(21*(t/2)), (l.get(i).getLigne()*t)+(t/6), Color.BLACK, intens));
            gui.addGraphicalElement(new Text((l.get(i).getColonne()*t)+(21*(t/2)), (l.get(i).getLigne()*t)+(t/2), Color.BLACK, String.format("n°%d", i)));
        }
    }

    /**
     * Affiche les robots
     * @param d
     */
    public void dessiner_robots(DonneesSimulation d){
        int t = donnees.getCarte().getTailleCases()/div;
        int ligText = 10;
        HashMap<Typerobot, LinkedList<Robot>> r = this.donnees.getRobots();
        //parcourir le HashMap necessite les 2 lignes suivantes :
        Set<Map.Entry<Typerobot, LinkedList<Robot>>> couples = r.entrySet();  //couples = set des couples(TypesRobot,LL<Robot>)
        Iterator<Map.Entry<Typerobot, LinkedList<Robot>>> itCouples = couples.iterator(); //itCouples = variable d'iteration du set couples
        while (itCouples.hasNext()) {
            Map.Entry<Typerobot,LinkedList<Robot>> couple = itCouples.next();
            LinkedList<Robot> liste = couple.getValue();
            int indiceListe = 0;
            String reserveEau = new String();
            String indiceRobot = new String();
            if(liste == null){
                continue; 
            }
            for(Robot robot : liste){
                gui.addGraphicalElement(new Oval((robot.getColonne()*t)+(21*(t/2)), (robot.getLigne()*t)+(t/2), Color.BLACK, Color.BLACK, t/4));
                ligText = ligText+t;
                try{
                    switch(couple.getKey()) { // Ensure case-insensitive matching
                        case DRONE:
                            indiceRobot  = String.format("%d", indiceListe);
                            gui.addGraphicalElement(new Text((robot.getColonne()*t)+(21*(t/2)), (robot.getLigne()*t)+(t/2), Color.WHITE, "D" + indiceRobot));
                            indiceListe ++;
                            reserveEau = String.format("Reserve Drone %d: %d", indiceListe, robot.getReservoir());
                            gui.addGraphicalElement(new Text(5*t, ligText, Color.WHITE, reserveEau));
                            break;

                        case PATTES:
                            indiceRobot  = String.format("%d", indiceListe);
                            gui.addGraphicalElement(new Text((robot.getColonne()*t)+(21*(t/2)), (robot.getLigne()*t)+(t/2), Color.WHITE, "P" + indiceRobot));
                            indiceListe ++;
                            reserveEau = String.format("Reserve Pattes %d: infini", indiceListe);
                            gui.addGraphicalElement(new Text(5*t, ligText, Color.WHITE, reserveEau));
                            break;

                        case ROUES:
                            indiceRobot  = String.format("%d", indiceListe);
                            gui.addGraphicalElement(new Text((robot.getColonne()*t)+(21*(t/2)), (robot.getLigne()*t)+(t/2), Color.WHITE, "R" + indiceRobot));
                            indiceListe ++;
                            reserveEau = String.format("Reserve Roues %d: %d", indiceListe, robot.getReservoir());
                            gui.addGraphicalElement(new Text(5*t, ligText, Color.WHITE, reserveEau));
                            break;

                        case CHENILLES:
                            indiceRobot  = String.format("%d", indiceListe);
                            gui.addGraphicalElement(new Text((robot.getColonne()*t)+(21*(t/2)), (robot.getLigne()*t)+(t/2), Color.WHITE, "C" + indiceRobot));
                            indiceListe ++;
                            reserveEau = String.format("Reserve Chenilles %d: %d", indiceListe, robot.getReservoir());
                            gui.addGraphicalElement(new Text(5*t, ligText, Color.WHITE, reserveEau));
                            break;
                        
                        default:
                            throw new DataFormatException("Type de robot invalide: "+ robot.getTyperobot());
                    }
                    
                }catch(DataFormatException e){
                    System.out.println(e);
                }
            } 
        }   
    }
 


    public long getDateCourante() {
        return dateCourante;
    }

    /**
     * Execute les évènements de date inférieure à la date courant
     * Test l'état des robot et fait appel au chef pompier si au moins l'un d'entre aux est innocupé (etat = false)
     */
    @Override
    public void next() {
        int t = donnees.getCarte().getTailleCases()/div;

        //Execution des evenements programés 
        System.out.println("\n\n\nnext : DATE COURANTE : " + dateCourante);
        this.gestion.nextEvenement(this.dateCourante);
        dessiner(this.donnees);

        //Vérification que tous les robots sont bien occupés, déclenchement du chef pompier sinon
        for (LinkedList<Robot> listeRobots : donnees.getRobots().values()) {
            for (Robot robot : listeRobots) {
                Boolean etat = robot.getEtat();
                if(!etat && (robot.getTyperobot() != Typerobot.ROUES)){
                    System.out.println("NEXT : chef declenché car : " + robot + ", etat=" + robot.getEtat());
                    PriorityQueue<Evenement> e = chef.execute(dateCourante + 1, this.donnees);
                    if(e == null){
                        System.out.println("Simulation terminée");
                        gui.addGraphicalElement(new Text(5*t, 500, Color.WHITE, "SIMULATION TERMINÉE"));
                        return;
                    }
                    events.addAll(e);
                    break;
                }
            }
        }
        this.dateCourante ++;
        
    } 
    


    /**
    * Fait une copie en profondeur des données initale 
    * Fait appel au chef pompier pour recalculer les évènements depuis le début
    */
    @Override
    public void restart(){
        System.out.println("\n\n\n RESTART : ");
        this.donnees = null;
        this.donnees = new DonneesSimulation(donneesInit);
        this.dateCourante =0;
        donnees.setEvenements(chef.execute(dateCourante, this.donnees));
        this.events = this.donnees.getEvenements();
        dessiner(this.donnees);
        this.gestion = new GestionEvenements(donnees);
    }

}


