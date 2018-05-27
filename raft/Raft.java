package raft;

import raft.net.ssl.SSLChannel;
import raft.util.Serialization;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Raft<T extends Serializable> {
    enum ServerState {
        INITIALIZING, WAITING, RUNNING, TERMINATING;
    }
    enum ClusterState {
        INITIALIZING, RUNNING, TERMINATING;
    }

	AtomicReference<ServerState> serverState = new AtomicReference<>(ServerState.INITIALIZING);
//	private AtomicReference<ClusterState> clusterState = new AtomicReference<>(ClusterState.INITIALIZING);

	UUID ID = UUID.randomUUID();
	Integer port;

	ConcurrentHashMap<UUID, RaftCommunication> cluster = new ConcurrentHashMap<>();
//  private AtomicReference<State> state = new AtomicReference<>(new FollowerState(this));
	AtomicReference<RaftState> state = new AtomicReference<>(RaftState.FOLLOWER);

	ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private Timer timer = new Timer();
    private TimerTask followerTimerTask = new TimerTask() {
        @Override
        public void run() {
            lock.lock();
            if (state.compareAndSet(RaftState.FOLLOWER, RaftState.CANDIDATE)) {
            	currentTerm.getAndAdd(1);
            	votes.getAndAdd(1);
            }
            condition.signal();
            lock.unlock();
        }
    };
	private TimerTask candidateTimerTask = new TimerTask() {
		@Override
		public void run() {
			lock.lock();
			if (state.get() == RaftState.CANDIDATE) {
				currentTerm.getAndAdd(1);
			}
			condition.signal();
			lock.unlock();
		}
	};
	private TimerTask leaderTimeout = new TimerTask() { // TODO
		@Override
		public void run() {
			lock.lock();
			/*if (state.get() == RaftState.CANDIDATE) {
				currentTerm.getAndAdd(1);
			}*/
			condition.signal();
			lock.unlock();
		}
	};

	private void followerTimeout() {
		timer.schedule(followerTimerTask, ThreadLocalRandom.current().nextInt(RaftProtocol.maxRandomDelay - RaftProtocol.minRandomDelay) + RaftProtocol.minRandomDelay);
	}
	void candidateTimeout() {
		timer.schedule(candidateTimerTask, ThreadLocalRandom.current().nextInt(RaftProtocol.maxRandomDelay - RaftProtocol.minRandomDelay) + RaftProtocol.minRandomDelay);
	}
	void leaderTimeout() { // TODO
		timer.schedule(leaderTimeout, ThreadLocalRandom.current().nextInt(RaftProtocol.maxRandomDelay - RaftProtocol.minRandomDelay) + RaftProtocol.minRandomDelay);
	}

/*    public void setState(State state) {
        this.state.set(state);
    }*/

	//	Persistent state (save this to stable storage)
	AtomicLong currentTerm = new AtomicLong(0L);
	UUID votedFor;
	RaftLog<T>[] log;

	//	Volatile state
	UUID leaderID;
	AtomicLong votes = new AtomicLong(0L);
	Long commitIndex = 0L;
	Long lastApplied = 0L;


	// TODO we need more locks!!! (or maybe not)
	ReentrantLock cluster_lock = new ReentrantLock();

	ReentrantLock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	/*
		Constructors
	 */

	public Raft(Integer port, InetSocketAddress cluster) {
		this.port = port;

		// Connect to known cluster
		{
			SSLChannel channel = new SSLChannel(cluster);
			if (channel.connect()) {
				this.pool.execute(new RaftDiscover(this, channel));
			} else {
				System.out.println("Connection failed!"); // DEBUG
				return; // Show better error message
			}
		}

		// Listen for new connections
		this.pool.execute(() -> {
			while (serverState.get() != ServerState.TERMINATING) {
				SSLChannel channel = new SSLChannel(port);
				if (channel.accept()) {
					pool.execute(new RaftServer<T>(this, channel));
				}
			}
		});
	}

	public Raft(Integer port) {
		this.port = port;

		// Listen for new connections
		this.pool.execute(() -> {
			while (serverState.get() != ServerState.TERMINATING) {
				SSLChannel channel = new SSLChannel(port);
				if (channel.accept()) {
					pool.execute(new RaftServer<T>(this, channel));
				}
			}
		});
	}

	/*
		Public methods
	 */

	public void run() {
		pool.execute(() -> {
			lock.lock();
			while (serverState.get() != ServerState.TERMINATING) {
				switch (state.get()) {
					case FOLLOWER:
						followerTimeout();
						break;
					case CANDIDATE:
						// TODO Issue requestVotes
						candidateTimeout();
						break;
					case LEADER:
					//	leaderTimeout();
						// TODO logic to handle client requests
						break;
				}
				condition.awaitUninterruptibly();
			}
		});
	}

	public boolean set(T var) {
		SSLChannel channel = connectToLeader();

		if(channel == null) {
			return false;
		}

		String serObj = new String(Serialization.serialize(var));

		channel.send(RPC.callSetValue(serObj));

		String message = channel.receiveString();

		return message.equals(RPC.retSetValue(true));
	}

	public T get() {
		SSLChannel channel = connectToLeader();

		if(channel == null) {
			return null;
		}

		channel.send(RPC.callGetValue());

		String message = channel.receiveString();

		T obj = Serialization.deserialize(message.split("\n")[1].getBytes());

		return obj;
	}

	public boolean delete() {
		SSLChannel channel = connectToLeader();

		if(channel == null) {
			return false;
		}

		channel.send(RPC.callDeleteValue());

		String message = channel.receiveString();

		return message.equals(RPC.retDeleteValue(true));
	}

	/*
		Private methods
	 */

	SSLChannel connectToLeader() {
		if(this.leaderID == null) {
			return null;
		}

		RaftCommunication leader = this.cluster.get(this.leaderID);

		if(leader == null) {
			return null;
		}

		SSLChannel channel = new SSLChannel(leader.address);

		if (!channel.connect()) {
			System.out.println("Connection failed!"); // DEBUG
			return null; // Show better error message
		}

		return channel;
	}

	boolean setValue(T object) {
		return true;
	}

	boolean deleteValue() {
		return true;
	}

	T getValue() {
		return null;
	}
}
