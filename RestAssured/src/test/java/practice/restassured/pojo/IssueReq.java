package practice.restassured.pojo;

import practice.restassured.dto.Project;

public class IssueReq {
  private Project project;
  private String summary;

  public IssueReq(String projectId, String summary) {
    this.project = new Project(projectId);
    this.summary = summary;
  }

  public IssueReq() {}

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}