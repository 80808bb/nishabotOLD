package entidades;

import java.io.File;
import java.util.Random;

public class FotoRandom {

    private String rutaFotos;
    private int numFotos;
    private int numRandom;
    private File foto;

    public FotoRandom(String rutaFotos, int numFotos) {
        this.rutaFotos = rutaFotos;
        this.numFotos = numFotos;
        generarFoto();
    }

    private void generarFoto() {
        setNumRandom(new Random().nextInt((getNumFotos() - 1) + 1) + 1);
        setFoto(new File(getRutaFotos() + getNumRandom() + ".jpg"));
    }

    public File getFoto() {
        return foto;
    }

    public int getNumRandom() {
        return numRandom;
    }

    private String getRutaFotos() {
        return rutaFotos;
    }

    private void setRutaFotos(String rutaFotos) {
        this.rutaFotos = rutaFotos;
    }

    private int getNumFotos() {
        return numFotos;
    }

    private void setNumFotos(int numFotos) {
        this.numFotos = numFotos;
    }

    private void setNumRandom(int numRandom) {
        this.numRandom = numRandom;
    }

    private void setFoto(File foto) {
        this.foto = foto;
    }

}
