import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ResortServlet", value = "/ResortServlet")
public class ResortServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
//        res.getWriter().write("resort works!");
        Gson gson = new Gson();

        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing paramterers");
            return;
        }
        String[] urlParts = urlPath.split("/");

        if (urlParts.length == 3) {
            int resortID;
            try{
                resortID = Integer.parseInt(urlParts[1]);
            } catch(Exception e) {
                System.out.println("Badd int parse int");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            List<String> seasons = new ArrayList<String>();
            seasons.add("2010");
            seasons.add("2011");
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonSeasons = new Gson().toJsonTree(seasons).getAsJsonArray();
            jsonObject.add("Seasons", jsonSeasons);

            PrintWriter out = res.getWriter();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            out.print(jsonObject.toString());
            out.flush();

            res.setStatus(HttpServletResponse.SC_CREATED);

        } else if (!isUrlValidDays(urlParts)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            System.out.println("not valid");
            return;
        } else {

            //Created values;
            ResortSkierOutput resortSkierOutput = new ResortSkierOutput("Mission impossible", 789123);
            String output = gson.toJson(resortSkierOutput);
            PrintWriter out = res.getWriter();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            out.print(output);
            out.flush();

        }

    }

    /**
     * Two separate url paths.
     * @param urlPath
     * @return
     */
    private boolean isUrlValidDays(String[] urlPath) {
        // TODO: validate the request url path according to the API spec
        // urlPath  = "/1/seasons/2019/days/1/skiers/123"
        // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]


        if (!urlPath[2].equalsIgnoreCase("seasons")) {
//            System.out.println(urlPath[2]);

            return false;
        } else if (!urlPath[4].equalsIgnoreCase("day")) {
            System.out.println(urlPath[4]);

            return false;
        } else if (!urlPath[6].contains("skiers")) {
            //Equals ignore case does not work.
            System.out.println(urlPath[6]);

            return false;
        }

        return true;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
//        res.getWriter().write("resort works!");

        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing paramterers");
            return;
        }
        String[] urlParts = urlPath.split("/");
        int resortID;
        try{
            resortID = Integer.parseInt(urlParts[1]);
        } catch(Exception e) {
            System.out.println("Badd int parse int");
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Gson gson = new Gson();
        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while(((s = req.getReader().readLine()) != null)) {
                sb.append(s);
            }

            ResortYear resortYear = (ResortYear) gson.fromJson(sb.toString(), ResortYear.class);
//            System.out.println(resortYear.toString());
            res.setStatus(HttpServletResponse.SC_CREATED);

        } catch (Exception ex) {
//                res.getWriter().write("Error message");
            System.out.println("Badd gson");

            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }

    }
}
