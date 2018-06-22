package io.pivotal.pcfaws.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@RestController
public class AWSS3Controller {

	@Value("${cloud.aws.s3.default-bucket}")
    private String bucket;
	
	@Value("${cloud.aws.region.static}")
    private String region;
	
	@Value("${cloud.aws.s3.access-key-id}")
    private String access_key_id;
	
	@Value("${cloud.aws.s3.access-key-secret}")
    private String secret_key_id;
	
	@GetMapping("/s3/resources")
    public ListObjectsV2Result getBucketResources() {
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(access_key_id, secret_key_id);				
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(region)
				.build();
		
		System.out.println("Listing objects");
		
		ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket).withMaxKeys(2);
        ListObjectsV2Result result;
		
		do {
            result = s3Client.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());
            }
            // If there are more than maxKeys keys in the bucket, get a continuation token
            // and list the next objects.
            String token = result.getNextContinuationToken();
            System.out.println("Next Continuation Token: " + token);
            req.setContinuationToken(token);
        } while (result.isTruncated());
		
		return result;
    }
}
