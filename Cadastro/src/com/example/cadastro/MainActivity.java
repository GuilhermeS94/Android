package com.example.cadastro;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pojo.Instrumento;
import com.exeample.service.InstrumentoService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_lista);
        
        ListView listaInstrumentos = (ListView) findViewById(R.id.listaInstrumento);
        listaInstrumentos.setClickable(true);
        
        listaInstrumentos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
				 
				String item = ((TextView)view).getText().toString();
                
				//Captura o código do cadastro
				String str = item.substring(0, item.lastIndexOf('-')).trim();
			
				
				Intent intent = new Intent(MainActivity.this, ConsultaCadastro.class);
				intent.putExtra("idInstrumento", str);
				startActivity(intent);
			}
		
        });
        
        //Carrega a lista de alunos        
        carregaListaInstrumentos();
    }
    
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    	
    	//Carrega a lista de alunos
    	carregaListaInstrumentos();
    }
    
    private void carregaListaInstrumentos(){
    	
    	InstrumentoService service = new InstrumentoService(MainActivity.this);
    	
    	List<Instrumento> listaInstrumentos = service.getListInstrumento();
    	
    	List<String> lista = new ArrayList<String>();
    	
    	for(Instrumento instrumento : listaInstrumentos){
    		lista.add(instrumento.getId() + " - " + instrumento.getNome() + "(" + instrumento.getDono() + ")" );
    	}
    	    	
    	
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        
        //Captura item de tela e carrega a lista
        ListView listViewInstrumentos = (ListView) findViewById(R.id.listaInstrumento);
        
        listViewInstrumentos.setAdapter(adapter);

    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	MenuItem novoInstrumento = menu.add(0,0,0,"Novo Instrumento");
    	
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	 
    	switch (item.getItemId()){
    	case 0:	startActivity(new Intent(MainActivity.this, CadastroActivity.class)); break;
    	case 1:	startActivity(new Intent(MainActivity.this, ConsultaCadastro.class)); break;
    	}
    	
    	return false;
    }
   
    
    }
