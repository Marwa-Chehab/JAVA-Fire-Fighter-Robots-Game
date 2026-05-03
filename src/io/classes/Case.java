package io.classes;

/**
 * Classe de l'objet Case
 */

public class Case {
    private int ligne , colonne ;
    private NatureTerrain  nature ; 

    public Case(int ligne, int colonne, NatureTerrain nature) {
    this.ligne = ligne;  
    this.colonne = colonne;  
    this.nature = nature;  
    }
  
    /**
     * Constructeur de copie
     * @param c
     */
    public Case(Case c){
        this.ligne = c.getLigne();
        this.colonne = c.getColonne();
        this.nature = c.getNature();
    }

    public int  getLigne(){
        return this.ligne;
    } 

    public int  getColonne(){
        return this.colonne ;
    }
    
    public NatureTerrain getNature(){
        return this.nature ;
    }
    public void setNature(NatureTerrain nature) {
        this.nature = nature;
    }
    


    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Check if the same object
        if (obj == null || getClass() != obj.getClass()) return false;  // Check type and null
    
        Case other = (Case) obj;  // Cast to Case after confirming type
        return this.ligne == other.ligne && 
               this.colonne == other.colonne && 
               this.nature == other.nature;
    }
    

}
