package sustainablehealthsolutionsllc.bitecounter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * This class helps the AboutUs activities xml exist, other than this
 * it has no other purposes.
 */
public class AboutUs extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutUs.this, BiteCounter.class);
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
                Intent intent = new Intent(AboutUs.this, Graph.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_counter:
                Intent intentUs = new Intent(AboutUs.this, BiteCounter.class);
                startActivity(intentUs);
                finish();
                return true;
            case R.id.action_tutorial:
                Intent intentT = new Intent(AboutUs.this, Tutorials.class);
                startActivity(intentT);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentS = new Intent(AboutUs.this, SettingsActivity.class);
                startActivity(intentS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
