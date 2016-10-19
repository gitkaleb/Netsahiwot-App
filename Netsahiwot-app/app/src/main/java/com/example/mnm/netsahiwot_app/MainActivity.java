package com.example.mnm.netsahiwot_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.mnm.netsahiwot_app.Database.DatabaseAdaptor;
import com.example.mnm.netsahiwot_app.FileManager.FileManager;


import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemClickListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener  {
    private SliderLayout mDemoSlider;
    DatabaseAdaptor DbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageButton b1= (ImageButton) findViewById(R.id.sidebaricon);
      b1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              drawer.openDrawer(Gravity.START);
          }
      });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Slider();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void Slider() {
        //slider


        /**if there is news in the database do this**/


        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

//                    HashMap<String,String> url_maps = new HashMap<String, String>();
//                    url_maps.put(Title, "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//                    url_maps.put("Pornography addiction", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//                    url_maps.put("prisonor of pornography ", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//                    url_maps.put("Happiness found in Christ", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        HashMap<String, String> file_maps = new HashMap<String, String>();
        DatabaseAdaptor DbHelper;
        DbHelper = new DatabaseAdaptor(MainActivity.this);
        Cursor c = DbHelper.getAll();
        c.moveToFirst();
        FileManager FM;
        FM=new FileManager(this);
        if (c.getCount() > 0) {
            while (!c.isAfterLast()) {

                String Title = c.getString(c.getColumnIndex("_title"));
                String Note = c.getString(c.getColumnIndex("_news"));
                String id = c.getString(c.getColumnIndex("_id"));
                String ImageLocation;

                Cursor cursor = DbHelper.getRowByID(Title);
                String imagename=cursor.getString(cursor.getColumnIndex("_id"))+".jpg";
                String ImageLocationid = FM.getFileAt("images", imagename).getAbsolutePath();
                File file = new File(ImageLocationid);

                if (Title != null && Note != null) {





                    if (file.exists()) {
                        /**Netsa Hiwot dummyImage**/
                        ImageLocation=FM.getFileAt("images",id+".jpg").getAbsolutePath();
                        file_maps.put(Title, ImageLocation);


                    }else {



                        ImageLocation=FM.getFileAt("images","0.jpg").getAbsolutePath();
                        file_maps.put(Title,  ImageLocation);
                    }


                }
                c.moveToNext();

            }
            c.close();

        } else {

            Toast.makeText(getApplicationContext(), "There is no news data in The database ", Toast.LENGTH_LONG).show();

        }

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(new File(file_maps.get(name)))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);


    }


    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        //Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(this,ArticleandMenuDisplay.class);
        i.putExtra("sliderTitle",slider.getBundle().get("extra").toString() );
        startActivity(i);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_custom_indicator:
//                mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
//                break;
//            case R.id.action_custom_child_animation:
//                mDemoSlider.setCustomAnimation(new ChildAnimationExample());
//                break;
//            case R.id.action_restore_default:
//                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//                break;
//            case R.id.action_github:
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/daimajia/AndroidImageSlider"));
//                startActivity(browserIntent);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    //MENU LIST
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent(this, ArticleandMenuDisplay.class);
//        intent.setClass(this, ListItemDetail.class);
        intent.putExtra("position", position);
//        // Or / And
//        intent.putExtra("id", id);
        startActivity(intent);
    }






}
