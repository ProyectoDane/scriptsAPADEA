package com.globant.scriptsapadea.manager;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/** The Response Returned.
 * @author nicolas.quartieri
 * @param <T> the type expected to be marshaled from the json response.
 */
public class Response<T> {
    final private T payload;
    final private int statusCode;
    final private Map<String, List<String>> headers;

    Response(T payload, int statusCode, Map<String, List<String>> headers) {
        this.payload = payload;
        this.statusCode = statusCode;
        this.headers = headers;
    }

    public final int getStatusCode() {
        return statusCode;
    }

    public T getPayload() {
        return this.payload;
    }

    /**
     * Returns a list of values to which the specified header is mapped, or null if this no values for the header.
     * @param header - the header whose associated values are to be returned
     * @return - the values that are associated to the specified header, or null if there is no values for the header
     */
    public List<String> getHeader(String header) {
        return headers.get(header);
    }

    /**
     * Returns an immutable copy of the headers of this response 
     * @return - an immutable copy of all the headers
     */
    public Map<String, List<String>> getHeaders() {
        return ImmutableMap.copyOf(headers);
    }
}