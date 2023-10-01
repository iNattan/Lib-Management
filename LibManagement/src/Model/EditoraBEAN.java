package Model;

public class EditoraBEAN {
	private int idEditoras;
	private String nome;
	private String documento;
	private String situacao;
	
	public EditoraBEAN(int idEditoras, String nome, String documento, String situacao) {
		super();
		this.idEditoras = idEditoras;
		this.nome = nome;
		this.documento = documento;
		this.situacao = situacao;
	}
	
	public int getIdEditoras() {
		return idEditoras;
	}
	public void setIdEditoras(int idEditoras) {
		this.idEditoras = idEditoras;
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
	
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
