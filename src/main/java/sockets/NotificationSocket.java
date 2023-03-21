package sockets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

@ServerEndpoint(value = "/bala" )
public class NotificationSocket {
	Map<String,List<Session>> mapOfUsers = new HashMap<>();
//	@OnOpen
//	public void open(Session session,EndpointConfig config) {
//		HttpServletRequest request = (HttpServletRequest) config.getUserProperties()
//                .get("javax.websocket.http.request");
//        Cookie[] cookies = request.getCookies();
//	}
}
