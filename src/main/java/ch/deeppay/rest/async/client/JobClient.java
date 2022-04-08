package ch.deeppay.rest.async.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "job")
public interface JobClient {

  @PostMapping(path = "/")
  ResponseEntity<Void> createJob(JobRequest jobRequest);


}