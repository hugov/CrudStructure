/**
 * 
 */
package br.com.decimal.sword.xml;

/**
 * @author Administrador
 * 
 */
public class Action {

	private String actionName;
	private String actionMethod;
	private String actionClass;

	private Result[] results;
	
	public Action(String actionName, String actionMethod, String actionClass,
			Result[] results) {
		super();
		this.actionName = actionName;
		this.actionMethod = actionMethod;
		this.actionClass = actionClass;
		this.results = results;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	public String getActionClass() {
		return actionClass;
	}

	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	public Result[] getResults() {
		return results;
	}

	public void setResults(Result[] results) {
		this.results = results;
	}

}

class Result {

	private String resultName;
	private String resultType;

	private Param[] param;
	
	public Result(String resultName, String resultType, Param[] param) {
		super();
		this.resultName = resultName;
		this.resultType = resultType;
		this.param = param;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public Param[] getParam() {
		return param;
	}

	public void setParam(Param[] param) {
		this.param = param;
	}

}

class Param {

	private String name;
	private String content;
	
	public Param(String name, String content) {
		super();
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
