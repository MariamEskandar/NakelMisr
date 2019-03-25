package animal.nakel.com.nakel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 0 ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.android.gms.common.SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private Button signOutButton;
    private TextView nameTextView;
    private TextView emailTextView;

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signInButton = (com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);
        signOutButton = (Button)findViewById(R.id.sign_out_button);
        nameTextView = (TextView)findViewById(R.id.name_text_view);
        emailTextView = (TextView)findViewById(R.id.email_text_view);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                signInButton.setVisibility(View.VISIBLE);

                signOutButton.setVisibility(View.GONE);

   /*             if (firebaseAuth.getCurrentUser() != null) {
                    //that means user is already logged in
                    //so close this activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
/////////////////////////////////////////////////////////////////
                    ////Check if user enter your data before  check type of user client or driver before
                    ////if there is data saved before
                    final FirebaseUser user1 = firebaseAuth.getCurrentUser();
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.addChildEventListener(new ChildEventListener() {
                        @Override

                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                            // System.out.println("cccc"+dataSnapshot.getKey());
                            // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                            if (dataSnapshot.child(user1.getUid()).getKey().equals(user1.getUid()) == true & dataSnapshot.child(user1.getUid()).getValue() != null)

                            {
                                String Type = dataSnapshot.child(user1.getUid()).child("Type").getValue().toString();
                                String Name = dataSnapshot.child(user1.getUid()).child("Name").getValue().toString();
                                if (Type.equals(""))  ///if your first time to eneter data
                                {
                                    startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
                                } else {
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


*/
                if (user != null) {


                    finish();
                    startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
/////////////////////////////////////////////////////////////////
                    ////Check if user enter your data before  check type of user client or driver before
                    ////if there is data saved before
                    final FirebaseUser user1 = firebaseAuth.getCurrentUser();
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.addChildEventListener(new ChildEventListener() {
                        @Override

                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                            // System.out.println("cccc"+dataSnapshot.getKey());
                            // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                            if (dataSnapshot.child(user1.getUid()).getKey().equals(user1.getUid()) == true & dataSnapshot.child(user1.getUid()).getValue() != null)

                            {
                                String Type = dataSnapshot.child(user1.getUid()).child("Type").getValue().toString();
                                String Name = dataSnapshot.child(user1.getUid()).child("Name").getValue().toString();
                                if (Type.equals(""))  ///if your first time to eneter data
                                {
                                    startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
                                } else {
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




                //        startActivity(new Intent(SignInActivity.this,MainActivity.class));
                    // User is signed in
               /*     Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if(user.getDisplayName() != null)
                        nameTextView.setText("HI " + user.getDisplayName().toString());
                    emailTextView.setText(user.getEmail().toString());
*/
               } //else {
                    // User is signed out
                   // startActivity(new Intent(SignInActivity.this,Type_Deriver_Client.class));
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
               // }
                // ...
            }
        };


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                signInButton.setVisibility(View.VISIBLE);
                                signOutButton.setVisibility(View.GONE);
                                emailTextView.setText(" ".toString());
                                nameTextView.setText(" ".toString());
                            }
                        });
            }
            // ..
        });

    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
/*
        FirebaseAuth firebaseAuth;


       if ( firebaseAuth.getCurrentUser()!= null) {


            finish();
            startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
/////////////////////////////////////////////////////////////////
            ////Check if user enter your data before  check type of user client or driver before
            ////if there is data saved before


        final FirebaseUser user1 = firebaseAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override

                public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                    // System.out.println("cccc"+dataSnapshot.getKey());
                    // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                    if (dataSnapshot.child(user1.getUid()).getKey().equals(user1.getUid()) == true & dataSnapshot.child(user1.getUid()).getValue() != null)

                    {
                        String Type = dataSnapshot.child(user1.getUid()).child("Type").getValue().toString();
                        String Name = dataSnapshot.child(user1.getUid()).child("Name").getValue().toString();
                        if (Type.equals(""))  ///if your first time to eneter data
                        {
                            startActivity(new Intent(getApplicationContext(), Type_Deriver_Client.class));
                        } else {
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


*/

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
