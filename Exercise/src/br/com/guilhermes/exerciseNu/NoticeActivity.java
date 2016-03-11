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
			String resp = new TratarRequests().execute(StaticVars.NOTICE).get();
			JSONObject json = new TratarJson().ConverterJsonParaJSONObject(resp);
			TextView nvTitulo = (TextView) findViewById(R.id.lblTitulo);			        
			nvTitulo.setText(json.get(StaticVars.TITLE).toString());			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
}
