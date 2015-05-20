package domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Follow.class)
public abstract class Follow_ {

	public static volatile SingularAttribute<Follow, ApplicationUser> follower;
	public static volatile SingularAttribute<Follow, ApplicationUser> followed;

}

