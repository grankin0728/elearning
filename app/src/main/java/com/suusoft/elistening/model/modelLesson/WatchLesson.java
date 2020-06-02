package com.suusoft.elistening.model.modelLesson;

/**
 * Created by Suusoft on 10/17/2017.
 */

public class WatchLesson {

    private int id , completePercent, numberOfCorrect;
    private boolean view, download, favorite;

    public WatchLesson() {
    }

    public WatchLesson(int id, boolean view, boolean download, boolean favorite) {
        this.id = id;
        this.view = view;
        this.download = download;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompletePercent() {
        return completePercent;
    }

    public void setCompletePercent(int completePercent) {
        this.completePercent = completePercent;
    }

    public int getNumberOfCorrect() {
        return numberOfCorrect;
    }

    public void setNumberOfCorrect(int numberOfCorrect) {
        this.numberOfCorrect = numberOfCorrect;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
