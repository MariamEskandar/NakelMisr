package animal.nakel.com.nakel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

/*this activity used to login by email and password then go to profileview*/

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //defining views
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private Button GoogleSingIn;
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;
    private DatabaseReference mDatabase;
    private GoogleApiClient mGoogleApiClient;
    private TextView ResetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSingIn=(Button)findViewById(R.id.GmailSingin);

        ResetPassword=(TextView)findViewById(R.id.Reset_Passowrd);
        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(intent);


            }
        });




        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            startActivity(new Intent(getApplicationContext(), ProfileView.class));
        }



        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup  = (TextView) findViewById(R.id.textViewSignUp);

        progressDialog = new ProgressDialog(this);

        //attaching click listener
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Login Please Wait......");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){

                            //start the profile activity
                            finish();
                            //   startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            // startActivity(new Intent(getApplicationContext(),Post_Activity.class));
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            mDatabase= FirebaseDatabase.getInstance().getReference();
                            mDatabase.addChildEventListener(new ChildEventListener() {


                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                                    // System.out.println("cccc"+dataSnapshot.getKey());
                                    // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                                    if (dataSnapshot.child(user.getUid()).getKey().equals(user.getUid())==true  & dataSnapshot.child(user.getUid()).getValue() != null)

                                    {

                                        String Type=dataSnapshot.child(user.getUid()).child("Type").getValue().toString();
                                        String Name=dataSnapshot.child(user.getUid()).child("Name").getValue().toString();
                                        if (Type.equals("") )  ///if your first time to eneter data
                                        {

                                            startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
                                       //     progressDialog2.dismiss();
                                        }


                                        else
                                        {
                                            startActivity(new Intent(getApplicationContext(), PostsMain.class));
                                     //   progressDialog2.dismiss();
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



                            progressDialog.setMessage("Login Please Wait......");
                            progressDialog.show();

                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){


            userLogin();

        }

        if(view == textViewSignup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }



    }


}

