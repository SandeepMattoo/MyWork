package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;

import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

@Dao
public interface MealDao {

    @Query("SELECT * FROM Meal" )
    List<MealEntity> loadAll();

    @Query("SELECT * FROM Meal WHERE meal_id = :meal_id")
    MealEntity loadMeal(int meal_id);

    @Query("SELECT * FROM Meal WHERE title = :title")
    MealEntity findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<MealEntity> mealEntityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MealEntity mealEntity);

    @Update
    void update(MealEntity mealEntityList);

    @Delete
    void delete(MealEntity mealEntity);

}



