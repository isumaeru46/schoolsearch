package br.iesb.schoolsearch.schoolsearch.activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.iesb.schoolsearch.schoolsearch.R;
import br.iesb.schoolsearch.schoolsearch.models.EscolaModel;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int MAP_PERMISSION_ACCESS_COURSE_LOCATION = 9999;

    private GoogleMap mMap;
    LocationManager locationManager;
    EscolaModel escola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if(getIntent() != null){
            escola = (EscolaModel) getIntent().getSerializableExtra("escola");
        }

        if(savedInstanceState != null){
            escola = (EscolaModel) savedInstanceState.get("escola");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MAP_PERMISSION_ACCESS_COURSE_LOCATION);
        } else {
            //getLastLocation();
            //getLocation();
        }

        if(escola != null){
            if(escola.getLatitude() != null && escola.getLongitude() != null){
                LatLng escolaMapa = new LatLng( new Double(escola.getLatitude()),new Double(escola.getLongitude()));
                mMap.addMarker(new MarkerOptions().position(escolaMapa).title(escola.getNome()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(escolaMapa, 15));
            }else{
                Toast.makeText(this,"Erro ao pegar localização", Toast.LENGTH_LONG);
            }
        }else{
            Toast.makeText(this,"Erro ao pegar localização", Toast.LENGTH_LONG);
        }

    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            LatLng me = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(me).title("Eu estava aqui quando o android me localizou pela última vez!!!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 10));
        }
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(me).title("Estou Aqui!!!"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 10));
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MAP_PERMISSION_ACCESS_COURSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                    getLocation();
                } else {
                    //Permissão negada
                }
                return;
            }
        }
    }



}
