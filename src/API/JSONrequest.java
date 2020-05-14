package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class JSONrequest {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

	// Gets json response from resource, Returns it as a string
	public String getJsonString(String resource) throws IOException, URISyntaxException {

		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setDefaultMaxPerRoute(1);
		pool.setMaxTotal(1);
		RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.build();
		HttpClientBuilder customizedClientBuilder = HttpClients.custom()
				.setDefaultRequestConfig(customizedRequestConfig);
		final CloseableHttpClient httpclient = customizedClientBuilder.setConnectionManager(pool).build();

		URI url = new URI(resource);
		StringBuilder returnValue = new StringBuilder(9999999);
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", USER_AGENT);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		int responsecode = response.getStatusLine().getStatusCode();
		if (responsecode != 200) {

			throw new RuntimeException("HttpResponseCode: " + responsecode);
		} else {

			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String userInput;
			while ((userInput = reader.readLine()) != null) {
				returnValue.append(userInput);
			}
			reader.close();
			response.close();
			httpclient.close();

		}

		return returnValue.toString();
	}
	public String getJsonString(CloseableHttpClient httpclient,HttpGet httpget,HttpClientContext context) throws URISyntaxException, ClientProtocolException, IOException {
		StringBuilder returnValue = new StringBuilder(9999999);
		CloseableHttpResponse response = httpclient.execute(httpget,context);
		int responsecode = response.getStatusLine().getStatusCode();
		System.out.println(responsecode);
		if (responsecode != 200) {

			throw new RuntimeException("HttpResponseCode: " + responsecode);
		} else {

			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String userInput;
			while ((userInput = reader.readLine()) != null) {
				returnValue.append(userInput);
			}
			reader.close();
			response.close();
			httpclient.close();

		}

		return returnValue.toString();
		
	}



	
	}

