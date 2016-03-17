package br.com.guilhermes.exerciseNu;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import br.com.guilhermes.exerciseNu.modelo.StaticVars;
import br.com.guilhermes.exerciseNu.requests.TratarPostRequests;

public class ChargebackActivity extends Activity {

	//Titulo
	private TextView titulo;
	//resposta do post Notice - JSON principal
	private JSONObject json;
	//Imagem/Icone do cadeado
	private ImageView cadeado;
	//Label que acompanha Imagem
	private TextView lblImg;
	//Interruptores do Merchant e Cartao
	private Switch mrc;
	private Switch cip;
	//Justificativa do usuario
	private EditText comentario;
	
	//botoes Cancelar e Contestar
	private Button btnCancelar;
	private Button btnContestar;
	
	//Tratamento de "Session"
	private SharedPreferences session;
	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chargeback);		
		session = getApplicationContext().getSharedPreferences("Sessions", 0);
		editor = session.edit();
		
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
		    
		    titulo.setText(json.get(StaticVars.TITLE).toString());
			
			//salvar na session este Json, contem o link(self) para post final
			editor.putString("JSON", json.getJSONObject(StaticVars.LINKS).getJSONObject(StaticVars.SELF).toString());
			editor.commit();
			
			String sAutoBlock = null;			
			if(json.getBoolean(StaticVars.AUTO_BLOCK))
			{
				//endereco block
				sAutoBlock = new TratarPostRequests()
						.execute(
								json.getJSONObject(StaticVars.LINKS)
								.getJSONObject(StaticVars.BLOCK_CARD)
								.getString(StaticVars.HREF),
								null)
						.get();
				BlockUnblockCard(new JSONObject(sAutoBlock), StaticVars.BLOCK_CARD);
			}
			else
			{
				//endereco unblock
				sAutoBlock = new TratarPostRequests()
						.execute(
								json.getJSONObject(StaticVars.LINKS)
								.getJSONObject(StaticVars.UNBLOCK_CARD)
								.getString(StaticVars.HREF),
								null)
						.get();				
				BlockUnblockCard(new JSONObject(sAutoBlock), StaticVars.UNBLOCK_CARD);
			}
			
			JSONArray jsoA = json.getJSONArray(StaticVars.REASON_DETAILS);
			
			mrc.setText(jsoA.getJSONObject(0).getString(StaticVars.TITLE).toString());			
			cip.setText(jsoA.getJSONObject(1).getString(StaticVars.TITLE).toString());
			
			comentario.setHint(Html.fromHtml(json.getString(StaticVars.COMMENT_HINT)));
			
		} catch (JSONException e) {
			e.printStackTrace();
			lblImg.setText(StaticVars.ERR_GERAL);
		} catch (InterruptedException e) {
			e.printStackTrace();
			lblImg.setText(StaticVars.ERR_GERAL);
		} catch (ExecutionException e) {
			e.printStackTrace();
			lblImg.setText(StaticVars.ERR_GERAL);
		}catch (Exception e){
			e.printStackTrace();
			lblImg.setText(StaticVars.ERR_GERAL);
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
					
					JSONObject post = new JSONObject(session.getString("JSON", null));
					
					resp = new TratarPostRequests().execute(post.getString(StaticVars.HREF), response.toString()).get();					
	            	
					JSONObject rsp = new JSONObject(resp);
					if(rsp.getString(StaticVars.STATUS).toLowerCase().equals("ok"))
					{
						AlertDialog.Builder alert = new AlertDialog.Builder(ChargebackActivity.this);
						alert.setTitle(StaticVars.OK_CONTESTACAO);
						alert.setMessage(StaticVars.MSG_CONTESTACAO);
						alert.setPositiveButton("Fechar",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int whichButton) {
													//encerrar aplicação						                                
													finish();
													System.exit(0);
													
													}
												}
											);
						alert.show();
					}
					else
					{
						throw new Exception("Não foi possível fazer sua contestação.");
					}
					
					editor.clear();
					editor.commit();
				} catch (InterruptedException e) {
					e.printStackTrace();
					lblImg.setText(StaticVars.ERR_GERAL);
				} catch (ExecutionException e) {
					e.printStackTrace();
					lblImg.setText(StaticVars.ERR_GERAL);
				} catch (JSONException e) {
					e.printStackTrace();
					lblImg.setText(StaticVars.ERR_GERAL);
				} catch (Exception e) {
					e.printStackTrace();
					lblImg.setText(StaticVars.ERR_GERAL);
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
	private void BlockUnblockCard(JSONObject jo, String action)
	{
		try {
			if(jo.getString(StaticVars.STATUS).toLowerCase().equals("ok"))
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
