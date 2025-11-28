package JavaSpringbootTableMigration.JavaSpringbootTableMigration.service.impl;

import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dao.CustomerRepository;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dto.CustomerDTO;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.model.Customer;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Customer with email " + customerDTO.getEmail() + " already exists");
        }
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        // Check if email is being changed and if new email already exists
        if (!existingCustomer.getEmail().equals(customerDTO.getEmail()) &&
                customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new RuntimeException("Customer with email " + customerDTO.getEmail() + " already exists");
        }

        existingCustomer.setFirstName(customerDTO.getFirstName());
        existingCustomer.setLastName(customerDTO.getLastName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setCity(customerDTO.getCity());
        existingCustomer.setState(customerDTO.getState());
        existingCustomer.setZipCode(customerDTO.getZipCode());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setCity(customer.getCity());
        dto.setState(customer.getState());
        dto.setZipCode(customer.getZipCode());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        return dto;
    }

    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setState(dto.getState());
        customer.setZipCode(dto.getZipCode());
        return customer;
    }
}

