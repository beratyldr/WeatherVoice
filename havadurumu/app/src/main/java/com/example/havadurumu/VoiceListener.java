package com.example.havadurumu;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceListener extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    String konusma="Bugün hava nasıl";
    Boolean kontrol;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speak();
    }

    private void speak(){
        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Bir cümle söyleyin");

        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //System.out.println(result.get(0));
                    if(result.get(0).equals(konusma)){
                        kontrol=true;
                        Intent intent=new Intent(VoiceListener.this,MainActivity.class);
                        intent.putExtra("kontrol",kontrol);
                        startActivity(intent);

                    }
                    else{
                        //Toast.makeText(this, "Lütfen şunu söyleyin 'Bugün Hava Nasıl' veya Şehir ismi Söyleyiniz", Toast.LENGTH_SHORT).show();
                        kontrol=false;
                        Intent intent=new Intent(VoiceListener.this,MainActivity.class);
                        intent.putExtra("kontrol",kontrol);
                        intent.putExtra("sehir",result.get(0));
                        startActivity(intent);
                    }

                }
                break;
            }
        }
    }
}
