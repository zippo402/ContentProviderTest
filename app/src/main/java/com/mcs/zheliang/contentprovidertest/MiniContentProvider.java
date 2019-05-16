package com.mcs.zheliang.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static java.lang.Integer.parseInt;

public class MiniContentProvider extends ContentProvider {

    private final String TAG = "MiniContentProvider";
    public String[] mData;
    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mData = context.getResources().getStringArray(R.array.words);
        Log.d(TAG, "onCreate "+ mData.length);
        initializeUriMatching();
        return true;
    }


    /**
     *
     * @param uri           The complete URI. This cannot be null.
     * @param projection       Indicates which columns/attributes to access.
     * @param selection         Indicates which rows/records of the objects to access.
     * @param selectionArgs     The binding parameters to the previous selection argument. For security reasons, the arguments are processed separately.
     * @param sortOrder         Whether to sort, and if so, whether ascending, descending or by . If this is null, the default sort or no sort is applied.
     * @return
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: ");
        int id = -1;
        switch (sUriMatcher.match(uri)) {
            case 0:
                // Matches URI to get all of the entries.
                id = Contract.ALL_ITEMS;
                // Look at the remaining arguments
                // to see whether there are constraints.
                // In this example, we only support getting
                //a specific entry by id. Not full search.
                // For a real-life app, you need error-catching code;
                // here we assume that the
                // value we need is actually in selectionArgs and valid.
                if (selection != null){
                    id = parseInt(selectionArgs[0]);
                }
                break;

            case 1:
                // The URI ends in a numeric value, which represents an id.
                // Parse the URI to extract the value of the last,
                // numeric part of the path,
                // and set the id to that value.
                id = parseInt(uri.getLastPathSegment());
                // With a database, you would then use this value and
                // the path to build a query.
                break;

            case UriMatcher.NO_MATCH:
                // You should do some error handling here.
                Log.d(TAG, "NO MATCH FOR THIS URI IN SCHEME.");
                id = -1;
                break;
            default:
                // You should do some error handling here.
                Log.d(TAG, "INVALID URI - URI NOT RECOGNIZED.");
                id = -1;
        }
        Log.d(TAG, "query: " + id);
        return populateCursor(id);
    }

    private Cursor populateCursor(int id) {
        MatrixCursor cursor = new MatrixCursor(new String[] { Contract.CONTENT_PATH });
        // If there is a valid query, execute it and add the result to the cursor.
        Log.d(TAG, "length = "+ mData.length);
        if (id == Contract.ALL_ITEMS) {
            for (int i = 0; i < mData.length; i++) {
                String word = mData[i];
                cursor.addRow(new Object[]{word});
            }
        } else if (id >= 0) {
            // Execute the query to get the requested word.
            String word = mData[id];
            // Add the result to the cursor.
            cursor.addRow(new Object[]{word});
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        switch (sUriMatcher.match(uri)) {
            case 0:
                return Contract.MULTIPLE_RECORD_MIME_TYPE;
            case 1:
                return Contract.SINGLE_RECORD_MIME_TYPE;
            default:
                // Alternatively, throw an exception.
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        return 0;
    }

    private void initializeUriMatching(){
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH + "/#", 1);
        sUriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH, 0);
    }
}
