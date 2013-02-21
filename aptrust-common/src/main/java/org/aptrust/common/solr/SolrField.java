package org.aptrust.common.solr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that allows methods to be annotated as mapping to a Solr
 * field.  This annotation may be applied to a method that accepts zero
 * parameters to indicated that the returned value maps to the Solr field with
 * the supplied name.  It may also be applied to a method that accepts exactly
 * one parameter to indicate that the value of the field in Solr with the given
 * name may be supplied to that method to initialize an object of that type
 * from a Solr document.  For a typical java bean pattern, you may annotate the
 * "setter" and the "getter" methods.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SolrField {
    
    /**
     * The name should be one of the statically defined fields below.  Then
     * changes to the Solr schema can be centralized in this interface. 
     */
    String name();
}
