package controller;

import java.sql.Connection;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import model.Reserva;

public class ReservaController {
	
	private ReservaDAO reservaDAO;
	
	public ReservaController() {
		Connection connection = new ConnectionFactory().conectar();
		this.reservaDAO = new ReservaDAO(connection);
	}
	
	public void cadastrar(Reserva reserva) {
		this.reservaDAO.cadastrar(reserva);
	}
	

	public List<Reserva> buscarPorNumero(String id){
		return this.reservaDAO.buscarPorNumero(id);
	}

	public void atualizar(Reserva reservaAtualizada, String string) {
		this.reservaDAO.atualizar(reservaAtualizada, string);
		
	}
	
}
