package com.kebaptycoon.desktop;

import com.kebaptycoon.util.FacebookLoginHelper;
import com.kebaptycoon.util.Globals;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class DesktopFacebook implements FacebookLoginHelper{
    Timer timer;

    @Override
    public void connectFacebook() {
        int desktopLoginId = 0;

        try {
            desktopLoginId = Integer.parseInt(getDesktopLoginId());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(desktopLoginId > 0){
            if(Desktop.isDesktopSupported()){
                try {
                    Desktop.getDesktop().browse
                            (new URI("https://www.facebook.com/dialog/oauth?client_id="+ Globals.FACEBOOK_APP_ID
                                    + "&redirect_uri="+ Globals.FACEBOOK_REDIRECT_URL + "&state=" + desktopLoginId ));

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                timer = new Timer();
                int begin = 1000;
                timer.scheduleAtFixedRate(new FacebookLoginQueryTask(desktopLoginId), begin,
                        Globals.FACEBOOK_LOGIN_QUERY_TIMER_PERIOD);
            }
        }else{
            System.out.println("Couldn't connect.");
        }
    }

    @Override
    public void disconnectFacebook() {

    }

    @Override
    public boolean isConnectedFacebook() {
        return false;
    }

    public String getDesktopLoginId() throws IOException, JSONException {

        String url = Globals.SERVER_API_URL + "get_desktop_login_id.php";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject jsonObject = new JSONObject(response.toString());
        return jsonObject.getString("login_id");
    }

    class FacebookLoginQueryTask extends TimerTask {

        int desktopLoginId;

        public FacebookLoginQueryTask(int desktopLoginId){
            this.desktopLoginId = desktopLoginId;
        }
        @Override
        public void run() {

            try {
                String loginResponse = makeRepeatedLoginQuery(desktopLoginId);
                if(!loginResponse.equals("NULL")){
                    System.out.println(loginResponse);
                    timer.cancel();
                }else
                    System.out.println("NULL");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String makeRepeatedLoginQuery(int desktopLoginId) throws IOException, JSONException {
            String url = Globals.SERVER_API_URL + "desktop_facebook_login_with_id.php";
            url += "?login_id=" + desktopLoginId;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            System.out.println(jsonObject.getString("facebook_id"));
            return jsonObject.getString("facebook_id");
        }
    }
}
