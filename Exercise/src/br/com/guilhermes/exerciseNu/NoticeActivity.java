package br.com.guilhermes.exerciseNu;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NoticeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		
		//Solicitar o get
		try {
			String resp = new TratarRequests().execute(StaticVars.NOTICE).get();
			JSONObject json = new TratarJson().ConverterJsonParaJSONObject(resp);
			TextView nvTitulo = (TextView) findViewById(R.id.lblTitulo);
			//nvTitulo.setTypeface(null, Typeface.BOLD);
			TextView nvConteudo = (TextView) findViewById(R.id.lblConteudo);
			nvTitulo.setText(json.get(StaticVars.TITLE).toString());
			nvConteudo.setText(Html.fromHtml(json.get(StaticVars.DESCRIPTION).toString()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Button btnFechar = (Button) findViewById(R.id.btnFechar);
        btnFechar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            	System.exit(0);
            }
        });
	}	
}
