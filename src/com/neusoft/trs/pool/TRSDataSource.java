package com.neusoft.trs.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.eprobiti.trs.TRSConnection;

public class TRSDataSource {

	private String host;
	
	private String port;
	
	private String username;
	
	private String passwd;
	
	private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
	
	private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;
	
	private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
	
	private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;
	
	private boolean lifo = GenericObjectPool.DEFAULT_LIFO;
	
	private long minEvictableIdleTimeMillis = GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
	
	private int numTestsPerEvictionRun = GenericObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
	
	private long softMinEvictableIdleTimeMillis = GenericObjectPool.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
	
	private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
	
	private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
	
	private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;
	
	private long timeBetweenEvictionRunsMillis = GenericObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
	
	private byte whenExhaustedAction = GenericObjectPool.DEFAULT_WHEN_EXHAUSTED_ACTION;
	
	private int intialSize = 1;
	
	private DataSource dataSource ;
	
	private GenericObjectPool<TRSConnection> connectionPool;
	
	private volatile boolean closed = false;
	
	public TRSConnection getConnection(){
		createDataSource();
		return dataSource.getConnection();
	}
	
	public synchronized boolean isClosed(){
		return closed;
	}
	
	public synchronized void close(){
		if(!closed){
			try{
				connectionPool.close();
				closed = true;
			}catch (Exception e) {
				throw new RuntimeException("close pool failed.");
			}
		}
	}
	
	private synchronized void createDataSource(){
		if(closed) throw new RuntimeException("the pool has closed");
		if(dataSource != null) return ;
		createPool();
		dataSource = new TRSPoolableSource(connectionPool,intialSize);
	}
	
	private void createPool(){
		TRSConnectionFactory connectionFactory = new TRSConnectionFactory(host,port,username,passwd);
		PoolableObjectFactory<TRSConnection> factory = new TRSPoolableObjectFactory(connectionFactory);
		connectionPool = new GenericObjectPool<TRSConnection>(factory);
		connectionFactory.setPool(connectionPool);
		connectionPool.setLifo(lifo);
		connectionPool.setMaxActive(maxActive);
		connectionPool.setMaxIdle(maxIdle);
		connectionPool.setMaxWait(maxWait);
		connectionPool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		connectionPool.setMinIdle(minIdle);
		connectionPool.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		connectionPool.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
		connectionPool.setTestOnBorrow(testOnBorrow);
		connectionPool.setTestOnReturn(testOnReturn);
		connectionPool.setTestWhileIdle(testWhileIdle);
		connectionPool.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		connectionPool.setWhenExhaustedAction(whenExhaustedAction);
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public void setLifo(boolean lifo) {
		this.lifo = lifo;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public void setSoftMinEvictableIdleTimeMillis(
			long softMinEvictableIdleTimeMillis) {
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public void setWhenExhaustedAction(byte whenExhaustedAction) {
		this.whenExhaustedAction = whenExhaustedAction;
	}

	public void setIntialSize(int intialSize) {
		this.intialSize = intialSize;
	}
	
}
