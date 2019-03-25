package animal.nakel.com.nakel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
//import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.snapshot.EmptyNode;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SinglePost extends AppCompatActivity {
    private String mPos_tkey = null;
    private TextView SingpostTitle;
    private TextView SingpostDescription;
    private TextView SingpostMoney;
    private TextView SingpostIduser;
    private TextView SingpostWeight;
    private TextView SingpostFromwhere;
    private TextView SingpostTowhere;
    private TextView SingpostPostTime;
    private TextView SingpostDaytomove;
    private EditText CommentEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentuser;
    private FirebaseUser mCurrentuserName;
    private Firebase fire;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private Query mQueryCurrentUser;
    private Query mQuerycomment;

    private DatabaseReference mDatabase;
    private DatabaseReference mmDatabaseCurrentUser;
    private DatabaseReference mDatabaseCurrentUser;
    private DatabaseReference mDatabaseUser;
    private DatabaseReference mmDatabase;
    private DatabaseReference SendComment;
    private DatabaseReference SendAcceptComment;
    private DatabaseReference CommentmmDatabase;
    private DatabaseReference mmDatabaseAcceptReqest;

    private Button addComment;
    private Button AccpetCommentq;
    private TextView CommentView;
    private Button RemovePost;
    private String CommentEt;
    private RecyclerView mPrice;
    private Button Commentonly;
    private TextView Comment__Done;
    private DatabaseReference Check_If_Comment;
    private TextView ClientDone;
    private TextView  Accept_Done;
    private  String getActivity_Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_post);
        getActivity_Number=getIntent().getExtras().getString("send_post");
        //   onStart();
        ClientDone=(TextView)findViewById(R.id.Client_Done);
        ///////////////////////////////////////////////////////*********RecyclerViwe mComment*************/////*//////////////
        mPos_tkey = getIntent().getExtras().getString("blog_id");
        Commentonly = (Button) findViewById(R.id.CommentOnly);
        Commentonly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SinglePost.this, Comment_only.class);
                intent.putExtra("mpost_key", mPos_tkey = getIntent().getExtras().getString("blog_id"));
                //      Toast.makeText(SinglePost.this,mPos_tkey,Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
        //    mPos_tkey = getIntent().getExtras().getString("blog_id");
        /////////////////////////////////////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();
        Comment__Done = (TextView) findViewById(R.id.Comment_Done);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child("Name");

        CommentmmDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").child(mPos_tkey).child("Comment").child(mCurrentuser.getUid());
        Check_If_Comment = FirebaseDatabase.getInstance().getReference().child("Blog").child(mPos_tkey).child("Comment");
        //.child("Comment")
        mmDatabaseAcceptReqest = FirebaseDatabase.getInstance().getReference().child("AccpetRequest");
        mmDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Users");

        SendComment = FirebaseDatabase.getInstance().getReference().child("Comment");

        //  mPos_tkey = getIntent().getExtras().getString("blog_id");

        addComment = (Button) findViewById(R.id.ButtonComment);
        CommentEditText = (EditText) findViewById(R.id.Commentt);
        CommentEt = CommentEditText.getText().toString().trim();

        RemovePost = (Button) findViewById(R.id.Remove_Post);
        Accept_Done=(TextView)findViewById(R.id.Acccept_Done);
        RemovePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(mPos_tkey).removeValue();
                Intent intent = new Intent(SinglePost.this, PostsMain.class);
                startActivity(intent);
            }
        });

//////////////////////////////////////////////////////////////////////***************Button Comment ***************//////////////////////////////

        addComment.setOnClickListener(new View.OnClickListener() {
            //    final String  CommentEt=CommentEditText.getText().toString().trim();
            @Override
            public void onClick(View v) {
                String Username_Taxi;
                if (!TextUtils.isEmpty(CommentEditText.getText())) {

                    mmDatabaseCurrentUser.child(mCurrentuser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String Taxi_Name = (String) dataSnapshot.child("Name").getValue();
                            String Taxi_Phone_Number = (String) dataSnapshot.child("Phone_Number").getValue();

                            //   SendComment = CommentmmDatabase.push();

                            CommentmmDatabase.child("Price").setValue(CommentEditText.getText().toString().trim());
                            CommentmmDatabase.child("Taxe_uid").setValue(mCurrentuser.getUid());
                            CommentmmDatabase.child("Post_id").setValue(mPos_tkey = getIntent().getExtras().getString("blog_id"));
                            CommentmmDatabase.child("Name").setValue(Taxi_Name.toString().trim());
                            CommentmmDatabase.child("Phone_Number").setValue(Taxi_Phone_Number.toString().trim());

//                            SendComment.push();
  //                          SendComment.child("Price").setValue(CommentEditText.getText().toString().trim());
    //                        SendComment.child("Taxe_uid").setValue(mCurrentuser.getUid());
      //                      SendComment.child("Post_id").setValue(mPos_tkey = getIntent().getExtras().getString("blog_id"));
        //                    SendComment.child("Name").setValue(Taxi_Name.toString().trim());
          //                  SendComment.child("Phone_Number").setValue(Taxi_Phone_Number.toString().trim());

                            Toast.makeText(SinglePost.this, "جارى ارسال السعر للعميل", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    Toast.makeText(SinglePost.this, "ادخل السعر الخاص بكل للطلب المقدم ", Toast.LENGTH_LONG).show();
                }
            }
        });


////////////////////////////////////////////////////////////////////////////////////////////*****Previw Selected post post**********//////////////////////////////////////

        SingpostTitle = (TextView) findViewById(R.id.Single_post_Post_title_BlogViwe);
        SingpostDescription = (TextView) findViewById(R.id.Single_post_Description_title_BlogViwe);
        SingpostDaytomove = (TextView) findViewById(R.id.Single_post_textdaymove);
        SingpostPostTime = (TextView) findViewById(R.id.Single_post_PostTime);
        SingpostTowhere = (TextView) findViewById(R.id.Single_post_TopWhere_title_BlogViwe);
        SingpostFromwhere = (TextView) findViewById(R.id.Single_post_Fromwhere_title_BlogViwe);
        SingpostWeight = (TextView) findViewById(R.id.Single_post_Weight);
        SingpostMoney = (TextView) findViewById(R.id.Single_post_MonyOffer_title_BlogViwe);


        mDatabase.child(mPos_tkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Post_title = (String) dataSnapshot.child("Title").getValue();
                String Post_description = (String) dataSnapshot.child("Description").getValue();
                String Post_money = (String) dataSnapshot.child("MoneyOffere").getValue();
                String Post_uid = (String) dataSnapshot.child("uid").getValue();
                String Post_fromwhere = (String) dataSnapshot.child("FromWhere").getValue();
                String Post_towhere = (String) dataSnapshot.child("ToLocation").getValue();
                String Post_weight = (String) dataSnapshot.child("Weight").getValue();
                String Post_posttime = (String) dataSnapshot.child("PostTime").getValue();
                String Post_daytomove = (String) dataSnapshot.child("dayToMove").getValue();

                String PostPrice = (String) dataSnapshot.child("Price").getValue();

                CommentEditText.setText(PostPrice);

                SingpostMoney.setText(Post_money);
                SingpostWeight.setText(Post_weight);
                SingpostFromwhere.setText(Post_fromwhere);
                SingpostTowhere.setText(Post_towhere);
                SingpostPostTime.setText(Post_posttime);
                SingpostDaytomove.setText(Post_daytomove);
                SingpostDescription.setText(Post_description);
                SingpostTitle.setText(Post_title);


                if (mCurrentuser.getUid().equals(Post_uid)) {

mmDatabaseAcceptReqest.addValueEventListener(new ValueEventListener() {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mPos_tkey = getIntent().getExtras().getString("blog_id");

        if (dataSnapshot.hasChild(mPos_tkey)){
        RemovePost.setVisibility(View.GONE);
        Commentonly.setVisibility(View.GONE);
        addComment.setVisibility(View.GONE);
        CommentEditText.setVisibility(View.GONE);
        Accept_Done.setVisibility(View.VISIBLE);
    }

    else{
        RemovePost.setVisibility(View.VISIBLE);
        Commentonly.setVisibility(View.VISIBLE);
        addComment.setVisibility(View.GONE);
        CommentEditText.setVisibility(View.GONE);
    }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});        } else {

                    mmDatabaseCurrentUser.child(mCurrentuser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String User_Type = (String) dataSnapshot.child("Type").getValue();

                            if (User_Type.equals("1")){
                                Check_If_Comment.addValueEventListener(new ValueEventListener() {
                               @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String Taxe_id= (String) dataSnapshot.child("Comment").child(mCurrentuser.getUid()).child("Taxe_uid").getValue();
                                        if (dataSnapshot.hasChild(mCurrentuser.getUid())){
                                            RemovePost.setVisibility(View.GONE);
                                            Commentonly.setVisibility(View.GONE);
                                            addComment.setVisibility(View.GONE);
                                            CommentEditText.setVisibility(View.GONE);
                                            Comment__Done.setVisibility(View.VISIBLE);
                                        }

                                    else {
                                            RemovePost.setVisibility(View.GONE);
                                            Commentonly.setVisibility(View.GONE);
                                            addComment.setVisibility(View.VISIBLE);
                                            CommentEditText.setVisibility(View.VISIBLE);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
   }
                            else if(User_Type.equals("2")) {

                                RemovePost.setVisibility(View.GONE);
                                Commentonly.setVisibility(View.GONE);
                                addComment.setVisibility(View.GONE);
                                CommentEditText.setVisibility(View.GONE);
                                ClientDone.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            }
 public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            getActivity_Number=getIntent().getExtras().getString("send_post");
try {
    if(getActivity_Number.equals("1")) {
        Intent intent = new Intent(SinglePost.this, Accept_Request.class);
        startActivity(intent);

    }
    if(getActivity_Number.equals("2")) {
        Intent intent = new Intent(SinglePost.this, Accept_Request_Taxi.class);
        startActivity(intent);

    }if(getActivity_Number.equals("3")){

        Intent intent = new Intent(SinglePost.this, PostsMain.class);
        startActivity(intent);
    }
}catch (Exception e){
    Intent intent = new Intent(SinglePost.this, PostsMain.class);
    startActivity(intent);
}
        return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}