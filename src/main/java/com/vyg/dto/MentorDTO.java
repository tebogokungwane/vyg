package com.vyg.dto;

public class MentorDTO {

    private Long id;
    private String name;
    private String surname;
    private String cellNumber;
    private String nation;
    private String address;
    private long menteeCount;

    public MentorDTO() {}

    public MentorDTO(Long id, String name, String surname,
                     String cellNumber, String nation,
                     String address, long menteeCount) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cellNumber = cellNumber;
        this.nation = nation;
        this.address = address;
        this.menteeCount = menteeCount;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getCellNumber() { return cellNumber; }
    public String getNation() { return nation; }
    public String getAddress() { return address; }
    public long getMenteeCount() { return menteeCount; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setCellNumber(String cellNumber) { this.cellNumber = cellNumber; }
    public void setNation(String nation) { this.nation = nation; }
    public void setAddress(String address) { this.address = address; }
    public void setMenteeCount(long menteeCount) { this.menteeCount = menteeCount; }
}

