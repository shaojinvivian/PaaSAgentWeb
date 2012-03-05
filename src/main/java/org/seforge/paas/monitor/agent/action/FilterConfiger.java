package org.seforge.paas.monitor.agent.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FilterConfiger {
	// 测试运行
		public static void main(String[] args) throws IOException {
			FilterConfiger test = new FilterConfiger();
			test.addFilterToWebXml("MyFilter", "E:\\program\\web.xml");
		}
		// 向web.xml中添加servletName的Filter
		public void addFilterToWebXml(String servletName, String fileName)
				throws FileNotFoundException, IOException {
			File file = new File(fileName);
			if (file.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				ArrayList<String> buffer = new ArrayList<String>();
				String filterName = "myFilter";
				String filterClass = "org.seforge.paas.agent.servlet.filter.ServletMonitorFilter";
				int num = 0;
				int insertPosition = 0;
				boolean flag = false;
				int filterNum = 0;
				try {
					String tmp = new String();
					// 将文件中的每一行读入buffer中
					tmp = reader.readLine();
					while (tmp != null) {
						buffer.add(tmp);
						if (!flag && tmp.contains("<servlet>"))
						{
							insertPosition = num;
							flag = true;
						}
						if (tmp.contains("<filter>"))
							filterNum ++;
						num++;
						tmp = reader.readLine();
					}
					try {
						reader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					for (int i = 0; i < num; i++) {
						// 遇到第一个servlet标签则将新的filter添加在这个标签前面
						if (i == insertPosition) {
							writer.write(" <filter>");
							writer.newLine();
							writer.write("  <filter-name>" + filterName + filterNum
									+ "</filter-name>");
							writer.newLine();
							// 这里有个小问题，filterClass指定为谁？
							writer.write("  <filter-class>" + filterClass
									+ "</filter-class>");
							writer.newLine();
							writer.write(" </filter>");
							writer.newLine();
							writer.write(" <filter-mapping>");
							writer.newLine();
							writer.write("  <filter-name>" + filterName + filterNum
									+ "</filter-name>");
							writer.newLine();
							writer.write("  <url-pattern>" + servletName
									+ "</url-pattern>");
							writer.newLine();
							writer.write(" </filter-mapping>");
							writer.newLine();
						}
						// 将原来的配置文件写回
						writer.write(buffer.get(i));
						writer.newLine();
					}
					try {
						// 关闭输入输出流
						writer.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

}
