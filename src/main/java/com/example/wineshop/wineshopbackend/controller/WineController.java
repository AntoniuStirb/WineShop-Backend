package com.example.wineshop.wineshopbackend.controller;

import com.example.wineshop.wineshopbackend.dto.WineDTO;
import com.example.wineshop.wineshopbackend.dto.WineFilterDTO;
import com.example.wineshop.wineshopbackend.mapper.WineMapper;
import com.example.wineshop.wineshopbackend.service.WineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/wines")
@AllArgsConstructor
public class WineController {

    private final WineService wineService;
    private final WineMapper wineMapper;

    @GetMapping
    public ResponseEntity<LinkedList<WineDTO>> getAllWines(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        List<WineDTO> wines = wineService.getAllWines(page, size);
        return ResponseEntity.ok(new LinkedList<>(wines));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WineDTO> getWineById(@PathVariable Long id) {
        WineDTO wineDTO = wineService.getWineById(id);
        return ResponseEntity.ok(wineDTO);
    }

    @PostMapping
    public ResponseEntity<WineDTO> addWine(@RequestPart("wineDTO") WineDTO wineDTO, @RequestPart("image")MultipartFile image) {
        wineDTO.setImageFile(image);
        WineDTO addedWineDTO = wineService.addWine(wineDTO);
        return new ResponseEntity<>(addedWineDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WineDTO> updateWine(@PathVariable Long id, @RequestPart("wineDTO") WineDTO updatedWineDTO, @RequestPart("image")MultipartFile image) {
        updatedWineDTO.setImageFile(image);
        WineDTO updatedWine = wineService.updateWine(id, updatedWineDTO);
        return ResponseEntity.ok(updatedWine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWine(@PathVariable Long id) {
        boolean deleted = wineService.deleteWine(id);
        return ResponseEntity.ok(deleted ? "Wine deleted successfully!" : "Wine not found");
    }

    @GetMapping("/search")
    public ResponseEntity<List<WineDTO>> searchWines(@RequestParam String keyword) {
        List<WineDTO> searchedWines = wineService.searchWines(keyword);
        return ResponseEntity.ok(searchedWines);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<WineDTO>> filterWinesByPrice(@RequestParam(required = false) Double minPrice,
                                                            @RequestParam(required = false) Double maxPrice) {
        WineFilterDTO filterDTO = new WineFilterDTO();
        filterDTO.setMinPrice(minPrice);
        filterDTO.setMaxPrice(maxPrice);
        List<WineDTO> filteredWines = wineService.filterWinesByPrice(filterDTO);
        return ResponseEntity.ok(filteredWines);
    }

}