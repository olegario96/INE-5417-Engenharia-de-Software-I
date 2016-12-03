package visao;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.QuadCurve2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import modelo.Dado;
import modelo.Jogador;
import modelo.Monstro;
import modelo.Monstro_Com_Habilidade;
import modelo.Posicao;
import modelo.TipoJogada;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import controle.AtorJogador;

;

public class JanelaPrincipal {

	private JFrame frame;
	private static JLabel lblJogador_1;
	private static JLabel lblJogador_2;
	private static JButton btnRolarDados;
	private static JButton btnInvocarMonstro;
	private static JButton btnMoverMonstro;
	private static JButton btnAtacar;
	private static JButton btnUsarHabilidade;
	private static JButton btnEncerrarJogada;
	private static JLabel lblNumeroEstrelas;
	private JMenuBar menuBar;
	private JMenu mnJogo;
	private JMenuItem mntmConectar;
	private JMenuItem mntmDesconectar;
	private JMenuItem mntmIniciarPartida;
	private JPanel panel;
	private static Casa lblquadrante[][];
	private static JLabel hearts_p1[];
	private static JLabel hearts_p2[];

	private static AtorJogador atorJogador;
	private int type;
	Monstro monstro = null;
	TipoJogada tipoJogada;

	/**
	 * Create the application.
	 */
	public JanelaPrincipal() {
		this.atorJogador = new AtorJogador();
		initialize();
	}

	private void init_buttons_menus() {
		lblJogador_1 = new JLabel("Jogador 1");
		lblJogador_1.setBounds(56, 33, 70, 15);
		frame.getContentPane().add(lblJogador_1);

		lblJogador_2 = new JLabel("Jogador 2");
		lblJogador_2.setBounds(508, 33, 70, 15);
		frame.getContentPane().add(lblJogador_2);

		btnRolarDados = new JButton("Rolar Dados");
		btnRolarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRolarDados.setBounds(31, 173, 154, 25);
		frame.getContentPane().add(btnRolarDados);

		btnInvocarMonstro = new JButton("Invocar Monstro");
		btnInvocarMonstro.setBounds(31, 210, 154, 25);
		frame.getContentPane().add(btnInvocarMonstro);

		btnMoverMonstro = new JButton("Mover Monstro");
		btnMoverMonstro.setBounds(31, 247, 154, 25);
		frame.getContentPane().add(btnMoverMonstro);

		btnAtacar = new JButton("Atacar");

		btnAtacar.setBounds(31, 284, 154, 25);
		frame.getContentPane().add(btnAtacar);

		btnUsarHabilidade = new JButton("Usar Habilidade");
		btnUsarHabilidade.setBounds(31, 321, 154, 25);
		frame.getContentPane().add(btnUsarHabilidade);

		btnEncerrarJogada = new JButton("Encerrar Jogada");
		btnEncerrarJogada.setBounds(31, 358, 154, 25);
		frame.getContentPane().add(btnEncerrarJogada);

		this.menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 620, 21);
		frame.getContentPane().add(menuBar);

		this.mnJogo = new JMenu("Jogo");
		menuBar.add(mnJogo);

		this.mntmConectar = new JMenuItem("Conectar");
		mnJogo.add(mntmConectar);

		this.mntmDesconectar = new JMenuItem("Desconectar");
		mnJogo.add(mntmDesconectar);

		this.mntmIniciarPartida = new JMenuItem("Iniciar partida");
		mnJogo.add(mntmIniciarPartida);
		
		this.lblNumeroEstrelas = new JLabel("Numero estrelas: 0");
		lblNumeroEstrelas.setBounds(26, 127, 159, 34);
		frame.getContentPane().add(lblNumeroEstrelas);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		this.panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		panel.setBounds(225, 127, 425, 333);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(8, 8, 0, 0));

		init_buttons_menus();

		this.lblquadrante = new Casa[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.lblquadrante[i][j] = new Casa(i, j);
				if ((i + j) % 2 == 0) {
					this.lblquadrante[i][j].setBackground(Color.WHITE);
				} else {
					this.lblquadrante[i][j].setBackground(Color.BLACK);
				}
				this.lblquadrante[i][j].setOpaque(true);
				panel.add(this.lblquadrante[i][j]);
				lblquadrante[i][j].addMouseListener(meulistener);
			}
		}

		this.hearts_p1 = new JLabel[3];
		this.hearts_p2 = new JLabel[3];
		int desloc_1 = 0;
		int desloc_2 = 400;

		for (int i = 0; i < hearts_p1.length; i++) {
			hearts_p1[i] = new JLabel();
			hearts_p2[i] = new JLabel();

			hearts_p1[i].setIcon(new ImageIcon("./Resources/heartcontainer.gif"));
			hearts_p2[i].setIcon(new ImageIcon("./Resources/heartcontainer.gif"));

			hearts_p1[i].setBounds(24 + desloc_1, 60, 64, 60);
			hearts_p2[i].setBounds(24 + desloc_2, 60, 64, 60);
			desloc_1 += 60;
			desloc_2 += 60;

			frame.getContentPane().add(hearts_p1[i]);
			frame.getContentPane().add(hearts_p2[i]);
		}

		this.config_buttons();
		frame.setTitle("Monstros dos Dados Masmorra");
	}

	private void config_buttons() {
		btnRolarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickRolarDados();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnInvocarMonstro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickInvocarMonstro();
			}
		});

		btnMoverMonstro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickMoverMonstro();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnAtacar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickAtacar();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnUsarHabilidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickUsarHabilidade();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnEncerrarJogada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickDarAVez();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.mntmConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickConectar();
			}
		});

		this.mntmDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickDesconectar();
				} catch (NaoConectadoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.mntmIniciarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iniciarPartida();
				} catch (NaoConectadoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnEncerrarJogada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
	}

	public void clickConectar() {
		String nome = registraNome();
		String ip = registraIP();
		try {
			atorJogador.clickConectar(ip, nome);
		} catch (JahConectadoException | NaoPossivelConectarException
				| ArquivoMultiplayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickDesconectar() throws NaoConectadoException {
		int op = JOptionPane.showConfirmDialog(null,
				"Você quer realmente sair?");
		if (op == 0) {
			this.atorJogador.getAtorNetGames().desconectar();
			System.exit(0);
		}
	}

	public void iniciarPartida() throws NaoConectadoException {
		this.atorJogador.getAtorNetGames().iniciarPartida();
		setNomeLabel1(atorJogador.getAtorNetGames().getProxy()
				.obterNomeAdversario(1));
		setNomeLabel2(atorJogador.getAtorNetGames().getProxy()
				.obterNomeAdversario(2));
	}

	public void clickAtacar() throws NaoJogandoException {
		if (!(this.atorJogador.getJogador1().possuiMonstro())) {
			this.informaSemMonstro();
		} else {
			type = 2;
			JOptionPane.showMessageDialog(null,
					"Clique no monstro com que você quer atacar");

		}
	}

	public void clickMoverMonstro() throws NaoJogandoException {
		if (!(this.atorJogador.getJogador1().possuiMonstro())) {
			this.informaSemMonstro();
		} else {
			type = 1;
			JOptionPane.showMessageDialog(null,
					"Clique na casa que possui o monstro que você quer mover.");
		}
	}

	public void clickInvocarMonstro() {
		new JanelaEscolha(this);
	}
	
	public void setMonstro(Monstro monstro) {
		this.monstro = monstro;
	}

	public void clickInvMonstro() {
		int estrelas = this.atorJogador.getJogador1().getEstrelas();
		if (estrelas < monstro.estrelasParaInvocacao()) {
			this.informaInsuficienciaEstrela();
		} else {
			JOptionPane.showMessageDialog(null,
					"Agora a escolha a casa que você quer invocar o monstro!");
			tipoJogada = TipoJogada._invocarMonstro;
		}
	}

	public Monstro mostraMonstro() {
		return null;
	}

	public Monstro escolherMonstro() {
		return null;
	}

	public Monstro_Com_Habilidade escolherMonstroHabilidade() {
		return null;
	}

	public void clickDarAVez() throws NaoJogandoException {
		this.atorJogador.clickDarAVez();
		disableButtons();
	}

	public static void atualizarInformacoes() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Posicao posicao = atorJogador.getTabuleiro().getPosicao(i, j);
				if (!posicao.casaVazia()) {
					String text = Integer.toString(posicao.getOcupante()
							.getInvocador().getId());
					text = "\u001B[31m" + text + "\u001B[0m";
					lblquadrante[i][j].setText(text);
				} else {
					lblquadrante[i][j].setText("");
				}
			}
		}
		
		String text = "Numero estrelas: " +  Integer.toString((atorJogador.getJogador1().getEstrelas()));
		lblNumeroEstrelas.setText(text);
		
		atualizaPontosDeVida();
		avaliaVencedor();
		
	}
	
	public static void atualizaPontosDeVida() {
		for (int i = 0; i < 3; i++) {
			if (i < atorJogador.getJogador1().getPontosDeVida())
				hearts_p1[i].setIcon(new ImageIcon("./Resources/heartcontainer.gif"));
			else
				hearts_p1[i].setIcon(new ImageIcon("./Resources/sem_vida.gif"));
			
			
			if (i < atorJogador.getJogador2().getPontosDeVida())
				hearts_p2[i].setIcon(new ImageIcon("./Resources/heartcontainer.gif"));
			else
				hearts_p2[i].setIcon(new ImageIcon("./Resources/sem_vida.gif"));
		}
	}
	
	public static void avaliaVencedor() {
		if (atorJogador.getJogador1().getPontosDeVida() == 0)
			informaVencedor(atorJogador.getJogador2().getNome());
		else if (atorJogador.getJogador2().getPontosDeVida() == 0)
			informaVencedor(atorJogador.getJogador1().getNome());
	}

	public void clickRolarDados() throws NaoJogandoException {
		Dado[] dados = atorJogador.clickRolarDados();
		if (dados == null) {
			JOptionPane.showMessageDialog(null,
					"Você já rolou os dados nesta rodada!");
		} else {
			JOptionPane.showMessageDialog(
					null,
					"As estrelas que você tirou foram: "
							+ dados[0].getFaceAtual() + " "
							+ dados[1].getFaceAtual() + " "
							+ dados[2].getFaceAtual());
			atualizarInformacoes();
		}
	}

	public void clickUsarHabilidade() throws NaoJogandoException {
		if (this.atorJogador.getJogador1().habilidades().size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Você não possui monstro com habilidades");
		} else {
			JOptionPane.showMessageDialog(null,
					"Escolha o monstro com habilidade que você quer usar");
			type = 3;
		}
	}

	public String registraNome() {
		return JOptionPane.showInputDialog("Digite o seu nome: ");
	}

	public String registraIP() {
		return JOptionPane.showInputDialog("Insira o endereço do server: ");
	}

	public void informaConexao(boolean conectado) {

	}

	public void novaTentativa() {

	}

	public void setSair(boolean sair) {

	}

	public void informaSemMonstro() {
		JOptionPane.showMessageDialog(null, "Você não possui monstros!");
	}

	public void informaUsouHabilidade(Monstro_Com_Habilidade monstro) {
		TipoJogada tipo = ((Monstro_Com_Habilidade) monstro).getHabilidade();
		switch (tipo) {
		case _atacar:
			JOptionPane.showMessageDialog(null,
					"Agora este monstro pode atacar mais uma vez!");
			break;
		case _moverMonstro:
			JOptionPane.showMessageDialog(null,
					"Agora este monstro pode se mover mais uma vez!");
			break;
		default:
			JOptionPane.showMessageDialog(null,
					"Agora você pode rolar os dados mais uma vez!");
			break;
		}
	}

	public Monstro moveMonstro() {
		return null;
	}

	public void informaJaAtacou() {
		JOptionPane.showMessageDialog(null, "Este monstro já atacou!");
	}

	public void informaCasaOcupada() {
		JOptionPane.showMessageDialog(null, "Esta casa está ocupada!");
	}

	public void informaJaAndou() {
		JOptionPane.showMessageDialog(null, "Este monstro já andou!");
	}

	public void informaInsuficienciaEstrela() {
		JOptionPane.showMessageDialog(null, "Você não possui estrelas o "
				+ "suficiente para realizar esta jogada!");
	}

	public void informaJaUsouHabilidade() {
		JOptionPane.showMessageDialog(null, "Esse monstro já usou habilidade!");
	}
	
	public static void informaVencedor(String nome) {
		JOptionPane.showMessageDialog(null, "O jogador vencedor foi o "
										+ nome + ". Parabéns!");
		atorJogador.encerraPartida();
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public static void setNomeLabel1(String name) {
		lblJogador_1.setText(name);
	}

	public static void setNomeLabel2(String name) {
		lblJogador_2.setText(name);
	}

	public static void disableButtons() {
		btnRolarDados.setEnabled(false);
		btnInvocarMonstro.setEnabled(false);
		btnMoverMonstro.setEnabled(false);
		btnAtacar.setEnabled(false);
		btnUsarHabilidade.setEnabled(false);
		btnEncerrarJogada.setEnabled(false);
	}

	public static void enableButtons() {
		btnRolarDados.setEnabled(true);
		btnInvocarMonstro.setEnabled(true);
		btnMoverMonstro.setEnabled(true);
		btnAtacar.setEnabled(true);
		btnUsarHabilidade.setEnabled(true);
		btnEncerrarJogada.setEnabled(true);
	}

	public boolean distanciaPermitida(Posicao fonte, Posicao destino) {
		int dif_linha = Math.abs(fonte.getLinha() - destino.getLinha());
		int dif_coluna = Math.abs(fonte.getColuna() - destino.getColuna());
		if (dif_linha > 1 || dif_coluna > 1) {
			return false;
		} else {
			return true;
		}
	}

	private MouseListener meulistener = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int linha = ((Casa) e.getSource()).getLinha();
			int coluna = ((Casa) e.getSource()).getColuna();
			Posicao posicao = atorJogador.getTabuleiro().getPosicao(linha, coluna);
			switch (tipoJogada) {
			case _atacar:
				aoAtacar(posicao);
				break;

			case _moverMonstro:
				aoMover(posicao);
				break;

			case _invocarMonstro:
				aoInvocar(posicao);
				break;

			default:
				if (type == 1) {
					aoClicar_mover(posicao);
				} else if (type == 2) {
					aoClicar_atacar(posicao);
					
				} else if (type == 3) {
					try {
						aoClicar_usarHabilidade(posicao);
					} catch (NaoJogandoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			}
			type = 0;
		}
	};

	private void aoAtacar(Posicao posicao) {
		if (posicao.casaVazia()) {
			JOptionPane.showMessageDialog(null,
					"Esta casa não possui nenhum monstro.");
		} else {
			if (distanciaPermitida(monstro.getPosicao(), posicao)) {
				try {
					atorJogador.clickAtacar(monstro, posicao);
				} catch (NaoJogandoException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"O alvo está muito longe do monstro!");
			}
		}
		tipoJogada = TipoJogada._clickMonstro;
	}

	private void aoMover(Posicao posicao) {
		if (!posicao.casaVazia()) {
			this.informaCasaOcupada();
		} else {
			if (distanciaPermitida(monstro.getPosicao(), posicao)) {
				try {
					atorJogador.clickMoveMonstro(monstro, posicao);
				} catch (NaoJogandoException e1) {
					e1.printStackTrace();
				}
				atualizarInformacoes();
			} else {
				JOptionPane.showMessageDialog(null,
						"O alvo está muito longe do monstro!");
			}
		}
		tipoJogada = TipoJogada._clickMonstro;

	}

	private void aoInvocar(Posicao posicao) {
		if (!posicao.casaVazia()) {
			this.informaCasaOcupada();
		} else {
			try {
				atorJogador.clickInvocarMonstro(monstro, posicao);
			} catch (NaoJogandoException e1) {
				e1.printStackTrace();
			}
			atualizarInformacoes();
		}
		tipoJogada = TipoJogada._clickMonstro;
	}

	private void aoClicar_mover(Posicao posicao) {
		if (posicao.casaVazia()) {
			JOptionPane.showMessageDialog(null, "Esta casa está vazia!");
		} else {
			if (!(posicao.getOcupante().getInvocador().compara(this.atorJogador
					.getJogador1()))) {
				JOptionPane.showMessageDialog(null,
						"Este mostro não pertence a você!");
			} else {
				monstro = posicao.getOcupante();
				if (monstro.getJaMoveu()) {
					this.informaJaAndou();
				} else {
					if (this.atorJogador.getJogador1().getEstrelas() < monstro
							.estrelasParaMovimento()) {
						this.informaInsuficienciaEstrela();
					} else {
						JOptionPane.showMessageDialog(null,
								"Clique na casa que você deseja mover!");
						tipoJogada = TipoJogada._moverMonstro;
					}
				}
			}
		}
	}

	private void aoClicar_atacar(Posicao posicao) {
		if (posicao.casaVazia()) {
			JOptionPane.showMessageDialog(null, "Esta casa está vazia!");
		} else {
			if (!(posicao.getOcupante().getInvocador().compara(this.atorJogador
					.getJogador1()))) {
				JOptionPane.showMessageDialog(null,
						"Este monstro não pertence a você!");
			} else {
				monstro = posicao.getOcupante();
				if (monstro.getJa_atacou()) {
					this.informaJaAtacou();
				} else {
					if (this.atorJogador.getJogador1().getEstrelas() < monstro
							.estrelasParaAtaque()) {
						this.informaInsuficienciaEstrela();
					} else {
						JOptionPane.showMessageDialog(null,
								"Clique na casa que você deseja atacar!");
						tipoJogada = TipoJogada._atacar;
					}
				}
			}
		}
	}

	private void aoClicar_usarHabilidade(Posicao posicao)
			throws NaoJogandoException {
		if (posicao.casaVazia()) {
			JOptionPane.showMessageDialog(null, "Esta casa está vazia!");
		} else {
			if (!(posicao.getOcupante().getInvocador().compara(this.atorJogador
					.getJogador1()))) {
				JOptionPane.showMessageDialog(null,
						"Este monstro não pertence a você!");
			} else {
				monstro = posicao.getOcupante();
				if (((Monstro_Com_Habilidade) this.monstro)
						.getJaUsouHabilidade()) {
					informaJaUsouHabilidade();
				} else {
					this.atorJogador
							.clickUsarHabilidade((Monstro_Com_Habilidade) monstro);
					informaUsouHabilidade((Monstro_Com_Habilidade) monstro);
				}
			}
		}
	}
}
