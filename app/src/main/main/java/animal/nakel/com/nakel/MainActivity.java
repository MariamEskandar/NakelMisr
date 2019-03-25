


package animal.nakel.com.nakel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*this activity used to register by username and password and save it and used this id of email to save data of user with the same i*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;
    private ProgressDialog progressDialog3;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private FirebaseUser mCurrentuser;
        //defining firebaseauth object
 //   private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase_User;
    private Button Gmail_LogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog2 = new ProgressDialog(this);
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){

            progressDialog2.setMessage("جارى التاكد من عمليه التسجيل ...");
            progressDialog2.show();
            mDatabase= FirebaseDatabase.getInstance().getReference();

            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final FirebaseUser user = firebaseAuth.getCurrentUser();

                    // System.out.println("cccc"+dataSnapshot.getKey());
                    // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                    if (dataSnapshot.child(user.getUid()).getKey().equals(user.getUid())==true  & dataSnapshot.child(user.getUid()).getValue() != null)

                    {


                        String Type=dataSnapshot.child(user.getUid()).child("Type").getValue().toString().trim();

                        String Name=dataSnapshot.child(user.getUid()).child("Name").getValue().toString();
                        if (Type.equals("") )  ///if your first time to eneter data
                        {
                            //progressDialog2.dismiss();

                            startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
                        }

                        if (Type.equals("1")||Type.equals("2"))
                        {

                      if(dataSnapshot.child(user.getUid()).hasChild("Block")) {

                            String Block_Stutue=dataSnapshot.child(user.getUid()).child("Block").getValue().toString();

                             if(Block_Stutue.equals("Enable"))
                         {
                                startActivity(new Intent(getApplicationContext(),PostsMain.class));
        //Toast.makeText(MainActivity.this,"Enable",Toast.LENGTH_SHORT).show();
                                    }
                                if(Block_Stutue.equals("Block"))
                        {
                            startActivity(new Intent(getApplicationContext(),Block.class));

       // Toast.makeText(MainActivity.this,"Block",Toast.LENGTH_SHORT).show();
                                    }
                          }
                           if (!dataSnapshot.child(user.getUid()).hasChild("Block")){
                               startActivity(new Intent(getApplicationContext(),PostsMain.class));
                           }


                        }
                    }

                    else
                    // (!dataSnapshot.child(user.getUid()).hasChild("Block"))
                    {startActivity(new Intent(getApplicationContext(),Type_Deriver_Client.class));
                        //   Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT).show();
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




            //that means user is already logged in
            //so close this activity
//                  finish();

  //          startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
/////////////////////////////////////////////////////////////////
            ////Check if user enter your data before  check type of user client or driver before
            ////if there is data saved before
         //   mCurrentuser = firebaseAuth.getCurrentUser();
            ////////////////////////////////////////////////////////
            //and open profile activity
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"ادخل الايميل الخاص الصحيح",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"ادخل الرقم السرى صحيح",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("جارى تسجيل البيانات...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"خطأ فى التسجيل",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

  }

