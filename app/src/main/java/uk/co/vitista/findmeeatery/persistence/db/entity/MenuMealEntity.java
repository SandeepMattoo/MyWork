package uk.co.vitista.findmeeatery.persistence.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "MenuMeal",
        foreignKeys = {
                @ForeignKey(entity = MenuEntity.class,
                        parentColumns = "menu_id",
                        childColumns = "menu_id"),
                @ForeignKey(entity = MealEntity.class,
                        parentColumns = "meal_id",
                        childColumns = "meal_id")

        },
        indices = {@Index(value = "menu_id"),@Index(value = "meal_id")}

)
public class MenuMealEntity {
    @PrimaryKey(autoGenerate = true)
    private int menu_meal_id;

    @ColumnInfo(name = "menu_id")
    private int menu_id;
    @ColumnInfo(name = "meal_id")
    private int meal_id;

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public int getMenu_meal_id() {
        return menu_meal_id;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMenu_meal_id(int menu_meal_id) {
        this.menu_meal_id = menu_meal_id;
    }

    public MenuMealEntity()
    {

    }

    public MenuMealEntity(int menu_id, int meal_id)
    {
        this.menu_id = menu_id;
        this.menu_meal_id =meal_id;
    }
}
