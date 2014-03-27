package com.ricardo.updateimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity.java";
    private static final String SERVER = "http://evening-meadow-5727.herokuapp.com/productos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // we are going to use asynctask to prevent network on main thread exception
        new PostDataAsyncTask().execute();
        
    }
    
    public class PostDataAsyncTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                // 1 = post text data, 2 = post file
                int actionChoice = 1;
                
                // post a text data
                if(actionChoice==1){
                    postText();
                }
                
                // post a file
                else{
                    postFile();
                }
                
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String lenghtOfFile) {
            // do stuff after posting data
        }
    }
    
    // this will post our text data
    private void postText(){
        try{
            // url where the data will be posted
            String postReceiverUrl = "http://evening-meadow-5727.herokuapp.com/productos";
            Log.v(TAG, "postURL: " + postReceiverUrl);
            
            // HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            
            // post header
            HttpPost httpPost = new HttpPost(postReceiverUrl);
    
            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("producto[nombre]", "ElRichard"));
            nameValuePairs.add(new BasicNameValuePair("producto[precio]", "12"));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    
            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            
            if (resEntity != null) {
                
                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v(TAG, "Response: " +  responseStr);
                
                // you can add an if statement here and do other actions based on the response
            }
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // will post our text file
	private void postFile() throws ClientProtocolException, IOException{
        try{
            
            // the file to be posted
            String textFile = Environment.getExternalStorageDirectory() + "/my-photo.jpg";
            
            Log.v(TAG, "textFile: " + textFile);
            
            // the URL where the file will be posted
            String postReceiverUrl = "http://evening-meadow-5727.herokuapp.com/productos";
            Log.v(TAG, "postURL: " + postReceiverUrl);
            
            // new HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            
            // post header
            HttpPost httpPost = new HttpPost(postReceiverUrl);
            
            File file = new File(textFile);
//            Toast.makeText(this, ""+file.exists(), Toast.LENGTH_LONG).show();
            Log.v(TAG, "Existe archivo: " + file.exists());
            FileBody fileBody = new FileBody(file);
    
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//            MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
//            reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            
            reqEntity.addPart("file", fileBody);
            httpPost.setEntity(reqEntity);
            
            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
    
            if (resEntity != null) {
                
                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v(TAG, "Response: " +  responseStr);
                
                // you can add an if statement here and do other actions based on the response
            }
            
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
//    	
//    	String fileName = Environment.getExternalStorageDirectory() + "/my-photo.jpg";
//    	
//    	HttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost(SERVER);
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
//        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//        final File file = new File(fileName);
//        FileBody fb = new FileBody(file);
//        
//        Toast.makeText(this, ""+file.exists(), Toast.LENGTH_LONG).show();
//
//        builder.addPart("producto[imagen]", fb);  
////        builder.addTextBody("userName", userName);
////        builder.addTextBody("password", password);
////        builder.addTextBody("macAddress",  macAddress);
//        final HttpEntity yourEntity = builder.build();
//
//        class ProgressiveEntity implements HttpEntity {
//            @Override
//            public void consumeContent() throws IOException {
//                yourEntity.consumeContent();                
//            }
//            @Override
//            public InputStream getContent() throws IOException,
//                    IllegalStateException {
//                return yourEntity.getContent();
//            }
//            @Override
//            public Header getContentEncoding() {             
//                return yourEntity.getContentEncoding();
//            }
//            @Override
//            public long getContentLength() {
//                return yourEntity.getContentLength();
//            }
//            @Override
//            public Header getContentType() {
//                return yourEntity.getContentType();
//            }
//            @Override
//            public boolean isChunked() {             
//                return yourEntity.isChunked();
//            }
//            @Override
//            public boolean isRepeatable() {
//                return yourEntity.isRepeatable();
//            }
//            @Override
//            public boolean isStreaming() {             
//                return yourEntity.isStreaming();
//            } // CONSIDER put a _real_ delegator into here!
//
//            @Override
//            public void writeTo(OutputStream outstream) throws IOException {
//
//                class ProxyOutputStream extends FilterOutputStream {
//                    /**
//                     * @author Stephen Colebourne
//                     */
//
//                    public ProxyOutputStream(OutputStream proxy) {
//                        super(proxy);    
//                    }
//                    public void write(int idx) throws IOException {
//                        out.write(idx);
//                    }
//                    public void write(byte[] bts) throws IOException {
//                        out.write(bts);
//                    }
//                    public void write(byte[] bts, int st, int end) throws IOException {
//                        out.write(bts, st, end);
//                    }
//                    public void flush() throws IOException {
//                        out.flush();
//                    }
//                    public void close() throws IOException {
//                        out.close();
//                    }
//                } // CONSIDER import this class (and risk more Jar File Hell)
//
//                class ProgressiveOutputStream extends ProxyOutputStream {
//                    public ProgressiveOutputStream(OutputStream proxy) {
//                        super(proxy);
//                    }
//                    public void write(byte[] bts, int st, int end) throws IOException {
//
//                        // FIXME  Put your progress bar stuff here!
//
//                        out.write(bts, st, end);
//                    }
//                }
//
//                yourEntity.writeTo(new ProgressiveOutputStream(outstream));
//            }
//
//        };
//        ProgressiveEntity myEntity = new ProgressiveEntity();
//
//        post.setEntity(myEntity);
//        HttpResponse response = client.execute(post);        

//        return getContent(response);
    }
}
