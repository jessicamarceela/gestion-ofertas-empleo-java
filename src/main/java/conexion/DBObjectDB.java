package conexion;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBObjectDB {

    // REQUISITO
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ejemplo.odb");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}