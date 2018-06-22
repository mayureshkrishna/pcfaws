package io.pivotal.pcfaws.dynamodb;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

@EnableScan
@EnableScanCount
public interface CustomerRepository extends CrudRepository<Customer, String> {
	
	List<Customer> findAll(Pageable pageable);
	List<Customer> findByLastName(String lastName);
	
	
}