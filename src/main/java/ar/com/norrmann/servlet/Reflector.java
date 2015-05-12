package ar.com.norrmann.servlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflector {

	public List<String> getUris(Class aClass) {
		List<String> uris = new ArrayList<String>();
		uris.addAll(getUris(aClass,javax.ws.rs.GET.class));
		uris.addAll(getUris(aClass,javax.ws.rs.POST.class));
		uris.addAll(getUris(aClass,javax.ws.rs.PUT.class));
		uris.addAll(getUris(aClass,javax.ws.rs.DELETE.class));
		return uris;
	}
	public List<String> getUris(Class aClass,Class annotationClass) {

		List<String> uris = new ArrayList<String>();
		Annotation path = aClass.getAnnotation(javax.ws.rs.Path.class);
		String uriBase = getValue("",path);

		for (Method aMethod : aClass.getMethods()) {

			Annotation annotation = aMethod.getAnnotation(annotationClass);
			Annotation methodPath = aMethod.getAnnotation(javax.ws.rs.Path.class);

			if (annotation != null) {
				if (methodPath!=null){
					uris.add(getValue(uriBase,methodPath));
				} else {
					uris.add(uriBase);
				}
				//Class<?>[] paramTypes = aMethod.getParameterTypes();
				// if (paramTypes!=null&&!(paramTypes.length==0)){
				// out.print(" PARAMS ");
				// out.print("(");
				// for (Class aParamClass:paramTypes){
				// out.write(aParamClass.getSimpleName());
				// out.print(",");
				// }
				// out.print(")");
				//
				// }
				// out.print("<br/>");
			}
		}
return uris;
	}
private String getValue(String uriBase, Annotation annotation){
	final String VALUE_STRING = "value=";
	String annotationString = annotation.toString();
	String value = annotationString.substring(annotationString.lastIndexOf(VALUE_STRING)+VALUE_STRING.length()).replaceAll("\\)", "");
	if (value.indexOf("/")!=0){
		value = "/".concat(value);
	}
	return uriBase+value;
}
}
