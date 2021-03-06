package org.jaxrx.core;

import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;

/**
 * This class contains information on the resource path and query parameters of
 * a JAX-RX request.
 * @author Sebastian Graf, Christian Gruen, Lukas Lewandowski, University of
 *         Konstanz
 */
@Path(JaxRxConstants.ROOTPATH)
public final class ResourcePath {
  /** Query parameters. */
  private final Map<QueryParameter, String> params;
  /** Resource path. */
  private final String[] resource;
  /** HTTP header attributes. */
  private final HttpHeaders headers;

  /**
   * Constructs a new {@code ResourcePath}.
   * @param resourcePath resource path string
   */
  public ResourcePath(final String resourcePath) {
    this(resourcePath, null, null);
  }

  /**
   * Constructs a new {@code ResourcePath} with an additional
   * {@link QueryParameter} map.
   * @param resourcePath resource path string
   * @param queryParameters query parameters
   */
  public ResourcePath(final String resourcePath,
      final Map<QueryParameter, String> queryParameters) {

    this(resourcePath, queryParameters, null);
  }

  /**
   * Constructs a new {@code ResourcePath} with an additional
   * {@link QueryParameter} map.
   * @param resourcePath resource path string
   * @param httpHeaders HTTP header attributes.
   */
  public ResourcePath(final String resourcePath, final HttpHeaders httpHeaders) {
    this(resourcePath, null, httpHeaders);
  }

  /**
   * Constructs a new {@code ResourcePath} with an additional
   * {@link QueryParameter} map.
   * @param resourcePath resource path string
   * @param queryParameters query parameters
   * @param httpHeaders HTTP header attributes.
   */
  public ResourcePath(final String resourcePath,
      final Map<QueryParameter, String> queryParameters,
      final HttpHeaders httpHeaders) {

    // chop trailing slashes, ignore empty steps
    final String[] rp = resourcePath.replaceAll("/+$", "").split("/");
    resource = rp.length == 1 && rp[0].isEmpty() ? new String[] { } : rp;
    params = queryParameters;
    headers = httpHeaders;

  }

  /**
   * Returns the complete resource path string.
   * @return resource path string
   */
  public String getResourcePath() {
    final StringBuilder sb = new StringBuilder();
    for(final String r : resource)
      sb.append(r + '/');
    return sb.substring(0, Math.max(0, sb.length() - 1));
  }

  /**
   * Returns the resource at the specified level.
   * @param level resource level
   * @return resource
   */
  public String getResource(final int level) {
    if(level < resource.length) {
      return resource[level];
    }
    // offset is out of bounds...
    throw new IndexOutOfBoundsException("Index: " + level + ", Size: "
        + resource.length);
  }

  /**
   * Returns the depth of the resource path.
   * @return resource depth
   */
  public int getDepth() {
    return resource.length;
  }

  /**
   * Returns the value of the specified query parameter, or {@code null} if no
   * value is mapped.
   * @param key query key
   * @return value
   */
  public String getValue(final QueryParameter key) {
    return params != null ? params.get(key) : null;
  }

  /**
   * This map return all available query parameters.
   * @return The parameter map.
   */
  public Map<QueryParameter, String> getQueryParameter() {
    return params;
  }

  /**
   * Return HTTP headers from user request.
   * @return HTTP headers.
   */
  public HttpHeaders getHttpHeaders() {
    return headers;
  }

  @Override
  public String toString() {
    return getResourcePath();
  }
}
