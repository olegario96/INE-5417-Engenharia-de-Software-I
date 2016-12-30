package visao;

import javax.swing.JLabel;

public class Casa extends JLabel {
	
	private static final long serialVersionUID = 887379446407433088L;
	
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
