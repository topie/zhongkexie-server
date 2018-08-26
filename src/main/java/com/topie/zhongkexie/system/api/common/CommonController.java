package com.topie.zhongkexie.system.api.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.topie.zhongkexie.common.tools.filemanager.DefaultFileManagerTool;
import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.common.utils.Result;
import com.topie.zhongkexie.common.utils.ZipUtil;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IScoreItemService;
import com.topie.zhongkexie.database.core.model.Attachment;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.security.utils.SecurityUtil;
import com.topie.zhongkexie.system.service.IAttachmentService;

/**
 * Created by chenguojun on 8/31/16.
 * 通用上传api
 */
@Controller
@RequestMapping("/api/common")
public class CommonController {

    private final static Integer IMAGE = 0;

    private final static Integer FILE = 1;

    @Autowired
    private IAttachmentService iAttachmentService;
    @Autowired
    private DefaultFileManagerTool defaultFileManagerTool;
    @Autowired
    private IScoreItemService scoreItemService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/uploadFile", method = { RequestMethod.POST })
    @ResponseBody
    public Result uploadFile(HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseUtil.error("请选择文件。");
            }
            //文件夹名
            String dirName = "file";
            // 最大文件大小
            long maxSize = 20000000;

            // 定义允许上传的文件扩展名
            HashMap<String, String> extMap = new HashMap<String, String>();
            extMap.put(dirName,
                    "apk,doc,docx,xls,xlsx,ppt,pptx,pdf,txt,gif,tif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");

            Attachment attachment = iAttachmentService
                    .uploadFileAttachement(request, file, dirName, maxSize, extMap, FILE);
            return ResponseUtil.success(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("上传失败。");
        }
    }

    @RequestMapping(value = "/uploadImage", method = { RequestMethod.POST })
    @ResponseBody
    public Result uploadImage(HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseUtil.error("请选择文件。");
            }
            //文件夹名
            String dirName = "image";
            // 最大文件大小
            long maxSize = 10000000;

            // 定义允许上传的文件扩展名
            HashMap<String, String> extMap = new HashMap<String, String>();
            extMap.put(dirName, "gif,jpg,jpeg,png,bmp");

            Attachment attachment = iAttachmentService
                    .uploadFileAttachement(request, file, dirName, maxSize, extMap, IMAGE);
            return ResponseUtil.success(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("上传失败。");
        }
    }

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFiles(HttpServletRequest request,
            @RequestParam(value = "files[]", required = false) MultipartFile[] file) throws Exception {
        if (file.length > 0) {
            for (MultipartFile multipartFile : file) {
                //文件夹名
                String dirName = "file";
                // 最大文件大小
                long maxSize = 20000000;

                // 定义允许上传的文件扩展名
                HashMap<String, String> extMap = new HashMap<String, String>();
                extMap.put(dirName,
                        "doc,docx,xls,xlsx,ppt,pptx,pdf,txt,gif,tif,jpg,jpeg,png,bmp,swf,flv,mp3,mp4,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");

                Attachment attachment = iAttachmentService
                        .uploadFileAttachement(request, multipartFile, dirName, maxSize, extMap, FILE);
                return attachment;
            }
        }
        return false;
    }

    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    @ResponseBody
    public Result attachment(@RequestParam("attachmentId") Integer attachmentId) throws Exception {
        if (attachmentId != null) {
            Attachment attachment = iAttachmentService.selectByKey(attachmentId);
            if (attachment != null) {
            	attachment.setAttachmentPath(null);
            	attachment.setUploadLoginName(null);
                return ResponseUtil.success(attachment);
            }
        }
        return ResponseUtil.error("附件id不存在。");
    }
    
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response, HttpServletRequest httpServletRequest,
    		@RequestParam("id") Integer attachmentId,
    		@RequestParam(value="itemId",required=false) Integer itemId,
    		@RequestParam(value="deptId",required=false) Integer deptId,
    		@RequestParam(value="userId",required=false) Integer userId) throws Exception {
    	
    	if (attachmentId != null) {
            Attachment attachment = iAttachmentService.selectByKey(attachmentId);
            if (attachment != null) {
            	String path = "";
                String filePath = attachment.getAttachmentPath();
                if(itemId!=null){
                	path = scoreItemService.selectPath(itemId)+"_";
                }
                path = addDeptName(path,deptId,userId);
                defaultFileManagerTool.download(response, filePath, path+attachment.getAttachmentName());
            }
        }
    	
    }
    
    private String addDeptName(String path, Integer deptId, Integer userId) {
    	if(deptId!=null){
			Dept d = deptService.selectByKey(deptId);
			if(d!=null)
				return d.getName()+"_"+path;
		}
    	if(userId!=null){
			User d = userService.selectByKey(userId);
			if(d!=null)
				return d.getDisplayName()+"_"+path;
		}
		return path;
	}

	@RequestMapping(value = "/downloadzip", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response, HttpServletRequest httpServletRequest,
    		@RequestParam("id") String attachmentId,
    		@RequestParam(value="itemId",required=false) Integer itemId,
    		@RequestParam(value="deptId",required=false) Integer deptId,
    		@RequestParam(value="userId",required=false) Integer userId) throws Exception {
    	
    	if (attachmentId != null) {
    		String[] atts = attachmentId.split("_");
    		List<File> files = new ArrayList<File>();
    		for(int i=0;i<atts.length;i++){
	    		Integer attId = Integer.valueOf(atts[i]);
	            Attachment attachment = iAttachmentService.selectByKey(attId);
	            if (attachment != null) {
	            	try{
	            	File file = new File(attachment.getAttachmentPath());
	            	files.add(file);
	            	}catch(Exception e){}
	            }
    		}
    		if(files.size()>0){
    			String path = getPath( httpServletRequest);
    			File zipfile = new File(path+"file_"+System.currentTimeMillis());
    			ZipUtil.zipFiles(files, zipfile);
    			String itemPath = "";
                if(itemId!=null){
                	itemPath = scoreItemService.selectPath(itemId)+"_";
                }
                itemPath = addDeptName(itemPath,deptId,userId);
    			defaultFileManagerTool.download(response, zipfile, itemPath+"上传资料.zip");
    		}
        }
    	
    }

	private String getPath(HttpServletRequest request) {
		String savePath = request.getSession().getServletContext().getRealPath("/") + "ziptemp/" + SecurityUtil
                .getCurrentUserName() + "/";
        // 文件保存目录URL
        File targetFile = new File(savePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 创建文件夹
        savePath += "ziptemp" + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
		return savePath;
	}

}
