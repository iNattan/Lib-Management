package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LivroDAO {
    private static LivroDAO instance;

    private LivroDAO(){
        MySQLDAO.getConnection();
    }

    public static LivroDAO getInstance(){
        if (instance == null){
            instance = new LivroDAO();
        }
        return instance;
    }

    public long create(LivroBEAN livro){
        String query = "INSERT INTO livros (nome, idAutor, idEditora, status, situacao) VALUES (?,?,?,?,?)";
        return MySQLDAO.executeQuery(query, livro.getNome(), livro.getIdAutor(), livro.getIdEditora(), livro.getStatus(), livro.getSituacao());
    }
    
    public long update(LivroBEAN livro){
        String query = "UPDATE livros SET nome=?, idAutor=?, idEditora=?, status=?, situacao=? WHERE idLivros=?";
        return MySQLDAO.executeQuery(query, livro.getNome(), livro.getIdAutor(), livro.getIdEditora(), livro.getStatus(), livro.getSituacao(), livro.getIdLivros());
    }

    public void inative(LivroBEAN livro){       
        String query = "UPDATE livros SET situacao=1 WHERE idLivros = ?";
        MySQLDAO.executeQuery(query, livro.getIdLivros());
    }
    
    public void active(LivroBEAN livro){       
        String query = "UPDATE livros SET situacao=0 WHERE idLivros = ?";
        MySQLDAO.executeQuery(query, livro.getIdLivros());
    }

    public ArrayList<LivroBEAN> listaLivros(String query, Object... parametros){
        ArrayList<LivroBEAN> lista = new ArrayList<LivroBEAN>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query, parametros);
        try{
            while(rs.next()){
                lista.add(new LivroBEAN(rs.getInt("idLivros"),rs.getString("nome"),rs.getInt("idAutor"),          		
                						rs.getInt("idEditora"),rs.getString("status"),rs.getInt("situacao")));

            }
            rs.close ();
        }catch(SQLException e){
            e.printStackTrace();
        }
    return lista;
    }
    
    public ArrayList<LivroBEAN> findAllLivros(int situacao){
        return listaLivros("SELECT idLivros, nome, idAutor, idEditora, status, situacao FROM livros WHERE situacao = ? ORDER BY nome", situacao);
    }    
    
    public ArrayList<LivroBEAN> findLivroByID(int id, int situacao){
    	return listaLivros("SELECT idLivros, nome, idAutor, idEditora, status, situacao FROM livros WHERE idLivros=? and situacao = ? ORDER BY nome", id, situacao);
    }
    
    public LivroBEAN findLivro(int id, int situacao){
    	LivroBEAN result = null;
    	ResultSet rs = null;
    	rs = MySQLDAO.getResultSet("SELECT idLivros, nome, idAutor, idEditora, status, situacao FROM livros WHERE idLivros=? and situacao = ? ORDER BY nome", id, situacao);
    	try{
    		if(rs.next()){
    			result = new LivroBEAN(rs.getInt("idLivros"),rs.getString("nome"),rs.getInt("idAutor"),              		
									   rs.getInt("idEditora"),rs.getString("status"),rs.getInt("situacao"));
    		}
    		rs.close ();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return result;
    }
    
    public ArrayList<LivroBEAN> findLivroByNome(String nome, int situacao){
    	return listaLivros("SELECT idLivros, nome, idAutor, idEditora, status, situacao FROM livros WHERE nome like ? and situacao = ? ORDER BY nome", "%" + nome + "%", situacao);     
    }        
    
    public ArrayList<LivroBEAN> findLivroByAutor(String autor, int situacao){
    	return listaLivros("SELECT "
	 	 		 	     + " l.idLivros, l.nome, l.idAutor, l.idEditora, l.status, l.situacao "
	 	 		 	     + "FROM livros l "
	 	 		 	     + "INNER JOIN autores a on l.idAutor = a.idAutores "
	 	 		 	     + "WHERE a.nome like ? and l.situacao = ? ORDER BY l.nome", "%" + autor + "%", situacao);      
    }
    
    public ArrayList<LivroBEAN> findLivroByEditora(String editora, int situacao){
    	return listaLivros("SELECT "
	 	 		 	     + " l.idLivros, l.nome, l.idAutor, l.idEditora, l.status, l.situacao "
	 	 		 	     + "FROM livros l "
	 	 		 	     + "INNER JOIN editoras e on l.idEditora = e.idEditoras "
	 	 		 	     + "WHERE e.nome like ? and l.situacao = ? ORDER BY l.nome", "%" + editora + "%", situacao);      
    } 
}