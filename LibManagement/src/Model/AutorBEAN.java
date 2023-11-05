package Model;

public class AutorBEAN {
	private int idAutores;
	private String nome;
	private String documento;
	private int situacao;
	
	public AutorBEAN(int idAutores, String nome, String documento, int situacao) {
		super();
		this.idAutores = idAutores;
		this.nome = nome;
		this.documento = documento;
		this.situacao = situacao;
	}
	
	public int getIdAutores() {
		return idAutores;
	}
	public void setIdAutores(int idAutores) {
		this.idAutores = idAutores;
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
