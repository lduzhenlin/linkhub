package com.qishanor.common.data.tenant;

import com.qishanor.common.data.constant.CommonConstant;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * 当使用RestTemplate请求时，传递 RestTemplate 请求的租户ID
 */
public class TenantRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		if (TenantContextHolder.getTenantId() != null) {
			request.getHeaders().set(CommonConstant.TENANT_ID, String.valueOf(TenantContextHolder.getTenantId()));
		}

		return execution.execute(request, body);
	}

}
