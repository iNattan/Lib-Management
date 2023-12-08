package Controller;

import java.util.ArrayList;

import Model.EmprestimoBEAN;
import Model.EmprestimoDAO;

public class EmprestimoController {
	
	public long addEmprestimo(EmprestimoBEAN emprestimo){
		return EmprestimoDAO.getInstance().create(emprestimo);
	}
	
	public ArrayList<EmprestimoBEAN> listaEmprestimo(int status){
		return EmprestimoDAO.getInstance().findAllEmprestimos(status);
	}
	
	public ArrayList<EmprestimoBEAN> listaEmprestimoByID(int id, int status){
		return EmprestimoDAO.getInstance().findEmprestimoByID(id, status);
	}
	
	public ArrayList<EmprestimoBEAN> listaEmprestimoByAmigo(String amigo, int status){
		return EmprestimoDAO.getInstance().findEmprestimoByAmigo(amigo, status);
	}
	
	public EmprestimoBEAN buscaEmprestimo(int id) {
		return EmprestimoDAO.getInstance().findEmprestimo(id);
	}
	
	public long atualizaStatus(int idEmprestimo, int status){
		return EmprestimoDAO.getInstance().updateStatus(idEmprestimo, status);
	}
	
	public long atualizaSituacao(int idEmprestimo, String situacao){
		return EmprestimoDAO.getInstance().updateSituacao(idEmprestimo, situacao);
	}
}
