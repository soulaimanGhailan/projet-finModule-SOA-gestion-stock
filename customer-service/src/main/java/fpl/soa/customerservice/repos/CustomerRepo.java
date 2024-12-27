package fpl.soa.customerservice.repos;


import fpl.soa.customerservice.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepo extends MongoRepository<Customer, String> {

}
