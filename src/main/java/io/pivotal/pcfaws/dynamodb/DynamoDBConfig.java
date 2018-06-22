package io.pivotal.pcfaws.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "io.pivotal.pcfaws.dynamodb")
public class DynamoDBConfig {

	@Value("${cloud.aws.dynamodb.access-key-id}")
	private String amazonAWSAccessKey;

	@Value("${cloud.aws.dynamodb.access-key-secret}")
	private String amazonAWSSecretKey;

	@Value("${cloud.aws.dynamodb.region.static}")
	private String region;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(region).build();
		return amazonDynamoDB;
	}

}