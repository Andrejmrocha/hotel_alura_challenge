package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Reserva;

public class ReservaDAO {
	
	public Connection connection;
	
	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void cadastrar(Reserva reserva){
		try {
			String sql = "INSERT INTO reservas (data_entrada, data_saida, valor, forma_pagamento)"
					+ " VALUES (?, ?, ?, ?)";
			
			try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				pstm.setDate(1, reserva.getDataEntrada());
				pstm.setDate(2, reserva.getDataSaida());
				pstm.setString(3, reserva.getValor());
				pstm.setString(4, reserva.getFormaPagamento());
				
				pstm.executeUpdate();
				
				try(ResultSet rst = pstm.getGeneratedKeys()) {
					while(rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
		
	public List<Reserva> buscarPorNumero(String id) {
		List<Reserva> reserva = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_pagamento from reservas WHERE id=?";
			
			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.setString(1, id);
				pstm.execute();
				
				try(ResultSet rst = pstm.getResultSet()){
					while(rst.next()) {
						Reserva criarReserva = new Reserva(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5));
						reserva.add(criarReserva);
					}
				}
				
			}
			return reserva;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void atualizar(String dado, String id, int coluna) {
		try {
			String sql = null;
			
			switch(coluna) {
			case 1:
				sql = "UPDATE reservas SET data_entrada = ? WHERE id = ?";
				break;
			
			case 2:
				sql = "UPDATE reservas SET data_saida = ? WHERE id = ?";
				break;
			
			case 3:
				sql = "UPDATE reservas SET valor = ? WHERE id = ?";
				break;
			
			case 4:
				sql = "UPDATE reservas SET forma_pagamento = ? WHERE id = ?";
				break;
			}
			try(PreparedStatement pst = connection.prepareStatement(sql)) {
				if(dado.contains("-")) {
					Date data = Date.valueOf(dado);
					pst.setDate(1, data);
					pst.setString(2, id);
					pst.execute();
				} else {
					pst.setString(1, dado);
					pst.setString(2, id);
					pst.execute();
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void excluirDados(String id, int coluna) {
		try {
			String sql = null;
			switch(coluna) {
			case 1:
				sql = "UPDATE reservas SET data_entrada = NULL WHERE id = ?";
				break;
				
			case 2:
				sql = "UPDATE reservas SET data_saida = NULL WHERE id = ?";
				break;
				
			case 3:
				sql = "UPDATE reservas SET valor = NULL WHERE id = ?";
				break;
				
			case 4:
				sql = "UPDATE reservas SET forma_pagamento = NULL WHERE id = ?";
				break;
			}
			
			
			try(PreparedStatement pst = connection.prepareStatement(sql)) {
				pst.setString(1, id);
				pst.execute();
			}
		}catch(SQLException e) {
			throw new RuntimeException("Erro ao excluir reserva " + e);
			
		}
	}
	

}
