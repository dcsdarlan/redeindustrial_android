package br.com.redeindustrial.json;

import java.io.Serializable;

public class BeerResponse implements Serializable {
    public int id;
    public String name;
    public String first_brewed;
    public String image_url;
    public String tagline;
    public String description;
    public float abv;
    public float ibu;
}
