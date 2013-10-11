package com.neusoft.trs.pool;

import org.apache.commons.pool.ObjectPool;

import org.apache.log4j.Logger;

import com.eprobiti.trs.TRSConnection;

public class TRSPoolableSource implements DataSource {
	
	private static final Logger LOG = Logger.getLogger(TRSPoolableSource.class);
	
	private ObjectPool<TRSConnection>  pool ;

	public TRSPoolableSource(ObjectPool<TRSConnection> pool,int initialSize){
		LOG.info("start initialize "+ TRSPoolableSource.class.getName());
		this.pool = pool;
		for(int i = 0 ; i < initialSize ; i++){
			try{
				this.pool.addObject();
			}catch (Exception e) {
				LOG.error("initialize "+TRSPoolableSource.class.getName()+" faile", e);
			}
		}
		LOG.info("initialized "+ TRSPoolableSource.class.getName());
	}
	
	public TRSConnection getConnection(){
		try{
			LOG.debug(Thread.currentThread().getClass()+" get connection from pool.");
			return pool.borrowObject();
		}catch (Exception e) {
			LOG.error("get the connection faile", e);
		}
		return null;
	}
	
}
