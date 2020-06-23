package fileCatalog.server.integration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import fileCatalog.common.integration.FileMeta;

public class DbHandler {
	private String userID = "root";
	private String password = "nikos";
	private Connection conn;
	private String connectionUrl = "jdbc:mysql://localhost:3306/filecatalogdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	
	public DbHandler() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.conn = DriverManager.getConnection(connectionUrl, userID, password);
		//ResultSet rs = conn.prepareStatement("show tables").executeQuery();
	}
	
	public boolean usernameAvailable(String username) throws SQLException {
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT username FROM users WHERE username=?");
		statement.setString(1, username);
		ResultSet rs = statement.executeQuery();
		return 	!rs.next();
	}
	
	public void registerUser(String username, String password) throws SQLException {
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement("INSERT INTO `filecatalogdb`.`users` (`username`, `password`) VALUES (?, ?)");
		statement.setString(1, username);
		statement.setString(2, password);
		statement.executeUpdate();
	}

	public void test() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		String connectionUrl = "jdbc:mysql://localhost:3306/filecatalogdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		Connection conn = DriverManager.getConnection(connectionUrl, userID, password);
		ResultSet rs = conn.prepareStatement("show tables").executeQuery();

		while(rs.next()){
			String s = rs.getString(1);
			System.out.println(s);
		}
	}

	public boolean validCredentials(String username, String password) throws SQLException {
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT username FROM users WHERE username=? AND password=?");
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet rs = statement.executeQuery();
		return rs.next();
	}

	public void registerFile(FileMeta file) throws SQLException {
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement("INSERT INTO `filecatalogdb`.`files` (`name`, `owner`, `size`) VALUES (?, ?, ?)");
		statement.setString(1, file.getName());
		statement.setString(2, file.getOwner());
		statement.setFloat(3, file.getSize());
		statement.executeUpdate();
	}

	public FileMeta[] getFiles() throws SQLException {
		Vector<FileMeta> files = new Vector<FileMeta>();
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT * FROM files");
		ResultSet rs = statement.executeQuery();
		int i = 0;
		while(rs.next()) {
			files.add(new FileMeta(rs.getString(1), rs.getString(2), rs.getInt(3)));
			
			i++;
		}
		return files.toArray(new FileMeta[i]);
	}

	public FileMeta getFile(String filename) throws SQLException {
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement("SELECT * FROM files WHERE name=?");
		statement.setString(1, filename);
		ResultSet rs = statement.executeQuery();
		if(rs.next())
			return new FileMeta(rs.getString(1), rs.getString(2), rs.getInt(3));
		return null;
	}

	public void deleteFile(String filename) throws SQLException {
		PreparedStatement statement = (PreparedStatement) conn.prepareStatement("DELETE FROM files WHERE name=?");
		statement.setString(1, filename);
		statement.executeUpdate();
	}

}
