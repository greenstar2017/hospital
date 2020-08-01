/**
 *
 * yuanhualiang
 */
package com.green.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.annotations.NoRequireLogin;

/**
 * @author yuanhualiang
 *
 */
@Controller
@RequestMapping("/download")
public class DownloadController extends BaseController {

	private static final String DOWNLOAN_FILE_PATH = "/data/download/";

	/**
	 * 下载文件
	 * @param fileName
	 * @param request
	 * @param response
	 */
	@NoRequireLogin
	@RequestMapping(method = RequestMethod.GET, value = "/{fileName:.+}")
	public void download(@PathVariable String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		if("occupy.exe".equals(fileName)) {
			fileName = "易采吉抢位软件.exe";
		}
		Path file = Paths.get(DOWNLOAN_FILE_PATH + fileName);
		if (Files.exists(file)) {
			response.setContentType("application/octet-stream");
			try {
				response.addHeader(
						"Content-Disposition",
						"attachment; filename="
								+ URLEncoder.encode(fileName, "UTF-8"));
				Files.copy(file, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
