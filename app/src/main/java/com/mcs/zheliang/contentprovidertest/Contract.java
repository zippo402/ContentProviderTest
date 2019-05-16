package com.mcs.zheliang.contentprovidertest;

import android.net.Uri;

public final class Contract {
    public static final String AUTHORITY = "com.mcs.zheliang.contentprovidertest.provider";
    public static final String CONTENT_PATH = "words";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    static final int ALL_ITEMS = -2;  //this is the dataset name you will use when retrieving all the words. The value is set to -2 because this is the first lowest value that will not be returned by method call

    static final String WORD_ID = "id"; //This is the id you will use when retrieving a single word.

    /**
     *Content providers provide content, and you need to specify what type of content they provide. Apps need to know the structure and format of the returned data, so that they can properly handle it.
     *
     * MIME types are of the form type/subtype, such as text/html for HTML pages. For your content provider, you need to define a vendor-specific MIME type for the kind of data your content provider returns. The type of vendor-specific Android MIME types is always:
     *
     * vnd.android.cursor.item for one data item (a record)
     * vnd.android.cursor.dir for s set of data (multiple records).
     * The subtype can be anything, but it is a good practice to make it informative. For example:
     *
     * vnd—a vendor MIME type
     * com.example—the domain
     * provider—it's for a content provider
     * words—the name of the table
     */
    static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/vnd.com.example.provider.words"; //Declare the MIME time for one data item.
    static final String MULTIPLE_RECORD_MIME_TYPE = "vnd.android.cursor.dir/vnd.com.example.provider.words"; //Declare the MIME type for multiple records.





    private Contract(){};
}
