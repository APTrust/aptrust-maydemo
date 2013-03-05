package org.aptrust.client.api;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Represents a constraint on a search query.
 * @author Daniel Bernstein
 *
 */
public class SearchConstraint {
    private String name;
    private String value;
 
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
