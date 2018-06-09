package uk.co.vitista.findmeeatery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayInputStream;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.MealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.mealsViewHolder> {

    private Context context;
    private MealViewModel mealViewModel;
    private int meal_id;



    public class mealsViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description ;
        public ImageView mealImage;
        public TextView category;
        public CardView cardView;

        public mealsViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.meal_title);
            description = (TextView) view.findViewById(R.id.meal_description);
            mealImage = (ImageView) view.findViewById(R.id.meal_image);
            category = (TextView) view.findViewById(R.id.meal_category);
            cardView = view.findViewById(R.id.meal_view);

        }
    }



    private String getMealCategoryName(Integer meal_category_id) {

        return AppDatabase.getInstance(context).mealCategoryDao().loadMealCategory(meal_category_id).getName();

    }


    public MealsAdapter(Context context, MealsActivity fragment, Integer meal_id) {
            this.context = context;
            this.meal_id = meal_id;
            this.mealViewModel = ViewModelProviders.of(fragment).get(MealViewModel.class);
        }



    @Override
    public mealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_card, parent, false);
        return new mealsViewHolder(itemView);
    }

    private Bitmap getImageofByteArray(byte[] mealimage)
    {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(mealimage);
        return BitmapFactory.decodeStream(imageStream);
    }
    @Override
    public void onBindViewHolder(final mealsViewHolder holder, int position) {
        MealEntity mealEntity = mealViewModel.getMeals(context).get(position);

        holder.category.setText(getMealCategoryName(mealEntity.getMeal_category_id()));
        holder.title.setText(mealEntity.getTitle());
        holder.description.setText(mealEntity.getDescription());
        holder.mealImage.setImageBitmap(getImageofByteArray(mealEntity.getImage()));

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
    private void show_EditMeal(MealEntity mealEntity){
        Intent intentToStartMeal ;
        intentToStartMeal = new Intent(context, AddMeal.class);
        intentToStartMeal.putExtra(context.getString(R.string.meal_id), mealEntity.getMeal_id());
        context.startActivity(intentToStartMeal);
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
         int position;
        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            MealEntity mealEntity = mealViewModel.getMeals(context).get(position);
                    Toast.makeText(context, "Edit meal - "+ mealEntity.getMeal_id(), Toast.LENGTH_SHORT).show();
                    show_EditMeal(mealEntity);
                    return true;

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}