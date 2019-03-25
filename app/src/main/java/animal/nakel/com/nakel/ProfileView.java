package animal.nakel.com.nakel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/* this activity used to view profile of deiver in list view*/
public class ProfileView extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;


    //defining a database reference
    private DatabaseReference databaseReference;

    //our new views
    private TextView editTextName, editTextNational_no,editTextPhone_no,editTextCar_Number,editTextLicense_no,editTextType,editTextPhoto;
    private DatabaseReference mDatabase;
    private ListView mUserList;
    private ArrayList<String> musername=new ArrayList<>();
    private StorageReference mStorage;
    private static final int Gallery_intent=2;
    private ImageView imageViewDownload;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        ///////////////////////////////////////////////////////////////////////////////////////////
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //getting the views from xml resource
        editTextName = (TextView) findViewById(R.id.editTextName);
        editTextNational_no = (TextView) findViewById(R.id.editTextNational_number);
        editTextPhone_no = (TextView) findViewById(R.id.editTextPhone_number);
        editTextCar_Number = (TextView) findViewById(R.id.editTextCar_Number);
        editTextLicense_no = (TextView) findViewById(R.id.editTextLicense_number);

        editTextType=(TextView)findViewById(R.id.editType);
        editTextPhoto=(TextView)findViewById(R.id.editPhoto);

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);


        //displaying logged in user name
        textViewUserEmail.setText("Welcome " + user.getEmail());

        /////////////////////////to put image in storage firebase upload and see
        mStorage= FirebaseStorage.getInstance().getReference();

        imageViewDownload=(ImageView) findViewById(R.id.imageViewDownload2);

        ////////////////////////////////////////////////////////////////////////////

        ////if there is data saved before
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                // System.out.println("cccc"+dataSnapshot.getKey());
                // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                if (dataSnapshot.child(user.getUid()).getKey().equals(user.getUid())==true  & dataSnapshot.child(user.getUid()).getValue() != null)

                {

                    String Car_Number=dataSnapshot.child(user.getUid()).child("Car_Number").getValue().toString();         //child("Name").getValue();
                    String License_Number=dataSnapshot.child(user.getUid()).child("License_Number").getValue().toString();
                    String Name=dataSnapshot.child(user.getUid()).child("Name").getValue().toString();
                    String National_Number=dataSnapshot.child(user.getUid()).child("National_Number").getValue().toString();
                    String Phone_Number=dataSnapshot.child(user.getUid()).child("Phone_Number").getValue().toString();
                    String Type=dataSnapshot.child(user.getUid()).child("Type").getValue().toString();
                    String photo=dataSnapshot.child(user.getUid()).child("photo").getValue().toString();

                    editTextName.setText(Name);
                    editTextNational_no.setText(National_Number);
                    editTextPhone_no.setText(Phone_Number);
                    editTextCar_Number.setText(Car_Number);
                    editTextLicense_no.setText(License_Number);
                    editTextType.setText(Type);
                    editTextPhoto.setText(photo);

                    Glide
                            .with(getApplicationContext())
                            .load(photo)
                            .into(imageViewDownload);
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


    //////////////////////////////////////////////////////////////////////////


}



