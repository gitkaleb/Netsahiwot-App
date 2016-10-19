package com.example.mnm.netsahiwot_app;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnm.netsahiwot_app.Database.DatabaseAdaptor;
import com.example.mnm.netsahiwot_app.FileManager.FileManager;

import java.io.File;
import java.util.HashMap;


public class ArticleandMenuDisplay extends AppCompatActivity {
    TextView Title,ContentText;
    ImageView menuImage;
    DatabaseAdaptor DbHelper;
    FileManager FM;
    ///cometnout


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articleand_post_display);
        FM=new FileManager(this);
        Title = (TextView) findViewById(R.id.ArticleTitle);
        ContentText = (TextView) findViewById(R.id.Content);
        menuImage = (ImageView) findViewById(R.id.ArticleImage);

        Bundle extras = getIntent().getExtras();
        String SliderTitle = extras.getString("sliderTitle");
        int listid = extras.getInt("position");
        DbHelper = new DatabaseAdaptor(this);


        if (SliderTitle == null) {


           // final TypedArray testArrayIcon = getResources().obtainTypedArray(R.array.menu_image_icon);
            switch (listid) {

//                case 0: {
//
//
//                    String[] testArray = getResources().getStringArray(menu1);
//                    Title.setText(testArray[0]);
//                    ContentText.setText(testArray[1]);
//                    menuImage.setImageResource(testArrayIcon.getResourceId(0, -1));
//
//                }
//                break;
//
//                case 1: {
//
//                    String[] testArray2 = getResources().getStringArray(R.array.menu2);
//                    Title.setText(testArray2[0]);
//                    ContentText.setText(testArray2[1]);
//                    menuImage.setImageResource(testArrayIcon.getResourceId(0, -1));
//                }
//                break;
//                case 2: {
//
//                    String[] testArray3 = getResources().getStringArray(R.array.menu3);
//                    Title.setText(testArray3[0]);
//                    ContentText.setText(testArray3[1]);
//                    menuImage.setImageResource(testArrayIcon.getResourceId(0, -1));
//                }
//                break;
//                case 3: {
//                    String[] testArray4 = getResources().getStringArray(R.array.menu4);
//                    Title.setText(testArray4[0]);
//                    ContentText.setText(testArray4[1]);
//                    menuImage.setImageResource(testArrayIcon.getResourceId(0, -1));
//                }
//                break;

            }
        } else {
            Title.setText(SliderTitle);
            getRow(SliderTitle);

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


        ContentText.setText(Note);


    }

}
