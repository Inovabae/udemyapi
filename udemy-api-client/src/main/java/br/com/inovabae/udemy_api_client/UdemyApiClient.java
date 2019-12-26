package br.com.inovabae.udemy_api_client;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class UdemyApiClient {
	private static Logger log = Logger.getLogger(UdemyApiClient.class.getName());

	public static void main(String[] args) {
		List<String> courseList = getCourseList();
		for (String courseId : courseList) {
			log.info("Curso:" + courseId);
			List<String> reviews = getCourseReviews(courseId);
			log.info("Avaliações");
			for (String review : reviews) {
				log.info(review);
			}
		}
		// CourseReview reviews = response.readEntity(CourseReview.class);
		//

		// System.out.println(response.getEntity().toString());
		// System.out.println(response.getStatusInfo());
	}

	private static List<String> getCourseReviews(String courseId) {
		String url = "https://www.udemy.com/api-2.0/courses/" + courseId + "/reviews/";
		Map<JsonObject, Response> mappedJson = getObject(url);
		List<String> retList = new ArrayList<String>();

		mappedJson.forEach((k, v) -> {
			JsonArray jsonArray = k.getJsonArray("results");
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jArray2 = jsonArray.getJsonObject(i);
				retList.add(String.valueOf(jArray2.getString("content")));
			}
			v.close();
		});
		return retList;
	}

	private static List<String> getCourseList() {
		String encodeParameter = "";
		String courseCategory = "Health & Fitness";
		try {
			encodeParameter = URLEncoder.encode(courseCategory, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "Error", e);
		}

		String url = "https://www.udemy.com/api-2.0/courses/?category=" + encodeParameter;

		Map<JsonObject, Response> mappedJson = getObject(url);
		List<String> retList = new ArrayList<String>();

		mappedJson.forEach((k, v) -> {
			JsonArray jsonArray = k.getJsonArray("results");
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jArray2 = jsonArray.getJsonObject(i);
				retList.add(String.valueOf(jArray2.getInt("id")));
			}
			v.close();
		});
		return retList;
	}

	private static Map<JsonObject, Response> getObject(String url) {
		Client client = getClient();
		WebTarget webTarget = client.target(url);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String body = response.readEntity(String.class);

		JsonObject jArray = Json.createReader(new StringReader(body)).readObject();
		Map<JsonObject, Response> retMap = new HashMap<JsonObject, Response>();
		retMap.put(jArray, response);
		return retMap;
	}

	private static Client getClient() {
		ClientConfig clientConfig = new ClientConfig();

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("IOTrsVoCHYi6sgUJx6hpiLm5xPJkdYpDrBMiWZXA",
				"GMJDq0GKuk3fcGFTFmmog2sHfyRMMsj3Bb61TjkKTEU6yWN2Kw1LTQaqnYGCAQ9ADGa9TSamLQzfQK5LqIP1ZiQS8rGHyHTvKcjZZo7OUBW0fLlzw2caIsubEOJcp7Xb");
		clientConfig.register(feature);

		Client client = ClientBuilder.newClient(clientConfig);
		return client;
	}
}
