package com.android.go4lunch.data.apiGoogleMaps.repositories;

import com.android.go4lunch.BuildConfig;

public class GoogleMapsRequestConfig {

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/";

    public static final String API_KEY = BuildConfig.GOOGLE_PLACE_API_KEY;


    // Api Place Nearby Search
    // Url form
    // https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters
    // Url to configure:
    // "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=Hackveda,Delhi&radius=500&types=food&key=PepZc";

    public static final String NEARBY_SEARCH_END_POINT = "place/nearbysearch/json";

    public static final String NEARBY_SEARCH_TYPE_PARAM = "restaurant";

    public static final String NEARBY_SEARCH_FIELDS_PARAM = "place_id";


    // Api Place Details
    // Url form
    // https://maps.googleapis.com/maps/api/place/details/json
    //  ?fields=name%2Crating%2Cformatted_phone_number
    //  &place_id=ChIJN1t_tDeuEmsRUsoyG83frY4
    //  &key=YOUR_API_KEY
    public static final String DETAILS_ENDPOINT = "place/details/json";

    public static final String DETAILS_FIELDS_PARAM = "name,geometry,formatted_address,opening_hours,photos,international_phone_number,website";

    // Api Place Photo
    // Url form
    // https://maps.googleapis.com/maps/api/place/photo
    //  ?maxwidth=400
    //  &photo_reference=Aap_uEA7vb0DDYVJWEaX3O-AtYp77AaswQKSGtDaimt3gt7QCNpdjp1BkdM6acJ96xTec3tsV_ZJNL_JP-lqsVxydG3nh739RE_hepOOL05tfJh2_ranjMadb3VoBYFvF0ma6S24qZ6QJUuV6sSRrhCskSBP5C1myCzsebztMfGvm7ij3gZT
    //  &key=YOUR_API_KEY
    public static final String PHOTO_ENDPOINT = "place/photo";

    public static final String PHOTO_MAXWIDTH_PARAM = String.valueOf(200);

    // Api Place Distance Matrix
    // https://maps.googleapis.com/maps/api/distancematrix/json
    //  ?destinations=37.773245%2C-122.469502
    //  &origins=heading%3D90%3A37.773279%2C-122.468780
    //  &key=YOUR_API_KEY
    public static final String DISTANCE_MATRIX_ENDPOINT = "distancematrix/json";
}
