package com.example.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pojo.Instrumento;
import com.exeample.service.InstrumentoService;


public class ConsultaCadastro extends Activity {
	
	private TextView labelID;
	private EditText txtNome;
    private EditText txtTipo;
    private EditText txtDono;
    private EditText txtTelefone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.consulta_cadastro);
		
		labelID   = (TextView) findViewById(R.id.labelID);
		txtNome   = (EditText) findViewById(R.id.campoNome);
		txtTipo  = (EditText) findViewById(R.id.campoTipo);
	    txtDono = (EditText) findViewById(R.id.campoDono);
	    txtTelefone = (EditText) findViewById(R.id.campoTelefone);
	    
	    Bundle extras = getIntent().getExtras();
	    
		carregaInstrumento(Integer.parseInt(extras.getString("idInstrumento")));
		
		
		//Botões
		Button botaoAtualizar = (Button) findViewById(R.id.botaoAtualizar);
	        
	    botaoAtualizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				InstrumentoService service = new InstrumentoService(ConsultaCadastro.this);
				String item = labelID.getText().toString();
				String str = item.substring(item.lastIndexOf(':')+1).trim();
				int id = Integer.parseInt(str);
				
				Instrumento instrumento = service.getInstrumento(id);
				instrumento.setNome(txtNome.getText().toString());
				instrumento.setTipo(txtTipo.getText().toString());
				instrumento.setDono(txtDono.getText().toString());
				instrumento.setTelefone(txtTelefone.getText().toString());
        
        		service.updateContact(instrumento);
        		
        		startActivity(new Intent(ConsultaCadastro.this, MainActivity.class));
				
				
			}
		});
	    
	    
	    Button botaoExcluir = (Button) findViewById(R.id.botaoExcluir);
        
	    botaoExcluir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				InstrumentoService service = new InstrumentoService(ConsultaCadastro.this);
				String item = labelID.getText().toString();
				String str = item.substring(item.lastIndexOf(':')+1).trim();
				int id = Integer.parseInt(str);
				Instrumento instrumento = service.getInstrumento(id);
				service.remove(instrumento);
				startActivity(new Intent(ConsultaCadastro.this, MainActivity.class));
				
				
			}
		});
	    
	    Button botaoLigar = (Button) findViewById(R.id.botaoLigar);
	    
	    botaoLigar.setOnClickListener(new OnClickListener() {
	    	
	    	@Override
	    	public void onClick(View arg0) {
	    		
	    		Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txtTelefone.getText().toString()));
	    		startActivity(call);
	    	}
	    });
		
	}
	
	private void carregaInstrumento(int idInstrumento){

		//Chama o serviço de consulta
    	InstrumentoService service = new InstrumentoService(ConsultaCadastro.this);
    	Instrumento instrumento = service.getInstrumento(idInstrumento);
    	
    	//Carrega a View com os dados da Base, pelo Objeto de Negócio (Aluno)
    	labelID.setText("ID Instrumento: "+ instrumento.getId()); 
    	txtNome.setText(instrumento.getNome());
    	txtTipo.setText(instrumento.getTipo());
    	txtDono.setText(instrumento.getDono());
    	txtTelefone.setText(instrumento.getTelefone());
        }
			    
	
}
