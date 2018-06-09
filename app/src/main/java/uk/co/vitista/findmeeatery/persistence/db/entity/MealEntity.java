package uk.co.vitista.findmeeatery.persistence.db.entity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.media.Image;

import java.sql.Blob;

@Entity(tableName = "Meal",
        foreignKeys = {
                @ForeignKey(entity = MealCategoryEntity.class,
                        parentColumns = "meal_category_id",
                        childColumns = "meal_category_id")},
        indices = {@Index(value = "meal_category_id")}

)
public class MealEntity {
    @PrimaryKey(autoGenerate = true)
    private int meal_id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Description")
    private String description ;

    @ColumnInfo(name = "Image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo (name = "meal_category_id")
    private int meal_category_id;





    public int getMeal_id() {
        return meal_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public byte[] getImage()
    {
        return image;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMeal_category_id() {
        return meal_category_id;
    }

    public void setMeal_category_id(int meal_category_id) {
        this.meal_category_id = meal_category_id;
    }

    public  MealEntity()
    {


    }

    public MealEntity(String title, String description, byte[] image, Integer meal_category_id )
    {
        setMeal_category_id(meal_category_id);
        setTitle(title);
        setDescription(description);
        setImage(image);

    }
}

