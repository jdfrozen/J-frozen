package com.frozen.raft.rpc;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author frozen
 */
@Data
@ToString
@Builder
public class Response<T> implements Serializable {

    private T result;
    private final static String Ok="ok";
	private final static String fail="fail";

    public Response(T result) {
        this.result = result;
    }

    public static Response ok() {
        return new Response<>(Ok);
    }

    public static Response fail() {
        return new Response<>(fail);
    }

	public boolean isSuccess(){
    	if(Ok.equals(result)){
    		return true;
	    }else {
    		return false;
	    }
	}
}
