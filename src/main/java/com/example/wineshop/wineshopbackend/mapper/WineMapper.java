package com.example.wineshop.wineshopbackend.mapper;

import com.example.wineshop.wineshopbackend.dto.WineDTO;
import com.example.wineshop.wineshopbackend.model.Wine;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WineMapper {


    public WineDTO mapToDTO(Wine wine) {
        WineDTO wineDTO = new WineDTO();
        wineDTO.setId(wine.getId());
        wineDTO.setName(wine.getName());
        wineDTO.setType(wine.getType());
        wineDTO.setPrice(wine.getPrice());
        wineDTO.setDescription(wine.getDescription());
        wineDTO.setFileBytes(wine.getImage());
        return wineDTO;
    }

    public static Wine mapToEntity(WineDTO wineDTO) {
        Wine wine = new Wine();
        wine.setId(wineDTO.getId());
        wine.setName(wineDTO.getName());
        wine.setType(wineDTO.getType());
        wine.setPrice(wineDTO.getPrice());
        wine.setDescription(wineDTO.getDescription());
        try {
            wine.setImage(wineDTO.getImageFile() != null ? wineDTO.getImageFile().getBytes() : null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wine;
    }

    public List<WineDTO> mapToDTOList(List<Wine> wines) {
        return wines.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public static MultipartFile convert(byte[] bytes, String filename) throws IOException {
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);

        return new MultipartFile() {
            @Override
            public String getName() {
                return filename;
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return bytes == null || bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return byteArrayResource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                throw new UnsupportedOperationException("transferTo() is not supported for ByteArrayResource");
            }
        };
    }
}