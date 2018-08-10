package hello.ComissionTransaction;

import hello.Client.Client;
import hello.Client.ClientService;
import hello.Employee.EmployeeService;
import hello.EmployeeTransaction.EmployeeTransaction;
import hello.Enums.Category;
import hello.Enums.Genre;
import hello.Project.Project;
import hello.Project.ProjectService;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Type.Type;
import hello.Type.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ComissionTransactionService {

    @Autowired
    ComissionTransactionRepository repository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    SubTypeService subTypeService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ProjectService projectService;
    @Autowired
    ClientService clientService;

    public Page<ComissionTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long projectId, Long clientId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {


        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || subTypeValue != null || projectId != null|| clientId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null || deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, projectId, clientId, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);
        else
            return repository.findAllByGenreAndExecutedAndActived(pageable, genre, executed, true);
    }

    private Page<ComissionTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long projectId, Long clientId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {
        Page<ComissionTransaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty()&& subTypeValue.isEmpty() && projectId == 0&& clientId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty() && deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActived(pageable, genre, executed, true);


        Project project= projectService.getProject(projectId);
        Client client = clientService.getClient(clientId);



        Specification<ComissionTransaction> specFilter = null;



        if(value.isEmpty() && frequency.isEmpty() && projectId == 0 && clientId== 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            ;
        else
        {
            if(specFilter==null)
                specFilter = ComissionTransactionSpecifications.filter(value, frequency, project,client, dateSince, dateUntil,valueSince, valueUntil, genre);
            else
                specFilter.and(ComissionTransactionSpecifications.filter(value, frequency, project,client, dateSince, dateUntil,valueSince, valueUntil, genre));

        }

        //types and subtypes
        List<SubType> subTypeList = null;
        if(!subTypeValue.isEmpty()){
            subTypeList = subTypeService.getSubType(subTypeValue);
//            System.out.println("\n\n\n\n subTypeList: " + subTypeList);
        }
        if(subTypeValue!= null && !subTypeValue.isEmpty())
        {
            List<Type> types = typeRepository.findByName(typeValue);

            if(!types.isEmpty())
            {
                for(Type type1: types)
                {
                    if(!type1.isManuallyCreated() && !Collections.disjoint(type1.getSubTypeList(), subTypeList))
                    {
//                        System.out.println("\n\n Type: " + type1);

                        if(specFilter==null)
                            specFilter = ComissionTransactionSpecifications.filterByType(type1);
                        else
                            specFilter = specFilter.or(ComissionTransactionSpecifications.filterByType(type1));
                    }
                }
            }
        }
        else
        {
            if(specFilter==null)
                specFilter = ComissionTransactionSpecifications.filterByNameType(typeValue);
            else
                specFilter = specFilter.or(ComissionTransactionSpecifications.filterByNameType(typeValue));
        }


        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = ComissionTransactionSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(ComissionTransactionSpecifications.filterDeleletedEntities(deletedEntities));


        //executed
        if(specFilter==null)
            specFilter = ComissionTransactionSpecifications.filterExecuted(true);
        else
            specFilter = specFilter.and(ComissionTransactionSpecifications.filterExecuted(true));


        transactionsPage = repository.findAll(specFilter, pageable);

        return transactionsPage;
    }

    public void addTransaction(@Valid ComissionTransaction transaction) {

        Client client = clientService.getClient(transaction.getClient().getId());
        Project project = projectService.getProject(transaction.getProject().getId());


        transaction.setProject(project);
        transaction.setClient(client);


        Type typeAux = new Type(transaction.getType().getName());
        typeAux.setCategory(Category.SUPPLIERS);
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : transaction.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);

        transaction.setType(typeAux);
        transaction.setExecuted(true);

        repository.save(transaction);
    }

    public ComissionTransaction getComissionTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editTransaction(@Valid ComissionTransaction editeComissionTransaction) {
        ComissionTransaction comissionTransaction= getComissionTransaction((long)editeComissionTransaction.getId());

        comissionTransaction.setGenre(editeComissionTransaction.getGenre());

        Type type = comissionTransaction.getType();
        type.setName(editeComissionTransaction.getType().getName());
        type.setSubTypeList(editeComissionTransaction.getType().getSubTypeList());

        comissionTransaction.setDate(editeComissionTransaction.getDate());
        comissionTransaction.setValue(editeComissionTransaction.getValue());
        comissionTransaction.setFrequency(editeComissionTransaction.getFrequency());
        comissionTransaction.setDescription(editeComissionTransaction.getDescription());
        comissionTransaction.setName(editeComissionTransaction.getName());

        Project project = projectService.getProject(editeComissionTransaction.getProject().getId());
        Client client = clientService.getClient(editeComissionTransaction.getClient().getId());

        comissionTransaction.setProject(project);
        comissionTransaction.setClient(client);

        repository.save(comissionTransaction);
    }

    public void removeTransaction(Long id) {
        ComissionTransaction transaction= getComissionTransaction((long)id);
        transaction.setActived(false);
        repository.save(transaction);
    }
    public void recoveryTransaction(Long id) {
        ComissionTransaction transaction = getComissionTransaction((long)id);
        transaction.setActived(true);
        repository.save(transaction);
    }
}
