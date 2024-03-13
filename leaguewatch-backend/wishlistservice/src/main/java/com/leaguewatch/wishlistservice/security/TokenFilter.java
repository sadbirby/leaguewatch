package com.leaguewatch.wishlistservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    private static final String JWT_SECRET = "TfHbLdTbqNCWfWNEoguZ13EVer8U06eS";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("Authorization");

        try {
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            } else {
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new ServletException("Token missing or invalid!");
                }
            }
            try {
                final String token = authHeader.substring(7);
                Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
                request.setAttribute("user", servletRequest.getParameter("id"));
            } catch (Exception e) {
                throw new ServletException("Unable to process token! Token might have expired.", e);
            }
        } catch (Exception ex) {
            logger.error("Error processing JWT token", ex);
            ResponseEntity<String> responseEntity = new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
            ((HttpServletResponse) servletResponse).setStatus(responseEntity.getStatusCode().value());
            servletResponse.getWriter().write(responseEntity.getBody());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
