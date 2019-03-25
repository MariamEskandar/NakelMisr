package animal.nakel.com.nakel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static animal.nakel.com.nakel.R.id.spinnerHavey;

public class PostsMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayoutManager mLayoutManager;
    private Button mLogout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLisener;
    private Toolbar toolbar;
    private DatabaseReference mDatabaseMainActivity;
    private RecyclerView mBlogList;
    private TextView txtViewName;
    private TextView txtView2Email;
    private Calendar c ;
    private DatabaseReference CommentmmDatabase;
    private DatabaseReference mDatabaseCurrentUser;
    private  String currentimeTime ;
    private Query mQueryCurrentUser;
    String StringToWhere;
    String StringWeight;
    String StringFromWhere;
    private Query mQuerywhere;
    View mViewweight;
    private FirebaseUser mCurrentuser;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private String Type;
    private DatabaseReference Blog_Posts_id;
    private String Posts_id1;
    private DatabaseReference Users;
    private Query query_Accept;
///////////////////////////////////////////////////////////**************************/////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        Firebase.setAndroidContext(this);

        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());
        final String currentimeTime=c.getTime().toString().trim();
        mAuth= FirebaseAuth.getInstance();
        //TextView txtViewName = (TextView) findViewById(R.id.usernametextviwe);
       // TextView txtView2Email = (TextView) findViewById(R.id.userEmailTextView);
        mDatabaseMainActivity = FirebaseDatabase.getInstance().getReference().child("Blog");
       String CurrentUserId=mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Blog");

        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();
        CommentmmDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").getRef().child("Comment").child(mCurrentuser.getUid());
    //    CommentmmDatabase = FirebaseDatabase.getInstance().getReference().child("Blog").child(mPos_tkey).child("Comment").child(mCurrentuser.getUid());

        mBlogList = (RecyclerView) findViewById(R.id.Blog_List);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        /////////////////////////////////////////////////////////////////////*******************************/////////////////
//        Blog_Posts_id=FirebaseDatabase.getInstance().getReference().child("Blog").child(Posts_id1);
////////////////////////////////////////////////////////////////////**********************/////////////////////////////////////////

        mAuth= FirebaseAuth.getInstance();
         mAuthLisener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent intent = new Intent(PostsMain.this, Blog.class);
                    startActivity(intent);
                }
            }
        };
        ////////////////////////////////////////////////////////////////////////////////**********************///////////////////////////////////////////////////////////////
     toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
     //   getSupportActionBar().setTitle(null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PostsMain.this);
                mBuilder.setTitle("استخدم احد الطرق التاليه فى البحث");
                mBuilder.setPositiveButton("الوزن", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogWeight();
                    onStart3();
                }
            });
                mBuilder.setNeutralButton("من محافظه", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FromWhere();
                }
            });

                mBuilder.setNegativeButton("الى محافظه ", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ToWhere();
                }
            });
            //mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
                dialog.show();
        }

    });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /////added by mariam

        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                // System.out.println("cccc"+dataSnapshot.getKey());
                // System.out.println("cccc"+dataSnapshot.child(user.getUid()).getValue());
                if (dataSnapshot.child(user.getUid()).getKey().equals(user.getUid())==true  & dataSnapshot.child
                        (user.getUid()).getValue() != null)

                {

                    Type=dataSnapshot.child(user.getUid()).child("Type").getValue().toString();
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
    ////////////////////////////////////////////////////////////////////////*********************//////////////////////////////////////////////
    protected void onStart() {

        super.onStart();

     //   Query query = mDatabaseMainActivity.orderByChild("PostTime").limitToLast(20);
               Query query = mDatabaseMainActivity.orderByKey();

        mLayoutManager = new LinearLayoutManager(this);
      //  mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true); // THIS ALSO SETS setStackFromBottom to true
        mLayoutManager.setStackFromEnd(true);

        mBlogList.setLayoutManager(mLayoutManager);
        FirebaseRecyclerAdapter<Blog, BlogViewLoader> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewLoader>(
                Blog.class,
                R.layout.blog_row,
                BlogViewLoader.class,
             //   mQueryCurrentUser
                 query
        ) {

////////////////////////////////////////////////////////////////////////*******************/////////////////////////////////////////////////////

            @Override
            protected void populateViewHolder(final BlogViewLoader viewHolder, final Blog model, int position) {

               final String postKey= getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFromWhere(model.getFromWhere());
                viewHolder.setMoneyOffere(model.getMoneyOffere());
                viewHolder.setToLocation(model.getToLocation());
                viewHolder.setDayToMove(model.getDayToMove());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setWeight(model.getWeight());
           //     viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUsename(model.getUsername());
                viewHolder.setUid(model.getUid());

                viewHolder.mTalabButton.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {

                        //Toast.makeText(PostsMain.this,postKey,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(PostsMain.this,SinglePost.class);
                        intent.putExtra("send_post","3");
                        intent.putExtra("blog_id",postKey);
                        startActivity(intent);
                    }
                });

         /*
                viewHolder.uidButtoninvisible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(PostsMain.this,Public_profile.class);
                        intent.putExtra("mPublic_Profile_tkey",model.getUid());
                        startActivity(intent);
                    }
                });
*/


            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }
  ////////////////////////////////////////////////////////////////***************************/////////////////////////////////////////////////////

    public static class BlogViewLoader extends RecyclerView.ViewHolder {
        Button mTalabButton;
        Intent intentSinglePost;
        TextView post_Name_View_All_offer;
        TextView uidButtoninvisible;
        View mView;
        ///////////////////////////////////////////////////////////////////***************************////////////////////////////////
        public BlogViewLoader(View itemView) {
            super(itemView);
            mView = itemView;
            post_Name_View_All_offer = (TextView) mView.findViewById(R.id.Post_UserName);
            mTalabButton = (Button) mView.findViewById(R.id.TalabVeiw);
           uidButtoninvisible = (TextView) mView.findViewById(R.id.uidButtoninvisiblexml);
        }

        //////////////////////////////////////////////////////////////////******* Title********///////////////////////////////////////////////////
        public void setTitle(String title) {

            TextView post_titlen_View_All_offer = (TextView) mView.findViewById(R.id.Post_title_BlogViwe);
            post_titlen_View_All_offer.setText(title);

        }

        /////////////////////////////////////////////////////////////////////////*************Description**********////////////////////////////////////////////////////////
        public void setDescription(String description) {
            TextView post_Description_View_All_offer = (TextView) mView.findViewById(R.id.Description_title_BlogViwe);

            post_Description_View_All_offer.setText(description);
        }

        ////////////////////////////////////////////////////////////////////////////////////************  Image *************/////////////////////////////////////////////////
     /*
       public void setImage(Context context, String Image) {
            ImageView post_image_View_All_offer = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(context).load(Image).into(post_image_View_All_offer);
        }
*/
        //////////////////////////////////////////////////////////////////****** From Where ******////////////////////////////////////////////////////////
        public void setFromWhere(String fromwhere) {
            TextView post_titlen_View_All_offer = (TextView) mView.findViewById(R.id.Fromwhere_title_BlogViwe);
            post_titlen_View_All_offer.setText(fromwhere);
        }

        //////////////////////////////////////////////////////////////////******To where******////////////////////////////////////////////////////////
        public void setToLocation(String totlocation) {

            TextView post_titlen_View_All_offer = (TextView) mView.findViewById(R.id.TopWhere_title_BlogViwe);
            post_titlen_View_All_offer.setText(totlocation);
        }

        /////////////////////////////////////////////////////////////////*********Money******///////////////////////////////////////////////////
        public void setMoneyOffere(String moneyoffere) {

            TextView post_titlen_View_All_offer = (TextView) mView.findViewById(R.id.MonyOffer_title_BlogViwe);
            post_titlen_View_All_offer.setText(moneyoffere);

        }

        //////////////////////////////////////////////////////////////////*******DaytoMove*****////////////////////////////////////////////////////////
        public void setDayToMove(String dayToMove) {

            TextView post_titlen_View_All_offer = (TextView) mView.findViewById(R.id.DaytoMove);
            post_titlen_View_All_offer.setText(dayToMove);

        }

        //////////////////////////////////////////////////////////////////*******Weight*****////////////////////////////////////////////////////////

        public void setWeight(String weight) {

            TextView post_weight_View = (TextView) mView.findViewById(R.id.Weight);
            post_weight_View.setText(weight);

        }
        //////////////////////////////////////////////////////////////////*******Post Time*****////////////////////////////////////////////////////////
        public void setPostTime(String postTime) {

            TextView post_titlen_View_All_offer = (TextView) mView.findViewById(R.id.PostTime);
            //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
            post_titlen_View_All_offer.setText(postTime);
        }

        public void setUsename(String username)
        {

            TextView post_Name_View_All_offer = (TextView) mView.findViewById(R.id.Post_UserName);
            //  DateUtils.getRelativeTimeSpanString(postTime,c.getTime() ,DateUtils.MINUTE_IN_MILLIS);
            post_Name_View_All_offer.setText(username);
        }

        public  void setUid(String uid){
            TextView uidButtoninvisible=(TextView) mView.findViewById(R.id.uidButtoninvisiblexml);
            uidButtoninvisible.setText(uid);
        }
    }
       //////////////////////////////*////////////////////////////////////////////********************///////////////////////////////////////////
        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/////////////////////////////////////////////////////////////////////////////***************************************///////////////////////

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (item.getItemId() == R.id.nav_camera) {


            startActivity(new Intent(PostsMain.this,Post_Activity.class));

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            onStart2();

        } else if (id == R.id.nav_slideshow) {
            onStart();
        }

        else if (id == R.id.nav_manage) {
            startActivity(new Intent(PostsMain.this,Accept_Request.class));
        }
        else if (id == R.id.nav_share) {

            startActivity(new Intent(PostsMain.this,Accept_Request_Taxi.class));
        }
        else if (id == R.id.nav_view) {

            startActivity(new Intent(PostsMain.this,About_Project.class));
        }
        /*  /*else if (id == R.id.nav_share) {
onStart_Comment();

        }
        */
        else if (id == R.id.nav_send) {
            startActivity(new Intent(PostsMain.this,Call_Us.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void ToWhere() {

        AlertDialog.Builder mmBuilderToWhere = new AlertDialog.Builder(PostsMain.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_towhere, null);
        mmBuilderToWhere.setTitle("التى تصل اليها الطلبيه");


        final Spinner mSPinnerToWhere = (Spinner) mView.findViewById(R.id.SearchSinnperTowhere);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostsMain.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.country_arrays));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSPinnerToWhere.setAdapter(adapter);

        mmBuilderToWhere.setPositiveButton("ابحث", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                StringToWhere = mSPinnerToWhere.getSelectedItem().toString().trim();
                onStart3();
            }
        });

        mmBuilderToWhere.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mmBuilderToWhere.setView(mView);
        AlertDialog dialog = mmBuilderToWhere.create();
        dialog.show();


    }

    public void dialogWeight() {

        AlertDialog.Builder mmBuilder = new AlertDialog.Builder(PostsMain.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_weight, null);
        mmBuilder.setTitle("وزن الطلبيه");

        final Spinner mSpinnerWieght = (Spinner) mView.findViewById(spinnerHavey);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostsMain.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.WeightHavey));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerWieght.setAdapter(adapter);


        mmBuilder.setPositiveButton("ابحث", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                StringWeight = mSpinnerWieght.getSelectedItem().toString().trim();
                onStart4();
            }

        });

        mmBuilder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mmBuilder.setView(mView);
        AlertDialog dialog = mmBuilder.create();
        dialog.show();

    }

    public void FromWhere() {

        AlertDialog.Builder mmBuilder = new AlertDialog.Builder(PostsMain.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_from_where, null);
        mmBuilder.setTitle("البحث عن طريق المحافظه المنطلق منها الطلبيه ");
        mmBuilder.setTitle("");

        final Spinner mSpinnerFromWhere = (Spinner) mView.findViewById(R.id.Search_From_Wherre);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostsMain.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.country_arrays));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerFromWhere.setAdapter(adapter);
        mmBuilder.setPositiveButton("ابحث", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringFromWhere = mSpinnerFromWhere.getSelectedItem().toString().trim();
                onStart5();
            }

        });

        mmBuilder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mmBuilder.setView(mView);
        AlertDialog dialog = mmBuilder.create();
        dialog.show();

    }
    protected void onStart2() {
        super.onStart();
        //   Query query = mDatabaseMainActivity.orderByChild("PostTime").limitToLast(20);
        Query query = mDatabaseMainActivity.orderByKey();
        String CurrentUserId=mAuth.getCurrentUser().getUid();
        mQueryCurrentUser=mDatabaseCurrentUser.orderByChild("uid").equalTo(CurrentUserId);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true); // THIS ALSO SETS setStackFromBottom to true
        mLayoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(mLayoutManager);
        FirebaseRecyclerAdapter<Blog, BlogViewLoader> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewLoader>(
                Blog.class,
                R.layout.blog_row,
                BlogViewLoader.class,
                //mQuerywhere
                mQueryCurrentUser
        ) {

////////////////////////////////////////////////////////////////////////*******************/////////////////////////////////////////////////////

            @Override
            protected void populateViewHolder(final BlogViewLoader viewHolder, final Blog model, int position) {

                final String postKey= getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFromWhere(model.getFromWhere());
                viewHolder.setMoneyOffere(model.getMoneyOffere());
                viewHolder.setToLocation(model.getToLocation());
                viewHolder.setDayToMove(model.getDayToMove());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setWeight(model.getWeight());
                //     viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUsename(model.getUsername());
                viewHolder.setUid(model.getUid());
                viewHolder.mTalabButton.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {
                       // Toast.makeText(PostsMain.this,postKey,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(PostsMain.this,SinglePost.class);
                        intent.putExtra("blog_id",postKey);
                        startActivity(intent);
                    }
                });
/*
                viewHolder.uidButtoninvisible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(PostsMain.this,Public_profile.class);
                        intent.putExtra("mPublic_Profile_tkey",model.getUid());
                        startActivity(intent);
                    }
                });

*/

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }
    ////////////////////////////////////////////////////////////////*************Onstart 3 ToLocation**************/////////////////////////////////////////////////////

    protected void onStart3() {

        super.onStart();



        mQuerywhere = mDatabaseCurrentUser.orderByChild("ToLocation").equalTo(StringToWhere);


        //   Query query = mDatabaseMainActivity.orderByChild("PostTime").limitToLast(20);
        Query query = mDatabaseMainActivity.orderByKey();

        mLayoutManager = new LinearLayoutManager(this);

        mLayoutManager.setReverseLayout(true); // THIS ALSO SETS setStackFromBottom to true
        mLayoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(mLayoutManager);

        FirebaseRecyclerAdapter<Blog, BlogViewLoader> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewLoader>(
                Blog.class,
                R.layout.blog_row,
                BlogViewLoader.class,
                mQuerywhere
                // /  mDatabaseMainActivity

                //mSearchall
                //mQueryCurrentUser
                // query
        ) {

////////////////////////////////////////////////////////////////////////*******************/////////////////////////////////////////////////////

            @Override
            protected void populateViewHolder(final BlogViewLoader viewHolder, final Blog model, int position) {

                final String postKey = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFromWhere(model.getFromWhere());
                viewHolder.setMoneyOffere(model.getMoneyOffere());
                viewHolder.setToLocation(model.getToLocation());
                viewHolder.setDayToMove(model.getDayToMove());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setWeight(model.getWeight());
                viewHolder.setUsename(model.getUsername());

                viewHolder.setUid(model.getUid());

                viewHolder.mTalabButton.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {
                        //Toast.makeText(PostsMain.this,postKey,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(PostsMain.this,SinglePost.class);
                        intent.putExtra("blog_id",postKey);
                        startActivity(intent);
                    }
                });
/*
                viewHolder.uidButtoninvisible.setOnClickListener(new Vie979w.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(PostsMain.this,Public_profile.class);
                        intent.putExtra("mPublic_Profile_tkey",model.getUid());
                        startActivity(intent);
                    }
                });
*/


            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }
    /////////////////////////////////////////////////////////////////////////******On start 4 Weight*******/////////////////////////////////////////////////
    protected void onStart4() {

        super.onStart();
        mQuerywhere = mDatabaseCurrentUser.orderByChild("Weight").equalTo(StringWeight);
        //   Query query = mDatabaseMainActivity.orderByChild("PostTime").limitToLast(20);
        Query query = mDatabaseMainActivity.orderByKey();

        mLayoutManager = new LinearLayoutManager(this);

        mLayoutManager.setReverseLayout(true); // THIS ALSO SETS setStackFromBottom to true
        mLayoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(mLayoutManager);

        FirebaseRecyclerAdapter<Blog, BlogViewLoader> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewLoader>(
                Blog.class,
                R.layout.blog_row,
                BlogViewLoader.class,
                mQuerywhere

                        ) {

////////////////////////////////////////////////////////////////////////*******************/////////////////////////////////////////////////////

            @Override
            protected void populateViewHolder(final BlogViewLoader viewHolder, final Blog model, int position) {

                final String postKey = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFromWhere(model.getFromWhere());
                viewHolder.setMoneyOffere(model.getMoneyOffere());
                viewHolder.setToLocation(model.getToLocation());
                viewHolder.setDayToMove(model.getDayToMove());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setWeight(model.getWeight());
                viewHolder.setUsename(model.getUsername());

                viewHolder.setUid(model.getUid());

                viewHolder.mTalabButton.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {
                      //  Toast.makeText(PostsMain.this,postKey,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(PostsMain.this,SinglePost.class);
                        intent.putExtra("blog_id",postKey);
                        startActivity(intent);
                    }
                });
/*
                viewHolder.uidButtoninvisible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(PostsMain.this,Public_profile.class);
                        intent.putExtra("mPublic_Profile_tkey",model.getUid());
                        startActivity(intent);
                    }
                });
  */          }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);


    }
    ////////////////////////////////////////////////////////////////////////********onstart 5 From where ********///////////////////
    protected void onStart5() {

        super.onStart();



        mQuerywhere = mDatabaseCurrentUser.orderByChild("FromWhere").equalTo(StringFromWhere);


        //   Query query = mDatabaseMainActivity.orderByChild("PostTime").limitToLast(20);
        Query query = mDatabaseMainActivity.orderByKey();

        mLayoutManager = new LinearLayoutManager(this);

        mLayoutManager.setReverseLayout(true); // THIS ALSO SETS setStackFromBottom to true
        mLayoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(mLayoutManager);

        FirebaseRecyclerAdapter<Blog, BlogViewLoader> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewLoader>(
                Blog.class,
                R.layout.blog_row,
                BlogViewLoader.class,
                mQuerywhere

        ) {

////////////////////////////////////////////////////////////////////////********Viwe Detalis of Post ***********/////////////////////////////////////////////////////

            @Override
            protected void populateViewHolder(final BlogViewLoader viewHolder, final Blog model, int position) {

                final String postKey = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFromWhere(model.getFromWhere());
                viewHolder.setMoneyOffere(model.getMoneyOffere());
                viewHolder.setToLocation(model.getToLocation());
                viewHolder.setDayToMove(model.getDayToMove());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setWeight(model.getWeight());
                viewHolder.setUsename(model.getUsername());
                viewHolder.setUid(model.getUid());
                viewHolder.mTalabButton.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {
                    //    Toast.makeText(PostsMain.this,postKey,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(PostsMain.this,SinglePost.class);
                        intent.putExtra("blog_id",postKey);
                        startActivity(intent);
                    }
                });
    /*            viewHolder.uidButtoninvisible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(PostsMain.this,Public_profile.class);
                        intent.putExtra("mPublic_Profile_tkey",model.getUid());
                        startActivity(intent);
                    }
                });
*/


            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    protected void onStart_Comment() {

        super.onStart();



        Users=FirebaseDatabase.getInstance().getReference().child("Users");

        Users.child(mCurrentuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Price")){

                    Posts_id1=(String)dataSnapshot.child("Post_id").getValue();
//                    Blog_Posts_id=FirebaseDatabase.getInstance().getReference().child("Blog").child(Posts_id1);

                }
                else
                {

                    Toast.makeText(PostsMain.this,"now match",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference mm =FirebaseDatabase.getInstance().getReference().child("Blog").child("Comment");
        String ff= mm.toString();
        Query kk =mm;

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true); // THIS ALSO SETS setStackFromBottom to true
        mLayoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(mLayoutManager);
        FirebaseRecyclerAdapter<Blog, BlogViewLoader> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewLoader>(
                Blog.class,
                R.layout.blog_row,
                BlogViewLoader.class,
               query_Accept
        ) {

////////////////////////////////////////////////////////////////////////*******************/////////////////////////////////////////////////////

            @Override
            protected void populateViewHolder(final BlogViewLoader viewHolder, final Blog model, int position) {

                final String postKey= getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFromWhere(model.getFromWhere());
                viewHolder.setMoneyOffere(model.getMoneyOffere());
                viewHolder.setToLocation(model.getToLocation());
                viewHolder.setDayToMove(model.getDayToMove());
                viewHolder.setPostTime(model.getPostTime());
                viewHolder.setWeight(model.getWeight());
                //     viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setUsename(model.getUsername());
                viewHolder.setUid(model.getUid());

                viewHolder.mTalabButton.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {
                  //      Toast.makeText(PostsMain.this,postKey,Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(PostsMain.this,SinglePost.class);

                        intent.putExtra("blog_id",postKey);

                        startActivity(intent);
                    }
                });

  /*              viewHolder.uidButtoninvisible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(PostsMain.this,Public_profile.class);
                        intent.putExtra("mPublic_Profile_tkey",model.getUid());
                        startActivity(intent);
                    }
                });

*/

            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        //   finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        String x="1";
        String y="2";

        switch (item.getItemId()) {
            case R.id.Edit_Profile:
                System.out.println("ggg"+Type);
                // Single menu item is selected do something
                // Ex: launching new activity/screen or show alert message
                if (Type.equals(x)) {
                    System.out.println("ggg11111");
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
                }
                else if (Type.equals(y)) {
                    System.out.println("ggg22222");
                    startActivity(new Intent(getApplicationContext(), ProfileClientActivity.class));
                    return true;
                }
                else
                    System.out.println("ggg323333");
                return  false;


            case R.id.View_Profile:

                if (Type.equals(x)) {
                    System.out.println("ggg11111");
                    startActivity(new Intent(getApplicationContext(), ProfileView.class));
                    return true;
                }
                else if (Type.equals(y)) {
                    System.out.println("ggg22222");
                    startActivity(new Intent(getApplicationContext(), Profile_View_Client.class));
                    return true;
                }
                else
                    System.out.println("ggg323333");
                return  false;


/*
            case R.id.Posts:
                startActivity(new Intent(getApplicationContext(), PostsMain.class));
                return true;
*/
            case R.id.Logout:
                mAuth.signOut();
                finish();
                //starting login activity
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            case R.id.action_add:

                Intent intents = new Intent(PostsMain.this, Post_Activity.class);
                startActivity(intents);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

}