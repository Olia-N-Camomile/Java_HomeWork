
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {
    private static SessionFactory sessionFactory;
    public static void main() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    public static void addWarehouses(String name, int capacity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        Warehouses warehouse = new Warehouses(name, capacity);
        session.save(warehouse);
        transaction.commit();
        session.close();
    }
    public static void updateShoe(Shoe shoe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        Shoe shoeOld = (Shoe) session.get(Shoe.class, shoe.getCode());
        if(shoeOld==null || !shoeOld.getCode().equals(shoe.getCode())) {
            session.save(shoe);
            System.out.println("Записаны сведения об новой обуви");
        }
        else {
            shoeOld.setColor(shoe.getColor());
            shoeOld.setMaterial(shoe.getMaterial());
            shoeOld.setName(shoe.getName());
            shoeOld.setSize(shoe.getSize());
            session.update(shoeOld);
            System.out.println("Изменены сведения об обуви");
        }
        transaction.commit();
        session.close();
    }
}
