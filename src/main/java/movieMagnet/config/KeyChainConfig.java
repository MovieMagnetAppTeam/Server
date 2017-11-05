package movieMagnet.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class KeyChainConfig {

	private String omdbKey = "";
	private String themoviedbKey = "";
	private String themoviedbReadAccessToken = "";

	@PostConstruct
	public void prepareBean() {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String keyConfigPath = rootPath + "keys.properties";

		Properties keysProps = new Properties();
		try {
			keysProps.load(new FileInputStream(keyConfigPath));
			omdbKey = keysProps.getProperty("key.omdb");
			themoviedbKey = keysProps.getProperty("key.themoviedb");
			themoviedbReadAccessToken = keysProps.getProperty("key.themoviedb.readaccess");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getOmdbKey() {
		return omdbKey;
	}

	public void setOmdbKey(String omdbKey) {
		this.omdbKey = omdbKey;
	}

	public String getThemoviedbKey() {
		return themoviedbKey;
	}

	public void setThemoviedbKey(String themoviedbKey) {
		this.themoviedbKey = themoviedbKey;
	}

	public String getThemoviedbReadAccessToken() {
		return themoviedbReadAccessToken;
	}

	public void setThemoviedbReadAccessToken(String themoviedbReadAccessToken) {
		this.themoviedbReadAccessToken = themoviedbReadAccessToken;
	}

}
