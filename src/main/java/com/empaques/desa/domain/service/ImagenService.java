package com.empaques.desa.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImagenService {
    @Value("${app.upload.base:uploads}")
    private String uploadBase;

    public String guardar(MultipartFile archivo) throws IOException {
        return guardar(archivo, "bolsas");
    }

    public String guardar(MultipartFile archivo, String subcarpeta) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }
        String extension = StringUtils.getFilenameExtension(archivo.getOriginalFilename());
        String nombreArchivo = UUID.randomUUID() + (extension != null ? "." + extension : "");

        Path directorio = Paths.get(uploadBase, subcarpeta);
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }
        Path rutaCompleta = directorio.resolve(nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + subcarpeta + "/" + nombreArchivo;
    }
}
