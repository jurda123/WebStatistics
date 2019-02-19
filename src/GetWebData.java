

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.jsoup.Jsoup;

/**
 * Servlet implementation class GetWebData
 */
@WebServlet("/GetWebData")
public class GetWebData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetWebData() {
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String urlString = (String)request.getParameter("url");
		  StringBuilder result = new StringBuilder();
	      URL url = new URL(urlString);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      connection.setRequestMethod("GET");
	      connection.setRequestProperty("Content-Type", "text; charset=utf-8");
	      connection.addRequestProperty("Accept-Encoding", "utf-8");
	      
	      BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	     
	      String pureText = Jsoup.parse(result.toString()).text();

	      pureText = pureText.replaceAll("[^a-zA-Z0-9]+"," ");
	      
	      String[] array = pureText.toString().split(" ");
	      
	     List<String> words = Arrays.asList(array);
	     
	     HashMap<String,Integer> occurences = new HashMap<String,Integer>();
	     
	     for(int i=0; i<array.length; i++){
	            if(occurences.containsKey(array[i])){
	            	occurences.put(array[i], occurences.get(array[i]) + 1);
	            } else {
	            	occurences.put(array[i], 1);
	            }
	        }
	        
	        response.getWriter();
	        
	        char[] arrayOfLetters = pureText.toString().toCharArray();
	       
	        HashMap<Character,Integer> letterOccurences = new HashMap<Character,Integer>();
		     
		     for(int j=0; j<arrayOfLetters.length; j++){
		            if(letterOccurences.containsKey(arrayOfLetters[j])){
		            	letterOccurences.put(arrayOfLetters[j], letterOccurences.get(arrayOfLetters[j]) + 1);
		            } else {
		            	letterOccurences.put(arrayOfLetters[j], 1);
		            }
		        }
		        
		        JSONObject json = new JSONObject();
		        
		        List<String> wordOccurences = new ArrayList<String>();
		        Iterator it = occurences.entrySet().iterator();
		        while (it.hasNext()) {
		            Map.Entry pair = (Map.Entry)it.next();
		            wordOccurences.add(new Occurence(pair.getKey().toString(), pair.getValue().toString()).toString());
		            it.remove(); 
		        }
		        
		        List<String> lettersOccurences = new ArrayList<String>();
		        Iterator iterator2 = letterOccurences.entrySet().iterator();
		        while (iterator2.hasNext()) {
		            Map.Entry pair = (Map.Entry)iterator2.next();
		            if(Character.isLetter(pair.getKey().toString().charAt(0))){
		            	 lettersOccurences.add(new Occurence(pair.getKey().toString(), pair.getValue().toString()).toString());
		            }
		           
		            iterator2.remove(); 
		        }
		        json.append("wordOccurences", wordOccurences);
		        json.append("letteroccurences", lettersOccurences);
		        response.getWriter().write(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
   
response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
