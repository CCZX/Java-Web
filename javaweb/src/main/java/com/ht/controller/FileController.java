package com.ht.controller;

/**
 * 实现对文档的访问，返回主要为数据
 * @author ht032
 *2019/4/25
 */
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * 文件上传模块
 * @author ht032
 *实现文件上传并对应到数据库记录
 */
import org.springframework.web.servlet.ModelAndView;

import com.ht.service.DocumentService;
import com.ht.tools.Result;
@Controller
public class FileController {
	//method=RequestMethod.POST,value=
//	@RequestMapping("/fileupload")
//	public String uploadget(HttpServletRequest request) {
//		System.out.println(request.getMethod());
//		return null;
//	}
	@Autowired
	private DocumentService service;
	@RequestMapping(value="/fileupload",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> upLoad(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		String depart_id = request.getParameter("depart_id");
		String readclass=request.getParameter("readclass");
		String remarks=request.getParameter("remarks");
		
		Result result = service.addDocument(depart_id, readclass, remarks, file);
		return result.getResult();
		
	}
	@RequestMapping("/downloadpage")
	public String downLoad(ModelMap model) {
		Result allDocument = service.getAllDocument();
		Result result = allDocument;
		System.out.println(result);
		model.addAllAttributes((Collection<?>) result);
		
		return "download";
	}
	
	@RequestMapping("/download/{id}")
	@ResponseBody
	public  Result downLoandfile(@PathVariable String id,HttpServletResponse response) {
		Result result = service.downloadDocument(id, response);
		return result;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteFile(@RequestParam String id) {
		service.deleteDocumentById(id);
		return "filepage";
	}
}
