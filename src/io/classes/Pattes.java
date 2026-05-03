package io.classes;

/**
 * Classe des robots de type pattes
 */

public class Pattes extends Robot{
    
    /**
     * Constructeur par défaut
     * @param carte
     * @param lig
     * @param col
     * @param indice
     */
    public Pattes(Carte carte,int lig, int col, int indice){
        super(carte,lig,col, indice, 1);
        this.setVitesse(30/3.6);
        this.setReservoir(10000);
        this.setTempRemplissage(0);
        this.setUniteVerse(10);

    }

    /**
     * La vitesse du robot est précisée lors de la lecture de la carte
     * Elle n'a pas de limite (cf sujet)
     * @param carte
     * @param lig
     * @param col
     * @param vit
     * @param indice
     */
    public Pattes(Carte carte,int lig, int col, int vit, int indice){
        super(carte,lig,col, indice, 1);   
        this.setVitesse(vit/3.6);
        this.setReservoir(10000);
        this.setTempRemplissage(0);
        this.setUniteVerse(10);
        
    }
    /**
     * Constructeur de copie
     * @param p
     */
    public Pattes(Pattes p){
        super(p.getCarte(), p.getLigne(), p.getColonne(), p.getIndice(), 1);
        this.setVitesse(p.getVitesse());
        this.setReservoir(10000);
        this.setTempRemplissage(0);
        this.setUniteVerse(10);
    }

    @Override
    public void setPosition(Case c){
        if(c.getNature()==NatureTerrain.EAU){
            System.out.println("Je ne peux pas me positionner dans cette case!!\n");
        }
        else{
            this.setLigne(c.getLigne());
            this.setColonne(c.getColonne());
        }

    }


    @Override
    public double getVitesse(NatureTerrain nature){
        if(nature == NatureTerrain.ROCHE){
            return 10;
        }
        return this.getVitesse();
    }

    @Override
    public boolean peutremplirReservoir(){
        //Reservoir infini
        return true;
    }
    
    

    @Override
    public boolean testdeplacement(Case c){
        if(c.getNature()==NatureTerrain.EAU)
        {
            return false;
        }
        else{
            this.setLigne(c.getLigne());
            this.setColonne(c.getColonne());
            return true;
        }
    }

    @Override
    public boolean testdeplacementDijkstra(Case c){
        if (c.getNature() == NatureTerrain.EAU) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Typerobot getTyperobot(){
       return Typerobot.PATTES;
    }
   


}
