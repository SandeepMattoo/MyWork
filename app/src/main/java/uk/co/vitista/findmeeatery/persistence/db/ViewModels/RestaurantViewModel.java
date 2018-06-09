package uk.co.vitista.findmeeatery.persistence.db.ViewModels;

import android.arch.lifecycle.*;
import android.content.Context;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;
import java.util.List;


public class RestaurantViewModel extends ViewModel {
    //private MutableLiveData<List<RestaurantEntity>> restaurantList = new MutableLiveData<>();
    private List<RestaurantEntity> restaurantEntityList;


   public RestaurantViewModel(){


   }
    public List<RestaurantEntity> getRestaurants(Context context ) {
        if (restaurantEntityList == null) {
            //fetch data from database
            restaurantEntityList= LoadRestaurants(context);


        }
        return restaurantEntityList;
    }
    public RestaurantEntity getRestaurant(Context context , int restaurant_id) {
        return LoadRestaurant(context, restaurant_id);
    }

    private List<RestaurantEntity> LoadRestaurants(Context context)
    {

        return AppDatabase.getInstance(context).restaurantDao().loadAll();

    }
    private RestaurantEntity LoadRestaurant(Context context, int restaurant_id)
    {

        return AppDatabase.getInstance(context).restaurantDao().load(restaurant_id);

    }


}