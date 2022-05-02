/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author Papiro
 */
public class ProjectController {
    
    public void save(Project project) {
        String sql = "INSERT INTO projects(name, description, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //cria conecxão com o DB
            connection = ConnectionFactory.getConnection();
            //Cria um PreparedStatement, Classe usada para executar a query
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            //Executa a sql para inserção dos dados
            statement.execute();
        } catch (SQLException ex){
            throw new RuntimeException("Erro ao salvar o Projeto.", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
                
    }
    
    public void update(Project project){
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //cria conecxão com o DB
            connection = ConnectionFactory.getConnection();
            //Cria um PreparedStatement, Classe usada para executar a query
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date( project.getCreatedAt().getTime()));
            statement.setDate(4, new Date( project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            
            //Executa a sql para inserção dos dados
            statement.execute();
            
        } catch (SQLException ex){
            throw new RuntimeException("Erro ao atualizar o Projeto.", ex);
            
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> getAll() {
        
        String sql = "SELECT * FROM projects";
        
        List<Project> projects = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        //Classe que vai pegar os dados do DB
        ResultSet resultSet = null;
        
        try{
            
            //cria conecxão com o DB
            connection = ConnectionFactory.getConnection();
            //Cria um PreparedStatement, Classe usada para executar a query
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            //enquanto existir dados no DB faça
            while(resultSet.next()){
                
             Project project = new Project();
             
             project.setId(resultSet.getInt("id"));
             project.setName(resultSet.getString("name"));
             project.setDescription(resultSet.getString("description"));
             project.setCreatedAt(resultSet.getDate("createdAt"));
             project.setUpdatedAt(resultSet.getDate("updatedAt")); 
             
             //adiciono o projeto recuperado do DB a lista de Projetos
             projects.add(project);
            }   
        } catch (SQLException ex){
            throw new RuntimeException("Erro ao buscar os Projetos.", ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
                
       return projects;
    }
    
    public void removeById (int idProject) {
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            statement.execute();
            
        } catch(SQLException ex){
            throw new RuntimeException("Erro ao deletar o Projeto.", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    
    
}
