package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Entrada;
import org.springframework.samples.petclinic.model.EntradaType;

public interface EntradaRepository extends CrudRepository<Entrada, Integer> {

	Collection<Entrada> findAll();

	@Query("SELECT etype FROM EntradaType etype where etype.name = ?1")
	EntradaType findEntradaTypeByName(String entradatype);

	@Query("SELECT etype.name FROM EntradaType etype ORDER BY etype.name")
	List<String> findEntradaTypes() throws DataAccessException;

	@Query("SELECT e FROM Entrada e where e.festival.id = ?1")
	Collection<Entrada> findAllEntradasByFestivalId(int festivalId);

	@Query("select e from Entrada e where e.festival.id = ?1 and e.id = ?2")
	Entrada findByEntradaIdFestivalId(int festivalId, int entradaId);

	// @Query("select e from entrada_usuario eu inner join Entrada e on e.id=eu.id
	// where eu.ususario.id = ?1")
//	SELECT c.class_id, name
//    FROM student_classes sc 
//INNER JOIN classes c ON c.class_id = sc.class_id
//   WHERE sc.student_id = Y

	@Query("SELECT distinct entrada FROM Entrada entrada join entrada.usuario eu where eu.id  =  ?1")
//	select distinct distributor 
//	from Distributor distributor  
//	join distributor.towns town 
//	join town.district district 
//	where district.name = :name
	Collection<Entrada> findEntradasCompradasUsuario(int usuarioId);

}
