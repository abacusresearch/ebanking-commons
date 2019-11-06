package ch.deeppay.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClientRequest {

  private @NotNull String transportData;
}
