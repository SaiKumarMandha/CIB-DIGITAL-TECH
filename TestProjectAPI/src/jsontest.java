import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class jsontest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// https://dog.ceo/dog-api/

		try {

			URL url = new URL("https://dog.ceo/api/breeds/list/all");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			connect(conn);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("##1. List of all breeds:: \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				// System.out.println("####");
				if (output.contains("retriever")) {
					System.out.println("##2. PASS: as retriever bread is present in list");
				} else {
					System.out.println("##2. FAIL: as retriever bread is missing in list");
				}
			}
			getSubList("retriever");// pass the bread name youwant to get the
									// sublist for

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
//method to get sublist of retriever breed 
	private static void getSubList(String breedname) {
		// TODO Auto-generated method stub

		try {

			URL url = new URL("https://dog.ceo/api/breed/" + breedname + "/list");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			connect(conn);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("##3. SubList of " + breedname + " :: \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);

			}
			getRandomImageOfBread(breedname);
			// https://dog.ceo/api/breed/"+breedname+"/golden/images/random
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
//method to get the random image of golden in retriever breed
	private static void getRandomImageOfBread(String breedname) {
		// TODO Auto-generated method stub

		try {

			URL url = new URL("https://dog.ceo/api/breed/" + breedname + "/golden/images/random");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			connect(conn);

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("##4. Random image of golden in bread -" + breedname + " is:: \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);

			}
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * @param conn
	 * @throws ProtocolException
	 * @throws IOException
	 * @throws RuntimeException
	 */
	private static void connect(HttpURLConnection conn) throws ProtocolException, IOException, RuntimeException {
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
	}

}
