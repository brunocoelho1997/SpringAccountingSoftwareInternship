package hello.Project;

import hello.Client.Client;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public class Project_ {

    public static volatile SingularAttribute<Project, String> name;

    public static volatile SingularAttribute<Project, LocalDate> initialDate;
    public static volatile SingularAttribute<Project, LocalDate> finalDate;
    public static volatile SingularAttribute<Project, Client> client;




}