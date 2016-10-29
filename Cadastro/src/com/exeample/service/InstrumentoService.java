package com.exeample.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.dao.InstrumentoDao;
import com.example.pojo.Instrumento;

public class InstrumentoService {

	Context 	   context;
	
	public InstrumentoService(Context context){
		this.context = context;
	}
	
	
	public void insert(Instrumento instrumento){

		//Instancia a camada DAO e tamb�m abre a conex�o pelo m�todo onCreate() 
		InstrumentoDao instrumentoDao = new InstrumentoDao(this.context);
		
		instrumentoDao.abreConexao();
		
		//Executa o comando INSERT
		instrumentoDao.insertInstrumento(instrumento);

		
		//Fecha Conex�o
		instrumentoDao.close();
		
	}
	
	public void updateContact(Instrumento instrumento) {
	    
		//Instancia a camada DAO e tamb�m abre a conex�o pelo m�todo onCreate() 
		InstrumentoDao instrumentoDao = new InstrumentoDao(this.context);
		
		instrumentoDao.abreConexao();
		
		//Executa o comando UPDATE
		instrumentoDao.updateInstrumento(instrumento);

		
		//Fecha Conex�o
		instrumentoDao.close();
	}
	
	public void remove(Instrumento instrumento){
		//Instancia a camada DAO e tamb�m abre a conex�o pelo m�todo onCreate() 
		InstrumentoDao instrumentoDao = new InstrumentoDao(this.context);
		
		instrumentoDao.abreConexao();
		
		//Executa o comando DELETE
		instrumentoDao.remove(instrumento);

		
		//Fecha Conex�o
		instrumentoDao.close();
	}
	
	public Instrumento getInstrumento(int id) {
	 
	    Instrumento instrumento = new Instrumento();
	    
	    //Instancia a camada DAO e tamb�m abre a conex�o pelo m�todo onCreate() 
  		InstrumentoDao instrumentoDao = new InstrumentoDao(this.context);
  		
  		instrumentoDao.abreConexao();
  		
  		//Executa a pesquisa
  		instrumento = instrumentoDao.getInstrumento(id);
  		
  		//Fecha Conex�o
  		instrumentoDao.close();
	    
	    return instrumento;
	}
	
	public List<Instrumento> getListInstrumento() {
	    
		List<Instrumento> listaInstrumentos = new ArrayList<Instrumento>();
		
		//Instancia a camada DAO e tamb�m abre a conex�o pelo m�todo onCreate() 
		InstrumentoDao instrumentoDao = new InstrumentoDao(this.context);
		
		instrumentoDao.abreConexao();
		
		//Executa a pesquisa
		listaInstrumentos = instrumentoDao.getListInstrumento();
				
		
		//Fecha Conex�o
		instrumentoDao.close();
	    
	    // retornando a lista de alunos carregados
	    return listaInstrumentos;
	}

	
	
	
}
