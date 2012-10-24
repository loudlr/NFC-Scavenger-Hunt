package org.ndeftools.boilerplate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

public class CouponActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon);
        
        Bundle bun = getIntent().getExtras();
        int position = bun.getInt("position", 0);
        
        ImageView img = (ImageView) findViewById(R.id.couponimg);
        
        switch (position) {
        case 0: // doll
            img.setImageResource(R.drawable.critter2);
            break;
        case 1:  // chocolate
            img.setImageResource(R.drawable.godiva3);
            break;
        case 2:  // lotion
            img.setImageResource(R.drawable.bath5);
            break;
        case 3: // congrats
            img.setImageResource(R.drawable.congrats);
            break;
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_coupon, menu);
//        return true;
//    }
}
