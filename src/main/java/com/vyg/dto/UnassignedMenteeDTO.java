package com.vyg.dto;

public class UnassignedMenteeDTO {

    private Long id;
    private String name;
    private String surname;
    private String cellNumber;
    private String nation;

    public UnassignedMenteeDTO() {}

    public UnassignedMenteeDTO(Long id, String name, String surname,
                               String cellNumber, String nation) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cellNumber = cellNumber;
        this.nation = nation;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getCellNumber() { return cellNumber; }
    public String getNation() { return nation; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setCellNumber(String cellNumber) { this.cellNumber = cellNumber; }
    public void setNation(String nation) { this.nation = nation; }
}

