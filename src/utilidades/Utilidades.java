package utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Utilidades {

	public static Properties leerPropiedades(String ruta) {
		try {
			Properties salida = new Properties();
			salida.loadFromXML(new FileInputStream(ruta));
			return salida;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void sortRankNegros(String[] arr) {
		// Select sort
		int mayor;
		String tempNombre;
		String tempTiempo;
		
		for(int i = 1; i < arr.length; i += 2) {
			mayor = i;
			for(int j = i; j < arr.length; j += 2) {
				if(Long.parseLong(arr[j]) > Long.parseLong(arr[mayor])) {
					mayor = j;
				}
			}
			// Cambio
			tempNombre = arr[i - 1];
			tempTiempo = arr[i];
			arr[i - 1] = arr[mayor - 1];
			arr[i] = arr[mayor];
			arr[mayor - 1] = tempNombre;
			arr[mayor] = tempTiempo;
		}
	}
	
	public static String[] copiarArrString(String[] arr) {
		String[] salida = new String[arr.length];
		for(int i = 0; i < arr.length; i++) {
			salida[i] = arr[i];
		}
		return salida;
	}
	
	public static String msADias(long ms) {
		long dias = TimeUnit.MILLISECONDS.toDays(ms);
		ms -= TimeUnit.DAYS.toMillis(dias);
		long horas = TimeUnit.MILLISECONDS.toHours(ms);
		ms -= TimeUnit.HOURS.toMillis(horas);
		long minutos = TimeUnit.MILLISECONDS.toMinutes(ms);
		ms -= TimeUnit.MINUTES.toMillis(minutos);
		long segundos = TimeUnit.MILLISECONDS.toSeconds(ms);
		ms -= TimeUnit.SECONDS.toMillis(segundos);
		String salida = "";
		
		if(dias > 0) {
			salida += dias;
			if(dias == 1) {
				salida += " día";
			}else {
				salida += " días";
			}
		}
		if(horas > 0) {
			if(dias != 0) {
				salida += ", ";
			}
			salida += horas;
			if(horas == 1) {
				salida += " hora";
			}else {
				salida += " horas";
			}
		}
		if(minutos > 0) {
			if(dias != 0 || horas != 0) {
				salida += ", ";
			}
			salida += minutos;
			if(minutos == 1) {
				salida += " minuto";
			}else {
				salida += " minutos";
			}
		}
		if(segundos > 0) {
			if(dias != 0 || horas != 0 || minutos != 0) {
				salida += " y ";
			}
			salida += segundos;
			if(segundos == 1) {
				salida += " segundo";
			}else {
				salida += " segundos";
			}
		}
		return salida;
	}
}
