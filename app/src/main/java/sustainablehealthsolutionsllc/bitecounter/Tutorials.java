package sustainablehealthsolutionsllc.bitecounter;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This class helps the tutorial xml exist. It has no other purposes currently.
 */
public class Tutorials extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Tutorials.this, BiteCounter.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_counter, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_graph:
                Intent intent = new Intent(Tutorials.this, Graph.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_aboutUs:
                Intent intentUs = new Intent(Tutorials.this, AboutUs.class);
                startActivity(intentUs);
                finish();
                return true;
            case R.id.action_counter:
                Intent intentT = new Intent(Tutorials.this, BiteCounter.class);
                startActivity(intentT);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentS = new Intent(Tutorials.this, SettingsActivity.class);
                startActivity(intentS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
