package com.example.anurag.involve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
 private Button button;
    public static final String MY_JSON ="MY_JSON";
    private TextView textView;
    private Button button2;
    private final static String file="http://10.0.2.2/product-manager/API/v1/products";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2=(Button)findViewById(R.id.button2);
    textView=(TextView)findViewById(R.id.textView);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              getJSON(file);
            }
        });
       button2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showParseActivity();
           }
       });
    }
    private void showParseActivity() {
        Intent intent = new Intent(this, ParseJSON.class);
        intent.putExtra(MY_JSON,textView.getText().toString());
        startActivity(intent);
    }
    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait...",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.d("Shit","Shit");
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                        Log.d("Shit","Shit");
                    }
                      Log.d("shit",sb.toString().trim());
                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                textView.setText(s);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }
}
