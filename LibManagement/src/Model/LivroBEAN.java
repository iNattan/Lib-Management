package Model;

public class LivroBEAN {
	private int idLivros;
	private String nome;
	private int idAutor;
	private int idEditora;
	private String status;
	private int situacao;
	
	public LivroBEAN(int idLivros, String nome, int idAutor, int idEditora, String status, int situacao) {
		super();
		this.idLivros = idLivros;
		this.nome = nome;
		this.idAutor = idAutor;
		this.idEditora = idEditora;
		this.status = status;		
		this.situacao = situacao;
	}
	
	public int getIdLivros() {
		return idLivros;
	}
	public void setIdLivros(int idLivros) {
		this.idLivros = idLivros;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}
	public int getIdEditora() {
		return idEditora;
	}
	public void setIdEditora(int idEditora) {
		this.idEditora = idEditora;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSituacao() {
		return situacao;
	}
	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}
}
