package scripts.pre

import javax.servlet.http.HttpServletResponse

import com.netflix.config.DynamicBooleanProperty
import com.netflix.config.DynamicPropertyFactory
import com.antzuhl.zeus.core.context.RequestContext
import com.antzuhl.zeus.core.filters.ZeusFilter

public class HealthCheck extends ZeusFilter{
	@Override
	public String filterType() {
		return "pre";
	}
	
	public Object uri() {
		return "/healthcheck";
	}
	
	@Override
	boolean shouldFilter() {
		String path = RequestContext.currentContext.getRequest().getRequestURI()
		return path.equalsIgnoreCase(uri())||path.toLowerCase().endsWith(uri());
	}
	
	public int filterOrder(){
		return 0;
	}
	
	public String responseBody() {
		return "<health>ok</health>";
	}
	
	@Override
	Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		// Set the default response code for static filters to be 200
		ctx.getResponse().setStatus(HttpServletResponse.SC_OK);
		ctx.getResponse().setContentType('application/xml')
		// first StaticResponseFilter instance to match wins, others do not set body and/or status
		if (ctx.getResponseBody() == null) {
			ctx.setResponseBody(responseBody())
			ctx.sendZeusResponse(false);
		}
	}
}
