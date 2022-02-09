package hu.webuni.hr.ferencjozsef.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;

public class DayOffDto {

	private Long id;
	@Future
	private LocalDate startDate;
	@Future
	private LocalDate endDate;
	private String createUser;
	@NotEmpty
	private LocalDateTime createDate;
	private Boolean approved;
	private String boss;

	public DayOffDto() {

	}

	public DayOffDto(Long id, LocalDate startDate, LocalDate endDate, String createUser, LocalDateTime createDate,
			Boolean approved, String boss) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createUser = createUser;
		this.createDate = createDate;
		this.approved = approved;	
		this.boss = boss;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

}
