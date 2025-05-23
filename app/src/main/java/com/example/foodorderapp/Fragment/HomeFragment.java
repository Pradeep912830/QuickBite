package com.example.foodorderapp.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodorderapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ImageSlider imageSlider;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.image_slider);
        setupImageSlider();

        return view;
    }

    private void setupImageSlider() {

            List<SlideModel> slideModels = new ArrayList<>();

            slideModels.add(new SlideModel(R.drawable.food_one, ScaleTypes.FIT));
            slideModels.add(new SlideModel(R.drawable.food_two, ScaleTypes.FIT));
            slideModels.add(new SlideModel(R.drawable.food_three, ScaleTypes.FIT));
            slideModels.add(new SlideModel(R.drawable.food_four, ScaleTypes.FIT));

            imageSlider.setImageList(slideModels);
    }
}
