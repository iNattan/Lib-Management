package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmprestimoBEAN {
	private int idEmprestimos;
	private LocalDate data;
	private int status;
	private int idAmigos;
	private String situacao;
	private ArrayList<LivroBEAN> livrosEmprestados;
	
	public EmprestimoBEAN(int idEmprestimos, LocalDate data, int status, int idAmigos, String situacao, ArrayList<LivroBEAN> livrosEmprestados) {
		super();
		this.idEmprestimos = idEmprestimos;
		this.data = data;
		this.status = status;
		this.idAmigos = idAmigos;
		this.situacao = situacao;
		this.livrosEmprestados = livrosEmprestados;
	}	

	public int getIdEmprestimos() {
		return idEmprestimos;
	}
	
	public void setIdEmprestimos(int idEmprestimos) {
		this.idEmprestimos = idEmprestimos;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getidAmigos() {
		return idAmigos;
	}
	
	public void setidAmigos(int idAmigos) {
		idAmigos = idAmigos;
	}
	
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public ArrayList<LivroBEAN> getLivrosEmprestados() {
		return livrosEmprestados;
	}

	public void setLivrosEmprestados(ArrayList<LivroBEAN> livrosEmprestados) {
		this.livrosEmprestados = livrosEmprestados;
	}

}