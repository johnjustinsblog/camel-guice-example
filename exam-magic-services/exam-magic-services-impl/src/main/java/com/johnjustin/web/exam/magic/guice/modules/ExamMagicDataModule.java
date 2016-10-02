package com.johnjustin.web.exam.magic.guice.modules;

import org.apache.commons.configuration.Configuration;
//import com.datastax.driver.core.Cluster;
//import com.datastax.driver.core.Session;
//import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
//import com.datastax.driver.core.policies.TokenAwarePolicy;


import com.google.inject.AbstractModule;
import com.johnjustin.exam.magic.data.ExamMagicAssessment;
import com.johnjustin.exam.magic.data.impl.ExamMagicAssessmentImpl;

/* initializes the db connection and session Guice injection to session and properties */

public final class ExamMagicDataModule extends AbstractModule{
	
	private Configuration config = null;
	//private Cluster cluster;
	//private Session session;

	public ExamMagicDataModule(Configuration config){
		this.config = config;
	}
	
	@Override
	protected void configure(){
		
		bind(ExamMagicAssessment.class).to(ExamMagicAssessmentImpl.class);
		//configureCassandra();
		//bind(Session.class).toInstance(session);
	}
	
	/*private void configureCassandra(){
		
		Cluster.Builder builder = Cluster
				.builder()
				.addContactPoints(config.getString("cassandra.seeds","127.0.0.1").split(","))
				.withPort(config.getInt("cassandra.port",9042))
				.withCredentials(System.getenv("CASSANDRAUSER"),System.getenv("CASSANDRAPWD"));
		
		String dcName = config.getString("cassandra.dcname");
		if(dcName!= null){
			builder.withLoadBalancingPolicy(new TokenAwarePolicy(new DCAwareRoundRobinPolicy(dcName)));
		}
		cluster = builder.build();
		session = cluster.connect("mykeyspace");
	}
	
	public void closeSession(){
		if(session!= null){
			session.close();
		}
		if(cluster!= null){
			cluster.close()
		}
	}*/
}
