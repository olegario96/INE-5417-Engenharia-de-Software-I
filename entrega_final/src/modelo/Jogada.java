package modelo;

import java.io.Serializable;

public class Jogada implements br.ufsc.inf.leobr.cliente.Jogada, Serializable {

	private static final long serialVersionUID = 4797097674463485934L;
	
	private int _linha;
	private int _coluna;
	private TipoJogada _tipo;
	private Monstro _monstro_fonte;
	private Monstro _monstro_alvo;
	private Dado _dados[];

	public Jogada(int _linha, int _coluna, TipoJogada _tipo, Monstro _monstro_fonte, Monstro _monstro_alvo, Dado[] _dados) {
		this._linha = _linha;
		this._coluna = _coluna;
		this._tipo = _tipo;
		this._monstro_fonte = _monstro_fonte;
		this._monstro_alvo = _monstro_alvo;
		this._dados = _dados;
	}
	
	public int getLinha() {
		return this._linha;
	}

	public int getColuna() {
		return this._coluna;
	}	

	public TipoJogada getTipoJogada() {
		return this._tipo;
	}

	public Monstro getMonstroFonte() {
		return this._monstro_fonte;
	}

	public Monstro getMonstroAlvo() {
		return this._monstro_alvo;
	}

	public Dado[] getDados() {
		return this._dados;
	}
}