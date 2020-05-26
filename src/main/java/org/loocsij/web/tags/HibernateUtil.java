/**
 * 
 *//*

package org.loocsij.web.tags;

import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.*;

*
 * @author wengm
 *
public class HibernateUtil {
	private static Logger log = LogManager.getLogger(HibernateUtil.class);
	*/
/**
	 * 
	 *//*

	public HibernateUtil() {
		// TODO Auto-generated constructor stub
	}

	private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            log.error("Initial SessionFactory creation failed.",ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

	*/
/**
	 * @return void
	 * @author wengm
	 * @since 2009-1-11
	 *//*

	public static void main(String[] args) {
	}

}
*/
