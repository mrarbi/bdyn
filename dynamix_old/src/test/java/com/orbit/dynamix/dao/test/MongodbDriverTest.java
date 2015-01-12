package com.orbit.dynamix.dao.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.service.IMatchingService;
import com.orbit.dynamix.utils.Utils;
import com.orbit.dynamix.vo.CreativeVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@ActiveProfiles("test")
public class MongodbDriverTest {
	
	@Autowired
	ICreativeService creativeService;
	
	@Autowired
	IMatchingService matchingService;

	@Test
	public void testConnect() {
		try {
			
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);
			
//			List<String> dbs = mongo.getDatabaseNames();
//			for (String db : dbs) {
//				System.out.println(db);
//			}

			DB db = mongo.getDB("mydb");
			
//			CreativeVO elt = creativeService.getCreativeVO(2);
			List<CreativeVO> creativeVOs = creativeService.getAllCreativesVO();

			for (CreativeVO elt : creativeVOs) {
//				DBCollection table = db.getCollection("crea_" + elt.getCreativeId());

				List<Map<String, String>> listMap = matchingService.getLignesAleratoire(0, elt);
				System.out.println("crea = " + elt.getCreativeId() + " ---- " + listMap.size());
//				for (Map<String, String> map : listMap) {
//					BasicDBObject document = new BasicDBObject();
//					for (String key : map.keySet()) {
//						document.put(key, map.get(key));
//					}
//					table.insert(document);
//				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertNull(e);
		}
	}
	
	
	public static void main(String[] args) {
		try {
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);
			
//			List<String> dbs = mongo.getDatabaseNames();
//			for (String db : dbs) {
//				System.out.println(db);
//			}

			DB db = mongo.getDB("mydb");

			DBCollection table = db.getCollection("crea_2");
			
			System.out.println(table.getCount());
			
//			BasicDBObject searchQuery = new BasicDBObject();
//			searchQuery.put("name", "mkyong");
			
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(Utils.getInteger(table.getCount()));
		 
			System.out.println(randomInt);
			
			DBCursor cursor = table.find().limit(-1).skip(randomInt);
		 
			String json = "";
			int i = 0;
			while (cursor.hasNext()) {
				i++;
				json = cursor.next().toString();
			}
			
			System.out.println(i);
			
//			String json = "{\"name\":\"mkyong\", \"age\":\"29\"}";
			 
			Map<String,Object> map = new HashMap<String,Object>(0);
			ObjectMapper mapper = new ObjectMapper();
		 
			try {
				//convert JSON string to Map
				map = mapper.readValue(json, new TypeReference<HashMap<String,Object>>(){});
		 
				System.out.println(map);
		 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			/*
			BasicDBObject document = new BasicDBObject();
			document.put("name", "mkyong");
			document.put("age", 30);
			document.put("createdDate", new Date());
			table.insert(document);
			
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "mkyong");
		 
			DBCursor cursor = table.find(searchQuery);
		 
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
			*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
}
