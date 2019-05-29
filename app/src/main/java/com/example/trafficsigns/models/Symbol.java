package com.example.trafficsigns.models;
import android.os.Parcel;
import android.os.Parcelable;

public class Symbol implements Parcelable {

    private String name;
    private int id;
    private String symbolDescription;
    private int categoryId;
    private byte[] image;
    private boolean isInList;


    public Symbol(int id,String name,String symbolDescription,byte[]image,int categoryId,boolean isInList){

        this.name = name;
        this.id = id;
        this.symbolDescription = symbolDescription;
        this.categoryId = categoryId;
        this.image = image;
        this.isInList = isInList;

    }

    protected Symbol(Parcel in) {
        name = in.readString();
        id = in.readInt();
        symbolDescription = in.readString();
        categoryId = in.readInt();
        image = in.createByteArray();
        isInList = in.readByte() != 0;
    }

    public static final Creator<Symbol> CREATOR = new Creator<Symbol>() {
        @Override
        public Symbol createFromParcel(Parcel in) {
            return new Symbol(in);
        }

        @Override
        public Symbol[] newArray(int size) {
            return new Symbol[size];
        }
    };


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getSymbolDescription() {
        return symbolDescription;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public byte[] getImage() {
        return image;
    }

    public boolean isInList() {
        return isInList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(symbolDescription);
        dest.writeInt(categoryId);
        dest.writeByteArray(image);
        dest.writeByte((byte) (isInList ? 1 : 0));
    }

    public void setInList(boolean inList) {
        isInList = inList;
    }

}
