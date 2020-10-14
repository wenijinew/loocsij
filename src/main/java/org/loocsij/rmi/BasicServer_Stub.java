/**
 *
 */
package org.loocsij.rmi;

import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;

/**
 * @author wengm
 *
 */
public class BasicServer_Stub extends RemoteStub implements BasicRMI{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public BasicServer_Stub() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public BasicServer_Stub(RemoteRef arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public String send() throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
