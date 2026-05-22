package mbemnova.taxi_manager.repository;


import mbemnova.taxi_manager.model.Course;
import mbemnova.taxi_manager.model.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour la gestion des courses.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByClientId(Long clientId);

    List<Course> findByChauffeurId(Long chauffeurId);

    List<Course> findByStatut(CourseStatus statut);

    List<Course> findByTaxiId(Long taxiId);

    long countByStatut(CourseStatus statut);

    /** Nombre de courses par type de véhicule */
    @Query("SELECT t.typeVehicule, COUNT(c) FROM Course c JOIN c.taxi t GROUP BY t.typeVehicule")
    List<Object[]> countCoursesParTypeVehicule();
}
