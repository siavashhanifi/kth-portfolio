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
