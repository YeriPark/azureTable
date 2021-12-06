package com.developery.azure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableQuery.Operators;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;
import com.microsoft.azure.storage.table.TableResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlarmStorageTableService {
	
	String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=yeritable;AccountKey=qYXnXv9TRq/qnyQfzu8JYxdB6FNNPhWVovveSrGq9iDPMlNxm8kucETLGgm/U/1579W3kCvisyIprlStbVVIrQ==;EndpointSuffix=core.windows.net";
	
	
	public boolean createTable(String tableName)
	{
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    CloudTable cloudTable = tableClient.getTableReference(tableName);
		    return cloudTable.createIfNotExists();
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return false;
	}

	public List<String> getTableList() {

	    List<String> resList = new ArrayList<>();
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    
		    // Loop through the collection of table names.
		    for (String table : tableClient.listTables())
		    {
		        // Output each table name.
		        System.out.println(table);
		        resList.add(table);
		    }
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		
		return resList;
	}
	
	public AlarmVO getAlarm(String partitionKey, String rowKey) {

		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("alarm");
		    
		    TableOperation retrieveOperation = TableOperation.retrieve(partitionKey, rowKey, AlarmVO.class);
		    
		    
		    // Submit the operation to the table service.
		    TableResult res = cloudTable.execute(retrieveOperation);
		    
		    AlarmVO alarm = res.getResultAsType();
		    
		    //log.info("alarm: {}", alarm);
		    
		    
		    
		    return alarm;
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return null;
	
	}
	
	public ArrayList<AlarmVO> searchAlarm(String alarm_nm, String rcv_nm) {

		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("alarm");
		    
		    String alarmFilter = TableQuery.generateFilterCondition(
		    		"Alarm_nm",
		            QueryComparisons.EQUAL,
		            alarm_nm);
		    
		    String rcvFilter = TableQuery.generateFilterCondition(
		            "Rcv_nm",
		            QueryComparisons.EQUAL,
		            rcv_nm);
		    
		    String combinedFilter = TableQuery.combineFilters(alarmFilter,
		            Operators.AND, rcvFilter);
		    
		    //log.info("combinedFilter: " + combinedFilter);
		    
		    TableQuery<AlarmVO> rangeQuery =
		            TableQuery.from(AlarmVO.class)
		            .where(combinedFilter);
		    
		    Iterable<AlarmVO> iter = cloudTable.execute(rangeQuery);
		    
		    ArrayList<AlarmVO> list = Lists.newArrayList(iter);
	        // Loop through the results, displaying information about the entity
	        for (AlarmVO entity : list) {
	            ////log.info("name: {}, age: {}", entity.getAlarm_nm(), entity.getRcv_nm(), entity.getCreate_user());
	        }
		    
		    
		    
		    return list;
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return null;
	
	}
	
	
	public String insertAlarm(AlarmVO p)
	{
		System.out.println(p);
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("alarm");
		    // unique 한 값조합이 되도록 아래 2개 세팅
		    p.setPartitionKey(p.getAlarm_id());
		    p.setRowKey(p.getAlarm_id());

		    TableOperation insertCustomer1 = TableOperation.insertOrReplace(p);		    

		    // Submit the operation to the table service.
		    TableResult res = cloudTable.execute(insertCustomer1);		    
		    
		    System.out.println(res.getHttpStatusCode());
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return p.getAlarm_id() + "/" + p.getAlarm_id();
	}
}
