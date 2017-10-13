package proyectoEjemplo.tests;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import proyectoEjemplo.entidades.Empleado;

/**
 * En esta clase se hace un ejemplo sobre la diferencia entre el método persist y merge del entity manager
 * de Hibernate + JPA
 * @author jbuitrago
 *
 */
public class TestMergePersist {
	
	private static EntityManager em;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
	
	public static void main (String [] args) {
		
		em = emf.createEntityManager();
		Empleado e = new Empleado(10L, "Perez", "Pepito", new GregorianCalendar(1979,6,6).getTime());
		// scenario 1
		
		// em.getTransaction().begin();
		// em.persist(e); 
		// e.setApellidos("ApellidoModificado");
		// em.getTransaction().commit();
		// tran ends, and the row for someField is updated in the database

		// scenario 2
		
		//em.getTransaction().begin();
		//em.merge(e);
		//e.setApellidos("ApellidoModificado");
		//em.getTransaction().commit();
		// (you made the changes *after* merging)

		// scenario 3
		
		em.getTransaction().begin();
		Empleado e2 = em.merge(e);
		e2.setApellidos("ApellidoModificado");
		em.getTransaction().commit();
		//(the changes were made to e2, not e)
		
		imprimirTodo();
	}
	
	private static void imprimirTodo() {
		List<Empleado> empleados = (List<Empleado>) em.createQuery("FROM Empleado").getResultList();
		System.out.println("El número de empleados es de "+empleados.size());
		for (Empleado emp:empleados) {
			System.out.println(emp.toString());
		}
	}
	
	
	

}
