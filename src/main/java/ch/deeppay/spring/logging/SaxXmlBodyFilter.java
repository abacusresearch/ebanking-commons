package ch.deeppay.spring.logging;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.zalando.logbook.BodyFilter;

@Log4j2
public class SaxXmlBodyFilter implements BodyFilter {


  private final String replacement;
  private final Set<String> fields;
  private final DocumentBuilderFactory documentBuilderFactory;
  private final TransformerFactory transformerFactory;

  public SaxXmlBodyFilter(final String replacement,
                          final Set<String> fields,
                          final DocumentBuilderFactory documentBuilderFactory,
                          final TransformerFactory transformerFactory) {
    this.replacement = replacement;
    this.fields = fields;
    this.documentBuilderFactory = documentBuilderFactory;
    this.transformerFactory = transformerFactory;
  }

  public SaxXmlBodyFilter(final String replacement, final Set<String> fields) {
    this(replacement, fields, DocumentBuilderFactory.newInstance(), TransformerFactory.newInstance());
  }


  @Override
  public String filter(@Nullable final String contentType, final String body) {
    return isContentSupported(contentType) ? filter(body) : body;
  }

  private boolean isContentSupported(String contentType) {
    try {
      MediaType mediaType = MediaType.valueOf(contentType);
      return MediaType.APPLICATION_XML.equalsTypeAndSubtype(mediaType) || MediaType.TEXT_XML.equalsTypeAndSubtype(mediaType);
    } catch (Exception e) {
      log.error(e);
    }
    return false;
  }

  private String filter(final String body) {

    try {
      // Convert string to XML document
      DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = docBuilder.parse(new InputSource(new StringReader(body)));
      maskElements(document.getDocumentElement());
      return toXmlString(document);
    } catch (Exception e) {
      log.error(e);
    }
    return body;
  }

  private String toXmlString(Document document) throws TransformerException {
    DOMSource source = new DOMSource(document);
    StringWriter strWriter = new StringWriter(); //close not needed
    StreamResult result = new StreamResult(strWriter);
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.transform(source, result);
    return strWriter.getBuffer().toString();
  }

  private void maskElements(Node node) {
    NodeList nodeList = node.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node currentNode = nodeList.item(i);
      if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
        maskElements(currentNode);
      } else if (currentNode.getNodeType() == Node.TEXT_NODE) {
        String name = currentNode.getParentNode().getNodeName();
        if (name != null && fields.contains(StringUtils.lowerCase(name))) {
          currentNode.setTextContent(replacement);
        }
      }
    }
  }

}