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

	public void atualizar(Hospede hospede, String id) {
		this.hospedeDAO.atualizar(hospede, id);
		
	}
	
//	public List<Hospede> buscar(){
//		return this.hospedeDAO.buscar();
//	}
//	
//	public List<Hospede> buscarPorId(String id){
//		return this.hospedeDAO.buscarPorId(id);
//	}
//	
//	public void deletar(Integer id) {
//		this.hospedeDAO.deletar(id);
//	}
}
