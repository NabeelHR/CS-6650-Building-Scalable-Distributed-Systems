import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("GEET REQUESt");
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

        if (urlParts.length == 3) {
            Gson gson = new Gson();
            try {
                ArrayList<String> sb = new ArrayList<>();
                String s;
                while(((s = req.getReader().readLine()) != null)) {
                    sb.add(s);
                }

                VerticalOutput verticalOutput = new VerticalOutput();
                //TODO: Add actual vertical output from db

                if(sb.size() == 1) {
                    verticalOutput.setSeasonID("All");
                    verticalOutput.setTotalVert(300);

                } else if(sb.size() == 2) {
                    verticalOutput.setSeasonID(sb.get(1));
                    verticalOutput.setTotalVert(300);
                }
//                System.out.println(sb.toString());
                String output = gson.toJson(verticalOutput);
//                System.out.println(output);
                PrintWriter out = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                out.print(output);
                out.flush();
                res.setContentType("text/plain");

                res.setStatus(HttpServletResponse.SC_CREATED);

            } catch (Exception ex) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            }
        }
        else if (!isUrlValid(urlParts)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`
            Gson gson = new Gson();

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            String rides = gson.toJson("10000");
            PrintWriter out = res.getWriter();
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            out.print(rides);
            out.flush();

            res.setContentType("text/plain");
//            res.getWriter().write("It works!");
        }
    }

    private boolean isUrlValid(String[] urlPath) {
        // TODO: validate the request url path according to the API spec
        // urlPath  = "/1/seasons/2019/days/1/skiers/123"
        // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
//        System.out.println(urlPath[1]);
        if(!urlPath[2].equalsIgnoreCase("seasons")) {
            return false;
        } else if(!urlPath[4].equalsIgnoreCase("days")){
            return false;
        } else if(!urlPath[6].equalsIgnoreCase("skiers")){
            return false;
        }


//        try{
//
//        }catch () {
//
//        }

        return true;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        System.out.println("GOTE REQUESt");
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
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`

            Gson gson = new Gson();
            try {
                StringBuilder sb = new StringBuilder();
                String s;
                while(((s = req.getReader().readLine()) != null)) {
                    sb.append(s);
                }
                Lift lift = (Lift) gson.fromJson(sb.toString(), Lift.class);
//                res.getWriter().write(lift.toString());
                res.setStatus(HttpServletResponse.SC_CREATED);

                System.out.println("retuened");
            } catch (Exception ex) {
//                res.getWriter().write("Error message");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            }


//            res.getWriter().write("Ian's servlet works!");
        }
    }
}
