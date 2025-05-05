package com.vyg.dto;

public class NationPointsSummaryDTO {

    private String nation;
    private int week;
    private String month;
    private int year;
    private int points;

    public NationPointsSummaryDTO(String nation, int week, Object month, int year, int points) {
        this.nation = nation;
        this.week = week;
        this.month = month.toString();
        this.year = year;
        this.points = points;
    }


    public String getNation() {
        return nation;
    }

    public int getWeek() {
        return week;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getPoints() {
        return points;
    }
}
