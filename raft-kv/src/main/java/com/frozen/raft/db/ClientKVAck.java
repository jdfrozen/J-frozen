package com.frozen.raft.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author frozen
 */
@Getter
@Setter
@ToString
public class ClientKVAck implements Serializable {
	private Object result;
	private final static String Ok="ok";
	private final static String fail="fail";
	public ClientKVAck(Object result) {
		this.result = result;
	}
    public static ClientKVAck ok() {
        return new ClientKVAck(Ok);
    }
    public static ClientKVAck fail() {
        return new ClientKVAck(fail);
    }
	public boolean isSuccess(){
		if(Ok.equals(result)){
			return true;
		}else {
			return false;
		}
	}
}
