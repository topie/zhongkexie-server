package com.topie.zhongkexie.common.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.topie.zhongkexie.database.core.model.ScoreAnswer;

public class JavaExecScript {
	   
	  public static void main(String[] args) {
	   // System.out.println(jsObjFunc());
	    //System.out.println(Arrays.toString(getArray()));
	    //System.out.println(jsCalculate("a=1+2+3+(2*2)"));
	    //jsFunction();
//	   // jsVariables();
//	    String fn = "var s = that;s = s.split(',')[0]; return 'hello,'+s; ";
//	    jsFunction("我,ni =",fn);
	    String functionBody="var v = value.split(\",\")[0];var sc1 = 0;if(v!=0){ if(v>=50000){  sc1 = score*0.5; }else{ sc1 = score*v/100000; }}var v1 = value.split(\",\")[1];var sc2 = 0;var s = 80;if(v>=10000 && v<30000)s=70;if(v>=30000 && v<50000)s=60;if(v>=50000)s=50;var bai = (parseFloat(v1)/parseFloat(v)*100);if(bai>=s) sc2 = score*0.5;else sc2=(score*0.5)*(bai/parseFloat(s));return sc1+sc2;";
	    ScriptEngineManager sem = new ScriptEngineManager();
	    ScriptEngine se = sem.getEngineByName("javascript");
	    try {
	      String script = "function getSocre(that,options,refer,value,score){ "+functionBody+" }";
	      se.eval(script);
	      Invocable inv2 = (Invocable) se;
	      BigDecimal res = new BigDecimal((Double)inv2.invokeFunction("getSocre", null,null,null,"29404,1483",9.875));
	      System.out.print(res.toString());
	    } catch (Exception e) {
	    	System.err.println(functionBody);
	    	e.printStackTrace();
	    }
	  }
	  
	  /**
	   * 运行JS基本函数
	   */
	  public static String jsFunction(Object data,String functionBody) {
	    ScriptEngineManager sem = new ScriptEngineManager();
	    ScriptEngine se = sem.getEngineByName("javascript");
	    try {
	      String script = "function say(value){ "+functionBody+"}";
	      se.eval(script);
	      Invocable inv2 = (Invocable) se;
	      String res = (String) inv2.invokeFunction("say", data); 
	      return res;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
	 
	  /**
	   * 运行JS对象中的函数
	   * 
	   * @return
	   */
	  public static Object jsObjFunc() {
	    String script =
	        "var obj={run:function(){return 'run method : return:\"abc'+this.next('test')+'\"';},next:function(str){return ' 我来至next function '+str+')'}}";
	    ScriptEngineManager sem = new ScriptEngineManager();
	    ScriptEngine scriptEngine = sem.getEngineByName("js");
	    try {
	      scriptEngine.eval(script);
	      Object object = scriptEngine.get("obj");
	      Invocable inv2 = (Invocable) scriptEngine;
	      return (String) inv2.invokeMethod(object, "run");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
	 
	  /**
	   * 获取js对象数字类型属性
	   * 
	   * @return
	   */
	  public static Object[] getArray() {
	    ScriptEngineManager sem = new ScriptEngineManager();
	    String script = "var obj={array:['test',true,1,1.0,2.11111]}";
	 
	    ScriptEngine scriptEngine = sem.getEngineByName("js");
	    try {
	      scriptEngine.eval(script);
	      Object object2 = scriptEngine.eval("obj.array");
	      Class clazz = object2.getClass();
	      Field denseField = clazz.getDeclaredField("dense");
	      denseField.setAccessible(true);
	      return (Object[]) denseField.get(object2);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
	 
	  /**
	   * JS计算
	   * 
	   * @param script
	   * @return
	   */
	  public static Object jsCalculate(String script) {
	    ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("javascript");
	    try {
	      return (Object) engine.eval(script);
	    } catch (ScriptException ex) {
	      ex.printStackTrace();
	    }
	    return null;
	  }
	 
	  /**
	   * 运行JS基本函数 ( that,options,refer,value,score)
	 * @param functionBody 
	 * @param referItemValue 
	 * @param scoreItem 
	 * @param referItemValue 
	   */
	  public static BigDecimal jsFunction(ScoreAnswer scoreItem, Object options, ScoreAnswer referItem, String functionBody) {
	    ScriptEngineManager sem = new ScriptEngineManager();
	    ScriptEngine se = sem.getEngineByName("javascript");
	    try {
	      String script = "function getSocre(that,options,refer,value,score){ "+functionBody+" }";
	      se.eval(script);
	      Invocable inv2 = (Invocable) se;
	      BigDecimal res = new BigDecimal((Double)inv2.invokeFunction("getSocre", scoreItem,options,referItem,scoreItem.getAnswerValue(),scoreItem.getItemScore()));
	      return res;
	    } catch (Exception e) {
	    	System.err.println(scoreItem.getAnswerValue());
	    	System.err.println(functionBody);
	    	e.printStackTrace();
	    }
	    return new BigDecimal(0);
	  }
	 
	  /**
	   * JS中变量使用
	   */
	  public static void jsVariables() {
	    ScriptEngineManager sem = new ScriptEngineManager();
	    ScriptEngine engine = sem.getEngineByName("javascript");
	    File file = new File("/data/js.txt");
	    engine.put("file", file);
	    try {
	      engine.eval("println('path:'+file.getAbsoluteFile())");
	    } catch (ScriptException e) {
	      e.printStackTrace();
	    }
	 
	  }
	}
