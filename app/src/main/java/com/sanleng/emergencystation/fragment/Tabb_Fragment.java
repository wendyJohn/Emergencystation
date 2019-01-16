package com.sanleng.emergencystation.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.baidu.navisdk.adapter.impl.BaiduNaviManager;
import com.baidu.platform.comapi.walknavi.widget.ArCameraView;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.loopj.android.http.RequestParams;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.EmergencyRescueActivity;
import com.sanleng.emergencystation.activity.EmergencyStationActivity;
import com.sanleng.emergencystation.activity.MaterialCaptureActivity;
import com.sanleng.emergencystation.activity.MonitorActivity;
import com.sanleng.emergencystation.adapter.BottomMenuAdapter;
import com.sanleng.emergencystation.adapter.StationAdapter;
import com.sanleng.emergencystation.baidumap.DemoGuideActivity;
import com.sanleng.emergencystation.baidumap.NormalUtils;
import com.sanleng.emergencystation.baidumap.WNaviGuideActivity;
import com.sanleng.emergencystation.bean.StationBean;
import com.sanleng.emergencystation.dialog.E_StationDialog;
import com.sanleng.emergencystation.net.NetCallBack;
import com.sanleng.emergencystation.net.RequestUtils;
import com.sanleng.emergencystation.net.URLs;
import com.sanleng.emergencystation.utils.PreferenceUtils;
import com.sanleng.emergencystation.utils.ScreenUtil;
import com.yinglan.scrolllayout.ScrollLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 应急救援
 *
 * @author qiaoshi
 */
public class Tabb_Fragment extends Fragment implements OnClickListener {
    private LocationClient mLocationClient = null; // 定位对象
    private BDLocationListener myListener = new MyLocationListener(); // 定位监听
    private RelativeLayout myr_back;
    private double S_mylatitude;// 纬度
    private double S_mylongitude;// 经度
    private double E_mylatitude;// 纬度
    private double E_mylongitude;// 经度
    private LocationManager locationManager;
    private MapView mMapView;  // 地图应用
    private MyLocationData locData;
    private BaiduMap mBaiduMap;
    private List<OverlayOptions> listoption;
    private LatLng latLng;
    private boolean isFirstLoc = true; // 是否首次定位
    BitmapDescriptor bdAs = BitmapDescriptorFactory.fromResource(R.drawable.stations_icon);//应急站标识
    BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.ico_sos);//求救标识
    private int i = 0;// 开锁次数
    private String str;
    private List<String> mylist = new ArrayList<>();//应急门的标识
    private static final double EARTH_RADIUS = 6378137.0;
    private List<StationBean> slist;
    private List<StationBean> slistsos;
    private List<StationBean> slists;//底部菜单选项
    private ListView stationlistview;
    private ListView soslistview;
    private StationAdapter stationAdapter;
    WalkNaviLaunchParam walkParam;
    /*导航起终点Marker，可拖动改变起终点的坐标*/
    private Marker mStartMarker;
    private Marker mEndMarker;
    BitmapDescriptor bdStart = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_start);
    BitmapDescriptor bdEnd = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_end);
    private static boolean isPermissionRequested = false;
    private LatLng startPt, endPt;
    //底部详情菜单
    private ScrollLayout mScrollLayout;
    private BottomMenuAdapter bottomMenuAdapter;
    private RelativeLayout foot;
    private TextView walkingnavigation;
    private TextView monitor;
    private TextView viewdetails;
    private TextView driveingnavigation;
    private TextView name;
    private TextView address;
    private TextView distance;
    //驾车导航
    private static final String[] authBaseArr = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int authBaseRequestCode = 1;
    private boolean hasInitSuccess = false;
    private static final String APP_FOLDER_NAME = "应急站";
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private String mSDCardPath = null;
    private BNRoutePlanNode mStartNode = null;
    //地图按钮功能
    private LinearLayout open_linyout;//开门
    private LinearLayout receivingmaterials_linyout;//领物资
    private LinearLayout returnsupplies_linyout; //还物资
    private LinearLayout surveillance;//视频
    private LinearLayout more_linyout;//更多
    //语音搜索
    private ImageView voicesearch_image;
    private AutoCompleteTextView search_edit;
    private E_StationDialog e_stationDialog;
    private String Mac;//开锁的MAC地址
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tabb_fragment, null);
        RequestPermission();
        requestPermissions();
        SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID + "=5c2ef470");
        initview();
        initMap();
        if (initDirs()) {
            initNavi();
        }
        return view;
    }

    private void initMap() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("GPS未打开");
            dialog.setMessage("请打开GPS或WIFI，提高定位精度");
            dialog.setCancelable(false);
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent setting_Intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(setting_Intent, 0);
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        }
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        // 开启交通图
        mBaiduMap.setTrafficEnabled(true);
        // 开启热力图
        mBaiduMap.setBaiduHeatMapEnabled(false);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getActivity());
        // 声明LocationClient类 //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);// 注册监听函数
        // 开启定位
        mLocationClient.start();
        // 图片点击事件，回到定位点
        mLocationClient.requestLocation();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // 获得marker中的数据
                StationBean bean = (StationBean) arg0.getExtraInfo().get("marker");
                E_mylatitude = bean.getE_mylatitude();
                E_mylongitude = bean.getE_mylongitude();
                String ids = bean.getId();
                String mac = bean.getMac();
                String names = bean.getName();
                String addresss = bean.getAddress();
                double distances = bean.getDistance();
                int type = bean.getType();
                name.setText(names);
                address.setText(addresss);
                distance.setText("距您 " + distances + " m");
                BottomMenu(names, addresss, distances, ids, mac);
                LatLng llA = new LatLng(E_mylatitude, E_mylongitude);
                showInfoWindow(llA, names);
                if (type == 1) {
                    mScrollLayout.setVisibility(View.VISIBLE);
                }
                if (type == 2) {
                    // 获得marker中的数据
                    e_stationDialog = new E_StationDialog(getActivity(), names, addresss, clickListener);
                    e_stationDialog.show();
                    mScrollLayout.setVisibility(View.GONE);
                }
                return true;

            }

        });

    }

    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 8000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true);// 打开gps
        // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程， 设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            S_mylatitude = location.getLatitude();
            S_mylongitude = location.getLongitude();
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            mBaiduMap.setMyLocationEnabled(true);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(getActivity(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(getActivity(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(getActivity(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(getActivity(), "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(getActivity(), "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(getActivity(), "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // 等待1000毫秒后获取数据
                NearbyEmergencyStation();
                NearbyEmergencySOS();
            }
        }, 1000);

        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        WalkNavigateHelper.getInstance().resume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        WalkNavigateHelper.getInstance().pause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        // 当不需要定位图层时关闭定位图层
        WalkNavigateHelper.getInstance().quit();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        super.onDestroy();
    }

    // 初始化数据
    private void initview() {
        mScrollLayout = (ScrollLayout) view.findViewById(R.id.scroll_down_layout);
        name = (TextView) view.findViewById(R.id.name);
        address = (TextView) view.findViewById(R.id.address);
        distance = (TextView) view.findViewById(R.id.distance);
        /**设置 setting*/
        mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(getActivity()) * 0.6));
        mScrollLayout.setExitOffset(ScreenUtil.dip2px(getActivity(), 110));
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToExit();
        mScrollLayout.getBackground().setAlpha(0);
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        foot = view.findViewById(R.id.foot);
        walkingnavigation = view.findViewById(R.id.walkingnavigation);
        monitor = view.findViewById(R.id.monitor);
        viewdetails = view.findViewById(R.id.viewdetails);
        driveingnavigation = view.findViewById(R.id.driveingnavigation);

        open_linyout = view.findViewById(R.id.open_linyout);//开门
        receivingmaterials_linyout = view.findViewById(R.id.receivingmaterials_linyout);//领物资
        returnsupplies_linyout = view.findViewById(R.id.returnsupplies_linyout); //还物资
        surveillance = view.findViewById(R.id.surveillance);//视频
        more_linyout = view.findViewById(R.id.more_linyout);//更多

        voicesearch_image = view.findViewById(R.id.voicesearch_image);//语音搜索
        search_edit =view.findViewById(R.id.search_edit);//搜索输入框

        myr_back = (RelativeLayout) view.findViewById(R.id.r_back);

        mylist.add("A");
        mylist.add("B");
        mylist.add("C");
        mylist.add("D");
        myr_back.setOnClickListener(this);
        walkingnavigation.setOnClickListener(this);
        viewdetails.setOnClickListener(this);
        monitor.setOnClickListener(this);
        driveingnavigation.setOnClickListener(this);
        foot.setOnClickListener(this);
        open_linyout.setOnClickListener(this);
        receivingmaterials_linyout.setOnClickListener(this);
        returnsupplies_linyout.setOnClickListener(this);
        surveillance.setOnClickListener(this);
        more_linyout.setOnClickListener(this);
        voicesearch_image.setOnClickListener(this);
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (i == 4) {
                handler.removeCallbacks(runnable);
            } else {
                str = mylist.get(i).toString().trim();
                i++;
                Unlock(str, Mac);
                // 要做的事情
                handler.postDelayed(this, 2000);
            }

        }
    };

    // 开锁方式
    private void Unlock(String position, final String mac) {
        RequestUtils.ClientPost(URLs.ORDER_BASE_URL + "/" + position + "/" + mac, null, new NetCallBack() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                System.out.println("数据请求成功" + result);
            }

            @Override
            public void onMyFailure(Throwable arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.r_back:
                getActivity().finish();
                break;
            //开门
            case R.id.open_linyout:
                Intent intent_emergencyStation = new Intent(getActivity(), EmergencyStationActivity.class);
                intent_emergencyStation.putExtra("mode", "应急开门");
                startActivity(intent_emergencyStation);
                break;
            //领物资
            case R.id.receivingmaterials_linyout:
                Intent intent_OutOfStock = new Intent(getActivity(), MaterialCaptureActivity.class);
                intent_OutOfStock.putExtra("mode", "OutOfStock");
                startActivity(intent_OutOfStock);
                break;
            //还物资
            case R.id.returnsupplies_linyout:
                Intent intent_Warehousing = new Intent(getActivity(), MaterialCaptureActivity.class);
                intent_Warehousing.putExtra("mode", "Warehousing");
                startActivity(intent_Warehousing);
                break;
            //查看视频
            case R.id.monitor:
                Intent intent_Monitor = new Intent(getActivity(), MonitorActivity.class);
                startActivity(intent_Monitor);
                break;
            //视频
            case R.id.surveillance:
                break;
            //更多
            case R.id.more_linyout:

                break;
            //语音搜索
            case R.id.voicesearch_image:
                initSpeech(getActivity());
                break;
            // 步行导航
            case R.id.walkingnavigation:
                //初始化导航数据
                initOverlay();
                startPt = new LatLng(S_mylatitude, S_mylongitude);
                endPt = new LatLng(E_mylatitude, E_mylongitude);
                /*构造导航起终点参数对象*/
                walkParam = new WalkNaviLaunchParam().stPt(startPt).endPt(endPt);
                walkParam.extraNaviMode(0);
                startWalkNavi();
                mBaiduMap.clear();
                break;
//                查看详情
            case R.id.viewdetails:
                mScrollLayout.setToOpen();
                break;
//                驾车导航
            case R.id.driveingnavigation:
                routeplanToNavi(CoordinateType.BD09LL);
                break;
            default:
                break;
        }
    }

    //附近的应急站
    private void NearbyEmergencyStation() {
        slist = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.put("lat", S_mylatitude + "");
        params.put("lng", S_mylongitude + "");
        params.put("pageNum", "1");
        params.put("pageSize", "100");
        params.put("username", PreferenceUtils.getString(getActivity(), "EmergencyStation_username"));
        params.put("platformkey", "app_firecontrol_owner");
        RequestUtils.ClientPost(URLs.NearbyEmergencyStation_URL, params, new NetCallBack() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String msg = jsonObject.getString("msg");
                    if (msg.equals("获取成功")) {
                        String data = jsonObject.getString("data");
                        JSONObject objects = new JSONObject(data);
                        String list = objects.getString("list");
                        JSONArray array = new JSONArray(list);
                        JSONObject object;
                        for (int i = 0; i < array.length(); i++) {
                            StationBean bean = new StationBean();
                            object = (JSONObject) array.get(i);
                            String name = object.getString("name");
                            String address = object.getString("address");
                            String mac = object.getString("mac");
                            String ids = object.getString("ids");
                            String lat = object.getString("lat");
                            String lng = object.getString("lng");

                            bean.setId(ids);
                            bean.setName(name);
                            bean.setAddress(address);
                            bean.setE_mylatitude(Double.parseDouble(lat));
                            bean.setE_mylongitude(Double.parseDouble(lng));
                            bean.setMac(mac);
                            bean.setType(1);

                            bean.setDistance(gps_m(S_mylatitude, S_mylongitude, Double.parseDouble(lat), Double.parseDouble(lng)));

                            // 构建MarkerOption，用于在地图上添加Marker
                            LatLng llA = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            MarkerOptions option = new MarkerOptions().position(llA).icon(bdAs);
                            Marker marker = (Marker) mBaiduMap.addOverlay(option);
                            // 将信息保存
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("marker", bean);
                            marker.setExtraInfo(bundle);
                            mBaiduMap.addOverlays(listoption);
                            slist.add(bean);
                        }
                        stationlistview = view.findViewById(R.id.stationlistview);
                        stationAdapter = new StationAdapter(getActivity(), slist);
                        stationlistview.setAdapter(stationAdapter);

                        stationlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                E_mylatitude = slist.get(position).getE_mylatitude();
                                E_mylongitude = slist.get(position).getE_mylongitude();

                                String ids = slist.get(position).getId();
                                String names = slist.get(position).getName();
                                String addresss = slist.get(position).getAddress();
                                String mac = slist.get(position).getMac();
                                double distances = slist.get(position).getDistance();
                                name.setText(names);
                                address.setText(addresss);
                                distance.setText("距您 " + distances + "m");

//                              ChooseMyLocation(E_mylatitude, E_mylongitude);
                                BottomMenu(names, addresss, distances, ids, mac);
                                mScrollLayout.setVisibility(View.VISIBLE);
                                LatLng llA = new LatLng(E_mylatitude, E_mylongitude);
                                showInfoWindow(llA, names);
                            }
                        });
                    } else {
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(Throwable arg0) {

            }
        });
    }

    //附近的SOS
    private void NearbyEmergencySOS() {
        slistsos = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.put("pageNum", "1");
        params.put("pageSize", "100");
        params.put("unit_code", PreferenceUtils.getString(getActivity(), "unitcode"));
        params.put("username", PreferenceUtils.getString(getActivity(), "EmergencyStation_username"));
        params.put("platformkey", "app_firecontrol_owner");
        RequestUtils.ClientPost(URLs.SOSITEM_URL, params, new NetCallBack() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onMySuccess(String result) {
                if (result == null || result.length() == 0) {
                    return;
                }
                System.out.println("附近SOS数据请求成功" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String msg = jsonObject.getString("msg");
                    if (msg.equals("获取成功")) {
                        String data = jsonObject.getString("data");
                        JSONObject objects = new JSONObject(data);
                        String list = objects.getString("list");
                        JSONArray array = new JSONArray(list);
                        JSONObject object;
                        for (int i = 0; i < array.length(); i++) {
                            StationBean bean = new StationBean();
                            object = (JSONObject) array.get(i);
//                          String lat = object.getString("lat");
//                          String lng = object.getString("lng");

                            //测试
                            String lat = "31.87368";
                            String lng = "118.83358";

                            bean.setName("SOS求救");
                            bean.setAddress("南京市-江宁区-秣周东路12号");
                            bean.setE_mylatitude(Double.parseDouble(lat));
                            bean.setE_mylongitude(Double.parseDouble(lng));
                            bean.setType(2);

                            bean.setDistance(gps_m(S_mylatitude, S_mylongitude, Double.parseDouble(lat), Double.parseDouble(lng)));

                            // 构建MarkerOption，用于在地图上添加Marker
                            LatLng llA = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                            MarkerOptions option = new MarkerOptions().position(llA).icon(bdA);
                            Marker marker = (Marker) mBaiduMap.addOverlay(option);
                            // 将信息保存
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("marker", bean);
                            marker.setExtraInfo(bundle);
                            mBaiduMap.addOverlays(listoption);
                            slistsos.add(bean);
                        }

                        soslistview = view.findViewById(R.id.soslistview);
                        stationAdapter = new StationAdapter(getActivity(), slistsos);
                        soslistview.setAdapter(stationAdapter);

                        soslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                E_mylatitude = slistsos.get(position).getE_mylatitude();
                                E_mylongitude = slistsos.get(position).getE_mylongitude();

                                String names = slistsos.get(position).getName();
                                String addresss = slistsos.get(position).getAddress();

                                LatLng llA = new LatLng(E_mylatitude, E_mylongitude);
                                showInfoWindow(llA, names);

                                // 获得marker中的数据
                                e_stationDialog = new E_StationDialog(getActivity(), names, addresss, clickListener);
                                e_stationDialog.show();
                                mScrollLayout.setVisibility(View.GONE);

                            }
                        });
                    } else {
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyFailure(Throwable arg0) {

            }
        });
    }

    // 返回单位是米
    private double gps_m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 初始化导航起终点Marker
     */
    public void initOverlay() {
        LatLng llA = new LatLng(S_mylatitude, S_mylongitude);
        LatLng llB = new LatLng(E_mylatitude, E_mylongitude);
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdStart)
                .zIndex(9).draggable(true);
        mStartMarker = (Marker) (mBaiduMap.addOverlay(ooA));
        mStartMarker.setDraggable(true);
        MarkerOptions ooB = new MarkerOptions().position(llB).icon(bdEnd)
                .zIndex(5);
        mEndMarker = (Marker) (mBaiduMap.addOverlay(ooB));
        mEndMarker.setDraggable(true);

        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
                if (marker == mStartMarker) {
                    startPt = marker.getPosition();
                } else if (marker == mEndMarker) {
                    endPt = marker.getPosition();
                }
                walkParam.stPt(startPt).endPt(endPt);
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });
    }

    /**
     * 开始步行导航
     */
    private void startWalkNavi() {
        try {
            WalkNavigateHelper.getInstance().initNaviEngine(getActivity(), new IWEngineInitListener() {
                @Override
                public void engineInitSuccess() {
                    routePlanWithWalkParam();

                }

                @Override
                public void engineInitFail() {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发起步行导航算路
     */
    private void routePlanWithWalkParam() {
        WalkNavigateHelper.getInstance().routePlanWithParams(walkParam, new IWRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
            }

            @Override
            public void onRoutePlanSuccess() {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(WalkRoutePlanError error) {
            }

        });

    }

    //底部菜单
    private void BottomMenu(String name, String address, double distance, String id, String mac) {
        slists = new ArrayList<>();
        StationBean beana = new StationBean();
        beana.setType(0);
        slists.add(beana);
        StationBean beanb = new StationBean();
        beanb.setName("防火服");
        beanb.setNumber("2件");
        beanb.setImage_type("1");
        beanb.setType(1);
        slists.add(beanb);
        StationBean beanc = new StationBean();
        beanc.setName("灭火器");
        beanc.setNumber("2件");
        beanc.setImage_type("2");
        beanc.setType(1);
        slists.add(beanc);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        bottomMenuAdapter = new BottomMenuAdapter(getActivity(), slists, name, address, distance, id, mac, m_Handler);
        listView.setAdapter(bottomMenuAdapter);
    }

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
//                if (foot.getVisibility() == View.VISIBLE)
//                foot.setVisibility(View.GONE);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
//                foot.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    //快速找到所需要的站点位置
    private void ChooseMyLocation(double la, double lo) {
        // 开启定位功能
        mBaiduMap.setMyLocationEnabled(true);
        // 构造定位数据
        MyLocationData locationData = new MyLocationData.Builder()
                .latitude(la)
                .longitude(lo)
                .build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locationData);
        // 设置定位图层的配置，设置图标跟随状态（图标一直在地图中心）
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        mBaiduMap.setMyLocationConfigeration(config);
        // 当不需要定位时，关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);

        StationBean bean = new StationBean();
        bean.setE_mylatitude(E_mylatitude);
        bean.setE_mylongitude(E_mylongitude);
        // 将信息保存
        Bundle bundle = new Bundle();
        bundle.putSerializable("marker", bean);
    }

    @SuppressLint("HandlerLeak")
    private Handler m_Handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                //一键开门
                case 5859590:
                    i = 0;
                    Bundle bundle = message.getData();
                    Mac = bundle.getString("mac");
                    handler.postDelayed(runnable, 2000);// 每两秒执行一次runnable.
                    break;
                //还物资
                case 5859591:
                    Intent intent_Warehousing = new Intent(getActivity(), MaterialCaptureActivity.class);
                    intent_Warehousing.putExtra("mode", "Warehousing");
                    startActivity(intent_Warehousing);
                    break;
                //领物质
                case 5859592:
                    Intent intent_OutOfStock = new Intent(getActivity(), MaterialCaptureActivity.class);
                    intent_OutOfStock.putExtra("mode", "OutOfStock");
                    startActivity(intent_OutOfStock);
                    break;
                //报损
                case 5859593:
                    Intent intent_Reportloss = new Intent(getActivity(), MaterialCaptureActivity.class);
                    intent_Reportloss.putExtra("mode", "Reportloss");
                    startActivity(intent_Reportloss);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 显示弹出窗
     */
    private void showInfoWindow(LatLng ll, String name) {
        //创建InfoWindow展示的view
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.infowindow_item, null);
        TextView tvCount = contentView.findViewById(R.id.tv_count);
        tvCount.setText(name);
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow infoWindow = new InfoWindow(contentView, ll, -80);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(infoWindow);

    }

    private boolean hasBasePhoneAuth() {
        PackageManager pm = getActivity().getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, getActivity().getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //============驾车导航===================
    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void initNavi() {
        // 申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
        BaiduNaviManagerFactory.getBaiduNaviManager().init(getActivity(),
                mSDCardPath, APP_FOLDER_NAME, new IBaiduNaviManager.INaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        String result;
                        if (0 == status) {
                            result = "key校验成功!";
                        } else {
                            result = "key校验失败, " + msg;
                        }
                    }

                    @Override
                    public void initStart() {
                    }

                    @Override
                    public void initSuccess() {
                        hasInitSuccess = true;
                        // 初始化tts
                        initTTS();
                    }

                    @Override
                    public void initFailed() {
                    }
                });

    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initTTS() {
        // 使用内置TTS
        BaiduNaviManagerFactory.getTTSManager().initTTS(getActivity(),
                getSdcardDir(), APP_FOLDER_NAME, NormalUtils.getTTSAppID());
        // 注册同步内置tts状态回调
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedListener(
                new IBNTTSManager.IOnTTSPlayStateChangedListener() {
                    @Override
                    public void onPlayStart() {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayStart");
                    }

                    @Override
                    public void onPlayEnd(String speechId) {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayEnd");
                    }

                    @Override
                    public void onPlayError(int code, String message) {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayError");
                    }
                }
        );

        // 注册内置tts 异步状态消息
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedHandler(
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("BNSDKDemo", "ttsHandler.msg.what=" + msg.what);
                        int type = msg.what;
                        switch (type) {
                            case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                                break;
                            }
                            case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                                break;
                            }
                            default:
                                break;
                        }
                    }
                }
        );
    }

    private void routeplanToNavi(final int coType) {
        if (!hasInitSuccess) {
            Toast.makeText(getActivity(), "还未初始化!", Toast.LENGTH_SHORT).show();
        }

        BNRoutePlanNode sNode = new BNRoutePlanNode(S_mylongitude, S_mylatitude, "", "", coType);
        BNRoutePlanNode eNode = new BNRoutePlanNode(E_mylongitude, E_mylatitude, "", "", coType);
        switch (coType) {
            case CoordinateType.BD09LL: {
                sNode = new BNRoutePlanNode(S_mylongitude, S_mylatitude, "", "", coType);
                eNode = new BNRoutePlanNode(E_mylongitude, E_mylatitude, "", "", coType);
                break;
            }
            default:
                break;
        }

        mStartNode = sNode;
        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
        list.add(sNode);
        list.add(eNode);

        BaiduNaviManagerFactory.getRoutePlanManager().routeplanToNavi(
                list,
                IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                null,
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START:
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                                Intent intent = new Intent(getActivity(),
                                        DemoGuideActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(ROUTE_PLAN_NODE, mStartNode);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            default:
                                // nothing
                                break;
                        }
                    }
                });
    }
    //=================================================================================

    /**
     * Android6.0之后需要动态申请权限
     */
    private void RequestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {
            isPermissionRequested = true;
            ArrayList<String> permissions = new ArrayList<>();
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ArCameraView.WALK_AR_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getActivity(), "没有相机权限,请打开后重试", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(getActivity(), "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        }
    }

    //语音搜索

    /**
     * 初始化语音识别
     */
    public void initSpeech(final Context context) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(getActivity(), null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音返回的result为识别后的汉字,直接赋值到TextView上即可
                    String result = parseVoice(recognizerResult.getResultString());
//                    search_edit.setText(result);
                    mBaiduMap.clear();

                    if (result.equals("消防应急站") || result.equals("救援") || result.equals("消防")) {
                        NearbyEmergencyStation();
                    }
                    if (result.equals("医疗应急站") || result.equals("医疗")) {
                        NearbyEmergencyStation();
                    }
                    if (result.equals("SOS求救信息") || result.equals("SOS")) {
                        NearbyEmergencySOS();
                    }
                    if (result.equals("全部信息") || result.equals("全部")) {
                        NearbyEmergencySOS();
                        NearbyEmergencyStation();
                        NearbyEmergencySOS();
                    }
                    if (result.equals("导航到离我最近的应急站") || result.equals("去我最近的应急站") || result.equals("最近的应急站") || result.equals("导航到去我最近的应急站") || result.equals("去离我最近的应急站") || result.equals("去最近的应急站") || result.equals("导航去最近的应急站") || result.equals("离我最近的应急站")) {
                        E_mylatitude = slist.get(0).getE_mylatitude();
                        E_mylongitude = slist.get(0).getE_mylongitude();
                        routeplanToNavi(CoordinateType.BD09LL);
                    }

                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();
    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        EmergencyRescueActivity.Voice voiceBean = gson.fromJson(resultString, EmergencyRescueActivity.Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<EmergencyRescueActivity.Voice.WSBean> ws = voiceBean.ws;
        for (EmergencyRescueActivity.Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {
        public ArrayList<EmergencyRescueActivity.Voice.WSBean> ws;

        public class WSBean {
            public ArrayList<EmergencyRescueActivity.Voice.CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }

    private void requestPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission = ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.LOCATION_HARDWARE, Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_SETTINGS, Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS}, 0x0010);
                }

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                //步行导航
                case R.id.walknavigation:
                    //初始化导航数据
                    initOverlay();
                    startPt = new LatLng(S_mylatitude, S_mylongitude);
                    endPt = new LatLng(E_mylatitude, E_mylongitude);
                    /*构造导航起终点参数对象*/
                    walkParam = new WalkNaviLaunchParam().stPt(startPt).endPt(endPt);
                    walkParam.extraNaviMode(0);
                    startWalkNavi();
                    mBaiduMap.clear();
                    break;
                //驾车导航
                case R.id.drivenavigation:
                    routeplanToNavi(CoordinateType.BD09LL);
                    break;
                // 取消
                case R.id.canles:
                    e_stationDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
}
