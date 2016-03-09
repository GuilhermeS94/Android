package br.com.guilhermes.exerciseNu;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NoticeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		
		//Solicitar o get
		AlterarTitulo();
	}
	
	/*Tratar a solicitação Get
	 * e atualizar a View com
	 * o JSON retornado
	 **/
	private void AlterarTitulo()
	{
		TextView nvTitulo = (TextView) findViewById(R.id.lblTitulo);			        
        nvTitulo.setText(new Date().toString());
	}
}
