package com.neusoft.trs.pool;

import com.eprobiti.trs.TRSConnection;

public interface DataSource {
	
	public TRSConnection getConnection();
	
}
