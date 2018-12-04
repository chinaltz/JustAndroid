package com.ningcui.mylibrary.utiils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info json处理类
 */

public class AbJsonUtil {
	
	private static GsonBuilder gsonBuilder = new GsonBuilder();
	
	/**
	 * 
	 * 将对象转化为json.
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		String json = null;
		try {
			Gson gson = gsonBuilder.create();
			json = gson.toJson(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 
	 * 将列表转化为json.
	 * @param list
	 * @return
	 */
	public static String toJson(List<?> list) {
		String json = null;
		try {
			Gson gson = gsonBuilder.create();
			json = gson.toJson(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 
	 * 将json转化为列表.
	 * @param json
	 * @param typeToken new TypeToken<ArrayList<?>>() {};
	 * @return
	 */
	public static <T> T fromJson(String json,TypeToken<T> typeToken) {
		List<?> list = null;
		try {
			Gson gson = gsonBuilder.create();
			Type type = typeToken.getType();
			list = gson.fromJson(json,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T)list;
	}
	
	/**
	 * 
	 * 将json转化为对象.
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String json,Class<T> clazz) {
		Object obj = null;
		try {
			Gson gson = gsonBuilder.create();
			obj = gson.fromJson(json,clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T)obj;
	}

	/**
	 * 设置日期格式
	 * @param format
     */
	public static void setGsonBuilderDateFormat(String format) {
		gsonBuilder.setDateFormat(format);
	}

	
	/**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
    	List<User> list = (List<User>)fromJson("[{id:1,name:22},{id:2,name:33}]",new TypeToken<ArrayList<User>>(){});
		System.out.println(list.size());
		for(User u:list){
			System.out.println(u.getName());
		}
		
		User u = (User)fromJson("{id:1,name:22}",User.class);
		System.out.println(u.getName());
	}
    
    static class User{
    	String id;
        String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
        
    }


    

}
