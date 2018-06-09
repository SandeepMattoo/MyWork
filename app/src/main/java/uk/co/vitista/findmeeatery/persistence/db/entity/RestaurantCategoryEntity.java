package uk.co.vitista.findmeeatery.persistence.db.entity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;


@Entity(tableName = "RestaurantCategory")
public class RestaurantCategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int restaurant_category_id;

    @ColumnInfo(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public int getRestaurant_category_id() {
        return restaurant_category_id;
    }

    public void setRestaurant_category_id(int restaurant_category_id) {
        this.restaurant_category_id = restaurant_category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RestaurantCategoryEntity(){

    }
    public RestaurantCategoryEntity(String name){
        this.name = name;
    }
}
