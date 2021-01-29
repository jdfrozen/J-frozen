package com.frozen.raft.node;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author Frozen
 * @create 2021-01-28 15:27
 * @description
 **/
@Data
@ToString
@Builder
public class Peer {
	private String addr;
	private Integer port;
	public String getPeerStr(){
		return this.addr+":"+this.port;
	}

	public static Peer getPeer(String peerStr){
		String[] strs=peerStr.split(":");
		return Peer.builder().addr(strs[0]).port(Integer.valueOf(strs[1])).build();
	}
}
