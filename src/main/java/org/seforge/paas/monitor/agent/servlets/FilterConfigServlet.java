package org.seforge.paas.monitor.agent.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seforge.paas.monitor.agent.LogAnalyzer;
import org.seforge.paas.monitor.agent.action.FilterConfiger;

public class FilterConfigServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//The element in params is <String, String[])
		Map params = request.getParameterMap();
		
		//name of appinstance
		String contextName = ((String[])params.get("contextName"))[0];	
		//type of monitorConfig
		String type =  ((String[])params.get("type"))[0];
		//value of monitorConfig
		String name = ((String[])params.get("name"))[0];
		
		
		String webappFolder = (new File(request.getSession().getServletContext().getRealPath("/"))).getParent(); 
		String webXmlPath = webappFolder + "\\" + contextName + "\\WEB-INF\\" + "web.xml";		
		
		FilterConfiger filterConfiger = new FilterConfiger();
		filterConfiger.addFilterToWebXml(name, webXmlPath);
		response.getWriter().write(webXmlPath);
		
		
	}

}
