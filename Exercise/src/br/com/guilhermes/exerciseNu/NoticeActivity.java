package br.com.guilhermes.exerciseNu;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.com.guilhermes.exerciseNu.modelo.StaticVars;
import br.com.guilhermes.exerciseNu.requests.TratarGetRequests;

public class NoticeActivity extends Activity {

	private JSONObject json;
	private TextView nvTitulo;
	private TextView nvConteudo;
	private Button btnFechar;
	private Button btnCont;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		btnFechar = (Button) findViewById(R.id.btn_fechar);
		btnCont = (Button) findViewById(R.id.btn_continuar);
		nvTitulo = (TextView) findViewById(R.id.lbl_titulo);			
		nvConteudo = (TextView) findViewById(R.id.lbl_conteudo);
		
		//Solicitar o get
		try {
			String resp = new TratarGetRequests().execute(StaticVars.NOTICE).get();
			json = new JSONObject(resp);      
			
			nvTitulo.setText(json.get(StaticVars.TITLE).toString());
			nvConteudo.setText(Html.fromHtml(json.get(StaticVars.DESCRIPTION).toString()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
        btnFechar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            	System.exit(0);
            }
        });

        btnCont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent chargeBack = new Intent(NoticeActivity.this, ChargebackActivity.class);
            	String resp;
				try {
					JSONObject links = json.getJSONObject(StaticVars.LINKS);
					JSONObject chgBack = links.getJSONObject(StaticVars.CHARGEBACK);
					resp = new TratarGetRequests().execute(chgBack.get(StaticVars.HREF).toString()).get();
					chargeBack.putExtra("conteudo", resp);
	            	startActivity(chargeBack);
	            	finish();
	            	
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
