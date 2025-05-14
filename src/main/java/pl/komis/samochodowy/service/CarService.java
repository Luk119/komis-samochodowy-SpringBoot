package pl.komis.samochodowy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.komis.samochodowy.dto.CarDTO;
import pl.komis.samochodowy.model.Car;
import pl.komis.samochodowy.model.CarImage;
import pl.komis.samochodowy.repository.CarImageRepository;
import pl.komis.samochodowy.repository.CarRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarImageRepository carImageRepository;

    @Autowired
    public CarService(CarRepository carRepository, CarImageRepository carImageRepository) {
        this.carRepository = carRepository;
        this.carImageRepository = carImageRepository;
    }

    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CarDTO> getCarById(Long id) {
        return carRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<CarDTO> searchCars(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCars();
        }
        return carRepository.searchByKeyword(keyword.toLowerCase())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CarDTO> filterCars(String brand, Integer year, String fuel, String gearbox,
                                   BigDecimal minPrice, BigDecimal maxPrice,
                                   Integer minMileage, Integer maxMileage) {

        // Początkowa lista wszystkich samochodów
        List<Car> filteredCars = carRepository.findAll();

        // Filtrowanie według kryteriów
        if (brand != null && !brand.isEmpty()) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getBrand().equals(brand))
                    .collect(Collectors.toList());
        }

        if (year != null) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getYear().equals(year))
                    .collect(Collectors.toList());
        }

        if (fuel != null && !fuel.isEmpty()) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getFuel().equalsIgnoreCase(fuel))
                    .collect(Collectors.toList());
        }

        if (gearbox != null && !gearbox.isEmpty()) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getGearbox().equals(gearbox))
                    .collect(Collectors.toList());
        }

        if (minPrice != null && maxPrice != null) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getPrice().compareTo(minPrice) >= 0 && car.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());
        } else if (minPrice != null) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getPrice().compareTo(minPrice) >= 0)
                    .collect(Collectors.toList());
        } else if (maxPrice != null) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());
        }

        if (minMileage != null && maxMileage != null) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getMileage() >= minMileage && car.getMileage() <= maxMileage)
                    .collect(Collectors.toList());
        } else if (minMileage != null) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getMileage() >= minMileage)
                    .collect(Collectors.toList());
        } else if (maxMileage != null) {
            filteredCars = filteredCars.stream()
                    .filter(car -> car.getMileage() <= maxMileage)
                    .collect(Collectors.toList());
        }

        return filteredCars.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Pomocnicze metody do konwersji między encjami i DTO
    private CarDTO convertToDTO(Car car) {
        List<String> imagePaths = car.getImages().stream()
                .map(CarImage::getImagePath)
                .collect(Collectors.toList());

        return new CarDTO(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getEngine(),
                car.getGearbox(),
                car.getPower(),
                car.getFuel(),
                car.getYear(),
                car.getPrice(),
                car.getMileage(),
                car.getColor(),
                car.getDescription(),
                imagePaths
        );
    }
}