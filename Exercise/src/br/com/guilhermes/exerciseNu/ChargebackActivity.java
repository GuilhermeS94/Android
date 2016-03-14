package br.com.guilhermes.exerciseNu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
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
			JSONArray jsoA = json.getJSONArray(StaticVars.REASON_DETAILS);
			JSONObject jso;
			for (int i=0; i < jsoA.length(); i++) {
				try {
					jso = (JSONObject)jsoA.get(i);
					
					if(jso.get(StaticVars.ID).toString().equals(StaticVars.MERCHANT_RECONIZED))
					{
						Switch merchant = (Switch) findViewById(R.id.merchant_recognized);
						merchant.setText(jso.getString(StaticVars.TITLE).toString());
					}
					else if(jso.get(StaticVars.ID).toString().equals(StaticVars.CARD_IN_POSSESSION))
					{
						Switch card = (Switch) findViewById(R.id.card_in_possession);
						card.setText(jso.getString(StaticVars.TITLE).toString());
					}
					
				} catch (Exception e) {
					continue;
				}
			}
			
			TextView titulo = (TextView) findViewById(R.id.lbl_chg_bk_titulo);
			titulo.setText(saida.get(StaticVars.TITLE).toString());
			TextView lblImg = (TextView)findViewById(R.id.lbl_desc_img);			
			//lblImg.setText();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
}
