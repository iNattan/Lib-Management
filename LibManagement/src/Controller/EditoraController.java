package Controller;

import java.util.ArrayList;

import Model.EditoraBEAN;
import Model.EditoraDAO;

public class EditoraController {
	
	public long addEditora(EditoraBEAN editora){
		return EditoraDAO.getInstance().create(editora);
	}
	
	public long updateEditora(EditoraBEAN editora){
		return EditoraDAO.getInstance().update(editora);
	}
	
	public void inativeEditora(EditoraBEAN editora){
		EditoraDAO.getInstance().inative(editora);
	}
	
	public void activeEditora(EditoraBEAN editora){
		EditoraDAO.getInstance().active(editora);
	}
	
	public ArrayList<EditoraBEAN> listaEditoras(int situacao){
		return EditoraDAO.getInstance().findAllEditoras(situacao);
	}
	
	public ArrayList<EditoraBEAN> listaEditorasByID(int id, int situacao){
		return EditoraDAO.getInstance().findEditoraByID(id, situacao);
	}
	
	public ArrayList<EditoraBEAN> listaEditorasByNome(String nome, int situacao){
		return EditoraDAO.getInstance().findEditoraByNome(nome, situacao);
	}
	
	public ArrayList<EditoraBEAN> listaEditorasByDocumento(String documento, int situacao){
		return EditoraDAO.getInstance().findEditoraByDocumento(documento, situacao);
	}	
	
	public EditoraBEAN buscaEditora(int id) {
		return EditoraDAO.getInstance().findEditora(id);
	}
}
