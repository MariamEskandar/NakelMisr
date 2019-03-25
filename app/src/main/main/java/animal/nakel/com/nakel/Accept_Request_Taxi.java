package animal.nakel.com.nakel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Accept_Request_Taxi extends AppCompatActivity {

    private RecyclerView mRecycler_Accept_Request;
    private DatabaseReference mDatabaseClienAccepttRequest;
    private DatabaseReference mDatabaseTaxiAcceptRequest;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept__request__taxi);
        mRecycler_Accept_Request = (RecyclerView) findViewById(R.id.Recycler_Accept_Request_Taxi);
        mRecycler_Accept_Request.setHasFixedSize(true);
        mRecycler_Accept_Request.setLayoutManager(new LinearLayoutManager(this));


        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();
        mDatabaseClienAccepttRequest = FirebaseDatabase.getInstance().getReference().child("AccpetRequest");

    }


    public void onStart() {
        super.onStart();


        mRecycler_Accept_Request = (RecyclerView) findViewById(R.id.Recycler_Accept_Request_Taxi);
        mRecycler_Accept_Request.setHasFixedSize(true);
        mRecycler_Accept_Request.setLayoutManager(new LinearLayoutManager(this));

        Query query = mDatabaseClienAccepttRequest.orderByChild("Taxe_uid").equalTo(mCurrentuser.getUid());

        FirebaseRecyclerAdapter<AcceptRequest, Accept_Request.Accept_RequestViweHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AcceptRequest, Accept_Request.Accept_RequestViweHolder>(
                AcceptRequest.class,
                R.layout.accept_reqest_taxi_row,
                Accept_Request.Accept_RequestViweHolder.class,
                //mDatabaseClienAccepttRequest
                query
        ) {

            @Override
            protected void populateViewHolder(Accept_Request.Accept_RequestViweHolder viewHolder, final AcceptRequest model, int position) {

                viewHolder.setPost_id(model.getPost_id());
                viewHolder.setClient_uid(model.getClient_uid());
                viewHolder.setTaxe_uid(model.getTaxe_uid());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setTaxi_Phone(model.getTaxi_Phone());

                viewHolder.Post_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Accept_Request_Taxi.this,"العرض",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(Accept_Request_Taxi.this,SinglePost.class);
                        intent.putExtra("send_post","2");
                        intent.putExtra("blog_id",model.getPost_id());

                        startActivity(intent);
                    }
                });


                viewHolder.client.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Accept_Request_Taxi.this, Public_Profile_Taxi.class);

                        intent.putExtra("mPublic_Profile_tkey", model.getClient_uid());
                        intent.putExtra("Accept_request","2");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                viewHolder.Taxi_id.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent intent = new Intent(Accept_Request_Taxi.this, Public_profile.class);
                        intent.putExtra("mPublic_Profile_tkey", model.getTaxe_uid());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        };
        mRecycler_Accept_Request.setAdapter(firebaseRecyclerAdapter);
    }

    public static class Accept_RequestViweHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView Taxi_id ;
        TextView client;
        TextView Post_id;
        public Accept_RequestViweHolder(View itemViwe) {

            super(itemViwe);
            mView = itemView;
            Taxi_id = (TextView) mView.findViewById(R.id.Accept_Rreqest_Taxe_id);
            client = (TextView) mView.findViewById(R.id.Accept_Rreqest_UserName_id);
            Post_id = (TextView) mView.findViewById(R.id.Accept_Rreqest_Post_Id);
        }

        public void setClient_uid(String client_uid) {
            TextView E_Accept_Rreqest_UserName_id = (TextView) mView.findViewById(R.id.Accept_Rreqest_UserName_id);
            //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
            E_Accept_Rreqest_UserName_id.setText(client_uid);
        }


        public void setPost_id(String post_id) {
            TextView E_Accept_Rreqest_Post_Id = (TextView) mView.findViewById(R.id.Accept_Rreqest_Post_Id);
            //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
            E_Accept_Rreqest_Post_Id.setText(post_id);
        }

        public void setPrice(String price) {
            TextView E_Accept_Rreqest_Money = (TextView) mView.findViewById(R.id.Accept_Rreqest_Money);
            //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
            E_Accept_Rreqest_Money.setText(price);
        }

        public void setTaxe_uid(String taxe_uid) {
            TextView E_Accept_Rreqest_Taxe_id = (TextView) mView.findViewById(R.id.Accept_Rreqest_Taxe_id);
            //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
            E_Accept_Rreqest_Taxe_id.setText(taxe_uid);
        }

        public void setTaxi_Phone(String taxe_phone) {
            TextView E_Accept_Rreqest_Taxe_Phone_Number = (TextView) mView.findViewById(R.id.Accept_Rreqest_Taxe_Phone_Number);
            //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
            E_Accept_Rreqest_Taxe_Phone_Number.setText(taxe_phone);
        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Accept_Request_Taxi.this, PostsMain.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}