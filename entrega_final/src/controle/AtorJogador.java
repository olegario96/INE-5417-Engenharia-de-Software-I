package controle;

import javax.swing.JOptionPane;

import visao.JanelaPrincipal;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import modelo.*;

public class AtorJogador {
	private int ordemUsuario;
	private AtorNetGames atorNetGames;
	private Tabuleiro tabuleiro;
	private Jogador jogador_1;
	private Jogador jogador_2;
	private Jogada jogada;
	
	public AtorJogador() {
		this.atorNetGames = new AtorNetGames(this);
	}

	public void iniciarPartida(Integer posicao) throws NaoJogandoException {
		String nome_p1 = this.atorNetGames.getProxy().obterNomeAdversario(1);
		String nome_p2 = this.atorNetGames.getProxy().obterNomeAdversario(2);

		this.jogador_1 = new Jogador(nome_p1, 1);
		this.jogador_2 = new Jogador(nome_p2, 2);
		if (posicao == 1) {
			ordemUsuario = 1;
			this.jogador_1.setSeu_turno(true);
		} else {
			ordemUsuario = 2;
			this.jogador_1.setSeu_turno(false);
			this.receberSolicitacaoDeInicio();
		}
		this.jogador_2.setSeu_turno(false);
		this.tabuleiro = new Tabuleiro(jogador_1, jogador_2);
	}

	public void receberSolicitacaoDeInicio() {
		JanelaPrincipal.setNomeLabel1(this.atorNetGames.getProxy()
				.obterNomeAdversario(1));

		JanelaPrincipal.setNomeLabel2(this.atorNetGames.getProxy()
				.obterNomeAdversario(2));
		if (ordemUsuario == 2) {
			JanelaPrincipal.disableButtons();
		}
		JOptionPane.showMessageDialog(null, "A partida começou!");
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

		destruido.destruirMonstro();
		
		this.jogada = new Jogada(posicao.getLinha(), posicao.getLinha(),
				TipoJogada._atacar, monstro, monstro_alvo, null);

		this.enviarJogada(jogada);
		this.tabuleiro.avaliaContinuidade();
	}

	public Dado[] clickRolarDados() throws NaoJogandoException {
		Dado[] dados = null;
		if (!(this.getJogador1().getJaRolou())) {
			dados = this.getTabuleiro().getDados();
			for (int i = 0; i < dados.length; i++) {
				dados[i].rolaDado();
				this.getJogador1().adicionaEstrelas(dados[i].getFaceAtual());
			}
			this.jogada = new Jogada(0, 0, TipoJogada._rolar_dados, null, null,
					dados);
			this.atorNetGames.enviarJogada(jogada);
		}
		this.jogador_1.setDados(true);
		return dados;
	}

	public void clickUsarHabilidade(Monstro_Com_Habilidade monstro)
			throws NaoJogandoException {
		switch (monstro.getHabilidade()) {
		case _atacar:
			monstro.setJa_atacou(false);
			break;
		case _moverMonstro:
			monstro.setJaMoveu(false);
			break;
		default:
			monstro.getInvocador().setDados(false);
		}
		monstro.setJaUsouHabilidade(true);
		jogada = new Jogada(0, 0, TipoJogada._usarHabilidade, monstro, null,
				null);
		this.atorNetGames.enviarJogada(jogada);
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

	public void receberJogada(Jogada jogada) throws NaoJogandoException {
		Monstro monstro_fonte = jogada.getMonstroFonte();
		Posicao posicao;
		switch (jogada.getTipoJogada()) {
		case _atacar:
			posicao = new Posicao(jogada.getMonstroAlvo(), jogada.getLinha(),
					jogada.getColuna());
			this.clickAtacar(monstro_fonte, posicao);
			JanelaPrincipal.atualizarInformacoes();
			break;

		case _moverMonstro:
			posicao = this.tabuleiro.getPosicao(jogada.getLinha(), jogada.getColuna());
			monstro_fonte.setJaMoveu(true);
			this.tabuleiro.movimentaMonstro(monstro_fonte, posicao);
			JanelaPrincipal.atualizarInformacoes();
			break;

		case _usarHabilidade:
			((Monstro_Com_Habilidade) monstro_fonte).getHabilidade();
			JOptionPane.showMessageDialog(null, "Oponente usou habilidade!");
			break;

		case _invocarMonstro:
			posicao = new Posicao(null, jogada.getLinha(), jogada.getColuna());
			this.tabuleiro.invocaMonstro(monstro_fonte, posicao);
			monstro_fonte.setInvocador(jogador_2);
			monstro_fonte.getInvocador().adicionaMonstro(monstro_fonte);
			

			monstro_fonte.getInvocador().diminuiEstrelas(monstro_fonte.estrelasParaInvocacao());
			JanelaPrincipal.atualizarInformacoes();
			break;

		case _darVez:
			this.getJogador1().setSeu_turno(true);
			this.jogador_2.setSeu_turno(false);
			JanelaPrincipal.enableButtons();
			JOptionPane.showMessageDialog(null, "Sua vez!");
			break;

		default:
			break;
		}

	}

	public Jogador getJogador1() {
		return this.jogador_1;
	}

	public Jogador getJogador2() {
		return this.jogador_2;
	}

	public AtorNetGames getAtorNetGames() {
		return this.atorNetGames;
	}

	public void clickDarAVez() throws NaoJogandoException {
		TipoJogada tipo = this.jogador_1.darAVez();
		this.jogada = new Jogada(0, 0, tipo, null, null, null);
		this.atorNetGames.enviarJogada(jogada);
	}

	public void clickMoveMonstro(Monstro monstro, Posicao posicao)
			throws NaoJogandoException {
		monstro.setJaMoveu(true);
		this.jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada._moverMonstro, monstro, null, null);
		this.atorNetGames.enviarJogada(jogada);
		this.tabuleiro.movimentaMonstro(monstro, posicao);
		monstro.getInvocador().diminuiEstrelas(monstro.estrelasParaMovimento());
		
	}

	public void clickInvocarMonstro(Monstro monstro, Posicao posicao)
			throws NaoJogandoException {
		
		posicao.setOcupante(monstro);
		monstro.setPosicao(posicao);
		monstro.setInvocador(jogador_1);
		monstro.getInvocador().adicionaMonstro(monstro);
		this.jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada._invocarMonstro, monstro, null, null);

		monstro.getInvocador().diminuiEstrelas(monstro.estrelasParaInvocacao());
		this.atorNetGames.enviarJogada(jogada);
	}

	public int getOrdemUsuario() {
		return this.ordemUsuario;
	}
}
