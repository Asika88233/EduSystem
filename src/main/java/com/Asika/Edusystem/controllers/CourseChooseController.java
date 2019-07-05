package com.Asika.Edusystem.controllers;

import java.util.List;

import com.Asika.Edusystem.dao.UserInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class CourseChooseController extends Controller{
	public void index() {
		render("/WEB-INF/view/menu/courseChoose/index.html");
	}
    public void getCourseChoosed() {
    	String sqlPara="SELECT\r\n" + 
    			"	t_student.id AS '学号',\r\n" + 
    			"	t_student.stu_name AS '姓名',\r\n" + 
    			"	t_course.course_name AS '课程名称',\r\n" + 
    			"	t_course.id AS '课程编号',\r\n" + 
    			"	t_course.teacher_name_fk AS '任课教师'\r\n" + 
    			" FROM\r\n" + 
    			"	`t_courselog`\r\n" + 
    			"	LEFT JOIN t_student  ON t_courselog.stu_id = t_student.id\r\n" + 
    			"	LEFT JOIN t_course   ON t_courselog.course_id = t_course.id\r\n" + 
    			"\r\n" + 
    			"WHERE\r\n" + 
    			"	t_courselog.stu_id = ? ";
    	List<Record>courseList=Db.find(sqlPara,getSessionAttr("studentid"));
    	JSONArray courseListArray = new JSONArray();
    	for (Record record : courseList) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("courseName", record.getStr("课程名称"));
    		jsonObject.put("courseId", record.getStr("课程编号"));
    		jsonObject.put("courseTeacher", record.getStr("任课教师"));
    		courseListArray.add(jsonObject);
		}
    	JSONObject jsonObject =new JSONObject();
    	jsonObject.put("data", courseListArray);
    	renderJson(jsonObject);
    }
    public void getCourseNotChoosed() {
    	String sqlPara= "SELECT id AS \"课程编号\",\r\n" + 
    			"       course_name as \"课程名称\",\r\n" + 
    			"			 teacher_name_fk as \"任课教师\"\r\n" + 
    			"FROM t_course\r\n" + 
    			" WHERE id not in( SELECT course_id FROM t_courselog WHERE stu_id =?)";
    	List<Record>courseList=Db.find(sqlPara,getSessionAttr("studentid"));
    	JSONArray courseListArray = new JSONArray();
    	for (Record record : courseList) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("courseName", record.getStr("课程名称"));
    		jsonObject.put("courseId", record.getStr("课程编号"));
    		jsonObject.put("courseTeacher", record.getStr("任课教师"));
    		courseListArray.add(jsonObject);
		}
    	JSONObject jsonObject =new JSONObject();
    	jsonObject.put("data", courseListArray);
    	renderJson(jsonObject);
		
	}
    public void selectCourse() {
		int courseId =getParaToInt("id");
		System.out.println(courseId);
		int result=Db.update("INSERT INTO `t_courselog` (course_id,stu_id) VALUES (?,?)",courseId,getSessionAttr("studentid"));
		setAttr("status", result);
		this.renderJson();
 	}
}
