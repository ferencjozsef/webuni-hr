package hu.webuni.hr.ferencjozsef.dto;

import java.time.LocalDate;

public class SearchDayOffDto {

	private Boolean approved;
	private String createUser; 
	private String boss;
	private LocalDate createDateStart;
	private LocalDate createDateEnd;
	private LocalDate startDayOffDate;
	private LocalDate endDayOffDate;

	public SearchDayOffDto() {

	}

	public SearchDayOffDto(Boolean approved, String createUser, String boss, LocalDate createDateStart,
			LocalDate createDateEnd, LocalDate startDayOffDate, LocalDate endDayOffDate) {
		super();
		this.approved = approved;
		this.createUser = createUser;
		this.boss = boss;
		this.createDateStart = createDateStart;
		this.createDateEnd = createDateEnd;
		this.startDayOffDate = startDayOffDate;
		this.endDayOffDate = endDayOffDate;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public LocalDate getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(LocalDate createDateStart) {
		this.createDateStart = createDateStart;
	}

	public LocalDate getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(LocalDate createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public LocalDate getStartDayOffDate() {
		return startDayOffDate;
	}

	public void setStartDayOffDate(LocalDate startDayOffDate) {
		this.startDayOffDate = startDayOffDate;
	}

	public LocalDate getEndDayOffDate() {
		return endDayOffDate;
	}

	public void setEndDayOffDate(LocalDate endDayOffDate) {
		this.endDayOffDate = endDayOffDate;
	}

}
