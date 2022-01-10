package com.example.monumentossorianosv2;


import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;

public class MonumentDTO implements Parcelable {

    public enum MonumentType {
        Verde,
        Arquitectonico
    }

    private int id;
    private String name;
    private MonumentType type;
    private String address;
    private int phone;
    private String url;
    private String comment;
    private Bitmap image;
    private String imageFullSize;

    public MonumentDTO(String name, MonumentType type, String address, int phone, String url, String comment, Bitmap image, String imageFullSize) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.comment = comment;
        this.image = image;
        this.imageFullSize = imageFullSize;
    }

    public MonumentDTO(int id, String name, MonumentType type, String address, int phone, String url, String comment, Bitmap image, String imageFullSize) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.url = url;
        this.comment = comment;
        this.image = image;
        this.imageFullSize = imageFullSize;
    }

    protected MonumentDTO(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = MonumentDTO.MonumentType.values()[in.readInt()];
        address = in.readString();
        phone = in.readInt();
        url = in.readString();
        comment = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        imageFullSize = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(type.ordinal());
        dest.writeString(address);
        dest.writeInt(phone);
        dest.writeString(url);
        dest.writeString(comment);
        dest.writeParcelable(image, flags);
        dest.writeString(imageFullSize);
    }

    public static final Creator<MonumentDTO> CREATOR = new Creator<MonumentDTO>() {
        @Override
        public MonumentDTO createFromParcel(Parcel in) {
            return new MonumentDTO(in);
        }

        @Override
        public MonumentDTO[] newArray(int size) {
            return new MonumentDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageFullSize() {
        return imageFullSize;
    }

    public void setImageFullSize(String imageFullSize) {
        this.imageFullSize = imageFullSize;
    }
}
