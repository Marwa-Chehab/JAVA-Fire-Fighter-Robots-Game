package io.simulation;
import io.classes.*;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;






public class CreateurDonnees {


    /**
     * A partir de la lecture d'un fichier de donnees, 
     * crée les instances associées (cases,robots et incendies).
     * @param fichierDonnees nom du fichier à lire
     */
   


    
    private static Scanner scanner;
    private Carte carte;
    private int nbDrones = 0;
    private int nbPattes = 0;
    private int nbChenilles = 0;
    private int nbRoues = 0;

    /**
     * Constructeur à partir d'un fichier
     * @param fichierDonnees nom du fichier a lire
     */
    public CreateurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et crée les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    public Carte creerCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
    
            // Store the carte object in the instance variable
            this.carte = new Carte(nbLignes, nbColonnes, tailleCases);  // Store in 'this.carte'
            System.out.println("Carte " + nbLignes + "x" + nbColonnes + "; taille des cases = " + tailleCases);
    
            for (int lig = 0; lig < nbLignes; lig++) {       
                for (int col = 0; col < nbColonnes; col++) {
                    carte.setCase(lig, col, creerCase(lig, col));
                }
            }
    
            return carte;
    
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
    }
    


    /**
     * Lit et crée les donnees d'une case.
     */
    public Case creerCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature)
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();
           
            System.out.println("nature = " + chaineNature);
            return new Case(lig, col, nature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
        
    }


    /**
     * Lit et crée les donnees des incendies.
     */
    public LinkedList<Incendie> creerIncendies() throws DataFormatException {
        ignorerCommentaires();
        if (carte == null) {
            throw new IllegalArgumentException("Carte is null. Incendies cannot be created without a map.");
        }
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            LinkedList<Incendie> incendies = new LinkedList<Incendie>();
            for (int i = 0; i < nbIncendies; i++) {
                Incendie incendie = creerIncendie(i);
                incendies.add(incendie);
            }
            return incendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }

    }


    /**
     * Lit et crée les donnees du i-eme incendie.
     * @param i
     */
    public Incendie creerIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        if (carte == null) {
            throw new IllegalArgumentException("Carte is null when trying to create an Incendie");
        }
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();
            Incendie incendie = new Incendie(lig, col, intensite,carte, i);

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);

                    return incendie;
          
        } 
        
        catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }      
    }


    /**
     * Lit et crée les donnees des robots.
     */
    public HashMap<Typerobot, LinkedList<Robot>> creerRobots() throws DataFormatException { 
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            HashMap<Typerobot, LinkedList<Robot>> robots = new HashMap<Typerobot, LinkedList<Robot>>();
            LinkedList<Robot> drones = new LinkedList<Robot>();
            LinkedList<Robot> roues = new LinkedList<Robot>();
            LinkedList<Robot> pattes = new LinkedList<Robot>();
            LinkedList<Robot> chenilles = new LinkedList<Robot>();
            for (int i = 0; i < nbRobots; i++) {
                Robot robot = creerRobot(i);
                switch(robot.getTyperobot()) { 
                    // Ensure case-insensitive matching
                    case DRONE:
                        drones.add((Drone)robot);
                        robot.setIndice(nbDrones);
                        nbDrones ++;
                        //System.out.println("creerRobots : drone " + drones.get(nbDrones) + " d'indice " + nbDrones);
                        break;
                    case PATTES:
                        pattes.add((Pattes)robot);
                        robot.setIndice(nbPattes);
                        nbPattes ++;
                        break;
                    case ROUES:
                        roues.add((Roues)robot);
                        robot.setIndice(nbRoues);
                        nbRoues ++;
                        break;
                    case CHENILLES:
                        chenilles.add((Chenilles)robot);
                        robot.setIndice(nbChenilles);
                        nbChenilles ++;
                        break;
                    default:
                    throw new DataFormatException("Type de robot invalide: "+ robot.getTyperobot());
             } 
            }
            robots.put(Typerobot.DRONE, drones);
            robots.put(Typerobot.ROUES, roues);
            robots.put(Typerobot.CHENILLES, chenilles);
            robots.put(Typerobot.PATTES, pattes);
             
             return robots;
    } catch (NoSuchElementException e)
     {throw new DataFormatException("Format invalide. " + "Attendu: nbRobots");
    }
    }

    /**
     * Lit et crée les donnees du i-eme robot.
     * @param i
     */
public Robot creerRobot(int i) throws DataFormatException {
    ignorerCommentaires();
    System.out.print("Robot " + i + ": ");

    try {
        int lig = scanner.nextInt();
        int col = scanner.nextInt();
        System.out.print("position = (" + lig + "," + col + ");");
        String type = scanner.next();

        System.out.print("\t type = " + type);

        // Reading optional speed
        System.out.print("; \t vitesse = ");
        String s = scanner.findInLine("(\\d+)");  // Try to find a number (speed)

        int vitesse = -1; // Default value for speed if not provided
        if (s != null) {
            vitesse = Integer.parseInt(s);  // Speed was found, parse it
            System.out.print(vitesse);
        } else {
            System.out.print("valeur par defaut");
        }

        verifieLigneTerminee(); // Verify end of the line
        System.out.println();

        // Switch to create the corresponding robot type
        switch(type.toUpperCase()) {  // Ensure case-insensitive matching
            case "DRONE":
                return (vitesse == -1) ? new Drone(carte, lig, col, i) : new Drone(carte,lig, col, vitesse, i);
                
            case "PATTES":
                return (vitesse == -1) ? new Pattes(carte, lig, col,i) : new Pattes(carte,lig, col, vitesse, i);
                
            case "ROUES":
                return (vitesse == -1) ? new Roues(carte, lig, col, i) : new Roues(carte,lig, col, vitesse,i);
                
            case "CHENILLES":
                return (vitesse == -1) ? new Chenilles(carte, lig, col, i) : new Chenilles(carte,lig, col, vitesse, i);
                
            default:
                throw new DataFormatException("Type de robot invalide: " + type);
        }

    } catch (NoSuchElementException e) {
        throw new DataFormatException("Format de robot invalide. Attendu: ligne colonne type [valeur_specifique]");
    }
}



    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }

}
