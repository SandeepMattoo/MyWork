package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingMealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MenuMealEntity;

import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface MenuMealDao {
    @Query("SELECT * FROM MenuMeal WHERE menu_meal_id = :menu_meal_id")
    MenuMealEntity loadMenuMeal(int menu_meal_id);

    @Query("SELECT * FROM MenuMeal WHERE  menu_id = :menu_id")
    List<MenuMealEntity> findbyMenuId(int menu_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<MenuMealEntity> menuMealEntityList);

    @Delete
    void delete(MenuMealEntity menuMealEntity);

}
