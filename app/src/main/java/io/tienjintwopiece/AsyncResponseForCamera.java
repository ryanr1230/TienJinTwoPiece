package io.tienjintwopiece;

import org.json.JSONException;

import java.io.IOException;


/**
 * Created by BenGirardeau on 5/9/15.
 */
public interface AsyncResponseForCamera {
    void processFinish(IdentifyResults results) throws IOException, JSONException;
}

