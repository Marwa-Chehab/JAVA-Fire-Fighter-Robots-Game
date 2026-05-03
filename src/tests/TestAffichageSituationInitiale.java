import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import io.simulation.DonneesSimulation;

import io.simulation.*;
 



public class TestAffichageSituationInitiale {
    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestAffichageSituationInitiale <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation d = new DonneesSimulation(args[0]);
            Simulateur affichage = new Simulateur(d);
            affichage.dessiner(d);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

        
    }
}




















