package com.homework.departmentcrud.services;

import com.homework.departmentcrud.dto.DepartmentDTO;
import com.homework.departmentcrud.entities.DepartmentEntity;
import com.homework.departmentcrud.exceptions.ResourceNotFoundException;
import com.homework.departmentcrud.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public DepartmentDTO createDepartment(DepartmentDTO inputDepartment) {
        DepartmentEntity toBeCreatedDepartment = modelMapper.map(inputDepartment, DepartmentEntity.class);
        DepartmentEntity savedDepartment = departmentRepository.save(toBeCreatedDepartment);
        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities.stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<DepartmentDTO> getDepartmentById(Long departmentId) {
        return departmentRepository.
                findById(departmentId).map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class));
    }

    public DepartmentDTO updateDepartmentById(DepartmentDTO departmentDTO, Long departmentId) {
        isExistById(departmentId);
        DepartmentEntity departmentEntity = modelMapper.map(departmentDTO, DepartmentEntity.class);
        departmentEntity.setId(departmentId);
        DepartmentEntity updatedDTO = departmentRepository.save(departmentEntity);
        return modelMapper.map(updatedDTO, DepartmentDTO.class);
    }

    public boolean deleteDepartmentById(Long departmentId) {
        isExistById(departmentId);
        departmentRepository.deleteById(departmentId);
        return true;
    }

    // Following method will determine if the department with id exist or not
    public boolean isExistById(Long departmentId) {
        boolean isExist = departmentRepository.existsById(departmentId);
        if(!isExist) throw new ResourceNotFoundException("Department not found with id - " + departmentId);
        return true;
    }
}
