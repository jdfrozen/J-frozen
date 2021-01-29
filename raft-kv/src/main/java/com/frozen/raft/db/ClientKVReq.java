package com.frozen.raft.db;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author frozen
 */
@Data
@Builder
@ToString
public class ClientKVReq implements Serializable {
    public static int PUT = 0;
    public static int GET = 1;
    int type;
    String key;
    String value;
}
