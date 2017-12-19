package httpServicio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Date;

public class atenderCliente extends Thread
{
	private Socket s = null;
	private DataInputStream in = null;
	private FileInputStream fileIn = null;
	private DataOutputStream out = null;
	private String rutaHome;
	
	public atenderCliente(Socket s,String rutaHome)
	{
		this.s = s;
		this.rutaHome = rutaHome;
	}
	
	public void run()
	{

		try
		{
			
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			
			byte [] buff = new byte [20];
			boolean range = false;
			
			String linea = in.readLine();
			System.out.println(linea);
			
			String lineaRange=linea;
			
			while(lineaRange.length() != 0 && range == false)
			{
				lineaRange = in.readLine();
				System.out.println(lineaRange);
				
				if(lineaRange.startsWith("Range: bytes="))
					range = true;
			}
			
			if(linea != null)
			{
				File f = buscaFichero(linea);
				
				//si el fichero no existe en el /home, lanzará el error 404.
				if(f == null)
				{
					//501
					sendMIMEHeading(out, 501 , "" , makeHTMLErrorText(501, "").length() );
					out.write( makeHTMLErrorText(501, "").getBytes() );// no se si este parametro "" está bien
					out.flush();
				}
				else if(!f.exists() || !f.isFile())
				{
					//404
					sendMIMEHeading(out, 404 , "" , makeHTMLErrorText(404, f.getName()).length() );
					out.write( makeHTMLErrorText(404, f.getName()).getBytes() );
					out.flush();
				}
				else
				{
					/////////////////
					//distinguir cuatro casos, get -> get con range -> y luego head con range o sin range
					/////////////////

					String cType="";
					if(f.getName().endsWith(".css"))
					{
						cType="text/css";
					}
					else
					{
						cType = URLConnection.guessContentTypeFromName(f.getName());
					}
					
					
					
					
					
					/////////////////////////////////////////////////////
					//Calculo los ini y fin
					long ini = 0;
					long fin = 0;
					if(range)
					{
						String [] iniFin = lineaRange.substring("Range: bytes=".length(),lineaRange.length()).split("-");
						//calculo los ini y fin de la cadena range

						if(iniFin.length == 1)
						{
							ini = Long.parseLong(iniFin[0]);
							fin = f.length();
							
						}
						else
						{
							ini = Long.parseLong(iniFin[0]);
							fin = Long.parseLong(iniFin[1]);
							
						}
					}
					/////////////////////////////////////////////////////
					
					
					
					// TENGO QUE ENVIAR ESTO EN CADA CASO
					// PUEDE SER 200 o 206
					// sendMIMEHeading(out, 200, cType, f.length());
					
					
					if(!linea.startsWith("HEAD "))
					{
						if(range)
						{
							//206
							sendMIMEHeading206(out, cType, ini, fin, f.length());
							
							
							RandomAccessFile raf = new RandomAccessFile(buscaFichero(linea), "r");
							
							//busca el primer valor del range.
							raf.seek(ini);
							
							

							buff = new byte [(int)(fin - ini + 1)];
							
							
							int leidos = raf.read(buff);
							int escritos = 0;	
							//while(leidos != -1)
							while( escritos < (fin-ini+1) && leidos != -1 )
							{
								
								out.write(buff);
								escritos += leidos;
								
								
								leidos = raf.read(buff);
								
							}
							
							out.flush();
							
							s.shutdownOutput();
						}
						else
						{
							//200
							sendMIMEHeading(out, 200, cType, f.length());
							
							
							fileIn = new FileInputStream(f);
							
							int leidos = fileIn.read(buff);
							while(leidos != -1)
							{
								
								out.write(buff, 0, leidos);
								leidos = fileIn.read(buff);
								
							}
							System.out.println("PETICION: GET");
						}
						
					}
					else
					{
						//es HEAD:::
						//distingo entre range o no range
						if(range)
						{
							sendMIMEHeading206(out, cType,ini,fin,f.length());
							System.out.println("PETICION: RANGE + HEAD");
						}
						else
						{
							sendMIMEHeading(out, 200, cType, f.length());
							System.out.println("PETICION: HEAD");
						}
						
					}
					
					out.flush();
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(in != null)
				{
					in.close();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				if(fileIn != null)
				{
					fileIn.close();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				if(out != null)
				{
					out.close();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				if(s != null)
				{
					s.close();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void sendMIMEHeading206(OutputStream os, String cType, long ini, long fin , long fichTam)
	{
		PrintStream dos = new PrintStream(os);

			dos.print("HTTP/1.1 " + 206 + " ");
			dos.print("Partial Content\r\n");
			dos.print("Date: " + new Date() + "\r\n");
			dos.print("Server: Cutre http Server ver. -4.0\r\n");
			dos.print("Connection: close\r\n");
			dos.print("Content-Range: bytes "+ini+"-"+fin+"/"+fichTam+"\r\n");	//Content-Range: bytes 21010-47021/47022
			dos.print("Content-length: " + fichTam + "\r\n");
			dos.print("Content-type: " + cType + "\r\n");
			dos.print("\r\n");

			
			dos.flush();

		
	}
	private void sendMIMEHeading(OutputStream os, int code, String cType, long fSize) 
	{
		PrintStream dos = new PrintStream(os);
		dos.print("HTTP/1.1 " + code + " ");
		if (code == 200) 
		{
			dos.print("OK\r\n");
			dos.print("Date: " + new Date() + "\r\n");
			dos.print("Server: Cutre http Server ver. -4.0\r\n");
			dos.print("Connection: close\r\n");
			dos.print("Content-length: " + fSize + "\r\n");
			dos.print("Content-type: " + cType + "\r\n");
			dos.print("\r\n");
		}
		
		else if (code == 404) 
		{
			dos.print("File Not Found\r\n");
			dos.print("Date: " + new Date() + "\r\n");
			dos.print("Server: Cutre http Server ver. -4.0\r\n");
			dos.print("Connection: close\r\n");
			dos.print("Content-length: " + fSize + "\r\n");
			dos.print("Content-type: " + "text/html" + "\r\n");
			dos.print("\r\n");
		} 
		else if (code == 501)
		{
			dos.print("Not Implemented\r\n");
			dos.print("Date: " + new Date() + "\r\n");
			dos.print("Server: Cutre http Server ver. -4.0\r\n");
			dos.print("Connection: close\r\n");
			dos.print("Content-length: " + fSize + "\r\n");
			dos.print("Content-type: " + "text/html" + "\r\n");
			dos.print("\r\n");
		}
		
		dos.flush();
		
	}
	
	private String makeHTMLErrorText(int code, String txt) 
	{
		StringBuffer msg = new StringBuffer("<HTML>\r\n");
		msg.append(" <HEAD>\r\n");
		msg.append(" <TITLE>" + txt + "</TITLE>\r\n");
		msg.append(" </HEAD>\r\n");
		msg.append(" <BODY>\r\n");
		msg.append(" <H1>HTTP Error " + code + ": " + txt + "</H1>\r\n");
		msg.append(" </BODY>\r\n");
		msg.append("</HTML>\r\n");
		return msg.toString();
	}
	
	
	private File buscaFichero(String m) 
	{
		String fileName="";
		if (m.startsWith("GET "))
		{
			// A partir de una cadena de mensaje (m) correcta (comienza por GET)
			fileName = m.substring(4, m.indexOf(" ", 5));
			if (fileName.equals("/")) 
			{
			fileName += "index.html";
			}
		}
		else if (m.startsWith("HEAD "))
		{
		// A partir de una cadena de mensaje (m) correcta (comienza por HEAD)
			fileName = m.substring(6, m.indexOf(" ", 7));
			if (fileName.equals("/")) 
			{
				fileName += "index.html";
			}
		}
		else 
		{
			return null;
		}
		return new File(rutaHome, fileName);
	}
}
