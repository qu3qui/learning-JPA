package proyectoEjemplo.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.GregorianCalendar;
import java.util.List;

import proyectoEjemplo.entidades.Direccion;
import proyectoEjemplo.entidades.Empleado;



public class EmpleadoTest {
	
	//@PersistenceContext(unitName = "Persistencia") -> Se usa cuando tenemos un contenedor y de ahi se extrae el contexto de persistencia
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
	
	public static void main (String [] args) {
		
		//Se crea el gestor de persistencia
		crearEmpleado();
		imprimirTodo();
	}
	
	private static void crearDireccion() {
		EntityManager manager = emf.createEntityManager();
		Direccion dir = new Direccion(1L,"Calle falsa","localidad","provincia","Colombia");
		manager.getTransaction().begin();
		manager.persist(dir);
		manager.getTransaction().commit();
		manager.close();
	}

	private static void crearEmpleado() {
		
		EntityManager manager = emf.createEntityManager();
		Empleado e = new Empleado(10L, "Sebastian", "Buitrago", LocalDate.of(1996, Month.FEBRUARY, 16),
				new Direccion(1L,"Calle falsa","localidad","provincia","Colombia"));
		manager.getTransaction().begin();
		manager.persist(e);
		manager.getTransaction().commit();
		manager.close();
	}
	
	private static void imprimirTodo() {
		EntityManager manager = emf.createEntityManager();
		List<Empleado> empleados = (List<Empleado>) manager.createQuery("FROM empleado").getResultList();
		System.out.println("El número de empleados es de "+empleados.size());
		for (Empleado emp:empleados) {
			System.out.println(emp.toString());
		}
		manager.close();
	}
	
	private static void actualizarEmpleado(Empleado e) {
		EntityManager manager = emf.createEntityManager();
		manager.getTransaction().begin();
		//Ayuda a persistir una entidad y me devuelve la entidad persistida en el entity manager
		e = manager.merge(e);
		e.setNombre("Nombre modificado por segunda vez");
		e.setApellidos("Apellido Modificado por segunda vez");
		manager.getTransaction().commit();
		imprimirTodo();
		manager.close();
		
	}
	
	//Elimina una entidad persistida
	private static void eliminarEntidad(Empleado e) {
		EntityManager manager = emf.createEntityManager();
		manager.getTransaction().begin();
		e = manager.merge(e);
		manager.remove(e);
		manager.getTransaction().commit();
		imprimirTodo();
		manager.close();	
	}

}
