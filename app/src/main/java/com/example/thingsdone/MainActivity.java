package com.example.thingsdone;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView lst;
    private RequestQueue req;
    private ArrayList<String> lngList;
    String taskLs, stateLs, dateLs, timeLs, itemLs, idLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst=findViewById(R.id.lst);
        lngList = new ArrayList<>();
        req= Volley.newRequestQueue(this);

        ImageView myImage = (ImageView) findViewById(R.id.mainBackGView);
        RotateAnimation rotateAnimation = new RotateAnimation(-1, 1, 100, 500,
                100, 500);
        rotateAnimation.setRepeatMode(rotateAnimation.REVERSE);
        rotateAnimation.setRepeatCount(rotateAnimation.INFINITE);
        rotateAnimation.setDuration(5500);
        TranslateAnimation translateAnimation = new TranslateAnimation(28,40,-7,7);
        translateAnimation.setRepeatMode(translateAnimation.REVERSE);
        translateAnimation.setRepeatCount(translateAnimation.INFINITE);
        translateAnimation.setDuration(8000);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        myImage.setAnimation(animationSet);
        animationSet.start();


        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);

        AnimationDrawable background_layout = (AnimationDrawable) constraintLayout.getBackground();
        background_layout.setEnterFadeDuration(3500);
        background_layout.setExitFadeDuration(7000);
        background_layout.start();
    }

    public void recuperar(View v){
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        //txtv.setText("");
        String url= "https//ejemploderequest/exec?func=ReadAll";
        JsonArrayRequest requerido = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int f=0;f< response.length();f++){
                            try{
                                JSONObject objeto = new JSONObject(response.get(f).toString());
                                taskLs = objeto.getString("description");
                                stateLs = objeto.getString("state");
                                dateLs = objeto.getString("date");
                                timeLs = objeto.getString("time");
                                idLs = objeto.getString("id");
                                itemLs = taskLs + " | " + dateLs + " | " + timeLs + " | " + stateLs + " | " + idLs;
                                lngList.add(itemLs);
                                // + " | " + stateLs[f] + " | " + dateLs[f] + " | " + timeLs[f]

                                /*txtv.append("ID:" + objeto.getString("id")+"\n");
                                txtv.append("Date:" + String.format(objeto.getString("date")) +"\n");
                                txtv.append("Time:" + objeto.getString("time")+"\n");
                                txtv.append("Description:" + objeto.getString("description")+"\n");
                                txtv.append("Subject:" + objeto.getString("subject")+"\n");
                                txtv.append("Institucion:" + objeto.getString("institucion")+"\n");
                                txtv.append("Priority:" + objeto.getString("priority")+"\n");
                                txtv.append("State:" + objeto.getString("state")+"\n");
                                txtv.append("Hoja:" + objeto.getString("sheetH")+"\n");*/
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lst.getContext(), android.R.layout.simple_list_item_1, lngList);
                        lst.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        req.add(requerido);
    }
}