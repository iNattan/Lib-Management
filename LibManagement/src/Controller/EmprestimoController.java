package Controller;

import java.util.ArrayList;

import Model.EmprestimoBEAN;
import Model.EmprestimoDAO;

public class EmprestimoController {
	
	public long addEmprestimo(EmprestimoBEAN emprestimo){
		return EmprestimoDAO.getInstance().create(emprestimo);
	}
	
	public ArrayList<EmprestimoBEAN> listaEmprestimo(int situacao){
		return EmprestimoDAO.getInstance().findAllEmprestimos(situacao);
	}
	
	public ArrayList<EmprestimoBEAN> listaEmprestimoByID(int id, int situacao){
		return EmprestimoDAO.getInstance().findEmprestimoByID(id, situacao);
	}
	
	public ArrayList<EmprestimoBEAN> listaEmprestimoByAmigo(String amigo, int situacao){
		return EmprestimoDAO.getInstance().findEmprestimoByNome(amigo, situacao);
	}
	
	/*public EmprestimoBEAN buscaEmprestimo(int id, int situacao) {
		return EmprestimoDAO.getInstance().findEmprestimo(id, situacao);
	}*/
	
	public long atualizaStatus(int idEmprestimo, String status){
		return EmprestimoDAO.getInstance().updateStatus(idEmprestimo, status);
	}
}
