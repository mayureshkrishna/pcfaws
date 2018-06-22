package io.pivotal.pcfaws.dynamodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;

@RestController
public class AWSDynamoDBController {

    private DynamoDBMapper dynamoDBMapper;
    
	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	
	@Autowired
	CustomerRepository repository;
	
	  @GetMapping("/dynamo/init")
	  public String init() throws Exception {
		  try {
	            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

	            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Customer.class);

	            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

	            amazonDynamoDB.createTable(tableRequest);
	        } catch (ResourceInUseException e) {
	            // Do nothing, table already created
	        }

	        dynamoDBMapper.batchDelete((List<Customer>) repository.findAll());
	       
	        List<Customer> customers = new ArrayList<>();
	        Customer customer = new Customer();
	        customer.setFirstName("John");
	        customer.setLastName("Doe");
	        customers.add(customer);
	        
	        Customer customer1 = new Customer();
	        customer1.setFirstName("Tom");
	        customer1.setLastName("Hanks");
	        customers.add(customer1);
	        
	        dynamoDBMapper.batchSave(customers);
	        
	        return "Success";
	    }
	  
	  @GetMapping("/dynamo/customers")
	  public List<Customer> getAllCustomers() {
		  List<Customer> customers = (List<Customer>) repository.findAll();
		  return customers;
	  }
	  
	  @GetMapping("/dynamo/customers/{lastName}")
	  public List<Customer> getCustomersByLastName(@PathVariable(value="lastName") String lastName) {
		  List<Customer> customers = (List<Customer>) repository.findByLastName(lastName);
		  return customers;
	  }
	  
}
