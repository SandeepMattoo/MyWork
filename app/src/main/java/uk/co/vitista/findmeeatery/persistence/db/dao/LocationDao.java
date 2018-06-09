package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;

import uk.co.vitista.findmeeatery.persistence.db.entity.LocationEntity;
import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface LocationDao {
    @Query("SELECT * FROM Location")
    List<LocationEntity> loadAll();

    @Query("SELECT * FROM Location WHERE location_id = :location_Id")
    LocationEntity loadLocation(int location_Id);

    @Query("SELECT * FROM Location WHERE name LIKE :name LIMIT 1")
    LocationEntity findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<LocationEntity> locationEntityList);

    @Delete
    void delete(LocationEntity locationEntity);

}

