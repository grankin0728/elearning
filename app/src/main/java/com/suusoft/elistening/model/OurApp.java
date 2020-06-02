package com.suusoft.elistening.model;

public class OurApp {

    private String id, name, image, linkSite, linkStore, description ;

    public OurApp(){}

    public OurApp(String id, String name, String image, String linkSite, String linkStore, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.linkSite = linkSite;
        this.linkStore = linkStore;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkSite() {
        return linkSite;
    }

    public void setLinkSite(String linkSite) {
        this.linkSite = linkSite;
    }

    public String getLinkStore() {
        return linkStore;
    }

    public void setLinkStore(String linkStore) {
        this.linkStore = linkStore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
