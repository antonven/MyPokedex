package myapps.wycoco.com.mypokedex;

/**
 * Created by dell on 7/5/2017.
 */

public class Pokemonster {

    private String pokeID;
    private String pokeName;
    private String pokeType;
    private String pokeHeight;
    private String pokeWeight;
    private String pokeAbility1;
    private String pokeImage;

    public Pokemonster() {
    }



    public Pokemonster( String pokeName,  String pokeImage, String pokeID, String pokeType, String pokeHeight, String pokeWeight) {
        this.pokeID = pokeID;
        this.pokeName = pokeName;
        this.pokeType = pokeType;
        this.pokeHeight = pokeHeight;
        this.pokeWeight = pokeWeight;
//        this.pokeAbility1 = pokeAbility1;
        this.pokeImage = pokeImage;

    }

    public String getPokeID() {
        return pokeID;
    }

    public void setPokeID(String pokeID) {
        this.pokeID = pokeID;
    }

    public String getPokeName() {
        return pokeName;
    }

    public void setPokeName(String pokeName) {
        this.pokeName = pokeName;
    }

    public String getPokeType() {
        return pokeType;
    }

    public void setPokeType(String pokeType) {
        this.pokeType = pokeType;
    }
//
    public String getPokeHeight() {
        return pokeHeight;
    }

    public void setPokeHeight(String pokeHeight) {
        this.pokeHeight = pokeHeight;
    }
//
    public String getPokeWeight() {
        return pokeWeight;
    }

    public void setPokeWeight(String pokeWeight) {
        this.pokeWeight = pokeWeight;
    }
//
//    public String getPokeAbility1() {
//        return pokeAbility1;
//    }
//
//    public void setPokeAbility1(String pokeAbility1) {
//        this.pokeAbility1 = pokeAbility1;
//    }

    public String getPokeImage() {
        return pokeImage;
    }

    public void setPokeImage(String pokeImage) {
        this.pokeImage = pokeImage;
    }
}
