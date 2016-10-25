package com.example.mnm.netsahiwot_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.example.mnm.netsahiwot_app.Database.DatabaseAdaptor;
import com.example.mnm.netsahiwot_app.FileManager.FileManager;

import java.io.File;
import java.util.HashMap;


public class ArticleandMenuDisplay extends AppCompatActivity {
    TextView Title,ContentText;
    ImageView menuImage;
    DatabaseAdaptor DbHelper;
    FileManager FM;
    ///comet

    private View mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FM=new FileManager(this);
        Title = (TextView) findViewById(R.id.Title);
        ContentText = (TextView) findViewById(R.id.Content);
        menuImage = (ImageView) findViewById(R.id.headerimage);

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Bundle extras = getIntent().getExtras();
        String SliderTitle = extras.getString("sliderTitle");
        int listid = extras.getInt("position");
        DbHelper = new DatabaseAdaptor(this);



        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(SliderTitle);

        if (SliderTitle != null) {
            Title.setText(SliderTitle);
            getRow(SliderTitle);

        } else {
          //  Title.setText(SliderTitle);


        }

    }
















/**Get the value by title and retrieve note**/
    void getRow(String Title) {

        Cursor cursor = DbHelper.getRowByID(Title);
        String imagename=cursor.getString(cursor.getColumnIndex("_id"))+".jpg";
        Toast.makeText(this, imagename, Toast.LENGTH_LONG).show();
        String ImageLocationid = FM.getFileAt("images", imagename).getAbsolutePath();
        String Note = cursor.getString(cursor.getColumnIndex("_news"));
        File file = new File(ImageLocationid);

        if (file.exists()) {
            menuImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }else{
            Log.d("not exist", "not exist");
  //          menuImage.setImageResource(R.drawable.happyteen);
        }


        //ContentText.setText(Note);


    }

}
