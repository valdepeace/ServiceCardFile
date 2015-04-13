package org.service.filecard;


import java.util.List;
import java.util.Iterator;




import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.tacografo.file.FileTGD;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jboss.resteasy.logging.Logger;

/**
 * @author Andres Carmona Gil
 * Servicio para la recepcion de archivos tgd con rest utilizando jax-rs y estructura
 * para la interpretacion de los ficheros tgd  
 */
@Path("filecard")
public class ServiceFileCard {
	private static final Logger log = Logger.getLogger(ServiceFileCard.class);
	@GET
	public String Test(){
		return "conexion establecida";
	}
	/**
	 * Espera datos de un multpart/form-data de un formulario y producira si el fichero 
	 * tgd es correcto una respuesta con de la clase FileTGD en json.
	 * @param request
	 * @return response json FileTGD
	 * @throws WebApplicationException
	 */
	@SuppressWarnings({ "finally", "null" })
	@POST
	@Produces("application/json")
	@Consumes("multipart/form-data")
	public Response uploadFile(@Context HttpServletRequest request) throws WebApplicationException {
		log.info("Servico iniciado con multipar a : "
				+ ServletFileUpload.isMultipartContent(request));

		Response response;
		ResponseBuilder builder = null;
		String error="";
		FileTGD tgd = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			

			// Create a factory for disk-based file items
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

			// String path = request.getRealPath("") + File.separatorChar +
			// "publishFiles" + File.separatorChar;

			// File f = new File(path + "myfile.txt");
			// File tmpDir = new File("c:\\tmp");

			// File destinationDir = new File(path);

			// Set the size threshold, above which content will be stored on
			// disk.
			// fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB

			// Set the temporary directory to store the uploaded files of size
			// above threshold.
			// fileItemFactory.setRepository(tmpDir);

			// Create a new file upload handler
			
			ServletFileUpload uploadHandler = new ServletFileUpload(
					fileItemFactory);

			try {
				
				/*
				 * Parse the request
				 */
				List items = uploadHandler.parseRequest(request);
				Iterator itr = items.iterator();

				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					/*
					 * Handle Form Fields.
					 */
					// si es un campo del part sino sera un fichero
					if (!item.isFormField()) {
						/*
						 * Write file to the ultimate location.
						 */
						// Estas lineas son para grabar el fichero en disco
						// File file = new File(destinationDir,item.getName());
						// item.write(file);
						
						tgd = new FileTGD(item.getInputStream(),
								(int) item.getSize());
						log.info("tgd construct true");

					}
					
				}
				if (tgd != null) {
					log.info("Response application json");
					builder=Response.ok(tgd.getJson(), MediaType.APPLICATION_JSON);					
				} else {
					log.warn("tgd error= archivo no reconocido");
					builder=Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity("tgd incorrecto");					
					//return Response.serverError().entity("archivo no valido").build();//return res; //throw new WebApplicationException(res);
					// return Response.ok("tgd incorrecto").build();
				}
				
			} catch (FileUploadException ex) {
				log.error(ex.getMessage());
				 error+= "\nError encountered while parsing the request "
						+ ex.getMessage();
				builder=Response.serverError().entity(error);
				
				
			} catch (Exception ex) {
				error+= "\nError encountered while uploading file " + ex
						+ ex.getMessage();
				builder=Response.serverError().entity(error);
			}finally{
			response=builder.build();
			return response;
			}
		}
		
		response=builder.build();
		return response;
	}
}


