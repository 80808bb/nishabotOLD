package utilidades;

import java.io.FileNotFoundException;

import net.dv8tion.jda.core.entities.User;

public class FicherosNishabot {
	
	private String rutaUltimoNegro;
	private String rutaRankNegros;
	
	public FicherosNishabot(String rutaNishabot) {
		rutaUltimoNegro = rutaNishabot + "ultimoNegro.txt";
		rutaRankNegros = rutaNishabot + "rankNegros.txt";
	}
	
	public String getRutaUltimoNegro() {
		return rutaUltimoNegro;
	}
	
	public String getRutaRankNegros() {
		return rutaRankNegros;
	}
	
	public String[] leerUltimoNegro() {
		String texto = "";
		try {
			texto = UtilFicheros.leerFichero(rutaUltimoNegro);
		} catch (FileNotFoundException e1) {
			System.out.println("El fichero ultimoNegro no existe.");
		}
		if(texto.length() > 0) {
			return texto.split(" ");
		}
		return null;
	}
	
	public void escribirUltimoNegro(User author) {
		String texto = author.getId() + " " + System.currentTimeMillis();
		UtilFicheros.escribirFichero(rutaUltimoNegro, texto);
	}
	
	public String[] leerRankNegros() {
		String texto = "";
		try {
			texto = UtilFicheros.leerFichero(rutaRankNegros);
		} catch (FileNotFoundException e1) {
			System.out.println("El fichero rankNegros no existe.");
		}
		if(texto.length() > 0) {
			return texto.split(" ");
		}
		return null;
	}
	
	public void escribirRankNegros(User user) {
		String[] arr = leerRankNegros();
		String[] arrUltimoNegro = leerUltimoNegro();
		
		if(arr != null) {
			boolean existe = false;
			for(int i = 0; i < arr.length && !existe; i += 2) {
				if(arr[i].equals(user.getId())) {
					existe = true;
					long tiempoAnterior = Long.parseLong(arr[i + 1]);
					long tiempoSumar = System.currentTimeMillis() - Long.parseLong(arrUltimoNegro[1]);
					long tiempoTotal = tiempoAnterior + tiempoSumar;
					arr[i + 1] = Long.toString(tiempoTotal);
				}
			}
			if(!existe) {
				String[] arrNuevo = new String[arr.length + 2];
				for(int i = 0; i < arr.length; i++) {
					arrNuevo[i] = arr[i];
				}
				arrNuevo[arr.length] = arrUltimoNegro[0];
				arrNuevo[arr.length + 1] = Long.toString(System.currentTimeMillis() - Long.parseLong(arrUltimoNegro[1]));
				Utilidades.sortRankNegros(arrNuevo);
				
				UtilFicheros.escribirFichero(rutaRankNegros, arrNuevo[0]);
				for(int i = 1; i < arrNuevo.length; i++) {
					UtilFicheros.escribirFicheroAppend(rutaRankNegros, " " + arrNuevo[i]);
				}
			}else {
				Utilidades.sortRankNegros(arr);
				UtilFicheros.escribirFichero(rutaRankNegros, arr[0]);
				for(int i = 1; i < arr.length; i++) {
					UtilFicheros.escribirFicheroAppend(rutaRankNegros, " " + arr[i]);
				}
			}
		}else {
			UtilFicheros.escribirFichero(rutaRankNegros, arrUltimoNegro[0] + " " + (System.currentTimeMillis() - Long.parseLong(arrUltimoNegro[1])));
		}
		
	}
}
