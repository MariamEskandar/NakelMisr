package animal.nakel.com.nakel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Public_profile extends AppCompatActivity {
    private FirebaseUser mCurrentuser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView PName,Pphone,PNationalID,PcarNumber,PlicenseNumber;
    private  String mPublic_Profile_tkey = null;
    private ImageView imageViewPhotoDriver1;
    private String getActivity;
    private ImageView imageViewDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_profile);

        getActivity=getIntent().getExtras().getString("Accept_request_client");
        mPublic_Profile_tkey=getIntent().getExtras().getString("mPublic_Profile_tkey");

        mAuth= FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
//.child(mPublic_Profile_tkey)
   //     imageViewPhotoDriver1=(ImageView)findViewById(R.id.imageView3);

        Pphone=(TextView)findViewById(R.id.Pphone);
        PNationalID=(TextView)findViewById(R.id.PnationalNumber);
        PcarNumber=(TextView)findViewById(R.id.PcarNumber);
        PlicenseNumber=(TextView)findViewById(R.id.PlicenseNumber);
        PName=(TextView)findViewById(R.id.PName);
        imageViewDownload=(ImageView) findViewById(R.id.imageViewDownload5555);
    mDatabase.child(mPublic_Profile_tkey).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        String P_Name=(String)dataSnapshot.child("Name").getValue();
        String P_phone=(String)dataSnapshot.child("Phone_Number").getValue();
        String P_NationalID=(String)dataSnapshot.child("National_Number").getValue();
        String P_car_number=(String)dataSnapshot.child("Car_Number").getValue();
        String P_License=(String)dataSnapshot.child("License_Number").getValue();
  //      String photo=(String)dataSnapshot.child("photo").getValue();
        String photo=(String)dataSnapshot.child("photo").getValue().toString();
        Picasso.with(Public_profile.this).load(photo).into(imageViewDownload);
        Pphone.setText(P_phone);
        PNationalID.setText(P_NationalID);
        PcarNumber.setText(P_car_number);
        PlicenseNumber.setText(P_License);
        PName.setText(P_Name);
/*       Glide
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

             getActivity=getIntent().getExtras().getString("Accept_request");
            if(getActivity.equals("1")) {
                Intent intent = new Intent(Public_profile.this, Accept_Request.class);
                startActivity(intent);

            }
            if(getActivity.equals("2")) {
                Intent intent = new Intent(Public_profile.this, Accept_Request_Taxi.class);
                startActivity(intent);

            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
