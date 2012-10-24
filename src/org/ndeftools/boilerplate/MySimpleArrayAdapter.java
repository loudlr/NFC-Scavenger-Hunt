package org.ndeftools.boilerplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageView thumbView = (ImageView) rowView.findViewById(R.id.thumbnail);
        
        // Change the icon for Windows and iPhone
        String s = values[position];
        String[] t = s.split(";");
        Log.d("NFCsh", "s=" + s);
        
        //Log.d("NFCsh", t[2] + " -- " + NfcReaderActivity.KEY_DOLL);
        Log.d("NFCsh", t[0] + " -- " + "array adapter");
        if (t[2].contentEquals(NfcReaderActivity.KEY_DOLL)) {            
            thumbView.setImageResource(R.drawable.criterlogo1);
            Log.d("NFCsh", "doll found");
        } else if (t[2].contentEquals(NfcReaderActivity.KEY_CHOCOLATE)) {
            thumbView.setImageResource(R.drawable.chocolate);
            Log.d("NFCsh", "chocolate found");
        } else if (t[2].contentEquals(NfcReaderActivity.KEY_LOTION)) {
            thumbView.setImageResource(R.drawable.bottle);
            Log.d("NFCsh", "lotion found");
        }
        
        if (t[1].contentEquals("1")) {            
            imageView.setImageResource(R.drawable.check3);
        }
        textView.setText(t[0]); // Text to be displayed

        return rowView;
    }
}