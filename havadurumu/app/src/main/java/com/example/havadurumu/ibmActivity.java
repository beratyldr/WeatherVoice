package com.example.havadurumu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ibmActivity extends AppCompatActivity {
    TextView ibmdegree1;
    TextView ibmdegree2;
    TextView ibmdegree3;
    TextView ibmdegree4;
    TextView ibmdegree5;
    TextView ibmdate1;
    TextView ibmdate2;
    TextView ibmdate3;
    TextView ibmdate4;
    TextView ibmdate5;
    TextView ibmdesc1;
    TextView ibmdesc2;
    TextView ibmdesc3;
    TextView ibmdesc4;
    TextView ibmdesc5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ibm);
        ibmdegree1=findViewById(R.id.ibmdegree1);
        ibmdegree2=findViewById(R.id.ibmdegree2);
        ibmdegree3=findViewById(R.id.ibmdegree3);
        ibmdegree4=findViewById(R.id.ibmdegree4);
        ibmdegree5=findViewById(R.id.ibmdegree5);

        ibmdate1=findViewById(R.id.ibmdate1);
        ibmdate2=findViewById(R.id.ibmdate2);
        ibmdate3=findViewById(R.id.ibmdate3);
        ibmdate4=findViewById(R.id.ibmdate4);
        ibmdate5=findViewById(R.id.ibmdate5);

        ibmdesc1=findViewById(R.id.ibmdesc1);
        ibmdesc2=findViewById(R.id.ibmdesc2);
        ibmdesc3=findViewById(R.id.ibmdesc3);
        ibmdesc4=findViewById(R.id.ibmdesc4);
        ibmdesc5=findViewById(R.id.ibmdesc5);

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


        ibmdate1.setText(dayWeekText);
        ibmdate2.setText(dayWeekText1);
        ibmdate3.setText(dayWeekText2);
        ibmdate4.setText(dayWeekText3);
        ibmdate5.setText(dayWeekText4);


        Intent intent =getIntent();
        String []desc=intent.getStringArrayExtra("desc1");
        int []a=intent.getIntArrayExtra("max1");
        int []b=intent.getIntArrayExtra("min1");

        ibmdegree1.setText(String.valueOf(b[0])+"/"+String.valueOf(a[0])+"°C");
        ibmdegree2.setText(String.valueOf(b[1])+"/"+String.valueOf(a[1])+"°C");
        ibmdegree3.setText(String.valueOf(b[2])+"/"+String.valueOf(a[2])+"°C");
        ibmdegree4.setText(String.valueOf(b[3])+"/"+String.valueOf(a[3])+"°C");
        ibmdegree5.setText(String.valueOf(b[4])+"/"+String.valueOf(a[4])+"°C");


        ibmdesc1.setText(desc[0]);
        ibmdesc2.setText(desc[1]);
        ibmdesc3.setText(desc[2]);
        ibmdesc4.setText(desc[3]);
        ibmdesc5.setText(desc[4]);

    }
}
