package crawler.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import crawler.services.QueryService;

@Controller
@RequestMapping(value = "/api/{version}/query")
public class QueryController {

	@Autowired(required=true)
	private QueryService queryService;
	
	@RequestMapping(value = "/query_by_keyword/{keyword}",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<Integer> getTotalKeyword(@PathVariable String keyword, HttpServletRequest request , HttpServletResponse response) throws Exception {
		int numberOfKeywords = queryService.getCountByKeyword(keyword);
		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(numberOfKeywords, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(value = "/query_by_keyword_and_pageno/{keyword}/{pageno}",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<List<String>> queryByKeywordAndPagenumber(@PathVariable String keyword, @PathVariable String pageno, HttpServletRequest request , HttpServletResponse response) throws Exception {
		List<String> urls = queryService.getRecordsByKeywordAndPageNo(keyword, pageno);
		ResponseEntity<List<String>> responseEntity = new ResponseEntity<List<String>>(urls, HttpStatus.OK);
		return responseEntity;
	}
}
