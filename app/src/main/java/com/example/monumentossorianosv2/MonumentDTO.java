package com.example.monumentossorianosv2;


import java.io.Serializable;

public class MonumentDTO implements Serializable {

    public enum MonumentType {
        Verde,
        Arquitectonico
    }

    private String name;
    private MonumentType type;
    private String address;
    private int phone;
    private String url;
    private String comment;

    public MonumentDTO(String name, MonumentType type, String address, int phone, String url, String comment) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MonumentType getType() {
        return type;
    }

    public void setType(MonumentType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
