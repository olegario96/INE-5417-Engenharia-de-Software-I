package visao;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
import modelo.Monstro;
import modelo.Posicao;
import br.ufsc.inf.leobr.cliente.exception.ArquivoMultiplayerException;
import br.ufsc.inf.leobr.cliente.exception.JahConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoConectadoException;
import br.ufsc.inf.leobr.cliente.exception.NaoJogandoException;
import br.ufsc.inf.leobr.cliente.exception.NaoPossivelConectarException;
import controle.AtorJogador;

;

public class JanelaPrincipal {

	private JFrame frame;
	private JLabel lblJogador_1;
	private JLabel lblJogador_2;
	private JButton btnRolarDados;
	private JButton btnInvocarMonstro;
	private JButton btnMoverMonstro;
	private JButton btnAtacar;
	private JButton btnUsarHabilidade;
	private JButton btnEncerrarJogada;
	private JMenuBar menuBar;
	private JMenu mnJogo;
	private JMenuItem mntmConectar;
	private JMenuItem mntmDesconectar;
	private JMenuItem mntmIniciarPartida;
	private JPanel panel;
	private JLabel lblquadrante[][];
	private JLabel hearts_p1[];
	private JLabel hearts_p2[];

	private AtorJogador atorJogador;

	/**
	 * Create the application.
	 */
	public JanelaPrincipal() {
		this.atorJogador = new AtorJogador();
		initialize();
	}

	private void init_buttons_menus() {
		this.lblJogador_1 = new JLabel("Jogador 1");
		lblJogador_1.setBounds(56, 33, 70, 15);
		frame.getContentPane().add(lblJogador_1);

		this.lblJogador_2 = new JLabel("Jogador 2");
		lblJogador_2.setBounds(508, 33, 70, 15);
		frame.getContentPane().add(lblJogador_2);

		this.btnRolarDados = new JButton("Rolar Dados");
		btnRolarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRolarDados.setBounds(31, 173, 154, 25);
		frame.getContentPane().add(btnRolarDados);

		this.btnInvocarMonstro = new JButton("Invocar Monstro");
		btnInvocarMonstro.setBounds(31, 210, 154, 25);
		frame.getContentPane().add(btnInvocarMonstro);

		this.btnMoverMonstro = new JButton("Mover Monstro");
		btnMoverMonstro.setBounds(31, 247, 154, 25);
		frame.getContentPane().add(btnMoverMonstro);

		this.btnAtacar = new JButton("Atacar");

		btnAtacar.setBounds(31, 284, 154, 25);
		frame.getContentPane().add(btnAtacar);

		this.btnUsarHabilidade = new JButton("Usar Habilidade");
		btnUsarHabilidade.setBounds(31, 321, 154, 25);
		frame.getContentPane().add(btnUsarHabilidade);

		this.btnEncerrarJogada = new JButton("Encerrar Jogada");
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

		this.lblquadrante = new JLabel[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.lblquadrante[i][j] = new JLabel();
				if ((i + j) % 2 == 0) {
					this.lblquadrante[i][j].setBackground(Color.WHITE);
				} else {
					this.lblquadrante[i][j].setBackground(Color.BLACK);
				}
				this.lblquadrante[i][j].setOpaque(true);
				panel.add(this.lblquadrante[i][j]);
			}
		}

		this.hearts_p1 = new JLabel[3];
		this.hearts_p2 = new JLabel[3];
		int desloc_1 = 0;
		int desloc_2 = 400;
		String path = "/home/olegario/workspace/MonstrosDosDadosMasmorra/Resources/heartcontainer.gif";

		for (int i = 0; i < hearts_p1.length; i++) {
			hearts_p1[i] = new JLabel();
			hearts_p2[i] = new JLabel();

			hearts_p1[i].setIcon(new ImageIcon(path));
			hearts_p2[i].setIcon(new ImageIcon(path));

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
		this.btnRolarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickRolarDados();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.btnInvocarMonstro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickInvocarMonstro();
			}
		});

		this.btnMoverMonstro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickMoverMonstro();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.btnAtacar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					clickAtacar();
				} catch (NaoJogandoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.btnUsarHabilidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clickUsarHabilidade();
			}
		});

		this.btnEncerrarJogada.addActionListener(new ActionListener() {
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

		this.btnEncerrarJogada.addActionListener(new ActionListener() {
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
		this.setNomeLabel1(atorJogador.getAtorNetGames().getProxy()
				.obterNomeAdversario(1));
		this.setNomeLabel2(atorJogador.getAtorNetGames().getProxy()
				.obterNomeAdversario(2));
	}

	public void clickAtacar() throws NaoJogandoException {
		if (!(this.atorJogador.getId1().possuiMonstro())) {
			this.informaSemMonstro();
		} else {
			Monstro monstro = this.escolherMonstro();
			if (monstro.getJa_atacou()) {
				this.informaJaAtacou();
			} else {
				Posicao posicao = this.clickCasa();
				if (posicao.casaVazia()) {
					this.informaCasaOcupada();
				} else {
					if (this.atorJogador.getId1().getEstrelas() < monstro.estrelasParaAtaque()) {
						this.informaInsuficienciaEstrela();
					} else {
						this.atorJogador.clickAtacar(monstro, posicao);
						this.atualizarInformacoes();
					}
				}
			}
		}
	}

	public Monstro escolherMonstro() {
		return null;
	}

	/*public Posicao clickCasa() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.lblquadrante[i][j].addMouseListener(new MouseListener() {
					
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
						// TODO Auto-generated method stub
						
					}
				});
			}
		}
	}*/

	public void clickInvocarMonstro() {
		Monstro escolha = this.mostraMonstro();
		int estrelas = this.atorJogador.getId1().getEstrelas();
		if (estrelas < escolha.estrelasParaInvocacao()) {
			this.informaInsuficienciaEstrela();
		} else {
			Posicao posicao = clickCasa();
			
		}
	}

	public Monstro mostraMonstro() {
		return null;
	}

	public void clickMoverMonstro() throws NaoJogandoException {
		if (!(this.atorJogador.getId1().possuiMonstro())) {
			this.informaSemMonstro();
		} else {
			Monstro escolha = this.moveMonstro();
			if (escolha.getJaMoveu()) {
				this.informaJaAndou();
			} else {
				Posicao posicao = clickCasa();
				if (!posicao.casaVazia()) {
					this.informaCasaOcupada();
				} else {
					if (this.atorJogador.getId1().getEstrelas() < escolha.estrelasParaMovimento()) {
						this.informaInsuficienciaEstrela();
					} else {
						this.atorJogador.clickMoveMonstro(escolha, posicao);
						this.atualizarInformacoes();
					}
				}
			}
		}
	}

	public void clickDarAVez() throws NaoJogandoException {
		this.atorJogador.clickDarAVez();
		this.disabelButtons();
	}
	
	public void atualizarInformacoes() {
		
	}

	public void clickRolarDados() throws NaoJogandoException {
		Dado[] dados = this.atorJogador.clickRolarDados();
		if (dados == null) {
			JOptionPane.showMessageDialog(null,
					"Você já rolou os dados nesta rodada!");
		} else {
			JOptionPane.showMessageDialog(null,
					"As estrelas que você tirou foram: " + dados[0] + " "
							+ dados[1] + " " + dados[2]);
		}
	}

	public void clickUsarHabilidade() {

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

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public void setNomeLabel1(String name) {
		this.lblJogador_1.setText(name);
	}

	public void setNomeLabel2(String name) {
		this.lblJogador_2.setText(name);
	}

	public void disabelButtons() {
		this.btnRolarDados.setEnabled(false);
		this.btnInvocarMonstro.setEnabled(false);
		this.btnMoverMonstro.setEnabled(false);
		this.btnAtacar.setEnabled(false);
		this.btnUsarHabilidade.setEnabled(false);
		this.btnEncerrarJogada.setEnabled(false);
	}

	public void enableButtons() {
		this.btnRolarDados.setEnabled(true);
		this.btnInvocarMonstro.setEnabled(true);
		this.btnMoverMonstro.setEnabled(true);
		this.btnAtacar.setEnabled(true);
		this.btnUsarHabilidade.setEnabled(true);
		this.btnEncerrarJogada.setEnabled(true);
	}
	
	public boolean distanciaPermitia(Posicao fonte, Posicao destino) {
		int dif_linha = Math.abs(fonte.getLinha() - destino.getLinha());
		int dif_coluna = Math.abs(fonte.getColuna() - destino.getColuna());
		if (dif_linha > 1 || dif_coluna > 1) {
			return false;
		} else {
			return true;
		}
	}
}
