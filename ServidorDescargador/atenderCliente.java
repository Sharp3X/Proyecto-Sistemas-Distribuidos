package ServidorDescargador;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Date;

public class atenderCliente extends Thread {
	Socket s;
	String directorioHome = "public_html";

	public atenderCliente(Socket s) {
		this.s = s;
	}

	public void run() {
		// leo en el datainputstream la peticion y respondo
		DataInputStream dis = null;
		OutputStream os = null;
		FileInputStream fis = null;

		try {
			os = s.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			dis = new DataInputStream(s.getInputStream());
			System.out.println("antes");
			String mensaje = dis.readLine();
			System.out.println(mensaje);

			if (mensaje != null) {
				File aux = buscaFichero(mensaje);

				String cType = URLConnection.guessContentTypeFromName(aux.getName());
				String error;
				long fSize;
				if (aux == null) {
					error = makeHTMLErrorText(501, "Error 501");
					fSize = error.length();
					sendMIMEHeading(s.getOutputStream(), 501, null, fSize);
					bw.write(error);
					bw.flush();

				} else if (aux.exists() && aux.isFile()) {

					fSize = aux.length();
					sendMIMEHeading(s.getOutputStream(), 200, cType, fSize);

					// comprobamos si es head o get
					if (mensaje.startsWith("GET ")) {
						String extra=dis.readLine();
						long i1=0;
						long i2=aux.length();
						while(extra.isEmpty()) {
							if(extra.startsWith("Range "))
							{
								i1=Integer.parseInt(extra.split(": ")[1].split("-")[0]);
								i2=Integer.parseInt(extra.split(": ")[1].split("-")[1]);
							}
						}
												
						fis = new FileInputStream(aux);
						int buffSize=1000;
						byte[] buff = new byte[buffSize];
						int leidos = fis.read(buff);
						while (leidos != -1) {
							if(i1-1000>0) {
								i1-=1000;
								i2-=1000;
							}
							else if(i2-1000>0){
								os.write(buff, (int)i1, leidos);
								i1=0;
								i2=1000;
							}
							else {
								os.write(buff, (int)i1, (int)i2);
							}
							leidos = fis.read(buff);
						}
					}
				} else {
					System.out.println("Llega al 404");
					error = makeHTMLErrorText(404, "Error 404");
					fSize = error.length();
					sendMIMEHeading(s.getOutputStream(), 404, null, fSize);
					bw.write(error);
					bw.flush();
					System.out.println("envia");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cerrar(fis);
		}

	}

	public static void cerrar(Closeable o) {
		try {
			if (o != null) {
				o.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File buscaFichero(String m) {
		String fileName = "";
		if (m.startsWith("GET ")) {
			// A partir de una cadena de mensaje (m) correcta (comienza por GET)
			fileName = m.substring(4, m.indexOf(" ", 5));
			if (fileName.equals("/")) {
				fileName += "index.html";
			}
			return new File(directorioHome, fileName);
		}
		if (m.startsWith("HEAD ")) {
			// A partir de una cadena de mensaje (m) correcta (comienza por HEAD)
			fileName = m.substring(6, m.indexOf(" ", 7));
			if (fileName.equals("/")) {
				fileName += "index.html";
			}
			return new File(directorioHome, fileName);
		}
		return null;
	}

	private void sendMIMEHeading(OutputStream os, int code, String cType, long fSize) {
		PrintStream dos = new PrintStream(os);
		dos.print("HTTP/1.1 " + code + " ");
		if (code == 200) {
			dos.print("OK\r\n");
			dos.print("Date: " + new Date() + "\r\n");
			dos.print("Server: Cutre http Server ver. -4.0\r\n");
			dos.print("Connection: close\r\n");
			dos.print("Content-length: " + fSize + "\r\n");
			dos.print("Content-type: " + cType + "\r\n");
			dos.print("\r\n");
		} else if (code == 404) {
			dos.print("File Not Found\r\n");
			dos.print("Date: " + new Date() + "\r\n");
			dos.print("Server: Cutre http Server ver. -4.0\r\n");
			dos.print("Connection: close\r\n");
			dos.print("Content-length: " + fSize + "\r\n");
			dos.print("Content-type: " + "text/html" + "\r\n");
			dos.print("\r\n");
		} else if (code == 501) {
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

	private String makeHTMLErrorText(int code, String txt) {
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

}
