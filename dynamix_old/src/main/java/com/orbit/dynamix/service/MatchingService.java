package com.orbit.dynamix.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.orbit.dynamix.utils.PositionalXMLReader;
import com.orbit.dynamix.utils.Utils;
import com.orbit.dynamix.utils.XMLUtils;
import com.orbit.dynamix.vo.CreativeVO;
import com.orbit.dynamix.vo.Pair;

/**
 * 
 * @author m.arbi
 *
 */
@Service
@Transactional
public class MatchingService implements IMatchingService {

	private static Logger log = LoggerFactory.getLogger(MatchingService.class);
	
	@Autowired
	MongoTemplate mongo;
//	Mongo mongo;
	
//	@Value("#{matchingService.fillMapEntries()}")
//	private static Map<Integer, List<Map<String, String>>> mapLigneEntries = new HashMap<Integer, List<Map<String,String>>>(0); 
	
	@Autowired
	ICreativeService creativeService;
	
	/**
	 * 
	 * @param file
	 * @param creativeVO
	 * @return
	 * @throws Exception
	 */
	public List<List<Pair<String, String>>> getDataList(CreativeVO creativeVO) throws Exception {
		List<List<Pair<String, String>>> dataList = new ArrayList<List<Pair<String, String>>>(0);
		try {
			File file = new File("/data/uploads/" + creativeVO.getUserId() + "/" + creativeVO.getCreativeId() + "/" + creativeVO.getFichier());
			if (null != file) {
				if ("xml".equalsIgnoreCase(creativeVO.getTypeFichier())) {
					for (int i = 1; i < 3; i++) {
						List<String> list = null;
						try {
							list = XMLUtils.readXPath(file, creativeVO.getXpath() + "[" + i + "]");
						} catch (SAXParseException e) {
							break;
						}
						if (null != list && !list.isEmpty()) {
							List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>(0);
							int j = 0;
							for (String elt : list) {
								String id = StringUtils.substring(elt, StringUtils.lastIndexOf(elt, "/") + 1, StringUtils.indexOf(elt, "="));
								String value = StringUtils.substring(elt, StringUtils.indexOf(elt, "=") + 1);
								Pair<String, String> pair = new Pair<String, String>(Utils.getString(++j), value);
								pairs.add(pair);
							}
							dataList.add(pairs);
						}
					}

				} else if ("csv".equalsIgnoreCase(creativeVO.getTypeFichier())) {
					String cvsSplitBy = creativeVO.getSeparateur();
					String line = "";
					BufferedReader br = null;
					// URL lien = new URL(PROGRAMME_DATA_FILE);
					// br = new BufferedReader(new
					// InputStreamReader(lien.openStream()));
					br = new BufferedReader(new FileReader(file));
					int iter = 0;
					while ((line = br.readLine()) != null && iter < 2) {
						// tronquer les colonnes longues
						List<String> listData = Arrays.asList(line.split(cvsSplitBy));
						List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>(0);
						int i = 0;
						if (null != listData && !listData.isEmpty()) {
							// int dataSize = listData.size();
							for (String elt : listData) {
								i++;
								String c = Utils.getString(i);
								if (StringUtils.isNotEmpty(elt) && elt.length() > 60) {
									elt = StringUtils.substring(elt, 0, 60);
									elt += "...";
								}
								Pair<String, String> pair = new Pair<String, String>(c, elt);
								pairs.add(pair);
							}
						}
						dataList.add(pairs);
						iter++;
					}
					br.close();
				}
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return dataList;
	}
	
	/**
	 * 
	 * @param file
	 * @param creativeVO
	 * @return
	 * @throws Exception
	 */
	public List<List<Pair<String, String>>> getDataListFromUrl(CreativeVO creativeVO) throws Exception {
		List<List<Pair<String, String>>> dataList = new ArrayList<List<Pair<String, String>>>(0);
		try {
			String cvsSplitBy = creativeVO.getSeparateur();
			String line = "";
			BufferedReader br = null;
			if (StringUtils.isNotEmpty(creativeVO.getUrl())) {
				if (StringUtils.isNotEmpty(creativeVO.getLoginUrl())) {

					String authString = creativeVO.getLoginUrl() + ":" + creativeVO.getPasswdUrl();

					byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
					String authStringEnc = new String(authEncBytes);

					URL url = new URL(creativeVO.getUrl());
					URLConnection urlConnection = url.openConnection();
					urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
					InputStream is = urlConnection.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					br = new BufferedReader(isr);
				} else {
					URL lien = new URL(creativeVO.getUrl());
					br = new BufferedReader(new InputStreamReader(lien.openStream()));
				}

				int iter = 0;
				while ((line = br.readLine()) != null && iter < 3) {
					if (iter == 0) {
						iter++;
						continue;
					}
					// tronquer les colonnes longues
					List<String> listData = Arrays.asList(line.split(cvsSplitBy));
					List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>(0);
					int i = 0;
					if (null != listData && !listData.isEmpty()) {
						// int dataSize = listData.size();
						for (String elt : listData) {
							i++;
							String c = Utils.getString(i);
							if (StringUtils.isNotEmpty(elt) && elt.length() > 60) {
								elt = StringUtils.substring(elt, 0, 60);
								elt += "...";
							}
							Pair<String, String> pair = new Pair<String, String>(c, elt);
							pairs.add(pair);
						}
					}
					dataList.add(pairs);
					iter++;
				}
				br.close();
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return dataList;
	}
	
	@PostConstruct
	public void fillMapEntries() throws Exception {
		try {
//			List<CreativeVO> creativeVOs = new ArrayList<CreativeVO>(0);
			List<CreativeVO> creativeVOs = creativeService.getAllCreativesVO();
//			List<Creative> list = creativeDAO.findAll();
//			if (null != list && !list.isEmpty()) {
//				for (Creative creative : list) {
//					CreativeVO creativeVO = new CreativeVO();
//					BeanUtils.copyProperties(creativeVO, creative);
//					creativeVOs.add(creativeVO);
//				}
//			}
			for (CreativeVO elt: creativeVOs) {
//				fillCreaInMapEntries(elt);
				fillDataIntoMongo(elt);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
		}
	}
	
	public void fillDataIntoMongo(CreativeVO elt) {
		try {
			// Since 2.10.0, uses MongoClient
//			MongoClient mongo = new MongoClient("localhost", 27017);

			DB db = mongo.getDb();
			
			db.getCollection("crea_" + elt.getCreativeId()).drop();
			DBCollection table = db.getCollection("crea_" + elt.getCreativeId());

			List<Map<String, String>> listMap = getLignesAleratoire(0, elt);
			System.out.println("crea = " + elt.getCreativeId() + " ---- " + listMap.size());
			for (Map<String, String> map : listMap) {
				BasicDBObject document = new BasicDBObject();
				for (String key : map.keySet()) {
					document.put(key, map.get(key));
				}
				table.insert(document);
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
		}
	}

//	public void fillCreaInMapEntries(CreativeVO creativeVO) {
//		try {
//			mapLigneEntries.put(creativeVO.getCreativeId(), getLignesAleratoire(0, creativeVO));
//		} catch (Exception e) {
//			log.error("Erreur : " + e.getMessage(), e);
//		}
//	}
	
//	public void removeCreaFromMapEntries(Integer idCrea) {
//		try {
//			mapLigneEntries.remove(idCrea);
//		} catch (Exception e) {
//			log.error("Erreur : " + e.getMessage(), e);
//		}
//	}
	
	public void removeCreaFromMongo(Integer idCrea) {
		try {
			
//			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDb();
			db.getCollection("crea_" + idCrea).drop();
			
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param nbrIterat
	 * @param creativeVO
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> getLignesAleratoire(int nbrIterat, CreativeVO creativeVO) throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>(0);
		try {
			String filePath = "/data/uploads/" + creativeVO.getUserId() + "/" + creativeVO.getCreativeId() + "/" + creativeVO.getFichier();
			if ("xml".equalsIgnoreCase(creativeVO.getTypeFichier())) {
				String xpath = creativeVO.getXpath();
				File file = new File(filePath);
				// InputStream is = new
				// ByteArrayInputStream(xmlString.getBytes());
				Document doc = PositionalXMLReader.readXML(file);
				// is.close();
				String elementName = StringUtils.substring(xpath, StringUtils.lastIndexOf(xpath, "/") + 1);
				int size = doc.getElementsByTagName(elementName).getLength();
				if (nbrIterat > size || nbrIterat == 0) {
					nbrIterat = size;
				}
				for (int i = 0; i < nbrIterat; i++) {
					Random randomGenerator = new Random();
					int randomInt = randomGenerator.nextInt(size);
					randomInt = randomInt == 0 ? 1 : randomInt;
					String expression = xpath + "[" + randomInt + "]";
					List<String> entries = XMLUtils.readXPath(file, expression);
					Map<String, String> map = new HashMap<String, String>(0);
					int j = 0;
					for (String elt : entries) {
						j++;
						String[] eltValues = StringUtils.split(elt, "=");
						if (eltValues.length > 1) {
							map.put("[" + j + "]", eltValues[1]);
						}
					}
					result.add(map);
				}

			} else if ("csv".equalsIgnoreCase(creativeVO.getTypeFichier()) || StringUtils.isNotEmpty(creativeVO.getUrl())) {
				Reader reader = null;
				if (StringUtils.isNotEmpty(creativeVO.getUrl())) {
					URL stockURL = new URL(creativeVO.getUrl());
					if (StringUtils.isNotEmpty(creativeVO.getLoginUrl())) {
						String authString = creativeVO.getLoginUrl() + ":" + creativeVO.getPasswdUrl();
						byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
						String authStringEnc = new String(authEncBytes);
						URLConnection urlConnection = stockURL.openConnection();
						urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
						InputStream is = urlConnection.getInputStream();
//						InputStreamReader isr = new InputStreamReader(is);
//						reader = new CSVReader(isr, creativeVO.getSeparateur().charAt(0));
						reader = new InputStreamReader(is);
					} else {
//						BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
//						reader = new CSVReader(in, creativeVO.getSeparateur().charAt(0));
						URLConnection urlConnection = stockURL.openConnection();
						InputStream is = urlConnection.getInputStream();
//						InputStreamReader isr = new InputStreamReader(is);
//						reader = new CSVReader(isr, creativeVO.getSeparateur().charAt(0));
						reader = new InputStreamReader(is);
					}
				} else {
//					reader = new CSVReader(new FileReader("C:\\Users\\m.arbi\\Desktop\\merchantListRetargeting.csv"), creativeVO.getSeparateur().charAt(0));
					reader = new FileReader(filePath);
//				reader=new CSVReader(
//					    new InputStreamReader(new FileInputStream("C:\\Users\\m.arbi\\Desktop\\merchantListRetargeting.csv"), "UTF-8"), 
//					    creativeVO.getSeparateur().charAt(0));
				}
//				List<String[]> listEntries = reader.readAll();
//				reader.close();
//				if (null != listEntries && !listEntries.isEmpty()) {
//					int size = listEntries.size();
//					if (nbrIterat > size || nbrIterat == 0) {
//						nbrIterat = size;
//					}
//					for (int i = 0; i < nbrIterat; i++) {
//						Random randomGenerator = new Random();
//						int randomInt = randomGenerator.nextInt(size);
//						String[] entries = listEntries.get(randomInt);
//						Map<String, String> map = new HashMap<String, String>(0);
//						int j = 0;
//						for (String elt : entries) {
//							j++;
//							map.put("[" + j + "]", elt);
//						}
//						result.add(map);
//					}
//				}
				
//				String[] row = reader.readNext();
//				int size = row.length;
//				while (row != null) {
//					Map<String, String> map = new HashMap<String, String>(0);
//					int j = 0;
//					for (String elt : row) {
//						j++;
//						map.put("[" + j + "]", elt);
//					}
//					result.add(map);
//					row = reader.readNext();
//				}
				String line = "";
				
//				BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\m.arbi\\Desktop\\merchantListRetargeting.csv"));
				BufferedReader br = new BufferedReader(reader);
				while ((line = br.readLine()) != null) {
					// use comma as separator
					String[] row = line.split(";");
					Map<String, String> map = new HashMap<String, String>(0);
					int j = 0;
					for (String elt : row) {
						j++;
						map.put("[" + j + "]", elt);
					}
					result.add(map);
				}
				
			}
		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
			throw e;
		}
		return result;
	}

//	@Override
//	public List<Map<String, String>> getLignesFromFlux(int nbrLigne, CreativeVO creativeVO) {
//		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>(0);
//		try {
//			if (null == mapLigneEntries || mapLigneEntries.isEmpty()) {
//				fillMapEntries();
//			}
//			if (null != mapLigneEntries && !mapLigneEntries.isEmpty()) {
//				int size = mapLigneEntries.get(creativeVO.getCreativeId()).size();
//				for (int i = 0; i < nbrLigne; i++) {
//					Random randomGenerator = new Random();
//					int randomInt = randomGenerator.nextInt(size);
//					resultList.add(mapLigneEntries.get(creativeVO.getCreativeId()).get(randomInt));
//				}
//			}
//		} catch (Exception e) {
//			log.error("Erreur : " + e.getMessage(), e);
//		}
//		return resultList;
//	}
	
	@Override
	public List<Map<String, String>> getDataFromMongo(int nbrLigne, CreativeVO creativeVO) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>(0);
		try {
//			MongoClient mongo = new MongoClient("localhost", 27017);

			DB db = mongo.getDb();
			DBCollection table = db.getCollection("crea_" + creativeVO.getCreativeId());
			Integer tableCount = Utils.getInteger(table.getCount());

			if (tableCount != null && tableCount > 0) {
				for (int i = 0; i < nbrLigne; i++) {

					Random randomGenerator = new Random();
					int randomInt = randomGenerator.nextInt(Utils.getInteger(tableCount));
					DBCursor cursor = table.find().limit(-1).skip(randomInt);
					String json = "";
					if (cursor.hasNext()) {
						json = cursor.next().toString();
					}
					Map<String, Object> map = new HashMap<String, Object>(0);
					ObjectMapper mapper = new ObjectMapper();
					// convert JSON string to Map
					map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
					});
					Map<String, String> mapToList = new HashMap<String, String>(0);
					if (null != map && !map.isEmpty()) {
						for (String key: map.keySet()) {
							Object object = map.get(key);
							if (null != object && object instanceof String) {
								mapToList.put(key, (String) object);
							}
						}
					}
					resultList.add(mapToList);
				}
			}

		} catch (Exception e) {
			log.error("Erreur : " + e.getMessage(), e);
		}
		return resultList;
	}


}
