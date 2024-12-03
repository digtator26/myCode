package com.ximert.electronicMoney;

import com.ximert.electronicMoney.models.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectronicMoneyApplication {

	public static void main(String[] args) {
		Bank.initialization();
		Clients.initialization();
		SpringApplication.run(ElectronicMoneyApplication.class, args);
	}

}
