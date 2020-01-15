package cn.cosmos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created with CosmosRay
 *
 * @author CosmosRay
 * @date 2020/01/14
 * Funciton:
 */
@RestController
public class SendEmailController {

    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public void send(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("发送接口调用");

        String filename =this.getClass().getClassLoader().getResource("/").getPath()+"email.text";
        String filename1 =this.getClass().getClassLoader().getResource("/").getPath()+"count.text";
           /*filename = filename.substring(1, filename.length());
          filename1 = filename1.substring(1, filename1.length());*/
        response.setContentType("application/text; charset=utf-8");
        PrintWriter out = response.getWriter();

        //判断该邮箱时候已经订阅过
        FileReader fr=new FileReader(filename);
        BufferedReader br=new BufferedReader(fr);
        String line="";
        String[] arrs=null;
        while ((line=br.readLine())!=null) {
            if(line.equals(request.getParameter("SendEmail").toString()+"\t")){
                out.write("1");
                return;
            }
        }
        br.close();
        fr.close();

        FileWriter writer = new FileWriter(filename, true);
        //writer.write(request.getParameter("SendEmail").toString()+  ";"+"/r/n");
        writer.write(request.getParameter("SendEmail").toString()+"\t\n");
        writer.close();

        File f = new File(filename1);
        int count = 0;
        if (!f.exists()) {
            writeFile(filename1, 100);
        }
        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            try {
                count = Integer.parseInt(in.readLine())+1;
                writeFile(filename1, count);
                out.write(String.valueOf(count));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String filename, int count) {

        try {
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            out.println(count);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
