package dataaccess;

import java.util.HashMap;

public interface Dao {
	
	public int execute();
	public HashMap<String, Object> getData();

}
