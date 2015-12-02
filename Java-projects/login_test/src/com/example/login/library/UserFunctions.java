
package com.example.login.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.login.Condition;

import android.content.Context;

public class UserFunctions {
	
	private Parser jsonParser;
	
	private static String loginURL = "http://ec2-23-20-205-16.compute-1.amazonaws.com/php/projectDistributed/signin.php";
	private static String registerURL = "http://ec2-23-20-205-16.compute-1.amazonaws.com/php/projectDistributed/signup.php";
	private static String sendMessageURL = "http://ec2-23-20-205-16.compute-1.amazonaws.com/php/projectDistributed/createMessage.php";
	private static String getNumberOfUnreadMessagesURL = "http://ec2-23-20-205-16.compute-1.amazonaws.com/php/projectDistributed/getNumberOfUnreadMessages.php";
	private static String getMessagesURL = "http://ec2-23-20-205-16.compute-1.amazonaws.com/php/projectDistributed/getMessages.php";
	private static String changeMessageStatusURL = "http://ec2-23-20-205-16.compute-1.amazonaws.com/php/projectDistributed/changeMessageStatus.php";
	private static String getSentMessagesURL = "http://ec2-23-20-205-16.compute-1.amazonaws.com/php/projectDistributed/getSentMessages.php";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String send_message_tag = "send_message";
	private static String get_number_of_unread_messages_tag = "get_number_of_unread_messages";
	private static String get_messages_tag = "get_messages";
	private static String get_sent_messages_tag = "get_sent_messages";
	private static String change_message_status_tag = "change_message_status";
	
	// constructor
	public UserFunctions(){
		jsonParser = new Parser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String username, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param password
	 * @param password
	 * */
	public JSONObject registerUser(String name, String username, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
		
	public JSONObject sendMessage(String sender, String receiver, String messageBody, String subject){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", send_message_tag));
		params.add(new BasicNameValuePair("sender", sender));
		params.add(new BasicNameValuePair("receiver", receiver));
		params.add(new BasicNameValuePair("message", messageBody));
		params.add(new BasicNameValuePair("subject", subject));
		JSONObject json = jsonParser.getJSONInsertFromUrl(sendMessageURL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	public JSONObject getNumberOfUnreadMessages (String username){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_number_of_unread_messages_tag));
		params.add(new BasicNameValuePair("username", username));
		JSONObject json = jsonParser.getJSONFromUrl(getNumberOfUnreadMessagesURL, params);
		
		return json;
	}
	
	public Condition getMessages (String username){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_messages_tag));
		params.add(new BasicNameValuePair("username", username));
		Condition cond = jsonParser.getJSONFromUrlMessages(getMessagesURL, params);
		return cond;
	}
	
	public Condition getSentMessages (String username){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", get_messages_tag));
		params.add(new BasicNameValuePair("username", username));
		Condition cond = jsonParser.getJSONFromUrlMessages(getSentMessagesURL, params);
		return cond;
	}
	
	public JSONObject changeMessageStatus (String message_id){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", change_message_status_tag));
		params.add(new BasicNameValuePair("messageId", message_id.toString()));
		JSONObject json = jsonParser.getJSONFromUrl(changeMessageStatusURL, params);
		
		return json;
	}
	

	
}
