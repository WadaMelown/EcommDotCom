package com.example.EcommerceShop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener,
        PermissionsListener, MapboxMap.OnMapClickListener
{
    private MapView mapView;
    private MapboxMap map;
    private Button startButton;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Point startingpoint;
    private Point destinationpoint;
    private Marker destinationMarker;
    private NavigationMapRoute navigationroute;
    private static final String TAG = "MapActivity";

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map);
        startButton = findViewById(R.id.StartButton);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .origin(startingpoint)
                        .destination(destinationpoint)
                        .shouldSimulateRoute(true)
                        .build();
                NavigationLauncher.startNavigation(MapActivity.this, options);
            }
        });
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap)
    {
        map = mapboxMap;
        map.addOnMapClickListener(this);
        enableLocation();
    }

    private void enableLocation()
    {
        if(PermissionsManager.areLocationPermissionsGranted(this))
        {
            setuplocationEngine();
            setuplocationlayer();
        }
        else
            {
                permissionsManager = new PermissionsManager(this);
                permissionsManager.requestLocationPermissions(this);
            }
    }

    private void setuplocationEngine()
    {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastlocation = locationEngine.getLastLocation();
        if(lastlocation!=null)
        {
            originLocation = lastlocation;
            setCameraPlace(lastlocation);
        }
        else
            {
                locationEngine.addLocationEngineListener(this);
            }
    }
    private void setuplocationlayer()
    {
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }

    private void setCameraPlace(Location location)
    {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(location.getLatitude(),location.getLongitude()), 13.0 ));
    }

    @Override
    public void onMapClick(@NonNull LatLng point)
    {
        if(destinationMarker != null)
        {
            map.removeMarker(destinationMarker);
        }


        destinationMarker = map.addMarker(new MarkerOptions().position(point));
        destinationpoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        startingpoint = Point.fromLngLat(originLocation.getLongitude(), originLocation.getLatitude());
        generateRoute(startingpoint, destinationpoint);

        startButton.setEnabled(true);
        startButton.setBackgroundResource(R.color.cardview_dark_background);
    }

    private void generateRoute(Point origin, Point destination)
    {
        NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response)
                    {
                        if(response.body() == null ){
                            Toast.makeText(MapActivity.this, "Invalid Ad Try Again", Toast.LENGTH_SHORT).show();
                            return;
                        }else if (response.body().routes().size() == 0)
                        {
                            Toast.makeText(MapActivity.this, "No Ads Found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DirectionsRoute currentRoute = response.body().routes().get(0);

                        if(navigationroute != null)
                        {
                            navigationroute.removeRoute();
                        }else
                            {
                                navigationroute = new NavigationMapRoute( null, mapView, map);
                            }

                        navigationroute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t)
                    {
                        Toast.makeText(MapActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onConnected()
    {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if(location != null)
        {
            originLocation = location;
            setCameraPlace(location);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain)
    {
        Toast.makeText(this, "We need your permission to be able to use this feature", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionResult(boolean granted)
    {
        if(granted)
        {
            enableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(locationEngine != null)
        {
            locationEngine.requestLocationUpdates();
        }
        if(locationLayerPlugin!= null)
        {
            locationLayerPlugin.onStart();
        }
        mapView.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(locationEngine != null)
        {
            locationEngine.removeLocationUpdates();
        }
        if(locationLayerPlugin != null)
        {
            locationLayerPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(locationEngine != null)
        {
            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }


}
