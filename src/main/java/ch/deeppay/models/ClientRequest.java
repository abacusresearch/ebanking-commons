package ch.deeppay.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClientRequest {

  @NotNull private String transportData;
}
