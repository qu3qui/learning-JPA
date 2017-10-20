package proyectoEjemplo.tests;

import proyectoEjemplo.entidades.Autor;

public class AutorTest {
	
	public static void main (String [] args) {
		Autor autor = new Autor(1L,"juan","colombiano");
		System.out.println(autor.getId());
	}

}
