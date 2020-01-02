package br.com.inovabae.udemy_api_client;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
		StringBuilder builder = new StringBuilder();
		List<Course> courseList = getCourseList();
		for (Course course : courseList) {
			builder.append("\nCurso:" + course.getId());
			List<CourseReview> reviews = getCourseReviews(course);
			builder.append("\nAvaliações");
			for (CourseReview review : reviews) {
				builder.append("\n");
				builder.append(review.getCreated());
				builder.append("\n");
				builder.append(review.getContent());
				builder.append("\n");
				builder.append(review.getRating());
				builder.append("\n");
			}
		}
		System.out.println(builder.toString());
		// CourseReview reviews = response.readEntity(CourseReview.class);
		//

		// System.out.println(response.getEntity().toString());
		// System.out.println(response.getStatusInfo());
	}

	private static List<CourseReview> getCourseReviews(Course course) {
		String url = "https://www.udemy.com/api-2.0/courses/" + course.getId() + "/reviews/";
		Map<JsonObject, Response> mappedJson = getObject(url);
		List<CourseReview> retList = new ArrayList<CourseReview>();

		mappedJson.forEach((k, v) -> {
			JsonArray jsonArray = k.getJsonArray("results");
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jArray2 = jsonArray.getJsonObject(i);
				CourseReview courseReview = new CourseReview();
				courseReview.setCourse(course);
				courseReview.setContent(jArray2.getString("content"));
				courseReview.setRating(jArray2.getJsonNumber("rating").doubleValue());

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddThh:mm:ssZ");

				try {
					courseReview.setCreated(df.parse(jArray2.getString("created")));
				} catch (ParseException e) {
					log.log(Level.SEVERE, "Error", e);
				}
				retList.add(courseReview);
			}
			v.close();
		});
		return retList;
	}

	private static List<Course> getCourseList() {
		String encodeParameter = "";
		String courseCategory = "Health & Fitness";
		try {
			encodeParameter = URLEncoder.encode(courseCategory, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "Error", e);
		}
		Locale localPt = new Locale("pt");
		String url = "https://www.udemy.com/api-2.0/courses/?category=" + encodeParameter + "&language="
				+ localPt.getCountry();

		Map<JsonObject, Response> mappedJson = getObject(url);
		List<Course> retList = new ArrayList<Course>();

		mappedJson.forEach((k, v) -> {
			JsonArray jsonArray = k.getJsonArray("results");
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jArray2 = jsonArray.getJsonObject(i);
				Course course = new Course();
				course.setId(jArray2.getInt("id"));
				course.setIs_paid(jArray2.getBoolean("is_paid"));
				course.setLanguage(localPt);
				course.setTitle(jArray2.getString("title"));
				course.setUrl(jArray2.getString("url"));

				JsonArray priceArray = jArray2.getJsonArray("price_detail");
				JsonObject prices = priceArray.getJsonObject(0);
				Currency currency = Currency.getInstance(prices.getString("currency"));
				ValorMonetario amount = new ValorMonetario(currency, prices.getJsonNumber("amount").bigDecimalValue());
				course.setPrice(amount);

				// course.setPrice(price);
				List<Instructor> instructors = new ArrayList<Instructor>();
				JsonArray instructorsJson = jArray2.getJsonArray("visible_instructors");
				for (int j = 0; j < instructorsJson.size(); j++) {
					JsonObject instructorsArray = instructorsJson.getJsonObject(i);
					Instructor instructor = new Instructor();
					instructor.setTitle(instructorsArray.getString("title"));
					instructor.setName(instructorsArray.getString("name"));
					instructor.setDisplay_name(instructorsArray.getString("display_name"));
					instructors.add(instructor);
				}
				course.setInstructors(instructors);
				retList.add(course);
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
