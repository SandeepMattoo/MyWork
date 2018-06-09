package uk.co.vitista.findmeeatery.persistence.db.dao;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import java.util.List;
import uk.co.vitista.findmeeatery.persistence.db.entity.UserEntity;
import android.arch.persistence.room.OnConflictStrategy;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<UserEntity> loadAll();

    @Query("SELECT * FROM User WHERE user_id = :user_Id")
    UserEntity loadUser(int user_Id);

    @Query("SELECT * FROM User WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    UserEntity findByName(String first, String last);

    @Query("SELECT * FROM User WHERE email_id LIKE :email_id LIMIT 1")
    UserEntity findByEmailId(String email_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<UserEntity> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(UserEntity user);

    @Delete
    void delete(UserEntity user);

}
