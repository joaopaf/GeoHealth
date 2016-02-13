package tk.geohealth.geohealth.network;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import tk.geohealth.geohealth.models.Result;

/**
 * Created by GeoHealth on 13/02/16.
 */
public interface MapService {
    @GET("/")
    Call<Result> isOnBeach(@Query("lat") double latitude, @Query("lon") double longitude);
}
