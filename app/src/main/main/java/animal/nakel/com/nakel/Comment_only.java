package animal.nakel.com.nakel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Comment_only extends AppCompatActivity {
    private FirebaseUser mCurrentuser;
    private DatabaseReference mmDatabaseAcceptReqest;
    private DatabaseReference mDatabaseTaxiAcceptRequest;
    private DatabaseReference SendAcceptTaxiRequest;
    private DatabaseReference SendAcceptComment;
    private DatabaseReference  mDatabaseClienAccepttRequest;
    private DatabaseReference  SendClienAccepttRequest;
    private RecyclerView mPrice;
    private String mPos_tkey = null;
    private DatabaseReference CommentmmDatabase;
    private DatabaseReference User_Money_Database;
    private DatabaseReference User_Money_Database_Push;
    private FirebaseAuth mAuth;
    private Button Accpet_Price;
   // TextView ClickbleName;
    private String Taxi;
   // TextView PPhone_Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_only);
/////////////////////////////////////////////////////////////////////***Recycler Layout****//////////////////////
        mPrice = (RecyclerView) findViewById(R.id.RecyclerpPrice);
        mPrice.setHasFixedSize(true);
        mPrice.setLayoutManager(new LinearLayoutManager(this));
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        mAuth = FirebaseAuth.getInstance();
        mCurrentuser=mAuth.getCurrentUser();
        mPos_tkey = getIntent().getExtras().getString("mpost_key");
        CommentmmDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").child(mPos_tkey).child("Comment");
        Accpet_Price=(Button)findViewById(R.id.acceptcommentrequest);
        mmDatabaseAcceptReqest= FirebaseDatabase.getInstance().getReference().child("AccpetRequest");


 /////////////////////////////////////////////////****on click name *****///////////////////////////
     //   ClickbleName = (TextView)findViewById(R.id.Price_UserName_id);
       // PPhone_Number = (TextView)findViewById(R.id.Taxe_Phone_Number);
   }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onStart() {
        super.onStart();

        Query query = CommentmmDatabase.orderByKey();
        FirebaseRecyclerAdapter<Comment,CommentViweHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Comment, CommentViweHolder>(
                Comment.class,
                R.layout.comment_row,
                CommentViweHolder.class,
                query
                //CommentmmDatabase
        ) {
            @Override
            protected void populateViewHolder(CommentViweHolder viewHolder, final Comment model, int position) {

                viewHolder.setPrice(model.getPrice());
                viewHolder.setPost_id(model.getPost_id());
                viewHolder.setTaxe_uid(model.getTaxe_uid());
//                viewHolder.setName(model.getName());
               viewHolder.setPhone_Number(model.getPhone_Number());


                        viewHolder.ClickbleName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
  //                   ClickbleName = (TextView)findViewById(R.id.Price_UserName_id);

    //              final String Taxi_Name=ClickbleName.getText().toString().trim();

                        TextView pPost_id=(TextView)findViewById(R.id.Price_Post_Id);

                        final String Post_Id=pPost_id.getText().toString().trim();

                        Intent intent = new Intent(Comment_only.this,Public_Profile_Price.class);

                         intent.putExtra("mPublic_Profile_tkey",model.getTaxe_uid());

                        intent.putExtra("mpost_key_id",Post_Id);
                        //Toast.makeText(Comment_only.this,Post_Id,Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });



                viewHolder.Accpet_Price.setOnClickListener(new View.OnClickListener() {
                    @Override

               //     String cc=(String)model.getPhone_Number();
                    public void onClick(View v) {

     AlertDialog.Builder mBuilder = new AlertDialog.Builder(Comment_only.this);
                mBuilder.setTitle("هل ترغب فعلا بالموافقه على عرض هذا السائق");
                mBuilder.setPositiveButton("تاكيد الموافقه ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                         TextView pPost_id=(TextView)findViewById(R.id.Price_Post_Id);

                        final String Post_Id=pPost_id.getText().toString().trim();

                        SendAcceptComment=mmDatabaseAcceptReqest.child(Post_Id);

                        SendAcceptComment.child("Price").setValue(model.getPrice());
                        SendAcceptComment.child("Client_uid").setValue(mCurrentuser.getUid());
                        SendAcceptComment.child("Taxe_uid").setValue(model.getTaxe_uid());
                        SendAcceptComment.child("Post_id").setValue( mPos_tkey = getIntent().getExtras().getString("mpost_key"));
                        SendAcceptComment.child("Taxi_Phone").setValue(model.getPhone_Number());

                        User_Money_Database=FirebaseDatabase.getInstance().getReference().child("Users").child(model.getTaxe_uid()).child("Price").child(mPos_tkey = getIntent().getExtras().getString("mpost_key"));

                        SendAcceptComment=User_Money_Database_Push=User_Money_Database;
                        SendAcceptComment.child("Price").setValue(model.getPrice());
                        SendAcceptComment.child("Post_id").setValue( mPos_tkey = getIntent().getExtras().getString("mpost_key"));

                        Intent intent =new Intent(Comment_only.this,Show_Number.class);
                        intent.putExtra("Number",model.getPhone_Number());
                        intent.putExtra("Price",model.getPrice());
                        intent.putExtra("mpost_key_id",Post_Id);
                        startActivity(intent);

                        Toast.makeText(Comment_only.this, "تم تاكيد عمليه الموافقه على سعر هذا السائق", Toast.LENGTH_LONG).show();


                }
            });


                mBuilder.setNegativeButton("لا اوافق", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            //mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
                dialog.show();


    /*

                       TextView pPost_id=(TextView)findViewById(R.id.Price_Post_Id);

                        final String Post_Id=pPost_id.getText().toString().trim();

                        SendAcceptComment=mmDatabaseAcceptReqest.child(Post_Id);

                        SendAcceptComment.child("Price").setValue(model.getPrice());
                        SendAcceptComment.child("Client_uid").setValue(mCurrentuser.getUid());
                        SendAcceptComment.child("Taxe_uid").setValue(model.getTaxe_uid());
                        SendAcceptComment.child("Post_id").setValue( mPos_tkey = getIntent().getExtras().getString("mpost_key"));
                        SendAcceptComment.child("Taxi_Phone").setValue(model.getPhone_Number());

                        User_Money_Database=FirebaseDatabase.getInstance().getReference().child("Users").child(model.getTaxe_uid()).child("Price").child(mPos_tkey = getIntent().getExtras().getString("mpost_key"));

                        SendAcceptComment=User_Money_Database_Push=User_Money_Database;
                        SendAcceptComment.child("Price").setValue(model.getPrice());
                        SendAcceptComment.child("Post_id").setValue( mPos_tkey = getIntent().getExtras().getString("mpost_key"));

                        Intent intent =new Intent(Comment_only.this,Show_Number.class);
                        intent.putExtra("Number",model.getPhone_Number());
                        intent.putExtra("Price",model.getPrice());
                        intent.putExtra("mpost_key_id",Post_Id);
                        startActivity(intent);

                        Toast.makeText(Comment_only.this, "تم تاكيد عمليه الموافقه على سعر هذا السائق", Toast.LENGTH_LONG).show();
*/
                    }
                });

            }
        };
        mPrice.setAdapter(firebaseRecyclerAdapter);

    }





                        /////DataBAse sort Data Fore Taxi
/*
                        mDatabaseTaxiAcceptRequest=FirebaseDatabase.getInstance().getReference().child("AccpetTaxiRequest").child(model.getTaxe_uid());
                        SendAcceptComment=mDatabaseTaxiAcceptRequest.child(Post_Id);


                        SendAcceptComment.child("Price").setValue(model.getPrice());
                        SendAcceptComment.child("Client_uid").setValue(mCurrentuser.getUid());
                        SendAcceptComment.child("Taxe_uid").setValue(model.getTaxe_uid());
                        SendAcceptComment.child("Post_id").setValue( mPos_tkey = getIntent().getExtras().getString("mpost_key"));
                        SendAcceptComment.child("Taxi_Phone").setValue(model.getPhone_Number());
*/
/*
                        // Make Data Base For Client Comment Accept
                        mDatabaseClienAccepttRequest=FirebaseDatabase.getInstance().getReference().child("AccptClientRequest");
                        SendAcceptComment=mDatabaseClienAccepttRequest.child(mCurrentuser.getUid());
                        SendAcceptComment.child("Price").setValue(model.getPrice());
                        SendAcceptComment.child("Client_uid").setValue(mCurrentuser.getUid());
                        SendAcceptComment.child("Taxe_uid").setValue(model.getTaxe_uid());
                        SendAcceptComment.child("Post_id").setValue( mPos_tkey = getIntent().getExtras().getString("mpost_key"));
                        SendAcceptComment.child("Taxi_Phone").setValue(model.getPhone_Number());
*/
 public static class CommentViweHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView ClickbleName;
        TextView post_Name_View_All_offer;
        TextView Price ;
        Button Accpet_Price;
        TextView PPhone_Number;

     public CommentViweHolder(View itemViwe) {

            super(itemViwe);
            mView = itemView;
            ClickbleName = (TextView)mView.findViewById(R.id.Price_UserName_id);
            post_Name_View_All_offer = (TextView) mView.findViewById(R.id.Price_UserName);
            Accpet_Price=(Button)mView.findViewById(R.id.acceptcommentrequest);
            Price=(TextView)mView.findViewById(R.id.Price_Money);
            Price.getText().toString().trim();
             PPhone_Number = (TextView)mView.findViewById(R.id.Taxe_Phone_Number);

        }
     public void setName(String name)
     {
         TextView Name_Taxi = (TextView) mView.findViewById(R.id.Post_UserName);
         //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
         Name_Taxi.setText(name);
  }

     public  void setTaxe_uid(String taxe_uid)
        {
            TextView Ptaxi_uid=(TextView)mView.findViewById(R.id.Price_UserName_id);
            Ptaxi_uid.setText(taxe_uid);
        }

        public  void setPost_id(String post_id)
        {
            TextView pPost_id=(TextView)mView.findViewById(R.id.Price_Post_Id);
            pPost_id.setText(post_id);
        }

        public void setPrice(String price) {
            TextView PPrice = (TextView) mView.findViewById(R.id.Price_Money);
            PPrice.setText(price);
        }
     public void setPhone_Number(String phone_Number) {
         TextView PPhone_Number = (TextView) mView.findViewById(R.id.Taxe_Phone_Number);
         PPhone_Number.setText(phone_Number);
     }


 }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Comment_only.this, SinglePost.class);
            intent.putExtra("blog_id",mPos_tkey);
//            Toast.makeText(Comment_only.this,mPos_tkey,Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public  void setTaxe_uid(String taxe_uid)
    {
        TextView Ptaxi_uid=(TextView)findViewById(R.id.Price_UserName_id);
        Ptaxi_uid.setText(taxe_uid);
   Taxi=Ptaxi_uid.getText().toString().trim();
    }

}

