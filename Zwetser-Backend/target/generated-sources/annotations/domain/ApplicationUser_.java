package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ApplicationUser.class)
public abstract class ApplicationUser_ {

	public static volatile SingularAttribute<ApplicationUser, String> role;
	public static volatile SingularAttribute<ApplicationUser, String> authToken;
	public static volatile SingularAttribute<ApplicationUser, String> bio;
	public static volatile SingularAttribute<ApplicationUser, String> avatar;
	public static volatile SingularAttribute<ApplicationUser, String> password;
	public static volatile CollectionAttribute<ApplicationUser, Follow> followers;
	public static volatile SingularAttribute<ApplicationUser, String> web;
	public static volatile CollectionAttribute<ApplicationUser, Follow> following;
	public static volatile SingularAttribute<ApplicationUser, String> name;
	public static volatile SingularAttribute<ApplicationUser, String> location;
	public static volatile SingularAttribute<ApplicationUser, Long> id;
	public static volatile SingularAttribute<ApplicationUser, String> email;
	public static volatile SingularAttribute<ApplicationUser, String> username;

}

