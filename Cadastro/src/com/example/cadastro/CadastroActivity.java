package com.example.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pojo.Instrumento;
import com.exeample.service.InstrumentoService;



public class CadastroActivity extends Activity {
	
	private EditText txtNome;
    private EditText txtTipo;
    private EditText txtDono;
    private EditText txtTelefone;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_instrumento);
        
        Button botao = (Button) findViewById(R.id.botaoInserir);
        
        botao.setOnClickListener(new OnClickListener() {

        	@Override
			public void onClick(View view) {
        		
        		//Link com a camada View
        		txtNome   = (EditText) findViewById(R.id.campoNome);
        	    txtTipo  = (EditText) findViewById(R.id.campoTipo);
        	    txtDono = (EditText) findViewById(R.id.campoDono);
        	    txtTelefone = (EditText) findViewById(R.id.campoTelefone);
        	    
        	    //Carregando o Objeto de negócio (POJO)
        		Instrumento instrumento = new Instrumento();
        		instrumento.setNome(txtNome.getText().toString());
        		instrumento.setTipo(txtTipo.getText().toString());
        		instrumento.setDono(txtDono.getText().toString());
        		instrumento.setTelefone(txtTelefone.getText().toString());
                
        		if(txtNome.getText().equals("")){
        			
        			 Toast.makeText(CadastroActivity.this, 
        		        	 "O nome do instrumento nao foi preenchido", 
        		        	 Toast.LENGTH_SHORT).show();        			
        		}
        		if(txtTipo.getText().equals("")){
        			
       			 Toast.makeText(CadastroActivity.this, 
       		        	 "O tipo do instrumento nao foi preenchido", 
       		        	 Toast.LENGTH_SHORT).show();        			
       		}
        		if(txtDono.getText().equals("")){
        			
       			 Toast.makeText(CadastroActivity.this, 
       		        	 "O dono do instrumento nao foi preenchido", 
       		        	 Toast.LENGTH_SHORT).show();        			
       		}
        		if(txtTelefone.getText().equals("")){
        			
       			 Toast.makeText(CadastroActivity.this, 
       		        	 "O telefone do instrumento nao foi preenchido", 
       		        	 Toast.LENGTH_SHORT).show();        			
       		}
        		
        		//Manipulando os dados na camada de Ação
        		//Vamos cadastrar os dados do POJO em uma base de dados
        		InstrumentoService service = new InstrumentoService(CadastroActivity.this);
        		service.insert(instrumento);
        		
        		//Após o cadastro vamos exibir a lista
        		startActivity(new Intent(CadastroActivity.this, MainActivity.class));

			}
		});
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
