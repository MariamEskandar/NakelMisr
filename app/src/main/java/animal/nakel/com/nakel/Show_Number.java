package animal.nakel.com.nakel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class Show_Number extends AppCompatActivity {
    private  String get_Number = null;
    private  String get_Price = null;
    private TextView Number;
    private TextView Price;
    private  String mPublic_Profile_Post_id = null;

    private String PostId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__number);


        get_Number=getIntent().getExtras().getString("Number");
        get_Price=getIntent().getExtras().getString("Price");

        mPublic_Profile_Post_id=getIntent().getExtras().getString("mpost_key_id");
        PostId=mPublic_Profile_Post_id.toString().trim();
        Number=(TextView)findViewById(R.id.Number_text);
        Number.setText(get_Number);
        Price=(TextView)findViewById(R.id.Price_text);
        Price.setText(get_Price);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Show_Number.this, PostsMain.class);
          //  intent.putExtra("mpost_key",PostId);
          //  Toast.makeText(Show_Number.this,PostId,Toast.LENGTH_SHORT).show();
            startActivity(intent);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

