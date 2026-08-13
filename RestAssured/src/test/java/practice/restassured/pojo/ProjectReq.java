package practice.restassured.pojo;

import practice.restassured.dto.Leader;

public class ProjectReq {
  private String name;
  private String shortName;
  private String description;
  private Leader leader;

  public ProjectReq(String name, String shortName, String description, String leaderId) {
    this.name = name;
    this.shortName = shortName;
    this.description = description;
    this.leader = new Leader(leaderId);
  }

  public ProjectReq() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Leader getLeader() {
    return leader;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }
}
