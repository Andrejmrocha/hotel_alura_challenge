package factory;

import java.sql.Connection;
import java.sql.SQLException;

public class testaConexao {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.conectar();
		
		System.out.println("conec");
		
		connection.close();
	}

}
