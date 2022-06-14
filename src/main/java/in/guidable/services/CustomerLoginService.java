package in.guidable.services;

import in.guidable.entities.Customer;
import in.guidable.exceptions.CustomerPresnetException;
import in.guidable.model.SignUpDTO;
import in.guidable.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerLoginService {

    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;

    public void verifyAndSaveCustomer(SignUpDTO signUpDTO){
        if(customerRepo.findByCustomerUserName(signUpDTO.getCustomerUserName()).isPresent()){
            throw new CustomerPresnetException("Username Already Present");
        }
        if(customerRepo.findByCustomerEmail(signUpDTO.getCustomerEmail()).isPresent()) {
            throw new CustomerPresnetException("Email Already Present for this customer");
        }

                try {
                    customerRepo.save(modelMapper.map(signUpDTO, Customer.class));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

    }

