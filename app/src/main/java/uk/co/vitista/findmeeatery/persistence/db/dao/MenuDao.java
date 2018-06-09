package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.entity.MenuEntity;

import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface MenuDao {

    @Query("SELECT * FROM Menu" )
    List<MenuEntity> loadAll();

    @Query("SELECT * FROM Menu WHERE menu_id = :menu_id")
    MenuEntity loadMenu(int menu_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MenuEntity mealEntity);

    @Delete
    void delete(MenuEntity mealEntity);

}



