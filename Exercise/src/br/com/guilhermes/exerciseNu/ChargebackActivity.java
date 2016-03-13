package br.com.guilhermes.exerciseNu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChargebackActivity extends Activity {

	private JSONObject json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chargeback);
		
		try {
			json = new JSONObject(getIntent().getStringExtra("conteudo"));
			Map<String, Object> saida = new HashMap();
			Iterator<String> chaves = json.keys();
		    while(chaves.hasNext()){
		        String chave = chaves.next();
		        String valor = null;
		        try{
		             JSONObject jso = json.getJSONObject(chave);
		             saida.put(chave, jso);
		        }catch(Exception e){
		            valor = json.getString(chave);
		        }

		        if(valor != null){
		            saida.put(chave, valor);
		        }
		    }
			
			boolean joAutoBlock = json.getBoolean(StaticVars.AUTO_BLOCK);
			String joReasonDt = json.getString(StaticVars.REASON_DETAILS);
			joReasonDt = joReasonDt.replace('[', ' ').replace(']', ' ').trim();
			
			TextView titulo = (TextView) findViewById(R.id.lblChgBkTitulo);
			titulo.setText(saida.get(StaticVars.TITLE).toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
