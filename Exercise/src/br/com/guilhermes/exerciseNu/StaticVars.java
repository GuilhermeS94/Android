package br.com.guilhermes.exerciseNu;

public class StaticVars {
	
	//primeiro endereco de acesso
	public static final String NOTICE = "https://nu-mobile-hiring.herokuapp.com/notice";
	
	//variaveis de retorno do JSON
	
	//Valor da chave - String
	public static final String TITLE = "title";
	//Valor da chave - String
	public static final String DESCRIPTION = "description";
	//Valor da chave - String
	public static final String PRIMARY_ACTION = "primary_action";
	//Valor da chave - String
	public static final String SECONDARY_ACTION = "secondary_action";
	//Valor da chave - String
	public static final String ACTION = "action";
	//Valor da chave - String
	public static final String LINKS = "links";
	//Valor da chave - String
	public static final String CHARGEBACK = "chargeback";
	//Valor da chave - String
	public static final String HREF = "href";
	//Valor da chave - String
	public static final String COMMENT_HINT = "comment_hint";
	//Valor da chave - String
	public static final String ID = "id";
	//Valor da chave - String
	public static final String RESPONSE = "response";
	//Valor da chave - boolean
	public static final String AUTO_BLOCK = "autoblock";
	//Valor da chave - JSON -> Reconhece estabelecimento? e Cartao em maos?
	public static final String REASON_DETAILS = "reason_details";
	//Valor da chave - String
	public static final String BLOCK_CARD = "block_card";
	//Valor da chave - String
	public static final String UNBLOCK_CARD = "unblock_card";
	//Valor da chave - String
	public static final String SELF = "self";	
	//Valor da chave - String
	public static final String MERCHANT_RECONIZED = "merchant_recognized";
	//Valor da chave - String
	public static final String CARD_IN_POSSESSION = "card_in_possession";
	//Valor da chave - String
	public static final String STATUS = "status";
	//Var de Erro - msg de erro ao bloquear o cart�o
	public static final String ERR_BLK = "Falha ao bloquear seu cart�o";
	//Var de Erro - msg de erro ao desbloquear o cart�o
	public static final String ERR_UNBLK = "Falha ao desbloquear seu cart�o";
	
	//Var de BLK ok - msg de sucesso ao bloquear o cart�o
	public static final String OK_BLK = "Cart�o bloqueado, recomendamos mant�-lo bloqueado.";
	//Var de UNBLK ok - msg de sucesso ao desbloquear o cart�o
	public static final String OK_UNBLK = "Bloqueamos preventivamente o seu cart�o.";
	
	//Var de Erro Geral - msg de erro gen�rica
	public static final String ERR_GERAL = "Falha ao processar os dados";
}
