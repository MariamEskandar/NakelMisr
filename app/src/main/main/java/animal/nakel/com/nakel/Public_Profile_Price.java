package animal.nakel.com.nakel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Public_Profile_Price extends AppCompatActivity {
    private FirebaseUser mCurrentuser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView PName,Pphone,PNationalID,PcarNumber,PlicenseNumber;
    private  String mPublic_Profile_tkey = null;
    private  String mPublic_Profile_Post_id = null;
    String PostId;
    private ImageView imageViewPhotoDriver1;
TextView PostIdTV;

    private ImageView imageViewDownload;
    private EditText  editTextPhoto;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public__profile__price);
//post id from commentonly class

       // imageViewPhotoDriver1=(ImageView)findViewById(R.id.imageViewkkkk);
        imageViewDownload=(ImageView) findViewById(R.id.imageViewDownload5555);
        editTextPhoto=(EditText)findViewById(R.id.editPhoto);

        mPublic_Profile_Post_id=getIntent().getExtras().getString("mpost_key_id");
    //    Toast.makeText(Public_Profile_Price.this,PostId,Toast.LENGTH_SHORT).show();
//Id for any one(User) send from postesMain or Comment_Only.Class
        mPublic_Profile_tkey=getIntent().getExtras().getString("mPublic_Profile_tkey");

        mAuth= FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
//.child(mPublic_Profile_tkey)

        PostIdTV=(TextView)findViewById(R.id.Postid);
        PNationalID=(TextView)findViewById(R.id.PnationalNumber);
        PcarNumber=(TextView)findViewById(R.id.PcarNumber);
        PlicenseNumber=(TextView)findViewById(R.id.PlicenseNumber);
        PName=(TextView)findViewById(R.id.PName);
        Pphone=(TextView)findViewById(R.id.Pphone);
        imageViewDownload=(ImageView) findViewById(R.id.imageViewDownload5555);
        mDatabase.child(mPublic_Profile_tkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String P_Name=(String)dataSnapshot.child("Name").getValue();
                String P_phone=(String)dataSnapshot.child("Phone_Number").getValue();
                String P_NationalID=(String)dataSnapshot.child("National_Number").getValue();
                String P_car_number=(String)dataSnapshot.child("Car_Number").getValue();
                String P_License=(String)dataSnapshot.child("License_Number").getValue();

                String photo=(String)dataSnapshot.child("photo").getValue().toString();
                Picasso.with(Public_Profile_Price.this).load(photo).into(imageViewDownload);



                PostId=mPublic_Profile_Post_id.toString().trim();
                //String photo=dataSnapshot.child(user.getUid()).child("photo").getValue().toString();
             /*   editTextPhoto.setText(photo);

                System.out.println("تم رفع صوره السياره"+photo);
                Glide
                        .with(Public_Profile_Price.this)
                        .load(photo)
                        .into(imageViewDownload);
*/
                System.out.println("jjjj"+photo);
                PostIdTV.setText(PostId);
                Pphone.setText(P_phone);
                PNationalID.setText(P_NationalID);
                PcarNumber.setText(P_car_number);
                PlicenseNumber.setText(P_License);
                PName.setText(P_Name);
/*                Glide
                        .with(getApplicationContext())
                        .load(photo)
                        .into(imageViewPhotoDriver1);
*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Public_Profile_Price.this, Comment_only.class);
            intent.putExtra("mpost_key",PostId);
  //          Toast.makeText(Public_Profile_Price.this,PostId,Toast.LENGTH_SHORT).show();
            startActivity(intent);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}




