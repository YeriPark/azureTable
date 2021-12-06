package com.developery.azure;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/alarmStorageTable")
public class AlarmStorageTableController {

	@Autowired
	AlarmStorageTableService service;
	
	@PostMapping("/table/{tableName}")
	public String createTable(
			@PathVariable("tableName") String tableName) throws IOException {
		
		boolean res = service.createTable(tableName);
		
		return "{\"res\": " + res + "}";
	}
	
	@GetMapping("/table")
	public List<String> getTableList( ) throws IOException {
		
		return service.getTableList();
	}
	
	@PostMapping("/insert/alarm")
	public String insertAlarm(@RequestBody AlarmVO p ) throws IOException {
		
		return service.insertAlarm(p);
	}
	
	@GetMapping("/alarm")
	public AlarmVO getAlarm(@RequestParam("partitionKey") String partitionKey, @RequestParam("rowKey") String rowKey)  {
		
		return service.getAlarm(partitionKey, rowKey);
	}
	
	@GetMapping("/search/alarm")
	public List<AlarmVO> searchAlarm(@RequestParam("alarm_nm") String alarm_nm, @RequestParam("rcv_nm") String rcv_nm)  {
		
		return service.searchAlarm(alarm_nm, rcv_nm);
	}
}
