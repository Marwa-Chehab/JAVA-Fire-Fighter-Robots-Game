package io.classes;

/**
 * Classe des Incendies
 */


public class Incendie {


    private Case position;
    private int intensite;
    private int indice;


    public Incendie(int lig, int col, int intensite,Carte carte, int indice){
    this.position = carte.getCase(lig, col);
    this.intensite = intensite;
    this.indice  = indice;
    }
    
    /**
     * Constructeur de copie
     * @param i
     */
    public Incendie(Incendie i){
        this.position = i.getPositionIncendie();
        this.intensite = i.getIntensite();
        this.indice = i.getIndice();
    }

    public Case getPositionIncendie(){
        return this.position;
    }

    public void setPosition(int lig, int col, Carte carte){
        position = carte.getCase(lig, col);
    }

    public int getIndice(){
        return this.indice;
    }

    public int getLigne() {
        return this.position.getLigne();  // Access ligne from the Case object
        
    }

    public int getColonne() {
        return this.position.getColonne();  // Access colonne from the Case object
    }
    public int getIntensite(){
        return this.intensite;
    }

    public void setIntensite(int newint){
        if (newint<0){
            this.intensite = 0;
        }
        else {this.intensite = newint;}
    }

    /**
     * Diminue l'intensité de l'incendie de la quantité d'eau versée
     * @param volEau volume d'eau à verser
     * */
    public void diminuerIntensite(int volEau){
        if (volEau>=this.intensite){
            this.setIntensite(0);
        }
        else{
            this.setIntensite((this.intensite - volEau));
        }
    }

    /**
     * Vérifie si l'incendie est éteint
     * retourne true si il ets éteint, false sinon
     * @return
     */
    public boolean incendieEteint(){
        if (this.intensite == 0){
            return true;
        }
        else { 
            return false;
        }
    }

}