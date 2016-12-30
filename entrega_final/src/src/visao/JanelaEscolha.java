package visao;

import modelo.Monstro;
import modelo.Monstro_Com_Habilidade;
import modelo.TipoJogada;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JanelaEscolha {

	private JFrame frame;
	private Monstro monstro;
	private JanelaPrincipal janela;

	public JanelaEscolha(JanelaPrincipal janela) {
		this.monstro = null;
		initialize();
		this.frame.setVisible(true);
		this.janela = janela;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 579, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(3, 2, 0, 0));
		
		JButton abakiButton = new JButton("");
		abakiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro(1, null, 3, 2, 1, null, 1);
				
				frame.dispose();
				janela.setMonstro(monstro);
				janela.clickInvMonstro();
			}
		});
		abakiButton.setIcon(new ImageIcon("./Resources/abaki.png"));
		frame.getContentPane().add(abakiButton);
		
		JButton agidoButton = new JButton("");
		agidoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro(2, null, 5, 2, 2, null, 2);
				frame.dispose();
				janela.setMonstro(monstro);
				janela.clickInvMonstro();
			}
		});
		agidoButton.setIcon(new ImageIcon("./Resources/agido.png"));
		frame.getContentPane().add(agidoButton);
		
		JButton airEaterButton = new JButton("");
		airEaterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro_Com_Habilidade(2, null, 5, 2, 2, null, 3, TipoJogada.MOVER_MONSTRO);
				frame.dispose();
				janela.setMonstro(monstro);
				janela.clickInvMonstro();
			}
		});
		airEaterButton.setIcon(new ImageIcon("./Resources/air_eater.png"));
		frame.getContentPane().add(airEaterButton);
		
		JButton airorcaButton = new JButton("");
		airorcaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro(1, null, 3, 2, 1, null, 4);
				frame.dispose();
				janela.setMonstro(monstro);
				janela.clickInvMonstro();
			}
		});
		airorcaButton.setIcon(new ImageIcon("./Resources/airorca.png"));
		frame.getContentPane().add(airorcaButton);
		
		JButton the13thGraveButton = new JButton("");
		the13thGraveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro_Com_Habilidade(3, null, 6, 2, 3, null, 5, TipoJogada.ROLAR_DADOS);
				frame.dispose();
				janela.setMonstro(monstro);
				janela.clickInvMonstro();
			}
		});
		
		the13thGraveButton.setIcon(new ImageIcon("./Resources/the_13th_grave.png"));
		frame.getContentPane().add(the13thGraveButton);
		
		JButton whiteDragonButton = new JButton("");
		whiteDragonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				monstro = new Monstro_Com_Habilidade(3, null, 7, 2, 3, null, 6, TipoJogada.ATACAR);
				frame.dispose();
				janela.setMonstro(monstro);
				janela.clickInvMonstro();
			}
		});
		
		whiteDragonButton.setIcon(new ImageIcon("./Resources/white_dragon.png"));
		frame.getContentPane().add(whiteDragonButton);
		
	}
	
	public Monstro getMonstro() {
		return this.monstro;
	}
}
