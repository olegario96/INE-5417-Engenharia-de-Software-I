package modelo;
import java.io.Serializable;
import java.util.Random;

public class Dado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6251163733923934772L;
	private int _face_atual;

	public Dado() {
	}

	public int rolaDado() {
		this._face_atual = (new Random()).nextInt(5) + 1;
		return _face_atual;
	}

	public int getFaceAtual() {
		return _face_atual;
	}
}