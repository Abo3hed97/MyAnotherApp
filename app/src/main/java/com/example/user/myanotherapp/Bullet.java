package com.example.user.myanotherapp;

/**
 * Bullet Class that works with the Ofline database.
 */
public class Bullet {
    private String id;
    private String uid;
    private String title;
    private String text;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private String type;
    private String months;
    private String imp;
    private String vimp;
    Bullet()
    {}

    Bullet(String id,String uid,String title,String text,String dateFrom,String dateTo,
           String timeFrom,String timeTo,String type,String months,String imp ,String vimp)
    {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.text = text;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.type = type;
        this.months =months;
        this.imp = imp;
        this.vimp = vimp;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", type='" + type + '\'' +
                ", months='" + months + '\'' +
                ", imp='" + imp + '\'' +
                ", vimp='" + vimp + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getImp() {
        return imp;
    }

    public void setImp(String imp) {
        this.imp = imp;
    }

    public String getVimp() {
        return vimp;
    }

    public void setVimp(String vimp) {
        this.vimp = vimp;
    }
}
