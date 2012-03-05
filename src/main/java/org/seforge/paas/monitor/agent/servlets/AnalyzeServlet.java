package org.seforge.paas.monitor.agent.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seforge.paas.monitor.agent.LogAnalyzer;

public class AnalyzeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String logLocation;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnalyzeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//The element in params is <String, String[])
		Map params = request.getParameterMap();
		String name = ((String[])params.get("name"))[0];		
		String type =  ((String[])params.get("type"))[0];
		
		String logFile = logLocation + "/" + name + "_PaaSMonitorRT.log";
		try {
			Map<String, Integer> hitNum = LogAnalyzer.parseLogForHitNum(logFile);
			StringBuilder sb = new StringBuilder();
			for(String s1 : hitNum.keySet()){
	        	sb.append("URL " + s1 + ":" + hitNum.get(s1));
	        } 
			response.getWriter().write(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.getWriter().write(logLocation);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void init() {
		//Get the value of logLocation from init-param specified in web.xml
		logLocation = this.getInitParameter("logLocation");
	}

}