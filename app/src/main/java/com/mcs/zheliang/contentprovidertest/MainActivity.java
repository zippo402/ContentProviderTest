package com.mcs.zheliang.contentprovidertest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView textView;
    private Button btn_all, btn_first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        btn_all = findViewById(R.id.button_display_all);
        btn_first = findViewById(R.id.button_display_first);

        btn_first.setOnClickListener(this);
        btn_all.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onClickDisplayEntries(v);
    }

    private void onClickDisplayEntries(View v) {
        String selectionClause;
        String selectionArgs[];
        String sortOrder = null;
        String queryUri = Contract.CONTENT_URI.toString();
        String[] projection = new String[] {Contract.CONTENT_PATH};
        switch (v.getId()) {
            case R.id.button_display_all:
                selectionClause = null;
                selectionArgs = null;
                break;
            case R.id.button_display_first:
                selectionClause = Contract.WORD_ID + " = ?";
                selectionArgs = new String[] {"0"};
                break;
            default:
                selectionClause = null;
                selectionArgs = null;
        }

        Cursor cursor = getContentResolver().query(Uri.parse(queryUri), projection, selectionClause, selectionArgs, sortOrder);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                do {
                    String word = cursor.getString(columnIndex);
                    textView.append(word + "\n");
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "onClickDisplayEntries " + "No data returned.");
                textView.append("No data returned." + "\n");
            }
            cursor.close();
        } else {
            Log.d(TAG, "onClickDisplayEntries " + "Cursor is null.");
            textView.append("Cursor is null." + "\n");
        }
    }
}
