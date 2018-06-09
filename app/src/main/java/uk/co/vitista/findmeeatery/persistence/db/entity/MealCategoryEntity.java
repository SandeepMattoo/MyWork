package uk.co.vitista.findmeeatery.persistence.db.entity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;

@Entity(tableName = "MealCategory")
public class MealCategoryEntity {
    @PrimaryKey(autoGenerate = true)
    private int meal_category_id;

    @ColumnInfo(name = "Name")
    private String name;

    public int getMeal_category_id() {
        return meal_category_id;
    }

    public String getName() {
        return name;
    }

    public void setMeal_category_id(int meal_category_id) {
        this.meal_category_id = meal_category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MealCategoryEntity(){

    }
    public MealCategoryEntity(String name){
        this.name = name;

    }
}
