package com.developery.azure;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmVO extends TableServiceEntity {

	String alarm_id;
	String alarm_nm;
	String rcv_nm;
	String create_user;
	
	public String getAlarm_id() {
		return alarm_id;
	}
	public void setAlarm_id(String alarm_id) {
		this.alarm_id = alarm_id;
	}
	public String getAlarm_nm() {
		return alarm_nm;
	}
	public void setAlarm_nm(String alarm_nm) {
		this.alarm_nm = alarm_nm;
	}
	public String getRcv_nm() {
		return rcv_nm;
	}
	public void setRcv_nm(String rcv_nm) {
		this.rcv_nm = rcv_nm;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
}
