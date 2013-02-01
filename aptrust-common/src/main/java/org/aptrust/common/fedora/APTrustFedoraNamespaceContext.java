package org.aptrust.common.fedora;

import java.util.Collections;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

/**
 * An XPath NamespaceContext with all the necessary namespace URIs for Fedora
 * and APTrust.
 * 
 * Besides containing the mappings, this class exposes the names as member 
 * variables.
 */
public class APTrustFedoraNamespaceContext implements NamespaceContext {

    public String rdfPrefix = "rdf";
    public String rdfURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public String aptrustPrefix = "aptrust";
    public String aptrustURI = APTrustFedoraConstants.APTRUST_URI_PREFIX;

    public String getNamespaceURI(String prefix) {
        if (prefix.equals(aptrustPrefix)) {
            return aptrustURI;
        } else if (prefix.equals(rdfPrefix)) {
            return rdfURI;
        } else {
            return null;
        }
    }

    public String getPrefix(String namespaceURI) {
        if (namespaceURI.equals(aptrustURI)) {
            return aptrustPrefix;
        } else if (namespaceURI.equals(rdfURI)) {
            return rdfPrefix;
        } else {
            return null;
        }    
    }

    public Iterator getPrefixes(String namespaceURI) {
        return Collections.singleton(getPrefix(namespaceURI)).iterator();
    }

}
