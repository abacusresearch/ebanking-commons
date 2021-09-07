package ch.deeppay.service;


import ch.deeppay.models.transportdata.Transportable;
import org.springframework.stereotype.Service;

@Service
public interface SessionService<T extends Transportable> {

  T getSession();

  T getSession(T defaultSessionData);

  T getSession(String transportDataAsString);

  T getSession(String transportDataAsString, T defaultSessionData);

  String storeSession(T sessionData);


}