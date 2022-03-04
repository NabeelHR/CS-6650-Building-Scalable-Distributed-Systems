import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "StatisticsServlet", value = "/StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Statistics> stats = Arrays.asList(new Statistics("/resorts", "Get", 10, 200));
        Gson gson = new Gson();
        JsonArray jsonResorts = gson.toJsonTree(stats).getAsJsonArray();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("endpointstats", jsonResorts);
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
