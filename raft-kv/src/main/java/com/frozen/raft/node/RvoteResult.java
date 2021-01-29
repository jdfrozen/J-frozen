package com.frozen.raft.node;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * 请求投票 RPC 返回值.
 *
 */
@Getter
@Setter
@Builder
@ToString
public class RvoteResult implements Serializable {

    /** 当前任期号，以便于候选人去更新自己的任期 */
    long term;

    /** 候选人赢得了此张选票时为真 */
    boolean voteGranted;
}
