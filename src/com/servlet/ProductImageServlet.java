package com.servlet;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.model.ProductVO;
import com.productimage.model.ProductImageDAOImpl;
import com.productimage.model.ProductImageVO;
import com.uti.tool.ImageUtil;
import com.uti.tool.WriteImg;

@WebServlet("/productImageServlet")
@MultipartConfig
public class ProductImageServlet extends BaseBackServlet {
	private static final long serialVersionUID = 1L;

	public String add(HttpServletRequest request, HttpServletResponse response) {
		// 取得上傳檔案的輸入流和圖檔名稱
		int id = 0;
		InputStream is = super.parseUpload(request);
		String type=null;
		int pid = Integer.parseInt(request.getParameter("pid"));
		try {
			type = new String(request.getParameter("type").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		ProductVO p = productDAOImpl.get(pid);
		ProductImageVO pi = new ProductImageVO();
		pi.setType(type);
		pi.setProduct(p);
		id = productImageDAOImpl.add(pi);
		
		// 生成文件,用新增的ID作為圖檔名稱
		String fileName = id + ".jpg";
		String imageFolder;
		String imageFolder_small = null;
		String imageFolder_middle = null;
		//判斷圖案類型選擇不一樣的存儲路徑
		if (ProductImageDAOImpl.type_single.equals(pi.getType())) {
			imageFolder = request.getServletContext().getRealPath("img/productSingle");
			imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
			imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
		}

		else
			imageFolder = request.getServletContext().getRealPath("img/productDetail");
		File f = new File(imageFolder, fileName);
		f.getParentFile().mkdirs();

		//圖片寫入
		WriteImg.writeImg(is, f);
		//圖片縮小後存入
		if(ProductImageDAOImpl.type_single.equals(pi.getType())){
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);

            ImageUtil.resizeImage(f, 56, 56, f_small);
            ImageUtil.resizeImage(f, 217, 190, f_middle);
        }

		return "@admin_productImage_list?pid=" + p.getProductId();
	}

	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductImageVO pi = productImageDAOImpl.get(id);
		productImageDAOImpl.delete(id);

		if (ProductImageDAOImpl.type_single.equals(pi.getType())) {
			String imageFolder_single = request.getSession().getServletContext().getRealPath("img/productSingle");
			String imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
			String imageFolder_middle = request.getSession().getServletContext()
					.getRealPath("img/productSingle_middle");
			//獲取圖檔位置刪除之
			File f_single = new File(imageFolder_single, pi.getImageId() + ".jpg");
			f_single.delete();
			File f_small = new File(imageFolder_small, pi.getImageId() + ".jpg");
			f_small.delete();
			File f_middle = new File(imageFolder_middle, pi.getImageId() + ".jpg");
			f_middle.delete();

		}

		else {
			String imageFolder_detail = request.getSession().getServletContext().getRealPath("img/productDetail");
			File f_detail = new File(imageFolder_detail, pi.getImageId() + ".jpg");
			f_detail.delete();
		}
		return "@admin_productImage_list?pid=" + pi.getProduct().getProductId();
	}

	public String edit(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	public String update(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	public String list(HttpServletRequest request, HttpServletResponse response) {
		//用該產品ID和圖片類型找出圖檔VO對象選發至View
		String spid = request.getParameter("pid");
		if(spid!=null) {
		int pid = Integer.parseInt(spid);
		ProductVO p = productDAOImpl.get(pid);
		List<ProductImageVO> pisSingle = productImageDAOImpl.list(p, ProductImageDAOImpl.type_single);
		List<ProductImageVO> pisDetail = productImageDAOImpl.list(p, ProductImageDAOImpl.type_detail);

		request.getSession().setAttribute("p", p);
		request.getSession().setAttribute("pisSingle", pisSingle);
		request.getSession().setAttribute("pisDetail", pisDetail);
		}
		return "admin/listProductImage.jsp";
	}
}
