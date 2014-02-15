package com.sumory.uc.test;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.uucampus.sns.gen.uc.service.UC;

public class Client {

    public static void main(String[] args) throws Exception {
        try {
            TTransport transport = new TSocket("192.168.1.118", 7100);
            TProtocol protocol = new TCompactProtocol(transport);
            UC.Client client = new UC.Client(protocol);

            transport.open();
            System.out.println("Client calls .....");

            //==========================================================
            System.out.print(client.genGroupFeedId());

            transport.close();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
