package visao;

import modelo.*;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

public class JanelaEscolha {

	private JFrame frame;
	private Monstro monstro;

	/**
	 * Create the application.
	 */
	public JanelaEscolha() {
		initialize();
		this.monstro = null;
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 579, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(3, 2, 0, 0));
		
		JButton abakiButton = new JButton("");
		abakiButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro(1, null, 3, 2, 1, null, 0, 1);
				frame.dispose();
			}
		});
		abakiButton.setIcon(new ImageIcon("/home/olegario/workspace/MonstrosDosDadosMasmorra/Resources/abaki.png"));
		frame.getContentPane().add(abakiButton);
		
		JButton agidoButton = new JButton("");
		agidoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro(2, null, 5, 2, 2, null, 0, 2);
				frame.dispose();
			}
		});
		agidoButton.setIcon(new ImageIcon("/home/olegario/workspace/MonstrosDosDadosMasmorra/Resources/agido.png"));
		frame.getContentPane().add(agidoButton);
		
		JButton airEaterButton = new JButton("");
		airEaterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro_Com_Habilidade(2, null, 5, 2, 2, null, 0, 3, TipoJogada._moverMonstro);
				frame.dispose();
			}
		});
		airEaterButton.setIcon(new ImageIcon("/home/olegario/workspace/MonstrosDosDadosMasmorra/Resources/air_eater.png"));
		frame.getContentPane().add(airEaterButton);
		
		JButton airorcaButton = new JButton("");
		airorcaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro(2, null, 2, 3, 1, null, 0, 4);
				frame.dispose();
			}
		});
		airorcaButton.setIcon(new ImageIcon("/home/olegario/workspace/MonstrosDosDadosMasmorra/Resources/airorca.png"));
		frame.getContentPane().add(airorcaButton);
		
		JButton the13thGraveButton = new JButton("");
		the13thGraveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monstro = new Monstro_Com_Habilidade(3, null, 6, 2, 3, null, 0, 5, TipoJogada._rolar_dados);
				frame.dispose();
			}
		});
		
		the13thGraveButton.setIcon(new ImageIcon("/home/olegario/workspace/MonstrosDosDadosMasmorra/Resources/the_13th_grave.png"));
		frame.getContentPane().add(the13thGraveButton);
		
		JButton whiteDragonButton = new JButton("");
		whiteDragonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aoClicar(6);
				monstro = new Monstro_Com_Habilidade(3, null, 7, 2, 3, null, 0, 6, TipoJogada._atacar);
				frame.dispose();
			}
		});
		
		whiteDragonButton.setIcon(new ImageIcon("/home/olegario/workspace/MonstrosDosDadosMasmorra/Resources/white_dragon.png"));
		frame.getContentPane().add(whiteDragonButton);
		while (monstro == null);
	}
	
	public 

}
