package com.azer.meetingmanager.ui;

import javafx.stage.Stage;

/**
 * abstract class to manage dialog result
 * @param <T> the result type
 */
public abstract class DialogController<T> {

    protected static byte STATE_COMPLETED = 1;
    protected static byte STATE_ERROR = 2;
    protected static byte STATE_CANCELLED = 4;

    /**
     * the container that displays this dialog
     */
    private Stage container;
    private T result;
    private Exception error;
    private byte state = STATE_CANCELLED;

    protected void setError(Exception error) {
        this.error = error;
    }

    protected void setState(byte state) {
        this.state = state;
    }

    protected void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    public boolean isCompleted() {
        return (state & STATE_COMPLETED) == STATE_COMPLETED;
    }

    public boolean isCancelled() {
        return (state & STATE_CANCELLED) == STATE_CANCELLED;
    }

    public boolean isError() {
        return (state & STATE_ERROR) == STATE_ERROR;
    }

    public Stage getContainer() {
        return container;
    }

    public void setContainer(Stage container) {
        this.container = container;
    }

    public void showAndWait(OnCompleteListener<T> callback) {
        if (container == null)
            throw new NullPointerException("there is no container to display this dialog");

        getContainer().showAndWait();

        if (isCompleted())
            callback.onCompleted(getResult());
        else if (isError())
            callback.onError(getError());
        else
            callback.onCancelled();
    }
}