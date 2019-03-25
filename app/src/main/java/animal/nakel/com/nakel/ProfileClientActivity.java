package animal.nakel.com.nakel;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ProfileClientActivity extends AppCompatActivity implements View.OnClickListener {
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    //defining a database reference

    //our new views
    private EditText editTextName, editTextNational_no, editTextPhone_no, editTextType;
    ;
    private Button buttonSave;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private ListView mUserList;
    private ArrayList<String> musername = new ArrayList<>();
    ///// upload Photo
    private DatabaseReference databaseReference;
    private Button mselectedImage_id;
    private ImageView imageViewDownload_id;
    private Uri uri_id;
    private Uri uri;
    private EditText editTextPhoto_id;
    private static final int Gallery_intent_id = 3;
    private StorageReference mStorage;
    ArrayList<String> al=new ArrayList<String>();
    String National_Number_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_client);

        isInternetOn();
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
//upload Photo
        editTextPhoto_id = (EditText) findViewById(R.id.editPhoto_id);
        imageViewDownload_id = (ImageView) findViewById(R.id.image_Id);
        mselectedImage_id = (Button) findViewById(R.id.chooseImgId);
        mStorage = FirebaseStorage.getInstance().getReference();
        //getting the views from xml resource
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNational_no = (EditText) findViewById(R.id.editTextNational_number);
        editTextPhone_no = (EditText) findViewById(R.id.editTextPhone_number);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextType = (EditText) findViewById(R.id.editType);
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome " + user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        mselectedImage_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/+");
                startActivityForResult(intent, Gallery_intent_id);
            }
        });
        ////if there is data saved before
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String Type = null;
                if (dataSnapshot.child(user.getUid()).getKey().equals(user.getUid()) == true & dataSnapshot.child(user.getUid()).getValue() != null)

                {


                    String Name = dataSnapshot.child(user.getUid()).child("Name").getValue().toString();
/*String National_Number*/
                    National_Number_user = dataSnapshot.child(user.getUid()).child("National_Number").getValue().toString();
                    String Phone_Number = dataSnapshot.child(user.getUid()).child("Phone_Number").getValue().toString();
                    Type = dataSnapshot.child(user.getUid()).child("Type").getValue().toString();
                    String photo_id = dataSnapshot.child(user.getUid()).child("photo_id").getValue().toString();

                    editTextName.setText(Name);
                    editTextNational_no.setText(National_Number_user);
                    editTextPhone_no.setText(Phone_Number);
                    editTextType.setText(Type);
                    //   editTextPhoto_id.setText(photo_id);

                    editTextPhoto_id.setText(photo_id);

                    System.out.println("تم رفع صوره البطاقه " + photo_id);
                    Glide
                            .with(ProfileClientActivity.this)
                            .load(photo_id)
                            .into(imageViewDownload_id);

                    Intent intent = new Intent(getBaseContext(), PostsMain.class);
                    intent.putExtra("Type", Type);
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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////get all national number

        mDatabase2= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChild("National_Number")) {
                    // run some code
                    al.add(dataSnapshot.child("National_Number").getValue().toString());
                    System.out.println("kkkk"+ dataSnapshot.child("National_Number").getValue().toString());
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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void saveUserInformation() {
        //Getting values from database
        String Name = editTextName.getText().toString().trim();
        String National_Number = editTextNational_no.getText().toString().trim();
        String Phone_Number = editTextPhone_no.getText().toString().trim();
        //String Type = getIntent().getStringExtra("Client_ID");////put type in MainActivity when user press driver=1, client=0
        String photo_id = editTextPhoto_id.getText().toString().trim();

        String Type;
        boolean containscc = al.contains(National_Number);
        if (editTextType.getText().toString().trim().equals("")) {
            Type = getIntent().getStringExtra("Client_ID");
        } else {
            Type = editTextType.getText().toString().trim();
        }

        if (editTextName.getText().toString().trim().equals("")) {
            Toast.makeText(this, "رجاء ادخالالاسم", Toast.LENGTH_LONG).show();
            return;
        }
        if (editTextNational_no.getText().toString().trim().equals("") || editTextNational_no.getText().toString().trim().length() != 14) {
            Toast.makeText(this, "رجاء كتابه الرقم القومى الصحيح المكون من 14 رقم", Toast.LENGTH_LONG).show();
            return;
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      // if (editTextNational_no.getText().toString().trim().length() == 14) {
/*
mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
    //    String NationalNumber=(String)dataSnapshot.child("National_Number").getValue().toString();


        if (dataSnapshot.hasChild(editTextNational_no.getText().toString().trim())){

            Toast.makeText(ProfileClientActivity.this, "هذا المستخدم موجود بالفعل", Toast.LENGTH_LONG).show();

            return;
        }
        return;

    }


    @Override
    public void onCancelled(DatabaseError databaseError) {
    }

}

);
*/

//       }


        if (editTextPhone_no.getText().toString().trim().equals("") || editTextPhone_no.getText().toString().trim().length() != 11) {
            Toast.makeText(this, "رجاء كتابه رقم الهاتف المكون من 11رقم", Toast.LENGTH_LONG).show();
            return;
        }

        if (editTextPhoto_id.getText().toString().trim().equals("")) {
            Toast.makeText(this, "رجاءرفع صوره البطاقه الشخصيه", Toast.LENGTH_LONG).show();
            return;
        }

        if(!National_Number.equals(National_Number_user))
        {
            System.out.println("equal");
        }
        else
        {System.out.println("not equal");}


        if(National_Number.equals(National_Number_user) )
        {


            Toast.makeText(getApplicationContext(), "..نفس العميل..", Toast.LENGTH_LONG).show();

            //getting the current logged in user
            FirebaseUser user = firebaseAuth.getCurrentUser();
            UserInformation userInformation = new UserInformation(Type, Name, National_Number, Phone_Number, photo_id,user.getEmail(),user.getUid());

            databaseReference.child("Users").child(user.getUid()).setValue(userInformation);

            Toast.makeText(this, "..جارى حفظ البيانات..", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),PostsMain.class));


        }
        else if(!containscc)
        {


            Toast.makeText(getApplicationContext(), "..رقم جديد..", Toast.LENGTH_LONG).show();


            //getting the current logged in user
            FirebaseUser user = firebaseAuth.getCurrentUser();
            UserInformation userInformation = new UserInformation(Type, Name, National_Number, Phone_Number, photo_id,user.getEmail(),user.getUid());

            databaseReference.child("Users").child(user.getUid()).setValue(userInformation);

            Toast.makeText(this, "..جارى حفظ البيانات..", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),PostsMain.class));


        }

        else
        {
            Toast.makeText(getApplicationContext(), "..الرقم القومى موجود..", Toast.LENGTH_LONG).show();
            return;
        }

        //creating a userinformation object
    /*    UserInformation userInformation = new UserInformation(Type, Name, National_Number, Phone_Number, photo_id);
        //,photo_id);,photo_id

        //getting the current logged in user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent intent = new Intent(getBaseContext(), PostsMain.class);
        intent.putExtra("Type", Type);

        //databaseReference.child(user.getUid()).setValue(userInformation);
        databaseReference.child("Users").child(user.getUid()).setValue(userInformation);
     //   databaseReference.child("Users").child(National_Number).setValue(userInformation);
        //  databaseReference.child("User").setValue(userInformation);
        //displaying a success toast


        Toast.makeText(this, "..جارى حفظ البيانات...", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), PostsMain.class));*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*        if (requestCode == Gallery_intent && resultCode == RESULT_OK) {
            //final Uri
            uri = data.getData();
          //uri_id=data.getData();
            /// System.out.print("uriiiii"+uri);

            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(ProfileActivity.this, "Upload Done" + uri, Toast.LENGTH_LONG).show();

                    // ImageView targetImageView = (ImageView) findViewById(R.id.imageViewDownload);
                    // String internetUrl = "https://firebasestorage.googleapis.com/v0/b/nakel-d3b81.appspot.com/o/Photos%2F23885?alt=media&token=502f35a9-0638-4194-af2a-c65d3f50";//"http://i.imgur.com/DvpvklR.png";

                    editTextPhoto.setText(uri.toString());
                    Glide
                            .with(ProfileActivity.this)
                            .load(uri)
                            .into(imageViewDownload);


                }
            });

        } else
        */
        if (requestCode == Gallery_intent_id && resultCode == RESULT_OK) {
            //final Uri
            uri_id = data.getData();
            //uri_id=data.getData();
            /// System.out.print("uriiiii"+uri);

            StorageReference filepath = mStorage.child("Photos_id").child(uri_id.getLastPathSegment());
            filepath.putFile(uri_id).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(ProfileClientActivity.this, "تم رفع صوره البطاقه الشخصيه " , Toast.LENGTH_LONG).show();

                    // ImageView targetImageView = (ImageView) findViewById(R.id.imageViewDownload);
                    // String internetUrl = "https://firebasestorage.googleapis.com/v0/b/nakel-d3b81.appspot.com/o/Photos%2F23885?alt=media&token=502f35a9-0638-4194-af2a-c65d3f50";//"http://i.imgur.com/DvpvklR.png";

                    editTextPhoto_id.setText(downloadUrl.toString());
                    Glide
                            .with(ProfileClientActivity.this)
                            .load(downloadUrl)
                            .into(imageViewDownload_id);


                }
            });

        }


    }


    @Override
    public void onClick(View view) {
        //if logout is pressed
        if (view == buttonLogout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == buttonSave) {
            saveUserInformation();
        }


    }



    /*
    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
        //we are connected to a network
        connected = true;
    }
    else
    connected = false;

*/


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

            Toast.makeText(this, " متصل بالانترنت  ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " رجاء الاتصال بالانترنت ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


}