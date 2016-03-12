package br.com.guilhermes.exerciseNu;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NoticeActivity extends Activity {

	private JSONObject json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		
		//Solicitar o get
		try {
			String resp = new TratarRequests().execute(StaticVars.NOTICE).get();
			json = new TratarJson().ConverterJsonParaJSONObject(resp);
	        
			TextView nvTitulo = (TextView) findViewById(R.id.lblTitulo);			
			TextView nvConteudo = (TextView) findViewById(R.id.lblConteudo);
			nvTitulo.setText(json.get(StaticVars.TITLE).toString());
			nvConteudo.setText(Html.fromHtml(json.get(StaticVars.DESCRIPTION).toString()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Button btnFechar = (Button) findViewById(R.id.btnFechar);
        btnFechar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            	System.exit(0);
            }
        });
        
        Button btnCont = (Button) findViewById(R.id.btnContinuar);
        btnCont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent chargeBack = new Intent(NoticeActivity.this, ChargebackActivity.class);
            	String resp;
				try {
					JSONObject links = json.getJSONObject(StaticVars.LINKS);
					JSONObject chgBack = links.getJSONObject(StaticVars.CHARGEBACK);
					resp = new TratarRequests().execute(chgBack.get(StaticVars.HREF).toString()).get();
					chargeBack.putExtra("conteudo", resp);
	            	startActivity(chargeBack);
	            	
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}                
            }
        });
	}	
}
