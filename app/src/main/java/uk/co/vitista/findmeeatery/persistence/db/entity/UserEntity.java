package uk.co.vitista.findmeeatery.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;

import java.io.Serializable;
import java.time.*;

@Entity(tableName = "User" )
public class UserEntity  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int user_id;
    @ColumnInfo(name = "date_of_birth")
    private LocalDate date_of_birth;

    @ColumnInfo(name = "postcode")
    private String postcode;

    @ColumnInfo(name = "address")
    private String address;


    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "first_name")
    private String first_name;

    @ColumnInfo(name = "last_name")
    private String last_name;
    @ColumnInfo(name="email_id")
    private String email_id;

    public UserEntity()
    {

    }

    public UserEntity(String email_id, String first_name, String last_name, String postcode, String title)
    {
        this.setEmail_id(email_id);
        this.setFirst_name(first_name);
        this.setLast_name(last_name);
        this.setPostcode(postcode);
        this.setTitle(title);

    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPostcode() {
        return postcode;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}

