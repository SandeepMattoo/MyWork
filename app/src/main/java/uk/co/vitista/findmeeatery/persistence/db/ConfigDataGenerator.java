package uk.co.vitista.findmeeatery.persistence.db;

import android.location.Location;
import android.support.annotation.NonNull;

import uk.co.vitista.findmeeatery.R;
import uk.co.vitista.findmeeatery.persistence.db.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ConfigDataGenerator {



    private static final String[] LOCATIONS = new String[]{

    "North", "East", "West","South"};

    private static final String[] MEAL_CATEGORY = new String[]{
            "Breakfast", "Lunch", "Dinner"};


    private static final String[] RESTAURANT_CATEGORIES = new String[]{
            "Restaurant", "Bistro", "Cafe"};



    public static List<LocationEntity> generateLocations() {

        List<LocationEntity> locations = new ArrayList<>(LOCATIONS.length );

        for (int i = 0; i < LOCATIONS.length; i++) {
            locations.add(new LocationEntity(LOCATIONS[i]));

        }

        return locations;

    }

    public static List<MealCategoryEntity> generateMealCategories() {

        List<MealCategoryEntity> mealCategories = new ArrayList<>(MEAL_CATEGORY.length );

        for (int i = 0; i < MEAL_CATEGORY.length; i++) {
            mealCategories.add(new MealCategoryEntity(MEAL_CATEGORY[i]));

        }

        return mealCategories;

    }

    public static List<RestaurantCategoryEntity> generateRestaurantCategories() {

        List<RestaurantCategoryEntity> restaurantCategories = new ArrayList<>(RESTAURANT_CATEGORIES.length );

        for (int i = 0; i < RESTAURANT_CATEGORIES.length; i++) {
            restaurantCategories.add(new RestaurantCategoryEntity(RESTAURANT_CATEGORIES[i]));

        }

        return restaurantCategories;

    }

    public static MenuEntity generateDefaultMenu() {

        return new MenuEntity("@id/default_menu_title","@id/default_menu_description");

    }
public  static UserLoginEntity generateAdminUserLogin(String Admin_User_Email){
        return new UserLoginEntity(Admin_User_Email,"passw0rd", "Administrator" );
}

    //public  static void delectUsersAdminUserLogin(){
    //    return new UserLoginEntity("Admin@FME.com","passw0rd", "Administrator" );
   // }


    public  static UserEntity generateAdminUser(String Admin_User_Email){

        return new UserEntity(Admin_User_Email,"Administrator","","","" );

    }

    public  static List<RestaurantEntity> generateDefaultRestuarant(){
        String about = "Billu Da Dhabba is situated on the corner of Some Street an historic street in the heart of England. \n" +
                " \n" +
                "We serve authentic Indian cuisine only the freshest ingredients are used for the vast array of dishes available. \n" +
                " \n" +
                "Our chefs has over 30 years of experience. Our dishes showcase instinctive ability of ur chefs to bring spices to life to give them a unique taste. \n" +
                " \n" +
                "The menu contains dishes that would be commonly found in restaurants throughout India with a delightful mouth-watering taste.";
        List<RestaurantEntity> restaurantEntityList = new ArrayList<RestaurantEntity>();

        restaurantEntityList.add(new RestaurantEntity(
                "Pappu Da Dhabha",
                about,
                LocalDate.now().minusYears(30),"Restaurant", null,"AB1 2XY", "14 London Road, Some City, AB1 2XY",
                "0123456789","0987654321", "092114200000","092114200000", "092114200000",
                LocalTime.of(17,00), LocalTime.of(23,59),25, null,"","","",""));

        return restaurantEntityList;

    }

    public static List<MealEntity> generateDefaultMeals(){
        List<MealEntity> mealEntityList = new ArrayList<MealEntity>();
        String about = "";

        mealEntityList.add(new MealEntity(
                "Wazwan",
                "Wazwan is a speciality cuisine from Kashmir",
                null, 3));
        mealEntityList.add(new MealEntity(
                "Pranthas parade",
                "Pranthas parade is a collection of various stuffed flat breads",
                null, 1));
        mealEntityList.add(new MealEntity(
                "Rassam rasoi",
                "Mouthwatering collection of foods from Southern states",
                null, 2));

        return mealEntityList;

    }

}