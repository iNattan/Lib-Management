package Controller;

import java.util.ArrayList;

import Model.AutorBEAN;
import Model.AutorDAO;

public class AutorController {
	
	public long addAutor(AutorBEAN autor){
		return AutorDAO.getInstance().create(autor);
	}
	
	public long updateAutor(AutorBEAN autor){
		return AutorDAO.getInstance().update(autor);
	}
	
	public void inativeAutor(AutorBEAN autor){
		AutorDAO.getInstance().inative(autor);
	}
	
	public void activeAutor(AutorBEAN autor){
		AutorDAO.getInstance().active(autor);
	}
	
	public ArrayList<AutorBEAN> listaAutores(int situacao){
		return AutorDAO.getInstance().findAllAutores(situacao);
	}
	
	public ArrayList<AutorBEAN> listaAutoresByID(int id, int situacao){
		return AutorDAO.getInstance().findAutorByID(id, situacao);
	}
	
	public ArrayList<AutorBEAN> listaAutoresByNome(String nome, int situacao){
		return AutorDAO.getInstance().findAutorByNome(nome, situacao);
	}
	
	public ArrayList<AutorBEAN> listaAutoresByDocumento(String documento, int situacao){
		return AutorDAO.getInstance().findAutorByDocumento(documento, situacao);
	}	
	
	public AutorBEAN buscaAutor(int id) {
		return AutorDAO.getInstance().findAutor(id);
	}
}
