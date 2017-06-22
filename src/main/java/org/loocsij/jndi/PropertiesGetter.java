package org.loocsij.jndi;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

public class PropertiesGetter {
	final static String[] PROPS = new String[] {
			javax.naming.Context.INITIAL_CONTEXT_FACTORY,
			javax.naming.Context.OBJECT_FACTORIES,
			javax.naming.Context.URL_PKG_PREFIXES,
			javax.naming.Context.STATE_FACTORIES,
			javax.naming.Context.PROVIDER_URL, javax.naming.Context.DNS_URL,
			// The following shouldn't create a runtime dependence on ldap
			// package.
			javax.naming.ldap.LdapContext.CONTROL_FACTORIES };
	
	private boolean getSystemPropsFailed =false;

	String getJndiProperty(final int i) {
		return (String) AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				try {
					return System.getProperty(PROPS[i]);
				} catch (SecurityException e) {
					return null;
				}
			}
		});
	}

	String[] getJndiProperties() {
		if (getSystemPropsFailed) {
			return null; // after one failure, don't bother trying again
		}
		Properties sysProps = (Properties) AccessController
				.doPrivileged(new PrivilegedAction() {
					public Object run() {
						try {
							return System.getProperties();
						} catch (SecurityException e) {
							getSystemPropsFailed = true;
							return null;
						}
					}
				});
		if (sysProps == null) {
			return null;
		}
		String[] jProps = new String[PROPS.length];
		for (int i = 0; i < PROPS.length; i++) {
			jProps[i] = sysProps.getProperty(PROPS[i]);
		}
		return jProps;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
