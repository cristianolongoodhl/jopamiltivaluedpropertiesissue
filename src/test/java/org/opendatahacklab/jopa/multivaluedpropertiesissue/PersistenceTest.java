package org.opendatahacklab.jopa.multivaluedpropertiesissue;

import cz.cvut.kbss.jopa.Persistence;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.EntityManagerFactory;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProperties;
import cz.cvut.kbss.jopa.model.JOPAPersistenceProvider;
import cz.cvut.kbss.ontodriver.jena.JenaDataSource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cz.cvut.kbss.ontodriver.jena.config.JenaOntoDriverProperties;
import org.junit.jupiter.api.Test;


public class PersistenceTest {
    /**
     * Configuration properties, excluding storage type and storage file name
     * @return EntityManagerFactory configurations properties
     */
    private static Map<String,String> persistenceConfiguration(final String storageType, final String ontologyPhysicalURIkey){
        final Map<String, String> props = new HashMap<>();
        props.put(JOPAPersistenceProperties.SCAN_PACKAGE, "org.opendatahacklab.jopa.multivaluedpropertiesissue");
        props.put(JOPAPersistenceProperties.JPA_PERSISTENCE_PROVIDER, JOPAPersistenceProvider.class.getName());
        props.put(JOPAPersistenceProperties.DATA_SOURCE_CLASS, JenaDataSource.class.getName());
        props.put(JenaOntoDriverProperties.JENA_STORAGE_TYPE, storageType);
        props.put(JOPAPersistenceProperties.ONTOLOGY_PHYSICAL_URI_KEY, ontologyPhysicalURIkey);
        return props;
    }

    @Test
    void testPersistParentWithChildInMemory(){
        try(final EntityManagerFactory emf=Persistence.createEntityManagerFactory("test-pu",
                persistenceConfiguration(JenaOntoDriverProperties.IN_MEMORY, "notRelevant"))){
            testParentWithChild(emf);
        }
    }

    @Test
    void testPersistParentWithChildFile(){
        final String ontologyFilePath="test-ontology.ttl";
        try(final EntityManagerFactory emf=Persistence.createEntityManagerFactory("test-pu",
                persistenceConfiguration(JenaOntoDriverProperties.FILE, ontologyFilePath))){
            testParentWithChild(emf);
        } finally {
            new File(ontologyFilePath).delete();
        }
    }

    private void testParentWithChild(final EntityManagerFactory emf){
        final EntityManager em=emf.createEntityManager();

        final Parent p=new Parent();
        p.setId("http://test.org/parent");
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        final Child c1=new Child();
        c1.setId("http://test.org/child1");
        p.getHasChild().add(c1);
        em.getTransaction().begin();
        em.persist(c1);
        em.merge(p);
        em.getTransaction().commit();

        final Child c2=new Child();
        c2.setId("http://test.org/child2");
        p.getHasChild().add(c2);
        em.getTransaction().begin();
        em.persist(c2);
        em.merge(p);
        em.getTransaction().commit();

    }
}
