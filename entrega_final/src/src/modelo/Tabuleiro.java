package modelo;

import visao.JanelaPrincipal;

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
	
	public Dado[] rolarDados() {
		for (int i = 0; i < _dados.length; i++) {
			this._dados[i].rolaDado();
		}
		return this._dados;
	}

	public Jogada invocaMonstro(Monstro aMonstro, Posicao posicao,
			Jogador jogador_1) {
		posicao = this.getPosicao(posicao.getLinha(), posicao.getColuna());
		posicao.setOcupante(aMonstro);
		aMonstro.setPosicao(posicao);
		aMonstro.setInvocador(jogador_1);
		aMonstro.getInvocador().adicionaMonstro(aMonstro);
		Jogada jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada.INVOCAR_MONSTRO, aMonstro, null, null);

		aMonstro.getInvocador().diminuiEstrelas(
				aMonstro.estrelasParaInvocacao());
		return jogada;
	}
	
	public Jogada movimentaMonstro(Monstro aMonstro, Posicao posicao) {
		aMonstro.setJaMoveu(true);
		Monstro monstro_fonte = new Monstro(aMonstro.getAtaque(),
				aMonstro.getPosicao(), aMonstro.estrelasParaInvocacao(),
				aMonstro.estrelasParaMovimento(),
				aMonstro.estrelasParaAtaque(), aMonstro.getInvocador(),
				aMonstro.getTipoMonstro());
		
		Jogada jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada.MOVER_MONSTRO, monstro_fonte, null, null);
		
		Posicao antiga = this.getPosicao(aMonstro.getPosicao().getLinha(),
				aMonstro.getPosicao().getColuna());
		
		antiga.setOcupante(null);
		posicao.setOcupante(aMonstro);
		aMonstro.setPosicao(posicao);
		aMonstro.getInvocador().diminuiEstrelas(
				aMonstro.estrelasParaMovimento());
		
		return jogada;
	}
	
	public Monstro atacarMonstro(Monstro monstro_fonte, Monstro monstro_alvo, Posicao posicao) {
		monstro_fonte.setJa_atacou(true);
		monstro_fonte.getInvocador().diminuiEstrelas(monstro_fonte.estrelasParaAtaque());
		monstro_alvo = posicao.getOcupante();
		Monstro destruido = this.comparaAtaqueMonstros(monstro_fonte, monstro_alvo);
		return destruido;
	}
	
	public void destroiMonstro(Monstro monstro, Posicao posicao) {
		posicao.setOcupante(null);
		monstro.setPosicao(null);
		Jogador invocador = monstro.getInvocador();
		invocador.adicionarMonstroDestruido(monstro);
	}
	
	public Monstro comparaAtaqueMonstros(Monstro escolha, Monstro monstroAlvo) {
		if (escolha.getAtaque() < monstroAlvo.getAtaque()) {
			return escolha;
		} else if (escolha.getAtaque() > monstroAlvo.getAtaque()) {
			return monstroAlvo;
		} else {
			return null;
		}
	}
	
	public Jogada usarHabilidade(Monstro_Com_Habilidade aMonstro) {
		switch (aMonstro.getHabilidade()) {
		case ATACAR:
			aMonstro.setJa_atacou(false);
			break;
		case MOVER_MONSTRO:
			aMonstro.setJaMoveu(false);
			break;
		default:
			aMonstro.getInvocador().setDados(false);
		}
		aMonstro.setJaUsouHabilidade(true);
		Jogada jogada = new Jogada(0, 0, TipoJogada.USAR_HABILIDADE, aMonstro, null,
				null);
		return jogada;
	}

	public TipoJogada mudaJogador() {
		if (_jogador1.getSeuTurno()) {
			return _jogador1.darAVez();
		} else {
			return _jogador2.darAVez();
		}
	}

	public void reproduzirJogada(Jogada jogada, Jogador jogador_2) {
		Monstro monstro_fonte = jogada.getMonstroFonte();
		Posicao posicao;
		switch (jogada.getTipoJogada()) {
		case ATACAR:
			posicao = this.getPosicao(jogada.getLinha(),
					jogada.getColuna());
			Monstro monstro_alvo = posicao.getOcupante();
			Monstro destruido = this.comparaAtaqueMonstros(monstro_fonte,
					monstro_alvo);
			this.destroiMonstro(destruido, destruido.getPosicao());
			JanelaPrincipal.atualizarInformacoes();
			break;

		case MOVER_MONSTRO:
			posicao = this.getPosicao(jogada.getLinha(),
					jogada.getColuna());
			this.movimentaMonstro(monstro_fonte, posicao);
			JanelaPrincipal.atualizarInformacoes();
			break;

		case USAR_HABILIDADE:
			((Monstro_Com_Habilidade) monstro_fonte).getHabilidade();
			break;

		case INVOCAR_MONSTRO:
			posicao = this.getPosicao(jogada.getLinha(),
					jogada.getColuna());
			this.invocaMonstro(monstro_fonte, posicao,
					monstro_fonte.getInvocador());
			monstro_fonte.setInvocador(jogador_2);
			monstro_fonte.getInvocador().adicionaMonstro(monstro_fonte);

			monstro_fonte.getInvocador().diminuiEstrelas(
					monstro_fonte.estrelasParaInvocacao());
			JanelaPrincipal.atualizarInformacoes();
			break;

		case DAR_VEZ:
			this.getJogador1().setSeu_turno(true);
			jogador_2.setSeu_turno(false);
			JanelaPrincipal.enableButtons();
			break;

		default:
			break;
		}
	}

	public Jogador getJogador1() {
		return this._jogador1;
	}

	public Jogador getJogador2() {
		return this._jogador2;
	}

	public boolean partidaEmAndamento() {
		return this._partida_em_andamento;
	}
	
	public boolean getConectado() {
		return this._conectado;
	}

	public Posicao getPosicao(int linha, int coluna) {
		return this.posicoes[linha][coluna];
	}

	public void setPartidaEmAndamento(boolean m_partidaEmAndamento) {
		this._partida_em_andamento = m_partidaEmAndamento;
	}	
}