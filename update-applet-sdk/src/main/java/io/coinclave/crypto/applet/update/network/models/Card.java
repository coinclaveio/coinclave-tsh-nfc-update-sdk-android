package io.coinclave.crypto.applet.update.network.models;

import java.util.Objects;

/**
 * Card
 */

public class Card   {

  private String PAN;

  private ExpirationDate expirationDate;

  public Card PAN(String PAN) {
    this.PAN = PAN;
    return this;
  }

  /**
   * Get PAN
   * @return PAN
  */
  public String getPAN() {
    return PAN;
  }

  public void setPAN(String PAN) {
    this.PAN = PAN;
  }

  public Card expirationDate(ExpirationDate expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  /**
   * Get expirationDate
   * @return expirationDate
  */
  public ExpirationDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(ExpirationDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return Objects.equals(this.PAN, card.PAN) &&
        Objects.equals(this.expirationDate, card.expirationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(PAN, expirationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Card {\n");
    sb.append("    PAN: ").append(toIndentedString(PAN)).append("\n");
    sb.append("    expirationDate: ").append(toIndentedString(expirationDate)).append("\n");
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

