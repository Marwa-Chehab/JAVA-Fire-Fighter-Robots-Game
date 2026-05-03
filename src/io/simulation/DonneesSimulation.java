
package io.simulation;
import io.classes.*;
import io.evenements.*;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;



public class DonneesSimulation {

    private Carte carte;
    private LinkedList<Incendie> incendies;
    private HashMap<Typerobot, LinkedList<Robot>> robots;   
    private PriorityQueue<Evenement> evenements;
  ;

    /**
     * Constructeur à partir de la lecture d'un fichier
     * @param fichierDonnees nom du fichier source
     * @throws FileNotFoundException
     * @throws DataFormatException
     */
    public DonneesSimulation(String fichierDonnees) 
        throws FileNotFoundException, DataFormatException {
            System.out.println("\n == Lecture du fichier " + fichierDonnees);
    
            CreateurDonnees createur = new CreateurDonnees(fichierDonnees);
    
            // Utilisation des méthodes de CreateurDonnees pour initialiser les objets
            this.carte = createur.creerCarte();
            this.incendies = createur.creerIncendies();
            this.robots = createur.creerRobots();

            //
            this.evenements = new PriorityQueue<Evenement>(10, new ComparateurEvenements());
            //this.basicEvents = new PriorityQueue<Evenement>(10, new ComparateurEvenements());
    
            System.out.println("\n == Lecture terminée");
    
    }


    /**
     * Constructeur de copie:
     * garde la même carte
     * copie en profondeur les incendies
     * copie en profondeur les robots
     * @param d données à copier
     */
    public DonneesSimulation(DonneesSimulation d) {
        // La carte reste la même d'une simulation sur l'autre
        this.carte = d.getCarte();
    
        // Copie en profondeur de la liste d'incendies
        LinkedList<Incendie> incendiesBis = new LinkedList<>();
        for (Incendie i : d.getIncendies()) {
            incendiesBis.add(new Incendie(i));
        }
        this.incendies = incendiesBis;
    
        // Copie en profondeur du `HashMap` de robots en utilisant un switch sur `Typerobot`
        this.robots = deepCopyRobots(d.getRobots());
    

    }


    public Carte getCarte() {
        return carte;
    }

    public LinkedList<Incendie> getIncendies() {
        return incendies;
    }

    public Incendie getIncendie(int i){
        return  this.incendies.get(i);
    }

    public HashMap<Typerobot,LinkedList<Robot>> getRobots() {
        return robots;
    }

    /**
     * Dans le Hashmap de robots, accède au i-ème robot de type Type
     * @param Type
     * @param i
     * @return
     */
    public Robot getRobot(Typerobot Type, int i){
        return this.robots.get(Type).get(i);
    }

    public PriorityQueue<Evenement> getEvenements(){
        return evenements;
    }

    public void setEvenements(PriorityQueue<Evenement> queue){
        this.evenements = queue;
    }




    /**
     * Copie en profondeur d'un HashMap de robots
     * La méthode ayant été écrite en tout début de projet, nous aurions pu éviter le downcast
     * mais l'avons remarqué trop tard
     * @param original hashmap à copier
     * @return copie du hashmap
     */
    public static HashMap<Typerobot, LinkedList<Robot>> deepCopyRobots(HashMap<Typerobot, LinkedList<Robot>> original) {
        HashMap<Typerobot, LinkedList<Robot>> copy = new HashMap<>();
    
        // Pour chaque type de robot, on effectue la copie en profondeur de sa liste spécifique
        for (Typerobot type : original.keySet()) {
            LinkedList<Robot> originalList = original.get(type);
            LinkedList<Robot> copiedList = new LinkedList<>();
    
            // Copie en profondeur de chaque robot dans la liste
            if (originalList != null) {
                for (Robot robot : originalList) {
                    Robot robotCopy;
                    switch (type) {
                        case DRONE:
                            robotCopy = new Drone((Drone) robot);
                            break;
                        case ROUES:
                            robotCopy = new Roues((Roues) robot); 
                            break;
                        case PATTES:
                            robotCopy = new Pattes((Pattes) robot);
                            break;
                        case CHENILLES:
                            robotCopy = new Chenilles((Chenilles) robot); 
                            break;
                        default:
                            throw new IllegalArgumentException("Type de robot inconnu : " + type);
                    }
                    copiedList.add(robotCopy); // Ajout du robot copié à la liste copiée
                }
            }
    
            // Ajout de la liste copiée pour ce type de robot dans le HashMap copié
            copy.put(type, copiedList);
        }
    
        return copy;
    }
    


}








