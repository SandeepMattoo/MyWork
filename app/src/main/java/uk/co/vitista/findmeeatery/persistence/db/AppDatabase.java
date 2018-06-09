package uk.co.vitista.findmeeatery.persistence.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import uk.co.vitista.findmeeatery.persistence.db.entity.*;
import uk.co.vitista.findmeeatery.persistence.db.dao.*;
import uk.co.vitista.findmeeatery.persistence.db.converters.*;

import java.util.List;



@Database(entities = {
        BookingEntity.class,
        BookingMealEntity.class,
        LocationEntity.class,
        MealCategoryEntity.class,
        MealEntity.class,
        MenuEntity.class,
        MenuMealEntity.class,
        RestaurantEntity.class,
        RestaurantCategoryEntity.class,
        UserEntity.class,
        UserLoginEntity.class}, version = 3)
@TypeConverters({DateConverter.class,TimeConverter.class,LocationConverter.class})

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;
    public static final String DATABASE_NAME = "find-me-eatery-db";
    private boolean mIsDatabaseCreated;

    public abstract BookingDao bookingDao();

    public abstract BookingMealDao bookingMealDao();

    public abstract LocationDao locationDao();

    public abstract MealCategoryDao mealCategoryDao();

    public abstract MealDao mealDao();

    public abstract MenuDao menuDao();

    public abstract MenuMealDao menuMealDao();

    public abstract RestaurantCategoryDao restaurantCategoryDao();

    public abstract RestaurantDao restaurantDao();

    public abstract UserDao userDao();

    public abstract UserLoginDao userLoginDao();


    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            if (sInstance == null) {
                sInstance = buildDatabase(context.getApplicationContext());
                sInstance.updateDatabaseCreated(context.getApplicationContext());

            }

        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }


    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * <p>
     * creates a new instance of the database.
     * <p>
     * The SQLite database is only created when it's accessed for the first time.
     */

    private static AppDatabase buildDatabase(final Context appContext) {

        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .build();

    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("ALTER TABLE USER ADD COLUMN email_id TEXT default null");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("DROP TABLE UserLogin");
            database.execSQL("CREATE TABLE UserLogin (login_id INTEGER PRIMARY KEY NOT NULL, email_id TEXT , password TEXT, use_type TEXT)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_UserLogin_email_id on UserLogin (email_id)");


        }
    };


    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */

    private void updateDatabaseCreated(final Context context) {

        if (context.getDatabasePath(DATABASE_NAME).exists()) {

            setDatabaseCreated();

        }

    }


    private void setDatabaseCreated() {

        mIsDatabaseCreated = true;

    }


    private static void insertData(final AppDatabase database,
                                   final List<LocationEntity> locationEntityList,
                                   List<MealCategoryEntity> mealCategoryEntityList,
                                   List<RestaurantCategoryEntity> restaurantCategoryEntityList,
                                   MenuEntity menuEntity) {

        database.runInTransaction(() -> {

            database.locationDao().insertAll(locationEntityList);
            database.restaurantCategoryDao().insertAll(restaurantCategoryEntityList);
            database.mealCategoryDao().insertAll(mealCategoryEntityList);
            database.menuDao().insert(menuEntity);
        });

    }


    private static void insertAdminUser(final AppDatabase database) {
        final String Admin_User_Email ="administrator@fme.com";
        //check if exists
        UserLoginEntity userLoginEntity =  database.userLoginDao().findByEmail_id(Admin_User_Email);
        //if not then create default admin user
        if (userLoginEntity == null) {
            userLoginEntity = ConfigDataGenerator.generateAdminUserLogin(Admin_User_Email);
            final UserEntity userEntity = ConfigDataGenerator.generateAdminUser(Admin_User_Email);
            final UserLoginEntity userLoginEntityFinal = userLoginEntity;
            database.runInTransaction(() -> {
                database.userDao().insert(userEntity);
                UserEntity mUserEntity = database.userDao().findByEmailId(userEntity.getEmail_id());
                database.userLoginDao().insert(userLoginEntityFinal);
            });
        }


    }


    private static void addDummyRestuarantAndMeals(final AppDatabase database,final List<RestaurantEntity> restaurantEntityList,
                                        final List<MealEntity> mealEntityList){
        database.runInTransaction(() -> {
            database.restaurantDao().insertAll(restaurantEntityList);

            database.mealDao().insertAll(mealEntityList);
        });


    }

    private static void addDelay() {

        try {

            Thread.sleep(4000);

        } catch (InterruptedException ignored) {

        }

    }



    public Boolean getDatabaseCreated() {

        return mIsDatabaseCreated;

    }

    public void InitialiseData(){
        insertAdminUser(this);
        //insertData(this, locationEntityList, mealCategoryEntityList,restaurantCategoryEntityList,menuEntity);
        //List<LocationEntity> locationEntityList = ConfigDataGenerator.generateLocations();
        //List<MealCategoryEntity> mealCategoryEntityList = ConfigDataGenerator.generateMealCategories();
        //List<RestaurantCategoryEntity> restaurantCategoryEntityList = ConfigDataGenerator.generateRestaurantCategories();
        //MenuEntity menuEntity = ConfigDataGenerator.generateDefaultMenu();



        //addDummyRestuarantAndMeals(this,ConfigDataGenerator.generateDefaultRestuarant(),ConfigDataGenerator.generateDefaultMeals());
        // notify that the database was created and it's ready to be used

        this.setDatabaseCreated();


    }

}