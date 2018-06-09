package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantCategoryEntity;


import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface RestaurantCategoryDao {

    @Query("SELECT * FROM RestaurantCategory")
    List<RestaurantCategoryEntity> loadAll();

    @Query("SELECT * FROM RestaurantCategory WHERE restaurant_category_id = :restaurant_category_id")
    RestaurantCategoryEntity loadRestaurantCategory(int restaurant_category_id);

    @Query("SELECT * FROM RestaurantCategory WHERE name LIKE :name LIMIT 1")
    RestaurantCategoryEntity findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<RestaurantCategoryEntity> restaurantCategoryEntityList);

    @Delete
    void delete(RestaurantCategoryEntity restaurantCategoryEntity);

}



