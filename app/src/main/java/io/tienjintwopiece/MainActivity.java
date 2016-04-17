package io.tienjintwopiece;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    public static final String EXTRA_MESSAGE = "io.tienjintwopiece.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getRecommendations(View view){
        Intent intent = new Intent(this, ShowResults.class);
        Bundle extras = new Bundle();
        EditText editText = (EditText)findViewById(R.id.request_form);
        EditText editText2 = (EditText)findViewById(R.id.location_form);
        String message1 = editText.getText().toString();
        String message2 = editText2.getText().toString();
        extras.putString("REQUESTS", message1);
        extras.putString("LOCATION", message2);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
