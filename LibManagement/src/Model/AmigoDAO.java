package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AmigoDAO {
    private static AmigoDAO instance;

    private AmigoDAO(){
        MySQLDAO.getConnection();
    }

    public static AmigoDAO getInstance(){
        if (instance == null){
            instance = new AmigoDAO();
        }
        return instance;
    }

    public long create(AmigoBEAN amigo){
        String query = "INSERT INTO amigos (nome, documento, situacao) VALUES (?,?,?)";
        return MySQLDAO.executeQuery(query, amigo.getNome(), amigo.getDocumento(), amigo.getSituacao());
    }
    
    public long update(AmigoBEAN amigo){
        String query = "UPDATE amigos SET nome=?, documento=?, situacao=? WHERE idAmigos=?";
        return MySQLDAO.executeQuery(query, amigo.getNome(), amigo.getDocumento(), amigo.getSituacao(), amigo.getIdAmigos());
    }

    public void inative(AmigoBEAN amigo){       
        String query = "UPDATE amigos SET situacao=1 WHERE idAmigos = ?";
        MySQLDAO.executeQuery(query, amigo.getIdAmigos());
    }
    
    public void active(AmigoBEAN amigo){       
        String query = "UPDATE amigos SET situacao=0 WHERE idAmigos = ?";
        MySQLDAO.executeQuery(query, amigo.getIdAmigos());
    }

    public ArrayList<AmigoBEAN> listaAmigos(String query, Object... parametros){
        ArrayList<AmigoBEAN> lista = new ArrayList<AmigoBEAN>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query, parametros);
        try{
            while(rs.next()){
                lista.add(new AmigoBEAN(rs.getInt("idAmigos"),rs.getString("nome"),
                                        rs.getString("documento"), rs.getInt("situacao")));

            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
    return lista;
    }
    
    public ArrayList<AmigoBEAN> findAllAmigos(int situacao){
        return listaAmigos("SELECT * FROM amigos WHERE situacao = ? ORDER BY nome", situacao);
    }    
    
    public ArrayList<AmigoBEAN> findAmigoByID(int id, int situacao){
    	return listaAmigos("SELECT * FROM amigos WHERE idAmigos=? and situacao = ? ORDER BY nome", id, situacao);
    }
    
    public AmigoBEAN findAmigo(int id){
    	AmigoBEAN result = null;
    	ResultSet rs = null;
    	rs = MySQLDAO.getResultSet("SELECT * FROM amigos WHERE idAmigos=? ORDER BY nome", id);
    	try{
    		if(rs.next()){
    			result = new AmigoBEAN(rs.getInt("idAmigos"),rs.getString("nome"),
    								   rs.getString("documento"), rs.getInt("situacao"));
    		}
    		rs.close ();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public ArrayList<AmigoBEAN> findAmigoByNome(String nome, int situacao){
    	return listaAmigos("SELECT * FROM amigos WHERE nome like ? and situacao = ? ORDER BY nome", "%" + nome + "%", situacao);     
    }        
    
    public ArrayList<AmigoBEAN> findAmigoByDocumento(String documento, int situacao){
    	return listaAmigos("SELECT * FROM amigos WHERE documento like ? and situacao = ? ORDER BY nome", "%" + documento + "%", situacao);      
    } 
}