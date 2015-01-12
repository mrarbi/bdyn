package com.orbit.dynamix.service;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.s3.AmazonS3Client;

public interface ISaveCreaServiceAws {

	public String getUrl(String nomFichier);

	public String getMediaUrl(String nomFichier);

	public void saveCrea(File file, String nameCrea);

	public String getUrlFile(String nameFile);

	public void saveCreaUrl(InputStream io, String nameCrea) throws IOException;

	String encode64Crea(String sUrl);
	
	public AmazonS3Client getSaveCreaAws();
	
	public String getS3Bucket();

	public String getS3Path();

	public String getS3Name();

	public String getS3Pwd();
}
