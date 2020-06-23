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