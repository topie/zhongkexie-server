package com.topie.zhongkexie.core.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DateEqualDto{
	Date start;
	Date end;
	
	public Date getStart() {
		return start;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setEnd(Date end) {
		this.end = end;
	}
	
}
