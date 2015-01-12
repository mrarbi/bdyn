//package com.orbit.dynamix.utils;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.net.ftp.FTPClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//
//public class FastFtpMain {
//
//	private static final Logger log = LoggerFactory.getLogger(FastFtpMain.class);
//	
//	private static final String serverFtp = ConfigProperties.getProperty("FTP_SERVER");
//	
//	private static final String repCsv = "http://" + serverFtp + ConfigProperties.getProperty("REP_CSV");
//	
//	private static final String loginFtp = ConfigProperties.getProperty("FTP_LOGIN");
//	private static final String passwdFtp = ConfigProperties.getProperty("FTP_PASSWD");
//	
//	
//
//
//
//	public static void run() {
//		try {
//			
//			readCSVs(datasMap);
//			generateCsvFile(datasMap);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error", e);
//		}
//		System.out.println("Done");
//		log.info("Done");
//	}
//
//	private static void readCSVs(Map<String, Programme> datasMap) {
//
//		readProgrammeCSV(datasMap);
//
//		readLotsEnrichiCSV(datasMap);
//		
//		readLotsCSV(datasMap);
//
//		readBandeauxCSV(datasMap);
//	}
//
//	private static void readBandeauxCSV(Map<String, Programme> datasMap) {
//		String cvsSplitBy = "!#";
//		String line = "";
//		BufferedReader br = null;
//		try {
////			br = new BufferedReader(new FileReader(bandeauxEnteteCsvFile));
//			URL lien = new URL(repCsv + bandeauxEnteteCsvFile);
//			br = new BufferedReader(new InputStreamReader(lien.openStream()));
//			while ((line = br.readLine()) != null) {
//
//				// use comma as separator
//				String[] lotsEnrichi = line.split(cvsSplitBy);
//
//				String id = StringUtils.replace(lotsEnrichi[5], "\"", "");
//
//				if (StringUtils.isEmpty(id)) {
//					continue;
//				}
//				Programme data = datasMap.get(id);
//				if (null == data) {
//					continue;
//				}
//
//				String visuel = StringUtils.replace(lotsEnrichi[6], "\"", "");
//				if (StringUtils.isEmpty(visuel) || !"I".equalsIgnoreCase(visuel)) {
//					datasMap.remove(id);
//					continue;
//				}
//				data.setVisuel(visuel);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	private static void readProgrammeCSV(Map<String, Programme> datasMap) {
//		String cvsSplitBy = "!#";
//		String line = "";
//		BufferedReader br = null;
//		try {
////			br = new BufferedReader(new FileReader(programmeCsvFile));
//			URL lien = new URL(repCsv + programmeCsvFile);
//			br = new BufferedReader(new InputStreamReader(lien.openStream(), "iso-8859-1"));
//			while ((line = br.readLine()) != null) {
//
//				// use comma as separator
//				String[] programme = line.split(cvsSplitBy);
//
//				String id = StringUtils.replace(programme[1], "\"", "");
//
//				if (StringUtils.isEmpty(id)) {
//					continue;
//				}
//
//				String libelle = StringUtils.replace(programme[7], "\"", "");
//				String ville = StringUtils.replace(programme[3], "\"", "");
//				String cp = StringUtils.replace(programme[2], "\"", "");
//				String photo = StringUtils.replace(programme[113], "\"", "");
//				String redirect = StringUtils.replace(programme[154], "\"", "");
//				String prix22 = StringUtils.replace(programme[21], "\"", "");
//				prix22 = StringUtils.replace(prix22, ",", ".");
//				String prix33 = StringUtils.replace(programme[32], "\"", "");
//				prix33 = StringUtils.replace(prix33, ",", ".");
//				String nbLot = StringUtils.replace(programme[144], "\"", "");
//				String desc = StringUtils.replace(programme[8], "\"", "");
//
//				Integer nbLotInt = CommonsBeanUtils.getInteger(nbLot);
//				if (nbLotInt == null || nbLotInt.equals(0)) {
//					continue;
//				}
//
//				Double prix = 0.0;
//				Double prixDbl22 = null;
//				try {
//					if (StringUtils.isNotEmpty(prix22)) {
//						prixDbl22 = Double.parseDouble(prix22);
//					}
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//				Double prixDbl33 = null;
//				try {
//					if (StringUtils.isNotEmpty(prix33)) {
//						prixDbl33 = Double.parseDouble(prix33);
//					}
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//				boolean isPrix22 = false;
//				if (null != prixDbl22 && prixDbl22 > 0) {
//					isPrix22 = true;
//				}
//				boolean isPrix33 = false;
//				if (null != prixDbl33 && prixDbl33 > 0) {
//					isPrix33 = true;
//				}
//
//				if (isPrix22 && isPrix33) {
//					if (prixDbl22 > prixDbl33) {
//						prix = prixDbl33;
//					} else {
//						prix = prixDbl22;
//					}
//				} else if (isPrix22) {
//					prix = prixDbl22;
//				} else if (isPrix33) {
//					prix = prixDbl33;
//				} else {
//					continue;
//				}
//
//				Programme data = new Programme();
//				data.setIdProg(id);
//				data.setLibelle(libelle);
//				data.setCodePostal(cp);
//				data.setVille(ville);
//				data.setPhoto(photo);
//				data.setPrix(CommonsBeanUtils.getString(prix));
//				data.setRedirect(redirect);
//				data.setNbrLot(nbLot);
//				data.setDescrption(desc);
//				datasMap.put(id, data);
//
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	private static void readLotsEnrichiCSV(Map<String, Programme> datasMap) {
//		String cvsSplitBy = "!#";
//		String line = "";
//		BufferedReader br = null;
//		try {
////			br = new BufferedReader(new FileReader(lotsEnrichiCsvFile));
//			URL lien = new URL(repCsv + lotsEnrichiCsvFile);
//			br = new BufferedReader(new InputStreamReader(lien.openStream()));
//			while ((line = br.readLine()) != null) {
//
//				// use comma as separator
//				String[] lotsEnrichi = line.split(cvsSplitBy);
//
//				String id = StringUtils.replace(lotsEnrichi[2], "\"", "");
//				String idLots = StringUtils.replace(lotsEnrichi[5], "\"", "");
//
//				if (StringUtils.isEmpty(id)) {
//					continue;
//				}
//				Programme data = datasMap.get(id);
//				if (null == data) {
//					continue;
//				}
//
//				String dateLivraison = StringUtils.replace(lotsEnrichi[28], "\"", "");
//				if (StringUtils.isNotEmpty(dateLivraison) && StringUtils.length(dateLivraison) > 7) {
//					dateLivraison = StringUtils.substring(dateLivraison, 6, 10) + "/" + StringUtils.substring(dateLivraison, 4, 6) + "/" + StringUtils.substring(dateLivraison, 0, 4);
//					data.setDateLivraison(dateLivraison);
//				}
//				if (StringUtils.isNotEmpty(idLots)) {
//					data.setIdLots(idLots);
//				}
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	
//	private static void readLotsCSV(Map<String, Programme> datasMap) {
//		String cvsSplitBy = "!#";
//		String line = "";
//		BufferedReader br = null;
//		try {
////			br = new BufferedReader(new FileReader(lotsEnrichiCsvFile));
//			URL lien = new URL(repCsv + lotsCsvFile);
//			br = new BufferedReader(new InputStreamReader(lien.openStream()));
//			while ((line = br.readLine()) != null) {
//				
//				// use comma as separator
//				String[] lots = line.split(cvsSplitBy);
//				
//				String id = StringUtils.replace(lots[1], "\"", "");
//				String idLots = StringUtils.replace(lots[2], "\"", "");
//				
//				if (StringUtils.isEmpty(id) && StringUtils.isEmpty(idLots)) {
//					continue;
//				}
//				Programme data = datasMap.get(id);
//				if (null == data) {
//					continue;
//				}
////				if (!idLots.equals(data.getIdLots())) {
////					continue;
////				}
//				String nbrPiece = StringUtils.replace(lots[17], "\"", "");
//				if (StringUtils.isNotEmpty(nbrPiece) && !"0".equals(nbrPiece)) {
//					// nombre de pièce minimum
//					if ("0".equals(data.getNbrPieceMin())) {
//						data.setNbrPieceMin(nbrPiece);
//					} else if (CommonsBeanUtils.getInteger(data.getNbrPieceMin()) > CommonsBeanUtils.getInteger(nbrPiece)) {
//						data.setNbrPieceMin(nbrPiece);
//					}
//					// nombre de pièce maximum
//					if ("0".equals(data.getNbrPieceMax())) {
//						data.setNbrPieceMax(nbrPiece);
//					} else if (CommonsBeanUtils.getInteger(data.getNbrPieceMax()) < CommonsBeanUtils.getInteger(nbrPiece)) {
//						data.setNbrPieceMax(nbrPiece);
//					}
//				}
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	private static void generateCsvFile(Map<String, Programme> datasMap) {
//		try {
//
//			// FileWriter writer = new
//			// FileWriter(FastMain.class.getClass().getResource(outputFile).getPath());
//			FileWriter writer = new FileWriter(outputFile);
//
//			// loop map
//			for (String key : datasMap.keySet()) {
//
//				Programme data = datasMap.get(key);
//
//				// if (StringUtils.isNotEmpty(data.getDateLivraison()) &&
//				// StringUtils.isNotEmpty(data.getVisuel())) {
//				writer.append(data.getIdProg());
//				writer.append(";");
//				writer.append(data.getLibelle());
//				writer.append(";");
//				writer.append(data.getCodePostal());
//				writer.append(";");
//				writer.append(data.getVille());
//				writer.append(";");
//				writer.append(data.getPrix());
//				writer.append(";");
//				writer.append(data.getNbrLot());
//				writer.append(";");
//				writer.append(data.getDateLivraison());
//				writer.append(";");
//				writer.append(data.getVisuel());
//				writer.append(";");
//				writer.append("http://media2-js.nexity.fr/" + data.getPhoto());
//				writer.append(";");
//				writer.append("http://www.nexity.fr/neuf/" + data.getRedirect() + "?xtor=XXX&ctcsrc=XXX");
//				writer.append(";");
//				writer.append(data.getDescrption());
//				writer.append(";");
//				writer.append(data.getNbrPieceMin());
//				writer.append(";");
//				writer.append(data.getNbrPieceMax());
//
//				writer.append('\n');
//				// }
//
//			}
//
//			// generate whatever data you want
//
//			writer.flush();
//			writer.close();
//
//			// uploader le fichier
//
//			FTPClient client = new FTPClient();
//			FileInputStream fis = null;
//
//			try {
//			    client.connect(serverFtp);
//			    client.login(loginFtp, passwdFtp);
//
//			    //
//			    // Create an InputStream of the file to be uploaded
//			    //
//			    fis = new FileInputStream(outputFile);
//			    //
//			    // Store file to server
//			    //
//			    client.storeFile("csv" + outputFile, fis);
//			    client.logout();
//			} catch (IOException e) {
//			    e.printStackTrace();
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
