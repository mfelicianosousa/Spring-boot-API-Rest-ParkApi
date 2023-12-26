package br.com.mfsdevsystem.parkapi.util;

import java.time.LocalDateTime;

import org.modelmapper.config.Configuration.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

	private EstacionamentoUtils() {
    }
	
   public static String gerarRecibo() {
	   LocalDateTime date = LocalDateTime.now();
	   String recibo = date.toString().substring(0,19);
	   return recibo.replace( "-","" ).replace( ":", "").replace( "T", "-");
   }
   
   
}
