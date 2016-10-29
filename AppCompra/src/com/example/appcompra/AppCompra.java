package com.example.appcompra;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class AppCompra extends Activity {
	//referencia ao atributo se esta marcado ou nao
CheckBox chkarroz,chkleite,chkcarne,chkfeijao,chkcoca;

@Override
public void onCreate(Bundle icicle) {
super.onCreate(icicle);
//Alterar o ‘Main’ para o nome do layout criado
setContentView(R.layout.activity_app_compra);

chkarroz = (CheckBox) findViewById(R.id.chkarroz);
chkleite = (CheckBox) findViewById(R.id.chkleite);
chkcarne = (CheckBox) findViewById(R.id.chkcarne);
chkfeijao = (CheckBox) findViewById(R.id.chkfeijao);
chkcoca = (CheckBox) findViewById(R.id.chkcoca);

//(Completar captura dos dados da camada View)

//Captura a instância do botão
Button bttotal = (Button) findViewById(R.id.bttotal);

//Adiciona o evento do click no botão
bttotal.setOnClickListener(new View.OnClickListener(){

//Método responsável pelo tratamento do click
public void onClick(View arg0) {
double total= 0;

if(chkarroz.isChecked()){
	total = total + 2.69;
}
if(chkleite.isChecked()){
	total = total + 5.00;
}
if(chkcarne.isChecked()){
	total = total + 10.00;
}
if(chkfeijao.isChecked()){
	total = total + 2.30;
}
if(chkcoca.isChecked()){
	total = total + 2.00;
}

	

Toast.makeText(AppCompra.this, "Valor total compra:" + total, Toast.LENGTH_LONG);
}
});
}




}

