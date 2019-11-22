package com.frozen.raft.node;


import com.frozen.raft.entity.LogEntry;

/**
 *
 * @see
 * @author frozen
 */
public interface LogModule {

    void write(LogEntry logEntry);

    LogEntry read(Long index);

    void removeOnStartIndex(Long startIndex);

    LogEntry getLast();

    Long getLastIndex();
}
