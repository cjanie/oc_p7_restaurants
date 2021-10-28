package com.android.go4lunch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RestaurantDeserializerTest {
    @Mock
    JSONObject jsonObject;

    @Before
    public void setUp() throws JSONException {
        MockitoAnnotations.initMocks(this);
        jsonObject.put("osm_id", 299983752);
        jsonObject.put("addr_street", null);
        jsonObject.put("addr_housenumber", null);
        jsonObject.put("name", "Le Saint-Roch");
        jsonObject.put("opening_hours", null);
        jsonObject.put("cuisine", null);

        JSONObject geometry = new JSONObject();
        geometry.put("type", "Point");
        JSONArray coordinates = new JSONArray();
        coordinates.put(3.875873816678675);
        coordinates.put(43.607746225692338);
        geometry.put("coordinates", coordinates);
    }

    @Test
    public void nothing() {



        Long osmId = jsonObject.optLong("osm_id");
        String street = jsonObject.optString("addr_street");
        Integer streetNumber = jsonObject.optInt("addr_housenumber");
        String name = jsonObject.optString("name");
        String openingHours = jsonObject.optString("opening_hours");
        String cuisine = jsonObject.optString("cuisine");
        JSONObject geometryObject = jsonObject.optJSONObject("geometry");
        String geometryType = "";
        Long coordinate0 = null;
        Long coordinate1 = null;

        if(geometryObject != null) {
            geometryType = geometryObject.optString("type");
            JSONArray geometryCoordinates = geometryObject.optJSONArray("coordinates");
            if(geometryCoordinates.length() == 2) {
                coordinate0 = geometryCoordinates.optLong(0);
                coordinate1 = geometryCoordinates.optLong(1);
            }
        }
        assertNotNull(name);


                /*
                * { "type": "Feature", "properties":
                *   { "osm_id": 299983752,
                *       "addr_street": null,
                *       "addr_housenumber": null,
                *       "amenity": "pub",
                *       "name": "Le Saint-Roch",
                *       "brand": null, "operator": null, "ref": null, "wheelchair": null, "internet_access": null,
                *       "opening_hours": null, "drive_through": null, "building": null,
                *       "cuisine": null, "capacity": null, "tourism": null,
                *       "osm_user": "", "osm_timestamp": "2013-02-17T19:53:19Z",
                *       "tags": "\"name\"=>\"Le Saint-Roch\", \"amenity\"=>\"pub\", \"osm_uid\"=>\"0\", \"osm_user\"=>\"\", \"osm_version\"=>\"5\", \"osm_changeset\"=>\"0\", \"osm_timestamp\"=>\"2013-02-17T19:53:19Z\"" },
                *       "geometry": {
                *           "type": "Point",
                *           "coordinates": [ 3.875873816678675, 43.607746225692338 ]
                *       }
                *   }
                * */
    }
}
