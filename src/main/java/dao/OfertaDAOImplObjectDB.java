package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelo.OfertaLaboral;
import conexion.DBObjectDB; // Importamos la nueva clase de conexión

public class OfertaDAOImplObjectDB {

    private EntityManagerFactory emf = DBObjectDB.getEntityManagerFactory();

    //LISTAR
    public List<OfertaLaboral> listarOfertas() {
        EntityManager em = emf.createEntityManager();
        // Usamos una consulta para traer todos los objetos de la base de datos
        TypedQuery<OfertaLaboral> query = em.createQuery("SELECT o FROM OfertaLaboral o", OfertaLaboral.class);
        List<OfertaLaboral> lista = query.getResultList();

        em.close(); // Cerramos antes de devolver la lista
        return lista;
    }

    //INSERTAR
    public boolean insertar(OfertaLaboral oferta) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin(); // Empezamos la operación
            em.persist(oferta); // Guardamos el objeto
            em.getTransaction().commit(); // Confirmamos

            em.close(); // Cerramos siempre al terminar
            return true;
        } catch (Exception e) {
            mensajeExcepcion(e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            return false;
        }
    }

    //ELIMINAR
    public boolean eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            // Primero buscamos la oferta por su ID
            OfertaLaboral o = em.find(OfertaLaboral.class, id);
            if (o != null) {
                em.getTransaction().begin();
                em.remove(o); // La borramos
                em.getTransaction().commit();
                em.close();
                return true;
            }
            em.close();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            return false;
        }
    }


    //MODIFICAR
    public boolean modificar(OfertaLaboral oferta) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(oferta); // El merge busca el objeto y actualiza sus campos
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            return false;
        }
    }

    //CONSULTAR POR ID
    public OfertaLaboral consultarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        // Buscamos la oferta usando su clave primaria
        OfertaLaboral o = em.find(OfertaLaboral.class, id);

        em.close();
        return o;
    }

    private void mensajeExcepcion(Exception e) {
        System.out.println("HA OCURRIDO UN ERROR EN OBJECTDB:");
        System.out.println("Mensaje: " + e.getMessage());
    }
}