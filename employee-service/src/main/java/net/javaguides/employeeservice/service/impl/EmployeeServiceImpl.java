package net.javaguides.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.employeeservice.dto.APIResponseDto;
import net.javaguides.employeeservice.dto.DepartmentDto;
import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.entity.Employee;
import net.javaguides.employeeservice.exception.EmailAlreadyExistsException;
import net.javaguides.employeeservice.exception.ResourceNotFoundException;
import net.javaguides.employeeservice.mapper.EmployeeMapper;
import net.javaguides.employeeservice.repository.EmployeeRepository;
import net.javaguides.employeeservice.service.APIClient;
import net.javaguides.employeeservice.service.EmployeeService;
//import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private APIClient apiClient;
    //private WebClient webClient;
    //private RestTemplate restTemplate;
    //private ModelMapper modelMapper;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Optional<Employee> employeeOpt = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeOpt.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exists for Employee");
        }

        // converted employee dto to employee jpa entity using Model Mapper
        //Employee employee = modelMapper.map(employeeDto, Employee.class);

        // converted employee dto to employee jpa entity using Map Struct
        Employee employee = EmployeeMapper.MAPPER.toEntity(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        // converted employee jpa entity to employee dto Model Mapper
        //EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);

        // converted employee jpa entity to employee dto Map Struct
        EmployeeDto savedEmployeeDto = EmployeeMapper.MAPPER.toDto(savedEmployee);
        return savedEmployeeDto;
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Employee", "id", employeeId);
        });

        // converted employee jpa entity to employee dto Model Mapper
        //EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        /*ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDeptCode(),
                DepartmentDto.class);
        DepartmentDto departmentDto = responseEntity.getBody();*/

       /* DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/"+employee.getDeptCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();*/
        DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDeptCode());

        // converted employee jpa entity to employee dto Map Struct
        EmployeeDto employeeDto = EmployeeMapper.MAPPER.toDto(employee);
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }
}
