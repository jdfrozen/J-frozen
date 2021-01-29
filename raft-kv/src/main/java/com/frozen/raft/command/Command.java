package com.frozen.raft.command;

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
public class Command implements Serializable {
    String key;
    String value;
}
