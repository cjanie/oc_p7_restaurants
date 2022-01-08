package com.android.go4lunch.ui.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import com.android.go4lunch.R;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.usecases.enums.TimeInfoVisitor;

public class TimeInfoTextHandler {

    public String getText(RestaurantVO restaurantVO) {
        return restaurantVO.getTimeInfo().accept(new TimeInfoVisitor<String>() {
            @Override
            public String visitOpen() {
                return "Open until " + restaurantVO.getRestaurant().getPlanning().get(new RealDateProvider().today()).get("close");
            }

            @Override
            public String visitClose() {
                return "Close";
            }

            @Override
            public String visitClosingSoon() {
                return "Closing soon";
            }

            @Override
            public String visitDefaultTimeInfo() {
                return "";
            }
        });
    }

    public int getColor(RestaurantVO restaurant, TextView textView) {
        return restaurant.getTimeInfo().accept(new TimeInfoVisitor<Integer>() {
            @Override
            public Integer visitOpen() {
                return textView.getTextColors().getDefaultColor();
            }

            @Override
            public Integer visitClose() {
                return textView.getTextColors().getDefaultColor();
            }

            @Override
            public Integer visitClosingSoon() {
                return textView.getContext().getResources().getColor(R.color.colorAccent);
            }

            @Override
            public Integer visitDefaultTimeInfo() {
                return textView.getTextColors().getDefaultColor();
            }
        });
    }

    public int getStyle(RestaurantVO restaurant) {
        return restaurant.getTimeInfo().accept(new TimeInfoVisitor<Integer>() {
            @Override
            public Integer visitOpen() {
                return Typeface.ITALIC;
            }

            @Override
            public Integer visitClose() {
                return Typeface.NORMAL;
            }

            @Override
            public Integer visitClosingSoon() {
                return Typeface.NORMAL;
            }

            @Override
            public Integer visitDefaultTimeInfo() {
                return Typeface.NORMAL;
            }
        });
    }
}
