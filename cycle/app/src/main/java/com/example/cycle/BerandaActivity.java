package com.example.cycle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cycle.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.concurrent.Executor;

//public class BerandaActivity extends AppCompatActivity {
public class BerandaActivity extends FragmentActivity implements OnMapReadyCallback {
//    private Button bProfile;
    private FusedLocationProviderClient locationProviderClient;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    BottomNavigationView bottomNavigationView;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

//        Fragment fragment = new Map_Fragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

        locationProviderClient = LocationServices.getFusedLocationProviderClient(BerandaActivity.this);
//        getLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.scan:
//                        startActivity(new Intent(getApplicationContext(),ScanActivity.class));
//                        overridePendingTransition(0,0);
                        scanCode();
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 10){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"Izin Lokasi Tidak Aktif !",Toast.LENGTH_SHORT).show();
            } else {
                getLocation();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Get Permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},10);
            return;
        } else {
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                // Get location
                @Override
                public void onSuccess(Location location) {
                    if(location == null) {
                        Toast.makeText(getApplicationContext(),"Lokasi Tidak Aktif !",Toast.LENGTH_SHORT).show();
                    } else {
                        Double dbllat = location.getLatitude();
                        Double dbllong = location.getLongitude();

//                        latitude.setText(String.valueOf(location.getLatitude()));
//                        longitude.setText(String.valueOf(location.getLongitude()));
//                        altitude.setText(String.valueOf(location.getAltitude()));
//                        akurasi.setText(location.getAccuracy() + "%");

                        gotoPeta(dbllat, dbllong, 16.0f);

//                        cariBtn.setOnClickListener(v -> {
//                            goCari(dbllat, dbllong);
//                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void gotoPeta(Double lat, Double lng, float dblzoom) {
        mMap.clear();
        LatLng Lokasibaru = new LatLng(lat,lng);
//        mMap.addMarker(new MarkerOptions(). position(Lokasibaru).title("Marker in  " +lat +":" +lng));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Lokasibaru, dblzoom));

        mMap.addCircle(new CircleOptions().center(new LatLng(-7.281501175467346, 112.79553146901594))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Lokasibaru,16.0f));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        LatLng its = new LatLng(-7.281501175467346, 112.79553146901594);

        mMap.addMarker(new MarkerOptions().position(its).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(its,12.0f));
//        Circle circle = mMap.addCircle(new CircleOptions()
//                .center(new LatLng(-7.281501175467346, 112.79553146901594))
////                .center(new LatLng(lat, lng))
//                .radius(10000)
//                .strokeColor(Color.RED)
//                .fillColor(Color.BLUE));

//        mMap.addCircle(new CircleOptions().center(new LatLng(-7.281501175467346, 112.79553146901594))
//                .radius(20)
//                .strokeColor(Color.RED)
//                .fillColor(Color.BLUE));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(its,16.0f));

    }

    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(BerandaActivity.this);
            builder.setTitle("Order:");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
//                    Toast.makeText(getApplicationContext(), "kepanggil cuy", Toast.LENGTH_SHORT).show();
                    fingerprint();
                    dialogInterface.dismiss();

                }
            }).show();

        }
    });

    private void fingerprint() {
        BiometricManager biometricManager=BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "Device doesn't have fingerprint", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(), "No Fingerprint Assigned", Toast.LENGTH_SHORT).show();
                break;
        }

        Executor executor= ContextCompat.getMainExecutor(this);

        biometricPrompt=new BiometricPrompt(BerandaActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Order Success", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Start Journey", Toast.LENGTH_SHORT).show();
                getLocation();
//                mMainLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("Confirm order bicycle and start journey")
                .setDescription("Use Figerprint To Login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
    }
}