package com.frozen.raft.db;

import com.frozen.raft.raftlog.LogEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frozen
 * @create 2021-01-29 14:56
 * @description
 **/
@Service
public class LogDb{
	List<LogEntry> logEntrieCommits = new ArrayList<>();

	public LogEntry  getlastLogEntrieCommit(){
		return null;
	}
}
