package com.example.mnm.netsahiwot_app.JSON;


import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mnm.netsahiwot_app.Database.DatabaseAdaptor;
import com.example.mnm.netsahiwot_app.FileManager.FileDownloader;
import com.example.mnm.netsahiwot_app.FileManager.FileManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**This Class synchronize database with the server MYSQL Database**/
public class SyncService extends JobService {
    String JSON_STRING;
   //MainActivity mainobject=new MainActivity();
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    DatabaseAdaptor DbHelper;
    FileDownloader FD;

    public SyncService(){



    }
    @Override
    public boolean onStartJob(JobParameters params) {
        DbHelper = new DatabaseAdaptor(this);
  //      Toast.makeText(getApplicationContext(), "job started ", Toast.LENGTH_LONG).show();
        Cursor cursor = DbHelper.getAll();



        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                FileManager FM;
                FM=new FileManager(this);
                String ImageUrl= cursor.getString(cursor.getColumnIndex("_ImageUrl"));
                //set image name as news id
                String ImageNamefromId=cursor.getString(cursor.getColumnIndex("_id"));

                String imagename=(ImageNamefromId+".jpg");
                String ImageLocationbyid = FM.getFileAt("images", imagename).getAbsolutePath();
                File file = new File(ImageLocationbyid);

//                Cursor cu = DbHelper.getID(ImageNamefromId);
                if (file.exists()) {

                    Toast.makeText(getApplicationContext(), "Image "+ImageNamefromId+".jpg"+" already exists", Toast.LENGTH_LONG).show();
                }
                else{

                    FD=new FileDownloader(getBaseContext(),ImageUrl,"images",ImageNamefromId+".jpg");
                    FD.execute();
  //                  Toast.makeText(getApplicationContext(), "Image DOWNLOADED", Toast.LENGTH_LONG).show();
   //                 mainobject.Slider();

                }
                cursor.moveToNext();
            }
            cursor.close();

        }







//    if(cursor.getCount()==0){
//
//            new insertBackgroundTask().execute();
//            parseInsertJSON();
//
//
//        }else {
////            new deleteBackgroundTask().execute();
////            parseDeleteJSON();
////
////
////            new updateBackgroundTask().execute();
////            parseUpdateJSON();
//
//            new insertBackgroundTask().execute();
//            parseInsertJSON();
//
//
//
//
//            }

            jobFinished(params, false);
            return false;
    }

    /**Request JSON data to be inserted**/
    private class insertBackgroundTask extends AsyncTask<Void,Void,String> {
        String json_url;
        @Override
        protected String doInBackground(Void... params) {
 //           Toast.makeText(getApplicationContext(), "insert called ", Toast.LENGTH_LONG).show();
            URL url= null;
            try {
                url = new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JSON_STRING= bufferedReader.readLine())!=null){


                    stringBuilder.append(JSON_STRING+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            json_url="http://kartauniverse.com/Json_get_data.php";


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;
        }
    }
//
//
//    /**Request JSON data to be deleted**/
//   private class deleteBackgroundTask extends AsyncTask<Void,Void,String> {
//        String json_url;
//        @Override
//        protected String doInBackground(Void... params) {
//            URL url= null;
//            try {
//                url = new URL(json_url);
//                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
//                InputStream inputStream=httpURLConnection.getInputStream();
//                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
//                StringBuilder stringBuilder=new StringBuilder();
//                while ((JSON_STRING= bufferedReader.readLine())!=null){
//
//
//                    stringBuilder.append(JSON_STRING+"\n");
//
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return stringBuilder.toString().trim();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            json_url="http://kartauniverse.com/Json_deleted_data.php";
//
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            json_string = result;
//        }
//    }
//
//
//
//    /**Request JSON data to be updated**/
//    private class updateBackgroundTask extends AsyncTask<Void,Void,String> {
//        String json_url;
//        @Override
//        protected String doInBackground(Void... params) {
//            URL url= null;
//            try {
//                url = new URL(json_url);
//                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
//                InputStream inputStream=httpURLConnection.getInputStream();
//                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
//                StringBuilder stringBuilder=new StringBuilder();
//                while ((JSON_STRING= bufferedReader.readLine())!=null){
//
//
//                    stringBuilder.append(JSON_STRING+"\n");
//
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return stringBuilder.toString().trim();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            json_url="http://kartauniverse.com/Json_updated_data.php";
//
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            json_string = result;
//        }
//    }

    /**Save Image to local storage**/
















/**this method will parse the json that we get from the server**/

    public void parseInsertJSON(){

        if(json_string==null){
            //Toast.makeText(getApplicationContext(),"First Get Json",Toast.LENGTH_LONG).show();
        }else {
            try {
               // Message.message(this, "parsing started");
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String ID, TITLE,NOTE ,ImageLoction ,ImageUrl;
                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);
                    ID = JO.getString("_id");
                    TITLE= JO.getString("_title");
                    NOTE  = JO.getString("_notes");
                    ImageLoction = JO.getString("_imagesLocation");
                    ImageUrl = JO.getString("_ImageUrl");
                        /**This method will add the json data to the database**/
                        addData( ID, TITLE, NOTE ,ImageLoction , ImageUrl);
                        count++;


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    public void parseDeleteJSON(){

        if(json_string==null){
            //Toast.makeText(getApplicationContext(),"First Get Json",Toast.LENGTH_LONG).show();
        }else {
            try {
               // Message.message(this, "parsing started");
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String Id;
                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);
                    Id = JO.getString("_id");

                    deleteData(Id);
                        count++;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public void parseUpdateJSON(){

        if(json_string==null){
            //Toast.makeText(getApplicationContext(),"First Get Json",Toast.LENGTH_LONG).show();
        }else {
            try {
               // Message.message(this, "parsing started");
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String ID, TITLE,NOTE ,ImageLoction ,ImageUrl;
                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);
                    ID = JO.getString("_id");
                    TITLE= JO.getString("_title");
                    NOTE  = JO.getString("_notes");
                    ImageLoction = JO.getString("_imagesLocation");
                    ImageUrl = JO.getString("_ImageUrl");


                        updateData(ID,TITLE,NOTE,ImageLoction,ImageUrl);
                        count++;


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }




    /**Insert data from JSON Request**/
    public void addData(String ID, String TITLE, String NOTE , String ImageLoction , String ImageUrl) {

        DbHelper =new DatabaseAdaptor(this);

        long id = DbHelper.insertData( ID,  TITLE, NOTE ,ImageLoction , ImageUrl);
        if (id < 0) {

            //Message.message(this, "JSON insert failed");
        } else {

           // Message.message(this, " JSON data successfully inserted");
        }


    }

    /**puts delete lines from JSON Request**/
    public void deleteData(String Id) {

        DbHelper =new DatabaseAdaptor(this);
       // Message.message(this, "delete called");
        long id = DbHelper.deleteData(Id);
       // Message.message(this, "delete second");
        if (id < 0) {

          //  Message.message(this, "data not deleted");
        } else {

          //  Message.message(this, "data deleted");
        }


    }


    /**puts update lines from JSON Request**/
    public void updateData(String ID, String TITLE, String NOTE , String ImageLoction , String ImageUrl){

        DbHelper =new DatabaseAdaptor(this);

        long id = DbHelper.updateData( ID, TITLE,  NOTE ,ImageLoction ,ImageUrl);
        if (id < 0) {

           // Message.message(this, "delete failed");
        } else {

           // Message.message(this, "delete Successful");
        }

    }


    /**Download Image and Rename**/
    private class DownloadImageandRename {

        void DownloadandRename(){
            Cursor c = DbHelper.getAll();


            c.moveToFirst();
            if (c.getCount() > 0) {
                while (!c.isAfterLast()) {

                    String ImageUrl= c.getString(c.getColumnIndex("_ImageUrl"));
                    //set image name as news id
                    String ImageNamefromId=c.getString(c.getColumnIndex("_id"));


                    Cursor cu = DbHelper.getID(ImageNamefromId);


                    if(cu==null){
                        FD=new FileDownloader(getBaseContext(),ImageUrl,"images",ImageNamefromId+".jpg");
                        FD.execute();
                    }
                    c.moveToNext();
                }
                c.close();

            } else {

  //              Toast.makeText(getApplicationContext(), "all images are downloaded ", Toast.LENGTH_LONG).show();

            }
        }


    }


    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }


}
