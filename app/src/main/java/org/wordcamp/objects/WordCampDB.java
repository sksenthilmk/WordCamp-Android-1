package org.wordcamp.objects;

import com.google.gson.Gson;

import org.wordcamp.objects.wordcamp.WordCampNew;
import org.wordcamp.utils.WordCampUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by aagam on 28/1/15.
 */
public class WordCampDB implements Serializable {

    private int wc_id;
    private String wc_title;
    private String wc_start_date;
    private String wc_end_date;
    private String last_scanned_gmt;
    private String gson_object;
    private String url;
    private String twitter;
    public String featureImageUrl;
    private String address;
    private String venue;
    private String about;
    private String location;
    private Gson gson;
    public boolean isMyWC = false;

    public WordCampDB(int wc_id, String wc_title, String wc_start_date, String wc_end_date,
                      String last_scanned_gmt, String gson_object,
                      String url, String featureImageUrl, boolean isMyWC,
                      String twitter, String address, String venue, String location, String about) {
        this.wc_id = wc_id;
        this.wc_title = wc_title;
        this.wc_start_date = wc_start_date;
        this.wc_end_date = wc_end_date;
        this.last_scanned_gmt = last_scanned_gmt;
        this.gson_object = gson_object;
        this.url = url;
        this.featureImageUrl = featureImageUrl;
        this.isMyWC = isMyWC;
        this.twitter = twitter;
        this.address = address;
        this.venue = venue;
        this.location = location;
        this.about = about;
    }

    public WordCampDB(WordCampNew wcs, String lastscanned) {
        gson = new Gson();
        this.wc_id = wcs.getID();
        this.wc_title = wcs.getTitle();
        this.last_scanned_gmt = lastscanned;
        this.gson_object = gson.toJson(wcs);

        HashMap<String, String> urlAndTwitterAndDate = WordCampUtils.getTwitterAndUrl(wcs);
        this.wc_start_date = urlAndTwitterAndDate.get("Start Date (YYYY-mm-dd)");
        this.wc_end_date = urlAndTwitterAndDate.get("End Date (YYYY-mm-dd)");
        this.url = urlAndTwitterAndDate.get("URL");
        this.twitter = urlAndTwitterAndDate.get("WordCamp Hashtag");
        this.address = urlAndTwitterAndDate.get("Physical Address");
        this.venue = urlAndTwitterAndDate.get("Venue Name");
        this.location = urlAndTwitterAndDate.get("Location");
        this.about = wcs.getContent();
    }

    public String getFeatureImageUrl() {
        return featureImageUrl;
    }

    public void setFeatureImageUrl(String featureImageUrl) {
        this.featureImageUrl = featureImageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWc_id() {
        return wc_id;
    }

    public void setWc_id(int wc_id) {
        this.wc_id = wc_id;
    }

    public String getWc_title() {
        return wc_title;
    }

    public void setWc_title(String wc_title) {
        this.wc_title = wc_title;
    }

    public String getWc_start_date() {
        return wc_start_date;
    }

    public void setWc_start_date(String wc_start_date) {
        this.wc_start_date = wc_start_date;
    }

    public String getWc_end_date() {
        return wc_end_date;
    }

    public void setWc_end_date(String wc_end_date) {
        this.wc_end_date = wc_end_date;
    }

    public String getLast_scanned_gmt() {
        return last_scanned_gmt;
    }

    public void setLast_scanned_gmt(String last_scanned_gmt) {
        this.last_scanned_gmt = last_scanned_gmt;
    }

    public String getGson_object() {
        return gson_object;
    }

    public void setGson_object(String gson_object) {
        this.gson_object = gson_object;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
