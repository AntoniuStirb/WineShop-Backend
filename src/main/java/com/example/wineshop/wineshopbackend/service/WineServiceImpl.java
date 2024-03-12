package com.example.wineshop.wineshopbackend.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import com.example.wineshop.wineshopbackend.dto.WineDTO;
import com.example.wineshop.wineshopbackend.dto.WineFilterDTO;
import com.example.wineshop.wineshopbackend.mapper.WineMapper;
import com.example.wineshop.wineshopbackend.model.Wine;
import com.example.wineshop.wineshopbackend.repository.WineRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WineServiceImpl implements WineService {
    private final WineRepository wineRepository;
    private final WineMapper wineMapper;

    @Autowired
    public WineServiceImpl(WineRepository wineRepository, WineMapper wineMapper) {
        this.wineRepository = wineRepository;
        this.wineMapper = wineMapper;
    }

    @Override
    public List<WineDTO> getAllWines(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Wine> winePage = wineRepository.findAll(pageable);

        return winePage.getContent().stream()
                .map(wineMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WineDTO getWineById(Long id) {
        Optional<Wine> optionalWine = wineRepository.findById(id);
        return optionalWine.map(wineMapper::mapToDTO).orElse(null);
    }

    @Override
    public WineDTO addWine(WineDTO wineDTO) {
        Wine wineToAdd = wineMapper.mapToEntity(wineDTO);
        Wine addedWine = wineRepository.save(wineToAdd);
        return wineMapper.mapToDTO(addedWine);
    }

    @Override
    public WineDTO updateWine(Long id, WineDTO updatedWineDTO) {
        Optional<Wine> optionalExistingWine = wineRepository.findById(id);

        if (optionalExistingWine.isPresent()) {
            Wine existingWine = optionalExistingWine.get();
            existingWine.setName(updatedWineDTO.getName());
            existingWine.setType(updatedWineDTO.getType());
            existingWine.setPrice(updatedWineDTO.getPrice());
            existingWine.setDescription(updatedWineDTO.getDescription());
            try {
                existingWine.setImage(updatedWineDTO.getImageFile().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Wine updatedWine = wineRepository.save(existingWine);
            return wineMapper.mapToDTO(updatedWine);
        }

        return null;
    }

    @Override
    public boolean deleteWine(Long id) {
        if (wineRepository.existsById(id)) {
            wineRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<WineDTO> searchWines(String keyword) {
        List<Wine> matchingWines = wineRepository.findByNameContainingIgnoreCase(keyword);
        return wineMapper.mapToDTOList(matchingWines);
    }

    @Override
    public List<WineDTO> filterWinesByPrice(WineFilterDTO filterDTO) {
        Double minPrice = filterDTO.getMinPrice();
        Double maxPrice = filterDTO.getMaxPrice();

        List<Wine> filteredWines;

        if (minPrice != null && maxPrice != null) {
            filteredWines = wineRepository.findByPriceBetween(minPrice, maxPrice);
        } else if (minPrice != null) {
            filteredWines = wineRepository.findByPriceGreaterThanEqual(minPrice);
        } else if (maxPrice != null) {
            filteredWines = wineRepository.findByPriceLessThanEqual(maxPrice);
        } else {
            filteredWines = wineRepository.findAll();
        }

        return wineMapper.mapToDTOList(filteredWines);
    }



}
