package controle;

import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import modelo.*;

public class AtorJogador {
	private int ordemUsuario;
	private boolean conectado;
	private AtorNetGames atorNetGames;
	private Tabuleiro tabuleiro;
	private Jogador jogador_1;
	private Jogador jogador_2;
	private Jogada jogada;

	public AtorJogador() {
		this.atorNetGames = new AtorNetGames(this);
	}

	public void iniciarPartida(Integer posicao) throws NaoJogandoException {
		this.conectado = true;
		String nome_p1 = this.atorNetGames.getProxy().obterNomeAdversario(1);
		String nome_p2 = this.atorNetGames.getProxy().obterNomeAdversario(2);
		if (posicao == 1) {
			this.jogador_1 = new Jogador(nome_p1, 1);
			this.jogador_2 = new Jogador(nome_p2, 2);
			this.ordemUsuario = 1;
		} else {
			this.jogador_1 = new Jogador(nome_p2, 2);
			this.jogador_2 = new Jogador(nome_p1, 1);
			this.ordemUsuario = 2;
		}
		this.tabuleiro = new Tabuleiro(jogador_1, jogador_2);
	}

	public void clickConectar(String ip, String nome)
			throws JahConectadoException, NaoPossivelConectarException,
			ArquivoMultiplayerException {
		this.atorNetGames.conectar(ip, nome);
	}

	public void clickAtacar(Monstro monstro, Posicao posicao)
			throws NaoJogandoException {
		monstro.setJa_atacou(true);
		monstro.getInvocador().diminuiEstrelas(monstro.estrelasParaAtaque());
		Monstro monstro_alvo = posicao.getOcupante();
		Monstro destruido = this.comparaAtaqueMonstros(monstro, monstro_alvo);

		Jogador invocador = destruido.getInvocador();
		destruido.destruirMonstro();
		if (invocador.compara(this.getId1())) {
			this.getId1().adicionarMonstroDestruido();
		} else {
			this.getId2().adicionarMonstroDestruido();
		}
		this.jogada = new Jogada(posicao.getLinha(), posicao.getLinha(),
				TipoJogada._atacar, monstro, monstro_alvo, null);

		this.enviarJogada(jogada);
		this.tabuleiro.avaliaContinuidade();
	}

	public Dado[] clickRolarDados() throws NaoJogandoException {
		Dado[] dados = null;
		if (this.getId1().getJaRolou()) {
			dados = this.getTabuleiro().getDados();
			for (int i = 0; i < dados.length; i++) {
				dados[i].rolaDado();
				this.getId1().adicionaEstrelas(dados[i].getFaceAtual());
			}
			this.jogada = new Jogada(0, 0, TipoJogada._rolar_dados, null, null,
					dados);
			this.atorNetGames.enviarJogada(jogada);
		}
		return dados;
	}

	public Monstro escolheMonstro() {
		return null;
	}

	public void clickCasa() {

	}

	public Monstro comparaAtaqueMonstros(Monstro escolha, Monstro monstroAlvo) {
		if (escolha.getAtaque() > monstroAlvo.getAtaque()) {
			return escolha;
		} else {
			return monstroAlvo;
		}
	}

	public Jogada getJogada() {
		return this.jogada;
	}

	public Jogador getVencedor() {
		return this.tabuleiro.identificaVencedor();
	}

	public Tabuleiro getTabuleiro() {
		return this.tabuleiro;
	}

	/*
	 * public boolean emAndamento() { }
	 */

	public void enviarJogada(Jogada jogada) throws NaoJogandoException {
		this.atorNetGames.enviarJogada(jogada);
	}

	public void receberJogada(Jogada jogada) {
		if (jogada.getTipoJogada() == null) {
			// this.jogador_2 = new Jogador(null, jogada.getLinha());
		}
	}

	public Jogador getId1() {
		if (jogador_1.getId() == 1) {
			return jogador_1;
		} else {
			return jogador_2;
		}
	}

	public Jogador getId2() {
		if (jogador_1.getId() == 2) {
			return jogador_1;
		} else {
			return jogador_2;
		}
	}

	public AtorNetGames getAtorNetGames() {
		return this.atorNetGames;
	}

	public void clickDarAVez() throws NaoJogandoException {
		TipoJogada tipo = this.jogador_1.darAVez();
		this.jogada = new Jogada(0, 0, tipo, null, null, null);
		this.atorNetGames.enviarJogada(jogada);
	}

	public void clickMoveMonstro(Monstro monstro, Posicao posicao) throws NaoJogandoException {
		monstro.setJaMoveu(true);
		this.jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada._moverMonstro, monstro, null, null);
		monstro.getInvocador().diminuiEstrelas(monstro.estrelasParaMovimento());
		this.atorNetGames.enviarJogada(jogada);
	}
}
