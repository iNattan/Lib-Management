package Model;

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
    
    public void update(EditoraBEAN editora){
        String query = "UPDATE editoras SET nomeo=?, documento=?, situacao=? WHERE idEditoras = ?";
        MySQLDAO.executeQuery(query, editora.getNome(), editora.getDocumento(),
        editora.getSituacao(), editora.getIdEditoras());
    }

    public void delete(EditoraBEAN editora){
        MySQLDAO.executeQuery("DELETE FROM editoras WHERE idEditoras = ?",editora.getIdEditoras());
    }

    public ArrayList<EditoraBEAN> findAllEditoras(){
        return listaEditoras("SELECT * FROM editoras ORDER BY nome");
    }
    
    public ArrayList<EditoraBEAN> listaEditoras(String query){
        ArrayList<EditoraBEAN> lista = new ArrayList<EditoraBEAN>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query);
        try{
            while(rs.next()){
                lista.add(new EditoraBEAN(rs.getInt("idEditoras"),rs.getString("nome"),
                                          rs.getString("documento"), rs.getString("situacao")));

            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
    return lista;
    }
    
    public EditoraBEAN findEditora(int id){
        EditoraBEAN result = null;
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet("SELECT * FROM editoras WHERE idEditoras=?", id);
        try{
            if(rs.next()){
                result = new EditoraBEAN(rs.getInt("idEditoras"),rs.getString("nome"),
                                         rs.getString("documento"), rs.getString("situacao"));
            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    
    public int findId(EditoraBEAN editora){
        int result = 0;
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet("SELECT * FROM editoras WHERE nome= ? and documento= ? and situacao = ?",
        		                   editora.getNome(),editora.getDocumento(),editora.getSituacao());
        try{
            if(rs.next()){
                result = rs.getInt("idEditoras");
            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    
    public Boolean isExist(int id){
        Boolean result = false;
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet("SELECT * FROM editoras WHERE idEditoras= ?",id);
        try{
            if(rs.next()){
            result = true;
            }
        rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }	
}