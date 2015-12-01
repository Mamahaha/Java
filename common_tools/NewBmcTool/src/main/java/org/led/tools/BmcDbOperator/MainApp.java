package org.led.tools.BmcDbOperator;

import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.led.tools.BmcDbOperator.entity.CDN;
import org.led.tools.BmcDbOperator.utils.ConfigLoader;
import org.led.tools.BmcDbOperator.utils.EntityManagerUtil;


public class MainApp<T>
{
    public <T> void testSqlCmd(String sqlCmd) {
        System.out.println("Run sql command: " + sqlCmd);
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Collection result = em.createQuery(sqlCmd).getResultList();        
        
        for(Iterator<T> it = result.iterator(); it.hasNext();) {
            T item = (T) it.next();
            System.out.println(item.toString());
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
        
    private static void addCDN() {
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("bmc");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            CDN cdn = new CDN("cdn1", "a1", 44, "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1", "k1", "l1", "m1");
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
        app.testSqlCmd(sqlCmd);
        //addCDN();
    }
}
