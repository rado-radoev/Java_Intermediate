package com.amazonlite.interfaces;

public interface Login {
	public boolean registerUser(String userName);
	public String getUserName();
	public String getPassword(String userName);
	public void setPassword(String password);
	public void setUserName(String userName);
}
