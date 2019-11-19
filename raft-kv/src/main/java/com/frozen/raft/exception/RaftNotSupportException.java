package com.frozen.raft.exception;

/**
 *
 */
public class RaftNotSupportException extends RuntimeException {

    public RaftNotSupportException() {
    }

    public RaftNotSupportException(String message) {
        super(message);
    }
}
