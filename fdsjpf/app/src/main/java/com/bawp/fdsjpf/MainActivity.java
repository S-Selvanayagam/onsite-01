package com.bawp.fdsjpf;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonParse = findViewById(R.id.button);
        image = findViewById(R.id.imageView);
        Button button = findViewById(R.id.button3);
        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                jsonParse();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to get the image from the ImageView (say iv)
                BitmapDrawable draw = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/YourFolderName");
                dir.mkdirs();
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                //outStream.flush();
                //outStream.close();
            }
        });

    }
        private void jsonParse(){

                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "https://randomized-avatar-api.p.rapidapi.com/getRandomAvatar";
//                String url3 = "https://res.cloudinary.com/dq8bxduza/image/upload/v1660753474/randomized-avatar-api/dsjglknasdhgfrku8m5y.png";
//                Picasso.get().load(url3).into(image);
                StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject parentObject = new JSONObject(response);
                                    String url = parentObject.getString("imageUrl");
                                    Log.d("guiii",url);
                                    //URL url2 = new URL(url);
                                    //String url3 = "https://res.cloudinary.com/dq8bxduza/image/upload/v1660753474/randomized-avatar-api/dsjglknasdhgfrku8m5y.png";
                                    Picasso.get().load(url).into(image);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // response
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("ERROR","error => "+error.toString());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("X-RapidAPI-Key", "4fc31e6f37mshf0455c0b3ec2c65p1dce96jsna4c2ac4ab7d0");
                        params.put("X-RapidAPI-Host", "randomized-avatar-api.p.rapidapi.com");

                        return params;
                    }
                };
                queue.add(getRequest);
        }


    }
