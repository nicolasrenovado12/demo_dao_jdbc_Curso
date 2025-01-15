package program_210;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
public class DB {

	private static Connection conn = null;
	
	public static Connection getConnection()    {
		if (conn == null) {
			Properties props = loadProperties();
			String url = props.getProperty("dburl");
			try {
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;

		}
		return conn;
		
	}
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();	
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 

	private static Properties loadProperties()   {
		Properties props = new Properties();
		try(FileInputStream fs = new FileInputStream("/home/nicolas-guilherme/Documents/db.properties")) {
			props.load(fs);
			return props;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
		
		 
	}
	
	public static void closeStatement(Statement st) {
		
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
