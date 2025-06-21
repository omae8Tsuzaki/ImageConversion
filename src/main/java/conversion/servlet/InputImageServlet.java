package conversion.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * <p>画像の入力を行うサーブレット。</p>
 */
@WebServlet("/function/inputImage")
@MultipartConfig
public class InputImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InputImageServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Part filePart = request.getPart("imageFile");
		if (filePart == null || filePart.getSize() == 0) {
			response.sendRedirect("/function/sampleConversion.jsp");
			return;
		}

		try (InputStream inputStream = filePart.getInputStream()) {
			BufferedImage originalImage = ImageIO.read(inputStream);
			if (originalImage == null) {
				RequestDispatcher rd = request.getRequestDispatcher("/exceptionMessage.jsp");
				request.setAttribute("exception", "無効な画像ファイルです");
				rd.forward(request, response);
				return;
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			byte[] imageBytes = baos.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);

			request.setAttribute("base64Image", base64Image);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/function/sampleConversion.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
