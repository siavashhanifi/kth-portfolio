<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.2.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	
	<groupId>hanifi.siavash.kth.id1212.hw4</groupId>
	<artifactId>currency-converter</artifactId>
	<version>0.0.1</version>
	<name>currency-converter</name>
	<description>project for hw4 in id1212</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
	
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		
		<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>5.4.10.Final</version>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.18</version>
		</dependency>
		
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

#content {
	align-content: center;
	background-color: white;
}

#convertFrom {
	border-color: black;
	border-width: 3px;
}

#convertTo {
	border-color: black;
	border-width: 3px;
}

#resultField {
	background-color: lightblue;
	border-color: black;
}

<!DOCTYPE HTML>
<html>
<head>
<title>Currency converter</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="css/styles.css"
	th:href="@{/css/styles.css}">
</head>
<body>
	<div id="content">
		<form method="get" action="/convertCurrency">
			<div id="convertFrom">
				<input type="number" step="0.001" name="amount" placeholder="amount"
					required />
				<p>Convert from:</p>
				<li><input type="radio" name="fromCurrency" value="SEK">
					SEK <br> <input type="radio" name="fromCurrency" value="USD">
					USD <br> <input type="radio" name="fromCurrency" value="JPY">
					JYP <br> <input type="radio" name="fromCurrency" value="AED">
					AED <br></li>
			</div>

			<div id="convertTo">
				<p>Convert to:</p>
				<li><input type="radio" name="toCurrency" value="SEK">
					SEK <br> <input type="radio" name="toCurrency" value="USD">
					USD <br> <input type="radio" name="toCurrency" value="JPY">
					JYP <br> <input type="radio" name="toCurrency" value="AED">
					AED <br></li>
			</div>
			<input type="submit" value="convert!">
		</form>

		<div id="resultField">
			<p id="conversionResult"
				th:text="'Conversion result:' + ' ' + ${amount} + ' ' + ${fromCurrency} + ' ' +'equals' + ' ' + ${conversionResult} + ' ' + ${toCurrency}"></p>
		</div>
	</div>
</body>
</html>

<!DOCTYPE HTML>
<html>
<head>
<title>Currency converter</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="css/styles.css" th:href="@{/css/styles.css}"> 
</head>
<body>
<div id="content">
	<form method="get" action="/convertCurrency">
		<input type="number" step="0.001" name="amount" placeholder="amount"
			required />
		<p> Convert from: </p><br>
			<input type="radio" name="fromCurrency" value="SEK"> SEK <br>
			<input type="radio" name="fromCurrency" value="USD"> USD <br>
			<input type="radio" name="fromCurrency" value="JPY"> JYP <br>
			<input type="radio" name="fromCurrency" value="AED"> AED <br>
		<p>Convert to: </p><br>
			<input type="radio" name="toCurrency" value="SEK"> SEK <br>
			<input type="radio" name="toCurrency" value="USD"> USD <br>
			<input type="radio" name="toCurrency" value="JPY"> JYP <br>
			<input type="radio" name="toCurrency" value="AED"> AED <br>
		<input type="submit" value="convert!">
	</form>
	<div id="resultField">
		<p id="conversionResult">Conversion result: </p>
	</div>
</div>
</body>
</html>
package hanifi.siavash.kth.id1212.hw4.currencyConverter.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hanifi.siavash.kth.id1212.hw4.currencyConverter.domain.CurrencyConverter;
import hanifi.siavash.kth.id1212.hw4.currencyConverter.repository.ConversionRateRepository;

/**
 * @author Siavash
 * Calls the appropriate methods in the domain to serve the client. 
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CurrencyConverterService {
	
	@Autowired
	private CurrencyConverter currencyConverter;
	
	public Float convertCurrency(Float amount, String fromCurrency, String toCurrency) {
		Float conversionResult = currencyConverter.convertCurrency(amount, fromCurrency, toCurrency);
		return conversionResult;
	}

}
package hanifi.siavash.kth.id1212.hw4.currencyConverter.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan("hanifi.siavash.kth.id1212.hw4.currencyConverter")
public class Config {
 
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	    dataSource.setUsername("root");
	    dataSource.setPassword("nikos");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/currencyconverterdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
	    return dataSource;
	}
	

}
package hanifi.siavash.kth.id1212.hw4.currencyConverter.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hanifi.siavash.kth.id1212.hw4.currencyConverter.entity.CurrencyConversionRate;
import hanifi.siavash.kth.id1212.hw4.currencyConverter.repository.ConversionRateRepository;

/**
 * @author Siavash
 * Handles the business logic for converting the currencies.
 */
@Component("currencyConverter")
public class CurrencyConverter {
	
	@Autowired
	private ConversionRateRepository conversionRateRepository;

	public Float convertCurrency(Float amount, String fromCurrency, String toCurrency) {
		Float convertedAmount; 
		Float amountInSEK;
		Optional<CurrencyConversionRate> conversionRate1 = this.conversionRateRepository.findById(fromCurrency);
		Optional<CurrencyConversionRate> conversionRate2 = this.conversionRateRepository.findById(toCurrency);
		amountInSEK = amount / conversionRate1.get().getSekRelativeConversionRate();
		convertedAmount = amountInSEK * conversionRate2.get().getSekRelativeConversionRate();
		return convertedAmount;
	}

}

package hanifi.siavash.kth.id1212.hw4.currencyConverter.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="conversion_rate")
public class CurrencyConversionRate {
	
	@Id
	private String currencyCode;
	private Float sekRelativeConversionRate;

	public Float getSekRelativeConversionRate() {
		return this.sekRelativeConversionRate;
	}
}
package hanifi.siavash.kth.id1212.hw4.currencyConverter.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hanifi.siavash.kth.id1212.hw4.currencyConverter.application.CurrencyConverterService;

/**
 * @author Siavash
 * Responsible for delegating client requests to the CurrencyConverterService and
 * presenting the correct response(html).
 */
@Controller
@Scope("session")
public class RequestController {
	
	@Autowired
	CurrencyConverterService currencyConverterService;

	/**
	 * Presents the index-page when "/" is requested by the client.
	 * @return index.html
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}

	
	/**
	 * @param amount the amount to be converted.
	 * @param fromCurrency the currency of the amount.
	 * @param toCurrency the currency which the amount is to be converted to.
	 * @param model a reference to the model which sets attributes for thymeleaf.
	 * @return convert-currency.html
	 */
	@GetMapping("/convertCurrency")
	public String convertCurrency(@RequestParam(name="amount", required=true, defaultValue="0.00") Float amount,
			@RequestParam(name="fromCurrency", required=true, defaultValue="SEK") String fromCurrency,
			@RequestParam(name="toCurrency", required=true, defaultValue="SEK") String toCurrency,Model model) {
		Float conversionResult = currencyConverterService.convertCurrency(amount, fromCurrency, toCurrency);
		model.addAttribute("amount", amount);
		model.addAttribute("fromCurrency", fromCurrency);
		model.addAttribute("toCurrency", toCurrency);
		model.addAttribute("conversionResult", conversionResult);
		return "convert-currency";
	}
	
	
}
package hanifi.siavash.kth.id1212.hw4.currencyConverter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}
package hanifi.siavash.kth.id1212.hw4.currencyConverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hanifi.siavash.kth.id1212.hw4.currencyConverter.entity.CurrencyConversionRate;


@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ConversionRateRepository extends JpaRepository<CurrencyConversionRate, String>{
	
}
