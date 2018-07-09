package com.topie.zhongkexie.core.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.topie.zhongkexie.common.utils.ResponseUtil;
import com.topie.zhongkexie.core.service.IDeptService;
import com.topie.zhongkexie.core.service.IScorePaperService;
import com.topie.zhongkexie.database.core.model.Attachment;
import com.topie.zhongkexie.database.core.model.Dept;
import com.topie.zhongkexie.database.core.model.User;
import com.topie.zhongkexie.security.service.UserService;
import com.topie.zhongkexie.system.service.IAttachmentService;

@Controller
@RequestMapping("/api/answerImport")
public class AnswerImportController {
	@Autowired
	private IScorePaperService iScorePaperService;
	@Autowired
	private IAttachmentService iAttachmentService;
	@Autowired
	private IDeptService iDeptService;
	@Autowired
	private UserService userService;

	@RequestMapping("template")
	public void exportPaper(Integer paperId, HttpServletRequest request,
			HttpServletResponse response) {
		HSSFWorkbook wb = iScorePaperService.exportPaper(paperId, null, null,
				"withID");
		try {
			String p = this.iScorePaperService.selectByKey(paperId).getTitle()
					+ "详细得分情况";
			outWrite(request, response, wb, p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("import")
	public Object importAnswer(Integer paperId, Integer fileId, Integer orgId) {
		Attachment attachment = iAttachmentService.selectByKey(fileId);
		if (attachment == null)
			return ResponseUtil.error("附件不存在！");
		if (paperId == null)
			return ResponseUtil.error("paperId不能为空");
		if (orgId == null)
			return ResponseUtil.error("选择学会不能为空");
		Dept dept = iDeptService.selectByKey(orgId);
		String code  = dept.getCode();
		String userLoginName = code+"002";
		User user = userService.findUserByLoginName(userLoginName);
		if(user==null)return ResponseUtil.error("选择的学会填报用户不存在");
		Integer userId = user.getId();
		iScorePaperService.importAnswer(attachment, paperId, userId);
		return ResponseUtil.success();
	}

	private static void outWrite(HttpServletRequest request,
			HttpServletResponse response, HSSFWorkbook wb, String fileName)
			throws IOException {
		OutputStream output = null;
		try {
			output = response.getOutputStream();
			response.reset();
			// fileName=new String((fileName+"导出数据.xls").getBytes(),
			// "ISO_8859_1");
			fileName = URLEncoder.encode(fileName + "导出数据.xls", "UTF-8");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			// response.setHeader("Content-disposition",
			// "attachment;filename*=UTF-8 "
			// + URLEncoder.encode(fileName,"UTF-8"));
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			wb.write(output);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}
}
