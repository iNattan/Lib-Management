package Controller;

import java.util.ArrayList;

import Model.LivroBEAN;
import Model.LivroDAO;

public class LivroController {
	
	public long addLivro(LivroBEAN livro){
		return LivroDAO.getInstance().create(livro);
	}
	
	public long updateLivro(LivroBEAN livro){
		return LivroDAO.getInstance().update(livro);
	}
	
	public void inativeLivro(LivroBEAN livro){
		LivroDAO.getInstance().inative(livro);
	}
	
	public void activeLivro(LivroBEAN livro){
		LivroDAO.getInstance().active(livro);
	}
	
	public ArrayList<LivroBEAN> listaLivro(int situacao){
		return LivroDAO.getInstance().findAllLivros(situacao);
	}
	
	public ArrayList<LivroBEAN> listaLivroByID(int id, int situacao){
		return LivroDAO.getInstance().findLivroByID(id, situacao);
	}
	
	public ArrayList<LivroBEAN> listaLivroByNome(String nome, int situacao){
		return LivroDAO.getInstance().findLivroByNome(nome, situacao);
	}
	
	public ArrayList<LivroBEAN> listaLivroByAutor(String autor, int situacao){
		return LivroDAO.getInstance().findLivroByAutor(autor, situacao);
	}	
	
	public ArrayList<LivroBEAN> listaLivroByEditora(String editora, int situacao){
		return LivroDAO.getInstance().findLivroByEditora(editora, situacao);
	}	
	
	public LivroBEAN buscaLivro(int id, int situacao) {
		return LivroDAO.getInstance().findLivro(id, situacao);
	}
	
	public ArrayList<LivroBEAN> listaLivroDisponiveis(int situacao){
		return LivroDAO.getInstance().findAllLivrosDisponiveis(situacao);
	}
	
	public ArrayList<LivroBEAN> listaLivroDisponiveisByNome(String nome, int situacao){
		return LivroDAO.getInstance().findLivroDisponivelByNome(nome, situacao);
	}
	
	public long atualizaStatus(int idLivro, String status){
		return LivroDAO.getInstance().updateStatus(idLivro, status);
	}
	
	public void atualizaLivros(int id) {
		LivroDAO.getInstance().atualizaLivros(id);
	}
}
