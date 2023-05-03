package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Hospede;

public class HospedeDAO {
	
	private Connection connection;
	
	public HospedeDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void cadastrar(Hospede hospede, int idReserva) {
		try {
			String sql = "INSERT INTO hospedes (nome, sobrenome, data_nascimento, nacionalidade, telefone, id_reserva) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				pstm.setString(1, hospede.getNome());
				pstm.setString(2, hospede.getSobrenome());
				pstm.setDate(3, hospede.getDataNascimento());
				pstm.setString(4, hospede.getNacionalidade());
				pstm.setString(5, hospede.getTelefone());
				pstm.setInt(6, idReserva);
				
				pstm.execute();
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Hospede> buscarPorSobrenome(String sobrenome) {
		List<Hospede> hospede = new ArrayList<Hospede>();
		try {
			String sql = "SELECT * FROM hospedes WHERE sobrenome = ?";
			
			try(PreparedStatement pstm = connection.prepareStatement(sql)){
				pstm.setString(1, sobrenome);
				pstm.execute();
				
				try(ResultSet rst = pstm.getResultSet()){
					while(rst.next()) {
						Hospede criarHospede = new Hospede(rst.getInt(1), rst.getString(2), rst.getString(3),
								rst.getDate(4), rst.getString(5), rst.getString(6), rst.getInt(7));
						hospede.add(criarHospede);
					}
				}
			}
			return hospede;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualizar(Hospede hospede, String id) {
		try {
			String sql = "UPDATE hospedes SET nome = ?, sobrenome = ?, data_nascimento = ?,"
					+ " nacionalidade = ?, telefone = ? WHERE id = ?";
			
			try(PreparedStatement pst = connection.prepareStatement(sql)){
				pst.setString(1, hospede.getNome());
				pst.setString(2, hospede.getSobrenome());
				pst.setDate(3, hospede.getDataNascimento());
				pst.setString(4, hospede.getNacionalidade());
				pst.setString(5, hospede.getTelefone());
				pst.setString(6, id);
				pst.execute();
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
