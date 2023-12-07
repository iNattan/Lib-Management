package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmprestimoDAO {
    private static EmprestimoDAO instance;

    private EmprestimoDAO(){
        MySQLDAO.getConnection();
    }

    public static EmprestimoDAO getInstance(){
        if (instance == null){
            instance = new EmprestimoDAO();
        }
        return instance;
    }

    public long create(EmprestimoBEAN emprestimo){
        String query = "INSERT INTO emprestimos (data, status, idAmigos) VALUES (?,?,?)";
        long emprestimoID = MySQLDAO.executeQuery(query, emprestimo.getData(), emprestimo.getStatus(), emprestimo.getidAmigos());
        if (emprestimoID > 0) { 
        	for (LivroBEAN livro : emprestimo.getLivrosEmprestados()) {
                String queryEmprestimoLivro = "INSERT INTO emprestimolivros (idLivros, idEmprestimos) VALUES (?,?)";
                MySQLDAO.executeQuery(queryEmprestimoLivro, livro.getIdLivros(), emprestimoID);
            }
        }
        return emprestimoID;
    }
    
    public long update(EmprestimoBEAN emprestimo){
        String query = "UPDATE emprestimos SET data=?, status=?, idAmigos=? WHERE idEmprestimos=?";
        return MySQLDAO.executeQuery(query, emprestimo.getNome(), emprestimo.getDocumento(), emprestimo.getSituacao(), emprestimo.getIdEmprestimos());
    }

    public long updateStatus(int idEmprestimo, String status){       
        String query = "UPDATE emprestimos SET situacao=? WHERE idEmprestimos = ?";
        return MySQLDAO.executeQuery(query, status, idEmprestimo);
    }
    

    public ArrayList<EmprestimoBEAN> listaEmprestimos(String query, Object... parametros){
        ArrayList<EmprestimoBEAN> lista = new ArrayList<EmprestimoBEAN>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query, parametros);
        try{
            while(rs.next()){
                lista.add(new EmprestimoBEAN(rs.getInt("idEmprestimos"),rs.getString("nome"),
                                        rs.getString("documento"), rs.getInt("situacao")));

            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
    return lista;
    }
    
    public ArrayList<EmprestimoBEAN> findAllEmprestimos(int situacao){
        return listaEmprestimos("SELECT * FROM emprestimos WHERE situacao = ? ORDER BY nome", situacao);
    }    
    
    public ArrayList<EmprestimoBEAN> findEmprestimoByID(int id, int situacao){
    	return listaEmprestimos("SELECT * FROM emprestimos WHERE idEmprestimos=? and situacao = ? ORDER BY nome", id, situacao);
    }
    
    public EmprestimoBEAN findEmprestimo(int id){
    	EmprestimoBEAN result = null;
    	ResultSet rs = null;
    	rs = MySQLDAO.getResultSet("SELECT * FROM emprestimos WHERE idEmprestimos=? ORDER BY nome", id);
    	try{
    		if(rs.next()){
    			result = new EmprestimoBEAN(rs.getInt("idEmprestimos"),rs.getString("nome"),
    								   rs.getString("documento"), rs.getInt("situacao"));
    		}
    		rs.close ();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public ArrayList<EmprestimoBEAN> findEmprestimoByNome(String nome, int situacao){
    	return listaEmprestimos("SELECT * FROM emprestimos WHERE nome like ? and situacao = ? ORDER BY nome", "%" + nome + "%", situacao);     
    }        
}