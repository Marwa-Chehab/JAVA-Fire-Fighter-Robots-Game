package io.classes;

/**
 * Classe de l'objet carte
 */

public class Carte{
    private Case [][]  Cases ; 
    private int tailleCases ;


    /**
     * Constructeur
     * La carte étant la même tout au long de la simulation,
     * pas besoin de constructeur de copie
     * @param nbligne
     * @param nbcolonne
     * @param taille taille des cases en mètres
     */
    public Carte (int nbligne , int nbcolonne, int taille){
        this.Cases= new Case [nbligne][nbcolonne ];
        this.tailleCases = taille ;
    }



     public void setCase(int ligne, int colonne, Case c) {
        Cases[ligne][colonne] = c;   
    }

    public int getNblignes(){
        return this.Cases.length ;
    }

    public int getNbcolonnes (){
        return this.Cases[0].length ;
    }

    public int getTailleCases(){
        return this.tailleCases ; 
    }

    public Case getCase(int ligne, int colonne) {
        int x = this.getNblignes();
        int y = this.getNbcolonnes();
            if (ligne < 0 || ligne >= x || colonne < 0 || colonne >= y) {
            return null; 
        }
    
        return this.Cases[ligne][colonne];
    }
    
     
    /**
     * Vérifie l'existance d'une case voisine dans la direction dir
     * retourne true si elle existe, false sinon
     * @param src case à tester
     * @param dir direction à tester
     * @return
     */
    public boolean voisinExiste(Case src, Direction dir) {
        int ligne = src.getLigne();
        int colonne = src.getColonne();
        int newLigne = ligne;
        int newColonne = colonne;
    
        switch (dir) {
            case NORD : newLigne = ligne - 1;
            break;
            case SUD : newLigne = ligne + 1;
            break;
            case EST : newColonne = colonne + 1;
            break;
            case OUEST : newColonne = colonne - 1;
            break;
            default : {
                return false;
            }
        }
    
        // Vérifier si le voisin calculé est dans les limites de la carte
        return newLigne >= 0 && newLigne < this.getNblignes()
            && newColonne >= 0 && newColonne < this.getNbcolonnes();
    }
    
    
    
    /**
     * Retourne la case voisine dans la direction dir
     * Retourne null en cas de non existance de la case voisine
     * @param src
     * @param dir
     * @return
     */
    public Case getVoisin(Case src, Direction dir) {
    if (voisinExiste(src, dir)) {
        int ligne = src.getLigne();
        int colonne = src.getColonne();
        int newli = ligne; 
        int newco = colonne; 

        if (dir == Direction.NORD) {
            newli = ligne - 1;
        } else if (dir == Direction.SUD) {
            newli = ligne + 1;
        } else if (dir == Direction.EST) {
            newco = colonne + 1;
        } else if (dir == Direction.OUEST) {
            newco = colonne - 1;
        }

        return getCase(newli, newco);
    }
    return null;
    
}




    }

    
