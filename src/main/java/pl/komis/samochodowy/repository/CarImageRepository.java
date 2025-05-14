package pl.komis.samochodowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.komis.samochodowy.model.CarImage;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, Long> {

}