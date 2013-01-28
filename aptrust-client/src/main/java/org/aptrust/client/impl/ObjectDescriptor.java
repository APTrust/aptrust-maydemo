package org.aptrust.client.impl;

/**
 * Data describing an object associated with a package.
 * @author Daniel Bernstein
 *
 */
public class ObjectDescriptor {
    private String id;
    private String title;

    public ObjectDescriptor(String id) {
        this(id, id);
    }

    public ObjectDescriptor(String id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
}
