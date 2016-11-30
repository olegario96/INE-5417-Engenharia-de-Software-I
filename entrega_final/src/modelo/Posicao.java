package modelo;

public class Posicao {

	private Monstro _ocupante;
	private int linha;
	private int coluna;

	public Posicao(Monstro _ocupante, int linha, int coluna) {
		this._ocupante = _ocupante;
		this.linha = linha;
		this.coluna = coluna;
	}

/*	public boolean verificaPosicao() {

	}*/

	public boolean casaVazia() {
		return this.getOcupante() == null;
	}

	public Monstro getOcupante() {
		return this._ocupante;
	}
	
	public void setOcupante(Monstro monstro) {
		this._ocupante = monstro;
	}
	
	public int getLinha() {
		return this.linha;
	}
	
	public int getColuna() {
		return this.coluna;
	}
}