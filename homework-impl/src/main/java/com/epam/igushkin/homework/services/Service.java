package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.dto.AbstractDTO;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

public interface Service<E, D extends AbstractDTO> {

    D create(D dto);

    D read(int id);

    List<D> readAll();

    D update(D dto);

    boolean delete(int id);

    E mapDTOToEntity (D dto);

    D mapEntityToDTO(E entity);
}
