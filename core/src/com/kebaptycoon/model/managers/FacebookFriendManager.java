package com.kebaptycoon.model.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.StreamUtils;
import com.kebaptycoon.KebapTycoonGame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FacebookFriendManager {
    ArrayList<FacebookFriend> facebookFriends;
    boolean isLoaded = false;
    public FacebookFriendManager(){
        facebookFriends = new ArrayList<FacebookFriend>();

    }

    public ArrayList<FacebookFriend> getFriendsFromFacebook() throws IOException {
        if(!isLoaded()){
            String url = "https://graph.facebook.com/v2.6/me?fields=id%2Cname%2Cfriends&access_token=" +
                    KebapTycoonGame.getInstance().getPrefs().getString("facebook_access_token");

            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            JSONObject jsonObject = null;
            JSONArray jsonArray = null;

            try {

                jsonObject = new JSONObject(response.toString()).getJSONObject("friends");
                jsonArray = jsonObject.getJSONArray("data");

                for(int i = 0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    String userId = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String profilePictureURL = "https://graph.facebook.com/v2.6/" +
                            userId+"/picture?access_token="
                            + KebapTycoonGame.getInstance().getPrefs().getString("facebook_access_token");
                    facebookFriends.add(new FacebookFriend(name, userId, profilePictureURL, null));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(FacebookFriend f : facebookFriends)
                System.out.println(f.name);


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
                    byte[] bytes = new byte[200 * 1024]; // assuming the content is not bigger than 200kb.
                    int numBytes;

                    for(final FacebookFriend facebookFriend : facebookFriends) {
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
                }
            }).start();
            isLoaded = true;
        }
        return facebookFriends;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public class FacebookFriend{

        String name;
        String userId;
        String profilePictureURL;
        TextureRegion profilePicture;

        public FacebookFriend(String name, String userId, String profilePictureURL, TextureRegion profilePicture) {
            this.name = name;
            this.userId = userId;
            this.profilePictureURL = profilePictureURL;
            this.profilePicture = profilePicture;
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

        public TextureRegion getProfilePicture() {
            return profilePicture;
        }
    }

}
