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
    @Value("${app.upload.dir:uploads/bolsas}")
    private String uploadDir;

    public String guardar(MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }
        String extension = StringUtils.getFilenameExtension(archivo.getOriginalFilename());
        String nameArchivo = UUID.randomUUID() + (extension != null ? "." + extension : "");

        Path directorio = Paths.get(uploadDir);
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }
        Path rutaCompleta = directorio.resolve(nameArchivo);
        Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/bolsas/" + nameArchivo;
    }
}
