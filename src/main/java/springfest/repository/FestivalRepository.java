package springfest.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import springfest.model.Festival;

public interface FestivalRepository extends CrudRepository<Festival, Integer> {

	Collection<Festival> findAll();

	@Query("SELECT f.name FROM Festival f ORDER BY f.name")
	Collection<String> findAllFestival();

	static void reducirEntradasrestantes(Festival festival) {
		Integer entradasRestantes = festival.getEntradasRestantes();
		festival.setEntradasRestantes(entradasRestantes-1);
	}

	@Query("SELECT f FROM Festival f where f.name =?1")
	Festival findFestivalByName(String name);

	@Query("SELECT fechaCom FROM Festival f where f.id =?1")
	LocalDate findStartDateFestival(int id);

	@Query("SELECT fechaFin FROM Festival f where f.id =?1")
	LocalDate findEndDateFestival(int id);

	@Query("SELECT f FROM Festival f where f.festivalAdmin.id =?1")
	Festival findFestivalByAdminId(Integer id);

}
