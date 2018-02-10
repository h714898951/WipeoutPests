package com.hh.wipeoutpests.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hh.wipeoutpests.R;
import com.hh.wipeoutpests.activity.main.BaseFragment;
import com.hh.wipeoutpests.baidumap.MyOrientationListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class OneFragment extends BaseFragment {
    Button dddd;
    private MapView mMapView;
    private BaiduMap mBaiduMap;//百度地图对象
    private LocationClient mylocationClient;//定位服务客户对象
    private MylocationListener mylistener;//重写的监听类
    private Context context;

    private double myLatitude;//纬度，用于存储自己所在位置的纬度
    private double myLongitude;//经度，用于存储自己所在位置的经度
    private float myCurrentX;
    private MyLocationConfiguration.LocationMode locationMode;//定位图层显示方式
    private MyOrientationListener myOrientationListener;//方向感应器类对象
    private BitmapDescriptor myIconLocation1;//图标1，当前位置的箭头图标


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_one, null);
        initView(view);
        initLocation();
        return view;
    }
    void initView(View view){
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        // 不显示缩放比例尺
        mMapView.showZoomControls(false);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        mBaiduMap = mMapView.getMap();
        //根据给定增量缩放地图级别
        MapStatusUpdate msu= MapStatusUpdateFactory.zoomTo(18.0f);
        mBaiduMap.setMapStatus(msu);

        dddd = view.findViewById(R.id.dddd);
        dddd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng latLng = new LatLng(30.739739,103.979747);
                huatu(latLng);
            }
        });

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                String info = bundle.getString("info");
                Toast.makeText(getActivity().getApplicationContext(), "Marker被点击了!"+info, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    Marker marker;
    void huatu(LatLng latLng){
        /** * 绘制Marker，地图上常见的类似气球形状的图层 */
         MarkerOptions markerOptions = new MarkerOptions();//参数设置类
         markerOptions.position(latLng);//marker坐标位置
         BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.img_bike_nearby_big);
         markerOptions.icon(icon);//marker图标，可以自定义
         markerOptions.draggable(false);//是否可拖拽，默认不可拖拽
         markerOptions.anchor(0.5f, 1.0f);//设置 marker覆盖物与位置点的位置关系，默认（0.5f, 1.0f）水平居中，垂直下对齐
//         markerOptions.alpha(0.8f);//marker图标透明度，0~1.0，默认为1.0
         markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);//marker出现的方式，从天上掉下
         markerOptions.flat(false);//marker突变是否平贴地面
         markerOptions.zIndex(1);//index //Marker动画效果
//         markerOptions.icons(bitmapList);//如果需要显示动画，可以设置多张图片轮番显示
         markerOptions.period(10);//每个10ms显示bitmapList里面的图片 ...
         mBaiduMap.addOverlay(markerOptions);//在地图上增加mMarker图层

         marker = (Marker) mBaiduMap.addOverlay(markerOptions);
         Bundle bundle = new Bundle();
         //info必须实现序列化接口
         bundle.putString("info", "info");
         marker.setExtraInfo(bundle);
    }
    private void initLocation() {
        //图标类型
//        locationMode = MyLocationConfiguration.LocationMode.NORMAL;
        locationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        mylocationClient = new LocationClient(getActivity());
        mylistener = new MylocationListener();
        // 开启定位图层，一定不要少了这句，否则对在地图的设置、绘制定位点将无效
        mBaiduMap.setMyLocationEnabled(true);
        //注册监听器
        mylocationClient.registerLocationListener(mylistener);
        //配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型等
        LocationClientOption mOption = new LocationClientOption();
        //设置坐标类型
        mOption.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        mOption.setIsNeedAddress(true);
        //设置是否打开gps进行定位
        mOption.setOpenGps(true);
        //设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        int span = 1000;
        mOption.setScanSpan(span);
        //设置 LocationClientOption
        mylocationClient.setLocOption(mOption);

        //初始化图标,BitmapDescriptorFactory是bitmap 描述信息工厂类.
//        myIconLocation1 = BitmapDescriptorFactory.fromResource(R.mipmap.map_marker);

        //配置定位图层显示方式,三个参数的构造器
        MyLocationConfiguration configuration = new MyLocationConfiguration(locationMode, true, myIconLocation1);
        //设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效，参见 setMyLocationEnabled(boolean)
//        mBaiduMap.setMyLocationConfigeration(configuration);
//        mBaiduMap.setMyLocationConfiguration(configuration);
        myOrientationListener = new MyOrientationListener(context);
        //通过接口回调来实现实时方向的改变
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                myCurrentX = x;
            }
        });

        mylocationClient.start();
    }
    /*
    *根据经纬度前往
    */
    public void getLocationByLL(double la, double lg)
    {
        //地理坐标的数据结构
        LatLng latLng = new LatLng(la, lg);
        //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

    /*
     *定位请求回调接口
     */
    public class MylocationListener implements BDLocationListener
    {
        //定位请求回调接口
        private boolean isFirstIn=true;
        //定位请求回调函数,这里面会得到定位信息
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //BDLocation 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
            //MyLocationData 定位数据,定位数据建造器
            /*
            * 可以通过BDLocation配置如下参数
            * 1.accuracy 定位精度
            * 2.latitude 百度纬度坐标
            * 3.longitude 百度经度坐标
            * 4.satellitesNum GPS定位时卫星数目 getSatelliteNumber() gps定位结果时，获取gps锁定用的卫星数
            * 5.speed GPS定位时速度 getSpeed()获取速度，仅gps定位结果时有速度信息，单位公里/小时，默认值0.0f
            * 6.direction GPS定位时方向角度
            * */
            myLatitude = bdLocation.getLatitude();
            myLongitude = bdLocation.getLongitude();
            Log.e("tag","myLatitude=="+myLatitude+"//.direction(myCurrentX)///"+"myLongitude"+myLongitude);
            MyLocationData data = new MyLocationData.Builder()
                    .direction(100).latitude(bdLocation.getLatitude())//设定图标方向
                    .accuracy(0.0f)//getRadius 获取定位精度,默认值0.0f
                    .latitude(myLatitude)//百度纬度坐标
                    .longitude(myLongitude)//百度经度坐标
                    .build();
            //设置定位数据, 只有先允许定位图层后设置数据才会生效，参见 setMyLocationEnabled(boolean)
            mBaiduMap.setMyLocationData(data);
            //判断是否为第一次定位,是的话需要定位到用户当前位置
            if (isFirstIn) {
                //根据当前所在位置经纬度前往
                getLocationByLL(myLatitude, myLongitude);
                isFirstIn = false;
                //提示当前所在地址信息
//                Toast.makeText(context, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
