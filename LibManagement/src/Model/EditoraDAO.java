package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditoraDAO {
    private static EditoraDAO instance;

    private EditoraDAO(){
        MySQLDAO.getConnection();
    }

    public static EditoraDAO getInstance(){
        if (instance == null){
            instance = new EditoraDAO();
        }
        return instance;
    }

    public long create(EditoraBEAN editora){
        String query = "INSERT INTO editoras (nome, documento, situacao) VALUES (?,?,?)";
        return MySQLDAO.executeQuery(query, editora.getNome(), editora.getDocumento(), editora.getSituacao());
    }
    
    public long update(EditoraBEAN editora){
        String query = "UPDATE editoras SET nome=?, documento=?, situacao=? WHERE idEditoras=?";
        return MySQLDAO.executeQuery(query, editora.getNome(), editora.getDocumento(), editora.getSituacao(), editora.getIdEditoras());
    }

    public void inative(EditoraBEAN editora){       
        String query = "UPDATE editoras SET situacao=1 WHERE idEditoras = ?";
        MySQLDAO.executeQuery(query, editora.getIdEditoras());
    }
    
    public void active(EditoraBEAN editora){       
        String query = "UPDATE editoras SET situacao=0 WHERE idEditoras = ?";
        MySQLDAO.executeQuery(query, editora.getIdEditoras());
    }

    public ArrayList<EditoraBEAN> listaEditoras(String query, Object... parametros){
        ArrayList<EditoraBEAN> lista = new ArrayList<EditoraBEAN>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query, parametros);
        try{
            while(rs.next()){
                lista.add(new EditoraBEAN(rs.getInt("idEditoras"),rs.getString("nome"),
                                          rs.getString("documento"), rs.getInt("situacao")));

            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
    return lista;
    }
    
    public ArrayList<EditoraBEAN> findAllEditoras(int situacao){
        return listaEditoras("SELECT * FROM editoras WHERE situacao = ? ORDER BY nome", situacao);
    }    
    
    public ArrayList<EditoraBEAN> findEditoraByID(int id, int situacao){
    	return listaEditoras("SELECT * FROM editoras WHERE idEditoras=? and situacao = ? ORDER BY nome", id, situacao);
    }
    
    public EditoraBEAN findEditora(int id, int situacao){
    	EditoraBEAN result = null;
    	ResultSet rs = null;
    	rs = MySQLDAO.getResultSet("SELECT * FROM editoras WHERE idEditoras=? and situacao = ? ORDER BY nome", id, situacao);
    	try{
    		if(rs.next()){
    			result = new EditoraBEAN(rs.getInt("idEditoras"),rs.getString("nome"),
    									 rs.getString("documento"), rs.getInt("situacao"));
    		}
    		rs.close ();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public ArrayList<EditoraBEAN> findEditoraByNome(String nome, int situacao){
    	return listaEditoras("SELECT * FROM editoras WHERE nome like ? and situacao = ? ORDER BY nome", "%" + nome + "%", situacao);     
    }        
    
    public ArrayList<EditoraBEAN> findEditoraByDocumento(String documento, int situacao){
    	return listaEditoras("SELECT * FROM editoras WHERE documento like ? and situacao = ? ORDER BY nome", "%" + documento + "%", situacao);      
    } 
}