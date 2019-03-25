package animal.nakel.com.nakel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Type_Deriver_Client extends AppCompatActivity {
    private Button button_Client;
    private Button button_Driver;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog2;
    private DatabaseReference mDatabase;
//    final FirebaseUser user = firebaseAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type__deriver__client);

        isInternetOn();

        firebaseAuth = FirebaseAuth.getInstance();
        button_Client = (Button) findViewById(R.id.button_Client);
        button_Driver = (Button) findViewById(R.id.button_Driver);
      //  progressDialog2.setMessage("Login Please Wait......");
        //progressDialog2.show();
        
        button_Client.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileClientActivity.class);
                intent.putExtra("Client_ID", "2");
                startActivity(intent);

            }
        });

        button_Driver.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                intent.putExtra("DRIVER_ID", "1");
                startActivity(intent);
                //startActivity(new Intent(Type_Deriver_Client.this, ProfileActivity.class));
            }
        });
    }
/*
    @Override
    public void onResume() {
        super.onResume();
        isInternetOn();

        mDatabase= FirebaseDatabase.getInstance().getReference();

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                // System.out.println("cccc"+dataSnapshot.getKey());
                // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                if (dataSnapshot.child(user.getUid()).getKey().equals(user.getUid())==true  & dataSnapshot.child(user.getUid()).getValue() != null)

                {

                    String Type=dataSnapshot.child(user.getUid()).child("Type").getValue().toString().trim();

                    String Name=dataSnapshot.child(user.getUid()).child("Name").getValue().toString();
                    if (Type.equals("") )  ///if your first time to eneter data
                    {
                        startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
                    }

                    else
                    {
                        startActivity(new Intent(getApplicationContext(), PostsMain.class));
                    }
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


*/       // @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.Edit_Profile:
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                return true;

            case R.id.View_Profile:
                startActivity(new Intent(getApplicationContext(),ProfileView.class));
                return true;
            /*
            case R.id.Posts:
                startActivity(new Intent(getApplicationContext(),PostsMain.class));
                return true;
            */

            case R.id.Logout:
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            Toast.makeText(this, " الانترنت متصل", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " رجاء الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}
