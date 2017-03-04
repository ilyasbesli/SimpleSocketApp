package com.t360.constant;

public final class SocketConstant {

	public static final String	SERVER						= "server";
	public static final String	CLIENT						= "client";
	public static final String	SOCKET_PORT					= "port";
	public static final String	SOCKET_HOST					= "host";
	public static final int		MAX_TRIAL_NUMBER			= 3;
	public static final int		DEFAULT_PORT				= 9999;
	public static final int		DEFAULT_NUMBER_OF_CLIENT	= 2;
	public static final String	DEFAULT_HOST				= "localhost";
	public static final String	LOG_FORMAT					= "%1$tH:%1$tM:%1$tS:%1$tL %4$-6s %5$s%6$s%n";

	private SocketConstant() {
	}
}
