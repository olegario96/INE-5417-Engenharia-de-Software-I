package modelo;

public class Monstro {
	
	protected int _ataque;
	protected Posicao _posicao;
	protected boolean _ja_moveu;
	protected boolean _ja_atacou;
	protected int _custoInvocacao;
	protected int _custo_movimento;
	protected int _custo_ataque;
	protected Jogador _invocador;
	protected int id;
	protected int monstro;
	
	
	public Monstro(int _ataque, Posicao _posicao, int _custoInvocacao, int _custo_movimento, int _custo_ataque,Jogador _invocador, int id, int monstro) {
		this._ataque = _ataque;
		this._posicao = _posicao;
		this._custoInvocacao = _custoInvocacao;
		this._custo_movimento = _custo_movimento;
		this._custo_ataque = _custo_ataque;
		this._invocador = _invocador;
		this.id = id;
		this.monstro = monstro;
	}

	public boolean getJaMoveu() {
		return _ja_moveu;
	}

	public void setJaMoveu(boolean aMoveu) {
		this._ja_moveu = aMoveu;
	}

	public Jogador getInvocador() {
		return this._invocador;
	}

	public void destruirMonstro() {
		Jogador jogador = this.getInvocador();
		jogador.getMonstros().remove(this);
		jogador.adicionarMonstroDestruido();
	}

	public void setJa_atacou(boolean aM_ja_atacou) {
		this._ja_atacou = aM_ja_atacou;
	}

	public boolean getJa_atacou() {
		return this._ja_atacou;
	}

	public void monstroDestruido() {
		throw new UnsupportedOperationException();
	}

	public void reiniciaMonstro() {
		this.setJa_atacou(false);
		this.setJaMoveu(false);
	}


	public void ataqueMonstro_Posicao_posicao_() {
		throw new UnsupportedOperationException();
	}
	
	public Posicao getPosicao() {
		return this._posicao;
	}
	
	public int estrelasParaInvocacao() {
		return this._custoInvocacao;
	}
	
	public int estrelasParaMovimento() {	
		return this._custo_movimento;
	}
	
	public int estrelasParaAtaque() {
		return this._custo_ataque;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getTipoMonstro() {
		return this.monstro;
	}
	
	public int getAtaque() {
		return this._ataque;
	}
	
}