package hello.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency getCurrentCurrencySelected(){
        return currencyRepository.findBySelected(true);
    }
}
