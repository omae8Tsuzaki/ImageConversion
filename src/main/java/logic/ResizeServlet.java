package logic;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

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
 * Servlet implementation class ResizeServlet
 */
@WebServlet("/resize")
@MultipartConfig
public class ResizeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResizeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 画像ファイルの取得
        Part filePart = request.getPart("imageFile");
        int newWidth = Integer.parseInt(request.getParameter("width"));
        int newHeight = Integer.parseInt(request.getParameter("height"));
        
        if (filePart != null && filePart.getSize() > 0) {
            // 入力ストリームからBufferedImageを作成
            InputStream inputStream = filePart.getInputStream();
            BufferedImage originalImage = ImageIO.read(inputStream);

            System.out.println("aaaaaaaaaa");
            if (originalImage != null) {
                // リサイズ処理
                BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
                g2d.dispose();

//                // レスポンスの設定
//                response.setContentType("image/png");
//                // クライアントにデータを送信するための 出力ストリームを取得
//                OutputStream outputStream = response.getOutputStream();
//                ImageIO.write(resizedImage, "png", outputStream);
//                outputStream.close();
                // 一時ファイルとして保存 (例: /uploads/resized.png)
                String uploadPath = getServletContext().getRealPath("/") + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                
                File outputFile = new File(uploadDir, "resized.png");
                ImageIO.write(resizedImage, "png", outputFile);

                System.out.println("aaaaaaaaaa");
                // JSP にパスを渡す
                request.setAttribute("resizedImagePath", "uploads/resized.png");
//                RequestDispatcher dispatcher = request.getRequestDispatcher("resize.jsp");
//                dispatcher.forward(request, response);
                response.sendRedirect("./resize");
            } else {
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効な画像ファイルです");
                RequestDispatcher rd = request.getRequestDispatcher("/exceptionMessage.jsp");
    			request.setAttribute("exception", "無効な画像ファイルです");
    			rd.forward(request, response);
    			return;
            }
        } else {
            //response.sendError(HttpServletResponse.SC_BAD_REQUEST, "画像ファイルを選択してください");
            RequestDispatcher rd = request.getRequestDispatcher("/exceptionMessage.jsp");
			request.setAttribute("exception", "画像ファイルを選択してください");
			rd.forward(request, response);
			return;

        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
