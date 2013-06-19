package im.duk.cmlbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ApiRequest {


	public static String request(ReqType type, String player, int time) {
		switch (type) {
		case PLAYERS:
			return playersReq();
		case STATS:
			return statsReq(player);
		case TRACK:
			return trackReq(player, time);
		case TTM:
			return ttmReq(player);
		case TTMRANK:
			return ttmRankReq(player);
		case UPDATE:
			return updateReq(player);
		default:
			break;

		}
		return null;
	}

	private static String trackReq(String player, int time) {
		if (player != null) {
			player = player.replace(" ", "_");
			try {
				URL trackUrl = new URL("http://crystalmathlabs.com/tracker/api.php?type=track&player=" + player
						+ "&time=" + time);
				URLConnection trackUrlConnection = trackUrl.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(trackUrlConnection.getInputStream()));
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

	private static String updateReq(String player) {
		if (player != null) {
			player = player.replace(" ", "_");
			URL url;
			try {
				url = new URL("http://crystalmathlabs.com/tracker/api.php?type=update&player=" + player);
				URLConnection conn = url.openConnection();
				BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputString;
				String res = "";
				while ((inputString = read.readLine()) != null) {
					res += inputString;
				}
				return res;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String statsReq(String player) {
		if (player != null) {
			player = player.replace(" ", "_");
			try {
				URL statsUrl = new URL("http://crystalmathlabs.com/tracker/api.php?type=stats&player=" + player);
				URLConnection statsUrlConnection = statsUrl.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(statsUrlConnection.getInputStream()));
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

	private static String ttmRankReq(String player) {
		if (player != null) {
			player = player.replace(" ", "_");
			try {
				URL ttmRankUrl = new URL("http://crystalmathlabs.com/tracker/api.php?type=ttmrank&player=" + player);
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
		return request(type, null, 0);
	}

	public static String request(ReqType type, String player) {
		return request(type, player, 0);
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
			e.printStackTrace();
		}
		return null;
	}
}
