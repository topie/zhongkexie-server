package com.topie.zhongkexie.system.aop;

import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topie.zhongkexie.common.utils.date.DateUtil;
import com.topie.zhongkexie.database.core.model.SysLog;
import com.topie.zhongkexie.security.security.SecurityUser;
import com.topie.zhongkexie.security.utils.SecurityUtil;
import com.topie.zhongkexie.system.service.LogService;


@Component
@Aspect 
public class LogAspect {  
	Logger logger = Logger.getLogger(LogAspect.class);
      
    @Autowired  
    private LogService logService;//日志记录Service  
      
    /** 
     * 添加业务逻辑方法切入点 
     */  
    @Pointcut("execution(* com.topie.zhongkexie.*.baseservice.impl.*.save*(..))||"
    		+ "execution(* com.topie.zhongkexie.*.service.impl.*.save*(..))")
    public void insertServiceCall() { }  
      
    /** 
     * 修改业务逻辑方法切入点 
     */  
    @Pointcut("execution(* com.topie.zhongkexie.*.baseservice.impl.*.update*(..))||"
    		+ "execution(* com.topie.zhongkexie.*.service.impl.*.update*(..))")
    public void updateServiceCall() { }  
      
    /** 
     * 
     */  
    @Pointcut("execution(* com.topie.zhongkexie.*.baseservice.impl.*.delete*(..))||"
    		+ "execution(* com.topie.zhongkexie.*.service.impl.*.delete*(..))")  
    public void deleteCall() { }  
    
    @Pointcut("execution(* com.topie.zhongkexie.*.api.*.*(..))")
	public void allCall() {
	}
      
    /** 
     * 管理员添加操作日志(后置通知) 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */  
    @AfterReturning(value="insertServiceCall()", argNames="rtv", returning="rtv")  
    public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable{  
          
          
        //判断参数  
        if(joinPoint.getArgs() == null){//没有参数  
            return;  
        }  
        //获取方法名  
        String methodName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        //获取操作内容  
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName); 
        if(opContent.contains("SysLog"))return;
          
        //创建日志对象  
        insertSysLog("添加成功",opContent,"");
    }  
      
    
     private void insertSysLog(String title,String content,String nll) {
    	 SysLog log = new SysLog();  
    	 //获取登录管理员 
    	 SecurityUser user = SecurityUtil.getCurrentSecurityUser(); 
    	 if(user==null){
    		 return;
    	 }
         log.setCuser(user.getUsername()+"["+user.getId()+"]");//设置管理员id  
         log.setCdate(DateUtil.DateToString(new Date(),"YYYY-MM-dd HH:mm:ss"));
         log.setContent(content);//操作内容 
         log.setTitle(title);//操作  
         log.setCtype(LogService.CAOZUO);
         String ip = SecurityUtil.getCurrentIP();
         log.setIp(ip);
         logService.save(log);//添加日志  
		
	}

	/** 
     * 管理员修改操作日志(后置通知) 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */  
    @AfterReturning(value="updateServiceCall()", argNames="rtv", returning="rtv")  
    public void updateServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable{  
          
        //判断参数  
        if(joinPoint.getArgs() == null){//没有参数  
            return;  
        }  
        //获取方法名  
        String methodName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        //获取操作内容  
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName);  
        if(opContent.contains("SysLog"))return;
        if(opContent.contains("updateLastLoginInfoByUserName"))return;
          
        //创建日志对象  
        insertSysLog("修改成功",opContent,"");
    }  
      
    
    @AfterThrowing(value = "allCall()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, RuntimeException e) {
    	 //获取方法名  
        String methodName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
         
        //获取操作内容  
        String opContent =methodName+"[throw 产生异常的方法名称]：  " + e.getMessage() + ">>>>>>>" + e.getCause();
        //创建日志对象  
        insertSysLog("异常",opContent,"");
	}

	@Around(value = "allCall()")
	public Object aroundCall(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		long procTime = System.currentTimeMillis();
		try {
			result = pjp.proceed();
			procTime = System.currentTimeMillis() - procTime;
			logger.info(pjp.getTarget().getClass().getName() + "."
					+ pjp.getSignature().getName() + "耗时：" + procTime + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    /** 
     * 管理员删除影片操作(环绕通知)，使用环绕通知的目的是 
     * 在影片被删除前可以先查询出影片信息用于日志记录 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */ 
    @AfterReturning(value="deleteCall()", argNames="rtv", returning="rtv") 
    public void deleteCallCalls(JoinPoint joinPoint, Object rtv  ) throws Throwable {  
          
    	  
        //判断参数  
        if(joinPoint.getArgs() == null){//没有参数  
            return;  
        }  
        //获取方法名  
        String methodName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        if(methodName.contains("SysLog"))return;
        //获取操作内容  
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName); 
          
        //创建日志对象  
        insertSysLog("删除成功",opContent,"");
               
      
    } 
      
    /** 
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 
     * 将参数值拼接为操作内容 
     */  
    public String adminOptionContent(Object[] args, String mName) throws Exception{  
  
        if (args == null) {  
            return null;  
        }  
          
        StringBuffer rs = new StringBuffer();  
        rs.append(mName);  
        String className = null;  
        int index = 1;  
        // 遍历参数对象  
        for (Object info : args) {  
              if(info==null){
            	  rs.append("[参数" + index + "，值：NULL"); 
            	  continue;
              }
            //获取对象类型  
            className = info.getClass().getName();  
            className = className.substring(className.lastIndexOf(".") + 1);  
            rs.append("[参数" + index + "，类型：" + className + "，值：");  
              
            /*// 获取对象的所有方法  
            Method[] methods = info.getClass().getDeclaredMethods();  
              
            // 遍历方法，判断get方法  
            for (Method method : methods) {  
                  
                String methodName = method.getName();  
                // 判断是不是get方法  
                if (methodName.indexOf("get") == -1) {// 不是get方法  
                    continue;// 不处理  
                }  
                  
                Object rsValue = null;  
                try {  
                      
                    // 调用get方法，获取返回值  
                    rsValue = method.invoke(info);  
                      
                    if (rsValue == null) {//没有返回值  
                        continue;  
                    }  
                      
                } catch (Exception e) {  
                    continue;  
                }  
                  
                //将值加入内容中  
                rs.append("(" + methodName + " : " + rsValue + ")");  
            }  
              
            rs.append("]");  */
            rs.append(info.toString());
              
            index++;  
        }  
          
        return rs.toString();  
    }  
      
}  
