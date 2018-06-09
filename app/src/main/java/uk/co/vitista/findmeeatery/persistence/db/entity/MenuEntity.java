package uk.co.vitista.findmeeatery.persistence.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;


@Entity(tableName = "Menu")
public class MenuEntity {
    @PrimaryKey(autoGenerate = true)
    private int menu_id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;


    public void setTitle(String title) {
        this.title = title;
    }


    public int getMenu_id() {
        return menu_id;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }


    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public  MenuEntity()
    {

    }
    public MenuEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
