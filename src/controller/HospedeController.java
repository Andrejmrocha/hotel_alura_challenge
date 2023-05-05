package controller;

import java.sql.Connection;
import java.util.List;

import dao.HospedeDAO;
import dao.ReservaDAO;
import factory.ConnectionFactory;
import model.Hospede;
import model.Reserva;

public class HospedeController {
private HospedeDAO hospedeDAO;
	
	public HospedeController() {
		Connection connection = new ConnectionFactory().conectar();
		this.hospedeDAO = new HospedeDAO(connection);
	}
	
	public void cadastrar(Hospede hospede, int id) {
		this.hospedeDAO.cadastrar(hospede, id);
	}

	public List<Hospede> buscarPorSobrenome(String sobrenome) {
		
		return this.hospedeDAO.buscarPorSobrenome(sobrenome);
	}

	public void atualizar(String dado, String id, int coluna) {
		this.hospedeDAO.atualizar(dado, id, coluna);
		
	}
	
	public void excluirDados(String id, int coluna) {
		this.hospedeDAO.excluirDados(id, coluna);
	}
}
