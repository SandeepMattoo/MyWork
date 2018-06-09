package uk.co.vitista.findmeeatery.persistence.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.time.*;

@Entity(tableName = "Restaurant")
public class RestaurantEntity {
    @PrimaryKey(autoGenerate = true)
    private int restaurant_id;
    @ColumnInfo(name = "name")
    private String name; //(Using drop down option)

    @ColumnInfo(name = "about_restaurant")
    private String about_restaurant; //(Using drop down option)

    @ColumnInfo(name = "established_on")
    private  LocalDate established_on; //(Using drop down option)


    @ColumnInfo(name = "category")
    private String category; //(Using drop down option)

    @ColumnInfo(name = "location")
    private Location location; //(Using drop down option)
    @ColumnInfo(name = "postcode")
    private String postcode;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "land_line")
    private String land_line;

    @ColumnInfo(name = "mobile")
    private String mobile;

    @ColumnInfo(name = "reservation_telephone_number")
    private String reservation_telephone_number;
    @ColumnInfo(name = "party_booking_telephone_number")
    private String party_booking__telephone_number;
    @ColumnInfo(name = "take_away_telephone_number")
    private String take_away_telephone_number;

    @ColumnInfo(name = "opening_hours_from")
    private LocalTime opening_hours_from;//(Using Android Time picker)
    @ColumnInfo(name = "opening_hours_to")
    private LocalTime opening_hours_to;//(Using Android Time picker)

    @ColumnInfo(name = "number_of_available_tables")
    private Integer number_of_available_tables;


    @ColumnInfo(name = "user_id")
    private Integer user_id;

    @ColumnInfo(name = "trip_adviser_url")
    private String trip_adviser_url;
    @ColumnInfo(name = "facebook_url")
    private String facebook_url;
    @ColumnInfo(name = "twitter_url")
    private String twitter_url;
    @ColumnInfo(name = "instagram_url")
    private String instagram_url;

    public RestaurantEntity() {
    }

    public RestaurantEntity(
            String name,String about_restaurant, LocalDate established_on,
            String category, Location location,String postcode, String address,
            String land_line,String mobile, String reservation_telephone_number,
            String party_booking__telephone_number, String take_away_telephone_number,
            LocalTime opening_hours_from, LocalTime opening_hours_to,Integer number_of_available_tables, Integer user_id,String trip_adviser_url
            ,String facebook_url,String twitter_url ,String instagram_url)

    {
        this.name = name;
        this.about_restaurant = about_restaurant;
        this.established_on = established_on;
        this.category = category;
        this.location = location;
        this.postcode = postcode;
        this.address = address;
        this.land_line = land_line;
        this.mobile = mobile;
        this.reservation_telephone_number = reservation_telephone_number;
        this.party_booking__telephone_number = party_booking__telephone_number;
        this.take_away_telephone_number = take_away_telephone_number;
        this.opening_hours_from = opening_hours_from;
        this.opening_hours_to= opening_hours_to;
        this.number_of_available_tables = number_of_available_tables;
        this.user_id = user_id;
        this.trip_adviser_url = trip_adviser_url;
        this.facebook_url = facebook_url;
        this.twitter_url = twitter_url;
        this.instagram_url  =instagram_url;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public Integer getNumber_of_available_tables() {
        return number_of_available_tables;
    }

    public String getCategory() {
        return category;
    }

    public Location getLocation() {
        return location;
    }

    public String getPostcode() {
        return postcode;
    }

    public LocalTime getOpening_hours_from() {
        return opening_hours_from;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setNumber_of_available_tables(Integer number_of_available_tables) {
        this.number_of_available_tables = number_of_available_tables;
    }

    public void setOpening_hours_from(LocalTime opening_hours_from) {
        this.opening_hours_from = opening_hours_from;
    }

    public void setOpening_hours_to(LocalTime opening_hours_to) {
        this.opening_hours_to = opening_hours_to;
    }
    public LocalTime getOpening_hours_to() {
        return opening_hours_to;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setRestaurant_id(int resturant_id) {
        this.restaurant_id = resturant_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalDate getEstablished_on() {
        return established_on;
    }

    public String getAbout_restaurant() {
        return about_restaurant;
    }

    public String getAddress() {
        return address;
    }

    public String getFacebook_url() {
        return facebook_url;
    }

    public String getInstagram_url() {
        return instagram_url;
    }

    public String getLand_line() {
        return land_line;
    }

    public String getMobile() {
        return mobile;
    }

    public String getParty_booking__telephone_number() {
        return party_booking__telephone_number;
    }

    public String getReservation_telephone_number() {
        return reservation_telephone_number;
    }

    public String getTake_away_telephone_number() {
        return take_away_telephone_number;
    }

    public String getTrip_adviser_url() {
        return trip_adviser_url;
    }

    public String getTwitter_url() {
        return twitter_url;
    }

    public void setAbout_restaurant(String about_restaurant) {
        this.about_restaurant = about_restaurant;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEstablished_on(LocalDate established_on) {
        this.established_on = established_on;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public void setInstagram_url(String instagram_url) {
        this.instagram_url = instagram_url;
    }

    public void setLand_line(String land_line) {
        this.land_line = land_line;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setParty_booking__telephone_number(String party_booking__telephone_number) {
        this.party_booking__telephone_number = party_booking__telephone_number;
    }

    public void setReservation_telephone_number(String reservation_telephone_number) {
        this.reservation_telephone_number = reservation_telephone_number;
    }

    public void setTake_away_telephone_number(String take_away_telephone_number) {
        this.take_away_telephone_number = take_away_telephone_number;
    }

    public void setTrip_adviser_url(String trip_adviser_url) {
        this.trip_adviser_url = trip_adviser_url;
    }

    public void setTwitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
    }
}
