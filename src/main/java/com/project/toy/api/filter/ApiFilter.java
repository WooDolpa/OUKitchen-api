package com.project.toy.api.filter;

import com.project.toy.api.config.StaticApplicationContext;
import com.project.toy.api.constant.ApiConstants;
import com.project.toy.api.service.ApiAccountService;
import com.project.toy.common.enums.YesNoType;
import com.project.toy.common.model.ApiAccountModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/05
 */
@Slf4j
public class ApiFilter implements Filter {

    private final static String NO_NEED_AUTH_API = new StringBuilder()
//            .append("/index.html").append("|")
            .toString();

    /**
     * 인증이 필요 없는 API 인지 여부
     *
     *
     * @param servletRequest
     * @return
     */
    private boolean isNoNeedAuthApi(ServletRequest servletRequest) {
        if (((HttpServletRequest) servletRequest).getServletPath().matches(this.NO_NEED_AUTH_API)) {
            return true;
        }

        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("ApiFilter|doFilter|Start");
        CustomHttpServletRequest customHttpServletRequest = null;
        boolean isNoNeedAuthApi = false;

        // service 선언
        ApiAccountService apiAccountService = (ApiAccountService) StaticApplicationContext.getContext().getBean(ApiConstants.SPRING_API_ACCOUNT_SERVICE_NAME);

        // HttpServletWrapper
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        customHttpServletRequest = new CustomHttpServletRequest(httpServletRequest);

        // 인증정보 추출
        String auth = httpServletRequest.getHeader(ApiConstants.OUKITCHEN_AUTHORIZATION);

        // 인증 정보 길이
        int authLength = !StringUtils.isEmpty(auth) ? auth.trim().length() : 0;

        // 인증이 필요한 API인지 체크
        if(authLength == 0){
            if(isNoNeedAuthApi(servletRequest)){
                isNoNeedAuthApi = true;
            }
        }

        log.info("ApiFilter|doFilter|isNoNeedAuthApi:{}", isNoNeedAuthApi);

        if(!isNoNeedAuthApi){
            if(authLength != 0){

                String[] authArr = auth.split(":");

                if(authArr == null || authArr.length != 2){
                    // 인증키 포멧이 아니거나 Null
                    customHttpServletRequest.putHeader(ApiConstants.OUKITCHEN_ACCOUNT_AUTH, YesNoType.No.toStr());
                }else{

                    String clientId = authArr[0];
                    String secretKey = authArr[1];

                    Optional<ApiAccountModel> apiAccountModelOptional = apiAccountService.findApiAccount(clientId, secretKey);

                    if(apiAccountModelOptional.isPresent()){
                        customHttpServletRequest.putHeader(ApiConstants.OUKITCHEN_ACCOUNT_AUTH, YesNoType.Yes.toStr());
                    }else{
                        customHttpServletRequest.putHeader(ApiConstants.OUKITCHEN_ACCOUNT_AUTH, YesNoType.No.toStr());
                    }
                }

            }else{
                customHttpServletRequest.putHeader(ApiConstants.OUKITCHEN_ACCOUNT_AUTH, YesNoType.No.toStr());
            }
        }

        filterChain.doFilter(customHttpServletRequest, servletResponse);
        log.info("ApiFilter|doFilter|End");
    }

    @Override
    public void destroy() {

    }
}
