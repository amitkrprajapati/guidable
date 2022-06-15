package in.guidable.services;

import in.guidable.entities.Customer;
import in.guidable.exceptions.CustomerPresnetException;
import in.guidable.model.SignUpDTO;
import in.guidable.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerValidationService {

    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void verifyAndSaveCustomer(SignUpDTO signUpDTO){
        if(customerRepo.findByCustomerUserName(signUpDTO.getCustomerUserName()).isPresent()){
            throw new CustomerPresnetException("Username Already Present");
        }
        if(customerRepo.findByCustomerEmail(signUpDTO.getCustomerEmail()).isPresent()) {
            throw new CustomerPresnetException("Email Already Present for this customer");
        }

                try {
                    signUpDTO.setCustomerPassword(passwordEncoder.encode(signUpDTO.getCustomerPassword()));
                    customerRepo.save(modelMapper.map(signUpDTO, Customer.class));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

    }

