package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;

import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;

import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM Restaurant" )
    List<RestaurantEntity> loadAll();

    @Query("SELECT * FROM Restaurant WHERE restaurant_id = :restaurant_id" )
    RestaurantEntity load(int restaurant_id);


    @Query("SELECT * FROM Restaurant WHERE category = :category")
    RestaurantEntity loadRestaurantByCategory(String category);

    @Query("SELECT * FROM Restaurant WHERE location = :location")
    RestaurantEntity loadRestaurantByLocation(String location);

    @Query("SELECT * FROM Restaurant WHERE name = :name")
    RestaurantEntity loadRestaurantByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<RestaurantEntity> restaurantEntityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RestaurantEntity restaurantEntityList);

    @Update
    void update(RestaurantEntity restaurantEntityList);

    @Delete
    void delete(RestaurantEntity restaurantEntity);

}


