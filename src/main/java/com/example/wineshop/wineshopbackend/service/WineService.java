package com.example.wineshop.wineshopbackend.service;

import com.example.wineshop.wineshopbackend.dto.WineDTO;
import com.example.wineshop.wineshopbackend.dto.WineFilterDTO;
import com.example.wineshop.wineshopbackend.model.Wine;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WineService {
    List<WineDTO> getAllWines(int page, int size);

    WineDTO getWineById(Long id);

    WineDTO addWine(WineDTO wineDTO);

    WineDTO updateWine(Long id, WineDTO updatedWineDTO);

    boolean deleteWine(Long id);

    List<WineDTO> searchWines(String keyword);

    List<WineDTO> filterWinesByPrice(WineFilterDTO filterDTO);


}
