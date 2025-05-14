package pl.komis.samochodowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.komis.samochodowy.dto.CarDTO;
import pl.komis.samochodowy.service.CarService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*") // To umożliwiamy dostęp z różnych źródeł (np. frontendu)
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCars(@RequestParam(required = false) String keyword) {
        List<CarDTO> cars = carService.searchCars(keyword);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CarDTO>> filterCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String fuel,
            @RequestParam(required = false) String gearbox,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minMileage,
            @RequestParam(required = false) Integer maxMileage) {

        List<CarDTO> cars = carService.filterCars(brand, year, fuel, gearbox, minPrice, maxPrice, minMileage, maxMileage);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}