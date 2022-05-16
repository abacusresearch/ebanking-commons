package ch.deeppay.metrics;

import ch.deeppay.util.FileFormat;

public interface MetricTagValueGetter {

  String getBankId();

  ClientType getClientType();

  String getClientId();

  FileFormat getFormat();
}
