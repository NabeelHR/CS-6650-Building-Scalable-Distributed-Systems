import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "SingleResortServlet", value = "/SingleResortServlet")
public class SingleResortServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //Test code

        List<Resort> resorts = Arrays.asList(new Resort("Whisler", 1), new Resort("Seymour", 1));
        Gson gson = new Gson();
        JsonArray jsonResorts = gson.toJsonTree(resorts).getAsJsonArray();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("resorts", jsonResorts);
        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        out.print(jsonObject.toString());
        out.flush();

        res.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
