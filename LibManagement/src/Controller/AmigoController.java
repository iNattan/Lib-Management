package Controller;

import java.util.ArrayList;

import Model.AmigoBEAN;
import Model.AmigoDAO;

public class AmigoController {
	
	public long addAmigo (AmigoBEAN amigo){
		return AmigoDAO.getInstance().create(amigo);
	}
	
	public long updateAmigo(AmigoBEAN amigo){
		return AmigoDAO.getInstance().update(amigo);
	}
	
	public void inativeAmigo(AmigoBEAN amigo){
		AmigoDAO.getInstance().inative(amigo);
	}
	
	public void activeAmigo(AmigoBEAN amigo){
		AmigoDAO.getInstance().active(amigo);
	}
	
	public ArrayList<AmigoBEAN> listaAmigos(int situacao){
		return AmigoDAO.getInstance().findAllAmigos(situacao);
	}
	
	public ArrayList<AmigoBEAN> listaAmigosByID(int id, int situacao){
		return AmigoDAO.getInstance().findAmigoByID(id, situacao);
	}
	
	public ArrayList<AmigoBEAN> listaAmigosByNome(String nome, int situacao){
		return AmigoDAO.getInstance().findAmigoByNome(nome, situacao);
	}
	
	public ArrayList<AmigoBEAN> listaAmigosByDocumento(String documento, int situacao){
		return AmigoDAO.getInstance().findAmigoByDocumento(documento, situacao);
	}	
	
	public AmigoBEAN buscaAmigo(int id) {
		return AmigoDAO.getInstance().findAmigo(id);
	}
}
