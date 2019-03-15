package com.example.notificationtutorial;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    private static String CHANNEL_ID="Channel_Id";
    private static String CHANNEL_Name="Channel 1";
    private static String CHANNEL_DESC="Notification Tutorial";
    private Button notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if(task.isSuccessful()){

                            String token=task.getResult().getToken();

                            Toast.makeText(MainActivity.this,"Toaken: "+ token,Toast.LENGTH_LONG).show();
                        }

                        else {
                            Toast.makeText(MainActivity.this,"Token not generated",Toast.LENGTH_LONG).show();
                        }

                    }
                });
        notificationChannelCreate();
        notificationButton=(Button)findViewById(R.id.activity_main_notify);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();

                Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void notificationChannelCreate(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel=new NotificationChannel
                    (CHANNEL_ID,CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }

    private void displayNotification(){


        NotificationCompat.Builder notification=new NotificationCompat.Builder(this,CHANNEL_ID)

                .setSmallIcon(R.drawable.ic_show)
                .setContentTitle("This is title")
                .setContentText("First Notification demo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mnotify=NotificationManagerCompat.from(this);
        mnotify.notify(1,notification.build());
    }
}
