package br.com.guilhermes.exerciseNu;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class TratarPostRequests extends AsyncTask<String, String, String>  {

	@Override
	protected String doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(params[0]);
				
        HttpResponse response;
        String returnJson = "";
        try {
        	
        	if(params[1] != null)
        	{
        		StringEntity se = new StringEntity(params[1]);
            	httpPost.setEntity(se);
        	}
        	
            response = httpclient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                returnJson = new Utils().ConverterInStreamParaJSON(response.getEntity().getContent());
            } else{
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException cpe) {
        	cpe.printStackTrace();
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        } 
        return returnJson;
	}

}
