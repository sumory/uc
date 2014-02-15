package com.sumory.uc.main;

import java.net.InetAddress;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sumory.uc.service.UCService;
import com.uucampus.sns.commons.zk.ClusterLocation;
import com.uucampus.sns.gen.uc.service.UC;


public class Server {
	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	//主线程名称
	private static final String MAIN_THREAD_NAME = "uc";
	//默认端口7000
	private Integer port = 7000;
	//本地ip
	private String ip = "127.0.0.1";
	
	private void start() {
		try {
			/*
			线程池 
			TServerSocket serverTransport = new TServerSocket(port);
			UC.Processor processor = new UC.Processor(new UCService());
			Factory protFactory = new TCompactProtocol.Factory();
			Args args = new Args(serverTransport).processor(processor).protocolFactory(protFactory);
			TServer server = new TThreadPoolServer(args);*/
			
			//非阻塞
			TNonblockingServerSocket socket = new TNonblockingServerSocket(port);
			UC.Processor processor = new UC.Processor(new UCService());
			Args args = new Args(socket);
			args.protocolFactory(new TCompactProtocol.Factory());
			args.transportFactory(new TFramedTransport.Factory());
			args.processor(processor);
			args.workerThreads(10);
			THsHaServer server = new THsHaServer(args);
			
			ClusterLocation.registerService("uc", ip+":"+port);
			logger.info(" start server success \t ip : "+ ip +"\t listen port : " + port);
			
			server.serve();
			
		} catch (Exception e) {
			logger.error(" uc serivce error ",e);
			throw new RuntimeException(e);
		}
		
	}
	
	public static void main(String args[]){
		Thread.currentThread().setName(MAIN_THREAD_NAME);
		Server srv = new Server();
		//设置端口   第一个 参数为port，不设置的话，使用默认端口 7000
		if(args.length > 0){
			try{
				srv.setPort(Integer.valueOf(args[0]));
			}catch(Exception e){
				logger.error("error port : " +args[0],e);
				System.exit(0);
			}
			
		}
		
		//设置ip  第二个参数为ip, 不设置的话，使用自动获取本地ip
		if(args.length > 1){
			  srv.setIp(args[1]);
		}else{
			try{
				srv.setIp(InetAddress.getLocalHost().getHostAddress());  //自动获取本地ip
			}catch (Exception e) {
				logger.error("can't not get ip address",e);
				System.exit(0);
			}
		}
		
		srv.start();
	}
	
	
	//=============setter an getter ==============================
	public Integer getPort() {
		return port;
	}


	public void setPort(Integer port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}
	
	//================main =========================================

}
