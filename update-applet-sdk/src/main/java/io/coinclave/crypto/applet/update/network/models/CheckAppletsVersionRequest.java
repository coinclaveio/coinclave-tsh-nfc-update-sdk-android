package io.coinclave.crypto.applet.update.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CheckAppletsVersionRequest
 */

public class CheckAppletsVersionRequest {

  @SerializedName("sessionId")
  private String sessionId;

  @SerializedName("commandResponses")
  private List<APDUCommandResponse> commandResponses = null;

  public CheckAppletsVersionRequest sessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  /**
   * Identifier of session
   * @return sessionId
  */
  
  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public CheckAppletsVersionRequest commandResponses(List<APDUCommandResponse> commandResponses) {
    this.commandResponses = commandResponses;
    return this;
  }

  public CheckAppletsVersionRequest addCommandResponsesItem(APDUCommandResponse commandResponsesItem) {
    if (this.commandResponses == null) {
      this.commandResponses = new ArrayList<APDUCommandResponse>();
    }
    this.commandResponses.add(commandResponsesItem);
    return this;
  }

  /**
   * Get commandResponses
   * @return commandResponses
  */
  public List<APDUCommandResponse> getCommandResponses() {
    return commandResponses;
  }

  public void setCommandResponses(List<APDUCommandResponse> commandResponses) {
    this.commandResponses = commandResponses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CheckAppletsVersionRequest checkAppletsVersionRequest = (CheckAppletsVersionRequest) o;
    return Objects.equals(this.sessionId, checkAppletsVersionRequest.sessionId) &&
        Objects.equals(this.commandResponses, checkAppletsVersionRequest.commandResponses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sessionId, commandResponses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckAppletsVersionRequest {\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    commandResponses: ").append(toIndentedString(commandResponses)).append("\n");
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

