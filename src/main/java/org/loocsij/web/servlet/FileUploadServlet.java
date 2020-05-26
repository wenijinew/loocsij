/**
 * 
 *//*

package org.loocsij.web.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.loocsij.util.ImageUtil;

public class FileUploadServlet extends HttpServlet {
	*
	 * 
	private static final long serialVersionUID = 1L;

	private static String filePath = "";

	*/
/**
	 * Destruction of the servlet.
	 * 
	 *//*

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	*/
/**
	 * The doPost method of the servlet.
	 * 
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 *//*

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html; charset=UTF-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(4096);
		// the location for saving data that is larger than getSizeThreshold()
		factory.setRepository(new File(filePath));
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum size before a FileUploadException will be thrown
		upload.setSizeMax(1000000);
		try {
			List fileItems = upload.parseRequest(req);
			Iterator iter = fileItems.iterator();
			// Get the file name
			String regExp = ".+\\\\(.+\\.?())$";
			Pattern fileNamePattern = Pattern.compile(regExp);
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String name = item.getName();
					long size = item.getSize();
					if ((name == null || name.equals("")) && size == 0)
						continue;
					Matcher m = fileNamePattern.matcher(name);
					boolean result = m.find();
					if (result) {
						try {
							// String type =
							// m.group(1).substring(m.group(1).lastIndexOf('.')+1);
							InputStream stream = item.getInputStream();
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							byte[] b = new byte[1000];
							while (stream.read(b) > 0) {
								baos.write(b);
							}
							byte[] imageByte = baos.toByteArray();
							String type = ImageUtil.getImageType(imageByte);
							if (type.equals(ImageUtil.TYPE_NOT_AVAILABLE))
								throw new Exception("file is not a image");
							BufferedImage myImage = ImageUtil
									.readImage(imageByte);
							// display the image
							ImageUtil.printImage(myImage, type, res
									.getOutputStream());
							// save the image
							// if you want to save the file into database, do it
							// here
							// when you want to display the image, use the
							// method printImage in
							// ImageUtil
							item.write(new File(filePath + "\\" + m.group(1)));

							stream.close();
							baos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						throw new IOException("fail to upload");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	*/
/**
	 * Initialization of the servlet.
	 * 
	 * 
	 * @throws ServletException
	 *             if an error occure
	 *//*

	public void init() throws ServletException {
		// Change the file path here
		filePath = getServletContext().getRealPath("/");
	}
}
*/
