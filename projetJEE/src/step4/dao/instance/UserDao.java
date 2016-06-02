package step4.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import step4.model.UserModelBean;

public class UserDao {
	private Connection connection;
	private String dB_HOST;
	private String dB_PORT;
	private String dB_NAME;
	private String dB_USER;
	private String dB_PWD;
	
	public UserDao(String DB_HOST,String DB_PORT, String DB_NAME,String DB_USER,String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD = DB_PWD;
	}
	
	public void addUser(UserModelBean user) {
		// Création de la requête
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "INSERT INTO User(lastname, surname, age, login, pwd) VALUES('"+user.getLastname()+"','"+user.getSurname()+"','"+user.getAge()+"','"+user.getLogin()+"','"+user.getPwd()+"')";
			query = connection.prepareStatement(sql);
			query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<UserModelBean> getAllUser(){
		java.sql.Statement query;
		//return value
		ArrayList<UserModelBean> userList=new ArrayList<UserModelBean>();
		try {
		// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from User";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet results = query.executeQuery(sql);
			UserModelBean user;
			while(results.next()) {
				user = new UserModelBean(results.getString("lastname"), results.getString("surname"), results.getInt("age"), results.getString("login"), results.getString("pwd"));
				userList.add(user);
			}
			results.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public UserModelBean checkUser(String login, String pwd) {
		java.sql.Statement query;
		UserModelBean user = null;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			String sql = "Select * from User where login ='"+login+"' and pwd='"+pwd+"'";
			query = connection.prepareStatement(sql);
			java.sql.ResultSet result = query.executeQuery(sql);
			if(result.next() != false) {
				user = new UserModelBean(result.getString("lastname"), result.getString("surname"), result.getInt("age"), result.getString("login"), result.getString("pwd"));
			}else {
				user = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
}
