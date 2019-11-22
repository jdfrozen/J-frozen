package com.frozen.raft.node;

/**
 *
 * @author frozen
 */
public interface LifeCycle {

    void init() throws Throwable;

    void destroy() throws Throwable;
}
