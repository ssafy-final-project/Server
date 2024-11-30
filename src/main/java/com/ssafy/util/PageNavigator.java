package com.ssafy.util;

public class PageNavigator {
  private boolean startRange;
  private boolean endRange;
  private int totalCount;
  private int totalPageCount;
  private int currentPage;
  private int navSize;

  private String navString = "";

  private boolean consistency = true;

  public boolean isStartRange() {
    return startRange;
  }

  public void setStartRange(boolean startRange) {
    this.startRange = startRange;
    this.consistency = false;
  }

  public boolean isEndRange() {
    return endRange;
  }

  public void setEndRange(boolean endRange) {
    this.endRange = endRange;
    this.consistency = false;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    this.consistency = false;
  }

  public int getTotalPageCount() {
    return totalPageCount;
  }

  public void setTotalPageCount(int totalPageCount) {
    this.totalPageCount = totalPageCount;
    this.consistency = false;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
    this.consistency = false;
  }

  public int getNavSize() {
    return navSize;
  }

  public void setNavSize(int navSize) {
    this.navSize = navSize;
    this.consistency = false;
  }

  public void makeNavString() {
    int startPage = (currentPage - 1) / navSize * navSize + 1;
    int endPage = Math.min(startPage + navSize - 1, totalPageCount);

    StringBuilder builder = new StringBuilder();
    builder.append("		<ul class=\"pagination  justify-content-center\"> \n");
    builder.append("			<li class=\"page-item\" data-pg=\"1\"> \n");
    builder.append(
        "				<button onclick=\"javascript:listNotice(1)\" class=\"page-link\">최신</a> \n");
    builder.append("			</li> \n");
    builder.append("			<li class=\"page-item\" data-pg=\""
        + (this.startRange ? 1 : (startPage - 1)) + "\"> \n");
    builder.append("				<button onclick=\"javascript:listNotice("
        + (this.startRange ? 1 : (startPage - 1)) + ")\" class=\"page-link\">이전</a> \n");
    builder.append("			</li> \n");
    for (int i = startPage; i <= endPage; i++) {
      builder
          .append("			<li class=\"" + (currentPage == i ? "page-item active" : "page-item")
              + "\" data-pg=\"" + i + "\"><button onclick=\"javascript:listNotice(" + i
              + ")\" class=\"page-link\">" + i + "</a></li> \n");
    }
    builder.append("			<li class=\"page-item\" data-pg=\""
        + (this.endRange ? endPage : (endPage + 1)) + "\"> \n");
    builder.append("				<button onclick=\"javascript:listNotice("
        + (this.endRange ? endPage : (endPage + 1)) + ")\" class=\"page-link\">다음</a> \n");
    builder.append("			</li> \n");
    builder.append("			<li class=\"page-item\" data-pg=\"" + totalPageCount + "\"> \n");
    builder.append("				<button onclick=\"javascript:listNotice(" + totalPageCount
        + ")\" class=\"page-link\">마지막</a> \n");
    builder.append("			</li> \n");
    builder.append("		</ul> \n");
    this.navString = builder.toString();
    this.consistency = true;
  }

  @Override
  public String toString() {
    if (!this.consistency) {
      this.makeNavString();
    }
    return this.navString;
  }
}
