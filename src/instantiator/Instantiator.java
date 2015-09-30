package instantiator;

import java.util.HashMap;

public interface Instantiator {
	/**
	 * Asocia un criterio a una clase especifica.
	 * La manera de evaluar y comparar criterios depende de la implementacion
	 * 
	 * @param criteria Criterio para determinar si se debe instanciar con la clase dada 
	 * @param type Clase a instanciar
	 */
	public void register(Object criteria, Class<?> type);
	
	/**
	 * Inferir la clase a partir de los criterios registrados y de los atributos
	 * @param keyval Mapea nombre de atributo - valor
	 * @return objeto creado e inicializado
	 */
	public Object instantiate(HashMap<String, Object> keyval);
	
	/**
	 * Crear e inicializar un objeto de la clase pasada
	 * @param type Clase a instanciar
	 * @param keyval Mapa atributo - valor
	 * @return Instancia de la clase
	 */
	public <T> T instantiate (Class<T> type, HashMap<String, Object> keyval);
}
