package io.tienjintwopiece;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by BenGirardeau on 4/16/16.
 */
public class ShowResults extends Activity implements AsyncResponse {
    APICalls asyncTask = new APICalls(this);
    RelativeLayout relativeLayout;
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        asyncTask.delegate = this;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//        String zipcode = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Bundle extras = intent.getExtras();
        String requests = extras.getString("REQUESTS");
        String location = extras.getString("LOCATION");
        relativeLayout = (RelativeLayout)findViewById(R.id.mainlayout);
        new APICalls(this).execute(requests, location);
    }

    public void processFinish(String output) throws IOException, JSONException {
        TextView textView = new TextView(this);
        JSONObject parser = new JSONObject(output);
        if (parser.has("businesses")){
            JSONArray businesses = parser.getJSONArray("businesses");
            int count = (businesses).length();
            String names = "";
            for (int i = 0; i < count; i++) {
                JSONObject business = (JSONObject) businesses.get(i);
                String name = business.getString("name");
                textView.setText(name);
                names += "\n\n " + name;
            }
            if (names.length() == 0) {
                String result = "Sorry, there are no businesses that match your search";
                textView.setText(result);
                System.out.println("HIIII1");
            } else {
                textView.setText(names);
            }
        }
        System.out.println(parser);
        System.out.println(parser.has("error"));
        if (parser.has("error")) {
            System.out.println("HIIII2");
            JSONObject error = parser.getJSONObject("error");
            if (error.getString("text").length() > 0) {
                textView.setText(error.getString("text"));
            }
        }
        Log.e("did i get here", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        setContentView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.output) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}