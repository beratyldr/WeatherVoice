package com.example.havadurumu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OpenActivity extends AppCompatActivity {
    TextView opendegree1;
    TextView opendegree2;
    TextView opendegree3;
    TextView opendegree4;
    TextView opendegree5;
    TextView opendate1;
    TextView opendate2;
    TextView opendate3;
    TextView opendate4;
    TextView opendate5;
    TextView opendesc1;
    TextView opendesc2;
    TextView opendesc3;
    TextView opendesc4;
    TextView opendesc5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openweather);
        opendegree1=findViewById(R.id.opendegree1);
        opendegree2=findViewById(R.id.opendegree2);
        opendegree3=findViewById(R.id.opendegree3);
        opendegree4=findViewById(R.id.opendegree4);
        opendegree5=findViewById(R.id.opendegree5);
        opendate1=findViewById(R.id.opendate1);
        opendate2=findViewById(R.id.opendate2);
        opendate3=findViewById(R.id.opendate3);
        opendate4=findViewById(R.id.opendate4);
        opendate5=findViewById(R.id.opendate5);
        opendesc1=findViewById(R.id.opendesc1);
        opendesc2=findViewById(R.id.opendesc2);
        opendesc3=findViewById(R.id.opendesc3);
        opendesc4=findViewById(R.id.opendesc4);
        opendesc5=findViewById(R.id.opendesc5);

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


        Intent intent =getIntent();
        String []max=intent.getStringArrayExtra("maxOpen");
        String []min=intent.getStringArrayExtra("minOpen");
        String []desc=intent.getStringArrayExtra("desc2");

        opendegree1.setText(String.valueOf(min[0])+"/"+String.valueOf(max[0])+"°C");
        opendegree2.setText(String.valueOf(min[1])+"/"+String.valueOf(max[1])+"°C");
        opendegree3.setText(String.valueOf(min[2])+"/"+String.valueOf(max[2])+"°C");
        opendegree4.setText(String.valueOf(min[3])+"/"+String.valueOf(max[3])+"°C");
        opendegree5.setText(String.valueOf(min[4])+"/"+String.valueOf(max[4])+"°C");

        opendesc1.setText(desc[0]);
        opendesc2.setText(desc[1]);
        opendesc3.setText(desc[2]);
        opendesc4.setText(desc[3]);
        opendesc5.setText(desc[4]);

    }
}
