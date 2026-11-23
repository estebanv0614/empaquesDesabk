package com.empaques.desa.domain.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class ImagenService {
    private final Cloudinary cloudinary;

    public ImagenService(@Value("${cloudinary.url:}") String cloudinaryUrl) {
        this.cloudinary = (cloudinaryUrl != null && cloudinaryUrl.startsWith("cloudinary://"))
                ? new Cloudinary(cloudinaryUrl)
                : null;
    }

    public String guardar(MultipartFile archivo) throws IOException {
        return guardar(archivo, "bolsas");
    }

    public String guardar(MultipartFile archivo, String subcarpeta) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }
        if (cloudinary == null) {
            throw new IllegalStateException(
                    "Cloudinary no está configurado: define la variable CLOUDINARY_URL");
        }
        String tipo = archivo.getContentType();
        if (tipo == null || !tipo.startsWith("image/")) {
            throw new IllegalArgumentException("El archivo debe ser una imagen");
        }
        Map<?, ?> resultado = cloudinary.uploader().upload(
                archivo.getBytes(),
                ObjectUtils.asMap("folder", "empaques/" + subcarpeta, "resource_type", "image"));
        return (String) resultado.get("secure_url");
    }
}
