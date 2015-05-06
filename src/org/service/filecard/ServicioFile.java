/**
 * 
 */
package org.service.filecard;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Andres Carmona Gil
 * Clase para la configuracion de la aplicacion para la escucha en el servidor
 */
@ApplicationPath("/")
public class ServicioFile extends Application {
	public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(ServiceFileCard.class);
        return s;
	}
}
