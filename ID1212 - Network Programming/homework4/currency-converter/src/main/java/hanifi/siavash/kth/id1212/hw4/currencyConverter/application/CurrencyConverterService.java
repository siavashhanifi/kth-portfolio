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
