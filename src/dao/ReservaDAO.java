package dao;

import java.sql.Connection;
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
	
	public void atualizar(Reserva reserva, String id) {
		try {
			String sql = "UPDATE reservas SET data_entrada = ?, data_saida = ?, valor = ?,"
					+ "forma_pagamento = ? WHERE id = ?";
			
			try(PreparedStatement pst = connection.prepareStatement(sql)){
				pst.setDate(1, reserva.getDataEntrada());
				pst.setDate(2, reserva.getDataSaida());
				pst.setString(3, reserva.getValor());
				pst.setString(4, reserva.getFormaPagamento());
				pst.setString(5, id);
				
				pst.execute();
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
