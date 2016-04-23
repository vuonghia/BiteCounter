package sustainablehealthsolutionsllc.bitecounter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Wallpaper Browser:
 * This class have a grid view of images. I use my customized image adapter
 * for this grid view. Every time users click on the selected image, the class
 * will send image ID to the BiteCounter class.
 */
public class WallpaperBrowser extends ActionBarActivity {
    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_browser);

        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(WallpaperBrowser.this, BiteCounter.class);
                intent.putExtra("imageID", position);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent intent2 = new Intent(WallpaperBrowser.this, Graph.class);
                intent2.putExtra("imageIDGraph", position);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}