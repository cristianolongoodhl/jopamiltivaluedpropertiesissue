package org.opendatahacklab.jopa.multivaluedpropertiesissue;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;

import java.util.HashSet;
import java.util.Set;

@OWLClass(iri="http://test.org/ParentClass")
public class Parent extends Child{

    @OWLObjectProperty(iri="http://test.org/hasChildProperty")
    private Set<Child> hasChild = new HashSet<>();

    public void setHasChild(final Set<Child> children){
        this.hasChild=children;
    }

    public Set<Child> getHasChild(){
        return hasChild;
    }
}
