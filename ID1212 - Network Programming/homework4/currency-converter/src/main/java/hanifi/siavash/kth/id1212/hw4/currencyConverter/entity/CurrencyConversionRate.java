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