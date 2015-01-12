package com.orbit.dynamix.service;

import org.springframework.stereotype.Service;

/**
 * 
 * @author m.arbi
 * 
 */
@Service
public class FtpService implements IFtpService {
	
//	@Value("${FTP_SERVER}") private String serverFtp;
//	
//	@Value("${FTP_LOGIN}") private String loginFtp;
//	
//	@Value("${FTP_PASSWD}") private String passwdFtp;
//	
//	@Value("${s4Path}") private String s4Path;
//
//	/**
//	 * 
//	 */
//	public boolean storeOnServer(String fileName, File doc, Integer idTemplate, Boolean exist)
//			throws SocketException, IOException {
//		boolean result = false;
//		String outputFile = "/dynamix/template/"+idTemplate;
//		FTPClient client = new FTPClient();
//		FileInputStream fis = null;
//
//		try {
//			
//			client.connect(serverFtp);
//			result = client.login(loginFtp, passwdFtp);
//			
//			// droits
//			client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
//			client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
//			client.setFileType(FTP.BINARY_FILE_TYPE);
//			client.enterLocalPassiveMode();
//			
//			// droits
//			doc.setExecutable(true);
//			doc.setReadable(true);
//			
//			// create directories
//			Boolean success = client.makeDirectory(outputFile);
//			//
//			// Store file to server
//			//
//			
//			//client.storeFile(outputFile, fis);
//			if(success || exist){
//				outputFile = outputFile +"/"+fileName;
//				fis = new FileInputStream(doc);
//				client.storeFile(outputFile, fis);
//			}
//			
//			client.logout();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return result;
//	}
//
//	public String getServerFtp() {
//		return serverFtp;
//	}
//
//	public String getLoginFtp() {
//		return loginFtp;
//	}
//
//	public String getPasswdFtp() {
//		return passwdFtp;
//	}
//
//	public String getS4Path() {
//		return s4Path;
//	}

}
