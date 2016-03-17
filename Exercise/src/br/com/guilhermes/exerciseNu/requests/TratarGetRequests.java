package br.com.guilhermes.exerciseNu.requests;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import br.com.guilhermes.exerciseNu.modelo.Utils;

public class TratarGetRequests extends AsyncTask<String, String, String> {
	
	@Override
    protected String doInBackground(String... uri){
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String returnJson = "";
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }
	
}
