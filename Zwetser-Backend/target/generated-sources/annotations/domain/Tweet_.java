package domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tweet.class)
public abstract class Tweet_ {

	public static volatile SingularAttribute<Tweet, Long> postedBy;
	public static volatile SingularAttribute<Tweet, Date> postDate;
	public static volatile SingularAttribute<Tweet, String> postedFrom;
	public static volatile SingularAttribute<Tweet, Long> id;
	public static volatile SingularAttribute<Tweet, String> screenName;
	public static volatile SingularAttribute<Tweet, String> message;

}

