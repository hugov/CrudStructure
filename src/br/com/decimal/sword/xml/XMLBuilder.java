/**
 * 
 */
package br.com.decimal.sword.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Vitor Hugo Oliveira
 * 
 */
public class XMLBuilder {

	private Document document;

	public Element recoverRoot(String path) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		document = documentBuilder.parse(path);

		return document.getDocumentElement();
	}

	public void addAttributeSqlMap(List<SqlMap> listSqlMap, Element root) throws Exception {
		for (SqlMap i : listSqlMap) {

			Element sqlMap = document.createElement("sqlmap");
			sqlMap.setAttribute("resource", i.getResource());

			root.appendChild(sqlMap);
		}
	}
	
	public void addAttributeBean(List<Bean> listBean, Element root) throws Exception {
		for (Bean i : listBean) {

			Element bean = document.createElement("bean");
			bean.setAttribute("id", i.getId());
			bean.setAttribute("class", i.getClazz());
			
			Element property = document.createElement("property");
			property.setAttribute("name", i.getName());
			
			Element ref = document.createElement("ref");
			ref.setAttribute("local", i.getLocal());
			
			property.appendChild(ref);
			bean.appendChild(property);
			
			root.appendChild(bean);
		}
	}
	
	public void addAttributeAction(List<Action> listAction, Element root) throws Exception {
		
		NodeList endList = root.getElementsByTagName("package");
	    Element endElement = (Element) endList.item(0);
		
		for (Action actionBean : listAction) {

			Element action = document.createElement("action");
			action.setAttribute("name", actionBean.getActionName());
			action.setAttribute("method", actionBean.getActionMethod());
			action.setAttribute("class", actionBean.getActionClass());
			
			for(Result resultBean : actionBean.getResults()) {
			
				Element result = document.createElement("result");
				result.setAttribute("name", resultBean.getResultName());
				result.setAttribute("type", resultBean.getResultType());
	
				for(Param paramBean : resultBean.getParam()) {
					
					Element param = document.createElement("param");
					param.setAttribute("name", paramBean.getName());
					param.setTextContent(paramBean.getContent());
					
					result.appendChild( param );
					
				}
				
				action.appendChild(result);
			
			}
			
			endElement.appendChild(action);
		}
	}
	
	public void write( String fileName ) throws Exception {
		DOMSource source = new DOMSource(getDocument());

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		StreamResult result = new StreamResult( fileName );
		transformer.transform(source, result);
	}

	public static void main(String[] args) {

		System.out.println("Acrescentando uma linha ao SqlMapConfig");
		XMLBuilder builder = new XMLBuilder();
		
		long start = System.currentTimeMillis();

		try {
			Element root = builder.recoverRoot("./sqlmap-config.xml");

			List<SqlMap> svr = new ArrayList<SqlMap>();
			svr.add(new SqlMap( "br/com/digicon/vo/LinhaIbatis.xml" ));

			builder.addAttributeSqlMap(svr, root);
			
			builder.write("server1.xml");
			
			System.out.println("Arquivo XML criado com sucesso !!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis() - start;
		System.out.println( "Tempo utilizado : " + end);
		
		System.out.println("Acrescentando uma linha ao ApplicationContext");
		builder = new XMLBuilder();
		
		start = System.currentTimeMillis();

		try {
			Element root = builder.recoverRoot("./applicationContext.xml");

			List<Bean> svr = new ArrayList<Bean>();
			svr.add(new Bean("linhaFacade", "br.com.digicon.sbe.facades.LinhaFacade", "linhaDAO", "linhaDAO"));

			builder.addAttributeBean(svr, root);
			
			builder.write("server2.xml");
			
			System.out.println("Arquivo XML criado com sucesso !!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		end = System.currentTimeMillis() - start;
		System.out.println( "Tempo utilizado : " + end);
		
		System.out.println("Acrescentando uma linha ao struts-cadastro");
		builder = new XMLBuilder();
		
		start = System.currentTimeMillis();

		try {
			Element root = builder.recoverRoot("./struts-cadastros.xml");

			List<Action> svr = new ArrayList<Action>();
			
			Param param1 = new Param("root", "mapaRetorno");
			Param param2 = new Param("contentType", "application/json;charset=ISO-8859-1");
			Param param3 = new Param("enableGZIP", "true");
			
			Param [] params = { param1, param2, param3 };
			
			Result result1 = new Result("success", "json", params);
			Result result2 = new Result("input", "json", params);
			
			Result [] results = { result1, result2 };
			
			Action action = new Action("linha!*", "{1}", "br.com.digicon.sbe.action.Linha", results);
			
			svr.add( action );

			builder.addAttributeAction(svr, root);
			
			builder.write("server3.xml");
			
			System.out.println("Arquivo XML criado com sucesso !!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		end = System.currentTimeMillis() - start;
		System.out.println( "Tempo utilizado : " + end);
		
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
