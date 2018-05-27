package raft;

import raft.net.ssl.SSLChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

class RaftServer implements Runnable { // Despite the name, this class actually models a connection with another server, and not the server itself, even though we keep track of some information regarding it
	private Raft raft;
	private SSLChannel channel;

	Raft.ServerState state;
	InetSocketAddress address;

	// Volatile state
	Long nextIndex;
	Long matchIndex = 0L;

	CompletableFuture<String> callRPC; // This is what I probably need
	AtomicBoolean callflag; // Combined with this (use this as atomic flag)
	CompletableFuture<String> retRPC;
	AtomicBoolean retflag;

	RaftServer() {
		// Placeholder constructor
	}

	RaftServer(Raft raft, SSLChannel channel, String addr, Integer port) {
		this.raft = raft;
		this.channel = channel;
		state = Raft.ServerState.INITIALIZING;
		address = new InetSocketAddress(addr, port);
		this.callRPC = new CompletableFuture<>();
		this.callflag = new AtomicBoolean(true);
		this.retRPC = new CompletableFuture<>();
		this.retflag = new AtomicBoolean(false);
	}

	@Override
	public void run() {

	}

	public void stop() {
		// Shutdown server
	}
}
