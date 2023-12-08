package Model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        String query = "INSERT INTO emprestimos (data, status, idAmigos, situacao) VALUES (?,?,?,?)";
        long emprestimoID = MySQLDAO.executeQuery(query, emprestimo.getData(), emprestimo.getStatus(), emprestimo.getidAmigos(), emprestimo.getSituacao());
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
        return MySQLDAO.executeQuery(query, emprestimo.getData(), emprestimo.getStatus(), emprestimo.getidAmigos(), emprestimo.getIdEmprestimos());
    }

    public long updateStatus(int idEmprestimo, int status){       
        String query = "UPDATE emprestimos SET status=? WHERE idEmprestimos = ?";
        return MySQLDAO.executeQuery(query, status, idEmprestimo);
    }
    
    public long updateSituacao(int idEmprestimo, String situacao){       
        String query = "UPDATE emprestimos SET situacao=? WHERE idEmprestimos = ?";
        return MySQLDAO.executeQuery(query, situacao, idEmprestimo);
    }    

    public ArrayList<EmprestimoBEAN> listaEmprestimos(String query, Object... parametros){
        ArrayList<EmprestimoBEAN> lista = new ArrayList<EmprestimoBEAN>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query, parametros);
        try{
            while(rs.next()){
            	Date dataSQL = rs.getDate("data");
            	LocalDate data = dataSQL.toLocalDate();
            	ArrayList<LivroBEAN> livrosEmprestados = new ArrayList<LivroBEAN>();
                lista.add(new EmprestimoBEAN(rs.getInt("idEmprestimos"), data, rs.getInt("status"), 
                							 rs.getInt("idAmigos"), rs.getString("situacao"), livrosEmprestados));

            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
    return lista;
    }
    
    public ArrayList<EmprestimoBEAN> findAllEmprestimos(int status){
        return listaEmprestimos("SELECT "
				  			  + "e.idEmprestimos, e.data, e.status, e.idAmigos, e.situacao "
				  			  + "FROM emprestimos e "
				  			  + "INNER JOIN amigos a ON e.idAmigos = a.idAmigos "
				  			  + "WHERE e.status = ? ORDER BY a.nome", status);
    }    
    
    public ArrayList<EmprestimoBEAN> findEmprestimoByID(int id, int status){
    	return listaEmprestimos("SELECT "
				  			  + "e.idEmprestimos, e.data, e.status, e.idAmigos, e.situacao "
				  			  + "FROM emprestimos e "
				  			  + "INNER JOIN amigos a ON e.idAmigos = a.idAmigos "
				  			  + "WHERE e.idEmprestimos = ? and status = ? ORDER BY a.nome", id, status);
    }
    
    public EmprestimoBEAN findEmprestimo(int id){
    	EmprestimoBEAN result = null;
    	ResultSet rs = null;
    	rs = MySQLDAO.getResultSet("SELECT * FROM emprestimos WHERE idEmprestimos=?", id);
    	try{
    		if(rs.next()){
    			Date dataSQL = rs.getDate("data");
            	LocalDate data = dataSQL.toLocalDate();
            	ArrayList<LivroBEAN> livrosEmprestados = new ArrayList<LivroBEAN>();
                result = new EmprestimoBEAN(rs.getInt("idEmprestimos"), data, rs.getInt("status"), 
                						    rs.getInt("idAmigos"), rs.getString("situacao"), livrosEmprestados);  

            }
    		rs.close ();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public ArrayList<EmprestimoBEAN> findEmprestimoByAmigo(String nome, int status){
    	return listaEmprestimos("SELECT "
    						  + "e.idEmprestimos, e.data, e.status, e.idAmigos, e.situacao "
    						  + "FROM emprestimos e "
    						  + "INNER JOIN amigos a ON e.idAmigos = a.idAmigos "
    						  + "WHERE a.nome like ? and status = ? ORDER BY a.nome", "%" + nome + "%", status);     
    }         
}