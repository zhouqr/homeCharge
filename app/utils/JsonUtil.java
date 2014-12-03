package utils;

import java.lang.reflect.Type;
import java.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {
	
	public static String toJson(Object src){
		if(src!=null){
			return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create().toJson(src);
		}else{
			return "{}";
		}
		
	}
	
	public static String toViewJson(Object src){
		if(src!=null){
			return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(src);
		}else{
			return "{}";
		}
		
	}

}
