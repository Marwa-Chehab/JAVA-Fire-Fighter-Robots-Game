package io.classes;

/**
 * Classe des robots de type drone
 */

public class Drone extends Robot {
    

    /**
     * Constructeur par défaut
     * @param carte
     * @param lig
     * @param col
     * @param indice
     */
    public Drone(Carte carte,int lig, int col, int indice){
        super(carte,lig,col, indice, 10000);
        this.setVitesse(100/3.6);
        this.setReservoir(10000);
        this.setTempRemplissage(30);
        this.setUniteVerse(10000/30);
        this.setUniteAspire(10000/(30*60));
    }

    /**
     * La vitesse est lue dans le fichier carte
     * Elle est limitée à 150 km/h
     * @param carte
     * @param lig
     * @param col
     * @param vit
     * @param indice
     */
    public Drone(Carte carte, int lig, int col, int vit, int indice){
        super(carte,lig,col, indice, 10000);
        if(vit>150){
            this.setVitesse(150/3.6);
        }
        else{
            this.setVitesse(vit/3.6);
        }
        this.setReservoir(10000);
        this.setTempRemplissage(30);
        this.setUniteVerse(10000/30);
        this.setUniteAspire(10000/(30*60));
    }

    /**
     * Constructeur de copie
     * @param d
     */
    public Drone(Drone d){
        super(d.getCarte(), d.getLigne(), d.getColonne(), d.getIndice(),10000);
        this.setVitesse(d.getVitesse());
        this.setReservoir(10000);
        this.setTempRemplissage(30);
        this.setUniteVerse(10000/30);
        this.setUniteAspire(10000/(30*60));
    }


    @Override
    public void setPosition(Case c){
        this.setLigne(c.getLigne());
        this.setColonne(c.getColonne());
    }



    @Override
    public double getVitesse(NatureTerrain nature){ 
       if(this.getVitesse()>150){
        this.setVitesse(150);
       }
     return this.getVitesse();

    }

    @Override
    public boolean peutremplirReservoir(){
      return this.getPosition().getNature() == NatureTerrain.EAU;
     
    }


    @Override
    public boolean testdeplacement(Case c){
        this.setLigne(c.getLigne());
        this.setColonne(c.getColonne());
        return true;
    }
    @Override
    public boolean testdeplacementDijkstra(Case c){
        return true;
    }
    @Override
    public Typerobot getTyperobot(){
       return Typerobot.DRONE;
    }





}
