package io.tienjintwopiece;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.math.BigInteger;

import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by BenGirardeau on 4/16/16.
 */
public class APICalls extends AsyncTask<String,Void, String>{
    public AsyncResponse delegate = null;
    private static SecureRandom random  = new SecureRandom();

    public APICalls(AsyncResponse delegate){
        this.delegate = delegate;
    }
    protected String doInBackground(String ... searchterms)
    {
//        API Keys
        String consumer_key = "OHMwCYmjO4uQhTMbXE9VyQ";
        String consumer_secret = "GBIdavGbYOTpgv0LT-lkvWVhr5E";
        String token = "MfdBcBZTCqJl6k8ScHwTkOyjOqF3NpFs";
        String token_secret = "qkQyQ_4nl9WPe3BzhNZXVbN7iUM";
//        API Authorization
        OAuthService service = new ServiceBuilder()
                .provider(YelpV2API.class)
                .apiKey(consumer_key)
                .apiSecret(consumer_secret)
                .build();
//        Access token
        Token accessToken = new Token(token, token_secret);
//        Formulate request
        OAuthRequest request = new OAuthRequest(Verb.GET,
                "http://api.yelp.com/v2/search");
        int limit = 4;
//        Add parameters
        request.addQuerystringParameter("category_filter", "restaurants");
        request.addQuerystringParameter("location",searchterms[1]);
        request.addQuerystringParameter("term", searchterms[0]);
        request.addQuerystringParameter("limit", Integer.toString(limit));
        service.signRequest(accessToken, request);
//        Get response
        Response response = request.send();
//        Return response as JSON string
        return response.getBody();
    }

    protected void onPostExecute(String result){
        String results = result;
        try {
            delegate.processFinish(results);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
