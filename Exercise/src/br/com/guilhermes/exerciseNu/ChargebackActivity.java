package br.com.guilhermes.exerciseNu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ChargebackActivity extends Activity {

	private JSONObject json;
	private ImageView cadeado;
	private TextView lblImg;
	private Switch mrc;
	private Switch cip;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chargeback);
		
		mrc = (Switch) findViewById(R.id.merchant_recognized);
		mrc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {            
                if (buttonView.isChecked()){
                	mrc.setTextColor(getResources().getColor(R.color.green));
                }else{
                	mrc.setTextColor(getResources().getColor(R.color.black));
                }
            }
		});
		
		cip = (Switch) findViewById(R.id.card_in_possession);
		cip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {            
                if (buttonView.isChecked()){
                	cip.setTextColor(getResources().getColor(R.color.green));
                }else{
                	cip.setTextColor(getResources().getColor(R.color.black));
                }
            }
		});
		
		try {
			json = new JSONObject(getIntent().getStringExtra("conteudo"));
			Map<String, Object> saida = new HashMap<String,Object>();
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
		    
		    TextView titulo = (TextView) findViewById(R.id.lbl_chg_bk_titulo);
			titulo.setText(saida.get(StaticVars.TITLE).toString());
			
			boolean joAutoBlock = json.getBoolean(StaticVars.AUTO_BLOCK);
			JSONObject joBlkUblk = json.getJSONObject(StaticVars.LINKS);
			
			String sAutoBlock = null;
			JSONObject action;
			cadeado = (ImageView) findViewById(R.id.img_cadeado);
			lblImg = (TextView)findViewById(R.id.lbl_desc_img);
			if(joAutoBlock)
			{
				JSONObject joBlk = joBlkUblk.getJSONObject(StaticVars.BLOCK_CARD);
				//endereco block
				sAutoBlock = new TratarPostRequests().execute(joBlk.getString(StaticVars.HREF), null).get();
				action = new JSONObject(sAutoBlock);
				BlockUnblockCard(action, StaticVars.BLOCK_CARD);
			}
			else
			{
				JSONObject joUnBlk = joBlkUblk.getJSONObject(StaticVars.UNBLOCK_CARD);
				//endereco unblock
				sAutoBlock = new TratarPostRequests().execute(joUnBlk.getString(StaticVars.HREF), null).get();
				action = new JSONObject(sAutoBlock);
				BlockUnblockCard(action, StaticVars.UNBLOCK_CARD);
			}
			
			JSONArray jsoA = json.getJSONArray(StaticVars.REASON_DETAILS);
			JSONObject jso;
			for (int i=0; i < jsoA.length(); i++) {
				try {
					jso = (JSONObject)jsoA.get(i);
					
					if(jso.get(StaticVars.ID).toString().equals(StaticVars.MERCHANT_RECONIZED))
					{
						mrc.setText(jso.getString(StaticVars.TITLE).toString());
					}
					else if(jso.get(StaticVars.ID).toString().equals(StaticVars.CARD_IN_POSSESSION))
					{
						cip.setText(jso.getString(StaticVars.TITLE).toString());
					}
					
				} catch (Exception e) {
					continue;
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * Método que configura a imagem e mensagem
	 * sobre o status do cartão
	 * */
	private void BlockUnblockCard(JSONObject json, String action)
	{
		try {
			if(json.getString(StaticVars.STATUS).toLowerCase().equals("ok"))
			{
				if(action.equals(StaticVars.BLOCK_CARD))
				{
					cadeado.setImageResource(R.drawable.charge_lock);
					lblImg.setText(StaticVars.OK_BLK);
				}else if(action.equals(StaticVars.UNBLOCK_CARD))
				{
					cadeado.setImageResource(R.drawable.charge_unlock);
					lblImg.setText(StaticVars.OK_UNBLK);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			lblImg.setText(StaticVars.ERR_GERAL);
		}
	}
}
