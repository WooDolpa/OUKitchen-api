package com.project.toy.api.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/05
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public CustomHttpServletRequest(HttpServletRequest request) {
        super(request);
        customHeaders = new HashMap<>();
    }

    public void putHeader(String name, String value){
        this.customHeaders.put(name, value);
    }

    public String getHeader(String name) {
        // check the custom headers first
        String headerValue = customHeaders.get(name);

        if (headerValue != null){
            return headerValue;
        }
        // else return from into the original wrapped object
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<String>(customHeaders.keySet());

        // now add the headers from the wrapped request object
        @SuppressWarnings("unchecked")
        Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            String n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }

}
