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

		if (posicao == 1) {
			this.jogador_1 = new Jogador(nome_p1, 1);
			this.jogador_2 = new Jogador(nome_p2, 2);
			this.ordemUsuario = 1;
			this.jogador_1.setSeu_turno(true);
			this.jogador_2.setSeu_turno(false);
		} else {
			this.ordemUsuario = 2;
			this.jogador_1 = new Jogador(nome_p1, 2);
			this.jogador_2 = new Jogador(nome_p2, 1);
			this.jogador_1.setSeu_turno(false);
			this.jogador_2.setSeu_turno(true);
			this.receberSolicitacaoDeInicio();
		}

		this.tabuleiro = new Tabuleiro(jogador_1, jogador_2);
		JOptionPane.showMessageDialog(null, "A partida começou!");
	}

	public void receberSolicitacaoDeInicio() {
		JanelaPrincipal.setNomeLabel1(this.atorNetGames.getProxy()
				.obterNomeAdversario(2));

		JanelaPrincipal.setNomeLabel2(this.atorNetGames.getProxy()
				.obterNomeAdversario(1));
		if (this.ordemUsuario == 2) {
			JanelaPrincipal.disableButtons();
		}

	}

	public void encerraPartida() {
		this.tabuleiro.getJogador1().desabilitaJogador();
		this.tabuleiro.getJogador2().desabilitaJogador();
		this.tabuleiro.setPartidaEmAndamento(false);
	}

	public void mostrarVencedor(String vencedor) {
		JanelaPrincipal.informaVencedor(vencedor);
	}

	public void clickConectar(String ip, String nome)
			throws JahConectadoException, NaoPossivelConectarException,
			ArquivoMultiplayerException {
		this.atorNetGames.conectar(ip, nome);
	}
	
	public Dado[] clickRolarDados() throws NaoJogandoException {
		Dado[] dados = null;
		if (!(this.getJogador1().getJaRolou())) {
			dados = this.getTabuleiro().rolarDados();
			for (int i = 0; i < dados.length; i++) {
				dados[i].rolaDado();
				this.getJogador1().adicionaEstrelas(dados[i].getFaceAtual());
			}
			this.jogada = new Jogada(0, 0, TipoJogada.ROLAR_DADOS, null, null,
					dados);
			this.atorNetGames.enviarJogada(jogada);
		}
		this.jogador_1.setDados(true);
		return dados;
	}

	public void clickInvocarMonstro(Monstro monstro, Posicao posicao)
			throws NaoJogandoException {
		this.jogada = this.tabuleiro.invocaMonstro(monstro, posicao, jogador_1);
		this.enviarJogada(jogada);
	}

	public void clickMoveMonstro(Monstro monstro, Posicao posicao)
			throws NaoJogandoException {
		this.jogada = this.tabuleiro.movimentaMonstro(monstro, posicao);
		this.enviarJogada(jogada);
	}

	public void clickAtacar(Monstro monstro, Posicao posicao)
			throws NaoJogandoException {

		Monstro destruido = this.tabuleiro.atacarMonstro(monstro,
				jogada.getMonstroAlvo(), posicao);
		if (destruido == null) {
			JOptionPane.showMessageDialog(null,
					"Os dois monstros possuem o mesmo poder\n"
							+ "de ataque. Nenhum monstro foi destruído.");
		} else {
			tabuleiro.destroiMonstro(destruido, destruido.getPosicao());
		}

		this.jogada = new Jogada(posicao.getLinha(), posicao.getColuna(),
				TipoJogada.ATACAR, monstro, jogada.getMonstroAlvo(), null);

		this.enviarJogada(jogada);
		JanelaPrincipal.atualizarInformacoes();
	}

	public void clickUsarHabilidade(Monstro_Com_Habilidade monstro)
			throws NaoJogandoException {
		this.jogada = this.tabuleiro.usarHabilidade(monstro);
		this.enviarJogada(jogada);
	}

	public void clickDarAVez() throws NaoJogandoException {
		TipoJogada tipo = this.tabuleiro.mudaJogador();
		this.jogada = new Jogada(0, 0, tipo, null, null, null);
		this.enviarJogada(jogada);
	}


	public void enviarJogada(Jogada jogada) throws NaoJogandoException {
		this.atorNetGames.enviarJogada(jogada);
	}
	
	public void receberJogada(Jogada jogada) throws NaoJogandoException {
		this.tabuleiro.reproduzirJogada(jogada, this.jogador_2);
		switch (jogada.getTipoJogada()) {
		case DAR_VEZ:
			JOptionPane.showMessageDialog(null, "Sua vez!");
			break;

		case USAR_HABILIDADE:
			JOptionPane.showMessageDialog(null, "Oponente usou habilidade!");
			break;

		case ROLAR_DADOS:
			Dado[] dados = jogada.getDados();
			JOptionPane.showMessageDialog(
					null,
					"Seu adversário rolou os dado! Ele tirou "
							+ dados[0].getFaceAtual() + " "
							+ dados[1].getFaceAtual() + " "
							+ dados[2].getFaceAtual());
			
		default:
			break;
		}
	}
	
	public int getOrdemUsuario() {
		return this.ordemUsuario;
	}
	
	public AtorNetGames getAtorNetGames() {
		return this.atorNetGames;
	}

	public Tabuleiro getTabuleiro() {
		return this.tabuleiro;
	}

	public Jogador getJogador1() {
		return this.jogador_1;
	}

	public Jogador getJogador2() {
		return this.jogador_2;
	}

	public Jogada getJogada() {
		return this.jogada;
	}
}
