package com.example.learn_naver_map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapActivity";
    private MapView mapView;
    private static NaverMap naverMap;

    //사용자 현재 위치
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSON = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

// 사용자 현재 위치
        mLocationSource =
                new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        //네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = new MapFragment();
            fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        mapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        //NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);

// 권한 확인, onRequestPermissionsResult 콜백 메서드 호출
        ActivityCompat.requestPermissions(this, PERMISSON, PERMISSION_REQUEST_CODE);

        //제주 메인 위치
        this.naverMap = naverMap;
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(33.38, 126.55),
                9
        );
        naverMap.setCameraPosition((cameraPosition));

        Log.d(TAG, "onMapReady");
//지도에 마커 표시
        Marker maker = new Marker();
        maker.setPosition(new LatLng(33.460082, 126.5623055));
        maker.setMap(naverMap);

        Marker maker2 = new Marker();
        maker2.setPosition(new LatLng(33.47768725, 126.5362232));
        maker2.setMap(naverMap);

        Marker maker3 = new Marker();
        maker3.setPosition(new LatLng(33.47535925, 126.5465348));
        maker3.setMap(naverMap);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 권한 획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

            }
        }
    }
}




