package com.example.user.myanotherapp.Mysql;

public class Bullet {
    private int bulletID;
    private int userID;
    private String content;
    private String title;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private String bulletType;
    private int importance;
    private int isMigrated ;
    private int isChecked;

    public Bullet()
    {
        this.bulletID = 0;
        this.userID = 0;
        this.content = "";
        this.title = "";
        this.dateFrom = "";
        this.dateTo = "";
        this.timeFrom = "";
        this.timeTo="";
        this.bulletType = "";
        this.importance = 0;
        this.isMigrated = 0;
        this.isChecked = 0;
    }

    public Bullet(int bulletID, int userID, String content, String title, String dateFrom, String dateTo, String timeFrom, String timeTo, String bulletType, int importance, int isMigrated, int isChecked) {
        this.bulletID = bulletID;
        this.userID = userID;
        this.content = content;
        this.title = title;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.bulletType = bulletType;
        this.importance = importance;
        this.isMigrated = isMigrated;
        this.isChecked = isChecked;
    }

    public int getBulletID() {
        return bulletID;
    }

    public void setBulletID(int bulletID) {
        this.bulletID = bulletID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getBulletType() {
        return bulletType;
    }

    public void setBulletType(String bulletType) {
        this.bulletType = bulletType;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getIsMigrated() {
        return isMigrated;
    }

    public void setIsMigrated(int isMigrated) {
        this.isMigrated = isMigrated;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "bulletID=" + bulletID +
                ", userID=" + userID +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", bulletType='" + bulletType + '\'' +
                ", importance=" + importance +
                ", isMigrated=" + isMigrated +
                ", isChecked=" + isChecked +
                '}';
    }
}

