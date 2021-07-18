package com.example.havadurumu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class AccuActivity extends AppCompatActivity {
    TextView accudegree1;
    TextView accudegree2;
    TextView accudegree3;
    TextView accudegree4;
    TextView accudegree5;
    TextView accudate1;
    TextView accudate2;
    TextView accudate3;
    TextView accudate4;
    TextView accudate5;
    TextView accudesc1;
    TextView accudesc2;
    TextView accudesc3;
    TextView accudesc4;
    TextView accudesc5;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accuweather);
        accudegree1=findViewById(R.id.accudegree1);
        accudegree2=findViewById(R.id.accudegree2);
        accudegree3=findViewById(R.id.accudegree3);
        accudegree4=findViewById(R.id.accudegree4);
        accudegree5=findViewById(R.id.accudegree5);
        accudate1=findViewById(R.id.accudate1);
        accudate2=findViewById(R.id.accudate2);
        accudate3=findViewById(R.id.accudate3);
        accudate4=findViewById(R.id.accudate4);
        accudate5=findViewById(R.id.accudate5);
        accudesc1=findViewById(R.id.accudesc1);
        accudesc2=findViewById(R.id.accudesc2);
        accudesc3=findViewById(R.id.accudesc3);
        accudesc4=findViewById(R.id.accudesc4);
        accudesc5=findViewById(R.id.accudesc5);

        Date date=new Date();
        Calendar c = Calendar. getInstance();
        c.setTime(date);
        String dayWeekText = new SimpleDateFormat("EEEE"). format(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        String dayWeekText1 = new SimpleDateFormat("EEEE"). format(date);
        c.add(Calendar.DATE, 2);
        date = c.getTime();
        String dayWeekText2 = new SimpleDateFormat("EEEE"). format(date);
        c.add(Calendar.DATE, 3);
        date = c.getTime();
        String dayWeekText3 = new SimpleDateFormat("EEEE"). format(date);
        c.add(Calendar.DATE, 4);
        date = c.getTime();
        String dayWeekText4 = new SimpleDateFormat("EEEE"). format(date);

        accudate1.setText(dayWeekText);
        accudate2.setText(dayWeekText1);
        accudate3.setText(dayWeekText2);
        accudate4.setText(dayWeekText3);
        accudate5.setText(dayWeekText4);

        Intent intent =getIntent();
        String []desc= intent.getStringArrayExtra("desc");
        int []a=intent.getIntArrayExtra("max");
        int []b=intent.getIntArrayExtra("min");

        accudegree1.setText(String.valueOf(b[0])+"/"+String.valueOf(a[0])+"°C");
        accudegree2.setText(String.valueOf(b[1])+"/"+String.valueOf(a[1])+"°C");
        accudegree3.setText(String.valueOf(b[2])+"/"+String.valueOf(a[2])+"°C");
        accudegree4.setText(String.valueOf(b[3])+"/"+String.valueOf(a[3])+"°C");
        accudegree5.setText(String.valueOf(b[4])+"/"+String.valueOf(a[4])+"°C");

        accudesc1.setText(desc[0]);
        accudesc2.setText(desc[1]);
        accudesc3.setText(desc[2]);
        accudesc4.setText(desc[3]);
        accudesc5.setText(desc[4]);

    }

}
