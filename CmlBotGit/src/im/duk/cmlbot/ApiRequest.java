package im.duk.cmlbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ApiRequest {

	public static String request(ReqType type, String player, String time) {
		switch (type) {
		case PLAYERS:
			return playersReq();
		case STATS:
			break;
		case TRACK:
			break;
		case TTM:
			return ttmReq(player);
		case TTMRANK:
			return ttmRankReq(player);
		default:
			break;

		}
		return null;
	}

	private static String ttmRankReq(String player) {
		if (player != null) {
			player = player.replace(" ", "_");
			try {
				URL ttmRankUrl = new URL("http://crystalmathlabs.com/tracker/api.php?type=ttm&player=" + player);
				URLConnection ttmRankConnection = ttmRankUrl.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(ttmRankConnection.getInputStream()));
				String inputLine;
				String response = "";
				while ((inputLine = reader.readLine()) != null) {
					response += inputLine;
				}
				return response;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}

	private static String ttmReq(String player) {
		if (player != null) {
			player = player.replace(" ", "_");
			try {
				URL ttmUrl = new URL("http://crystalmathlabs.com/tracker/api.php?type=ttm&player=" + player);
				URLConnection ttmConnection = ttmUrl.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(ttmConnection.getInputStream()));
				String inputLine;
				String response = "";
				while ((inputLine = reader.readLine()) != null) {
					response += inputLine;
				}
				return response;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}

	public static String request(ReqType type) {
		return request(type, null, null);
	}

	public static String request(ReqType type, String player) {
		return request(type, player, null);
	}

	private static String playersReq() {
		try {
			URL playersUrl = new URL("http://crystalmathlabs.com/tracker/api.php?type=players");
			URLConnection playersConnection = playersUrl.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(playersConnection.getInputStream()));
			String inputLine;
			String response = "";
			while ((inputLine = reader.readLine()) != null) {
				response += inputLine;
			}
			return response;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
