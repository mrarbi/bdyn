package com.orbit.dynamix.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

@Service
public class SaveCreaServiceAws implements ISaveCreaServiceAws {

	@Value("${S3Bucket}")
	String s3Bucket;
	
	@Value("${s3Path}")
	String s3Path;
	
	@Value("${s3Name}")
	String s3Name;
	
	@Value("${s3Pwd}")
	String s3Pwd;

	AmazonS3Client s3;

	public String getUrl(String nomFichier) {

		return s3Path + s3Bucket + "/" + nomFichier;
	}

	public void saveCrea(File file, String nameCrea) {
		getSaveCreaAws().putObject(s3Bucket, nameCrea, file);

	}

	public void saveCreaUrl(InputStream io, String nameCrea) throws IOException {
		getSaveCreaAws().putObject(s3Bucket, nameCrea, io, null);
		io.close();
	}

	public String getMediaUrl(String nomFichier) {
//		return s3Path + s3Bucket + "/" + nomFichier;
		return getSaveCreaAws().getUrl(s3Bucket, nomFichier).toString();
	}

	public String getUrlFile(String nameFile) {
		return getSaveCreaAws().getUrl(s3Bucket, nameFile).toString();
//		return s3Path + s3Bucket + "/" + nameFile;
	}
	
	public String encode64Crea(String sUrl){
		try {

			URL url = new URL(sUrl);
			InputStream is = url.openStream();
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i=is.read(b)) != -1)
				bais.write(b,0,i);
			is.close();
			String img = new String(Base64.encodeBase64(bais.toByteArray()));

			return img;
		} catch (Exception e) {
			return null;
		}
	}
	
	public AmazonS3Client getSaveCreaAws() {
		s3 = new AmazonS3Client(new BasicAWSCredentials(s3Name, s3Pwd));
		return s3;
	}

	public String getS3Bucket() {
		return s3Bucket;
	}

	public void setS3Bucket(String s3Bucket) {
		this.s3Bucket = s3Bucket;
	}

	public String getS3Path() {
		return s3Path;
	}

	public void setS3Path(String s3Path) {
		this.s3Path = s3Path;
	}

	public String getS3Name() {
		return s3Name;
	}

	public void setS3Name(String s3Name) {
		this.s3Name = s3Name;
	}

	public String getS3Pwd() {
		return s3Pwd;
	}

	public void setS3Pwd(String s3Pwd) {
		this.s3Pwd = s3Pwd;
	}

	public AmazonS3Client getS3() {
		return s3;
	}

	public void setS3(AmazonS3Client s3) {
		this.s3 = s3;
	}
	
	

}
