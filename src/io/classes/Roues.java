package io.classes;


/**
 * Classe des robots de type Roues
 */

public class Roues extends Robot {

    /**
     * constructeur par défaut
     * @param carte carte où se trouve le robot
     * @param lig ordonnée du robot
     * @param col abscisse du robot
     * @param indice indice du robot
     */
    public Roues(Carte carte,int lig, int col, int indice){
        super(carte,lig,col, indice, 5000);
        this.setVitesse(80/3.6);
        this.setReservoir(5000);
        this.setTempRemplissage(10);
        this.setUniteVerse(100/5);
        this.setUniteAspire(5000/(10*60));

    }

    /**
     * La vitesse du robot est précisée lors de la lecture de la carte
     * Elle n'a pas de limite (cf sujet)
     */
    public Roues(Carte carte,int lig, int col, int vit, int indice){
        super(carte,lig,col, indice, 5000);
        this.setVitesse(vit/3.6);
        this.setReservoir(5000);
        this.setTempRemplissage(10);
        this.setUniteVerse(100/5);
        this.setUniteAspire(5000/(10*60));
        
    } 


    /**
     * Constructeur de copie
     * @param r robot à copier
     */
    public Roues(Roues r){
        super(r.getCarte(), r.getLigne(), r.getColonne(), r.getIndice(), 5000);
        this.setVitesse(r.getVitesse());
        this.setReservoir(5000);
        this.setTempRemplissage(10);
        this.setUniteVerse(100/5);
        this.setUniteAspire(5000/(10*60));
    }


    @Override
    public Case getPosition(){
        return  this.getPosition();
    } 

    @Override
    public void setPosition(Case c){
        if(c.getNature() == NatureTerrain.TERRAIN_LIBRE || c.getNature() == NatureTerrain.HABITAT){
        this.setLigne(c.getLigne());
        this.setColonne(c.getColonne());
        }
        else{
            System.err.println("Robot Roues : Position inaccessible!!\n");
        }
    }
    
    @Override
    public double getVitesse(NatureTerrain nature){
        return this.getVitesse();
    }

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
        if(c.getNature() == NatureTerrain.TERRAIN_LIBRE || c.getNature() == NatureTerrain.HABITAT){
            this.setLigne(c.getLigne());
            this.setColonne(c.getColonne());
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean testdeplacementDijkstra(Case c){
        if (c.getNature() == NatureTerrain.TERRAIN_LIBRE || c.getNature() == NatureTerrain.HABITAT) {
            return true; 
        } else {
            return false;
        }
    }

    @Override
    public Typerobot getTyperobot(){
       return Typerobot.ROUES;
    }
   
 }

