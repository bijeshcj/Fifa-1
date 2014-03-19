package com.prokarma.fifa.webservice;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.prokarma.loggers.FifaLog;
import com.prokarma.loggers.Severity;



public class RestClient {

	private ArrayList<NameValuePair> headers;
	private ArrayList<NameValuePair> params;

	public enum RequestMethod{ GET, POST, PUT };

	private String url;
	private int requestType;

    public void addParam(String name, String value)
    {
        params.add(new BasicNameValuePair(name, value));
    }

    public void addHeader(String name, String value)
    {
        headers.add(new BasicNameValuePair(name, value));
    }

	
	
	public RestClient(String url, int requestType) {
		this.url = url;
		this.requestType = requestType;
		headers = new ArrayList<NameValuePair>();
		params = new ArrayList<NameValuePair>();

	}

	public void execute(RequestMethod method, ResponseListener obj)
			throws Exception {
		switch (method) {
		case GET:
				
			String combinedParams = "";
            if(!params.isEmpty()){
                combinedParams += "?";
                for(NameValuePair p : params)
                {
                	// if array that is json formatted array, just add the value to URL
                	if(p.getName().equalsIgnoreCase("array")) {
                		combinedParams += p.getValue();
                	} else {
                        String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");
                        if(combinedParams.length() > 1){
                            combinedParams  +=  "&" + paramString;
                        }
                        else{
                            combinedParams += paramString;
                        }
                	}

                }
            }
         
            
            // Refer http://en.wikipedia.org/wiki/Percent-encoding
            String reqUrl = (url + combinedParams).replace("{", "%7B").replace("}", "%7D");
            FifaLog.i(Severity.LOW, "Request params after encoding " + reqUrl);
            
            
            HttpGet request = new HttpGet(reqUrl);

            //add headers
            for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }
       
            new AsynchronousSender((ResponseListener) obj, requestType).execute(request);
					
            break;

		case POST:
			 FifaLog.i(Severity.LOW, "Request params " + url);
            HttpPost postRequest = new HttpPost(url);
            String s = url.toString();
            //add headers
            for(NameValuePair h : headers)
            {
                postRequest.addHeader(h.getName(), h.getValue());
                FifaLog.i(Severity.LOW, "Setting header :" + h.getName() + " = " + h.getValue());
              
            }

            // For debugging
            for(NameValuePair p : params)
            {
            	 FifaLog.i(Severity.LOW, "Setting param :" + p.getName() + " = " + p.getValue());
                s = s+p.getName()+"="+p.getValue().toString();
            }
            if(!params.isEmpty()){
            	postRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            } 
   
            FifaLog.i(Severity.LOW, postRequest.toString());
              
         /*   if(requestType == Constants.LOCALITY){
            	MessageHandler.showStringDialog(activity, s, 100);
            }
         */   
				 new AsynchronousSender((ResponseListener) obj, 
						 requestType).execute(postRequest);
			
			break;

		case PUT:

			break;

	
		}
	}


}
