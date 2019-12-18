package br.com.inovabae.udemy_api_client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;


public class UdemyApiClient {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		Integer courseId = 49250;
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("IOTrsVoCHYi6sgUJx6hpiLm5xPJkdYpDrBMiWZXA",
				"GMJDq0GKuk3fcGFTFmmog2sHfyRMMsj3Bb61TjkKTEU6yWN2Kw1LTQaqnYGCAQ9ADGa9TSamLQzfQK5LqIP1ZiQS8rGHyHTvKcjZZo7OUBW0fLlzw2caIsubEOJcp7Xb");
		clientConfig.register(feature);

		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client.target("https://www.udemy.com/api-2.0/courses/" + courseId + "/reviews/");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		
		CourseReview reviews = response.readEntity(CourseReview.class);
		

		System.out.println(response.getEntity().toString());
		System.out.println(response.getStatusInfo());
		System.out.println(reviews.toString());
	}
}
