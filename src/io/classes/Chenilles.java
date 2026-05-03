package io.classes;

/**
 * Classe des robots de type chenille
 */

public class Chenilles extends Robot {
    
    /**
     * Constructeur par défaut
     * @param carte
     * @param lig
     * @param col
     * @param indice
     */
    public Chenilles(Carte carte,int lig, int col, int indice){
        super(carte,lig,col, indice,2000);
        this.setVitesse(60/3.6);
        this.setReservoir(2000);
        this.setTempRemplissage(5);
        this.setUniteVerse(100/8);
        this.setUniteAspire(2000/(5*60));
    }

    /**
     * La vitesse est lue dans les fichier carte
     * Elle ne doit pas dépasser 80 km/h
     * @param carte
     * @param lig
     * @param col
     * @param vit
     * @param indice
     */
    public Chenilles(Carte carte,int lig, int col, int vit, int indice){
        super(carte,lig,col, indice, 2000);
        if (vit>80){
            this.setVitesse(80/3.6);
        }
        else {
            this.setVitesse(vit/3.6);
        }
        this.setReservoir(2000);
        this.setTempRemplissage(5);
        this.setUniteVerse(100/8);
        this.setUniteAspire(2000/(5*60));
    }

    /**
     * Constructeur de copie
     * @param c
     */
    public Chenilles(Chenilles c){
        super(c.getCarte(), c.getLigne(), c.getColonne(), c.getIndice(), 2000);
        this.setVitesse(c.getVitesse());
        this.setReservoir(2000);
        this.setTempRemplissage(5);
        this.setUniteVerse(100/8);
        this.setUniteAspire(2000/(5*60));
    }

    
    @Override
    public void setPosition(Case c){ 
    if (c.getNature() ==NatureTerrain.EAU || c.getNature() == NatureTerrain.ROCHE)
    System.err.println("Position inaccessible!!\n");
    else{
        this.setLigne(c.getLigne());
        this.setColonne(c.getColonne());
        }
    }
   

    @Override
    public double getVitesse(NatureTerrain nature){
        if(nature == NatureTerrain.FORET)
            return getVitesse()/2;
 
        return getVitesse();
    }


    /**
     * Le robot doir être à coté de l'eau pour remplir son réservoir
     */
    @Override
    public boolean peutremplirReservoir(){
        Case c = this.getPosition();

        Case est = this.getCarte().getVoisin(c,Direction.EST);
        Case ouest  = this.getCarte().getVoisin(c,Direction.OUEST);
        Case nord = this.getCarte().getVoisin(c,Direction.NORD);
        Case sud = this.getCarte().getVoisin(c,Direction.SUD);
       
        return (est != null && est.getNature() == NatureTerrain.EAU) ||
         (ouest != null && ouest.getNature() == NatureTerrain.EAU) ||
         (nord != null && nord.getNature() == NatureTerrain.EAU) ||
         (sud != null && sud.getNature() == NatureTerrain.EAU);
    }
    

    @Override
    public boolean testdeplacement(Case c){
        if(c.getNature() == NatureTerrain.EAU || c.getNature() == NatureTerrain.ROCHE){
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
        if (c.getNature() == NatureTerrain.EAU || c.getNature() == NatureTerrain.ROCHE) {
            return false;
        } else {
            return true;
        }
    }



    @Override
    public Typerobot getTyperobot(){
       return Typerobot.CHENILLES;
    }

 }
