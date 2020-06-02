package com.suusoft.elistening.model;

public class MenuLeft {

//    public static final int FRAGMENT_HOME = 0;
//    public static final int FRAGMENT_BEGIN = 1;
//    public static final int FRAGMENT_INTERMEDIATE = 2;
//    public static final int FRAGMENT_ADVANCE = 3;
//    public static final int FRAGMENT_FAVOURITE = 4;
//    public static final int FRAGMENT_DOWNLOAD = 5;
//    public static final int FRAGMENT_SETTING = 6;
//    public static final int FRAGMENT_FEEDBACK = 7;
//    public static final int FRAGMENT_MORE_APP = 8;
//    public static final int FRAGMENT_ABOUT = 9;

    public static final int FRAGMENT_HOME       = 0;
    public static final int FRAGMENT_FAVOURITE  = 1;
    public static final int FRAGMENT_DOWNLOAD   = 2;
    public static final int FRAGMENT_SETTING    = 3;
    public static final int FRAGMENT_FEEDBACK   = 4;
    public static final int FRAGMENT_MORE_APP   = 5;
    public static final int FRAGMENT_ABOUT      = 6;


    private int id;
    private String name;
    private boolean isSelected;

    public MenuLeft() {
    }

    public MenuLeft(int id, String name, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
