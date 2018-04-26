package hello.CostCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CostCenterService {

    @Autowired
    private CostCenterRepository repository;

    public List<CostCenter> getCostsCenter() {
        return repository.findAll();
    }

    //method private! If you need a client use method getClient(long id);
    private CostCenter getOne(Long id) {

        try
        {
            if(id == null)
                throw new EntityNotFoundException();

            CostCenter c = repository.getOne(id);

            //            TODO: isto deveria funcionar como funciona o cliente... no entanto tive de fazer assim porque a exception nao accionava
            System.out.print(c);

            return c;
        }catch (EntityNotFoundException ex)
        {
            //if can't getClient
            return null;
        }
    }

    public CostCenter getCostCenter(Long id) {
        try
        {

            return getOne(id);

        }catch (EntityNotFoundException ex)
        {
            //if can't getClient
            return null;
        }
    }
}
