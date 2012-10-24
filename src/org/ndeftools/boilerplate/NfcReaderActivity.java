/***************************************************************************
 * 
 * This file is part of the 'NDEF Tools for Android' project at
 * http://code.google.com/p/ndef-tools-for-android/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ****************************************************************************/

package org.ndeftools.boilerplate;

import org.ndeftools.Message;
import org.ndeftools.MimeRecord;
import org.ndeftools.Record;
import org.ndeftools.externaltype.ExternalTypeRecord;
import org.ndeftools.util.NdefReader;
import org.ndeftools.util.NdefReader.NdefReaderListener;
import org.ndeftools.wellknown.TextRecord;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sprint.pinsightmediaplus.PsmAdView;

/**
 * 
 * Activity for reading NFC messages - both via a tag and via Beam
 * 
 * @author Thomas Rorvik Skjolberg
 * 
 */

public class NfcReaderActivity extends NfcDetectorActivity implements
        NdefReaderListener, OnClickListener {

    private static final String TAG = NfcReaderActivity.class.getSimpleName();
    public static final String KEY_DOLL = "doll";
    public static final String KEY_CHOCOLATE = "chocolate";
    public static final String KEY_LOTION = "lotion";

    PsmAdView _adTop;
    
    String[] m_items = new String[] {
            "Cittercism Doll", "Godiva Chocolate",
            "Bath & Body Works Moonlight Path Body Lotion",
    };
    private String[] m_found = {
            "0", "0", "0"
    };
    private ListView m_listView;
    private Button m_congratsButton;

    protected NdefReader reader;

    protected Message message;

    protected final int layout; // for subclassing

    public NfcReaderActivity() {
        this(R.layout.reader);
    }

    public NfcReaderActivity(int layout) {
        this.layout = layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout);
        
        _adTop = (PsmAdView) findViewById(R.id.adTop);
        
        m_listView = (ListView) findViewById(R.id.recordListView);
        m_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
              int position, long arg3) {
                Log.d("NFCsh", position + " " + m_items[position]);
                showCoupon(position);
            }
//             String[] lister = null;
//             switch (positon) {
//             case 0:
//              lister = list21;
//              break;
//             case 1:
//              lister = list22;
//              break;
//             case 2:
//              lister = list23;
//              break;
//             case 3:
//              lister = list24;
//              break;
//             default:
//              lister = list21;
//             }
//             lister2.setAdapter(new ArrayAdapter(
//               ListViewExample.this,
//               android.R.layout.simple_list_item_1, lister));
//            }
           });
        
        m_congratsButton = (Button) findViewById(R.id.btnCongrats);
//        congratsButton.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              Log.d("NFCsh", "button clicked");
//            showCoupon(3);
//          }
//        });
        
//        congratsButton.setOnClickListener(new OnClickListener(){
//            public void onClick(View view) {
//                Log.d("NFCsh", "button clicked");
//            }
//
//        });
        m_congratsButton.setOnClickListener(this);
        
//        showList2();
        initList();
    }

    @Override
    protected void onNfcFeatureFound(boolean enabled) {
        reader = new NdefReader();
        reader.setListener(this);

        if (enabled) {
            // toast(getString(R.string.nfcAvailableEnabled));
        } else {
            // toast(getString(R.string.nfcAvailableDisabled));
        }
    }

    @Override
    protected void onNfcFeatureNotFound() {
        // toast(getString(R.string.noNfcMessage));
    }

    @Override
    public void nfcIntentDetected(Intent intent, String action) {
        Log.d(TAG, "nfcIntentDetected: " + action);

        if (reader.read(intent)) {
            // do something

            // show in log
            if (message != null) {
                // iterate through all records in message
                Log.d(TAG, "Found " + message.size() + " NDEF records");

                for (int k = 0; k < message.size(); k++) {
                    Record record = message.get(k);

                    Log.d(TAG, "Record " + k + " type "
                            + record.getClass().getSimpleName());

                    // your own code here, for example:
                    if (record instanceof MimeRecord) {
                        // ..
                    } else if (record instanceof ExternalTypeRecord) {
                        // ..
                    } else if (record instanceof TextRecord) {
                        // ..
                    } else { // more else
                        // ..
                    }
                }
            }

            // show in gui
            showList2();
        } else {
            // do nothing(?)

            clearList();
        }
    }

    @Override
    public void readUnparsableNdefMessage(NdefMessage[] rawMessages, Exception e) {
        // toast(getString(R.string.readUnparsableNDEFMessage) + ": " +
        // e.toString());

        message = null;

        // Analyze raw message contents? In that case, parse record for record
    }

    @Override
    public void readNdefMessage(Message message) {
        if (message.size() > 1) {
            // toast(getString(R.string.readMultipleRecordNDEFMessage));
        } else {
            // toast(getString(R.string.readSingleRecordNDEFMessage));
        }

        this.message = message;
    }

    @Override
    public void readEmptyNdefMessage() {
        // toast(getString(R.string.readEmptyMessage));
    }

    @Override
    public void readNonNdefMessage() {
        // toast(getString(R.string.readNonNDEFMessage));
    }

    public void toast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.show();
    }

    private void showList() {
        if (message != null && !message.isEmpty()) {

            // display the message
            // show in gui
            Record record = message.get(0);
            TextRecord textRecord = (TextRecord) record;

            // Toast.makeText(this.getApplicationContext(),
            // textRecord.getText(), Toast.LENGTH_LONG);
            Log.d("NDEF", textRecord.getText()); // doll

            ArrayAdapter<? extends Object> adapter = new NdefRecordAdapter(
                    this, message);
            m_listView.setAdapter(adapter);
            
        } else {
            clearList();
        }
    }

    private void clearList() {
//        m_listView = (ListView) findViewById(R.id.recordListView);
        m_listView.setAdapter(null);
    }

    private void showList2() {
          String sItem = "";
          int iFound = 0;
          String[] values = {"", "", ""}; 

          if (message != null && !message.isEmpty()) {
          
	      Record record = message.get(0);
	      TextRecord textRecord = (TextRecord) record;
	      sItem = textRecord.getText();
	      Log.d("NDEF", sItem);  // doll
	      
	      
	      
	      if (sItem.contentEquals(KEY_DOLL)) { // Doll found
	          m_found[0] = "1";	          
	          iFound = 0;
	      }
	      values[0] = m_items[0] + ";" + m_found[0] + ";" + KEY_DOLL;
	      
          if (sItem.contentEquals(KEY_CHOCOLATE)) { // Chocolate found
              m_found[1] = "1";   
              iFound = 1;
          }
          values[1] = m_items[1] + ";" + m_found[1] + ";" + KEY_CHOCOLATE;

          if (sItem.contentEquals(KEY_LOTION)) { // Lotion found
              m_found[2] = "1";
              iFound = 2;
          }
          values[2] = m_items[2] + ";" + m_found[2] + ";" + KEY_LOTION;

//          if (allFound()) {
//              values[3] = "Congratulations!;1;congratulations";                     
//          } else {
//              values[3] = " ;0; ";
//          }
          }
          
          if (allFound()) { 
//              View footer = getLayoutInflater().inflate(R.layout.footer, null);
//              m_listView.addFooterView(footer);
              m_congratsButton.setVisibility(View.VISIBLE);
          } else {
              m_congratsButton.setVisibility(View.GONE);
          }
          
	      MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
//	      setListAdapter(adapter);
//	      ListView listView = (ListView) findViewById(R.id.recordListView);
          m_listView.setAdapter(adapter);
          
          showCoupon(iFound);
	}
    
    public void showCoupon(int position) {
        

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setClassName(this, CouponActivity.class.getName());
//        startActivity(intent);
        
        // Show coupon only if the item has been found
        if (((position < 3) && m_found[position].contentEquals("1")) ||
                position == 3) {
            Intent intent = new Intent();
            Bundle bun = new Bundle();
    
            bun.putInt("position", position);
    
            intent.setClass(this, CouponActivity.class);
            intent.putExtras(bun);
            startActivity(intent);
        }
    }
    
    private boolean allFound() {
        if (m_found[0].contentEquals("1") && m_found[1].contentEquals("1") &&
                m_found[2].contentEquals("1")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
        Log.d("NFCsh", "button clicked");
        showCoupon(3);
    }
    
    private void initList() {
        String[] values = {"", "", ""};
        
//        values[0] = m_items[0] + ";" + m_found[0] + ";" + KEY_DOLL;      
//        values[1] = m_items[1] + ";" + m_found[1] + ";" + KEY_CHOCOLATE;
//        values[2] = m_items[2] + ";" + m_found[2] + ";" + KEY_LOTION;
        values[0] = m_items[0] + ";0;" + KEY_DOLL;      
        values[1] = m_items[1] + ";0;" + KEY_CHOCOLATE;
        values[2] = m_items[2] + ";0;" + KEY_LOTION;
        
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
        m_listView.setAdapter(adapter);
    }
}
