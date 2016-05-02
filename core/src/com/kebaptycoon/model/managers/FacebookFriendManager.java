package com.kebaptycoon.model.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.StreamUtils;
import com.kebaptycoon.KebapTycoonGame;
import com.kebaptycoon.utils.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FacebookFriendManager {
    ArrayList<FacebookFriend> facebookFriends;
    boolean isLoading;
    String venueCount, level, dailyIncome;

    public FacebookFriendManager(){
        isLoading = false;

        venueCount = "N/A";
        level = "N/A";
        dailyIncome = "N/A";

        facebookFriends = null;
    }

    private void getFriendsFromFacebook() throws IOException {
        if(!isLoaded()){
            if(isLoading)
                return;

            isLoading = true;

            new Thread(new Runnable() {
                /** Downloads the content of the specified url to the array. The array has to be big enough. */
                private int download (byte[] out, String url) {
                    InputStream in = null;
                    try {
                        HttpURLConnection conn = null;
                        conn = (HttpURLConnection)new URL(url).openConnection();
                        conn.setDoInput(true);
                        conn.setDoOutput(false);
                        conn.setUseCaches(true);
                        conn.connect();
                        in = conn.getInputStream();
                        int readBytes = 0;
                        while (true) {
                            int length = in.read(out, readBytes, out.length - readBytes);
                            if (length == -1) break;
                            readBytes += length;
                        }
                        return readBytes;
                    } catch (Exception ex) {
                        return 0;
                    } finally {
                        StreamUtils.closeQuietly(in);
                    }
                }

                @Override
                public void run () {

                    String url = "https://graph.facebook.com/v2.6/me?fields=name,friends{picture.type(large),name}&access_token=" +
                            KebapTycoonGame.getInstance().getPrefs().getString("facebook_access_token");
                    URL obj = null;
                    try {
                        obj = new URL(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    HttpURLConnection con = null;
                    try {
                        con = (HttpURLConnection) obj.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    BufferedReader in = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    try {
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonObject = null;
                    JSONArray jsonArray = null;

                    ArrayList<FacebookFriend> fList = new ArrayList<FacebookFriend>();

                    try {

                        jsonObject = new JSONObject(response.toString()).getJSONObject("friends");
                        jsonArray = jsonObject.getJSONArray("data");

                        for(int i = 0; i < jsonArray.length(); i++){
                            jsonObject = jsonArray.getJSONObject(i);
                            String userId = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String profilePictureURL = jsonObject.getJSONObject("picture").
                                                                getJSONObject("data").getString("url");
                            try {
                                JSONObject userDetails = new JSONObject(getUserDetailsFromServer(userId));
                                if(userDetails.getString("code").equals("1")){
                                    System.out.println("code 1");
                                    
                                    JSONObject playerStat = userDetails.getJSONObject("player_stat");
                                    venueCount = playerStat.getString("venue_count");
                                    level = playerStat.getString("level");
                                    dailyIncome = playerStat.getString("daily_income");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                venueCount = "N/A";
                                level = "N/A";
                                dailyIncome = "N/A";
                            }
                            fList.add(new FacebookFriend(name, userId, profilePictureURL, dailyIncome, level,
                                    venueCount, null));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    byte[] bytes = new byte[200 * 1024]; // assuming the content is not bigger than 200kb.
                    int numBytes;

                    for(final FacebookFriend facebookFriend : fList) {
                        numBytes = download(bytes, facebookFriend.profilePictureURL);
                        if (numBytes != 0) {
                            // load the pixmap, make it a power of two if necessary (not needed for GL ES 2.0!)
                            Pixmap pixmap = new Pixmap(bytes, 0, numBytes);
                            final int originalWidth = pixmap.getWidth();
                            final int originalHeight = pixmap.getHeight();
                            int width = MathUtils.nextPowerOfTwo(pixmap.getWidth());
                            int height = MathUtils.nextPowerOfTwo(pixmap.getHeight());
                            final Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
                            potPixmap.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
                            pixmap.dispose();
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run () {
                                    facebookFriend.profilePicture = new TextureRegion(new Texture(potPixmap),
                                            0, 0, originalWidth, originalHeight);
                                }
                            });
                        }
                    }

                    facebookFriends = fList;

                }
            }).start();
        }
    }

    public String getUserDetailsFromServer(String userId) throws IOException {
        String url = Globals.SERVER_API_URL + "get_player_stats.php?";
        url += "fb_user_id=" + userId;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }

    public boolean isLoaded() {
        return facebookFriends != null;
    }

    public ArrayList<FacebookFriend> getFacebookFriends() throws IOException {
        if (isLoaded())
            return facebookFriends;

        getFriendsFromFacebook();
        return facebookFriends;
    }

    public class FacebookFriend{

        String name;
        String userId;
        String profilePictureURL;
        String dailyIncome;
        String level;
        String venueCount;
        TextureRegion profilePicture;

        public FacebookFriend(String name, String userId, String profilePictureURL, String dailyIncome,
                              String level, String venueCount, TextureRegion profilePicture) {
            this.name = name;
            this.userId = userId;
            this.profilePictureURL = profilePictureURL;
            this.profilePicture = profilePicture;
            this.level = level;
            this.venueCount = venueCount;
            this.dailyIncome = dailyIncome;
        }

        public String getName() {
            return name;
        }

        public String getUserId() {
            return userId;
        }

        public String getProfilePictureURL() {
            return profilePictureURL;
        }

        public String getDailyIncome() {
            return dailyIncome;
        }

        public String getLevel() {
            return level;
        }

        public String getVenueCount() {
            return venueCount;
        }

        public TextureRegion getProfilePicture() {
            return profilePicture;
        }
    }

}
