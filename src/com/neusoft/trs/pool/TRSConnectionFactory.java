package com.neusoft.trs.pool;

import org.apache.commons.pool.ObjectPool;

import com.eprobiti.trs.TRSConnection;
import com.eprobiti.trs.TRSException;

public class TRSConnectionFactory implements ConnectionFactory{
	
	private String host;
	
	private String port;
	
	private String username;
	
	private String passwd;
	
	private ObjectPool<TRSConnection> pool;
	
	public TRSConnectionFactory(String host,String port,String username,String passwd){
		this.host = host;
		this.port = port;
		this.username = username;
		this.passwd = passwd;
	}
	
	public TRSConnectionFactory(String host,String port,String username,String passwd,ObjectPool<TRSConnection> pool){
		this.host = host;
		this.port = port;
		this.username = username;
		this.passwd = passwd;
		this.pool = pool;
	}
	
	public TRSConnection createConnection(){
		try{
			TRSPoolableConnection conn = new TRSPoolableConnection(this.pool);
			conn.connect(this.host, this.port, this.username, this.passwd);
			return conn;
		}catch (TRSException e) {
			throw new RuntimeException(e);
		}
	}

	public void setPool(ObjectPool<TRSConnection> pool) {
		this.pool = pool;
	}
	
}
