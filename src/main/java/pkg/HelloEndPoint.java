package pkg;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Websocket Endpoint implementation class HelloEndPoint
 */

@ServerEndpoint("/helloendpoint")
public class HelloEndPoint {

	// 現在のセッションを記録
	Session currentSession = null;

	public HelloEndPoint() {
		super();
	}

	/*
	 * 接続がクローズしたとき
	 */
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		// ...
	}

	/*
	 * 接続エラーが発生したとき
	 */
	@OnError
	public void onError(Throwable t) {
		// ...
	}

	/*
	 * 接続がオープンしたとき
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig ec) {
		/* セッション確立時の処理 */
		System.err.println("[セッション確立]");
		this.currentSession = session;
		try {
			this.currentSession.getBasicRemote().sendText("Hello. Server time is " + new Date());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * メッセージを受信したとき
	 */
	@OnMessage
	public void receiveMessage(String msg) throws IOException {
		/* メッセージ受信時の処理 */
		System.err.println("[受信]:" + msg);

		// メッセージをクライアントに送信する
		this.currentSession.getBasicRemote().sendText("Hello " + msg + ".");

		// ALL of open web socket
		Set<Session> sessions = currentSession.getOpenSessions();
		for (Session session : sessions) {
			try {
				session.getBasicRemote().sendText("Hello " + msg + " for all active clients");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}