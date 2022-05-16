package ch.deeppay.rest.async;

import java.util.concurrent.Executor;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Log4j2
@Data
@Configuration
@EnableAsync
@ConditionalOnProperty(value = "ch.deeppay.job.enabled", matchIfMissing = false)
public class AsyncConfiguration {

  public static final String BUCKET_NAME_PREFIX = "job-";
  public static final String EXECUTOR_NAME = "restAsyncExecutor";
  public static final String MINIO_CLIENT_NAME = "restAsyncMinioClient";

  @Value("${ch.deeppay.job.executor.queueCapacity:100}")
  private int queueCapacity;

  @Value("${ch.deeppay.job.executor.maxPoolSize:10}")
  private int maxPoolSize;

  @Value("${ch.deeppay.job.executor.corePoolSize:5}")
  private int corePoolSize;

  @Value("${ch.deeppay.job.response.timeout:45}")
  private int responseTimeout;

  @Value("${ch.deeppay.job.minio.access.key}")
  private String minioAccessKey;

  @Value("${ch.deeppay.job.minio.access.secret}")
  private String minioAccessSecret;

  @Value("${ch.deeppay.job.minio.encryption.secret}")
  private String minioEncryptionSecret;

  @Value("${ch.deeppay.job.minio.url}")
  private String minioUrl;

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${ch.deeppay.job.minio.bucketValidation:true}")
  private boolean bucketValidation;

  @Value("${ch.deeppay.job.minio.bucketName:}")
  private String bucketName;

  @Bean(name = EXECUTOR_NAME)
  public Executor restAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setThreadNamePrefix("RestAsyncThread-");
    executor.initialize();
    return executor;
  }

  @Bean(name = MINIO_CLIENT_NAME )
  public MinioClient getMinioClient() {

    try {
      MinioClient minioClient =
          MinioClient.builder()
                     .credentials(minioAccessKey, minioAccessSecret)
                     .endpoint(minioUrl)
                     .build();

      if(bucketValidation) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(getMinioBucketName()).build())) {
          throw new Exception("Bucket is required. Define a valid bucket name at ch.deeppay.rest.async.minio.bucket.name");
        } else {
          log.debug("Minio-Service update and running");
        }
      }else{
        log.warn("Bucket validation disabled");
      }
      return minioClient;
    } catch (Exception e) {
      throw new UnsatisfiedDependencyException("Minio Bucket not available or accessible!", AsyncConfiguration.class.getSimpleName(), "", "Minio Bucket not available or accessible!");
    }
  }

  public String getMinioBucketName(){
    return StringUtils.isEmpty(bucketName) ? BUCKET_NAME_PREFIX + applicationName : bucketName;
  }

  public static String getBucketName(String applicationName){
    return BUCKET_NAME_PREFIX + applicationName;
  }

}