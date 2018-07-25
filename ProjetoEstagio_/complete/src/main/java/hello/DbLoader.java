package hello;

import hello.Adress.Adress;
import hello.Client.Client;
import hello.Client.ClientRepository;
import hello.Contact.Contact;
import hello.CostCenter.CostCenter;
import hello.CostCenter.CostCenterRepository;
import hello.Currency.Currency;
import hello.Currency.CurrencyRepository;
import hello.Employee.Employee;
import hello.Employee.EmployeeRepository;
import hello.EmployeeTransaction.EmployeeTransaction;
import hello.EmployeeTransaction.EmployeeTransactionRepository;
import hello.Enums.Frequency;
import hello.Enums.Genre;
import hello.Enums.Category;
import hello.GeneralTransaction.GeneralTransaction;
import hello.GeneralTransaction.GeneralTransactionRepository;
import hello.PostContact.PostContact;
import hello.PostContact.PostContactRepository;
import hello.PostEmployee.PostEmployee;
import hello.PostEmployee.PostEmployeeRepository;
import hello.Project.Project;
import hello.Project.ProjectRepository;
import hello.ProjectTransaction.ProjectTransaction;
import hello.ProjectTransaction.ProjectTransactionRepository;
import hello.SaleTransaction.SaleTransaction;
import hello.SaleTransaction.SaleTransactionRepository;
import hello.SheetTransaction.Resources.HoursPerProject;
import hello.SheetTransaction.SheetTransaction;
import hello.SheetTransaction.SheetTransactionRepository;
import hello.SubType.SubType;
import hello.SubType.SubTypeRepository;
import hello.Supplier.Resources.StringContact;
import hello.Supplier.Supplier;
import hello.Supplier.SupplierRepository;
import hello.Type.Type;
import hello.Type.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EmployeeTransactionRepository employeeTransactionRepository;

    @Autowired
    private SaleTransactionRepository saleTransactionRepository;

    @Autowired
    private GeneralTransactionRepository generalTransactionRepository;

    @Autowired
    private SheetTransactionRepository sheetTransactionRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) throws Exception {

        if(typeRepository.findAll().isEmpty() && subTypeRepository.findAll().isEmpty())
        {
//         create subTypes
            SubType subType1 = new SubType();
            subType1.setName("Base dados");

            SubType subType2 = new SubType();
            subType2.setName("Estruturação");

            SubType subType3 = new SubType();
            subType3.setName("Base dados");


            subTypeRepository.save(subType1);
            subTypeRepository.save(subType2);
            subTypeRepository.save(subType3);


//      add types
            Type type1 = new Type();
            type1.setCategory(Category.PROJECTS);
            type1.setName("Manutenção");
            type1 = typeRepository.save(type1);

            Type type2 = new Type();
            type2.setCategory(Category.PROJECTS);
            type2.setName("Desenvolvimento");
            type2 = typeRepository.save(type2);

            Type type3 = new Type();
            type3.setCategory(Category.SHEET);
            type3.setName("Salário");
            type3 = typeRepository.save(type3);


//            associate types with subtypes
            subType1.setType(type1);
            subType2.setType(type2);
            subType3.setType(type2);


            subTypeRepository.save(subType1);
            subTypeRepository.save(subType2);
            subTypeRepository.save(subType3);


            typeRepository.save(type1);
            typeRepository.save(type3);
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
            LocalDate date3 = LocalDate.of(2018,2,12);
            LocalDate date4 = LocalDate.of(2018,1,23);
            LocalDate date5 = LocalDate.now();


            project1.setInitialDate(randomDate1);
            project1.setFinalDate(randomDate2);
            project1.setScope("Scope1");
            project1.setBalance(0);
            project1.setCostCenter(costCenter1);

            project1 = projectRepository.save(project1);

            Currency brlCurrency = new Currency();
            brlCurrency.setName("BRL");
            brlCurrency.setSymbol("R$");
            brlCurrency.setSelected(true);
            currencyRepository.save(brlCurrency);

            ProjectTransaction projectTransaction1 = new ProjectTransaction();
            projectTransaction1.setDate(date3);
            projectTransaction1.setName("Receita de Projeto 1");
            projectTransaction1.setFrequency(Frequency.DAILY);
            projectTransaction1.setValue((float)20.3);
            projectTransaction1.setProject(project1);
            projectTransaction1.setGenre(Genre.REVENUE);
            projectTransaction1.setType(type2);
            projectTransaction1.setExecuted(true);
            projectTransaction1.setCurrency(brlCurrency);

            ProjectTransaction projectTransaction2 = new ProjectTransaction();
            projectTransaction2.setDate(randomDate1);
            projectTransaction2.setName("Despesa de Projeto 2");
            projectTransaction2.setFrequency(Frequency.MONTHLY);
            projectTransaction2.setValue((float)135.3);
            projectTransaction2.setProject(project1);
            projectTransaction2.setGenre(Genre.COST);
            projectTransaction2.setType(type2);
            projectTransaction2.setExecuted(true);
            projectTransaction2.setCurrency(brlCurrency);

            ProjectTransaction projectTransaction3 = new ProjectTransaction();
            projectTransaction3.setDate(date4);
            projectTransaction3.setName("Despesa de Projeto 3");
            projectTransaction3.setFrequency(Frequency.DAILY);
            projectTransaction3.setValue((float)35.1);
            projectTransaction3.setProject(project1);
            projectTransaction3.setGenre(Genre.COST);
            projectTransaction3.setType(type1);
            projectTransaction3.setExecuted(true);
            projectTransaction3.setCurrency(brlCurrency);

//            projectTransaction3.getType().setSubType(subType1);

            ProjectTransaction projectTransaction4 = new ProjectTransaction();
            projectTransaction4.setDate(randomDate1);
            projectTransaction4.setName("Receita de Projeto 4");
            projectTransaction4.setFrequency(Frequency.DAILY);
            projectTransaction4.setValue((float)261.9);
            projectTransaction4.setProject(project1);
            projectTransaction4.setGenre(Genre.REVENUE);
            projectTransaction4.setType(type1);
            projectTransaction4.setExecuted(true);
            projectTransaction4.setCurrency(brlCurrency);

//            projectTransaction4.getType().setSubType(subType2);

            ProjectTransaction projectTransaction5 = new ProjectTransaction();
            projectTransaction5.setDate(randomDate1);
            projectTransaction5.setName("Despesa de Projeto 5");
            projectTransaction5.setFrequency(Frequency.DAILY);
            projectTransaction5.setValue((float)23.2);
            projectTransaction5.setProject(project1);
            projectTransaction5.setGenre(Genre.COST);
            projectTransaction5.setType(type1);
            projectTransaction5.setExecuted(true);
            projectTransaction5.setCurrency(brlCurrency);

//            projectTransaction5.getType().setSubType(subType2);



            ProjectTransaction projectTransaction6 = new ProjectTransaction();
            projectTransaction6.setDate(date4);
            projectTransaction6.setName("Receita de Projeto 6");
            projectTransaction6.setFrequency(Frequency.DAILY);
            projectTransaction6.setValue((float)12.3);
            projectTransaction6.setProject(project1);
            projectTransaction6.setGenre(Genre.REVENUE);
            projectTransaction6.setType(type2);
            projectTransaction6.setExecuted(true);
            projectTransaction6.setCurrency(brlCurrency);

            projectTransactionRepository.save(projectTransaction1);
            projectTransactionRepository.save(projectTransaction2);
            projectTransactionRepository.save(projectTransaction3);
            projectTransactionRepository.save(projectTransaction4);
            projectTransactionRepository.save(projectTransaction5);
            projectTransactionRepository.save(projectTransaction6);




//            add posts employess
            PostEmployee postEmployee1 = new PostEmployee();
            postEmployee1.setName("Programador - backend");
            PostEmployee postEmployee2 = new PostEmployee();
            postEmployee2.setName("Gerente");
            PostEmployee postEmployee3 = new PostEmployee();
            postEmployee3.setName("Programador - frontend");

            postEmployeeRepository.save(postEmployee1);
            postEmployeeRepository.save(postEmployee2);
            postEmployeeRepository.save(postEmployee3);

//            add employee
            Employee employee1 = new Employee();

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


//            add supplier
            Supplier supplier1 = new Supplier();

            Adress adress8 = new Adress();
            adress8.setCity("Faro");
            adress8.setZipCode("5550-143");
            adress8.setNumber(3);
            adress8.setAdressName("Rua De Faro");

            StringContact stringContact1 = new StringContact();
            stringContact1.setContact("912345654");

            StringContact stringContact2 = new StringContact();
            stringContact2.setContact("fornecedor1@isec.pt");


            supplier1.setName("Fornecedor1");

            supplier1.setContacts(new ArrayList<>());
            supplier1.getContacts().add(stringContact1);
            supplier1.getContacts().add(stringContact2);

            supplier1.setAdresses(new ArrayList<>());
            supplier1.getAdresses().add(adress8);


//another
            Adress adress9 = new Adress();
            adress9.setCity("Óbidos");
            adress9.setZipCode("5550-143");
            adress9.setNumber(3);
            adress9.setAdressName("Rua De Faro");

            StringContact stringContact3 = new StringContact();
            stringContact3.setContact("234432345");

            Supplier supplier2 = new Supplier();
            supplier2.setAdresses(new ArrayList<>());
            supplier2.getAdresses().add(adress9);
            supplier2.setName("Fornecedor2");
            supplier2.setContacts(new ArrayList<>());
            supplier2.getContacts().add(stringContact3);



            supplierRepository.save(supplier2);
            supplierRepository.save(supplier1);

//            about empployee transactiosn
            EmployeeTransaction employeeTransaction1 = new EmployeeTransaction();
            employeeTransaction1.setDate(randomDate1);
            employeeTransaction1.setName("Despesa de Funcionário 1");
            employeeTransaction1.setFrequency(Frequency.DAILY);
            employeeTransaction1.setValue((float)90.3);
            employeeTransaction1.setEmployee(employee1);
            employeeTransaction1.setGenre(Genre.COST);
            employeeTransaction1.setType(type2);
            employeeTransaction1.setExecuted(true);
            employeeTransaction1.setCurrency(brlCurrency);






            EmployeeTransaction employeeTransaction2 = new EmployeeTransaction();
            employeeTransaction2.setDate(date5);
            employeeTransaction2.setName("Despesa de Funcionário 2");
            employeeTransaction2.setFrequency(Frequency.DAILY);
            employeeTransaction2.setValue((float)40.3);
            employeeTransaction2.setEmployee(employee1);
            employeeTransaction2.setGenre(Genre.COST);
            employeeTransaction2.setType(type1);
            employeeTransaction2.setExecuted(true);
            employeeTransaction2.setCurrency(brlCurrency);

//            employeeTransaction2.getType().setSubType(subType1);

            employeeTransactionRepository.save(employeeTransaction1);
            employeeTransactionRepository.save(employeeTransaction2);




            /*
            OU SEJa:
            tipos tem uma lista de subtipos....
            subtipos tem a identificacao dos tipos...

            quando criamos subtipos definimos logo qual o seu subtipo...
            só criamos um tipo quando criarmos uma transacao e aí definimos os tipos que queremos...


            quando for para criar uma transacao:
            X -mandamos todos os nomes possiveis de tipos (apenas nomes, getDistinctTypeNames() )
            Y -quando for para definir o seu subtipo vamos buscar todos os nomes do subtipos (apenas q tenham aquele tipo)

            por fim:
            criar um novo tipo com nome X e a lista de subtipos selecionados

             */
            Type type4 = new Type();
            type4.setName("Desenvolvimento");
            type4.setCategory(Category.PROJECTS);
            typeRepository.save(type4);

            type4.setSubTypeList(new ArrayList<>());
            type4.getSubTypeList().add(subType2);
            typeRepository.save(type4);

            EmployeeTransaction employeeTransaction3 = new EmployeeTransaction();
            employeeTransaction3.setDate(date3);
            employeeTransaction3.setName("Despesa de Funcionário 3");
            employeeTransaction3.setFrequency(Frequency.DAILY);
            employeeTransaction3.setValue((float)28.3);
            employeeTransaction3.setEmployee(employee2);
            employeeTransaction3.setGenre(Genre.COST);
            employeeTransaction3.setType(type4);
            employeeTransaction3.setExecuted(true);
            employeeTransaction3.setCurrency(brlCurrency);
            employeeTransactionRepository.save(employeeTransaction3);


            Type type5 = new Type();
            type5.setName("Desenvolvimento");
            type5.setCategory(Category.PROJECTS);
            typeRepository.save(type5);

            type5.setSubTypeList(new ArrayList<>());
            type5.getSubTypeList().add(subType2);
            type5.getSubTypeList().add(subType3);
            typeRepository.save(type5);

            EmployeeTransaction employeeTransaction6 = new EmployeeTransaction();
            employeeTransaction6.setDate(date3);
            employeeTransaction6.setName("Despesa de Funcionário 6");
            employeeTransaction6.setFrequency(Frequency.DAILY);
            employeeTransaction6.setValue((float)28.3);
            employeeTransaction6.setEmployee(employee2);
            employeeTransaction6.setGenre(Genre.COST);
            employeeTransaction6.setType(type5);
            employeeTransaction6.setExecuted(true);
            employeeTransaction6.setCurrency(brlCurrency);
            employeeTransactionRepository.save(employeeTransaction6);

//            about sale transactiosn
            SaleTransaction saleTransaction1 = new SaleTransaction();
            saleTransaction1.setDate(date5);
            saleTransaction1.setName("Receita de Venda 1");
            saleTransaction1.setFrequency(Frequency.DAILY);
            saleTransaction1.setValue((float)20.3);
            saleTransaction1.setGenre(Genre.REVENUE);
            saleTransaction1.setType(type2);
            saleTransaction1.setExecuted(true);
            saleTransaction1.setCurrency(brlCurrency);


            SaleTransaction saleTransaction2 = new SaleTransaction();
            saleTransaction2.setDate(randomDate1);
            saleTransaction2.setName("Receita de Venda 2");
            saleTransaction2.setFrequency(Frequency.MONTHLY);
            saleTransaction2.setValue((float)120.3);
            saleTransaction2.setGenre(Genre.REVENUE);
            saleTransaction2.setType(type1);
            saleTransaction2.setExecuted(true);
            saleTransaction2.setCurrency(brlCurrency);

//            saleTransaction2.getType().setSubType(subType2);

            SaleTransaction saleTransaction3 = new SaleTransaction();
            saleTransaction3.setDate(randomDate1);
            saleTransaction3.setName("Receita de Venda 3");
            saleTransaction3.setFrequency(Frequency.DAILY);
            saleTransaction3.setValue((float)10.1);
            saleTransaction3.setGenre(Genre.REVENUE);
            saleTransaction3.setType(type1);
            saleTransaction3.setExecuted(true);
            saleTransaction3.setCurrency(brlCurrency);


            saleTransactionRepository.save(saleTransaction1);
            saleTransactionRepository.save(saleTransaction2);
            saleTransactionRepository.save(saleTransaction3);


//            about general transactiosn
            GeneralTransaction generalTransaction1 = new GeneralTransaction();
            generalTransaction1.setDate(randomDate1);
            generalTransaction1.setName("Despesa Geral 1");
            generalTransaction1.setFrequency(Frequency.DAILY);
            generalTransaction1.setValue((float)6.3);
            generalTransaction1.setGenre(Genre.COST);
            generalTransaction1.setType(type2);
            generalTransaction1.setExecuted(true);
            generalTransaction1.setCurrency(brlCurrency);

            GeneralTransaction generalTransaction2 = new GeneralTransaction();
            generalTransaction2.setDate(date3);
            generalTransaction2.setName("Despesa Geral 2");
            generalTransaction2.setFrequency(Frequency.MONTHLY);
            generalTransaction2.setValue((float)120.3);
            generalTransaction2.setGenre(Genre.COST);
            generalTransaction2.setType(type1);
            generalTransaction2.setExecuted(true);
            generalTransaction2.setCurrency(brlCurrency);

//            generalTransaction2.getType().setSubType(subType2);

            GeneralTransaction generalTransaction3 = new GeneralTransaction();
            generalTransaction3.setDate(randomDate1);
            generalTransaction3.setName("Despesa Geral 3");
            generalTransaction3.setFrequency(Frequency.DAILY);
            generalTransaction3.setValue((float)10.1);
            generalTransaction3.setGenre(Genre.COST);
            generalTransaction3.setType(type1);
            generalTransaction3.setExecuted(true);
            generalTransaction3.setCurrency(brlCurrency);

            generalTransactionRepository.save(generalTransaction1);
            generalTransactionRepository.save(generalTransaction2);
            generalTransactionRepository.save(generalTransaction3);

            //sheet transactions...

            SheetTransaction sheetTransaction1 = new SheetTransaction();
            sheetTransaction1.setDate(date5);
            sheetTransaction1.setName("Despesa de Folha 1");
            sheetTransaction1.setFrequency(Frequency.DAILY);
            sheetTransaction1.setValue((float)17.1);
            sheetTransaction1.setGenre(Genre.COST);
            sheetTransaction1.setType(type1);
            sheetTransaction1.setEmployee(employee1);
            sheetTransaction1.setExecuted(true);
            sheetTransaction1.setCurrency(brlCurrency);


            SheetTransaction sheetTransaction2 = new SheetTransaction();
            sheetTransaction2.setDate(date4);
            sheetTransaction2.setName("Despesa de Folha 2");
            sheetTransaction2.setFrequency(Frequency.DAILY);
            sheetTransaction2.setValue((float)10.1);
            sheetTransaction2.setGenre(Genre.COST);
            sheetTransaction2.setType(type1);
            sheetTransaction2.setExecuted(true);
            sheetTransaction2.setCurrency(brlCurrency);

//            sheetTransaction2.getType().setSubType(subType1);


            SheetTransaction sheetTransaction3 = new SheetTransaction();
            sheetTransaction3.setDate(randomDate1);
            sheetTransaction3.setName("Despesa de Folha 3");
            sheetTransaction3.setFrequency(Frequency.DAILY);
            sheetTransaction3.setValue((float)120.1);
            sheetTransaction3.setGenre(Genre.COST);
            sheetTransaction3.setType(type1);
//            sheetTransaction3.getType().setSubType(subType1);
            sheetTransaction3.setEmployee(employee1);
            sheetTransaction3.setExecuted(true);
            sheetTransaction3.setCurrency(brlCurrency);

            sheetTransaction3.setHoursPerProjectList(new ArrayList<>());

            HoursPerProject hoursPerProject1 = new HoursPerProject();
            hoursPerProject1.setDuration((float)2.3);
            hoursPerProject1.setProject(project1);
            sheetTransaction3.getHoursPerProjectList().add(hoursPerProject1);



            SheetTransaction sheetTransaction4 = new SheetTransaction();
            sheetTransaction4.setDate(date4);
            sheetTransaction4.setName("Despesa Salarial 1");
            sheetTransaction4.setFrequency(Frequency.MONTHLY);
            sheetTransaction4.setValue((float)10.1);
            sheetTransaction4.setGenre(Genre.COST);
            sheetTransaction4.setType(type1);
            sheetTransaction4.setExecuted(false);
            sheetTransaction4.setCurrency(brlCurrency);
            sheetTransaction4.setInstallments(10);
            sheetTransaction4.setEmployee(employee1);
            sheetTransaction4.setHoursPerProjectList(new ArrayList<>());

            HoursPerProject hoursPerProject2 = new HoursPerProject();
            hoursPerProject2.setDuration((float)5.5);
            hoursPerProject2.setProject(project1);
            sheetTransaction4.getHoursPerProjectList().add(hoursPerProject2);


            SheetTransaction sheetTransaction5 = new SheetTransaction();
            sheetTransaction5.setDate(date4);
            sheetTransaction5.setName("Despesa Premio Outubro");
            sheetTransaction5.setFrequency(Frequency.SEMESTER);
            sheetTransaction5.setValue((float)12.1);
            sheetTransaction5.setGenre(Genre.COST);
            sheetTransaction5.setType(type2);
            sheetTransaction5.setExecuted(false);
            sheetTransaction5.setCurrency(brlCurrency);
            sheetTransaction5.setInstallments(1);
            sheetTransaction5.setEmployee(employee1);

            SheetTransaction sheetTransaction6 = new SheetTransaction();
            sheetTransaction6.setDate(date4);
            sheetTransaction6.setName("Despesa Salarial 2");
            sheetTransaction6.setFrequency(Frequency.MONTHLY);
            sheetTransaction6.setValue((float)110.1);
            sheetTransaction6.setGenre(Genre.COST);
            sheetTransaction6.setType(type1);
            sheetTransaction6.setExecuted(false);
            sheetTransaction6.setCurrency(brlCurrency);
            sheetTransaction6.setInstallments(10);
            sheetTransaction6.setEmployee(employee2);


            sheetTransactionRepository.save(sheetTransaction1);
            sheetTransactionRepository.save(sheetTransaction2);
            sheetTransactionRepository.save(sheetTransaction3);
            sheetTransactionRepository.save(sheetTransaction4);
            sheetTransactionRepository.save(sheetTransaction5);
            sheetTransactionRepository.save(sheetTransaction6);




            GeneralTransaction generalTransaction4 = new GeneralTransaction();
            generalTransaction4.setDate(randomDate1);
            generalTransaction4.setName("Despesa Geral 4 - PF");
            generalTransaction4.setFrequency(Frequency.DAILY);
            generalTransaction4.setValue((float)230.1);
            generalTransaction4.setGenre(Genre.COST);
            generalTransaction4.setType(type1);
            generalTransaction4.setExecuted(false);
            generalTransaction4.setCurrency(brlCurrency);

            GeneralTransaction generalTransaction5 = new GeneralTransaction();
            generalTransaction5.setDate(randomDate1);
            generalTransaction5.setName("Despesa Geral 5 - PF");
            generalTransaction5.setFrequency(Frequency.DAILY);
            generalTransaction5.setValue((float)120.1);
            generalTransaction5.setGenre(Genre.COST);
            generalTransaction5.setType(type1);
            generalTransaction5.setExecuted(false);
            generalTransaction5.setInstallments(4);
            generalTransaction5.setCurrency(brlCurrency);

            generalTransactionRepository.save(generalTransaction5);
            generalTransactionRepository.save(generalTransaction4);



            EmployeeTransaction employeeTransaction4 = new EmployeeTransaction();
            employeeTransaction4.setDate(date3);
            employeeTransaction4.setName("Despesa de Funcionário 4 - PF");
            employeeTransaction4.setFrequency(Frequency.DAILY);
            employeeTransaction4.setValue((float)18.3);
            employeeTransaction4.setEmployee(employee2);
            employeeTransaction4.setGenre(Genre.COST);
            employeeTransaction4.setType(type2);
            employeeTransaction4.setExecuted(false);
            employeeTransaction4.setCurrency(brlCurrency);

            EmployeeTransaction employeeTransaction5 = new EmployeeTransaction();
            employeeTransaction5.setDate(date3);
            employeeTransaction5.setName("Despesa de Funcionário 5 - PF");
            employeeTransaction5.setFrequency(Frequency.DAILY);
            employeeTransaction5.setValue((float)82.3);
            employeeTransaction5.setEmployee(employee2);
            employeeTransaction5.setGenre(Genre.COST);
            employeeTransaction5.setType(type2);
            employeeTransaction5.setExecuted(false);
            employeeTransaction5.setInstallments(5);
            employeeTransaction5.setCurrency(brlCurrency);

            employeeTransactionRepository.save(employeeTransaction4);
            employeeTransactionRepository.save(employeeTransaction5);


            ProjectTransaction projectTransaction7 = new ProjectTransaction();
            projectTransaction7.setDate(date5);
            projectTransaction7.setName("Receita de Projeto 7 - PF");
            projectTransaction7.setFrequency(Frequency.DAILY);
            projectTransaction7.setValue((float)24.3);
            projectTransaction7.setProject(project1);
            projectTransaction7.setGenre(Genre.REVENUE);
            projectTransaction7.setType(type2);
            projectTransaction7.setExecuted(false);
            projectTransaction7.setCurrency(brlCurrency);

            ProjectTransaction projectTransaction8 = new ProjectTransaction();
            projectTransaction8.setDate(date5);
            projectTransaction8.setName("Receita de Projeto 8 - PF");
            projectTransaction8.setFrequency(Frequency.DAILY);
            projectTransaction8.setValue((float)124.3);
            projectTransaction8.setProject(project1);
            projectTransaction8.setGenre(Genre.REVENUE);
            projectTransaction8.setType(type2);
            projectTransaction8.setExecuted(false);
            projectTransaction8.setCurrency(brlCurrency);

            projectTransactionRepository.save(projectTransaction7);
            projectTransactionRepository.save(projectTransaction8);


        }
    }
}