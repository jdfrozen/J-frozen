package com.frozen.raft.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 节点配置
 * @author frozen
 */
@Getter
@Setter
@ToString
@Component
public class NodeConfig {
    /** 自身 selfPort */
    @Value("#{${selfPort}}")
    private int selfPort;
    /** 所有节点地址. */
    @Value("#{'${peerAddrs}'.split(',')}")
    private List<String> peerAddrs;
}
