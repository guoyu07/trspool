package com.neusoft.trs.pool;

import org.apache.commons.pool.PoolableObjectFactory;

import com.eprobiti.trs.TRSConnection;

public class TRSPoolableObjectFactory implements PoolableObjectFactory<TRSConnection> {
	
	private ConnectionFactory factory ;
	
	public TRSPoolableObjectFactory(ConnectionFactory factory){
		this.factory = factory;
	}

	public void activateObject(TRSConnection conn){
		if(conn instanceof TRSPoolableConnection){
			TRSPoolableConnection connection = (TRSPoolableConnection) conn;
			if(connection.isClosed()) connection.setClosed(false);
		}
	}
	 
	public void destroyObject(TRSConnection conn){
		if(conn instanceof TRSPoolableConnection){
			TRSPoolableConnection connection = (TRSPoolableConnection) conn;
			connection.reallyClose();
		}else if(!conn.isClosed()){
			conn.clean();
			conn.close();
		}
	}
	
	public TRSConnection makeObject(){
		return factory.createConnection();
	}
	
	public void passivateObject(TRSConnection conn){
		if(conn instanceof TRSPoolableConnection){
			TRSPoolableConnection connection = (TRSPoolableConnection) conn;
			if(!connection.isClosed()) connection.setClosed(true);
		}
	}

	public boolean validateObject(TRSConnection conn){
		if(conn instanceof TRSPoolableConnection){
			TRSPoolableConnection connection = (TRSPoolableConnection) conn;
			if(connection.isClosed()) return false;
			return true;
		}
		return false;
	}
	 
	
}
