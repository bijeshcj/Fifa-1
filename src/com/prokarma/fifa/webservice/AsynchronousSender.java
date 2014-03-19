package com.prokarma.fifa.webservice;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.fifa.models.soccer.Soccer;
import com.prokarma.loggers.FifaLog;
import com.prokarma.loggers.Severity;



public class AsynchronousSender extends AsyncTask<HttpRequest, Void, Object> {
    private static final String TAG = AsynchronousSender.class.getCanonicalName();
	private ResponseListener responseListener;
	private int requestType;
	private DefaultHttpClient httpClient;


	public AsynchronousSender(ResponseListener listener, int reqType) {
		responseListener = listener;
		requestType = reqType;
		httpClient = new DefaultHttpClient();
	}
	
	@Override
	protected Object doInBackground(HttpRequest... params) {
		try {
			HttpParams httpParams = new BasicHttpParams();
			int timeout = 60000;
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		
			httpClient.setParams(httpParams);
		
			
			/*UsernamePasswordCredentials defaultcreds = new UsernamePasswordCredentials(Constants.USER_NAME, Constants.PASSWORD);
			((AbstractHttpClient) httpClient).getCredentialsProvider().setCredentials(
					new AuthScope(null, -1,AuthScope.ANY_SCHEME),
					defaultcreds);*/
			
			Log.i(TAG,"$$$ param "+params[0]);
			HttpResponse response = httpClient.execute((HttpUriRequest) params[0]);
			Object object = invokeParser(response, requestType);
			return object;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
		
	}
	
	
	@Override
	protected void onPostExecute(Object object) {
		responseListener.onResponseReceived(object, requestType);
	}


	public static final int REQUEST_LOCATION_HISTORY = 4;
	public static final int DROPPED_CALLS_HISTORY = 5;
	public static final int DATA_CONNETION_REPORT = 6;
	
	private Object invokeParser(HttpResponse response, int req_code){
		String res = null;
		try{
			if(response == null)
				return null;
			
			res = Parser.convertStreamToString(response.getEntity().getContent());
			Soccer soccerResp = null;
			Log.d(TAG,"req_code "+req_code);
			FifaLog.i(Severity.LOW, res);
			switch (req_code) {
				case 1:
					
					 soccerResp = new Soccer();
					 soccerResp = new ObjectMapper().readValue(res,Soccer.class);
					return soccerResp;
//				case 2:
//						 
//						return soccerResp;
//				case 3:
//					 soccerResp = new Gson().fromJson(res, ServerResponse.class);
//					return soccerResp;
//				case REQUEST_LOCATION_HISTORY:
//					return LocationHistoryAdapter.generateLocationHistoryListFrom(res);
//				case DROPPED_CALLS_HISTORY:
//					return DroppedCallsHistoryActivity.generateDroppedCallListFrom(res);
//				case DATA_CONNETION_REPORT:
//					soccerResp = new Gson().fromJson(res, ServerResponse.class);
//					return soccerResp;
				default :
					FifaLog.i(Severity.HIGH, "Unknown ReqCode");
			}
		}catch(Exception e){
			FifaLog.i(Severity.HIGH, "Exception In the  AsynchronousSender invokeParser ");
			e.printStackTrace();
		}
			
			return res;
			
	}


}