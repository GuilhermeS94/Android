package br.com.guilhermes.exerciseNu;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		
		//Solicitar o get
		try {
			String resp = new TratarRequests().execute("https://nu-mobile-hiring.herokuapp.com/notice").get();
			JSONObject json = new TratarJson().ConverterJsonParaJSONObject(resp);
			Iterator<String> temp = json.keys();
	        while (temp.hasNext()) {
	            String key = temp.next();
	            //criar switch case para pegar titulo e conteudo
	            //deste json
	            Object value = json.get(key);
	        }
			//Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG).show();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
}
