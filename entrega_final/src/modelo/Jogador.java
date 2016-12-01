package modelo;

import java.util.ArrayList;

public class Jogador {

	private String _nome;
	private int id;
	private boolean _seu_turno;
	private boolean _vencedor;
	private ArrayList<Monstro> _monstros;
	private boolean _ja_rolou_dados;
	private int _numEstrelas;
	private int _monstrosDestruidos;
	private int pontos_de_vida;
	private int numero_monstros;
	
	public Jogador(String _nome, int id) {
		this._nome = _nome;
		this.pontos_de_vida = 3;
		this._monstrosDestruidos = 0;
		this._numEstrelas = 0;
		this._monstros = new ArrayList<Monstro>();
		this.numero_monstros = 0;
		this.id = id;
		this._seu_turno = false;
		this._ja_rolou_dados = false; 
		this._vencedor = false;
	}

	public TipoJogada darAVez() {
		this.resetarMonstros();
		this.resetarDados();
		this.desabilitaJogador();
		return TipoJogada._darVez;
	}

	public void resetarMonstros() {
		for (Monstro m : _monstros) {
			m.reiniciaMonstro();
		}
	}
	
	public void setDados(boolean dados) {
		this._ja_rolou_dados = dados;
	}

	public void resetarDados() {
		this._ja_rolou_dados = false;
	}

	public void desabilitaJogador() {
		this._seu_turno = false;
	}

	public void setVencedor() {
		this._vencedor = true;
	}

	public boolean getJaRolou() {
		return this._ja_rolou_dados;
	}

	public void adicionaMonstro(Monstro aEscolha) {
		this._monstros.add(aEscolha);
	}

	public void diminuiEstrelas(int aEstrelas) {
		this._numEstrelas = this._numEstrelas - aEstrelas;
	}

	public boolean possuiMonstro() {
		return (_monstros.size() > 0);
	}

	public void setSeu_turno(boolean aM_seu_turno) {
		this._seu_turno = aM_seu_turno;
	}

	public void adicionarMonstroDestruido() {
		++(this._monstrosDestruidos);
		this.avaliaPontosDeVida();
	}

	public void avaliaPontosDeVida() {
		if (this._monstrosDestruidos % 3 == 0) {
			--(this.pontos_de_vida);
		}
	}

	public ArrayList<String> habilidades() {
		ArrayList<String> habilidades = new ArrayList<String>();
		for(Monstro m : _monstros) {
			if (m instanceof Monstro_Com_Habilidade) {
				Monstro_Com_Habilidade m_h = (Monstro_Com_Habilidade) m;
				habilidades.add(m_h.escreverHabilidade());
			}
		}
		return habilidades;
	}

	public int getPontosDeVida() {
		return this.pontos_de_vida;
	}
	
	public boolean getSeuTurno() {
		return this._seu_turno;
	}

	public ArrayList<Monstro> getMonstros() {
		return this._monstros;
	}
	
	public String getNome() {
		return this._nome;
	}
	
	public void setName(String nome) {
		this._nome = nome;
	}

	public boolean compara(Jogador jogador) {
		return this.getId() == jogador.getId();
	}
	
	public int getId() {
		return this.id;
	}
	
	public void adicionaEstrelas(int estrelas) {
		this._numEstrelas += estrelas;
	}
	
	public int getEstrelas() {
		return this._numEstrelas;
	}
}