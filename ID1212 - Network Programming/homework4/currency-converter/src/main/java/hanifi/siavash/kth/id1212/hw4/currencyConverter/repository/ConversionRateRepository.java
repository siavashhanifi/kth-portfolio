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