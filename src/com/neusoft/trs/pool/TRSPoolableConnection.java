package com.neusoft.trs.pool;

import org.apache.commons.pool.ObjectPool;

import org.apache.log4j.Logger;

import com.eprobiti.trs.TRSConnection;
import com.eprobiti.trs.TRSException;

public class TRSPoolableConnection extends TRSConnection{
	
	private static final Logger LOG = Logger.getLogger(TRSPoolableConnection.class);

	private boolean closed = false;
	
	private ObjectPool<TRSConnection> pool;

	public TRSPoolableConnection(ObjectPool<TRSConnection> pool)throws TRSException{
		super();
		this.pool = pool;
		LOG.info(TRSPoolableConnection.class.getName() +" initialized.");
	}
	
	@Override
	public boolean close(){
		if(closed) return true;
		if(super.isClosed()){
			try{
				pool.invalidateObject(this);
			}catch (Exception e) {
				LOG.error("the connectin alread closed.", e);
				throw new RuntimeException("");
			}
		}
		try{
			pool.returnObject(this);
		}catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		return true;
	}
	
	public void reallyClose(){
		if(!this.isClosed()){
			this.close();
		}
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public boolean isClosed(){
		return closed;
	}
	
}
