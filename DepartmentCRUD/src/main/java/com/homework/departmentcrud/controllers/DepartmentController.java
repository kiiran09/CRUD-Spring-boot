package com.homework.departmentcrud.controllers;

import com.homework.departmentcrud.dto.DepartmentDTO;
import com.homework.departmentcrud.exceptions.ResourceNotFoundException;
import com.homework.departmentcrud.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /*
    * Create Department
    * */
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @Valid DepartmentDTO inputDepartment) {
        DepartmentDTO createdDepartment = departmentService.createDepartment(inputDepartment);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    /*
    * Get All Departments
    * */
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    /*
    * Get Department By ID
    * */
    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        Optional<DepartmentDTO> gotDepartment = departmentService.getDepartmentById(departmentId);
        return gotDepartment
                .map(departmentDTO -> ResponseEntity.ok(departmentDTO))
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id - " + departmentId));
    }

    /*
    * Update Department By Id
    * */
    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartmentById(@RequestBody DepartmentDTO departmentDTO, @PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentService.updateDepartmentById(departmentDTO, departmentId));
    }

    /*
    * Delete Department By ID
    * */
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable Long departmentId) {
        boolean isDeleted = departmentService.deleteDepartmentById(departmentId);
        return ResponseEntity.ok(isDeleted);
    }
}
