//import utility.Logr;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;











@WebServlet(name = "CoolServlet", value = "/CoolServlet")
public class CoolServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");
		String urlPath = req.getPathInfo();

		// check we have a URL!
		if (urlPath == null || urlPath.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			res.getWriter().write("missing paramterers");
			return;
		}

		String[] urlParts = urlPath.split("/");
		// and now validate url path and return the response status code
		// (and maybe also some value if input is valid)

		if (!isUrlValid(urlParts)) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			res.setStatus(HttpServletResponse.SC_OK);
			// do any sophisticated processing with urlParts which contains all the url params
			// TODO: process url params in `urlParts`
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			res.getWriter().write("2282882");
		}
	}

	private boolean isUrlValid(String[] urlPath) {
		// TODO: validate the request url path according to the API spec
		// urlPath  = "/1/seasons/2019/day/1/skier/123"
		// urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
		return true;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain");
		String urlPath = req.getPathInfo();
		// check we have a URL!
		if (urlPath == null || urlPath.isEmpty()) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			res.getWriter().write("missing paramterers");
			return;
		}

		String[] urlParts = urlPath.split("/");
		// and now validate url path and return the response status code
		// (and maybe also some value if input is valid)

		if (!isUrlValid(urlParts)) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			res.setStatus(HttpServletResponse.SC_OK);
			// do any sophisticated processing with urlParts which contains all the url params
			// TODO: process url params in `urlParts`
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			res.getWriter().write("It works POST 0.5s!");
		}
	}
}
