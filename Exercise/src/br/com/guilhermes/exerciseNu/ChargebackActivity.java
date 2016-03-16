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
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ChargebackActivity extends Activity {

	private TextView titulo;
	private JSONObject json;
	private ImageView cadeado;
	private TextView lblImg;
	private Switch mrc;
	private Switch cip;
	private EditText comentario;
	private Button btnCancelar;
	private Button btnContestar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chargeback);		
		
		titulo = (TextView) findViewById(R.id.lbl_chg_bk_titulo);
		cadeado = (ImageView) findViewById(R.id.img_cadeado);
		lblImg = (TextView)findViewById(R.id.lbl_desc_img);
		cip = (Switch) findViewById(R.id.card_in_possession);
		btnCancelar = (Button) findViewById(R.id.btn_cancelar);
		btnContestar = (Button) findViewById(R.id.btn_contestar);
		mrc = (Switch) findViewById(R.id.merchant_recognized);
		comentario = (EditText) findViewById(R.id.comentario);

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
		    
			titulo.setText(saida.get(StaticVars.TITLE).toString());			
			JSONObject joBlkUblk = json.getJSONObject(StaticVars.LINKS);			
			String sAutoBlock = null;
			
			if(json.getBoolean(StaticVars.AUTO_BLOCK))
			{
				JSONObject joBlk = joBlkUblk.getJSONObject(StaticVars.BLOCK_CARD);
				//endereco block
				sAutoBlock = new TratarPostRequests().execute(joBlk.getString(StaticVars.HREF), null).get();				
				BlockUnblockCard(new JSONObject(sAutoBlock), StaticVars.BLOCK_CARD);
			}
			else
			{
				JSONObject joUnBlk = joBlkUblk.getJSONObject(StaticVars.UNBLOCK_CARD);
				//endereco unblock
				sAutoBlock = new TratarPostRequests().execute(joUnBlk.getString(StaticVars.HREF), null).get();				
				BlockUnblockCard(new JSONObject(sAutoBlock), StaticVars.UNBLOCK_CARD);
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
			comentario.setHint(Html.fromHtml(json.getString(StaticVars.COMMENT_HINT)));
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		comentario.addTextChangedListener(new TextWatcher() {          
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {                                   

            }                       
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {                          
            }                       
            @Override
            public void afterTextChanged(Editable s) {                         
            	
            	if(comentario.getText().toString().trim().length() > 0){
	            	btnContestar.setEnabled(true);
	            	btnContestar.setTextColor(getResources().getColor(R.color.purple));
            	}
            	else{
            		btnContestar.setEnabled(false);
            		btnContestar.setTextColor(getResources().getColor(R.color.disabled_gray));
            	}
            }
        });
		
		btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            	System.exit(0);
            }
        });

		btnContestar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	String resp = null;
				try {
					JSONObject response = new JSONObject();
					JSONArray jsArray = new JSONArray();
					jsArray.put(
							new JSONObject()
							.put(StaticVars.RESPONSE, mrc.isChecked())
							.put(StaticVars.ID, StaticVars.MERCHANT_RECONIZED)
							);
					jsArray.put(
							new JSONObject()
							.put(StaticVars.RESPONSE, cip.isChecked())
							.put(StaticVars.ID, StaticVars.CARD_IN_POSSESSION)
							);
							
					response.put(StaticVars.COMMENT, comentario.getText().toString().trim());
					response.put(StaticVars.REASON_DETAILS, jsArray.toString());
					
					resp = new TratarPostRequests().execute(json.getString(StaticVars.SELF), response.toString()).get();					
	            	
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}                
            }
        });
				
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
