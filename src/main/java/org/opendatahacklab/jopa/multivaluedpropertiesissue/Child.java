package org.opendatahacklab.jopa.multivaluedpropertiesissue;

import cz.cvut.kbss.jopa.model.annotations.Id;
import cz.cvut.kbss.jopa.model.annotations.OWLClass;

@OWLClass(iri="http://test.org/ChildClass")
public class Child {
    @Id
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
