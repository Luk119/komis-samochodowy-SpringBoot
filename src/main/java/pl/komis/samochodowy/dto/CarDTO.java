package pl.komis.samochodowy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private String engine;
    private String gearbox;
    private String power;
    private String fuel;
    private Integer year;
    private BigDecimal price;
    private Integer mileage;
    private String color;
    private String description;
    private List<String> images;
}