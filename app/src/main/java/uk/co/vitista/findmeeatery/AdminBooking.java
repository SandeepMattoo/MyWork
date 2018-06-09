package uk.co.vitista.findmeeatery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdminBooking extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_individual_booking,container,false);
     return  view;
    }
}
