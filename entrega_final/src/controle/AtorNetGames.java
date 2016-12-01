package controle;

import br.ufsc.inf.leobr.cliente.Jogada;
import br.ufsc.inf.leobr.cliente.OuvidorProxy;
import br.ufsc.inf.leobr.cliente.Proxy;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;

public class AtorNetGames implements OuvidorProxy {

	private static final long serialVersionUID = 3325312239370218970L;

	private Proxy proxy;
	private AtorJogador atorJogador;

	public AtorNetGames(AtorJogador atorJogador) {
		super();
		this.atorJogador = atorJogador;
		this.proxy = Proxy.getInstance();
	}

	public void conectar(String ip, String nome) throws JahConectadoException,
			NaoPossivelConectarException, ArquivoMultiplayerException {
		this.proxy.conectar(ip, nome);
		this.proxy.addOuvinte(this);
	}
	
	public void desconectar() throws NaoConectadoException {
		proxy.desconectar();
	}
	
	public void iniciarPartida() throws NaoConectadoException {
		proxy.iniciarPartida(2);
	}

	@Override
	public void iniciarNovaPartida(Integer posicao) {
		try {
			this.atorJogador.iniciarPartida(posicao);
		} catch (NaoJogandoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void finalizarPartidaComErro(String message) {
	}

	@Override
	public void receberMensagem(String msg) {
	}

	@Override
	public void tratarConexaoPerdida() {
		System.out.println("Conexão perdida!");
		System.exit(1);
	}

	@Override
	public void tratarPartidaNaoIniciada(String message) {
		System.out.println("Erro: " + message);
		System.exit(1);
	}

	@Override
	public void receberJogada(Jogada jogada) {
		try {
			atorJogador.receberJogada((modelo.Jogada) jogada);
		} catch (NaoJogandoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void enviarJogada(modelo.Jogada jogada) throws NaoJogandoException {
		proxy.enviaJogada(jogada);
	}
	
	public Proxy getProxy() {
		return this.proxy;
	}

}
