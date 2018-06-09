package uk.co.vitista.findmeeatery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.BookingMealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.BookingViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.MealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.RestaurantViewModel;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingMealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.RestaurantEntity;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.restaurantsViewHolder> {

    private Context context;
    private RestaurantViewModel restaurantViewModel;



    public class restaurantsViewHolder extends RecyclerView.ViewHolder {
        public TextView category,location, name, postcode, opening_hours , number_of_covers;

        public CardView cardView;

        public restaurantsViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.restaurant_category);
            name = (TextView) view.findViewById(R.id.restaurant_name);
            location = (TextView) view.findViewById(R.id.restaurant_location);
            postcode = (TextView) view.findViewById(R.id.postcode);
            opening_hours = (TextView) view.findViewById(R.id.restaurant_opening_hours);
            number_of_covers = (TextView) view.findViewById(R.id.restaurant_no_of_tables);
            cardView = view.findViewById(R.id.restaurant_view);
        }
    }


    private String getRestaurantCategoryName(Integer restaurant_category_id) {

        return AppDatabase.getInstance(context).restaurantCategoryDao().loadRestaurantCategory(restaurant_category_id).getName();

    }


    public RestaurantsAdapter(Context context, RestaurantsActivity fragment) {
        this.context = context;
        this.restaurantViewModel =ViewModelProviders.of(fragment).get(RestaurantViewModel.class);
    }

    @Override
    public restaurantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restauarant_card, parent, false);
        return new restaurantsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final restaurantsViewHolder holder, int position) {
        RestaurantEntity restaurantEntity = restaurantViewModel.getRestaurants(context).get(position);
        holder.name.setText(restaurantEntity.getName());
        holder.category.setText(restaurantEntity.getCategory());
        holder.postcode.setText(restaurantEntity.getPostcode());


        holder.opening_hours.setText(String.format("We are open from {0} to {1}", restaurantEntity.getOpening_hours_from(), restaurantEntity.getOpening_hours_to().toString()));

        holder.number_of_covers.setText( String.format("Number of Tabled available : {0}", restaurantEntity.getNumber_of_available_tables()));

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_manage_booking, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    private void show_EditRestaurant(RestaurantEntity restaurantEntity){
        Intent intentToStartRestaurant ;
        intentToStartRestaurant = new Intent(context, RestaurantsActivity.class);
        intentToStartRestaurant.putExtra(context.getString(R.string.restaurant_id), restaurantEntity.getRestaurant_id());
        context.startActivity(intentToStartRestaurant);
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
         int position;
        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            RestaurantEntity restaurantEntity = restaurantViewModel.getRestaurants(context).get(position);
                    Toast.makeText(context, "Edit restaurant - "+ restaurantEntity.getRestaurant_id(), Toast.LENGTH_SHORT).show();
                    show_EditRestaurant(restaurantEntity);
                    return true;

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}