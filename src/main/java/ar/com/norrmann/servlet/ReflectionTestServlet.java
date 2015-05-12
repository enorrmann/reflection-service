package ar.com.norrmann.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.reflections.Reflections;

/**
 * Servlet implementation class ReflectionTestServlet
 */
@WebServlet("/")
public class ReflectionTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ReflectionTestServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Reflections reflections = new Reflections("ar.gov.santafe");
		//Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(javax.ws.rs.Path.class);
		//Set<Method> getMethods = reflections.getMethodsAnnotatedWith(javax.ws.rs.GET.class);

		//response.setContentType("application/json");
		response.setContentType("text/html");
		

		PrintWriter out = response.getWriter();

		//entidades(annotated, out);
		apis(annotated, out);
		//methods(getMethods, out);

	}

	private void methods(Set<Method> methods, PrintWriter out) {
		
		for (Method aMethod:methods){
			out.write(aMethod.toString());
			out.print("<br/>");
			Annotation path = aMethod.getAnnotation(javax.ws.rs.Path.class);
			if (path != null){
				out.write(path.toString());
				out.print("<br/>");
			}
		}
		out.flush();
		
	}

	private void apis(Set<Class<?>> annotated, PrintWriter out) {
		for (Class aClass:annotated){
			Annotation path = aClass.getAnnotation(javax.ws.rs.Path.class);
			out.write(path.toString());
			out.print("<br/>");
			for (Method aMethod: aClass.getMethods()){
				
				Annotation get = aMethod.getAnnotation(javax.ws.rs.GET.class);
				Annotation post = aMethod.getAnnotation(javax.ws.rs.POST.class);
				Annotation methodPath = aMethod.getAnnotation(javax.ws.rs.Path.class);

				if (get!=null){
					out.write(" GET ");
					out.write(methodPath.toString());
					Class<?>[] paramTypes = aMethod.getParameterTypes();
					if (paramTypes!=null&&!(paramTypes.length==0)){
						out.print(" PARAMS ");
						out.print("(");
						for (Class aParamClass:paramTypes){
							out.write(aParamClass.getSimpleName());
							out.print(",");
						}
						out.print(")");

					}
					out.print("<br/>");
				}
			}
			out.print("<br/>");


		}
		out.flush();
		

		
	}

	private void entidades(Set<Class<?>> annotated, PrintWriter out) {
		out.println("{");
		for (Class aClass : annotated) {
			out.print("{\"className \" : \"");
			out.print(aClass.getName().substring(
					aClass.getName().lastIndexOf(".") + 1));
			out.print("\" , ");
			out.print("\"qualifiedClassName \" : \"");
			out.print(aClass.getName());
			out.print("\"}");

		}

		out.println("}");
		out.flush();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
