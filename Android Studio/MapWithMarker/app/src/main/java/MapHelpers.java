import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Bailey Miller on 3/20/2018.
 */

public class MapHelpers
{




    public void GetUserLocation()
    {
        FusedLocationProviderApi locationApi = LocationServices.FusedLocationApi;
        location = locationApi.getLastLocation();
    }

}
