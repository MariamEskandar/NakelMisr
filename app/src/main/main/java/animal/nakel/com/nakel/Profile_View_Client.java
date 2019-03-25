
package animal.nakel.com.nakel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/* this activity used to view profile view of Client in */
public class Profile_View_Client extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;


    //defining a database reference
    private DatabaseReference databaseReference;

    //our new views
    private TextView editTextName, editTextNational_no,editTextPhone_no,editTextType;
    private DatabaseReference mDatabase;

    private StorageReference mStorage;
    private ImageView imageViewDownload_id;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__view__client);
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
        editTextType=(TextView)findViewById(R.id.editType);

     //   imageViewDownload=(ImageView) findViewById(R.id.image_Id);

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);


        //displaying logged in user name
        textViewUserEmail.setText("Welcome " + user.getEmail());

        /////////////////////////to put image in storage firebase upload and see
        mStorage= FirebaseStorage.getInstance().getReference();


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

                    imageViewDownload_id=(ImageView) findViewById(R.id.image_Id);

                    String Name=dataSnapshot.child(user.getUid()).child("Name").getValue().toString();
                    String National_Number=dataSnapshot.child(user.getUid()).child("National_Number").getValue().toString();
                    String Phone_Number=dataSnapshot.child(user.getUid()).child("Phone_Number").getValue().toString();
                    String Type=dataSnapshot.child(user.getUid()).child("Type").getValue().toString();

                  //  editTextPhoto_id.setText(photo_id);
                    String photo_id = dataSnapshot.child(user.getUid()).child("photo_id").getValue().toString();

                    System.out.println("تم رفع صوره البطاقه " + photo_id);
                    Glide
                            .with(Profile_View_Client.this)
                            .load(photo_id)
                            .into(imageViewDownload_id);
                    System.out.println("photohhhh"+photo_id);
                  //  String photo=(String)dataSnapshot.child("photo").getValue().toString();
                    //Picasso.with(Public_profile.this).load(photo).into(imageViewDownload);

                    editTextName.setText(Name);
                    editTextNational_no.setText(National_Number);
                    editTextPhone_no.setText(Phone_Number);
                    editTextType.setText(Type);


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



