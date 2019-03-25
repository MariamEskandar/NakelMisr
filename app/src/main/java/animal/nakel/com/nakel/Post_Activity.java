package animal.nakel.com.nakel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Post_Activity extends AppCompatActivity {

    private EditText mPostTitie;
    private EditText mPostDescription;
    private EditText MoneyInoffer;
    private Button mSubmitbtn;
  //  private ImageButton mSelectImage;
    private static final  int GALLERY_REQUEST=1;
    private  Uri  imageUri=null;
    private ProgressDialog mProgres;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseComment;
    private Spinner spinnerFromhwere, spinnerToWhere,spinnerHavey;
    private DatabaseReference newpost;
    private Button btnSubmit;
    private TextView TimeView;
    private  Calendar c;
    private  FirebaseAuth mAuth;
    private FirebaseUser mCurrentuser;
    DatePicker datePicker;
    private DatabaseReference mDatabaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_);

        mAuth=FirebaseAuth.getInstance();
        mCurrentuser=mAuth.getCurrentUser();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog_Permation");

        mDatabaseUser=FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentuser.getUid());
////////////////////////////////////////////////////////////////////////////time and data/////////////////////////////////////////////////////
        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        // Now we display formattedDate value in TextView
     //   TimeView.setText("Current Date and Time : "+formattedDate);

       // setContentView(TimeView);
/////////////////////////////////////////////////////////////////////////////////*******************//////////////////////////////////////////
        mProgres=new ProgressDialog(this);
        mStorage= FirebaseStorage.getInstance().getReference();
        mPostTitie=(EditText)findViewById(R.id.titlepost);
        mPostDescription=(EditText)findViewById(R.id.postDiscription);
        MoneyInoffer=(EditText)findViewById(R.id.moneyInOffer);
        mSubmitbtn=(Button)findViewById(R.id.submet) ;
      //  mSelectImage=(ImageButton)findViewById(R.id.imageSelect);
        spinnerFromhwere = (Spinner) findViewById(R.id.spinner1);
        spinnerHavey = (Spinner) findViewById(R.id.spinnerHavey);
      //  TimeView =  (TextView)findViewById(R.id.TimeViewXMl) ;
        spinnerToWhere = (Spinner) findViewById(R.id.spinner2);
      datePicker= (DatePicker)findViewById(R.id.datePicker);
        // /////////////////////////////////////////////////////////////////********************/////////////////////////////////////////////////////////
/*        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });
        */
//////////////////////////////////////////////////////////////////////////////****************************/////////////////////////////////////
        mSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startpost();
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////*************************////////////////////////////////////////////
            private void startpost(){

            addListenerOnButton();

                final String TitleValue=mPostTitie.getText().toString().trim();
            final String DescriptionValue=mPostDescription.getText().toString().trim();
            final String Moneyinoffer=MoneyInoffer.getText().toString().trim();
            final String fromlocation=spinnerFromhwere.getSelectedItem().toString().trim();
            final String toLocation=spinnerToWhere.getSelectedItem().toString().trim();
            final String weight=spinnerHavey.getSelectedItem().toString().trim();
            final String currentimeTime=c.getTime().toString().trim();

                int day = datePicker.getDayOfMonth();

                int month = datePicker.getMonth()  ;

                int year = datePicker.getYear()-1900;

                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

                Date d = new Date(year, month,day );

                final String strDate = dateFormatter.format(d).toString();

          if (TextUtils.isEmpty(TitleValue)){
              Toast.makeText(this,"رجاء ادخال العنوان",Toast.LENGTH_LONG).show();
              return;
          }

                if (TextUtils.isEmpty(DescriptionValue)){
                    Toast.makeText(this,"رجاء ادخال التفاصيل ",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(Moneyinoffer)){
                    Toast.makeText(this,"رجاء ادخال السعر ",Toast.LENGTH_LONG).show();
                    return;
                }
            if (!TextUtils.isEmpty(TitleValue)&&!TextUtils.isEmpty(DescriptionValue)&&!TextUtils.isEmpty(Moneyinoffer))
            {

                mProgres.setMessage("جارى نشر العرض ...");
                mProgres.show();
                newpost=mDatabase.push();

                newpost.child("Title").setValue(TitleValue);
                newpost.child("Description").setValue(DescriptionValue);
                newpost.child("MoneyOffere").setValue(Moneyinoffer);
                newpost.child("FromWhere").setValue(fromlocation);
                newpost.child("ToLocation").setValue(toLocation);
                newpost.child("Weight").setValue(weight);
                newpost.child("PostTime").setValue(currentimeTime);
                newpost.child("dayToMove").setValue(strDate);
                newpost.child("uid").setValue(mCurrentuser.getUid());

                mDatabaseUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String Username =(String)dataSnapshot.child("Name").getValue();
                        newpost.child("Username").setValue(Username).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Intent intents=new Intent(Post_Activity.this,PostsMain.class);
                                    startActivity(intents);
                                }
                            }
                        });                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }


////////////////////////////////////////////////////////////////////*******************//////////////////////////////////////////////////
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==GALLERY_REQUEST && resultCode == RESULT_OK) {
        imageUri = data.getData();
            mSelectImage.setImageURI(imageUri);
        }
        }
        */
    ////////////////////////////////////////////////////////////////////////*******Snipping********//////////////////////////////////////////////
    public void addListenerOnSpinnerItemSelection(){

        spinnerFromhwere.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinnerHavey.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        spinnerToWhere.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
    //get the selected dropdown list value
   public void addListenerOnButton() {
       spinnerFromhwere = (Spinner) findViewById(R.id.spinner1);
       spinnerToWhere = (Spinner) findViewById(R.id.spinner2);
       spinnerHavey = (Spinner) findViewById(R.id.spinnerHavey);

   }
}
