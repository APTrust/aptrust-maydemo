package org.aptrust.admin.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.aptrust.client.api.SearchConstraint;
import org.aptrust.client.api.SearchParams;
import org.springframework.util.AutoPopulatingList;

public class WebSearchParams extends SearchParams{
    public WebSearchParams(){
        super(null, new AutoPopulatingList<SearchConstraint>(SearchConstraint.class));
    }
    
    public WebSearchParams(String query, List<SearchConstraint> constraints){
        super(query, constraints);
    }
        @SuppressWarnings("unused")
        public String toQueryStringWithout(SearchConstraint c) {
            String query = getQuery();
            StringBuffer s = new StringBuffer("query=");
            if(query != null){
                s.append(query);
            }
            
            if (!CollectionUtils.isEmpty(getConstraints())) {
                int index = 0;
                for (SearchConstraint sc : getConstraints()) {
                    if (c == null || !sc.equals(c)) {
                        appendConstraint(index, s, sc);
                        index++;
                    }
                }
            }
            return s.toString();
        }

        @SuppressWarnings("unused")
        public String
            toQueryStringWithConstraint(String name, String value) {
            SearchConstraint c = new SearchConstraint();
            c.setName(name);
            c.setValue(value);
            StringBuffer s = new StringBuffer(toQueryStringWithout(null));
            List<SearchConstraint> constraints = this.getConstraints();
            int index = constraints != null ? constraints.size() : 0;
            return appendConstraint(index, s,c).toString();
        }

        private StringBuffer appendConstraint(int index, StringBuffer s,
                                              SearchConstraint sc) {
            s.append("&");
            s.append("constraints["+index+"].name="+sc.getName());
            s.append("&");
            s.append("constraints["+index+"].value="+sc.getValue());
            return s;
        }

    
}