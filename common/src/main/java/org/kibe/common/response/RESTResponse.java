package org.kibe.common.response;

import java.util.List;

public class RESTResponse {
    private Status status;
    private int code;
    private Object result;
    private List<String> messages;

    private RESTResponse() {
    }

    public int getCode() {
        return code;
    }

    public Status getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public List<String> getMessages() {
        return messages;
    }

    public static final class Builder {
        private Status status;
        private int code;
        private Object result;
        private List<String> messages;

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Builder code(final int code) {
            this.code = code;
            return this;
        }

        public Builder result(final Object result) {
            this.result = result;
            return this;
        }

        public Builder messages(final List<String> messages) {
            this.messages = messages;
            return this;
        }

        public RESTResponse build() {
            final RESTResponse restResponse = new RESTResponse();
            restResponse.status = this.status;
            restResponse.code = this.code;
            restResponse.result = this.result;
            restResponse.messages = this.messages;
            return restResponse;
        }
    }
}
