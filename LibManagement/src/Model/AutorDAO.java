package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutorDAO {
    private static AutorDAO instance;

    private AutorDAO(){
        MySQLDAO.getConnection();
    }

    public static AutorDAO getInstance(){
        if (instance == null){
            instance = new AutorDAO();
        }
        return instance;
    }

    public long create(AutorBEAN autor){
        String query = "INSERT INTO autores (nome, documento, situacao) VALUES (?,?,?)";
        return MySQLDAO.executeQuery(query, autor.getNome(), autor.getDocumento(), autor.getSituacao());
    }
    
    public long update(AutorBEAN autor){
        String query = "UPDATE autores SET nome=?, documento=?, situacao=? WHERE idAutores=?";
        return MySQLDAO.executeQuery(query, autor.getNome(), autor.getDocumento(), autor.getSituacao(), autor.getIdAutores());
    }

    public void inative(AutorBEAN autor){       
        String query = "UPDATE autores SET situacao=1 WHERE idAutores = ?";
        MySQLDAO.executeQuery(query, autor.getIdAutores());
    }
    
    public void active(AutorBEAN autor){       
        String query = "UPDATE autores SET situacao=0 WHERE idAutores = ?";
        MySQLDAO.executeQuery(query, autor.getIdAutores());
    }

    public ArrayList<AutorBEAN> listaAutores(String query, Object... parametros){
        ArrayList<AutorBEAN> lista = new ArrayList<AutorBEAN>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query, parametros);
        try{
            while(rs.next()){
                lista.add(new AutorBEAN(rs.getInt("idAutores"),rs.getString("nome"),
                                        rs.getString("documento"), rs.getInt("situacao")));

            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
    return lista;
    }
    
    public ArrayList<AutorBEAN> findAllAutores(int situacao){
        return listaAutores("SELECT * FROM autores WHERE situacao = ? ORDER BY nome", situacao);
    }    
    
    public ArrayList<AutorBEAN> findAutorByID(int id, int situacao){
    	return listaAutores("SELECT * FROM autores WHERE idAutores=? and situacao = ? ORDER BY nome", id, situacao);
    }
    
    public AutorBEAN findAutor(int id){
    	AutorBEAN result = null;
    	ResultSet rs = null;
    	rs = MySQLDAO.getResultSet("SELECT * FROM autores WHERE idAutores=? ORDER BY nome", id);
    	try{
    		if(rs.next()){
    			result = new AutorBEAN(rs.getInt("idAutores"),rs.getString("nome"),
    								   rs.getString("documento"), rs.getInt("situacao"));
    		}
    		rs.close ();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public ArrayList<AutorBEAN> findAutorByNome(String nome, int situacao){
    	return listaAutores("SELECT * FROM autores WHERE nome like ? and situacao = ? ORDER BY nome", "%" + nome + "%", situacao);     
    }        
    
    public ArrayList<AutorBEAN> findAutorByDocumento(String documento, int situacao){
    	return listaAutores("SELECT * FROM autores WHERE documento like ? and situacao = ? ORDER BY nome", "%" + documento + "%", situacao);      
    } 
}