package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

/**
 * Class which specifically contains functions to crawl through the unicode 
 * emoji database and extract the unicodes, annotations and names of twitter
 * emoji.
 * 
 * date - 13/01/2016
 * authors - Wicke
 */

public class crawlPage {
	public crawlPage() {
	}
	
	/**
	 * Read the html file  -> found in src.txt (stores entire html page)
	 * Extract info		   -> data.txt has unicode, annotations and names of emoji
	 */
	public void readScr(){
		// Current line in html file
		String line;
		
		try {
			// Contains the webpage in "src.txt"
			FileInputStream fis = new FileInputStream("src.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));

			// Save extracted date to "data.txt"
			FileOutputStream fos=new FileOutputStream("data.txt");
	        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
			
	        // run through html file
	        Boolean flag_hasAnno = false;
	        
			while((line=br.readLine())!=null){
				
				// get unicode by key-word
				String unicode 	= null;
				String key_code	= "<td class='code'"; 
				
				if(line.contains(key_code)){
					if(!flag_hasAnno){
						bw.newLine();
					}
					flag_hasAnno = false;
					// cut of the found line at the necessary symbol appearence
					unicode=line.substring(line.indexOf("#"), line.indexOf("' name"));
					bw.write(unicode);
				}
				// get annotations
				String key_annot = "<td class='name'";
				
				if(line.contains(key_annot)){
					//System.out.println(line);					
					// every occurence of html# marks an annotation
					if(line.contains("html#")){
						flag_hasAnno = true;
						String token[] = line.split("html#");
						StringBuffer sb = new StringBuffer();
						
						// iterate through and store all annotations
						for (int i = 1; i < token.length; i++) {
							String anno = (token[i].substring(0,token[i].indexOf("'")));
							sb.append(anno+"|");
						}
						bw.write("\t"+sb.toString().substring(0, sb.length()-1));
						bw.newLine();
					}
					else{
						String name = line.substring(line.indexOf("name'>")+6, line.indexOf("</td"));
						bw.write("\t"+name);
					} 		
				}
			}
			bw.close();
			fos.close();
			br.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Unused function to store an image from a webpage
	 */
	public void saveImg(String url, String filePath){
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			OutputStream os = new FileOutputStream(filePath);
			
			byte[] b = new byte[2048];
			int length;
			
			while((length = is.read(b))!= -1){
				os.write(b, 0, length);
			}
			is.close();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Stores an entire webpage as html file
	 */
	public void readPage(){
		try {
			// open url
			URL oracle = new URL("http://unicode.org/emoji/charts/full-emoji-list.html");
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	        
	        // open file to store html as txt
	        FileOutputStream fos=new FileOutputStream("src.txt");
	        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
	        
	        String inputLine;
	        while ((inputLine = in.readLine()) != null){
	        	bw.write(inputLine);
	        bw.newLine();}
	            //System.out.println(inputLine);
	        in.close();
	        bw.close();
	        fos.close();
		} catch (Exception e) {
			System.err.println("Whoops, an error occured.");
		}
		
	}

	public void buildDb() {
		int count = 0;
		String line = null;
		
		try {
			FileInputStream fis = new FileInputStream("data.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
	
			FileOutputStream fos=new FileOutputStream("db.txt");
	        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
	        
	        while((line=br.readLine())!=null){
	        		
	        		
	        		String segments[] 	= line.split("\\t");
	        		System.out.println(segments[1]);
	        		String unicode 		= segments[0];
	        		String annots 		= segments[segments.length-1];
	        		
	        		String annota[]		= annots.split("|");
	        		
	        		for (int i = 0; i < annota.length; i++) {
						bw.write(unicode+"\t"+annota[i]+"\n");
					}
	        		
	        		count++;
	        		if(count >=2){
	        			break;
	        		}
	        }		
		br.close();
		bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
