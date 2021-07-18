package com.example.havadurumu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;
import de.hdodenhof.circleimageview.CircleImageView;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout mySwipeRefreshLayout;
    private CircleImageView circleImageView1;
    private CircleImageView circleImageView2;
    private CircleImageView circleImageView3;
    TextView sehirismi;
    TextView countrydegree;
    TextView accuwrite;
    TextView ibmwrite;
    TextView openwriter;
    TextView degree1_1;
    TextView degree2_1;
    TextView degree3_1;
    TextView mic;
    public int x=0,y=0,z=0;
    String key;
    int []celciusMax=new int[5];
    int []celciusMin=new int[5];
    int []celciusMax1=new int[5];
    int []celciusMin1=new int[5];
    String []celciusMaxOpen=new String[5];
    String []celciusMinOpen=new String[5];
    String []description=new String[5];
    String []description1=new String[5];
    String []descOpen=new String[5];
    public String paralel;
    public String meridyen;
    Boolean visibilty=false;
    Boolean kontrol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleImageView1 = findViewById(R.id.accu);
        circleImageView2 = findViewById(R.id.ibm);
        circleImageView3 = findViewById(R.id.openweather);
        sehirismi = findViewById(R.id.sehirismi);
        countrydegree = findViewById(R.id.countrydegree);
        accuwrite = findViewById(R.id.accuwrite);
        ibmwrite = findViewById(R.id.ibmwrite);
        openwriter = findViewById(R.id.openwriter);
        degree1_1 = findViewById(R.id.degree1_1);
        degree2_1 = findViewById(R.id.degree2_1);
        degree3_1 = findViewById(R.id.degree3_1);
        mic=findViewById(R.id.mic);
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                        if (paralel !=null) {
                            Visibility(visibilty);
                        } else {
                            mySwipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }
        );
        Intent intent =getIntent();
        kontrol=intent.getBooleanExtra("kontrol",false);
        String sehir=intent.getStringExtra("sehir");
        String cityName=intent.getStringExtra("cityName");
        String citynameUrl;
        if(cityName!=null || sehir!=null) {
            if (cityName != null) {
                citynameUrl = "https://maps.googleapis.com/maps/api/geocode/json?&address=" + cityName + "&key=AIzaSyC8EcQvkmzlmHkLfvcQMvqG0sGBJ63kibM";
            } else if (sehir != null) {
                citynameUrl = "https://maps.googleapis.com/maps/api/geocode/json?&address=" + sehir + "&key=AIzaSyC8EcQvkmzlmHkLfvcQMvqG0sGBJ63kibM";

            }
            else {
                citynameUrl="";
                Toast.makeText(this, "Lütfen Konumunuzu Belirtin", Toast.LENGTH_SHORT).show();
            }
            ApiRequest apiRequest4 = new ApiRequest();
            apiRequest4.getData(MainActivity.this, citynameUrl, new ApiRequest.VolleyResponseListener() {

                @Override
                public void onError(String Error) {

                }

                @Override
                public void onSuccess(JSONObject jsonObject) {

                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        JSONObject geo = jsonArray.getJSONObject(0).getJSONObject("geometry");
                        sehirismi.setText(jsonArray.getJSONObject(0).getString("formatted_address"));
                        JSONObject latlng = geo.getJSONObject("bounds").getJSONObject("northeast");
                        BigDecimal bd = new BigDecimal(latlng.getDouble("lat")).setScale(2, RoundingMode.HALF_UP);
                        BigDecimal bd1 = new BigDecimal(latlng.getDouble("lng")).setScale(2, RoundingMode.HALF_UP);
                        paralel = String.valueOf(bd.doubleValue());
                        meridyen = String.valueOf(bd1.doubleValue());
                        visibilty=true;
                        vericek();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Şehir bulunamadı",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }


            mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VoiceListener.class);
                startActivity(intent);
            }
        });
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if(paralel==null && kontrol==true) {
                            locationGet();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        Visibility(visibilty);


    }
    public void locationGet(){
        MyLocationListener lc = new MyLocationListener();
        lc.startLocationUpdates(getApplicationContext(), MainActivity.this, new MyLocationListener.loc() {
            @Override
            public void onError(String Error) {

            }

            @Override
            public void onSuccess(Double paralel1, Double meridyen1, Boolean b) {
                geoCode(paralel1, meridyen1, b);
                vericek();
            }
        });

    }
    public void vericek() {
        if (visibilty==true) {
            mic.setVisibility(View.VISIBLE);

            ApiRequest apiRequest = new ApiRequest();
            String urlLocationKey = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=C82m8nLhE97Jyyn15iIpVNrfyYv9pmTu&q=" + paralel + "%2C" + meridyen;
            apiRequest.getData1(MainActivity.this, urlLocationKey, new ApiRequest.VolleyResponseListener1() {

                @Override
                public void onError(String Error) {
                    System.out.println(Error);
                }

                @Override
                public void onSuccess(JSONArray jsonArray) {

                    try {
                        JSONObject keyobj = jsonArray.getJSONObject(0);
                        key = keyobj.getString("Key");
                        String city = keyobj.getString("LocalizedName");
                        if(kontrol==true){
                            sehirismi.setText(city);
                        }
                        String accuweather = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/" + key + "?apikey=C82m8nLhE97Jyyn15iIpVNrfyYv9pmTu&language=tr-tr";
                        ApiRequest apiRequest3 = new ApiRequest();
                        apiRequest3.getData(MainActivity.this, accuweather, new ApiRequest.VolleyResponseListener() {

                            @Override
                            public void onError(String Error) {
                                System.out.println(Error);
                            }

                            @Override
                            public void onSuccess(JSONObject jsonObject) {

                                try {

                                    JSONArray dailyForecasts = jsonObject.getJSONArray("DailyForecasts");
                                    for (int i = 0; i < dailyForecasts.length(); i++) {
                                        JSONObject s = dailyForecasts.getJSONObject(i);
                                        int fahrenheitMax = s.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
                                        int fahrenheitMin = s.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
                                        description1[i] = s.getJSONObject("Day").getString("IconPhrase");
                                        celciusMax[i] = (int) ((fahrenheitMax - 32) / 1.8000);
                                        celciusMin[i] = (int) ((fahrenheitMin - 32) / 1.8000);
                                        degree1_1.setText(String.valueOf(celciusMin[0]) + "/" + String.valueOf(celciusMax[0]) + "°C");
                                        accuwrite.setText(description1[0].toUpperCase());
                                        x = celciusMax[0];

                                    }


                                    circleImageView1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(MainActivity.this, AccuActivity.class);
                                            intent.putExtra("max", celciusMax);
                                            intent.putExtra("min", celciusMin);
                                            intent.putExtra("desc", description1);
                                            startActivity(intent);
                                        }
                                    });
                                    //System.out.println(jsonObject.getJSONArray("DailyForecasts"));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });



            ApiRequest apiRequest1 = new ApiRequest();
            String ibmweather = "https://api.weather.com/v3/wx/forecast/daily/7day?geocode=" + paralel + "," + meridyen + "&format=json&units=e&language=tr-TR&apiKey=2b6ed19f3d474152aed19f3d4791527d";
            apiRequest1.getData(MainActivity.this, ibmweather, new ApiRequest.VolleyResponseListener() {

                @Override
                public void onError(String Error) {
                    System.out.println(Error);
                }

                @Override
                public void onSuccess(JSONObject jsonObject) {
                    try {
                        for (int i = 0; i < 5; i++) {
                            int fahrenheitMax = jsonObject.getJSONArray("calendarDayTemperatureMax").getInt(i);
                            int fahrenheitMin = jsonObject.getJSONArray("calendarDayTemperatureMin").getInt(i);
                            description[i] = jsonObject.getJSONArray("narrative").getString(i).split(Pattern.quote("."))[0];
                            celciusMax1[i] = (int) ((fahrenheitMax - 32) / 1.8000);
                            celciusMin1[i] = (int) ((fahrenheitMin - 32) / 1.8000);
                            degree2_1.setText(String.valueOf(celciusMin1[0]) + "/" + String.valueOf(celciusMax1[0]) + "°C");
                            y = celciusMax1[0];
                            ibmwrite.setText(description[0].toUpperCase());
                            ibmwrite.setMovementMethod(new ScrollingMovementMethod());

                        }


                        circleImageView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, ibmActivity.class);
                                intent.putExtra("max1", celciusMax1);
                                intent.putExtra("min1", celciusMin1);
                                intent.putExtra("desc1", description);
                                startActivity(intent);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            ApiRequest apiRequest2 = new ApiRequest();
            String openweather = "https://api.openweathermap.org/data/2.5/onecall?lat=" + paralel + "&lon=" + meridyen + "&lang=tr&exclude=hourly,current,minutely,alerts&units=metric&appid=fb684ab4d775bc6db98d13573c8596da";
            apiRequest2.getData(MainActivity.this, openweather, new ApiRequest.VolleyResponseListener() {

                @Override
                public void onError(String Error) {
                    System.out.println(Error);
                }

                @Override
                public void onSuccess(JSONObject jsonObject) {
                    try {
                        JSONArray dailyProperty = jsonObject.getJSONArray("daily");
                        for (int i = 0; i < 5; i++) {
                            JSONObject m = dailyProperty.getJSONObject(i);
                            JSONObject sicaklik = m.getJSONObject("temp");

                            JSONArray aciklama = m.getJSONArray("weather");
                            JSONObject desc = aciklama.getJSONObject(0);
                            descOpen[i] = desc.getString("description");
                            celciusMaxOpen[i] = sicaklik.getString("max").split(Pattern.quote("."))[0];
                            celciusMinOpen[i] = sicaklik.getString("min").split(Pattern.quote("."))[0];

                            degree3_1.setText(celciusMinOpen[0] + "/" + celciusMaxOpen[0] + "°C");
                            openwriter.setText(descOpen[0].toUpperCase());
                            z = Integer.parseInt(celciusMaxOpen[0]);
                            setOptimumValue(x, y, z);
                            Visibility(visibilty);

                        }
                        circleImageView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, OpenActivity.class);
                                intent.putExtra("maxOpen", celciusMaxOpen);
                                intent.putExtra("minOpen", celciusMinOpen);
                                intent.putExtra("desc2", descOpen);
                                startActivity(intent);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }

    public void geoCode(Double paralel1, Double meridyen1, Boolean b) {

        paralel=paralel1.toString();
        meridyen=meridyen1.toString();
        visibilty=b;
    }

    private void setOptimumValue(int x, int y, int z) {
        int a=(x+y+z)/3;
        countrydegree.setText(String.valueOf(a)+"°C");
    }

    public void Visibility(Boolean visibility){

        if(visibility==false){
            circleImageView1.setVisibility(CircleImageView.INVISIBLE);
            circleImageView2.setVisibility(CircleImageView.INVISIBLE);
            circleImageView3.setVisibility(CircleImageView.INVISIBLE);
            sehirismi.setVisibility(TextView.INVISIBLE);
            countrydegree.setVisibility(TextView.INVISIBLE);
            accuwrite.setVisibility(TextView.INVISIBLE);
            ibmwrite.setVisibility(TextView.INVISIBLE);
            openwriter.setVisibility(TextView.INVISIBLE);
            degree1_1.setVisibility(TextView.INVISIBLE);
            degree2_1.setVisibility(TextView.INVISIBLE);
            degree3_1.setVisibility(TextView.INVISIBLE);


        }
        else if(visibility==true){
            mic.setVisibility(View.VISIBLE);
            circleImageView1.setVisibility(CircleImageView.VISIBLE);
            circleImageView2.setVisibility(CircleImageView.VISIBLE);
            circleImageView3.setVisibility(CircleImageView.VISIBLE);
            sehirismi.setVisibility(TextView.VISIBLE);
            countrydegree.setVisibility(TextView.VISIBLE);
            accuwrite.setVisibility(TextView.VISIBLE);
            ibmwrite.setVisibility(TextView.VISIBLE);
            openwriter.setVisibility(TextView.VISIBLE);
            degree1_1.setVisibility(TextView.VISIBLE);
            degree2_1.setVisibility(TextView.VISIBLE);
            degree3_1.setVisibility(TextView.VISIBLE);

            mySwipeRefreshLayout.setRefreshing(false);
        }

    }
}
