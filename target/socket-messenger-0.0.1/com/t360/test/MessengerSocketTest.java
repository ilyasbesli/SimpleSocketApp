package com.t360.test;

import org.testng.annotations.Test;

import com.t360.MessengerMain;
import com.t360.constant.SocketConstant;

public class MessengerSocketTest {

	@Test
	public void runMessengerSameJavaProcessDefault() {
		MessengerMain.main(null);
	}

	@Test
	public void runMessengerSameJavaProcessTwoClient() {
		System.setProperty(SocketConstant.SOCKET_PORT_NUMBER, "8888");
		String[] args = { "2" };
		MessengerMain.main(args);
	}

	@Test
	public void runMessengerSeperateJavaProcessAllParam() throws InterruptedException {
		System.setProperty(SocketConstant.SOCKET_PORT_NUMBER, "7777");
		String[] args = { "1", "server" };
		MessengerMain.main(args);
		Thread.sleep(1000);
		String[] args2 = { "1", "client" };
		MessengerMain.main(args2);
	}

	@Test
	public void runMessengerSeperateJavaProcessAllParamTwoClient() throws InterruptedException {
		System.setProperty(SocketConstant.SOCKET_PORT_NUMBER, "6666");
		String[] args = { "2", "server" };
		MessengerMain.main(args);
		Thread.sleep(1000);
		String[] args2 = { "2", "client" };
		MessengerMain.main(args2);
	}

}
