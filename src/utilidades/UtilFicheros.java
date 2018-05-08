package utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UtilFicheros {
	
	private static FileInputStream abrirLectura(String ruta) throws FileNotFoundException {
		return new FileInputStream(new File(ruta));
	}
	
	private static void cerrarLectura(FileInputStream fis) {
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static FileOutputStream abrirEscritura(String ruta) {
		try {
			return new FileOutputStream(new File(ruta));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static FileOutputStream abrirEscrituraAppend(String ruta) {
		try {
			return new FileOutputStream(new File(ruta), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void cerrarEscritura(FileOutputStream fos) {
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String leerFichero(String ruta) throws FileNotFoundException {
		String salida = "";
		FileInputStream fis = abrirLectura(ruta);
		int charActual;
		try {
			while((charActual = fis.read()) != -1) {
				salida += (char)charActual;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			cerrarLectura(fis);
		}
		return salida;
	}
	
	public static void escribirFichero(String ruta, String entrada) {
		FileOutputStream fos = abrirEscritura(ruta);
		int longitud = entrada.length();
		try {
			for(int i = 0; i < longitud; i++) {
				fos.write((int)entrada.charAt(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			cerrarEscritura(fos);
		}
	}
	
	public static void escribirFicheroAppend(String ruta, String entrada) {
		FileOutputStream fos = abrirEscrituraAppend(ruta);
		int longitud = entrada.length();
		try {
			for(int i = 0; i < longitud; i++) {
				fos.write((int)entrada.charAt(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			cerrarEscritura(fos);
		}
	}
}
