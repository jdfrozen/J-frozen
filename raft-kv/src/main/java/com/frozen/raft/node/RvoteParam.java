package com.frozen.raft.node;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求投票 RPC 参数.
 *
 * @author frozen
 * @see
 */
@Getter
@Setter
@Builder
@ToString
public class RvoteParam {
	/** 候选人的任期号  */
	private long term;
	/** 被请求者 ID(ip:selfPort) */
	private String serverId;
    /** 请求选票的候选人的 Id(ip:selfPort) */
    private String candidateId;
    /** 候选人的最后日志条目的索引值 */
    private long lastLogIndex;
    /** 候选人最后日志条目的任期号  */
    private long lastLogTerm;
}
