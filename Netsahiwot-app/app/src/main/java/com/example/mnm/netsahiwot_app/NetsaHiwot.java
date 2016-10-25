package com.example.mnm.netsahiwot_app;

import android.Manifest;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import com.example.mnm.netsahiwot_app.Database.DatabaseAdaptor;
import com.example.mnm.netsahiwot_app.FileManager.FileManager;
import com.example.mnm.netsahiwot_app.JSON.SyncService;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by kzone on 5/28/2016.
 */
public class NetsaHiwot extends Application {
/**This Class starts everytime our application opens**/
    private static final int JOB_ID = 100;
    private JobScheduler myJobScheduler;
    private FileManager FM;

    @Override
    public void onCreate() {

        super.onCreate();
        DatabaseAdaptor DbHelper;
        DbHelper = new DatabaseAdaptor(this);



        FM=new FileManager(this);
        Intent intent = new Intent(this, SyncService.class);
        startService(intent);
        myJobScheduler  = JobScheduler.getInstance(this);
        Toast.makeText(getApplicationContext(),"updating data!", Toast.LENGTH_LONG).show();
        JobConstr();

        DbHelper.insertData( "1", "this is the title", "this is the note" , "" ,"");
        DbHelper.insertData( "2", "this is title", "this  te te" ,"","http://www.delraypsychotherapist.com/wp-content/uploads/2011/08/Porn2-300x177.jpg");

    }

    public void JobConstr(){

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this,SyncService.class));
        builder.setPeriodic(100000);
        builder.setBackoffCriteria(500,1);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        myJobScheduler.schedule(builder.build());
    }
}

