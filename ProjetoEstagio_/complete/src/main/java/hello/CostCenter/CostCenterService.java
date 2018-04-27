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

    public CostCenter getCostCenter(Long id) {
        return repository.findById((long)id);
    }
}
