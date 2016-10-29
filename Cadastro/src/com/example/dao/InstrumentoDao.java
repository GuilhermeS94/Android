package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.pojo.Instrumento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InstrumentoDao extends SQLiteOpenHelper {


	private static int VERSION = 4;
	private static String DATA_BASE= "instrumento.db";
	private static String TABELA= "tb_instrumento";
	private static String[] COLS ={"id", "Nome","Tipo","Dono","Telefone"};
	
	private SQLiteDatabase conexao;
	

	//Construtor
	public InstrumentoDao(Context context){
		super(context, DATA_BASE, null, VERSION);
	}

	
	public void abreConexao(){
		this.conexao = getWritableDatabase();
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
				
		String sql = "CREATE TABLE " + TABELA +
				     "(id INTEGER PRIMARY KEY, " +
				     "nome TEXT, " +
				     "tipo TEXT, " +
				     "dono TEXT, " +
				     "telefone TEXT);";
		
		db.execSQL(sql);
		
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		String sql = "DROP TABLE IF EXISTS " + TABELA + "; ";

		db.execSQL(sql);
		this.onCreate(db);
	}
	
	
	
	public void insertInstrumento(Instrumento instrumento){

		ContentValues values= new ContentValues();
		
		//Adiciona valores aos campos da tabela
		values.put("nome", instrumento.getNome());
		values.put("tipo", instrumento.getTipo());
		values.put("dono", instrumento.getDono());
		values.put("telefone", instrumento.getTelefone());
		
		//Executa o comando de insert
		conexao.insert(TABELA, null, values);
		
	}
	
	public int updateInstrumento(Instrumento instrumento) {
	    
		//Adiciona os campos com os novos valores
	    ContentValues values = new ContentValues();
	    values.put("nome", instrumento.getNome());
		values.put("tipo", instrumento.getTipo());
		values.put("dono", instrumento.getDono());
		values.put("telefone", instrumento.getTelefone());

		
	    // Atualiza os campos passados pelo parametro 'values', com a condição id = aluno.getId() 
	    int retorno = conexao.update(TABELA, values, "id = ?", new String[] { String.valueOf(instrumento.getId()) });
	    
	    //Retorna o numeros de linhas atualizadas
	    return retorno;
	}
	
	public void remove(Instrumento instrumento){
		
		//Remove a linha na qual o id = aluno.getId()
	    conexao.delete(TABELA, "id = ?", new String[] { String.valueOf(instrumento.getId()) });
	
	}
	
	public Instrumento getInstrumento(int id) {
	 
	    Cursor cursor = conexao.query(TABELA, COLS, "id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
	
	    Instrumento instrumento = new Instrumento();
	    
	    while (cursor.moveToNext()){
            
    		instrumento.setId(Integer.parseInt(cursor.getString(0)));
    		instrumento.setNome(cursor.getString(1));
    		instrumento.setTipo(cursor.getString(2));
    		instrumento.setDono(cursor.getString(3));
    		instrumento.setTelefone(cursor.getString(4));
	    }
	    
	    return instrumento;
	}
	
	
	public List<Instrumento> getListInstrumento() {
	    
		List<Instrumento> listaInstrumentos = new ArrayList<Instrumento>();
	    
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABELA;
	 
	    Cursor cursor = conexao.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
    	while (cursor.moveToNext()){
            
    		Instrumento instrumento = new Instrumento();
            
    		instrumento.setId(Integer.parseInt(cursor.getString(0)));
    		instrumento.setNome(cursor.getString(1));
    		instrumento.setTipo(cursor.getString(2));
    		instrumento.setDono(cursor.getString(3));
    		instrumento.setTelefone(cursor.getString(4));
    		
            // Adicionando os instrumentos na lista
            listaInstrumentos.add(instrumento);
        } 
	 
	    // retornando a lista de Instrumento carregados
	    return listaInstrumentos;
	}
	
	public SQLiteDatabase getConexao() {
		return conexao;
	}
	
}

