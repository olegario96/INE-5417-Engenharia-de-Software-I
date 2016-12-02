package modelo;

public class Monstro_Com_Habilidade extends Monstro {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2569163861153300956L;
	private TipoJogada _habilidade;
	private boolean _ja_usou_habilidade;

	public Monstro_Com_Habilidade(int _ataque, Posicao _posicao, int _custoInvocacao,
			int _custo_movimento, int _custo_ataque, Jogador _invocador,
			int monstro, TipoJogada _habilidade) {
		super(_ataque, _posicao, _custoInvocacao, _custo_movimento,
				_custo_ataque, _invocador, monstro);
		this._habilidade = _habilidade;
	}

	public String escreverHabilidade() {
		if (_habilidade == TipoJogada._atacar) {
			return "Pode atacar duas vezes";
		} else if (_habilidade == TipoJogada._moverMonstro) {
			return "Pode andar duas vezes";
		} else {
			return "Permite ao jogador rolar os dados mais uma vez";
		}
	}
	
	public TipoJogada getHabilidade() {
		return this._habilidade;
	}

	@Override
	public void reiniciaMonstro() {
		this.setJa_atacou(false);
		this.setJaMoveu(false);
		this.setJaUsouHabilidade(false);
	}

	public boolean getJaUsouHabilidade() {
		return this._ja_usou_habilidade;
	}

	public void setJaUsouHabilidade(boolean aM_ja_usou_habilidade) {
		this._ja_usou_habilidade = aM_ja_usou_habilidade;
	}
}