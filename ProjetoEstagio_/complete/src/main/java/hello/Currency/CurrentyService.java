package hello.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentyService {

    @Autowired
    private CurrencyRepository currencyRepository;

//    public Currency getCurrentCurrency(){
//        return currencyRepository.findBySelected(true);
//    }
}
