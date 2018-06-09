package uk.co.vitista.findmeeatery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.RestaurantViewModel;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;

public class RestaurantsActivity extends Fragment{

    private RecyclerView recyclerView;
    private RestaurantsAdapter restaurantsAdapter;

    private AppDatabase appDatabase;
    private Context context;
    private static Integer EDIT_CODE=666;
    private boolean reloadNeeded = true;

    private RestaurantViewModel restaurantViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_restaurants,container,false);

        context = container.getContext();

        appDatabase =  AppDatabase.getInstance(context);

        recyclerView = (RecyclerView) view.findViewById(R.id.restaurant_view);

        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);

        restaurantsAdapter = new RestaurantsAdapter(context,this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(restaurantsAdapter);
        restaurantsAdapter.notifyDataSetChanged();

        TextView restuarantTitle = view.findViewById(R.id.restaurant_view_title);
        restuarantTitle.setText("List of Restaurants");
        FloatingActionButton fab = view.findViewById(R.id.fabRestaurant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Add_EditRestaurant(null); }
            });

        return  view;
    }

    @Override
    public void onResume()
    {
      super.onResume();
        if (this.reloadNeeded)
        {
                refresh_Data();
        }

        this.reloadNeeded = false;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_CODE) { // Ah! We are back from EditActivity, did we make any changes?
            if (resultCode == Activity.RESULT_OK) {
                // Yes we did! Let's allow onResume() to reload the data
                this.reloadNeeded = true;
            }
        }
    }
    private void refresh_Data() {

        restaurantsAdapter.notifyDataSetChanged();
    }

    private void show_Add_EditRestaurant(RestaurantEntity restaurantEntity){
        Intent intentToStartRestaurant ;
        intentToStartRestaurant = new Intent(context, AddRestaurantActivity.class);

        if (restaurantEntity != null) {
            //pass id of current selection
            intentToStartRestaurant.putExtra(getString(R.string.restaurant_id), restaurantEntity.getRestaurant_id());
            }
        startActivityForResult(intentToStartRestaurant, EDIT_CODE);


    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
