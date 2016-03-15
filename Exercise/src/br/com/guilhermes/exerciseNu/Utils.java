package br.com.guilhermes.exerciseNu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class Utils {
	
	/*
	 * Converte o Stream da Resposta(Get) em String
	 * */
	public String ConverterInStreamParaJSON(InputStream respStream) throws IOException{
		
		if (respStream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(respStream, "UTF-8"),1024);
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch(IOException ioex){
            	throw ioex;
            }finally {
                respStream.close();
            }
            return writer.toString();
        } else {
            return "";
        }
	}
	
}
