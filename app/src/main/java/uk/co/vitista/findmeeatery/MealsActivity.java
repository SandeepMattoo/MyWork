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
import android.widget.TextView;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.ViewModels.MealViewModel;
import uk.co.vitista.findmeeatery.persistence.db.entity.BookingEntity;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;


public class MealsActivity extends Fragment{

    private RecyclerView recyclerView;
    private MealsAdapter mealsAdapter;

    private AppDatabase appDatabase;
    private Context context;
    private Integer user_id;
    private static Integer EDIT_CODE=666;
    private boolean reloadNeeded = true;

    private MealViewModel mealViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_meals,container,false);

        context = container.getContext();

        appDatabase =  AppDatabase.getInstance(context);

         user_id= getArguments().getInt(getString(R.string.user_id));


        recyclerView = (RecyclerView) view.findViewById(R.id.meal_view);


        mealsAdapter = new MealsAdapter(context,this, user_id );

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mealsAdapter);
        mealsAdapter.notifyDataSetChanged();

        TextView mealTitle = view.findViewById(R.id.meal_view_title);
        mealTitle.setText("Menu");

        FloatingActionButton fab = view.findViewById(R.id.fabMeal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Add_EditMeal(null); }
            });

        return  view;
    }

    @Override
    public void onResume()
    {
      super.onResume();
        if (this.reloadNeeded)
        {
            if (user_id != null)
            {
                refresh_Data();
            }

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

        mealsAdapter.notifyDataSetChanged();
    }

    private void show_Add_EditMeal(MealEntity mealEntity){
        Intent intentToStartMeal ;
        intentToStartMeal = new Intent(context, AddMeal.class);

        if (mealEntity != null) {
            //pass id of current selection
            intentToStartMeal.putExtra(getString(R.string.meal_id), mealEntity.getMeal_id());
            }
        startActivityForResult(intentToStartMeal, EDIT_CODE);

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
