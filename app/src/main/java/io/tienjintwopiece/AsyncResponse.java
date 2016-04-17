package io.tienjintwopiece;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by BenGirardeau on 5/9/15.
 */
public interface AsyncResponse {
    void processFinish(String output) throws IOException, JSONException;
}
