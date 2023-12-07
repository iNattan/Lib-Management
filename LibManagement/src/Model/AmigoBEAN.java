package Model;

public class AmigoBEAN {
	private int idAmigos;
	private String nome;
	private String documento;
	private int situacao;
	
	public AmigoBEAN(int idAmigos, String nome, String documento, int situacao) {
		super();
		this.idAmigos = idAmigos;
		this.nome = nome;
		this.documento = documento;
		this.situacao = situacao;
	}
	
	public int getIdAmigos() {
		return idAmigos;
	}
	public void setIdAmigos(int idAmigos) {
		this.idAmigos = idAmigos;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public int getSituacao() {
		return situacao;
	}
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
}
