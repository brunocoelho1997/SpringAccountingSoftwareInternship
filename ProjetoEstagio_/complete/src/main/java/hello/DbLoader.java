package hello;

import hello.Adress.Adress;
import hello.Client.Client;
import hello.Client.ClientRepository;
import hello.Contact.Contact;
import hello.CostCenter.CostCenter;
import hello.CostCenter.CostCenterRepository;
import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;
import hello.Enums.Frequency;
import hello.Enums.Genre;
import hello.Enums.Category;
import hello.PostContact.PostContact;
import hello.PostContact.PostContactRepository;
import hello.PostEmployee.PostEmployee;
import hello.PostEmployee.PostEmployeeRepository;
import hello.Project.Project;
import hello.Project.ProjectRepository;
import hello.ProjectTransaction.ProjectTransaction;
import hello.ProjectTransaction.ProjectTransactionRepository;
import hello.SubType.SubType;
import hello.SubType.SubTypeRepository;
import hello.Type.Type;
import hello.Type.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DbLoader implements CommandLineRunner {

    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private SubTypeRepository subTypeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTransactionRepository projectTransactionRepository;

    @Autowired
    private PostContactRepository postContactRepository;

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Autowired
    private PostEmployeeRepository postEmployeeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public void run(String... args) throws Exception {

        if(typeRepository.findAll().isEmpty() && subTypeRepository.findAll().isEmpty())
        {
//         create subTypes
            SubType subType1 = new SubType();
            subType1.setName("Base dados");

            SubType subType2 = new SubType();
            subType2.setName("Estruturação");


//      add types
            Type type1 = new Type();
            type1.setCategory(Category.PROJECTS);
            type1.setName("Manutenção");
            type1.setSubTypeList(new ArrayList<>());
            type1 = typeRepository.save(type1);

            Type type2 = new Type();
            type2.setCategory(Category.PROJECTS);
            type2.setName("Desenvolvimento");
            type2.setSubTypeList(new ArrayList<>());
            type2 = typeRepository.save(type2);

//            associate types with subtypes
            type1.getSubTypeList().add(subType1);
            type1.getSubTypeList().add(subType2);


            typeRepository.save(type1);
            typeRepository.save(type2);

//            create adress
            Adress adress1 = new Adress();
            adress1.setCity("Coimbra");
            adress1.setZipCode("3220-113");
            adress1.setNumber(23);
            adress1.setAdressName("Avenida Padre Américo");

            Adress adress2 = new Adress();
            adress2.setCity("Lisboa");
            adress2.setZipCode("1220-143");
            adress2.setNumber(61);
            adress2.setAdressName("Rua Ali e Acolá");

            Adress adress3 = new Adress();
            adress3.setCity("Viseu");
            adress3.setZipCode("6220-143");
            adress3.setNumber(12);
            adress3.setAdressName("Rua Aqui E Ali");

            Adress adress4 = new Adress();
            adress4.setCity("Aveiro");
            adress4.setZipCode("1250-143");
            adress4.setNumber(123);
            adress4.setAdressName("Rua XPTO");

            Adress adress5 = new Adress();
            adress5.setCity("Porto");
            adress5.setZipCode("5550-143");
            adress5.setNumber(523);
            adress5.setAdressName("Rua Do Porto");



//            create posts of Contacts

            PostContact postContact1 = new PostContact();
            postContact1.setName("Revendedor");
            PostContact postContact2 = new PostContact();
            postContact2.setName("Gestor de Projetos");
            PostContact postContact3 = new PostContact();
            postContact3.setName("Gerente");

            postContact1 = postContactRepository.save(postContact1);
            postContact2 = postContactRepository.save(postContact2);
            postContact3 = postContactRepository.save(postContact3);



//            create contacts
            Contact contact1 = new Contact();
            contact1.setEmail("contato1@isec.pt");
            contact1.setNumberPhone("239345542");
            contact1.setName("Contato1");
            contact1.setAdresses(new ArrayList<>());
            contact1.getAdresses().add(adress1);
            contact1.setPostContact(postContact1);

            Contact contact2 = new Contact();
            contact2.setEmail("contato2@isec.pt");
            contact2.setNumberPhone("239345542");
            contact2.setName("Contato2");
            contact2.setAdresses(new ArrayList<>());
            contact2.getAdresses().add(adress2);
            contact2.setPostContact(postContact2);


            Contact contact3 = new Contact();
            contact3.setEmail("contato3@isec.pt");
            contact3.setNumberPhone("239345542");
            contact3.setName("Contato3");
            contact3.setAdresses(new ArrayList<>());
            contact3.getAdresses().add(adress3);
            contact3.setPostContact(postContact3);

//            create clients
            Client client1 = new Client();
            client1.setRegistrationCode("CODEClient1");
            client1.setNumberPhone("234432234");
            client1.setName("Cliente1");
            client1.setAdresses(new ArrayList<>());
            client1.setContacts(new ArrayList<>());
            client1.getAdresses().add(adress4);
            client1.getContacts().add(contact3);

            Client client2 = new Client();
            client2.setRegistrationCode("CODEClient2");
            client2.setNumberPhone("234432234");
            client2.setName("Cliente2");
            client2.setAdresses(new ArrayList<>());
            client2.setContacts(new ArrayList<>());
            client2.getAdresses().add(adress5);
            client2.getContacts().add(contact1);
            client2.getContacts().add(contact2);


            client1 = clientRepository.save(client1);
            client2 = clientRepository.save(client2);


//            create Cost Centers

            CostCenter costCenter1 = new CostCenter();
            costCenter1.setName("Centro de Custos 1");
            costCenter1.setDescription("Descrição");
            CostCenter costCenter2 = new CostCenter();
            costCenter2.setName("Centro de Custos 2");
            costCenter2.setDescription("Descrição");

            costCenter1 = costCenterRepository.save(costCenter1);
            costCenter2 = costCenterRepository.save(costCenter2);

//            create projects

            Project project1 = new Project();
            project1.setClient(client1);
            project1.setContact(contact1);
            project1.setDescription("Descrição1");
            project1.setName("Projecto1");
            long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate1 = LocalDate.ofEpochDay(randomDay);
            LocalDate randomDate2 = LocalDate.ofEpochDay(randomDay);
            project1.setInitialDate(randomDate1);
            project1.setFinalDate(randomDate2);
            project1.setScope("Scope1");
            project1.setBalance(0);
            project1.setCostCenter(costCenter1);

            project1 = projectRepository.save(project1);




            ProjectTransaction projectTransaction1 = new ProjectTransaction();
            projectTransaction1.setDate(randomDate1);
            projectTransaction1.setName("Receita de Projeto 1");
            projectTransaction1.setFrequency(Frequency.DAILY);
            projectTransaction1.setValue((float)20.3);
            projectTransaction1.setProject(project1);
            projectTransaction1.setGenre(Genre.REVENUE);
            projectTransaction1.setType(type2);

            ProjectTransaction projectTransaction2 = new ProjectTransaction();
            projectTransaction2.setDate(randomDate1);
            projectTransaction2.setName("Despesa de Projeto 1");
            projectTransaction2.setFrequency(Frequency.MONTHLY);
            projectTransaction2.setValue((float)135.3);
            projectTransaction2.setProject(project1);
            projectTransaction2.setGenre(Genre.COST);
            projectTransaction2.setType(type2);

            projectTransactionRepository.save(projectTransaction1);
            projectTransactionRepository.save(projectTransaction2);



//            add posts employess
            PostEmployee postEmployee1 = new PostEmployee();
            postEmployee1.setName("Programador - beckend");
            PostEmployee postEmployee2 = new PostEmployee();
            postEmployee2.setName("Gerente");
            PostEmployee postEmployee3 = new PostEmployee();
            postEmployee3.setName("Programador - frontend");

            postEmployeeRepository.save(postEmployee1);
            postEmployeeRepository.save(postEmployee2);
            postEmployeeRepository.save(postEmployee3);

//            add employee
            Employee employee1 = new Employee();
            employee1.setActived(true);

            Adress adress6 = new Adress();
            adress6.setCity("Faro");
            adress6.setZipCode("5550-143");
            adress6.setNumber(3);
            adress6.setAdressName("Rua De Faro");

            employee1.setAdresses(new ArrayList<>());
            employee1.getAdresses().add(adress6);
            employee1.setNumberPhone("939898345");
            employee1.setEmail("funcionario1@isec.pt");
            employee1.setName("Funcionário1");
            employee1.setPostEmployee(postEmployee1);

            Employee employee2 = new Employee();
            employee2.setActived(true);
            employee2.setPostEmployee(postEmployee3);

            Adress adress7 = new Adress();
            adress7.setCity("Portimão");
            adress7.setZipCode("2550-143");
            adress7.setNumber(300);
            adress7.setAdressName("Rua De Portimão");

            employee2.setAdresses(new ArrayList<>());
            employee2.getAdresses().add(adress7);
            employee2.setNumberPhone("939898345");
            employee2.setEmail("funcionario1@isec.pt");
            employee2.setName("Funcionário2");


            employeeRepository.save(employee1);
            employeeRepository.save(employee2);

        }
    }
}