package animal.nakel.com.nakel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*this activity used for register data of user and if there is other data before can update on it   */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener  {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    //defining a database reference
    private DatabaseReference databaseReference;

    //our new views
    private EditText editTextName,
            editTextNational_no,editTextPhone_no,editTextCar_Number,editTextLicense_no,editTextType,editTextPhoto,editTextPhoto_id,editTextBlock_no;
    private Button buttonSave;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private ListView mUserList;
    private ArrayList<String> musername=new ArrayList<>();


    private StorageReference mStorage;
    private static final int Gallery_intent=2;
    private static final int Gallery_intent_id=3;
    private Button mselectedImage;
    private Uri uri;

    private ImageView imageViewDownload;
    private Button mselectedImage_id;
    private ImageView imageViewDownload_id;
    private Uri uri_id;
    String Type;
    Map<String, Object> map = new HashMap<>();
    ArrayList<String> al=new ArrayList<String>();
    String National_Number_user;
    String Block_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextNational_no = (EditText) findViewById(R.id.editTextNational_number);
        editTextPhone_no = (EditText) findViewById(R.id.editTextPhone_number);
        editTextCar_Number = (EditText) findViewById(R.id.editTextCar_Number);
        editTextLicense_no = (EditText) findViewById(R.id.editTextLicense_number);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextType=(EditText)findViewById(R.id.editType);
        editTextPhoto=(EditText)findViewById(R.id.editPhoto);
        editTextBlock_no=(EditText)findViewById(R.id.editBlock);

        editTextPhoto_id=(EditText)findViewById(R.id.editPhoto_id);
        imageViewDownload_id=(ImageView) findViewById(R.id.image_Id);
        mselectedImage_id=(Button)findViewById(R.id.chooseImgId);

        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);


        //displaying logged in user name
        textViewUserEmail.setText("Welcome " + user.getEmail());

        /////////////////////////to put image in storage firebase upload and see
        mStorage= FirebaseStorage.getInstance().getReference();
        mselectedImage=(Button)findViewById(R.id.chooseImg);
        imageViewDownload=(ImageView) findViewById(R.id.imageViewDownload5555);
        ////////////////////////////////////////////////////////////////////////////

        //adding listener to button

        buttonSave.setOnClickListener(this);

        mselectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/+");
                startActivityForResult(intent,Gallery_intent);


            }
        } );
    mselectedImage_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/+");
                        startActivityForResult(intent,Gallery_intent_id);


                    }
                }


        );

        ////if there is data saved before
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                // System.out.println("cccc"+dataSnapshot.getKey());
                // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                if (dataSnapshot.child(user.getUid()).getKey().equals(user.getUid())==true  & dataSnapshot.child
                        (user.getUid()).getValue() != null)

                {

                    String Car_Number=dataSnapshot.child(user.getUid()).child("Car_Number").getValue().toString();
                    //child("Name").getValue();
                    String License_Number=dataSnapshot.child(user.getUid()).child("License_Number").getValue
                            ().toString();
                    String Name=dataSnapshot.child(user.getUid()).child("Name").getValue().toString();
                    National_Number_user=dataSnapshot.child(user.getUid()).child("National_Number").getValue
                            ().toString();
                    String Phone_Number=dataSnapshot.child(user.getUid()).child("Phone_Number").getValue
                            ().toString();
                    String Type=dataSnapshot.child(user.getUid()).child("Type").getValue().toString();

                    String photo=dataSnapshot.child(user.getUid()).child("photo").getValue().toString();

                    String photo_id=dataSnapshot.child(user.getUid()).child("photo_id").getValue().toString();

                    Block_no=dataSnapshot.child(user.getUid()).child("Block").getValue().toString();
                    editTextName.setText(Name);
                    editTextNational_no.setText(National_Number_user);
                    editTextPhone_no.setText(Phone_Number);
                    editTextCar_Number.setText(Car_Number);
                    editTextLicense_no.setText(License_Number);
                    editTextType.setText(Type);

                    editTextPhoto.setText(photo);
                    editTextBlock_no.setText(Block_no);
                    System.out.println("تم رفع صوره السياره"+photo);
                    Glide
                            .with(ProfileActivity.this)
                            .load(photo)
                            .into(imageViewDownload);

                    editTextPhoto_id.setText(photo_id);
                    System.out.println("تم رفع صوره البطاقه "+photo_id);
                    Glide
                            .with(ProfileActivity.this)
                            .load(photo_id)
                            .into( imageViewDownload_id);

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


///////////////////////////////////////////////get all national number

        mDatabase2= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                if (dataSnapshot.hasChild("National_Number")) {
                    // run some code
                     al.add(dataSnapshot.child("National_Number").getValue().toString());
                    System.out.println("kkkk"+ dataSnapshot.child("National_Number").getValue().toString());
                }


               /*   Iterator itr=al.iterator();
                while(itr.hasNext()){
                    System.out.println("Key = lll" +itr.next());
                }
                map.put("National_Number",dataSnapshot.child("National_Number").getValue() );
               Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
              while (entries.hasNext()) {
                    Map.Entry<String, Object> entry = entries.next();
                    System.out.println("Key = lll" + entry.getKey() + ", Value = " + entry.getValue());
                }*/

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


       /* Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Object> entry = entries.next();
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }*/
        String Name = editTextName.getText().toString().trim();
        String National_Number = editTextNational_no.getText().toString().trim();
        String Phone_Number = editTextPhone_no.getText().toString().trim();
        String Car_Number = editTextCar_Number.getText().toString().trim();
        String License_Number = editTextLicense_no.getText().toString().trim();
        String photo = editTextPhoto.getText().toString().trim();
        String photo_id = editTextPhoto_id.getText().toString().trim();

        String Type;
        boolean containscc = al.contains(National_Number);
        System.out.print("Key lllllll= "+containscc+National_Number+National_Number_user);
        if ( editTextType.getText().toString().trim().equals(""))
        {
            Type = getIntent().getStringExtra("DRIVER_ID");

            // System.out.println("typenull"+Type+editTextType.getText().toString());
        }
        else
        {
            Type=editTextType.getText().toString().trim();
            //System.out.println("typefound"+Type);
        }
        Intent intent = new Intent(getBaseContext(), Block.class);
        intent.putExtra("Type", Type);

        ///////////////////////////
        // test all data entered or not

        if (editTextName.getText().toString().trim().equals(""))
        {
            Toast.makeText(this,"رجاء ادخال الاسم",Toast.LENGTH_LONG).show();
            return;
        }
        if( editTextNational_no.getText().toString().trim().equals("") ||editTextNational_no.getText().toString().trim().length()!=14)
        {
            Toast.makeText(this,"رجاء ادخال الرقم القومى الصحيح المكون من 14 رقم",Toast.LENGTH_LONG).show();
            return;
        }
        if( editTextPhone_no.getText().toString().trim().equals("")||editTextPhone_no.getText().toString().trim().length()!=11)
        {
            Toast.makeText(this,"رجاء ادخال رقم الهاتف المكون من 11رقم",Toast.LENGTH_LONG).show();
            return;
        }
        if( editTextCar_Number.getText().toString().trim().equals(""))
        {
            Toast.makeText(this,"رجاء ادخال رقم السياره",Toast.LENGTH_LONG).show();
            return;
        }
        if( editTextLicense_no.getText().toString().trim().equals(""))
        {
            Toast.makeText(this,"رجاء ادخال رقم الرخصه الخاصه بك",Toast.LENGTH_LONG).show();
            return;
        }


        if (editTextPhoto.getText().toString().trim().equals("")) {
            Toast.makeText(this,"رجاء رفع صوره السياره",Toast.LENGTH_LONG).show();
            return;
        }

        if (editTextPhoto_id.getText().toString().trim().equals("")) {
            Toast.makeText(this,"رجاءرفع صوره البطاقه الشخصيه ",Toast.LENGTH_LONG).show();
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
            System.out.println("nnnn"+Block_no);
            if (Block_no==null) {
                System.out.println("nnnnfff"+Block_no);
                UserInformation userInformation = new UserInformation(Type, Name, National_Number, Phone_Number, Car_Number, License_Number, photo, photo_id, user.getEmail(), user.getUid(), "Block");
                databaseReference.child("Users").child(user.getUid()).setValue(userInformation);
            }
            else
                {
                    UserInformation userInformation = new UserInformation(Type, Name, National_Number, Phone_Number, Car_Number, License_Number, photo, photo_id, user.getEmail(), user.getUid(), Block_no);
                    databaseReference.child("Users").child(user.getUid()).setValue(userInformation);
                }


            Toast.makeText(this, "..جارى حفظ البيانات..", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),PostsMain.class));


       }
        else if(!containscc)
        {


            Toast.makeText(getApplicationContext(), "..رقم جديد..", Toast.LENGTH_LONG).show();


            //getting the current logged in user
            FirebaseUser user = firebaseAuth.getCurrentUser();
            System.out.println("nnnnggg"+Block_no);
            if (Block_no==null) {
                System.out.println("nnnnvvvv"+Block_no);
                UserInformation userInformation = new UserInformation(Type, Name, National_Number, Phone_Number, Car_Number, License_Number, photo, photo_id, user.getEmail(), user.getUid(), "Block");
                databaseReference.child("Users").child(user.getUid()).setValue(userInformation);
            }
            else
            {
                UserInformation userInformation = new UserInformation(Type, Name, National_Number, Phone_Number, Car_Number, License_Number, photo, photo_id, user.getEmail(), user.getUid(), Block_no);
                databaseReference.child("Users").child(user.getUid()).setValue(userInformation);
            }

            Toast.makeText(this, "..جارى حفظ البيانات..", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),Block.class));


        }

      else
        {
            Toast.makeText(getApplicationContext(), "..الرقم القومى موجود..", Toast.LENGTH_LONG).show();
           return;
         }
        //creating a userinformation object


       /* Iterator itr=al.iterator();
        while(itr.hasNext()){
            System.out.println("Key = " +itr.next());
        }*/

    }

    ///////////////////////////////////////////////////////////////



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_intent && resultCode==RESULT_OK) {
            //final Uri
            uri = data.getData();
//uri_id=data.getData();
            /// System.out.print("uriiiii"+uri);

            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(ProfileActivity.this, "Upload Done" , Toast.LENGTH_LONG).show();

                    // ImageView targetImageView = (ImageView) findViewById(R.id.imageViewDownload);
                    // String internetUrl = "https://firebasestorage.googleapis.com/v0/b/nakel-d3b81.appspot.com/o/Photos%2F23885?alt=media&token=502f35a9-0638-4194-af2a-c65d3f50";//"http://i.imgur.com/DvpvklR.png";

                    editTextPhoto.setText(downloadUrl.toString());
                    Glide
                            .with(ProfileActivity.this)
                            .load(downloadUrl)
                            .into(imageViewDownload);


                }
            });

        }

       else if(requestCode==Gallery_intent_id && resultCode==RESULT_OK) {
            //final Uri
            uri_id=data.getData();
              //uri_id=data.getData();
            /// System.out.print("uriiiii"+uri);

            StorageReference filepath = mStorage.child("Photos_id").child(uri_id.getLastPathSegment());
            filepath.putFile(uri_id).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(ProfileActivity.this, "تم رفع صوره البطاقه الشخصيه " , Toast.LENGTH_LONG).show();

                    // ImageView targetImageView = (ImageView) findViewById(R.id.imageViewDownload);
                    // String internetUrl = "https://firebasestorage.googleapis.com/v0/b/nakel-d3b81.appspot.com/o/Photos%2F23885?alt=media&token=502f35a9-0638-4194-af2a-c65d3f50";//"http://i.imgur.com/DvpvklR.png";

                    editTextPhoto_id.setText(downloadUrl.toString());
                    Glide
                            .with(ProfileActivity.this)
                            .load(downloadUrl)
                            .into(imageViewDownload_id);


                }
            });

        }


    }
    //  System.out.println(uri);
/*


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_intent_id && resultCode==RESULT_OK) {
            //final Uri
            uri_id=data.getData();
            //uri_id=data.getData();
            /// System.out.print("uriiiii"+uri);

            StorageReference filepath = mStorage.child("Photos").child(uri_id.getLastPathSegment());
            filepath.putFile(uri_id).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(ProfileActivity.this, "تم رفع صوره البطاقه الشخصيه " + uri_id, Toast.LENGTH_LONG).show();

                    // ImageView targetImageView = (ImageView) findViewById(R.id.imageViewDownload);
                    // String internetUrl = "https://firebasestorage.googleapis.com/v0/b/nakel-d3b81.appspot.com/o/Photos%2F23885?alt=media&token=502f35a9-0638-4194-af2a-c65d3f50";//"http://i.imgur.com/DvpvklR.png";

                    editTextPhoto_id.setText(uri_id.toString());
                    Glide
                            .with(ProfileActivity.this)
                            .load(uri_id)
                            .into(imageViewDownload_id);


                }
            });

        }}
*/
    //////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick(View view) {
        //if logout is pressed

        if(view == buttonSave){
            saveUserInformation();
        }


    }



}