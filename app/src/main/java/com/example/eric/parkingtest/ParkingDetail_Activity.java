package com.example.eric.parkingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParkingDetail_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvName , tvArea , tvServicetime , tvAddress;
    private MapView mapView;
    private GoogleMap gmap;
    private double pdTW97X , pdTW97Y;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail_);

        tvName = findViewById(R.id.pDetail_name);
        tvArea = findViewById(R.id.pDetail_area);
        tvServicetime = findViewById(R.id.pDetail_servicetime);
        tvAddress = findViewById(R.id.pDetail_address);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        Intent intent = getIntent();
        String pdName = intent.getExtras().getString("name");
        String pdArea = intent.getExtras().getString("area");
        String pdServicetime = intent.getExtras().getString("pServicetime");
        String pdAddress = intent.getExtras().getString("address");
        pdTW97X = intent.getExtras().getDouble("tw97x");
        pdTW97Y = intent.getExtras().getDouble("tw97y");

        tvName.setText("停車場名稱: "+pdName);
        tvArea.setText("區域: "+pdArea);
        tvServicetime.setText("營業時間: "+pdServicetime);
        tvAddress.setText("地址: "+pdAddress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(17);
        gmap.setIndoorEnabled(true);

        UiSettings uiSettings = gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        TMParameter tm =new TMParameter();
        double[] wgs84= TWD97_convert_to_WGS84(tm,pdTW97X,pdTW97Y);
        LatLng ny = new LatLng(wgs84[0], wgs84[1]);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ny);
        gmap.addMarker(markerOptions);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    public class TMParameter {
        public double getDx() {
            return 250000;
        }

        public double getDy() {
            return 0;
        }

        public double getLon0() {
            return 121 * Math.PI / 180;
        }

        public double getK0() {
            return 0.9999;
        }

        public double getA() {
            return 6378137.0;
        }

        public double getB() {
            return 6356752.314245;
        }
    }
    public double[] TWD97_convert_to_WGS84(TMParameter tm, double x, double y) {
        double dx = tm.getDx();
        double dy = tm.getDy();
        double lon0 = tm.getLon0();
        double k0 = tm.getK0();
        double a = tm.getA();
        double b = tm.getB();
        //double e = tm.getE();
        double e= Math.pow((1- Math.pow(b,2)/ Math.pow(a,2)), 0.5);
        x -= dx;
        y -= dy;

        // Calculate the Meridional Arc
        double M = y/k0;

        // Calculate Footprint Latitude
        double mu = M/(a*(1.0 - Math.pow(e, 2)/4.0 - 3* Math.pow(e, 4)/64.0 - 5* Math.pow(e, 6)/256.0));
        double e1 = (1.0 - Math.pow((1.0 - Math.pow(e, 2)), 0.5)) / (1.0 + Math.pow((1.0 - Math.pow(e, 2)), 0.5));

        double J1 = (3*e1/2 - 27* Math.pow(e1, 3)/32.0);
        double J2 = (21* Math.pow(e1, 2)/16 - 55* Math.pow(e1, 4)/32.0);
        double J3 = (151* Math.pow(e1, 3)/96.0);
        double J4 = (1097* Math.pow(e1, 4)/512.0);

        double fp = mu + J1* Math.sin(2*mu) + J2* Math.sin(4*mu) + J3* Math.sin(6*mu) + J4* Math.sin(8*mu);

        // Calculate Latitude and Longitude

        double e2 = Math.pow((e*a/b), 2);
        double C1 = Math.pow(e2* Math.cos(fp), 2);
        double T1 = Math.pow(Math.tan(fp), 2);
        double R1 = a*(1- Math.pow(e, 2))/ Math.pow((1- Math.pow(e, 2)* Math.pow(Math.sin(fp), 2)), (3.0/2.0));
        double N1 = a/ Math.pow((1- Math.pow(e, 2)* Math.pow(Math.sin(fp), 2)), 0.5);

        double D = x/(N1*k0);

        // lat
        double Q1 = N1* Math.tan(fp)/R1;
        double Q2 = (Math.pow(D, 2)/2.0);
        double Q3 = (5 + 3*T1 + 10*C1 - 4* Math.pow(C1, 2) - 9*e2)* Math.pow(D, 4)/24.0;
        double Q4 = (61 + 90*T1 + 298*C1 + 45* Math.pow(T1, 2) - 3* Math.pow(C1, 2) - 252*e2)* Math.pow(D, 6)/720.0;
        double lat = fp - Q1*(Q2 - Q3 + Q4);

        // long
        double Q5 = D;
        double Q6 = (1 + 2*T1 + C1)* Math.pow(D, 3)/6;
        double Q7 = (5 - 2*C1 + 28*T1 - 3* Math.pow(C1, 2) + 8*e2 + 24* Math.pow(T1, 2))* Math.pow(D, 5)/120.0;
        double lon = lon0 + (Q5 - Q6 + Q7)/ Math.cos(fp);

        return new double[] {Math.toDegrees(lat), Math.toDegrees(lon)};
    }
}
