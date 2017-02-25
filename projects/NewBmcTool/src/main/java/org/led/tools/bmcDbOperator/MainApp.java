package org.led.tools.bmcDbOperator;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.led.tools.bmcDbOperator.entity.CDN;
import org.led.tools.bmcDbOperator.utils.ConfigLoader;
import org.led.tools.bmcDbOperator.utils.EntityManagerUtil;


public class MainApp<T>
{
    public <T> void testSqlCmd(String sqlCmd) {
        System.out.println("Run sql command: " + sqlCmd);
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Collection<T> result = em.createQuery(sqlCmd).getResultList();        
        
        for(Iterator<T> it = result.iterator(); it.hasNext();) {
            T item = it.next();
            System.out.println(item.toString());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
        
    private void addCDN() {
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("bmc");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            CDN cdn = new CDN("cdn2", "a2", 34, "b2", "c2", "d1", "e1", "f1", "g1", "h1", "i1", "j1", "k1", "l1", "m1");
            em.persist(cdn);
            em.getTransaction().commit();
            System.out.println("addCDN completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }        
    }
    
    public static void main( String[] args )
    {
        String sqlCmd = ConfigLoader.getValue("sql_cmd");
        MainApp<CDN> app = new MainApp<CDN>();
        app.addCDN();
        //app.testSqlCmd(sqlCmd);
        
    }
}
