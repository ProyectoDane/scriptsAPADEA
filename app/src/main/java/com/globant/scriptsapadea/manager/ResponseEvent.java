package com.globant.scriptsapadea.manager;

import com.google.common.base.Preconditions;

/** The Response event to extend for each call.
 * @author nicolas.quartieri
 * @param <T> the type expected to be marshaled from the json response.
 */
public abstract class ResponseEvent<T> {
    private T payload;
    private boolean success;
    private Throwable throwable;

    public void setResult(boolean success) {
        this.success = success;
    }

    public void setResult(Response<T> response) {
        Preconditions.checkNotNull(response, "The response cannot be null on a success event.");
        this.payload = response.getPayload();
        success = true;
    }

    public void setResult(T payload) {
        Preconditions.checkNotNull(payload, "The payload cannot be null on a success event.");
        this.payload = payload;
        success = true;
    }

    public void setException(Throwable exception) {
        Preconditions.checkNotNull(exception, "The exception cannot be null on a fail event.");
        success = false;
        this.throwable = exception;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getPayload() {
        return payload;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}