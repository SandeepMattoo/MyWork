package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.entity.UserEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.UserLoginEntity;

import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface UserLoginDao {


    @Query("SELECT * FROM UserLogin WHERE email_id LIKE :email_id LIMIT 1")
    UserLoginEntity findByEmail_id(String email_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(UserLoginEntity userLoginEntity);

    @Delete
    void delete(UserLoginEntity userLoginEntity);

}
