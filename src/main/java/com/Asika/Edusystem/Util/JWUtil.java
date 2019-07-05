package com.Asika.Edusystem.Util;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class JWUtil {
	public static Document readXml(File xmlFile) throws Exception {
		SAXReader reader =new SAXReader();
		Document doc= reader.read(xmlFile);
		return doc;
	}
	public static Document readMenu() throws Exception{
		String menuFileStr=PathKit.getWebRootPath()+"/WEB-INF/menus-student.xml";
		File menuFile=new File(menuFileStr);
		Document menuDoc=readXml(menuFile);
		System.out.println(menuFileStr);
		return menuDoc;
	}
	public static Document readMenu(String type) throws Exception{
		String menuFileStr=PathKit.getWebRootPath()+"/WEB-INF/menus-"+type+".xml";
		File menuFile=new File(menuFileStr);
		Document menuDoc=readXml(menuFile);
		return menuDoc;
	}
	public static JSONArray readMenuToJson() throws Exception {
		JSONArray menuArray=new JSONArray();
		Document menuDoc=JWUtil.readMenu();
		Element rootEle=menuDoc.getRootElement();
		List<Element> menuList=rootEle.elements();
		for(Element menuEle:menuList) {
			JSONObject menuJson=new JSONObject();
			menuJson.put("id",menuEle.attributeValue("id"));
			menuJson.put("name",menuEle.attributeValue("name"));
			menuJson.put("icon",menuEle.attributeValue("icon"));
			//menuArray.add(menuJson);
			JSONArray subArray=new JSONArray();
			List<Element> subMenuList=menuEle.elements();
			for(Element subMenuEle:subMenuList) {
				JSONObject subMenuJson=new JSONObject();
				subMenuJson.put("id",subMenuEle.attributeValue("id"));
				subMenuJson.put("name",subMenuEle.attributeValue("name"));
				subMenuJson.put("icon",subMenuEle.attributeValue("icon"));
				subArray.add(subMenuJson);
			}
			menuJson.put("subMenus", subArray);
			menuArray.add(menuJson);
		}
		return menuArray;
	}
	public static JSONArray readMenuToJson(String type) throws Exception {
		JSONArray menuArray=new JSONArray();
		Document menuDoc=JWUtil.readMenu(type);
		Element rootEle=menuDoc.getRootElement();
		List<Element> menuList=rootEle.elements();
		for(Element menuEle:menuList) {
			JSONObject menuJson=new JSONObject();
			menuJson.put("id",menuEle.attributeValue("id"));
			menuJson.put("name",menuEle.attributeValue("name"));
			menuJson.put("icon",menuEle.attributeValue("icon"));
			//menuArray.add(menuJson);
			JSONArray subArray=new JSONArray();
			List<Element> subMenuList=menuEle.elements();
			for(Element subMenuEle:subMenuList) {
				JSONObject subMenuJson=new JSONObject();
				subMenuJson.put("id",subMenuEle.attributeValue("id"));
				subMenuJson.put("name",subMenuEle.attributeValue("name"));
				subMenuJson.put("icon",subMenuEle.attributeValue("icon"));
				subArray.add(subMenuJson);
			}
			menuJson.put("subMenus", subArray);
			menuArray.add(menuJson);
		}
		return menuArray;
	}
}
