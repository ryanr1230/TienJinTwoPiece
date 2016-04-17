package io.tienjintwopiece;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Post extends AsyncTask<File, Void, IdentifyResults> {
    public AsyncResponseForCamera delegate = null;

    public Post(AsyncResponseForCamera delegate) {
        this.delegate = delegate;
    }

    @Override

    protected IdentifyResults doInBackground(File... files) {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        HttpPost httppost = new HttpPost("https://api.clarifai.com/v1/tag/");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("encoded_data", files[0], ContentType.APPLICATION_OCTET_STREAM, "testingoutputpic.jpg");
        HttpEntity mpEntity = builder.build();
//        ContentBody cbFile = new FileBody(file, "image/jpeg");
//        mpEntity.addPart("encoded_data", cbFile);
        httppost.setHeader("Authorization", "Bearer fzPMvRgNJJrN4paU0hlHMd6StgH6h8");
        List<NameValuePair> params = new ArrayList<>();

        BasicNameValuePair bEntity = new BasicNameValuePair("url", "http://www-tc.pbs.org/food/files/2012/09/Sushi-5-1.jpg");
        params.add(bEntity);
//        httppost.setEntity(new UrlEncodedFormEntity(params));
        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
//        System.out.println(httppost.getParams().setParameter("asdf", ));
        Map<String, Object> r = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            System.out.println(response.getStatusLine());
            String responseText = "";
            if (resEntity != null) {
                //System.out.println(EntityUtils.toString(resEntity));
                responseText = EntityUtils.toString(resEntity);
            }
            if (resEntity != null) {
                resEntity.consumeContent();
            }

            httpclient.getConnectionManager().shutdown();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Log.e("asdf", responseText);
            r = objectMapper.readValue(responseText, Map.class);
        }catch(Exception e){}
        ArrayList results = (ArrayList) r.get("results");
        Map result = (Map) ((Map) results.get(0)).get("result");
        Map tag = (Map) result.get("tag");
        return new IdentifyResults((ArrayList)tag.get("classes"), (ArrayList)tag.get("probabilities"));
    }

    protected void onPostExecute(IdentifyResults results) {
        try {
            delegate.processFinish(results);
        }catch(Exception e){}
    }
}
