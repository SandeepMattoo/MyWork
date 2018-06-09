package uk.co.vitista.findmeeatery.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import java.io.Serializable;
/*
        foreignKeys = {

                @ForeignKey(entity = UserEntity.class,

                        parentColumns = "user_id",

                        childColumns = "user_id",

                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = RestaurantEntity.class,

                        parentColumns = "restaurant_id",

                        childColumns = "restaurant_id",

                        onDelete = ForeignKey.CASCADE)},

                        ,

        indices = {@Index(value = "user_id"),
                @Index(value = "restaurant_id")
        }
*/

@Entity(tableName = "UserLogin"
        )
public class UserLoginEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int login_id;

    @ColumnInfo(name = "email_id",index=true )
    private String email_id;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "use_type")
    private String use_type;

    public UserLoginEntity(){

    }

    public  UserLoginEntity(String email_id, String password, String use_type){
        this.email_id = email_id;
        this.password = hashPassword(password);
        this.use_type = use_type;
    }

    private String hashPassword(String password){
        //TODO Hash password
        return password;
    }
    public boolean matchPassword(String password)
    {
        return this.password.compareTo(hashPassword(password))==0;
    }

    public int getLogin_id() {
        return login_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/*
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int resturant_id) {
        this.restaurant_id = resturant_id;
    }*/

    public String getUse_type() {
        return use_type;
    }

    public void setUse_type(String login_type) {
        this.use_type = login_type;
    }
}
