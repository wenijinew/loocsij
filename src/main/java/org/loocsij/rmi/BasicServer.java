package org.loocsij.rmi;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import java.rmi.server.UnicastRemoteObject;


public class BasicServer implements BasicRMI {

	public BasicServer() {
		// TODO Auto-generated constructor stub
	}

	public String send() {
		return "Test";
	}

	public static void main(String[] strs) {
		try {
			BasicServer bs = new BasicServer();
			BasicRMI br = (BasicRMI)UnicastRemoteObject.exportObject(bs);

			Registry r = LocateRegistry.getRegistry();
			r.bind("Basic", br);
			
			System.out.println("Server Ready!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
