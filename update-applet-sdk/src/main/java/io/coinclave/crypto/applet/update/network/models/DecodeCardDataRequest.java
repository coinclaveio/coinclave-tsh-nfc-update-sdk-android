package io.coinclave.crypto.applet.update.network.models;

import java.util.Objects;

/**
 * Decode card data request
 */

public class DecodeCardDataRequest   {

  private String data;

  public DecodeCardDataRequest data(String data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
  */
  
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public DecodeCardDataRequest cardKey() {
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DecodeCardDataRequest decodeCardDataRequest = (DecodeCardDataRequest) o;
    return Objects.equals(this.data, decodeCardDataRequest.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DecodeCardDataRequest {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

