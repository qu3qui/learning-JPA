package proyectoEjemplo.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import java.util.GregorianCalendar;
import java.util.List;
import proyectoEjemplo.entidades.Empleado;



public class EmpleadoTest {
	
	//@PersistenceContext(unitName = "Persistencia") -> Se usa cuando tenemos un contenedor y de ahi se extrae el contexto de persistencia
	private static EntityManager manager;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
	
	public static void main (String [] args) {
		
		//Se crea el gestor de persistencia
		manager = emf.createEntityManager();
		
		crearRegistro();
		
		imprimirTodo();
		
		manager.getTransaction().begin();
		Empleado e = manager.find(Empleado.class, 10L);
		e.setNombre("Nombre modificado");
		e.setApellidos("Apellido Modificado");
		manager.getTransaction().commit();
		
		imprimirTodo();
		//Esta sentencia se utiliza para cerrar el manager y liberar recursos, Importante revisar esto
		manager.close();
	}

	private static void crearRegistro() {
		Empleado e = new Empleado(10L, "Perez", "Pepito", new GregorianCalendar(1979,6,6).getTime());
		
		manager.getTransaction().begin();
		manager.persist(e);
		manager.getTransaction().commit();
	}
	
	private static void imprimirTodo() {
		List<Empleado> empleados = (List<Empleado>) manager.createQuery("FROM empleado").getResultList();
		System.out.println("El número de empleados es de "+empleados.size());
		for (Empleado emp:empleados) {
			System.out.println(emp.toString());
		}
	}

}
