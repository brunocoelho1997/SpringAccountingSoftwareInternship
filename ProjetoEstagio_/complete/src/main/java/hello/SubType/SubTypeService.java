package hello.SubType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SubTypeService {

    @Autowired
    SubTypeRepository subTypeRepository;

    public void addSubType(SubType subType) {
        subTypeRepository.save(subType);
    }

    public SubType getSubType(Long id) {
        return subTypeRepository.findById((long)id);
    }
}
