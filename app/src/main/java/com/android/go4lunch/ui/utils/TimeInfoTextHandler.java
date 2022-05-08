package com.android.go4lunch.ui.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.android.go4lunch.R;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.usecases.enums.TimeInfo;
import com.android.go4lunch.usecases.models.RestaurantModel;
import com.android.go4lunch.usecases.enums.TimeInfoVisitor;

import java.time.LocalTime;

public class TimeInfoTextHandler {

    public String getText(RestaurantModel restaurantModel, Context context) {
        return restaurantModel.getTimeInfo().accept(new TimeInfoVisitor<String>() {
            @Override
            public String visitOpen() {
                LocalTime close = restaurantModel.getCloseToday();
                return context.getString(
                        R.string.open_until) + " "
                        + close.toString();
            }

            @Override
            public String visitClose() {
                return context.getString(R.string.close);
            }

            @Override
            public String visitClosingSoon() {
                return context.getString(R.string.closing_soon);
            }

            @Override
            public String visitDefaultTimeInfo() {
                return "";
            }
        });
    }

    public int getColor(RestaurantModel restaurant, TextView textView) {
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

    public int getStyle(RestaurantModel restaurant) {
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
