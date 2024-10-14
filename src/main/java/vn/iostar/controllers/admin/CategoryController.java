package vn.iostar.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iostar.models.CategoryModel;
import vn.iostar.services.ICategoryService;
import vn.iostar.services.impl.CategoryServiceImpl;
import vn.iostar.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024
		* 1024, maxFileSize = Constant.MAX_FILE_SIZE, maxRequestSize = Constant.MAX_REQUEST_SIZE)
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/edit",
		"/admin/category/insert", "/admin/category/update", "/admin/category/delete", "/admin/category/search" })
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public ICategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().toString();

		if (url.contains("add")) {
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
		} else if (url.contains("edit")) {
			int id = Integer.parseInt(req.getParameter("id"));

			CategoryModel category = cateService.findById(id);
			req.setAttribute("cate", category);

			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
		} else if (url.contains("delete")) {
			String id = req.getParameter("id");
			cateService.delete(Integer.parseInt(id));
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		} else {
			List<CategoryModel> list = cateService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().toString();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		if (url.contains("insert")) {

			String categoryname = req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));

			CategoryModel category = new CategoryModel();
			category.setCategoryName(categoryname);
			category.setActive(status);

			// upload file
			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("image");
				if (part.getSize() > 0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					// doi ten file
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index + 1);
					fname = System.currentTimeMillis() + "." + ext;
					// upload
					part.write(uploadPath + "/" + fname);
					// ghi ten file vao data
					category.setImage(fname);
				} else {
					category.setImage("avatar.png");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		} else if (url.contains("update")) {

			int categoryid = Integer.parseInt(req.getParameter("categoryid"));
			String categoryname = req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));

			CategoryModel category = new CategoryModel();
			category.setCategoryId(categoryid);
			category.setCategoryName(categoryname);
			category.setActive(status);
			
			// old pic
			CategoryModel cateold = cateService.findById(categoryid);
			String fileold = cateold.getImage();

			// upload file
			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("image");
				if (part.getSize() > 0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					// doi ten file
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index + 1);
					fname = System.currentTimeMillis() + "." + ext;
					// upload
					part.write(uploadPath + "/" + fname);
					// ghi ten file vao data
					category.setImage(fname);
				} else {
					category.setImage(fileold);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			cateService.update(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}

	}
}
