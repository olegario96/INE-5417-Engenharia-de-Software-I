package visao;

import javax.swing.JLabel;

public class Casa extends JLabel {
	int linha;
	int coluna;
	
	public Casa(int linha, int coluna) {
		super();
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public int getLinha() {
		return this.linha;
	}
	
	public int getColuna() {
		return this.coluna;
	}
}
