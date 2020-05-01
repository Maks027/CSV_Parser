import entities.EntityX;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
public class dbQuery {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("UnitX");

        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();

        EntityX a = new EntityX();
        a.setA("a");
        a.setB("b");
        a.setC("c");

        entityManager.persist(a);
        entityManager.getTransaction().commit();
        entityManager.close();
        emFactory.close();
    }
}
