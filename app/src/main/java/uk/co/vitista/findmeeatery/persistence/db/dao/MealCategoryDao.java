package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;

import uk.co.vitista.findmeeatery.persistence.db.entity.LocationEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealCategoryEntity;

import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface MealCategoryDao {

    @Query("SELECT * FROM MealCategory")
    List<MealCategoryEntity> loadAll();

    @Query("SELECT * FROM MealCategory WHERE meal_category_id = :meal_category_Id")
    MealCategoryEntity loadMealCategory(int meal_category_Id);

    @Query("SELECT * FROM MealCategory WHERE name LIKE :name LIMIT 1")
    MealCategoryEntity findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<MealCategoryEntity> mealCategoryEntityList);

    @Delete
    void delete(MealCategoryEntity mealCategoryEntity);

}



