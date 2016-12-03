package modelo;

public class Tabuleiro {
	private Jogador _jogador1;
	private Jogador _jogador2;
	private boolean _partida_em_andamento;
	private boolean _conectado;
	private Dado _dados[];
	private Posicao posicoes[][];

	public Tabuleiro(Jogador _jogador1, Jogador _jogador2) {
		this._jogador1 = _jogador1;
		this._jogador2 = _jogador2;
		iniciaizarTabuleiro();
	}

	public boolean conectar() {
		throw new UnsupportedOperationException();
	}

	public boolean desconectar() {
		throw new UnsupportedOperationException();
	}

	public Dado[] getDados() {
		return this._dados;
	}

	public boolean verificaPosicao(Posicao posicao) {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (posicoes[i][j].equals(posicao)) {
					return posicoes[i][j].casaVazia();
				}
			}
		}
		return false;
	}

	public Jogada invocaMonstro(Monstro aMonstro, Posicao posicao,
			Jogador jogador_1) {
		posicao = this.getPosicao(posicao.getLinha(), posicao.getColuna());
		posicao.setOcupante(aMonstro);
		aMonstro.setPosicao(posicao);
		aMonstro.setInvocador(jogador_1);
		aMonstro.getInvocador().adicionaMonstro(aMonstro);
		Jogada jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada._invocarMonstro, aMonstro, null, null);

		aMonstro.getInvocador().diminuiEstrelas(
				aMonstro.estrelasParaInvocacao());
		return jogada;
	}

	public TipoJogada mudaJogador() {
		if (_jogador1.getSeuTurno()) {
			return _jogador1.darAVez();
		} else {
			return _jogador2.darAVez();
		}
	}

	public Jogada movimentaMonstro(Monstro aMonstro, Posicao posicao) {
		aMonstro.setJaMoveu(true);
		Monstro monstro_fonte = new Monstro(aMonstro.getAtaque(),
				aMonstro.getPosicao(), aMonstro.estrelasParaInvocacao(),
				aMonstro.estrelasParaMovimento(),
				aMonstro.estrelasParaAtaque(), aMonstro.getInvocador(),
				aMonstro.getTipoMonstro());
		
		Jogada jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada._moverMonstro, monstro_fonte, null, null);
		
		Posicao antiga = this.getPosicao(aMonstro.getPosicao().getLinha(),
				aMonstro.getPosicao().getColuna());
		
		antiga.setOcupante(null);
		posicao.setOcupante(aMonstro);
		aMonstro.setPosicao(posicao);
		aMonstro.getInvocador().diminuiEstrelas(
				aMonstro.estrelasParaMovimento());
		
		return jogada;
	}

	public void destroiMonstro(Monstro monstro, Posicao posicao) {
		posicao.setOcupante(null);
		monstro.setPosicao(null);
		Jogador invocador = monstro.getInvocador();
		invocador.adicionarMonstroDestruido(monstro);
	}

	public boolean getConectado() {
		return this._conectado;
	}

	public void reproduzirJogada(Jogada aJogada, int aTipoJogada) {
		TipoJogada jogada = aJogada.getTipoJogada();
		switch (jogada) {
		case _rolar_dados:
			break;

		case _atacar:

			break;

		case _moverMonstro:
			break;

		case _usarHabilidade:
		}
	}

	public void iniciaizarTabuleiro() {
		this._dados = new Dado[3];
		for (int i = 0; i < 3; i++) {
			_dados[i] = new Dado();
		}

		this.posicoes = new Posicao[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				posicoes[i][j] = new Posicao(null, i, j);
			}
		}
		this._partida_em_andamento = true;
		this._conectado = true;
	}

	public Jogador getJogador1() {
		return this._jogador1;
	}

	public Jogador getJogador2() {
		return this._jogador2;
	}

	public Jogada usarHabilidade(Monstro_Com_Habilidade aMonstro) {
		switch (aMonstro.getHabilidade()) {
		case _atacar:
			aMonstro.setJa_atacou(false);
			break;
		case _moverMonstro:
			aMonstro.setJaMoveu(false);
			break;
		default:
			aMonstro.getInvocador().setDados(false);
		}
		aMonstro.setJaUsouHabilidade(true);
		Jogada jogada = new Jogada(0, 0, TipoJogada._usarHabilidade, aMonstro, null,
				null);
		return jogada;
	}

	public boolean partidaEmAndamento() {
		return this._partida_em_andamento;
	}

	public Posicao getPosicao(int linha, int coluna) {
		return this.posicoes[linha][coluna];
	}

	public void setPartidaEmAndamento(boolean m_partidaEmAndamento) {
		this._partida_em_andamento = m_partidaEmAndamento;
	}
	
	public Dado[] rolarDados() {
		for (int i = 0; i < _dados.length; i++) {
			this._dados[i].rolaDado();
		}
		return this._dados;
	}
}