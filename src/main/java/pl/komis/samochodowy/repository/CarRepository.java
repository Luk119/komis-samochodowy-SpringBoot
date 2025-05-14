package pl.komis.samochodowy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.komis.samochodowy.model.Car;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByBrand(String brand);

    List<Car> findByYear(Integer year);

    List<Car> findByFuel(String fuel);

    List<Car> findByGearbox(String gearbox);

    @Query("SELECT c FROM Car c WHERE c.price >= :minPrice AND c.price <= :maxPrice")
    List<Car> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT c FROM Car c WHERE c.mileage >= :minMileage AND c.mileage <= :maxMileage")
    List<Car> findByMileageRange(@Param("minMileage") Integer minMileage, @Param("maxMileage") Integer maxMileage);

    @Query("SELECT c FROM Car c WHERE LOWER(c.brand) LIKE %:keyword% OR LOWER(c.model) LIKE %:keyword% OR CAST(c.year AS string) LIKE %:keyword%")
    List<Car> searchByKeyword(@Param("keyword") String keyword);
}