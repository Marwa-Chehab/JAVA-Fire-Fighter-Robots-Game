package io.classes;


/**
 * Classe mère abstraite des robots
 */


public abstract class Robot {
    private Carte carte;
    private double vitesse;
    private int ligne;
    private int colonne;
    private int temps_remplissage;
    private int reservoir;
    private int reservoirMax;
    private int indice;
    private Boolean etat; //vaut true si le robot est occupé, false sinon
    private int uniteVerse;
    private int uniteAspire;


 



    public Robot(Carte carte, int ligne, int colonne, int indice, int reservoirMax){
        this.carte = carte;
        this.ligne = ligne;
        this.colonne = colonne;
        this.etat = false;
        this.indice = indice;
        this.reservoirMax = reservoirMax;
    }

    public void setUniteVerse(int u){
        this.uniteVerse = u;
    }

    public void setUniteAspire(int u){
        this.uniteAspire = u;
    }

    public int getUniteAspire(){
        return this.uniteAspire;
    }

    public int getUniteVerse(){
        return this.uniteVerse;
    }

    public double getVitesse(){
        return this.vitesse;
    }

    public void setVitesse(double vitesse){
        this.vitesse = vitesse;
    }

    public int getColonne(){
        return this.colonne;
    }
    public void setColonne(int colonne){
        this.colonne = colonne;
    }

    public int getLigne(){
        return this.ligne;
    }

    public void setLigne(int ligne){
        this.ligne = ligne;
    }

    public int getTempRemplissage(){
        return this.temps_remplissage;
    }

    public void setTempRemplissage(int temps_remplissage){
        this.temps_remplissage = temps_remplissage;
    }

    public int getReservoir(){
        return this.reservoir;
    }

    public int getReservoirMax(){
        return this.reservoirMax;
    }
   
    public void setReservoir(int reservoir){
         this.reservoir = reservoir;
    }

    /**
     * Vérifie si le réservoir est vide
     * @return
     */
    public boolean reservoirIsEmpty(){
      if(this.reservoir == 0){
        return true;
      }
      else 
      return false;
    }

    public Carte getCarte(){
        return this.carte;
    }

    public Boolean getEtat(){
        return etat;
    }

     public void setEtat(Boolean b){
        this.etat = b;
    } 

    public int getIndice(){
        return this.indice;
    }

    public void setIndice(int i){
        this.indice = i;
    }

    /**
     * Calcul le temps de déplacement entre deux cases adjacentes
     * @param c case destination
     * @return
     */
    public double tempsdeplacement( Case c ){
      NatureTerrain nature = c.getNature() ;
      NatureTerrain nature2 = this.getPosition().getNature() ;
      double moyenne = (this.getVitesse(nature)+this.getVitesse(nature2))/2 ;
      //return moyenne * this.carte.getTailleCases() ;  // la taille des cases question 
      return carte.getTailleCases()/moyenne ;
    }


    
    /**
     * Méthode clone des robots
     * on aurait pu éviter le downcast en mettant
     * la méthode de type abstract
     */
    public Robot clone(){
        switch(this.getTyperobot()){
            case DRONE:
            return new Drone((Drone) this);
            case ROUES:
            return new Roues((Roues)this);
            case PATTES:
            return new Pattes((Pattes)this);
            case CHENILLES:
            return new Chenilles((Chenilles)this);
            default:
            throw new IllegalArgumentException("Type de robot inconnu");

        }
    }
    
   public Case getPosition(){
        return this.carte.getCase(this.getLigne(),this.getColonne());
    } 
   
    /**
     * Calcule la quantité d'eau à verser dans la seconde
     * @param intensite intensité de l'incendie sur lequel verser l'eau
     * @return
     */
    public int deverseUnite(int intensite){
        int eauVerse = 0;
        if(intensite<this.uniteVerse){
            eauVerse = intensite;
            this.reservoir = this.reservoir-intensite;
        }
        else{
            if(this.reservoir-this.uniteVerse<0){
            eauVerse = this.reservoir;
            this.reservoir = 0;
            }
            else{
                eauVerse = this.uniteVerse;
                this.reservoir = this.reservoir-this.uniteVerse;
            }
        }
        return eauVerse;
    }


    /**
     * Rempli le réservoir de la quantité d'eau ingurgitée par le robot en une seconde
     */
    public void remplissageUnitaire(){
        if(this.reservoir + this.uniteAspire>this.reservoirMax){
            this.reservoir = this.reservoirMax;
        }
        else{
            this.reservoir = this.reservoir + this.uniteAspire;
        }
    }

    /**
     * Dépend des spécificitées et contraintes de chaque robot
     * @param c
     */
    public abstract void setPosition(Case c);

    /**
     * Dépend du robot et de la nature du terrain
     * @param nature
     * @return
     */
    public abstract double getVitesse(NatureTerrain nature);

    /**
     * Dépend des spécificitées de chaque robot
     * Un drone doit être sur une case eau
     * Les autres doivent etre à côté
     * @return
     */
    public abstract boolean peutremplirReservoir();
    

    /**
     * Test si le robot peut se déplacer et déplace le robot sur
     * la case c si le déplacement est possible
     * retuurne true si oui, false si non
     * @param c case destination
     * @return
     */
    public abstract boolean testdeplacement(Case c); 

    /**
     * retourne le type du robot
     * @return
     */
    public abstract Typerobot getTyperobot();

    /**
     * Test si le robot peut se déplacer sur la case c
     * retourne true si oui, false sinon
     * @param c
     * @return
     */
    public abstract boolean testdeplacementDijkstra(Case c); 




    









}
