package com.empaques.desa.domain.service;

import com.empaques.desa.domain.dto.GastoOperativoDto;
import com.empaques.desa.domain.dto.GastoOperativoRequest;
import com.empaques.desa.domain.repository.GastoOperativoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GastoOperativoService {
    private final GastoOperativoRepository gastoOperativo;

    public GastoOperativoService(GastoOperativoRepository gastoOperativo) {
        this.gastoOperativo = gastoOperativo;
    }

    public List<GastoOperativoDto> getAll() {
        return gastoOperativo.getAll();
    }

    public Optional<GastoOperativoDto> getById(Integer id) {
        return gastoOperativo.getById(id);
    }

    public GastoOperativoDto save(GastoOperativoRequest req, String evidenciaUrl) {
        return gastoOperativo.save(req, evidenciaUrl);
    }

    public boolean delete(Integer id) {
        return gastoOperativo.delete(id);
    }
}
