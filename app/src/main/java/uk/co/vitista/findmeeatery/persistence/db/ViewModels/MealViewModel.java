package uk.co.vitista.findmeeatery.persistence.db.ViewModels;


import android.arch.lifecycle.*;
import android.content.Context;

import uk.co.vitista.findmeeatery.persistence.db.AppDatabase;
import uk.co.vitista.findmeeatery.persistence.db.entity.MealEntity;

import java.util.List;


public class MealViewModel extends ViewModel {
    private List<MealEntity> MealList;

    public MealViewModel(){
    }

    public List<MealEntity> getMeals(Context context) {
        if (MealList == null) {
            MealList = LoadMeals(context);
        }
        return MealList;
    }

    public MealEntity getMeal(Context context, int meal_id) {
        return LoadMeal(context, meal_id);
    }

    private List<MealEntity> LoadMeals(Context context)
    {
        return AppDatabase.getInstance(context).mealDao().loadAll();

    }
    private MealEntity LoadMeal(Context context, int meal_id)
    {
        return AppDatabase.getInstance(context).mealDao().loadMeal(meal_id);

    }
}
