package com.t360.test;

import com.t360.MessengerMain;
import com.t360.constant.SocketConstant;
import org.testng.annotations.Test;

public class MessengerSocketTest {

    @Test
    public void runMessengerSameJavaProcessDefault() {
        MessengerMain.main(null);
    }

    @Test
    public void runMessengerSameJavaProcessTwoClient() {
        System.setProperty(SocketConstant.SOCKET_PORT, "8888");
        String[] args = {"2"};
        MessengerMain.main(args);
    }

    @Test
    public void runMessengerSeparateJavaProcessAllParam() throws InterruptedException {
        System.setProperty(SocketConstant.SOCKET_PORT, "7777");
        String[] args = {"1", "server"};
        MessengerMain.main(args);
        Thread.sleep(1000);
        String[] args2 = {"1", "client"};
        MessengerMain.main(args2);
    }

    @Test
    public void runMessengerSeparateJavaProcessAllParamTwoClient() throws InterruptedException {
        System.setProperty(SocketConstant.SOCKET_PORT, "6666");
        String[] args = {"2", "server"};
        MessengerMain.main(args);
        Thread.sleep(1000);
        String[] args2 = {"2", "client"};
        MessengerMain.main(args2);
    }

}
