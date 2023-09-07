package net.javaguides.departmentservice.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.departmentservice.dto.DepartmentDto;
import net.javaguides.departmentservice.entity.Department;
import net.javaguides.departmentservice.exception.ResourceNotFoundException;
import net.javaguides.departmentservice.mapper.DepartmentMapper;
import net.javaguides.departmentservice.repository.DepartmentRepository;
import net.javaguides.departmentservice.service.DepartmentService;
//import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    //private ModelMapper modelMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        // convert department dto to department jpa entity
        /*Department department = new Department(
                departmentDto.getId(),
                departmentDto.getDeptName(),
                departmentDto.getDeptDesc(),
                departmentDto.getDeptCode()
        );*/

        // converted department dto to department jpa entity using Model Mapper
        //Department department = modelMapper.map(departmentDto, Department.class);

        // converted department dto to department jpa entity using Map Struct
        Department department = DepartmentMapper.MAPPER.toEntity(departmentDto);

        Department savedDepartment = departmentRepository.save(department);
        /*DepartmentDto saveDepartmentDto = new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getDeptName(),
                savedDepartment.getDeptDesc(),
                savedDepartment.getDeptCode()
        );*/

        // converted department jpa entity to department dto using Model Mapper
        //DepartmentDto saveDepartmentDto = modelMapper.map(savedDepartment, DepartmentDto.class);

        // converted department jpa entity to department dto using Map Struct
        DepartmentDto savedDepartmentDto = DepartmentMapper.MAPPER.toDto(savedDepartment);
        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String deptCode) {
        Department department = departmentRepository.findByDeptCode(deptCode)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "Department Code", deptCode));
        /*DepartmentDto departmentDto = new DepartmentDto(
                department.getId(),
                department.getDeptName(),
                department.getDeptDesc(),
                department.getDeptCode()
        );*/

        // converted department jpa entity to department dto using Model Mapper
        //DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);

        // converted department jpa entity to department dto using Map Struct
        DepartmentDto departmentDto = DepartmentMapper.MAPPER.toDto(department);
        return departmentDto;
    }
}
