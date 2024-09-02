package io.coinclave.crypto.applet.update.network.models;

import java.util.Objects;

/**
 * Date which cards use to expire
 */

public class ExpirationDate {

  private String month;

  private String year;

  public ExpirationDate month(String month) {
    this.month = month;
    return this;
  }

  /**
   * Get month
   * @return month
  */
  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public ExpirationDate year(String year) {
    this.year = year;
    return this;
  }

  /**
   * Get year
   * @return year
  */
  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExpirationDate expirationDate = (ExpirationDate) o;
    return Objects.equals(this.month, expirationDate.month) &&
        Objects.equals(this.year, expirationDate.year);
  }

  public String convertToString(){
      return new String(year + month);
  }

  @Override
  public int hashCode() {
    return Objects.hash(month, year);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExpirationDate {\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
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

