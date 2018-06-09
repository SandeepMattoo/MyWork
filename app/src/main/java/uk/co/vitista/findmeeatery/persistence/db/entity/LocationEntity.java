package uk.co.vitista.findmeeatery.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;

@Entity(tableName = "Location"
)
public class LocationEntity {
    @PrimaryKey(autoGenerate = true)
    private int location_id;

    @ColumnInfo(name = "name")
    private String name;

    public LocationEntity() {

    }

    public LocationEntity(String name)
    {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocation_id() {
        return location_id;
    }

    public String getName() {
        return name;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

}
