package backend;
import org.asynchttpclient.*;

import shared.*;

import java.io.IOError;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;;

public class getRequest {

	public getRequest() {
		// TODO Auto-generated constructor stub
	}
	/*
	public ArrayList<Note> getAllNotes() throws IOError{
		
	}
	
	public Response getAllNotes() throws IOErrror{
		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
		Future<Integer> f = asyncHttpClient.prepareGet("http://www.example.com/").execute(
				new AsyncCompletionHandler<Integer>() {
					public Integer onCompleted(Response arg0) throws Exception {
						return response.
					};
				};
	}
	*/

	private static final String host = "NO HOST YET";
	private static final String fullUrl = host + "NO SUB DIR YET";
}
