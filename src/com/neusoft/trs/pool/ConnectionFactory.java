package com.neusoft.trs.pool;

import com.eprobiti.trs.TRSConnection;

public interface ConnectionFactory {

	public TRSConnection createConnection();
	
}
