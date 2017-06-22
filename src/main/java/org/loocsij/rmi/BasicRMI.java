/**
 * 
 */
package org.loocsij.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author wengm
 *
 */
public interface BasicRMI extends Remote {
	String send() throws RemoteException;
}
