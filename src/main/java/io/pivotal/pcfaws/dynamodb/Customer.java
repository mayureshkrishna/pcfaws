package io.pivotal.pcfaws.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Customer")
public class Customer {

  private String id;
  private String firstName;
  private String lastName;

  @DynamoDBHashKey
  @DynamoDBAutoGeneratedKey 
  public String getId() {
	return id;
  }

  @DynamoDBAttribute
  public String getFirstName() {
	return firstName;
  }

  @DynamoDBAttribute
  public String getLastName() {
	return lastName;
  }
       
  public void setId(String id) {
    this.id = id;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}