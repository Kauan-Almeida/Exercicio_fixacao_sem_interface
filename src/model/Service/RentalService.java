package model.Service;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;

	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	public void processInvoice(CarRental carRental) {
		//pegar o pre�o arredondado e multiplicar pelo pre�o de dia ou hora
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1) / 1000 / 60 / 60;
		
		double basicPayment;
		if(hours <= 12) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
		}
		//calcular o imposto:
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
