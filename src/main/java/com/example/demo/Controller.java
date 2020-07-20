package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

@RestController
public class Controller {
	
	public static void downloadFileFromUrl(String url, String file) throws IOException{
		  File filer = new File(file);
		  filer.createNewFile();
		  
		  URL website = new URL(url);
		  HttpURLConnection connection = (HttpURLConnection) website.openConnection();
		    /*for (String s : connection.getHeaderFields().keySet()) {
		    	System.out.println(s+": "+connection.getHeaderFields().get(s));
		    }*/
		    connection.setRequestMethod("GET");
		    //connection.setRequestProperty("Content-Type", "application/json");
		    try {
			  ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
			  FileOutputStream fos = new FileOutputStream(file);
			  fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			  fos.close();
		    } catch (ConnectException e) {
		    	System.out.println("Failed to connect, moving on...");
		    }
	  }
	
	@GetMapping("/math/calculate")
	public String piDisplay(@RequestParam(value="operation",required=false) String operation,
			@RequestParam(value="x") String x,
			@RequestParam(value="y") String y) {
		int val1=Integer.parseInt(x);
		int val2=Integer.parseInt(y);
		switch (operation) {
			case "subtract":{
				return Integer.toString(val1-val2);
			}
			case "multiply":{
				return Integer.toString(val1*val2);
			}
			case "divide":{
				return Integer.toString(val1/val2);
			}
			default:{
				return Integer.toString(val1+val2);
			}
		}
	}
	
	@PostMapping("/math/sum")
	public String sumDisplay(@RequestParam Map<String,String> keys) {
		int sum = 0;
		for (String i : keys.keySet()) {
			sum += Integer.parseInt(keys.get(i));
		}
		return Integer.toString(sum);
	}
	
	@GetMapping("/math/pi")
	public String piDisplay() {
		return Double.toString(Math.PI);
	}

    @GetMapping("/image")
    public HashMap<String,String> helloWorld(@RequestParam("url") String url){
		try {
			downloadFileFromUrl("http://pbs.twimg.com/media/EdKE8xzVcCEf1qd.jpg","temp");
			BufferedImage img = ImageIO.read(new File("temp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
}