package com.frozen.raft.node;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * 附加 RPC 日志返回值.
 *
 * @author frozen
 */
@Setter
@Getter
@ToString
@Builder
public class AentryResult implements Serializable {
    /** 当前的任期号，用于领导人去更新自己 */
    long term;
    /** 跟随者包含了匹配上 prevLogIndex 和 prevLogTerm 的日志时为真  */
    boolean success;
}
