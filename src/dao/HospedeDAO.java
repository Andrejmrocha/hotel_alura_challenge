package dao;

import java.sql.Connection;
import java.sql.Date;
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

	public void atualizar(String dado, String id, int coluna) {
		try {
			String sql = null;
			
			switch(coluna) {
			case 1:
				sql = "UPDATE hospedes SET nome = ? WHERE id = ?";
				break;
			
			case 2:
				sql = "UPDATE hospedes SET sobrenome = ? WHERE id = ?";
				break;
			
			case 3:
				sql = "UPDATE hospedes SET data_nascimento = ? WHERE id = ?";
				break;
			
			case 4:
				sql = "UPDATE hospedes SET nacionalidade = ? WHERE id = ?";
				break;
				
			case 5:
				sql = "UPDATE hospedes SET telefone = ? WHERE id = ?";
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
				sql = "UPDATE hospedes SET nome = NULL WHERE id = ?";
				break;
				
			case 2:
				sql = "UPDATE hospedes SET sobrenome = NULL WHERE id = ?";
				break;
				
			case 3:
				sql = "UPDATE hospedes SET data_nascimento = NULL WHERE id = ?";
				break;
				
			case 4:
				sql = "UPDATE hospedes SET nacionalidade = NULL WHERE id = ?";
				break;
				
			case 5:
				sql = "UPDATE hospedes SET telefone = NULL WHERE id = ?";
				break;
			}
			
			
			try(PreparedStatement pst = connection.prepareStatement(sql)) {
				pst.setString(1, id);
				pst.execute();
			}
		}catch(SQLException e) {
			throw new RuntimeException("Erro ao excluir hóspede " + e);
			
		}
	}
}
